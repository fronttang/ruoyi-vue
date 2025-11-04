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
      <el-form-item label="状态" prop="status" >
        <el-select v-model="queryParams.status" placeholder="请选择状态" filterable clearable>
          <el-option
            v-for="dict in dict.type.owner_unit_report_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="区" prop="district">
        <el-select v-model="queryParams.district" placeholder="请选择区"  filterable clearable @change="handleChangeDistrict">
          <el-option
            v-for="dict in districtOptions"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="街道" prop="street">
        <el-select v-model="queryParams.street" placeholder="请选择街道"  filterable clearable @change="handleChangeStreet">
          <el-option
            v-for="dict in streetOptions"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="社区" prop="community" v-if="projectType === '1' || projectType == '3'">
        <el-select v-model="queryParams.community" placeholder="请选择社区"  filterable clearable  @change="handleChangeCommunity">
          <el-option
            v-for="dict in communityOptions"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="村" prop="hamlet" v-if="projectType === '1'">
        <el-select v-model="queryParams.hamlet" placeholder="请选择村"  filterable clearable >
          <el-option
            v-for="dict in hamletOptions"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="初检状态" prop="detectStatus" >
        <el-select v-model="queryParams.detectStatus" placeholder="请选择初检状态" filterable clearable>
          <el-option
            v-for="dict in dict.type.initial_test_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="初检日期" prop="initialDate" >
        <el-date-picker clearable @change="$forceUpdate()"
            v-model="queryParams.initialDate"
            type="daterange"
            range-separator="-"
            value-format="yyyy-MM-dd"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            size="small">
          </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          @click="handleBatchAudit"
          v-hasPermi="['report:init:audit']"
        >批量审核</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-date"
          size="mini"
          @click="handleBatchSetReportDate"
          v-hasPermi="['report:init:reportdate']"
        >批量设置编制日期</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-dropdown size="mini" @command="handleBatchCommand" v-hasPermi="['report:init:download']">
          <el-button size="mini" type="primary" plain icon="el-icon-download">批量下载<i class="el-icon-arrow-down el-icon--right"></i></el-button>
          <el-dropdown-menu slot="dropdown">
              <el-dropdown-item :command="1">制式Word报告</el-dropdown-item>
              <el-dropdown-item :command="2">归档Word报告</el-dropdown-item>
              <el-dropdown-item :command="3">原始记录(电检)</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="OwnerUnitReportList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" width="60" prop="unitId" />
      <el-table-column label="名称" align="center" prop="name" min-width="200" :show-overflow-tooltip="true" >
        <template slot-scope="scope">
          <router-link :to="'/danger/list/index/' + scope.row.unitId" class="link-type">
            <span>{{ scope.row.name }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="检测单位" v-if="false" align="center" prop="detectId" min-width="300" :formatter="detectFormat" :show-overflow-tooltip="true"/>
      <el-table-column label="业主单元类型" width="100" align="center" prop="highRiskType" v-if="projectType === '3'">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.high_risk_type" :value="scope.row.highRiskType"/>
        </template>
      </el-table-column>
      <el-table-column label="区" align="center" prop="district" :show-overflow-tooltip="true" >
        <template slot-scope="scope">
          <dict-tag :options="districtList" :value="scope.row.district"/>
        </template>
      </el-table-column>
      <el-table-column label="街道" align="center" prop="street" :show-overflow-tooltip="true" >
        <template slot-scope="scope">
          <dict-tag :options="streetList" :value="scope.row.street"/>
        </template>
      </el-table-column>
      <el-table-column label="社区" align="center" prop="community" :show-overflow-tooltip="true" v-if="projectType === '1' || projectType == '3'">
        <template slot-scope="scope">
          <dict-tag :options="communityList" :value="scope.row.community"/>
        </template>
      </el-table-column>
      <el-table-column label="村" align="center" prop="hamlet" :show-overflow-tooltip="true" v-if="projectType === '1'">
        <template slot-scope="scope">
          <dict-tag :options="hamletList" :value="scope.row.hamlet"/>
        </template>
      </el-table-column>
      <el-table-column label="编制日期" align="center" prop="reportDate" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.reportDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" width="150" :formatter="statusFormat" />
      <el-table-column label="操作" align="center" fixed="right"  width="220" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleAudit(scope.row)"
            v-hasPermi="['report:init:audit']"
          >审核</el-button>
          <el-tooltip class="item" effect="dark" content="打开制式Word报告" placement="top">
            <el-button 
              size="medium"
              type="text"
              icon="iconfont iconfont-word"
              @click="handleOpenInitialReport(scope.row)"
              v-hasPermi="['report:init:edit']"
            ></el-button>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="打开归档Word报告" placement="top">
            <el-button 
              size="medium"
              type="text"
              icon="iconfont iconfont-bg-word"
              @click="handleOpenArchivedWordReport(scope.row)"
              v-hasPermi="['report:init:edit']"
            ></el-button>
          </el-tooltip>
          <el-dropdown size="medium" @command="(command) => handleCommand(command, scope.row)" v-hasPermi="['report:init:download']" >
            <el-button size="medium" type="text" icon="iconfont iconfont-download"></el-button>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="downloadInitialReport" icon="iconfont iconfont-download" >下载制式Word报告</el-dropdown-item>
                <el-dropdown-item command="downloadArchivedWordReport" icon="iconfont iconfont-download" >下载归档Word报告</el-dropdown-item>
                <el-dropdown-item command="downloadOriginalRecords" icon="iconfont iconfont-download" >下载原始记录（电检）</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <el-tooltip class="item" effect="dark" content="重置报告状态为“未审核”" placement="top">
            <el-button v-if="scope.row.status !== '0' && workerRoleId === '2'"
              size="medium"
              type="text"
              icon="iconfont iconfont-reload"
              @click="handleResetStatus(scope.row)"
              v-hasPermi="['report:init:status']"
            >  </el-button>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="编制日期" placement="top">
            <el-button
              size="medium"
              type="text"
              icon="el-icon-date"
              @click="handleReportDate(scope.row)"
              v-hasPermi="['report:init:reportdate']"
            >  </el-button>
          </el-tooltip>
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

    <el-dialog title="" :visible.sync="open" width="800px" append-to-body>
      <el-row>
        <el-col :span="form.status !== '3'? 12 : 24">
          <el-timeline :data="reportLogs" v-for="(item,index) in reportLogs" >
            <el-timeline-item :timestamp="item.createTime" placement="top">
              <el-collapse accordion>
                <el-collapse-item :title="item.content" :name="index">
                  <div>
                    <p v-if="item.remark != null && item.remark != ''">备注：{{item.remark}}</p>
                    <p v-if="item.operationPic != null && item.operationPic != ''">
                      相关图片：<image-preview :src="item.operationPic" :width="50" :height="50"/>
                    </p>
                  </div>
                </el-collapse-item>
              </el-collapse>
            </el-timeline-item>
          </el-timeline>
        </el-col>
        <el-col :span="12" v-if="form.status !== '3'">
          <el-form ref="form" :model="form" :rules="rules" label-width="80px">
            <el-form-item label="备注" label-width="100px" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
            </el-form-item>
            <el-form-item label="相关图片" label-width="100px" prop="operationPic">
              <image-upload v-model="form.operationPic" />
            </el-form-item>
          </el-form>
          <div style="padding-left: 100px;">
            <el-button type="primary" @click="handlePass">提交审核</el-button>
            <el-button v-if="form.status !== '0'" @click="handleNotPass">驳回</el-button>
          </div>
        </el-col>
      </el-row>
    </el-dialog>

    <el-dialog title="编制日期" :visible.sync="openReportDate" width="600px" append-to-body>
      <el-form ref="reportDateForm" :model="reportDateForm" :rules="reportDateRoles" label-width="100px" label-position="top">
        <el-form-item label="编制日期" label-width="100px" prop="reportDate">
          <el-date-picker clearable @change="$forceUpdate()"
            v-model="reportDateForm.reportDate"
            type="daterange"
            range-separator="-"
            value-format="yyyy-MM-dd"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            size="small">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="setReportDateLoading" @click="submitReportDate">确 定</el-button>
        <el-button @click="openReportDate = false">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="批量审核" :visible.sync="batchAuditOpen" width="800px" append-to-body>
        <el-form ref="batchAuditForm" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="备注" label-width="100px" prop="remark">
            <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
          </el-form-item>
          <el-form-item label="相关图片" label-width="100px" prop="operationPic">
            <image-upload v-model="form.operationPic" />
          </el-form-item>
        </el-form>
        <div style="padding-left: 100px;">
          <el-button type="primary" :loading="handleBatchPassLoading" @click="handleBatchPass">提交审核</el-button>
          <el-button v-if="form.status !== '0'" :loading="handleBatchNotPassLoading" @click="handleBatchNotPass">驳回</el-button>
        </div>
    </el-dialog>

    <el-dialog title="批量设置编制日期" :visible.sync="batchReportDateOpen" width="600px" append-to-body>
      <el-form ref="batchReportDateForm" :model="reportDateForm" :rules="reportDateRoles" label-width="100px" label-position="top">
        <el-form-item label="编制日期" label-width="100px" prop="reportDate">
          <el-date-picker clearable @change="$forceUpdate()"
            v-model="reportDateForm.reportDate"
            type="daterange"
            range-separator="-"
            value-format="yyyy-MM-dd"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            size="small">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="submitBatchReportDateLoading" @click="submitBatchReportDate">确 定</el-button>
        <el-button @click="batchReportDateOpen = false">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<style>
.el-collapse-item__header{
border-bottom:0px;
height: 35px;
}
.el-collapse-item__arrow {
display:none;
}
.el-timeline-item {
padding-bottom: 0px;
}
</style>
<script>
import { listReport, getWordReport, archivedPdf, getReportLogs, passReport, notPassReport, resetReportStatus, setReportDate, getOriginalRecords, batchPass, batchNotPass, batchSetReportDate, batchDownload, batchGenerateReport} from "@/api/report/report";
import { detectUnitDict } from "@/api/projectrole/DetectUnit";
import { getProject } from "@/api/project/project";
import { getProjectAreaDictByProjectIdAndType, getProjectAreaDictTree } from "@/api/project/ProjectArea";
import DictMeta from '@/utils/dict/DictMeta'
import {Loading } from 'element-ui'

export default {
  name: "Initial",
  dicts: ['high_risk_type', 'owner_unit_report_status', 'initial_test_status'],
  computed: {
    workerRole() {
      return this.$store.state.settings.workerRoleId;
    },
  },
  watch: {
    workerRole: {
      handler(newVal, oldVal) {
        if (!newVal || newVal == oldVal) return;
        this.workerRoleId = newVal;
      },
      immediate: true,
    },
  },
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
      OwnerUnitReportList: [],
      detectUnitDict: [],
      districtOptions: [],
      streetOptions: [],
      communityOptions: [],
      hamletOptions: [],

      districtData: [],
      streetData: [],
      communityData: [],
      hamletData: [],

      districtList: [],
      streetList: [],
      communityList: [],
      hamletList: [],
      
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      batchAuditOpen: false,
      openReportDate: false,
      setReportDateLoading: false,
      batchReportDateOpen: false,
      handleBatchPassLoading: false,
      handleBatchNotPassLoading: false,
      submitBatchReportDateLoading: false,
      workerRoleId: this.$store.state.settings.workerRoleId,
      projectInfo: {

      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        type: '1',
        highRiskType: null,
        projectId: this.$store.state.settings.projectId,
        status: null,
        district: null,
        street: null,
        community: null,
        hamlet: null,
        unitIds: [],
        startDate: null,
        endDate: null,
        remark: null,
        operationPic: null,
        detectStatus: null,
        initialDate: [],
        startDate: null,
        endDate: null
      },
      // 表单参数
      form: {},
      reportDateForm: {
        reportDate: [],
        unitId: null,
        type: '1'
      },
      // 表单校验
      rules: {
      },
      reportDateRoles: {},
      projectType: null,
      reportLogs: [],
      loadingInstance: null
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询城中村电检列表 */
    getList() {

      this.loading = true;
      detectUnitDict().then(response => {
        this.detectUnitDict = response.data;
      });

      getProject(this.queryParams.projectId).then(response => {
        this.projectInfo = response.data;
        this.projectType = response.data.type;
      });

      getProjectAreaDictTree(this.$store.state.settings.projectId).then(response => {
        this.districtData = response.data;
        const dictMeta = DictMeta.parse("districtOptions");
        this.districtOptions = dictMeta.responseConverter(response.data, dictMeta);
      });

      getProjectAreaDictByProjectIdAndType(this.$store.state.settings.projectId, 'district').then(response => {
        const dictMeta = DictMeta.parse("districtList");
        this.districtList = dictMeta.responseConverter(response.data, dictMeta);
      });

      getProjectAreaDictByProjectIdAndType(this.$store.state.settings.projectId, 'street').then(response => {
        const dictMeta = DictMeta.parse("streetList");
        this.streetList = dictMeta.responseConverter(response.data, dictMeta);
      });

      getProjectAreaDictByProjectIdAndType(this.$store.state.settings.projectId, 'community').then(response => {
        const dictMeta = DictMeta.parse("communityList");
        this.communityList = dictMeta.responseConverter(response.data, dictMeta);
      });

      getProjectAreaDictByProjectIdAndType(this.$store.state.settings.projectId, 'hamlet').then(response => {
        const dictMeta = DictMeta.parse("hamletList");
        this.hamletList = dictMeta.responseConverter(response.data, dictMeta);
      });

      listReport(this.queryParams).then(response => {
        this.OwnerUnitReportList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    handleChangeDistrict(district) {
      this.queryParams.street = null;
      this.queryParams.community = null;
      this.queryParams.hamlet = null;
      if( district != null) {
        var selectDistrict  = this.districtData.filter(item => item.dictValue == district);
        console.log(selectDistrict);
        if(selectDistrict != null && selectDistrict.length > 0) {
          this.streetData = selectDistrict[0].sub;
          console.log(this.streetData);
          const dictMeta = DictMeta.parse("streetOptions");
          this.streetOptions = dictMeta.responseConverter(this.streetData, dictMeta);
        } else {
          this.streetOptions = [];
        }
      } else {
        this.streetOptions = [];
      }
    },
    handleChangeStreet(street) {
      this.queryParams.community = null;
      this.queryParams.hamlet = null;
      if( street != null) {
        var selectStreet  = this.streetData.filter(item => item.dictValue == street);
        console.log(selectStreet);
        if(selectStreet != null && selectStreet.length > 0) {
          this.communityData = selectStreet[0].sub;
          console.log(this.communityData);
          const dictMeta = DictMeta.parse("streetOptions");
          this.communityOptions = dictMeta.responseConverter(this.communityData, dictMeta);
        } else {
          this.communityOptions = [];
        }
      } else {
        this.communityOptions = [];
      }
    },
    handleChangeCommunity(community) {
      this.queryParams.hamlet = null;
      if( community != null) {
        var selected  = this.communityData.filter(item => item.dictValue == community);
        console.log(selected);
        if(selected != null && selected.length > 0) {
          this.hamletData = selected[0].sub;
          console.log(this.hamletData);
          const dictMeta = DictMeta.parse("streetOptions");
          this.hamletOptions = dictMeta.responseConverter(this.hamletData, dictMeta);
        } else {
          this.hamletOptions = [];
        }
      } else {
        this.hamletOptions = [];
      }
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    handleNotPass(){
      this.$refs["form"].validate(valid => {
        if (valid) {
          notPassReport(this.form).then(response => {
            this.$modal.msgSuccess("操作成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
    handlePass(){
      passReport(this.form.unitId, '1').then(response => {
        this.$modal.msgSuccess("操作成功");
        this.open = false;
        this.getList();
      });
    },
    // 表单重置
    reset() {
      this.form = {
        reportId: null,
        remark: null,
        operationPic: null,
        status: null,
        unitId: null,
        type: '1'
      };
      this.resetForm("form");
    },
    statusFormat(row){
      var statusName =  this.selectDictLabel(this.dict.type.owner_unit_report_status, row.status);
      if(row.reject === '1'){
        statusName = statusName + "（被驳回）";
      }
      return statusName;
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
      if(this.queryParams.initialDate != null){
        this.queryParams.startDate = this.queryParams.initialDate[0];
        this.queryParams.endDate = this.queryParams.initialDate[1];
      }
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
      this.ids = selection.map(item => item.unitId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    handleAudit(row){
      this.reset();
      this.form.status = row.status;
      this.form.unitId = row.unitId;
      getReportLogs(row.unitId, '1').then(response => {
        this.reportLogs = response.data;
        this.open = true;
      });
    },
    handleUpdate(row){

    },
    handleResetStatus(row){
      this.$modal.confirm('确认重置业主单元"' + row.name + '"的初检报告状态为未审核吗？').then(function() {
        return resetReportStatus(row.unitId, "1");
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("重置成功");
      }).catch(() => {});
    },
    handleChooseRow(row){
      const unitId = row.unitId;
      const params = {};
      this.$tab.openPage("隐患数据汇总", '/danger/list/index/' + unitId, params);
    },
    handleDownloadOriginalRecords(row){
      var fileName = "Y" + row.name + ".docx";
      getOriginalRecords(row.unitId, '1').then(response => {
        this.$download.resource(response.data, fileName);
      });
    },
    handleOpenInitialReport(row){
      this.loadingInstance = Loading.service({ text: "正在生成数据，请稍候", spinner: "el-icon-loading", background: "rgba(0, 0, 0, 0.7)", })
      // 打开初检报告
      getWordReport(row.unitId, '1').then(response => {
        this.$tab.openPage("编辑Word报告", '/report/weboffice/weboffice/' + response.data.reportId + "/1");
        //this.download('common/download/resource?resource=' + response.file, {})

        this.loadingInstance.close();
      }).catch((r) => {
        this.$modal.msgError('生成报告出现错误，请联系管理员！')
        this.loadingInstance.close();
      });
    },
    handleDownloadInitialReport(row){
      var fileName = "Z" + row.name + ".docx";
      //if(row.wordFile == null){
        this.loadingInstance = Loading.service({ text: "正在生成数据，请稍候", spinner: "el-icon-loading", background: "rgba(0, 0, 0, 0.7)", })
        getWordReport(row.unitId, '1').then(response => {
          //this.download('common/download/resource?resource=' + response.file, {})
          this.$download.resource(response.data.path, fileName);
          this.loadingInstance.close();
        }).catch((r) => {
          this.$modal.msgError('生成报告出现错误，请联系管理员！')
          this.loadingInstance.close();
        });

        //this.$modal.msgError("无制式Word报告");
      //} else {
      //  this.$download.resource(row.wordFile, fileName);
      //}
    },
    handleOpenArchivedPdf(row){
      if(row.archivedPdf == null){
        this.$modal.msgError("请先归档Word报告为PDF");
      } else {
        this.$tab.openPage("查看归档PDF报告", '/report/weboffice/weboffice/' + row.reportId + "/3");
      }
    },
    handleDownloadArchivedPDFReport(row) {
      var fileName = "C" + row.name + ".pdf";
      if(row.archivedPdf == null){
        this.$modal.msgError("请先归档Word报告为PDF");
      } else {
        this.$download.resource(row.archivedPdf, fileName);
        //this.$download.resource(row.archivedPdf);
      }
    },
    handleOpenArchivedWordReport(row) {
      if(row.archivedWord == null){
        this.$modal.msgError("无归档Word报告");
      } else {
        this.$tab.openPage("编辑Word归档报告", '/report/weboffice/weboffice/' + row.reportId + "/2");
      }
    },
    handleDownloadArchivedWordReport(row) {
      var fileName = "C" + row.name + ".docx";
      if(row.archivedWord == null){
        this.$modal.msgError("无归档Word报告");
      } else {
        this.$download.resource(row.archivedWord, fileName);
      }
    },
    handleArchivedWordReportToPdf(row){
      this.loading = true;
      // word报告归档为pdf
      archivedPdf(row.unitId, '1').then(response => {
        this.$modal.msgSuccess('归档成功')
        this.loading = false;
      }).catch((r) => {
        //this.$modal.msgError("无归档Word报告");
        this.loading = false;
      });
    },
    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case "downloadInitialReport":
          this.handleDownloadInitialReport(row);
          break;
        case "downloadArchivedWordReport":
          this.handleDownloadArchivedWordReport(row);
          break;
        case "downloadOriginalRecords":
          this.handleDownloadOriginalRecords(row);
          break;
        default:
          break;
      }
    },
    handleReportDate(row){
      this.reportDateForm.unitId = row.unitId;
      this.reportDateForm.type = '1'; 
      if(row.startDate != null && row.endDate != null){
        this.reportDateForm.reportDate = [row.startDate , row.endDate];
      } else {
        this.reportDateForm.reportDate = [];
      }
      this.openReportDate = true;
    },
    submitReportDate(){
      var param = {};
      if(this.reportDateForm.reportDate != null){
        param.startDate = this.reportDateForm.reportDate[0];
        param.endDate = this.reportDateForm.reportDate[1];
      }

      param.unitId = this.reportDateForm.unitId;
      param.type = '1';

      this.setReportDateLoading = true;
      setReportDate(param).then(response => {
        this.setReportDateLoading = false;
        this.openReportDate = false;
        this.$modal.msgSuccess('设置成功')
        this.getList();
      }).catch((r) => {
        this.setReportDateLoading = false;
        this.openReportDate = false;
      });
    },
    handleBatchCommand(type){
      this.loadingInstance = Loading.service({ text: "正在生成数据，请稍候", spinner: "el-icon-loading", background: "rgba(0, 0, 0, 0.7)", })
      var filename = '报告.zip';
      if(type == '1'){
        filename = '制式报告.zip';
      } else if(type == '2'){
        filename = '归档报告.zip';
      } else if(type == '3'){
        filename = '原始记录.zip';
      } 
      // 批量下载
      this.queryParams.unitIds = this.ids;

      batchGenerateReport(this.queryParams, type).then(res => {
        batchDownload(this.queryParams, type).then(response => {
          this.$download.resource(response.data, filename);
          this.loadingInstance.close();
        }).catch((r) => {
          this.$modal.msgError('生成报告出现错误，请稍后重试！')
          this.loadingInstance.close();
        });
      }).catch((r) => {
        this.$modal.msgError('生成报告出现错误，请稍后重试！')
        this.loadingInstance.close();
      });
    },
    handleBatchAudit(){
      this.reset();
      this.batchAuditOpen = true;
    },
    handleBatchPass(){
      this.handleBatchPassLoading = true;
      this.queryParams.unitIds = this.ids;
      batchPass(this.queryParams).then(response => {
        this.$modal.msgSuccess('操作成功')
        this.batchAuditOpen = false;
        this.handleBatchPassLoading = false;
        this.getList();
      }).catch((r) => {
        this.handleBatchPassLoading = false;
        this.batchAuditOpen = false;
      });
    },
    handleBatchNotPass(){
      this.handleBatchNotPassLoading = true;
      this.$refs["batchAuditForm"].validate(valid => {
        if (valid) {
          this.queryParams.unitIds = this.ids;
          this.queryParams.remark = this.form.remark;
          this.queryParams.operationPic = this.form.operationPic;
          batchNotPass(this.queryParams).then(response => {
            this.$modal.msgSuccess('操作成功')
            this.batchAuditOpen = false;
            this.handleBatchNotPassLoading = false;
            this.getList();
          }).catch((r) => {
            this.handleBatchNotPassLoading = false;
            this.batchAuditOpen = false;
          });
        } else{
          this.handleBatchNotPassLoading = false;
        }
      });
    },
    handleBatchSetReportDate(){
      this.reportDateForm.reportDate = [];
      this.batchReportDateOpen = true;
    },
    submitBatchReportDate() {
      this.submitBatchReportDateLoading = true;
      if(this.reportDateForm.reportDate != null){
        this.queryParams.startDate = this.reportDateForm.reportDate[0];
        this.queryParams.endDate = this.reportDateForm.reportDate[1];
      }
      this.queryParams.unitIds = this.ids;
      batchSetReportDate(this.queryParams).then(response => {
        this.$modal.msgSuccess('操作成功')
        this.batchReportDateOpen = false;
        this.submitBatchReportDateLoading = false;
        this.getList();
      }).catch((r) => {
        this.submitBatchReportDateLoading = false;
        this.batchReportDateOpen = false;
      });
    }
  }
};
</script>
