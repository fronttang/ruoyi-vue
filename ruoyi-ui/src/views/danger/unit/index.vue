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
      <el-form-item label="类型" label-width="100px" prop="highRiskType"  v-if="projectType === '3'">
        <el-select v-model="queryParams.highRiskType" placeholder="请选择业主单元类型" filterable clearable>
          <el-option
            v-for="dict in dict.type.high_risk_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="初检状态" prop="initialStatus" >
        <el-select v-model="queryParams.initialStatus" placeholder="请选择初检状态" filterable clearable>
          <el-option
            v-for="dict in dict.type.initial_test_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="复检状态" prop="reviewStatus" >
        <el-select v-model="queryParams.reviewStatus" placeholder="请选择复检状态" filterable clearable>
          <el-option
            v-for="dict in dict.type.again_test_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          ></el-option>
        </el-select>
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
      <el-form-item label="社区" prop="community" v-if="projectType === '1' || projectType == '3'">
        <el-select v-model="queryParams.community" placeholder="请选择社区"  filterable clearable>
          <el-option
            v-for="dict in communityOptions"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="村" prop="hamlet"  v-if="projectType === '1'">
        <el-select v-model="queryParams.hamlet" placeholder="请选择村"  filterable clearable>
          <el-option
            v-for="dict in hamletOptions"
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

      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5" v-if="this.projectType == '1'">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExportDangerType1"
          >导出初检隐患台账</el-button>
        </el-col>
        <el-col :span="1.5" v-if="this.projectType == '1'">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExportReviewDanger"
          >导出复检隐患台账</el-button>
        </el-col>
        <el-col :span="1.5" v-if="this.projectType == '2'">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExportDangerType2"
          >导出隐患台账</el-button>
        </el-col>
        <el-col :span="1.5" v-if="this.projectType == '3'">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExportDanger"
          >导出隐患台账</el-button>
        </el-col>
        <el-col :span="1.5" v-if="this.projectType == '3'">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExportMissDevice"
          >导出缺失设备台账</el-button>
        </el-col>
        <el-col :span="1.5" v-if="this.projectType == '4'">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExportStationDanger"
          >导出隐患台账</el-button>
        </el-col>
        <el-col :span="1.5" v-if="this.projectType == '4'">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExportRoundsStationDanger"
          >导出总隐患台账</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="OwnerUnitList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" width="60" prop="id" />
      <el-table-column label="业主单元名称" align="center" prop="name" min-width="200" :show-overflow-tooltip="true" />
      <el-table-column label="类型" width="100" align="center" prop="highRiskType" v-if="projectType === '3'">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.high_risk_type" :value="scope.row.highRiskType"/>
        </template>
      </el-table-column>
      <el-table-column label="区域" align="center" prop="area" min-width="200" :formatter="areaFormat" :show-overflow-tooltip="true" />
      <el-table-column label="初检状态" align="center" prop="initialStatus" width="100" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.initial_test_status" :value="scope.row.initialStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="复检状态" align="center" prop="reviewStatus" width="100" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.again_test_status" :value="scope.row.reviewStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="初检时间" align="center" prop="initialDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.initialDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="隐患数量" align="center" prop="dangers" width="100" />
      <el-table-column label="待整改数" align="center" prop="rectifications" width="100" />
      <el-table-column label="待复检数" align="center" prop="reexaminations" width="100" />
      <el-table-column label="完成数" align="center" prop="finishs" width="100" />

      <el-table-column label="操作" align="center" fixed="right"  width="120" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleView(scope.row)"
          >查看</el-button>
          <el-button v-if="projectType !== '2'" 
            size="mini"
            type="text"
            icon="el-icon-picture"
            @click="handlePictures(scope.row)"
          >图片</el-button>
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
    <el-dialog :title="title" class="showAll_dialog" :visible.sync="open" width="860px" height="600px" append-to-body style="overflow:auto" lock-scroll>
      <el-row :gutter="20">
        <el-col :span="6" v-for="pic in pictures" style="padding-top: 10px;">
          <el-card  :body-style="{ padding: '0px' }" style="width:200px">
            <el-image style="width: 200px; height: 150px"
              :src="buildImg(pic.picture)"
              :preview-src-list="buildImgList(pic.picture)">
            </el-image>
            <div id="text" :title="pic.name">
                <span>{{ pic.name }}</span>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-dialog>

  </div>
</template>
<style lang="scss" scoped>
  #text{
    padding: 5px;
    text-align:center;
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
  }
  .showAll_dialog {
    display: flex;
    justify-content: center;
    align-items: center;
    overflow: hidden;
    ::v-deep .el-dialog {
      margin: 0 auto !important;
      height: 80%;
      overflow: hidden;
      .el-dialog__body {
        position: absolute;
        left: 0;
        top: 60px;
        bottom: 10px;
        right: 0;
        padding: 0;
        z-index: 1;
        overflow: hidden;
        overflow-y: auto;
        line-height: 30px;
        padding: 0 15px;
      }
    }
  }
</style>
<script>
import { listUnitDanger, unitPictures, dangerExport, dangerReivewExport, stationRoundsDangerExport} from "@/api/danger/danger";
import { getProject } from "@/api/project/project";
import { getProjectAreaDictByProjectIdAndType } from "@/api/project/ProjectArea";
import DictMeta from '@/utils/dict/DictMeta'
import { Loading } from 'element-ui'

export default {
  name: "OwnerUnitDanger",
  dicts: ['initial_test_status', 'again_test_status', 'high_risk_type'],
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
      // 城中村电检表格数据
      OwnerUnitList: [],
      districtOptions: [],
      streetOptions: [],
      communityOptions: [],
      hamletOptions: [],
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
        name: null,
        id: null,
        ids: null,
        projectId: this.$store.state.settings.projectId,
        district: null,
        street: null,
        community: null,
        hamlet: null,
        highRiskType: null,
        startInitialDate: null,
        endInitialDate: null,
        startReviewDate: null,
        endReviewDate: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      // 检测单位字典选项
      detectUnitDict: [],
      projectAreaDict: [],
      projectType: null,
      pictures:[],
      downloadLoadingInstance: null
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
        this.form.projectName = this.projectInfo.name;
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

      listUnitDanger(this.queryParams).then(response => {
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
        
      };
      this.resetForm("form");
      this.pictures = [];
    },
    buildImg(url){
      return process.env.VUE_APP_BASE_API + url;
    },
    buildImgList(url){
      return [process.env.VUE_APP_BASE_API + url]
    },
    areaFormat(row) {
      var area = [];
      var districtName = this.selectDictLabel(this.districtOptions, row.district);
      var streetName = this.selectDictLabel(this.streetOptions, row.street);
      var communityName = this.selectDictLabel(this.communityOptions, row.community);
      var hamletName = this.selectDictLabel(this.hamletOptions, row.hamlet);

      area.push(districtName);
      if(streetName && streetName != null && streetName != ''){
        area.push(streetName);
      }
      if(communityName && communityName != null && communityName != ''){
        area.push(communityName);
      }
      if(hamletName && hamletName != null && hamletName != ''){
        area.push(hamletName);
      }

      return area.join('/');
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
    handleView(row){
      if(this.projectType === '2'){
        const unitId = row.id;
        const params = {};
        this.$tab.openPage("楼栋隐患分组汇总", '/danger/building/index/' + unitId, params);
      } else {
        const unitId = row.id;
        const params = {};
        this.$tab.openPage("隐患列表", '/danger/list/index/' + unitId, params);
      }
    },
    handlePictures(row){
      this.reset();
      this.loading = true;
      unitPictures(row.id).then(response => {
        this.pictures = response.data;
        this.title = row.name + " 图片列表";
        this.open = true;
        this.loading = false;
      })
    },
    handleExportMissDevice(row){
      this.queryParams.ids = this.ids;

      this.download1('/miss/device/export', {
        ...this.queryParams
      }, `缺失设备台账.xlsx`)
    },
    handleExportDanger(row){
      this.queryParams.ids = this.ids;

      this.downloadDangerExport(this.queryParams, '高风险隐患台账.xlsx')

      //this.download1('/owner/unit/danger/export', {
      //  ...this.queryParams
      //}, `高风险隐患台账.xlsx`)
    },
    handleExportDangerType1(row){
      this.queryParams.ids = this.ids;

      this.downloadDangerExport(this.queryParams, '城中村初检隐患台账.xlsx')

      //this.download1('/owner/unit/danger/export', {
      //  ...this.queryParams
      //}, `城中村初检隐患台账.xlsx`)
    },
    handleExportReviewDanger(row){
      this.queryParams.ids = this.ids;

      this.downloadReviewDangerExport(this.queryParams, '城中村复检隐患台账.xlsx');

      //this.download1('/owner/unit/danger/export/review', {
      //  ...this.queryParams
      //}, `城中村复检隐患台账.xlsx`)
    },
    handleExportDangerType2(row){
      this.queryParams.ids = this.ids;

      this.downloadDangerExport(this.queryParams, '工业园初检隐患台账.xlsx')

      //this.download1('/owner/unit/danger/export', {
      //  ...this.queryParams
      //}, `工业园初检隐患台账.xlsx`)
    },
    handleExportStationDanger(row){
      this.queryParams.ids = this.ids;

      this.downloadDangerExport(this.queryParams, '充电站隐患台账.xlsx')

      //this.download1('/owner/unit/danger/export', {
      //  ...this.queryParams
      //}, `充电站隐患台账.xlsx`)
    },
    handleExportRoundsStationDanger(row){
      this.queryParams.ids = this.ids;
      this.downloadLoadingInstance = Loading.service({ text: "正在下载数据，请稍候", spinner: "el-icon-loading", background: "rgba(0, 0, 0, 0.7)", })
      stationRoundsDangerExport(this.queryParams).then(response => {
        let a = document.createElement("a");
        a.href = process.env.VUE_APP_BASE_API + response.data;
        a.download = "充电站总隐患台账.xlsx";
        document.body.appendChild(a);
        a.click();
        a.remove();
        this.downloadLoadingInstance.close();
      }).catch((r) => {
        this.$modal.msgError('生成文件出现错误，请联系管理员！')
        this.downloadLoadingInstance.close();
      });
    },
    downloadDangerExport(data, fileName){
      this.downloadLoadingInstance = Loading.service({ text: "正在下载数据，请稍候", spinner: "el-icon-loading", background: "rgba(0, 0, 0, 0.7)", })
      dangerExport(data).then(response => {
        let a = document.createElement("a");
        a.href = process.env.VUE_APP_BASE_API + response.data;
        a.download = fileName;
        document.body.appendChild(a);
        a.click();
        a.remove();
        this.downloadLoadingInstance.close();
      }).catch((r) => {
        this.$modal.msgError('生成文件出现错误，请联系管理员！')
        this.downloadLoadingInstance.close();
      });
    },
    downloadReviewDangerExport(data, fileName){
      this.downloadLoadingInstance = Loading.service({ text: "正在下载数据，请稍候", spinner: "el-icon-loading", background: "rgba(0, 0, 0, 0.7)", })
      dangerReivewExport(data).then(response => {
        let a = document.createElement("a");
        a.href = process.env.VUE_APP_BASE_API + response.data;
        a.download = fileName;
        document.body.appendChild(a);
        a.click();
        a.remove();
        this.downloadLoadingInstance.close();
      }).catch((r) => {
        this.$modal.msgError('生成文件出现错误，请联系管理员！')
        this.downloadLoadingInstance.close();
      });
    }
  }
};
</script>
