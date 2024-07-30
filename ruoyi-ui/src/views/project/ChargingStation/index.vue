<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="区" prop="district">
        <el-select v-model="queryParams.district" placeholder="请选择区"  filterable clearable>
          <el-option
            v-for="dict in districtOptions"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="街道" prop="street">
        <el-select v-model="queryParams.street" placeholder="请选择街道"  filterable clearable>
          <el-option
            v-for="dict in streetOptions"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['project:OwnerUnit:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['project:OwnerUnit:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['project:OwnerUnit:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['project:OwnerUnit:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="OwnerUnitList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" width="60" prop="id" />
      <el-table-column label="场站名称" align="center" prop="name" min-width="200" :show-overflow-tooltip="true" />
      <el-table-column label="轮次" align="center" prop="rounds" width="50" />
      <el-table-column label="检测单位" align="center" prop="detectName" min-width="300" :formatter="detectFormat" :show-overflow-tooltip="true" />
      <el-table-column label="区域" align="center" prop="area" min-width="200" :formatter="areaFormat" :show-overflow-tooltip="true"  />
      <el-table-column label="检测地址" align="center" prop="address" min-width="200" :show-overflow-tooltip="true"  />
      <el-table-column label="最后修改时间" align="center" prop="updateTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="全景图" align="center" prop="panoramaPic" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.panoramaPic" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="点位图" align="center" prop="stationPic" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.stationPic" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" fixed="right" width="160" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-guide"
            @click="handleRounds(scope.row)"
          >轮次</el-button>
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

    <!-- 轮次对话框 -->
    <el-dialog title="轮次" :visible.sync="openRounds" width="600px" append-to-body @close="handleCloseRoundsDialog">
      <el-card class="box-card">
        <div class="clearfix">
          <span>当前轮次：{{this.rounds.current}}</span>
          <el-button style="margin-left: 50px;" type="primary" @click="handleStartRounds()">推进</el-button>
        </div>
        <div v-if="this.rounds.start" class="text item" style="padding-top:20px;">
          <el-steps :active="rounds.active" :finish-status="rounds.status" direction="vertical" >
            <el-step v-for="item in rounds.steps" :title="item"></el-step>
          </el-steps>
        </div>
      </el-card>
    </el-dialog>

    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
            <el-col :span="12">
              <el-form-item label="场站名称" label-width="100px" prop="name">
                <el-input v-model="form.name" placeholder="请输入场站名称" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="充电站类型" label-width="100px" prop="stationType">
                <el-select v-model="form.stationType" placeholder="请选择充电站类型" filterable>
                  <el-option
                    v-for="dict in dict.type.charging_station_type"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
              <el-form-item label="检测模块" label-width="100px"  prop="detectModuleArr">
                <el-select v-model="form.detectModuleArr" placeholder="请选择检测模块" multiple filterable>
                  <el-option
                    v-for="dict in dict.type.detect_module"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="区域" label-width="100px" prop="area">
                <el-select v-model="form.area" placeholder="请选择区域" filterable>
                  <el-option
                    v-for="item in projectAreaDict"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
              <el-form-item label="地址" label-width="100px" prop="address">
                <el-input v-model="form.address" placeholder="请输入检测地址" />
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
              <el-form-item label="运营单位" label-width="100px"  prop="operating">
                <el-input v-model="form.operating" placeholder="请输入运营单位" />
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
              <el-form-item label="负责人" label-width="100px" prop="contact">
                <el-input v-model="form.contact" placeholder="请输入负责人" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="联系电话" label-width="100px" prop="phone">
                <el-input v-model="form.phone" placeholder="请输入联系电话" />
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
              <el-form-item label="委托单位" label-width="100px" prop="entrust">
                <el-input v-model="form.entrust" placeholder="请输入委托单位" />
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
              <el-form-item label="物业类型" label-width="100px"  prop="propertyType">
                <el-select v-model="form.propertyType" placeholder="请选择物业类型" filterable>
                  <el-option
                    v-for="dict in dict.type.property_type"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  ></el-option>
                </el-select>
                <el-input v-model="form.propertyName" placeholder="物业类型选其他时输入"  />
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
              <el-form-item label="全景图" label-width="100px" prop="panoramaPic">
                <image-upload v-model="form.panoramaPic" :limit="limit" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="点位图" label-width="100px" prop="stationPic">
                <image-upload v-model="form.stationPic" :limit="limit" />
              </el-form-item>
            </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<style>
.el-step__main{
  height: 40px;
}
</style>
<script>
import { listOwnerUnit, getOwnerUnit, delOwnerUnit, addOwnerUnit, updateOwnerUnit, startRounds, roundsStep} from "@/api/project/OwnerUnit";
import { detectUnitDict } from "@/api/projectrole/DetectUnit";
import { getProject } from "@/api/project/project";
import { getProjectAreaDict, getProjectAreaDictByProjectId, getProjectAreaDictByProjectIdAndType } from "@/api/project/ProjectArea";
import { getDetectUnitUserDictByTypeAndProjectId, getDetectUnitUserDict } from "@/api/projectrole/detectUnitUser";
import DictMeta from '@/utils/dict/DictMeta'

export default {
  name: "OwnerUnit",
  dicts: ['detect_content', 'building_nature', 'charging_station_type', 'property_type', 'detect_module'],
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
      limit: 1,
      // 充电场站检测表格数据
      OwnerUnitList: [],
      districtOptions: [],
      streetOptions: [],
      communityOptions: [],
      hamletOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openRounds: false,
      projectInfo: {

      },
      rounds: {
        status: "success",
        unitId: null,
        start: false,
        current: 1,
        active: 0,
        steps: []
      },
      timer: null,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        type: '4',
        detectId: null,
        detectName: null,
        projectId: this.$store.state.settings.projectId,
        projectName: null,
        area: null,
        district: null,
        street: null,
        community: null,
        hamlet: null,
        entrust: null,
        manager: null,
        gridman: null,
        address: null,
        contact: null,
        phone: null,
        acreage: null,
        layers: null,
        nature: null,
        testStartDate: null,
        testEndDate: null,
        testDate: null,
        testContent: null,
        doorNumber: null,
        buildman: null,
        unitType: null,
        incharge: null,
        venueType: null,
        safetyIncharge: null,
        safetyManager: null,
        staffs: null,
        licence: null,
        safetyKeyUnit: null,
        stationType: null,
        detectModule: null,
        operating: null,
        propertyType: null,
        panoramaPic: null,
        stationPic: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "请输入名称", trigger: "blur" }
        ],
        area: [
          { required: true, message: "请选择区域", trigger: "change" }
        ],
        stationType: [
          { required: true, message: "请选择类型", trigger: "change" }
        ],
        detectModuleArr: [
          { required: true, message: "请选择检测模块", trigger: "change" }
        ],
      },
      // 检测单位字典选项
      detectUnitDict: [],
      projectAreaDict: [],
      ownerUnitUserDict: [],
      gridmanDict: []
    };
  },
  created() {
    this.getList();
  },
  beforeDestroy(){
   clearInterval(this.timer);        
   this.timer = null;
  },
  methods: {
    /** 查询充电场站检测列表 */
    getList() {
      this.loading = true;
      detectUnitDict().then(response => {
        this.detectUnitDict = response.data;
      });

      getProject(this.queryParams.projectId).then(response => {
        this.projectInfo = response.data;
        this.form.projectName = this.projectInfo.name;
      });

      getProjectAreaDictByProjectId(this.queryParams.projectId).then(response => {
        this.projectAreaDict = response.data;
      });

      getProjectAreaDictByProjectIdAndType(this.$store.state.settings.projectId, 'district').then(response => {
        this.districtList = response.data;
        const dictMeta = DictMeta.parse("districtOptions");
        this.districtOptions = dictMeta.responseConverter(response.data, dictMeta);
      });

      getProjectAreaDictByProjectIdAndType(this.$store.state.settings.projectId, 'street').then(response => {
        const dictMeta = DictMeta.parse("streetOptions");
        this.streetOptions = dictMeta.responseConverter(response.data, dictMeta);
      });

      getProjectAreaDictByProjectIdAndType(this.$store.state.settings.projectId, 'community').then(response => {
        const dictMeta = DictMeta.parse("communityOptions");
        this.communityOptions = dictMeta.responseConverter(response.data, dictMeta);
      });

      getProjectAreaDictByProjectIdAndType(this.$store.state.settings.projectId, 'hamlet').then(response => {
        const dictMeta = DictMeta.parse("hamletOptions");
        this.hamletOptions = dictMeta.responseConverter(response.data, dictMeta);
      });

      listOwnerUnit(this.queryParams).then(response => {
        this.OwnerUnitList = response.rows;
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
        name: null,
        type: '4',
        detectId: null,
        detectName: null,
        projectId: this.$store.state.settings.projectId,
        projectName: this.projectInfo.name,
        area: null,
        entrust: null,
        manager: null,
        gridman: null,
        address: null,
        contact: null,
        phone: null,
        acreage: null,
        layers: null,
        nature: null,
        testStartDate: null,
        testEndDate: null,
        testDate: null,
        testContent: null,
        doorNumber: null,
        buildman: null,
        unitType: null,
        incharge: null,
        venueType: null,
        safetyIncharge: null,
        safetyManager: null,
        staffs: null,
        licence: null,
        safetyKeyUnit: null,
        stationType: null,
        rounds: null,
        detectModule: null,
        detectModuleArr: [],
        operating: null,
        propertyType: null,
        propertyName: null,
        panoramaPic: null,
        stationPic: null,
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
    areaFormat(row) {
      var area = [];
      var districtName = this.selectDictLabel(this.districtOptions, row.district);
      var streetName = this.selectDictLabel(this.streetOptions, row.street);
      //var communityName = this.selectDictLabel(this.communityOptions, row.community);
      //var hamletName = this.selectDictLabel(this.hamletOptions, row.hamlet);

      area.push(districtName);
      area.push(streetName);
      //area.push(communityName);
      //area.push(hamletName);

      return area.join('/');
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加充电场站";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOwnerUnit(id).then(response => {
        this.form = response.data;
        this.form.detectModuleArr = this.form.detectModule.split(',')
        this.open = true;
        this.title = "修改充电场站";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.detectModule = this.form.detectModuleArr.join(",");
          if (this.form.id != null) {
            updateOwnerUnit(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.rounds = "1";
            addOwnerUnit(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除充电场站检测编号为"' + ids + '"的数据项？').then(function() {
        return delOwnerUnit(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('project/OwnerUnit/export', {
        ...this.queryParams
      }, `OwnerUnit_${new Date().getTime()}.xlsx`)
    },
    createStepInterval(unitId) {
      clearInterval(this.timer);
      this.timer = window.setInterval(() => {
        setTimeout(() => {
            this.queryRoundsStep(unitId);
        }, 1)
      }, 1000)
    },
    queryRoundsStep(unitId) {
      roundsStep(unitId).then(response => {
          this.rounds.current = response.rounds;
          this.rounds.steps = response.steps;
          if(response.status != null){
            this.rounds.status = response.status;
          }

          if(this.rounds.steps != null ) {
            this.rounds.start = this.rounds.steps.length > 0;
            this.rounds.active = this.rounds.steps.length - 1;
            if(this.rounds.steps.length === 4 || this.rounds.status === 'error'){
              this.rounds.active = this.rounds.steps.length;
              clearInterval(this.timer);
            } else {
              this.createStepInterval(unitId);
            }
          }
          
      }).then(() => {});
    },
    handleRounds(row) {
      this.rounds.current = row.rounds;
      this.rounds.unitId = row.id;
      this.rounds.steps = [];
      this.rounds.active = 0;

      this.queryRoundsStep(row.id);
      this.openRounds = true;
    },
    handleStartRounds() {

      this.$modal.confirm('确认开始推进新的轮次？').then(function() {
        return true;
      }).then(() => {
        console.log("startrounds");
        return startRounds(this.rounds.unitId).then(response => {
          this.rounds.active = 0;
          this.rounds.start = true;
          this.rounds.steps = ["开始推进"];

          this.createStepInterval(this.rounds.unitId);

        });
      }).catch(() => {});

     
    },
    handleCloseRoundsDialog(){
      clearInterval(this.timer);
      this.timer = null;
      this.getList();
    }
  }
};
</script>
