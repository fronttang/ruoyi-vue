<template>
    <div class="app-container">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
        <el-form-item label="照片集名称" prop="imageName">
          <el-input
            v-model="queryParams.imageName"
            placeholder="请输入照片集名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
  
      <el-row :gutter="10" class="mb8">
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
  
      <el-table v-loading="loading" :data="ElectricityProjectDeviceImageList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="照片集名称" align="center" prop="imageName" />
        <el-table-column label="照片集类型" align="center" prop="imageType" />
        <el-table-column label="照片数量" align="center" prop="count" />
        <el-table-column label="操作" align="center" fixed="right" class-name="small-padding fixed-width">
            <template slot-scope="scope">
                <el-button
                size="mini" 
                type="text"
                icon="el-icon-tickets"
                @click="handleDeviceImageList(scope.row)"
            >照片</el-button>
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
  
      <!-- 添加或修改设备照片集对话框 -->
      <el-dialog title="照片平铺" class="showAll_dialog" :visible.sync="open" width="860px" height="600px" append-to-body style="overflow:auto" lock-scroll>
        <el-tabs v-model="activeName" >
            <el-tab-pane v-for="(item, index) in imagesList" :label="item.name" :name="index + ''">
                <el-row :gutter="20">
                    <el-col :span="6" v-for="pic in item.images" style="padding-top: 10px;">
                        <el-card  :body-style="{ padding: '0px' }" style="width:200px">
                            <el-image style="width: 200px; height: 150px"
                            :src="buildImg(pic)" :preview-src-list="buildImgList(pic)">
                            </el-image>
                        </el-card>
                    </el-col>
                </el-row>
            </el-tab-pane>
        </el-tabs>
      </el-dialog>
    </div>
  </template>
  <style lang="scss" scoped>
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
  import { listProjectDeviceImage } from "@/api/design/project";
  
  export default {
    name: "ElectricityProjectDeviceImage",
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
        // 设备照片集表格数据
        ElectricityProjectDeviceImageList: [],
        imagesList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          projectId: null,
          deviceId: this.$route.params.deviceId,
          imageName: null,
          imageType: null,
          images: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {},
        activeName: '0',
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询设备照片集列表 */
      getList() {
        this.loading = true;
        listProjectDeviceImage(this.queryParams).then(response => {
          this.ElectricityProjectDeviceImageList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
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
      handleDeviceImageList(row) {
        this.imagesList = row.images;
        this.activeName = "0";
        this.open = true;
      },
      buildImg(url){
        return process.env.VUE_APP_BASE_API + url;
      },
      buildImgList(url){
      return [process.env.VUE_APP_BASE_API + url]
    },
    }
  };
  </script>
  