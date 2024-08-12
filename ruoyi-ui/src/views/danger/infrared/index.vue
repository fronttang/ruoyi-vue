<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="关键字" prop="keyword">
        <el-input
          v-model="queryParams.keyword"
          placeholder="业主单元名称/楼栋名称/公共区域/户/被测设备名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="判定状态" prop="result">
        <el-select v-model="queryParams.result" placeholder="请选择判定状态"  filterable clearable>
          <el-option key="合格" label="合格" value="合格" />
          <el-option key="不合格" label="不合格" value="不合格" />
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
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infraredList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" width="60" />
      <el-table-column label="检测单位" align="center" prop="detectName" min-width="200" :show-overflow-tooltip="true" />
      <el-table-column label="业主单元" align="center" prop="unitName" min-width="100" :show-overflow-tooltip="true" />
      <el-table-column label="楼栋名称" v-if="projectType === '2'" align="center" prop="buildingName" min-width="100" :show-overflow-tooltip="true" />
      <el-table-column label="公共区域/户" align="center" prop="areaName" min-width="100" :show-overflow-tooltip="true" />
      <el-table-column label="区域" align="center" prop="area" min-width="200" :formatter="areaFormat" :show-overflow-tooltip="true" />
      <el-table-column label="类型" align="center" prop="type" width="60" />
      <el-table-column label="被测设备名称" align="center" prop="deviceName" min-width="100" :show-overflow-tooltip="true"  />
      <el-table-column label="设备编号" align="center" prop="deviceCode" min-width="100" :show-overflow-tooltip="true"  />
      <el-table-column label="图片编号" align="center" prop="imageCode" min-width="100" :show-overflow-tooltip="true"  />
      <el-table-column label="现场检测图" align="center" prop="inspectionPic" width="100" >
        <template slot-scope="scope">
          <image-preview :src="scope.row.inspectionPic" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="红外判定图" align="center" prop="infraredPic" width="100" >
        <template slot-scope="scope">
          <image-preview :src="scope.row.infraredPic" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="判定状态" align="center" prop="result" width="100" />
      <el-table-column label="操作" fixed="right" width="80" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >判定</el-button>
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

    <!-- =红外判定对话框 -->
    <el-dialog title="红外判定" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="判定状态" prop="result">
          <el-select v-model="form.result" placeholder="请选择判定状态"  filterable clearable>
            <el-option key="合格" label="合格" value="合格" />
            <el-option key="不合格" label="不合格" value="不合格" />
          </el-select>
        </el-form-item>
        <el-form-item label="现场检测图" label-width="100px" prop="inspectionPic">
          <image-preview :src="form.inspectionPic" :width="100" :height="100"/>
        </el-form-item>
        <el-form-item label="红外判定图" label-width="100px" prop="infraredPic">
          <image-upload v-model="form.infraredPic" :limit="limit" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listInfrared, updateInfrared } from "@/api/danger/infrared";
import { getProject } from "@/api/project/project";
import { getProjectAreaDictByProjectIdAndType } from "@/api/project/ProjectArea";
import DictMeta from '@/utils/dict/DictMeta'

export default {
  name: "Infrared",
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
      // 红外判定表格数据
      infraredList: [],
      districtOptions: [],
      streetOptions: [],
      communityOptions: [],
      hamletOptions: [],
      projectInfo: {},
      projectType: null,
      limit: 1,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectId: this.$store.state.settings.projectId,
        keyword: null,
        type: null,
        result: null,
        startDate: null,
        endDate: null,
        district: null,
        street: null,
        community: null,
        hamlet: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询红外判定列表 */
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

      listInfrared(this.queryParams).then(response => {
        this.infraredList = response.rows;
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
        inspectionPic: null,
        result: null,
        infraredPic: null,
      };
      this.resetForm("form");
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
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.form.id = row.id;
      this.form.result = row.result;
      this.form.inspectionPic = row.inspectionPic;
      this.form.infraredPic = row.infraredPic;
      this.open = true;
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateInfrared(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } 
        }
      });
    },
  }
};
</script>
