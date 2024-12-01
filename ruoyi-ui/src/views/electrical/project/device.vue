<template>
    <div class="app-container">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
        <el-form-item label="设备名称" prop="deviceName">
          <el-input
            v-model="queryParams.deviceName"
            placeholder="请输入设备名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="设备位置" prop="deviceLocation">
          <el-input
            v-model="queryParams.deviceLocation"
            placeholder="请输入设备位置"
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
  
      <el-table v-loading="loading" :data="ElectricityProjectDeviceList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="设备名称" align="center" prop="deviceName" />
        <el-table-column label="设备位置" align="center" prop="deviceLocation" />
        <el-table-column label="设备类型" align="center" prop="deviceType" />
        <el-table-column label="照片数量" align="center" prop="images" />
        <el-table-column label="操作" align="center" fixed="right" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
            size="mini"
            type="text"
            icon="el-icon-tickets"
            @click="handleDeviceImageList(scope.row)"
          >照片集合</el-button>
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
  import { listProjectDevice } from "@/api/design/project";
  
  export default {
    name: "ElectricityProjectDevice",
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
        // 电力设计项目设备表格数据
        ElectricityProjectDeviceList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          projectId: this.$route.params.projectId,
          deviceName: null,
          deviceLocation: null,
          deviceType: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {}
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询电力设计项目设备列表 */
      getList() {
        this.loading = true;
        listProjectDevice(this.queryParams).then(response => {
          this.ElectricityProjectDeviceList = response.rows;
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
        const deviceId = row.id || this.ids[0];
        this.$tab.openPage("设备照片集合", '/electrical/project/view/image/' + deviceId, {});
      }
    }
  };
  </script>
  