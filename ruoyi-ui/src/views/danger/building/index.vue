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
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="OwnerUnitList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" width="60" prop="id" />
      <el-table-column label="楼栋名称" align="center" prop="name" min-width="200" :show-overflow-tooltip="true" />
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
            @click="handleViewDangers(scope.row)"
            v-hasPermi="['danger:export:view']"
          >查看</el-button>
          <el-button 
            size="mini"
            type="text"
            icon="el-icon-picture"
            @click="handlePictures(scope.row)"
            v-hasPermi="['danger:export:images']"
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
import { listUnitBuildingDanger, unitBuildingPictures} from "@/api/danger/danger";
import { getProject } from "@/api/project/project";
import { getProjectAreaDictByProjectIdAndType } from "@/api/project/ProjectArea";
import DictMeta from '@/utils/dict/DictMeta'

export default {
  name: "Building",
  dicts: ['initial_test_status', 'again_test_status'],
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
        unitId: parseInt(this.$route.params.unitId),
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
      pictures:[]
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询城中村电检列表 */
    getList() {
      this.loading = true;

      listUnitBuildingDanger(this.queryParams).then(response => {
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
    handlePictures(row){
      this.reset();
      this.loading = true;
      unitBuildingPictures(row.unitId, row.id).then(response => {
        this.pictures = response.data;
        this.title = row.unitName + row.name + " 图片列表";
        this.open = true;
        this.loading = false;
      })
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
    handleViewDangers(row){
      const unitId = row.unitId;
      const buildingId = row.id;
      const params = {};
      this.$tab.openPage("隐患列表", '/danger/list/index/' + unitId + '/' + buildingId, params);
    }
  }
};
</script>
