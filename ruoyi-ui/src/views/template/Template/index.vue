<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="检测单位" prop="detectId" clearable filterable>
        <el-select v-model="queryParams.detectId" placeholder="请选择检测单位" clearable filterable>
          <el-option
            v-for="item in detectUnitDict"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          ></el-option>
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
          v-hasPermi="['template:Template:add']"
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
          v-hasPermi="['template:Template:edit']"
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
          v-hasPermi="['template:Template:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['template:Template:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="TemplateList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="检测单位" align="center" prop="detectId"  :show-overflow-tooltip="true" >
        <template slot-scope="scope">
          <span>{{ formatDetectName(scope.row.detectId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="模板名称" align="center" prop="name" />
      <el-table-column label="模板类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.project_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="updateTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <span v-if="scope.row.type === 'urban_village' || scope.row.type === 'industrial_area'">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUrbanVillageIntuitiveDatect(scope.row)"
            >直观标题</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUrbanVillageIntuitiveDatectData(scope.row)"
            >直观内容</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUrbanVillageDatectDevice(scope.row)"
            >仪器模板</el-button>
          </span>
          <span v-if="scope.row.type === 'charging_station'">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleChargingStationIntuitiveDatectData(scope.row)"
            >检测项</el-button>
          </span>
          <span v-if="scope.row.type === 'high_fire_risk'">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleHighFireRiskScore(scope.row)"
            >记分模块</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleHighFireRiskView(scope.row)"
            >展示模块</el-button>
          </span>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
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

    <!-- 添加或修改模板列表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="检测单位" prop="detectId">
        <el-select v-model="form.detectId" placeholder="请选择检测单位">
          <el-option
            v-for="item in detectUnitDict"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
        <el-form-item label="模板名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入模板名称" />
        </el-form-item>
        <el-form-item label="模板类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择模板类型">
            <el-option
              v-for="dict in dict.type.project_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
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
import { listTemplate, getTemplate, delTemplate, addTemplate, updateTemplate } from "@/api/template/Template";
import { detectUnitDict } from "@/api/projectrole/DetectUnit";

export default {
  name: "Template",
  dicts: ['project_type'],
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
      // 模板列表表格数据
      TemplateList: [],
      // 检测单位字典选项
      detectUnitDict: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        detectId: null,
        detectName: null,
        name: null,
        type: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        detectId: [
          { required: true, message: "检测单位不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询模板列表列表 */
    getList() {
      this.loading = true;
      detectUnitDict().then(response => {
        this.detectUnitDict = response.data;
      });
      listTemplate(this.queryParams).then(response => {
        this.TemplateList = response.rows;
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
        detectId: null,
        detectName: null,
        name: null,
        type: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    formatDetectName(detectId) {
      return this.selectDictLabel(this.detectUnitDict, detectId);
    },
    selectDictLabel(datas, value) {
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
    /** 打开直观标题 */
    handleUrbanVillageIntuitiveDatect(row){
      const templateId = row.id || this.ids[0];
      const params = {};
      this.$tab.openPage("直观检测标题", '/template/UrbanVillage/IntuitiveDetect/index/' + templateId, params);
    },
    /** 打开直观内容 */
    handleUrbanVillageIntuitiveDatectData(row){
      const templateId = row.id || this.ids[0];
      const params = {};
      this.$tab.openPage("直观检测表内容", '/template/UrbanVillage/IntuitiveDetectData/index/' + templateId, params);
    },
    /** 打开仪器模板 */
    handleUrbanVillageDatectDevice(row){

    },
    handleChargingStationIntuitiveDatectData(row) {
      const templateId = row.id || this.ids[0];
      const params = {};
      this.$tab.openPage("直观检测项", '/template/ChargingStation/index/' + templateId, params);
    },
    handleHighFireRiskScore(row){
      const templateId = row.id || this.ids[0];
      const params = {};
      this.$tab.openPage("直观检测计分模块", '/template/HighFireRisk/score/index/' + templateId, params);
    },
    handleHighFireRiskView(row){
      const templateId = row.id || this.ids[0];
      const params = {};
      this.$tab.openPage("展示检测表", '/template/HighFireRisk/view/index/' + templateId, params);
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
      this.title = "添加模板列表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTemplate(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改模板列表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTemplate(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTemplate(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除模板列表编号为"' + ids + '"的数据项？').then(function() {
        return delTemplate(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('template/Template/export', {
        ...this.queryParams
      }, `Template_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
