<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="关键字" prop="location">
        <el-input
          v-model="queryParams.location"
          placeholder="请输入关键字"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="楼栋" prop="buildingId" v-if="projectType === '2'">
        <el-select v-model="queryParams.buildingId" placeholder="请选择楼栋" filterable clearable>
          <el-option
            v-for="dict in buildingOptions"
            :key="dict.id"
            :label="dict.name"
            :value="dict.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="隐患等级" prop="level" >
        <el-select v-model="queryParams.level" placeholder="请选择隐患状态" filterable clearable>
          <el-option
            v-for="dict in levelOptions"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="隐患状态" prop="status" >
        <el-select v-model="queryParams.status" placeholder="请选择隐患状态" filterable clearable>
          <el-option
            v-for="dict in dict.type.again_test_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="检测日期" prop="initialTime">
        <el-date-picker clearable
          v-model="queryParams.initialTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择初检时间">
        </el-date-picker>
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
      <el-table-column label="楼栋" v-if="projectType === '2'" align="center" prop="buildingName" min-width="120" :show-overflow-tooltip="true"/>
      <el-table-column label="公共区域/户" v-if="projectType === '1' || projectType === '2'" align="center" prop="areaName" min-width="120" :show-overflow-tooltip="true"/>
      <el-table-column label="充电桩" v-if="projectType === '4'" align="center" prop="stationName" min-width="120" :show-overflow-tooltip="true"/>
      <el-table-column label="隐患位置" align="center" prop="location" min-width="200" :show-overflow-tooltip="true" />
      <el-table-column label="隐患描述" align="center" prop="description" min-width="200" :show-overflow-tooltip="true" />
      <el-table-column label="整改建议" align="center" prop="suggestions" min-width="200" :show-overflow-tooltip="true"  />
      <el-table-column label="隐患等级" align="center" prop="level" width="80" :formatter="dangerLevelFormat"  />
      <el-table-column label="状态" align="center" prop="status" width="80" :formatter="dangerStatusFormat" />
      <el-table-column label="检测员" align="center" width="120" prop="inspector" />
      <el-table-column label="检测单位" align="center" width="120" prop="detectName" min-width="300" :formatter="detectFormat" :show-overflow-tooltip="true"/>
      <el-table-column label="检测日期" align="center" prop="initialTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.initialTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="初检照片" align="center" prop="dangerPic" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.dangerPic" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="整改照片" align="center" prop="rectificationPic" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.rectificationPic" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="复检照片" align="center" prop="detectPic" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.detectPic" :width="50" :height="50"/>
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
  </div>
</template>

<script>
import { listDanger, getBuildingDict} from "@/api/danger/danger";
import { getProject } from "@/api/project/project";
import { detectUnitDict } from "@/api/projectrole/DetectUnit";

export default {
  name: "OwnerUnit",
  dicts: ['high_risk_type', 'again_test_status', 'hazard_level', 'hazard_level_high', 'hazard_level_charging_station'],
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
      buildingOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      projectInfo: {

      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        unitId: parseInt(this.$route.params.unitId),
        location: null,
        buildingId: null,
        highRiskType: null,
        projectId: this.$store.state.settings.projectId,
        status: null,
        level: null,
        initialTime: null
      },
      // 表单参数
      form: {},
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

      detectUnitDict().then(response => {
        this.detectUnitDict = response.data;
      });

      getBuildingDict(this.$route.params.unitId).then(response => {
        this.buildingOptions = response.data;
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
      if(row.level == null || row.level == '' || row.formType === 'B'){
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
    dangerStatusFormat(row){
      if(row.formType === 'B'){
        return "/";
      } else{
        return this.selectDictLabel(this.dict.type.again_test_status, row.status);
      }

    }
  }
};
</script>
