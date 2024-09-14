<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称/隐患位置" prop="location" label-width="120px">
        <el-input
          v-model="queryParams.location"
          placeholder="请输入名称/隐患位置"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status" >
        <el-select v-model="queryParams.status" placeholder="请选择状态" filterable clearable>
          <el-option
            v-for="dict in dict.type.danger_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="业主单元类型" label-width="100px" prop="highRiskType"  v-if="projectType === '3'">
        <el-select v-model="queryParams.highRiskType" placeholder="请选择业主单元类型" filterable clearable>
          <el-option
            v-for="dict in dict.type.high_risk_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dangerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" width="60" prop="id" />
      <el-table-column label="业主单元" align="center" prop="unitName" min-width="120" :show-overflow-tooltip="true"/>
      <el-table-column label="轮次" v-if="projectType === '4'" align="center" prop="rounds" width="50" />
      <el-table-column label="楼栋" v-if="projectType === '2'" align="center" prop="buildingName" min-width="120" :show-overflow-tooltip="true"/>
      <el-table-column label="公共区域/户" v-if="projectType === '1' || projectType === '2'" align="center" prop="areaName" min-width="120" :show-overflow-tooltip="true"/>
      <el-table-column label="充电桩" v-if="projectType === '4'" align="center" prop="chargingPileName" min-width="120" :show-overflow-tooltip="true"/>
      <el-table-column label="隐患位置" align="center" prop="location" min-width="200" :show-overflow-tooltip="true" />
      <el-table-column label="隐患描述" align="center" prop="description" min-width="200" :show-overflow-tooltip="true" />
      <el-table-column label="整改建议" align="center" prop="suggestions" min-width="200" :show-overflow-tooltip="true"  />
      <el-table-column label="隐患等级" align="center" prop="level" width="80" :formatter="dangerLevelFormat"  />
      <el-table-column label="状态" align="center" prop="status" width="80" :formatter="dangerStatusFormat" />
      <el-table-column label="检测员" align="center" width="120" prop="inspector" />
      <el-table-column label="检测单位" align="center" width="120" prop="detectName" min-width="300" :formatter="detectFormat" :show-overflow-tooltip="true"/>
      <el-table-column label="初检时间" align="center" prop="initialTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.initialTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="外观图" align="center" prop="dangerPic" width="100" v-if="this.projectType === '1' || this.projectType === '2'">
        <template slot-scope="scope">
          <image-preview :src="scope.row.formType === 'B' ? scope.row.overallPic : scope.row.dangerPic" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="检测图" align="center" prop="dangerPic" width="100"  v-if="this.projectType === '1' || this.projectType === '2' ">
        <template slot-scope="scope">
          <image-preview :src="scope.row.inspectionPic" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="隐患图" align="center" prop="dangerPic" width="100" v-if="this.projectType === '3' || this.projectType === '4'">
        <template slot-scope="scope">
          <image-preview :src="scope.row.dangerPic" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="整改图" align="center" prop="rectificationPic" width="100" >
        <template slot-scope="scope">
          <image-preview :src="scope.row.rectificationPic" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="复检照片" align="center" prop="detectPic" width="100" v-if="false">
        <template slot-scope="scope">
          <image-preview :src="scope.row.detectPic" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" align="center" width="160" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleView(scope.row)"
          >查看</el-button>
          <el-dropdown size="mini" @command="(command) => handleStatus(scope.row, command)" v-if="scope.row.status != null && scope.row.status != '' && scope.row.status != '9'" >
            <el-button size="mini" type="text" icon="el-icon-refresh-left">重置</el-button>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="0" icon="el-icon-edit" >重置待整改</el-dropdown-item>
                <el-dropdown-item command="1" icon="el-icon-edit" >重置待复检</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 隐患数据详情对话框 -->
    <el-dialog title="隐患详情" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" disabled>
        <el-form-item label="业主单元" prop="unitName">
          <el-input v-model="form.unitName"  />
        </el-form-item>
        <el-form-item label="楼栋名称" prop="buildingName" v-if="projectType === '2'">
          <el-input v-model="form.buildingName" />
        </el-form-item>
        <el-form-item label="公共区域/户" prop="areaName" v-if="projectType === '1' || projectType === '2'">
          <el-input v-model="form.areaName" />
        </el-form-item>
        <el-form-item label="充电桩" prop="chargingPileName" v-if="projectType === '4'">
          <el-input v-model="form.chargingPileName" />
        </el-form-item>
        <el-form-item label="位置" prop="location">
          <el-input v-model="form.location" />
        </el-form-item>
        <el-form-item label="隐患等级" prop="level">
          <el-select v-model="form.level" >
            <el-option
              v-for="dict in levelOptions"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="隐患描述" prop="description">
          <el-input v-model="form.description" type="textarea" disabled />
        </el-form-item>
        <el-form-item label="整改建议" prop="suggestions">
          <el-input v-model="form.suggestions" type="textarea" disabled/>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" disabled>
            <el-option
              v-for="dict in dict.type.again_test_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="检测员" prop="inspector">
          <el-input v-model="form.inspector" disabled />
        </el-form-item>
        <el-form-item label="初检时间" prop="initialTime">
          <el-date-picker clearable
            v-model="form.initialTime"
            type="date"
            value-format="yyyy-MM-dd"
            disabled >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="整改员" prop="rectification">
          <el-input v-model="form.rectification" disabled/>
        </el-form-item>
        <el-form-item label="整改时间" prop="rectificationDate">
          <el-date-picker clearable
            v-model="form.rectificationDate"
            type="date"
            value-format="yyyy-MM-dd"
            disabled>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="复检员" prop="reviewer">
          <el-input v-model="form.reviewer" disabled />
        </el-form-item>
        <el-form-item label="复检时间" prop="reviewerDate">
          <el-date-picker clearable
            v-model="form.reviewerDate"
            type="date"
            value-format="yyyy-MM-dd"
            disabled>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="隐患图片" prop="dangerPic">
          <image-preview :src="form.dangerPic" :width="50" :height="50"/>
        </el-form-item>
        <el-form-item label="整改图" prop="rectificationPic">
          <image-preview :src="form.rectificationPic" :width="50" :height="50"/>
        </el-form-item>
        <el-form-item label="复检图片" prop="detectPic">
          <image-preview :src="form.detectPic" :width="50" :height="50"/>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 隐患数据B1详情对话框 -->
    <el-dialog title="B1（带电设备红外检测-非变压器）" :visible.sync="openb1" width="800px" append-to-body>
      <el-form ref="formb" :model="formb" label-width="120px">
        <el-form-item label="辐射率" prop="radiation">
          <el-input v-model="formb.radiation" disabled />
        </el-form-item>
        <el-form-item label="天气" prop="weather" v-if="false" >
          <el-input v-model="formb.weather" disabled/>
        </el-form-item>
        <el-form-item label="测试距离（m)" prop="distance">
          <el-input v-model="formb.distance" disabled/>
        </el-form-item>
        <el-form-item label="风速（m/s）" prop="windSpeed" v-if="false">
          <el-input v-model="formb.windSpeed" disabled/>
        </el-form-item>
        <el-form-item label="检测时间" prop="detectionTime">
          <el-input v-model="formb.detectionTime" disabled/>
        </el-form-item>
        <el-form-item label="检测位置" prop="location" >
          <el-input v-model="formb.location" disabled/>
        </el-form-item>
        <el-form-item label="被测设备名称" prop="deviceName">
          <el-input v-model="formb.deviceName" disabled/>
        </el-form-item>
        <el-form-item label="设备编号" prop="deviceCode">
          <el-input v-model="formb.deviceCode" disabled/>
        </el-form-item>
        <el-form-item label="图片编号" prop="imageCode">
          <el-input v-model="formb.imageCode" disabled/>
        </el-form-item>
        <el-form-item label="额定电压（V）" prop="ratedVoltage">
          <el-input v-model="formb.ratedVoltage" disabled/>
        </el-form-item>
        <el-form-item label="额定电流（A)" prop="ratedCurrent">
          <el-input v-model="formb.ratedCurrent" disabled/>
        </el-form-item>
        <el-form-item label="判定结果" prop="result">
          <el-input v-model="formb.result" disabled/>
        </el-form-item>
        <el-form-item label-width="0px">
        <el-card class="box-card" v-if="formb.threePhase != null">
          <div slot="header" class="clearfix">
            <span>三相检测</span>
          </div>
          <div class="text item">
            <el-card class="box-card" v-if="formb.threePhase.a != null">
              <div slot="header" class="clearfix">
                <span>A</span>
              </div>
              <div class="text item">
                <el-form-item label="电源侧温度（℃）" prop="powerTemp" label-width="140px">
                  <el-input v-model="formb.threePhase.a.powerTemp" disabled/>
                </el-form-item>
                <el-form-item label="负荷侧温度（℃）" prop="loadTemp" label-width="140px">
                  <el-input v-model="formb.threePhase.a.loadTemp" disabled/>
                </el-form-item>
                <el-form-item label="电流（A）" prop="current" label-width="140px">
                  <el-input v-model="formb.threePhase.a.current" disabled />
                </el-form-item>
              </div>
            </el-card>
            <el-card class="box-card" v-if="formb.threePhase.b != null">
              <div slot="header" class="clearfix">
                <span>B</span>
              </div>
              <div class="text item">
                <el-form-item label="电源侧温度（℃）" prop="powerTemp" label-width="140px">
                  <el-input v-model="formb.threePhase.b.powerTemp" disabled/>
                </el-form-item>
                <el-form-item label="负荷侧温度（℃）" prop="loadTemp" label-width="140px">
                  <el-input v-model="formb.threePhase.b.loadTemp" disabled/>
                </el-form-item>
                <el-form-item label="电流（A）" prop="current" label-width="140px">
                  <el-input v-model="formb.threePhase.b.current" disabled/>
                </el-form-item>
              </div>
            </el-card>
            <el-card class="box-card" v-if="formb.threePhase.n != null">
              <div slot="header" class="clearfix">
                <span>N</span>
              </div>
              <div class="text item">
                <el-form-item label="电源侧温度（℃）" prop="powerTemp" label-width="140px">
                  <el-input v-model="formb.threePhase.n.powerTemp" disabled/>
                </el-form-item>
                <el-form-item label="负荷侧温度（℃）" prop="loadTemp" label-width="140px">
                  <el-input v-model="formb.threePhase.n.loadTemp" disabled/>
                </el-form-item>
                <el-form-item label="电流（A）" prop="current" label-width="140px">
                  <el-input v-model="formb.threePhase.n.current" disabled/>
                </el-form-item>
              </div>
            </el-card>
            <el-card class="box-card" v-if="formb.threePhase.c != null">
              <div slot="header" class="clearfix">
                <span>C</span>
              </div>
              <div class="text item">
                <el-form-item label="电源侧温度（℃）" prop="powerTemp" label-width="140px">
                  <el-input v-model="formb.threePhase.c.powerTemp" disabled/>
                </el-form-item>
                <el-form-item label="负荷侧温度（℃）" prop="loadTemp" label-width="140px">
                  <el-input v-model="formb.threePhase.c.loadTemp" disabled/>
                </el-form-item>
                <el-form-item label="电流（A）" prop="current" label-width="140px">
                  <el-input v-model="formb.threePhase.c.current" disabled/>
                </el-form-item>
              </div>
            </el-card>
            <el-card class="box-card" v-if="formb.threePhase.voltage != null">
              <div slot="header" class="clearfix">
                <span>电压（V）</span>
              </div>
              <div class="text item">
                <el-form-item label="A-B" prop="powerTemp" label-width="140px">
                  <el-input v-model="formb.threePhase.voltage.ab" disabled/>
                </el-form-item>
                <el-form-item label="A-C" prop="loadTemp" label-width="140px">
                  <el-input v-model="formb.threePhase.voltage.ac" disabled/>
                </el-form-item>
                <el-form-item label="B-C" prop="current" label-width="140px">
                  <el-input v-model="formb.threePhase.voltage.bc" disabled/>
                </el-form-item>
              </div>
            </el-card>
          </div>
        </el-card>
        </el-form-item>
        <el-form-item label-width="0px">
        <el-card class="box-card" v-if="formb.singlePhase != null">
          <div slot="header" class="clearfix">
            <span>单相检测</span>
          </div>
          <div class="text item">
            <el-card class="box-card" v-if="formb.singlePhase.l != null">
              <div slot="header" class="clearfix">
                <span>L</span>
              </div>
              <div class="text item">
                <el-form-item label="电源侧温度（℃）" prop="powerTemp" label-width="140px">
                  <el-input v-model="formb.singlePhase.l.powerTemp" disabled/>
                </el-form-item>
                <el-form-item label="负荷侧温度（℃）" prop="loadTemp" label-width="140px">
                  <el-input v-model="formb.singlePhase.l.loadTemp" disabled/>
                </el-form-item>
                <el-form-item label="电流（A）" prop="current" label-width="140px">
                  <el-input v-model="formb.singlePhase.l.current" disabled />
                </el-form-item>
              </div>
            </el-card>
            <el-card class="box-card" v-if="formb.singlePhase.n != null">
              <div slot="header" class="clearfix">
                <span>N</span>
              </div>
              <div class="text item">
                <el-form-item label="电源侧温度（℃）" prop="powerTemp" label-width="140px">
                  <el-input v-model="formb.singlePhase.n.powerTemp" disabled/>
                </el-form-item>
                <el-form-item label="负荷侧温度（℃）" prop="loadTemp" label-width="140px">
                  <el-input v-model="formb.singlePhase.n.loadTemp" disabled/>
                </el-form-item>
                <el-form-item label="电流（A）" prop="current" label-width="140px">
                  <el-input v-model="formb.singlePhase.n.current" disabled/>
                </el-form-item>
              </div>
            </el-card>
          </div>
        </el-card>
        </el-form-item>
        <el-form-item label="整体外观图" prop="overallPic">
          <image-preview :src="formb.overallPic" :width="50" :height="50"/>
        </el-form-item>
        <el-form-item label="现场检测图" prop="inspectionPic">
          <image-preview :src="formb.inspectionPic" :width="50" :height="50"/>
        </el-form-item>
        <el-form-item label="红外判定图" prop="infraredPic">
          <image-preview :src="formb.infraredPic" :width="50" :height="50"/>
        </el-form-item>
      </el-form>
    </el-dialog>

     <!-- 隐患数据BB1详情对话框 -->
     <el-dialog title="B1（带电设备红外检测-变压器）" :visible.sync="openbb1" width="500px" append-to-body>
      <el-form ref="formb" :model="formb" label-width="120px">
        <el-form-item label="辐射率" prop="radiation">
          <el-input v-model="formb.radiation" disabled />
        </el-form-item>
        <el-form-item label="测试距离（m)" prop="distance">
          <el-input v-model="formb.distance" disabled/>
        </el-form-item>
        <el-form-item label="检测时间" prop="detectionTime">
          <el-input v-model="formb.detectionTime" disabled/>
        </el-form-item>
        <el-form-item label="检测位置" prop="location" >
          <el-input v-model="formb.location" disabled/>
        </el-form-item>
        <el-form-item label="被测设备名称" prop="deviceName">
          <el-input v-model="formb.deviceName" disabled/>
        </el-form-item>
        <el-form-item label="设备编号" prop="deviceCode">
          <el-input v-model="formb.deviceCode" disabled/>
        </el-form-item>
        <el-form-item label="图片编号" prop="imageCode">
          <el-input v-model="formb.imageCode" disabled/>
        </el-form-item>
        <el-form-item label="最高温度" prop="maxTemp">
          <el-input v-model="formb.maxTemp" disabled/>
        </el-form-item>
        <el-form-item label="额定电压（V）" prop="ratedVoltage">
          <el-input v-model="formb.ratedVoltage" disabled/>
        </el-form-item>
        <el-form-item label="额定电流（A)" prop="ratedCurrent">
          <el-input v-model="formb.ratedCurrent" disabled/>
        </el-form-item>
        <el-form-item label="判定结果" prop="result">
          <el-input v-model="formb.result" disabled/>
        </el-form-item>
        <el-form-item label="整体外观图" prop="overallPic">
          <image-preview :src="formb.overallPic" :width="50" :height="50"/>
        </el-form-item>
        <el-form-item label="现场检测图" prop="inspectionPic">
          <image-preview :src="formb.inspectionPic" :width="50" :height="50"/>
        </el-form-item>
        <el-form-item label="红外判定图" prop="infraredPic">
          <image-preview :src="formb.infraredPic" :width="50" :height="50"/>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 隐患数据B2详情对话框 -->
    <el-dialog title="B2（接地电阻值检测）" :visible.sync="openb2" width="500px" append-to-body>
      <el-form ref="formb" :model="formb" label-width="100px">
        <el-form-item label="被测设备类型" prop="deviceType">
          <el-input v-model="formb.deviceType" disabled />
        </el-form-item>
        <el-form-item label="接地系统" prop="groundingSystem" >
          <el-input v-model="formb.groundingSystem" disabled/>
        </el-form-item>
        <el-form-item label="测试地点" prop="location" >
          <el-input v-model="formb.location" disabled/>
        </el-form-item>
        <el-form-item label="被测设备名称" prop="deviceName">
          <el-input v-model="formb.deviceName" disabled/>
        </el-form-item>
        <el-form-item label="接地电阻" prop="resistance">
          <el-input v-model="formb.resistance" disabled/>
        </el-form-item>
        <el-form-item label="判定结果" prop="result">
          <el-input v-model="formb.result" disabled/>
        </el-form-item>
        <el-form-item label="整体外观图" prop="overallPic">
          <image-preview :src="formb.overallPic" :width="50" :height="50"/>
        </el-form-item>
        <el-form-item label="现场检测图" prop="inspectionPic">
          <image-preview :src="formb.inspectionPic" :width="50" :height="50"/>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 隐患数据B3详情对话框 -->
    <el-dialog title="B3（变配电装置距可燃物距离检测）" :visible.sync="openb3" width="500px" append-to-body>
      <el-form ref="formb" :model="formb" label-width="100px">
        <el-form-item label="被测设备名称" prop="deviceName">
          <el-input v-model="formb.deviceName" disabled/>
        </el-form-item>
        <el-form-item label="检测位置" prop="location" >
          <el-input v-model="formb.location" disabled/>
        </el-form-item>
        <el-form-item label="距可燃物距离" prop="combustibles">
          <el-input v-model="formb.combustibles" disabled/>
        </el-form-item>
        <el-form-item label="判定结果" prop="result">
          <el-input v-model="formb.result" disabled/>
        </el-form-item>
        <el-form-item label="整体外观图" prop="overallPic">
          <image-preview :src="formb.overallPic" :width="50" :height="50"/>
        </el-form-item>
        <el-form-item label="现场检测图" prop="inspectionPic">
          <image-preview :src="formb.inspectionPic" :width="50" :height="50"/>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 隐患数据B4详情对话框 -->
    <el-dialog title="B4（照明灯具距可燃物距离检测）" :visible.sync="openb4" width="500px" append-to-body>
      <el-form ref="formb" :model="formb" label-width="100px">
        <el-form-item label="灯具类型" prop="lampType">
          <el-input v-model="formb.lampType" disabled/>
        </el-form-item>
        <el-form-item label="被测设备名称" prop="deviceName">
          <el-input v-model="formb.deviceName" disabled/>
        </el-form-item>
        <el-form-item label="检测位置" prop="location" >
          <el-input v-model="formb.location" disabled/>
        </el-form-item>
        <el-form-item label="距可燃物距离" prop="combustibles">
          <el-input v-model="formb.combustibles" disabled/>
        </el-form-item>
        <el-form-item label="判定结果" prop="result">
          <el-input v-model="formb.result" disabled/>
        </el-form-item>
        <el-form-item label="整体外观图" prop="overallPic">
          <image-preview :src="formb.overallPic" :width="50" :height="50"/>
        </el-form-item>
        <el-form-item label="现场检测图" prop="inspectionPic">
          <image-preview :src="formb.inspectionPic" :width="50" :height="50"/>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 隐患数据B5详情对话框 -->
    <el-dialog title="B5（开关、插座安装高度检测）" :visible.sync="openb5" width="500px" append-to-body>
      <el-form ref="formb" :model="formb" label-width="100px">
        <el-form-item label="场所类型" prop="venueType">
          <el-input v-model="formb.venueType" disabled/>
        </el-form-item>
        <el-form-item label="被测设备名称" prop="deviceName">
          <el-input v-model="formb.deviceName" disabled/>
        </el-form-item>
        <el-form-item label="检测位置" prop="location" >
          <el-input v-model="formb.location" disabled/>
        </el-form-item>
        <el-form-item label="安装高度" prop="height">
          <el-input v-model="formb.height" disabled/>
        </el-form-item>
        <el-form-item label="判定结果" prop="result">
          <el-input v-model="formb.result" disabled/>
        </el-form-item>
        <el-form-item label="整体外观图" prop="overallPic">
          <image-preview :src="formb.overallPic" :width="50" :height="50"/>
        </el-form-item>
        <el-form-item label="现场检测图" prop="inspectionPic">
          <image-preview :src="formb.inspectionPic" :width="50" :height="50"/>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 隐患数据B6详情对话框 -->
    <el-dialog title="B6（室内漏电保护开关检测）" :visible.sync="openb6" width="500px" append-to-body>
      <el-form ref="formb" :model="formb" label-width="150px">
        <el-form-item label="被测设备型号" prop="deviceModel">
          <el-input v-model="formb.deviceModel" disabled/>
        </el-form-item>
        <el-form-item label="额定动作电流（mA）" prop="ratedActionCurrent" >
          <el-input v-model="formb.ratedActionCurrent" disabled/>
        </el-form-item>
        <el-form-item label="动作" prop="action">
          <el-input v-model="formb.action" disabled/>
        </el-form-item>
        <el-form-item label="表盘读数 0°" prop="dialValue0">
          <el-input v-model="formb.dialValue0" disabled/>
        </el-form-item>
        <el-form-item label="表盘读数 180°" prop="dialValue180">
          <el-input v-model="formb.dialValue180" disabled/>
        </el-form-item>
        <el-form-item label="手动测试" prop="manualTest">
          <el-input v-model="formb.manualTest" disabled/>
        </el-form-item>
        <el-form-item label="其他情况" prop="other">
          <el-input v-model="formb.other" disabled/>
        </el-form-item>
        <el-form-item label="判定结果" prop="result">
          <el-input v-model="formb.result" disabled/>
        </el-form-item>
        <el-form-item label="整体外观图" prop="overallPic">
          <image-preview :src="formb.overallPic" :width="50" :height="50"/>
        </el-form-item>
        <el-form-item label="现场检测图" prop="inspectionPic">
          <image-preview :src="formb.inspectionPic" :width="50" :height="50"/>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 隐患数据B7详情对话框 -->
    <el-dialog title="B7（楼栋总漏保检测）" :visible.sync="openb7" width="500px" append-to-body>
      <el-form ref="formb" :model="formb" label-width="150px">
        <el-form-item label="被测设备型号" prop="deviceModel">
          <el-input v-model="formb.deviceModel" disabled/>
        </el-form-item>
        <el-form-item label="检测位置" prop="location" >
          <el-input v-model="formb.location" disabled/>
        </el-form-item>
        <el-form-item label="额定电流（A)" prop="ratedCurrent">
          <el-input v-model="formb.ratedCurrent" disabled/>
        </el-form-item>
        <el-form-item label="额定动作电流（mA）" prop="ratedActionCurrent">
          <el-input v-model="formb.ratedActionCurrent" disabled/>
        </el-form-item>
        <el-form-item label="检测结果" prop="testResults">
          <el-input v-model="formb.testResults" disabled/>
        </el-form-item>
        <el-form-item label="判定结果" prop="result">
          <el-input v-model="formb.result" disabled/>
        </el-form-item>
        <el-form-item label="整体外观图" prop="overallPic">
          <image-preview :src="formb.overallPic" :width="50" :height="50"/>
        </el-form-item>
        <el-form-item label="现场检测图" prop="inspectionPic">
          <image-preview :src="formb.inspectionPic" :width="50" :height="50"/>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 隐患数据B8详情对话框 -->
    <el-dialog title="B8（楼层总漏保检测）" :visible.sync="openb8" width="500px" append-to-body>
      <el-form ref="formb" :model="formb" label-width="150px">
        <el-form-item label="被测设备型号" prop="deviceModel">
          <el-input v-model="formb.deviceModel" disabled/>
        </el-form-item>
        <el-form-item label="检测位置" prop="location" >
          <el-input v-model="formb.location" disabled/>
        </el-form-item>
        <el-form-item label="额定电流（A)" prop="ratedCurrent">
          <el-input v-model="formb.ratedCurrent" disabled/>
        </el-form-item>
        <el-form-item label="额定动作电流（mA）" prop="ratedActionCurrent">
          <el-input v-model="formb.ratedActionCurrent" disabled/>
        </el-form-item>
        <el-form-item label="检测结果" prop="testResults">
          <el-input v-model="formb.testResults" disabled/>
        </el-form-item>
        <el-form-item label="判定结果" prop="result">
          <el-input v-model="formb.result" disabled/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="formb.remark" disabled/>
        </el-form-item>
        <el-form-item label="整体外观图" prop="overallPic">
          <image-preview :src="formb.overallPic" :width="50" :height="50"/>
        </el-form-item>
        <el-form-item label="现场检测图" prop="inspectionPic">
          <image-preview :src="formb.inspectionPic" :width="50" :height="50"/>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 隐患数据B9详情对话框 -->
    <el-dialog title="B9（电气设备对地电压）" :visible.sync="openb9" width="500px" append-to-body>
      <el-form ref="formb" :model="formb" label-width="150px">
        <el-form-item label="检测位置" prop="location" >
          <el-input v-model="formb.location" disabled/>
        </el-form-item>
        <el-form-item label="检测结果" prop="testResults">
          <el-input v-model="formb.testResults" disabled/>
        </el-form-item>
        <el-form-item label="判定结果" prop="result">
          <el-input v-model="formb.result" disabled/>
        </el-form-item>
        <el-form-item label="电气设备对地电压" prop="voltage">
          <el-input v-model="formb.voltage" disabled/>
        </el-form-item>
        <el-form-item label="整体外观图" prop="overallPic">
          <image-preview :src="formb.overallPic" :width="50" :height="50"/>
        </el-form-item>
        <el-form-item label="现场检测图" prop="inspectionPic">
          <image-preview :src="formb.inspectionPic" :width="50" :height="50"/>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 隐患数据B10详情对话框 -->
    <el-dialog title="B10（计量电能表规格）" :visible.sync="openb10" width="500px" append-to-body>
      <el-form ref="formb" :model="formb" label-width="100px">
        <el-form-item label="检测位置" prop="location" >
          <el-input v-model="formb.location" disabled/>
        </el-form-item>
        <el-form-item label="检测结果" prop="testResults">
          <el-input v-model="formb.testResults" disabled/>
        </el-form-item>
        <el-form-item label="判定结果" prop="result">
          <el-input v-model="formb.result" disabled/>
        </el-form-item>
        <el-form-item label="整体外观图" prop="overallPic">
          <image-preview :src="formb.overallPic" :width="50" :height="50"/>
        </el-form-item>
        <el-form-item label="现场检测图" prop="inspectionPic">
          <image-preview :src="formb.inspectionPic" :width="50" :height="50"/>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 隐患数据B11详情对话框 -->
    <el-dialog title="B11（室内插座接线）" :visible.sync="openb11" width="500px" append-to-body>
      <el-form ref="formb" :model="formb" label-width="150px">
        <el-form-item label="检测位置" prop="location" >
          <el-input v-model="formb.location" disabled/>
        </el-form-item>
        <el-form-item label="插座接地线检测结果" prop="testResults">
          <el-input v-model="formb.testResults" disabled/>
        </el-form-item>
        <el-form-item label="其他隐患检测" prop="other">
          <el-input v-model="formb.other" disabled/>
        </el-form-item>
        <el-form-item label="判定结果" prop="result">
          <el-input v-model="formb.result" disabled/>
        </el-form-item>
        <el-form-item label="整体外观图" prop="overallPic">
          <image-preview :src="formb.overallPic" :width="50" :height="50"/>
        </el-form-item>
        <el-form-item label="现场检测图" prop="inspectionPic">
          <image-preview :src="formb.inspectionPic" :width="50" :height="50"/>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 隐患数据B12详情对话框 -->
    <el-dialog title="B12（浴室热水器漏电保护开关）" :visible.sync="openb12" width="500px" append-to-body>
      <el-form ref="formb" :model="formb" label-width="100px">
        <el-form-item label="检测位置" prop="location" >
          <el-input v-model="formb.location" disabled/>
        </el-form-item>
        <el-form-item label="其他隐患检测" prop="other">
          <el-input v-model="formb.other" disabled/>
        </el-form-item>
        <el-form-item label="判定结果" prop="result">
          <el-input v-model="formb.result" disabled/>
        </el-form-item>
        <el-form-item label="整体外观图" prop="overallPic">
          <image-preview :src="formb.overallPic" :width="50" :height="50"/>
        </el-form-item>
        <el-form-item label="现场检测图" prop="inspectionPic">
          <image-preview :src="formb.inspectionPic" :width="50" :height="50"/>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 隐患数据B13详情对话框 -->
    <el-dialog title="B13（绝缘电阻检测）" :visible.sync="openb13" width="500px" append-to-body>
      <el-form ref="formb" :model="formb" label-width="120px">
        <el-form-item label="检测地点" prop="location" >
          <el-input v-model="formb.location" disabled/>
        </el-form-item>
        <el-form-item label="设备名称" prop="deviceName">
          <el-input v-model="formb.deviceName" disabled/>
        </el-form-item>
        <el-form-item label="设备编号" prop="deviceCode">
          <el-input v-model="formb.deviceCode" disabled/>
        </el-form-item>
        <el-form-item label="规格型号" prop="ospecificationsther">
          <el-input v-model="formb.specifications" disabled/>
        </el-form-item>
        <el-form-item label="绝缘等级" prop="insulation">
          <el-input v-model="formb.insulation" disabled/>
        </el-form-item>
        <el-form-item label="额定电压（V）" prop="ratedVoltage">
          <el-input v-model="formb.ratedVoltage" disabled/>
        </el-form-item>
        <el-form-item label="总判定结果" prop="result">
          <el-input v-model="formb.result" disabled/>
        </el-form-item>
        <el-form-item label-width="0px">
          <el-card class="box-card" v-if="formb.ab != null">
            <div slot="header" class="clearfix">
              <span>A-B</span>
            </div>
            <div class="text item">
                <el-form-item label="绝缘电阻值（MΩ）" prop="resistance" label-width="140px">
                  <el-input v-model="formb.ab.resistance" disabled/>
                </el-form-item>
                <el-form-item label="检查结果" prop="result" label-width="140px">
                  <el-input v-model="formb.ab.result" disabled/>
                </el-form-item>
            </div>
          </el-card>
        </el-form-item>
        <el-form-item label-width="0px">
          <el-card class="box-card" v-if="formb.ac != null">
            <div slot="header" class="clearfix">
              <span>A-C</span>
            </div>
            <div class="text item">
                <el-form-item label="绝缘电阻值（MΩ）" prop="resistance" label-width="140px">
                  <el-input v-model="formb.ac.resistance" disabled/>
                </el-form-item>
                <el-form-item label="检查结果" prop="result" label-width="140px">
                  <el-input v-model="formb.ac.result" disabled/>
                </el-form-item>
            </div>
          </el-card>
        </el-form-item>
        <el-form-item label-width="0px">
          <el-card class="box-card" v-if="formb.bc != null">
            <div slot="header" class="clearfix">
              <span>B-C</span>
            </div>
            <div class="text item">
                <el-form-item label="绝缘电阻值（MΩ）" prop="resistance" label-width="140px">
                  <el-input v-model="formb.bc.resistance" disabled/>
                </el-form-item>
                <el-form-item label="检查结果" prop="result" label-width="140px">
                  <el-input v-model="formb.bc.result" disabled/>
                </el-form-item>
            </div>
          </el-card>
        </el-form-item>
        <el-form-item label="整体外观图" prop="overallPic">
          <image-preview :src="formb.overallPic" :width="50" :height="50"/>
        </el-form-item>
        <el-form-item label="现场检测图" prop="inspectionPic">
          <image-preview :src="formb.inspectionPic" :width="50" :height="50"/>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 隐患数据B14详情对话框 -->
    <el-dialog title="B14（电气火灾监控探测器）" :visible.sync="openb14" width="500px" append-to-body>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>剩余电流值检测</span>
        </div>
        <div class="text item">
          <el-form ref="residualCurrent" :model="residualCurrent" label-width="120px">
            <el-form-item label="检测地点" prop="address" >
              <el-input v-model="residualCurrent.address" disabled/>
            </el-form-item>
            <el-form-item label="检测位置" prop="location" >
              <el-input v-model="residualCurrent.location" disabled/>
            </el-form-item>
            <el-form-item label="检测设备" prop="device" >
              <el-input v-model="residualCurrent.device" disabled/>
            </el-form-item>
            <el-form-item label="剩余电流有效值" prop="residualCurrent">
              <el-input v-model="residualCurrent.residualCurrent" disabled/>
            </el-form-item>
            <el-form-item label="检查结果" prop="testResults">
              <el-input v-model="residualCurrent.testResults" disabled/>
            </el-form-item>
            <el-form-item label="判定结果" prop="result">
              <el-input v-model="residualCurrent.result" disabled/>
            </el-form-item>
          </el-form>
        </div>
      </el-card>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>报警时间检测</span>
        </div>
        <div class="text item">
          <el-form ref="alarmTime" :model="alarmTime" label-width="150px">
            <el-form-item label="检测地点" prop="address" >
              <el-input v-model="alarmTime.address" disabled/>
            </el-form-item>
            <el-form-item label="设备型号" prop="deviceModel">
              <el-input v-model="alarmTime.deviceModel" disabled/>
            </el-form-item>
            <el-form-item label="额定报警时间（ms）" prop="expected">
              <el-input v-model="alarmTime.expected" disabled/>
            </el-form-item>
            <el-form-item label="实际报警时间（ms）" prop="actual">
              <el-input v-model="alarmTime.actual" disabled/>
            </el-form-item>
            <el-form-item label="判定结果" prop="result">
              <el-input v-model="alarmTime.result" disabled/>
            </el-form-item>
          </el-form>
        </div>
      </el-card>
      
    </el-dialog>

    <!-- 隐患数据B15详情对话框 -->
    <el-dialog title="B15（开关、插座温度检测）" :visible.sync="openb15" width="500px" append-to-body>
      <el-form ref="formb" :model="formb" label-width="100px">
        <el-form-item label="被测设备名称" prop="deviceName" >
          <el-input v-model="formb.deviceName" disabled/>
        </el-form-item>
        <el-form-item label="检测位置" prop="location">
          <el-input v-model="formb.location" disabled/>
        </el-form-item>
        <el-form-item label="辐射率" prop="radiation">
          <el-input v-model="formb.radiation" disabled/>
        </el-form-item>
        <el-form-item label="检测温度" prop="temperature">
          <el-input v-model="formb.temperature" disabled/>
        </el-form-item>
        <el-form-item label="判定结果" prop="result">
          <el-input v-model="formb.result" disabled/>
        </el-form-item>
        <el-form-item label="整体外观图" prop="overallPic">
          <image-preview :src="formb.overallPic" :width="50" :height="50"/>
        </el-form-item>
        <el-form-item label="现场检测图" prop="inspectionPic">
          <image-preview :src="formb.inspectionPic" :width="50" :height="50"/>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>
<style>
.el-input.is-disabled .el-input__inner {
  color: #606266;
}
.el-textarea.is-disabled .el-textarea__inner{
  color: #606266;
}
</style>
<script>
import { listDanger, getDanger, resetStatus} from "@/api/danger/danger";
import { getProject } from "@/api/project/project";
import { detectUnitDict } from "@/api/projectrole/DetectUnit";

export default {
  name: "OwnerUnit",
  dicts: ['high_risk_type', 'again_test_status', 'hazard_level', 'hazard_level_high', 'hazard_level_charging_station', 'danger_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 报告表格数据
      dangerList: [],
      detectUnitDict: [],
      districtOptions: [],
      streetOptions: [],
      communityOptions: [],
      hamletOptions: [],
      levelOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openb1: false,
      openbb1: false,
      openb2: false,
      openb3: false,
      openb4: false,
      openb5: false,
      openb6: false,
      openb7: false,
      openb8: false,
      openb9: false,
      openb10: false,
      openb11: false,
      openb12: false,
      openb13: false,
      openb14: false,
      openb15: false,

      projectInfo: {

      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        location: null,
        highRiskType: null,
        projectId: this.$store.state.settings.projectId,
        status: null,
        unitId: null,
        buildingId: null
      },
      // 表单参数
      form: {},
      formb: {},
      residualCurrent: {},
      alarmTime: {},
      // 表单校验
      rules: {
      },
      projectType: null
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {

      this.loading = true;

      if(this.$route.params.unitId && this.$route.params.unitId != null && this.$route.params.unitId != ''){
        this.queryParams.unitId = parseInt(this.$route.params.unitId)
      }
      if(this.$route.params.buildingId && this.$route.params.buildingId != null && this.$route.params.buildingId != ''){
        this.queryParams.buildingId = parseInt(this.$route.params.buildingId)
      }

      detectUnitDict().then(response => {
        this.detectUnitDict = response.data;
      });

      getProject(this.queryParams.projectId).then(response => {
        this.projectInfo = response.data;
        this.projectType = response.data.type;

        if(this.projectType === '3') {
          this.levelOptions = this.dict.type.hazard_level_high;
        } else if(this.projectType === '4'){
          this.levelOptions = this.dict.type.hazard_level_charging_station;
        } else {
          this.levelOptions = this.dict.type.hazard_level;
        }

      });

      listDanger(this.queryParams).then(response => {
        this.dangerList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        unitId: null,
        unitName: null,
        buildingId: null,
        buildingName: null,
        unitAreaId: null,
        areaName: null,
        chargingPileId: null,
        rounds: null,
        dangerId: null,
        formId: null,
        formCode: null,
        formType: null,
        formDataId: null,
        level: null,
        description: null,
        suggestions: null,
        location: null,
        dangerPic: null,
        status: null,
        inspector: null,
        inspectorId: null,
        initialTime: null,
        formb: null,
        rectification: null,
        rectificationDate: null,
        reviewer: null,
        reviewerDate: null,
        rectificationPic: null,
        reason: null,
        detectPic: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    detectFormat(row){
      return this.selectDictVoLabel(this.detectUnitDict, row.detectId);
    },
    selectDictVoLabel(datas, value) {
      if (value === undefined) {
        return "";
      }
      var actions = [];
      Object.keys(datas).some((key) => {
        if (datas[key].id == value) {
          actions.push(datas[key].name);
          return true;
        }
      })
      if (actions.length === 0) {
        actions.push(value);
      }
      return actions.join('');
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    dangerLevelFormat(row){
      if(row.level == null || row.level == ''){
        return "/";
      }
      if(this.projectType === '3') {
        return this.selectDictLabel(this.dict.type.hazard_level_high, row.level);
      } else if(this.projectType === '4'){
        return this.selectDictLabel(this.dict.type.hazard_level_charging_station, row.level);
      } else {
        return this.selectDictLabel(this.dict.type.hazard_level, row.level);
      }
    },
    handleStatus(row, status){
      let text = status === "0" ? "待整改" : "待复检";
      const ids = row.id || this.ids;
      this.$modal.confirm('确认要重置编号为' + ids + '的隐患的状态为' + text + '吗？').then(function() {
        return resetStatus(ids, status);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("重置成功");
      }).catch(function() {
      });
    },
    dangerStatusFormat(row){
      if(row.status === '9'){
        return "非隐患";
      } else{
        return this.selectDictLabel(this.dict.type.danger_status, row.status);
      }
    },
    handleView(row){
      this.reset();
      const id = row.id || this.ids
      getDanger(id).then(response => {
        this.form = response.data;

        this.formb = {};
        this.residualCurrent = {};
        this.alarmTime = {};
        if(this.form.formb != null && this.form.formb.data != null){
          this.formb  = this.form.formb.data;
        }

        if(this.form.formType !== 'B'){
          this.open = true;
        } else if(this.form.formType === 'B'){
          if(this.form.formCode === 'B1'){
            this.openb1 = true;
          } else if(this.form.formCode === 'BB1'){
            this.openbb1 = true;
          } else if(this.form.formCode === 'B2'){
            this.openb2 = true;
          } else if(this.form.formCode === 'B3'){
            this.openb3 = true;
          } else if(this.form.formCode === 'B4'){
            this.openb4 = true;
          } else if(this.form.formCode === 'B5'){
            this.openb5 = true;
          } else if(this.form.formCode === 'B6'){
            this.openb6 = true;
          } else if(this.form.formCode === 'B7'){
            this.openb7 = true;
          } else if(this.form.formCode === 'B8'){
            this.openb8 = true;
          } else if(this.form.formCode === 'B9'){
            this.openb9 = true;
          } else if(this.form.formCode === 'B10'){
            this.openb10 = true;
          } else if(this.form.formCode === 'B11'){
            this.openb11 = true;
          } else if(this.form.formCode === 'B12'){
            this.openb12 = true;
          } else if(this.form.formCode === 'B13'){
            this.openb13 = true;
          } else if(this.form.formCode === 'B14'){
            this.openb14 = true;

            if(this.formb != null  ){
              if(this.formb.residualCurrent != null){
                this.residualCurrent = this.formb.residualCurrent;
              }
              if(this.formb.alarmTime != null){
                this.alarmTime = this.formb.alarmTime;
              }
            }

          } else if(this.form.formCode === 'B15'){
            this.openb15 = true;
          }
        }
        //this.openb1 = true;
      });
    }
  }
};
</script>
