<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      
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
      <el-form-item label="社区" prop="community" v-if="projectType === '1' || projectType == '3'">
        <el-select v-model="queryParams.community" placeholder="请选择社区"  filterable clearable >
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
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="OwnerUnitReportList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="区" align="center" prop="district" :show-overflow-tooltip="true" >
        <template slot-scope="scope">
          <dict-tag :options="districtOptions" :value="scope.row.district"/>
        </template>
      </el-table-column>
      <el-table-column label="街道" align="center" prop="street" :show-overflow-tooltip="true" >
        <template slot-scope="scope">
          <dict-tag :options="streetOptions" :value="scope.row.street"/>
        </template>
      </el-table-column>
      <el-table-column label="社区" align="center" prop="community" :show-overflow-tooltip="true" v-if="projectType === '1' || projectType == '3'">
        <template slot-scope="scope">
          <dict-tag :options="communityOptions" :value="scope.row.community"/>
        </template>
      </el-table-column>
      <el-table-column label="村" align="center" prop="hamlet" :show-overflow-tooltip="true" v-if="projectType === '1'">
        <template slot-scope="scope">
          <dict-tag :options="hamletOptions" :value="scope.row.hamlet"/>
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
          >审核</el-button>
          <el-tooltip class="item" effect="dark" content="打开制式Word报告" placement="top">
            <el-button 
              size="medium"
              type="text"
              icon="iconfont iconfont-word"
              @click="handleOpenInitialReport(scope.row)"
            ></el-button>
          </el-tooltip>
          <el-tooltip v-if="false" class="item" effect="dark" content="打开归档Word报告" placement="top">
            <el-button v-if="false"
              size="medium"
              type="text"
              icon="iconfont iconfont-bg-word"
              @click="handleOpenArchivedWordReport(scope.row)"
            ></el-button>
          </el-tooltip>
          <el-dropdown v-if="false" size="medium" @command="(command) => handleCommand(command, scope.row)" >
            <el-button size="medium" type="text" icon="iconfont iconfont-download"></el-button>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="downloadInitialReport" icon="iconfont iconfont-download" >下载制式Word报告</el-dropdown-item>
                <el-dropdown-item command="downloadArchivedWordReport" icon="iconfont iconfont-download" >下载归档Word报告</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <el-tooltip v-if="false" class="item" effect="dark" content="重置报告状态为“未审核”" placement="top">
            <el-button v-if="false"
              size="medium"
              type="text"
              icon="iconfont iconfont-reload"
              @click="handleResetStatus(scope.row)"
            ></el-button>
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
                    <p v-if="item.remark != null && item.remark != ''">驳回原因：{{item.remark}}</p>
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
            <el-form-item label="驳回原因" label-width="100px" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入驳回原因" />
            </el-form-item>
            <el-form-item label="相关图片" label-width="100px" prop="operationPic">
              <image-upload v-model="form.operationPic" />
            </el-form-item>
          </el-form>
          <div style="padding-left: 100px;">
            <el-button type="primary" @click="handlePass">通过</el-button>
            <el-button v-if="form.status !== '0'" @click="handleNotPass">驳回</el-button>
          </div>
        </el-col>
      </el-row>
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
import { listReport, getReport, getWordReport, archivedPdf, getReportLogs, passReport, notPassReport, resetReportStatus} from "@/api/report/CommunityReport";
import { getProject } from "@/api/project/project";
import { getProjectAreaDictByProjectIdAndType } from "@/api/project/ProjectArea";
import DictMeta from '@/utils/dict/DictMeta'
import { Loading } from 'element-ui'

export default {
  name: "CommunityReport",
  dicts: ['high_risk_type', 'owner_unit_report_status'],
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
      
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      workerRoleId: this.$store.state.settings.workerRoleId,
      projectInfo: {

      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        type: '1',
        projectId: this.$store.state.settings.projectId,
        status: null,
        district: null,
        street: null,
        community: null,
        hamlet: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        remark: [
          { required: true, message: "驳回原因不能为空", trigger: "blur" }
        ],
      },
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

      getProject(this.queryParams.projectId).then(response => {
        this.projectInfo = response.data;
        this.projectType = response.data.type;
      });

      getProjectAreaDictByProjectIdAndType(this.$store.state.settings.projectId, 'district').then(response => {
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

      listReport(this.queryParams).then(response => {
        if(this.projectType == 3){
          this.OwnerUnitReportList = response.rows;
          this.total = response.total;
        }
        this.loading = false;
      });
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
      passReport(this.form.reportId).then(response => {
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
        status: null
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
    handleAudit(row){
      this.reset();
      this.form.status = row.status;
      if(row.reportId != null){
        this.form.reportId = row.reportId;
        getReportLogs(row.reportId).then(response => {
          this.reportLogs = response.data;
          this.open = true;
        });
      } else {
        this.queryReportForm = {
          projectId: this.$store.state.settings.projectId,
          district: row.district,
          street: row.street,
          community: row.community,
          type: '1'
        }
        getReport(this.queryReportForm).then(response => {
          this.report = response.data;
          this.form.reportId = this.report.id;
          getReportLogs(this.report.id).then(response => {
            this.reportLogs = response.data;
            this.open = true;
          });
        });
      }
    },
    handleUpdate(row){

    },
    handleResetStatus(row){
      this.$modal.confirm('确认重置社区报告状态为未审核吗？').then(function() {
        return resetReportStatus(row.reportId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("重置成功");
      }).catch(() => {});
    },
    handleOpenInitialReport(row){
      this.loadingInstance = Loading.service({ text: "正在生成数据，请稍候", spinner: "el-icon-loading", background: "rgba(0, 0, 0, 0.7)", })
      // 打开初检报告
      if(row.reportId != null){

        getWordReport(row.reportId).then(response => {

          //this.$tab.openPage("编辑Word报告", '/report/weboffice/weboffice/' + row.reportId + "/1");
          //this.download('common/download/resource?resource=' + response.file, {})
          this.$download.resource(response.data, "社区总报告.docx");
          this.loadingInstance.close();
        }).catch((r) => {
          this.$modal.msgError('生成报告出现错误，请联系管理员！')
          this.loadingInstance.close();
        });
        
      } else {
        this.queryReportForm = {
          projectId: this.$store.state.settings.projectId,
          district: row.district,
          street: row.street,
          community: row.community,
          type: '1'
        }
        getReport(this.queryReportForm).then(response => {
          getWordReport(this.report.id).then(response => {
            //this.$tab.openPage("编辑Word报告", '/report/weboffice/weboffice/' + row.reportId + "/1");
            
            this.$download.resource(response.data, "社区总报告.docx");
            this.loadingInstance.close();

          }).catch((r) => {
            this.$modal.msgError('生成报告出现错误，请联系管理员！')
            this.loadingInstance.close();
          });
          this.loadingInstance.close();
        }).catch((r) => {
          this.$modal.msgError('生成报告出现错误，请联系管理员！')
          this.loadingInstance.close();
        });
      }
    },
    handleDownloadInitialReport(row){
      if(row.wordFile == null){
        this.$modal.msgError("无制式Word报告");
      } else {
        this.$download.resource(row.wordFile, "制式Word报告.docx");
      }
    },
    handleOpenArchivedPdf(row){
      if(row.archivedPdf == null){
        this.$modal.msgError("请先归档Word报告为PDF");
      } else {
        this.$tab.openPage("查看归档PDF报告", '/report/weboffice/weboffice/' + row.reportId + "/3");
      }
    },
    handleDownloadArchivedPDFReport(row) {
      if(row.archivedPdf == null){
        this.$modal.msgError("请先归档Word报告为PDF");
      } else {
        this.$download.resource(row.archivedPdf, "归档PDF报告.pdf");
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
      if(row.archivedWord == null){
        this.$modal.msgError("无归档Word报告");
      } else {
        this.$download.resource(row.archivedWord, "Word归档报告.docx");
      }
    },
    handleArchivedWordReportToPdf(row){
      this.loading = true;
      // word报告归档为pdf
      archivedPdf(row.reportId).then(response => {
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
        default:
          break;
      }
    },
  }
};
</script>
