<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="项目名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入项目名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择项目类型" filterable clearable>
          <el-option
            v-for="dict in dict.type.project_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="检测单位">
        <el-select v-model="queryParams.detectId" placeholder="请选择检测单位" filterable clearable>
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
          v-hasPermi="['project:project:add']"
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
          v-hasPermi="['project:project:edit']"
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
          v-hasPermi="['project:project:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['project:project:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="projectList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" width="50" />
      <el-table-column label="项目名称" align="center" prop="name" />
      <el-table-column label="项目类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.project_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="检测单位" align="center" prop="detectName" :show-overflow-tooltip="true" />
      <el-table-column label="最后修改时间" align="center" prop="updateTime" width="180">
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
            v-hasPermi="['project:project:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['project:project:remove']"
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

    <!-- 添加或修改项目对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="项目名称" label-width="130px" prop="name">
          <el-input v-model="form.name" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="项目类型" label-width="130px" prop="type">
          <el-select v-model="form.type" placeholder="请选择项目类型" filterable clearable @change="handleChangeProjectType" >
            <el-option
              v-for="dict in dict.type.project_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="检测单位" label-width="130px" prop="detectId">
          <el-select v-model="form.detectId" placeholder="请选择检测单位" filterable clearable @change="handleChangeDetectUnit" >
            <el-option
              v-for="item in detectUnitDict"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <template  v-if="addHouseholdRate == true" >
          <el-form-item label="入户率要求（%）" label-width="130px" prop="householdRate">
              <el-input v-model="form.householdRate" placeholder="请输入入户率要求" />
          </el-form-item>
        </template>
        <el-form-item label="直观检测模板" label-width="130px" prop="templateId" filterable clearable>
          <el-select v-model="form.templateId" placeholder="请选择直观检测模板">
            <el-option
              v-for="item in templateDict"
              :key="item.id"
              :label="item.name"
              :value="item.id"
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
import { listProject, getProject, delProject, addProject, updateProject } from "@/api/project/project";
import { detectUnitDict } from "@/api/projectrole/DetectUnit";
import { queryTemplateDict } from "@/api/template/Template";

export default {
  name: "Project",
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
      // 项目表格数据
      projectList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      addHouseholdRate: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        type: null,
        detectId: null,
      },
      templateQuery: {
        type: null,
        detectId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        detectId: [
          { required: true, message: "请选择检测单位", trigger: "change" }
        ],
        name: [
          { required: true, message: "请输入项目名称", trigger: "blur" }
        ],
        type: [
          { required: true, message: "请选择项目类型", trigger: "change" }
        ],
        templateId: [
          { required: true, message: "请选择直观检测模板", trigger: "change" }
        ],
      },
      // 检测单位字典选项
      detectUnitDict: [],
      templateDict: []
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询项目列表 */
    getList() {
      this.loading = true;
      detectUnitDict().then(response => {
        this.detectUnitDict = response.data;
      });
      listProject(this.queryParams).then(response => {
        this.projectList = response.rows;
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
        type: null,
        detectId: null,
        detectName: null,
        householdRate: null,
        templateId: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
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
      this.templateDict = [];
      detectUnitDict().then(response => {
        this.detectUnitDict = response.data;
      });
      this.open = true;
      this.title = "添加项目";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      this.templateDict = [];
      detectUnitDict().then(response => {
        this.detectUnitDict = response.data;
      });
      getProject(id).then(response => {
        this.form = response.data;
        this.templateQuery.detectId = this.form.detectId;
        this.templateQuery.type = this.form.type;
        queryTemplateDict(this.templateQuery).then(response => {
          this.templateDict = response.data;
        });
        if(this.form.type == 'urban_village' || this.form.type == 'industrial_area'){
          this.addHouseholdRate = true;
        } else {
          this.addHouseholdRate = false;
        }
        this.open = true;
        this.title = "修改项目";
      });
    },
    handleChangeProjectType(value){
      if(value == 'urban_village' || value == 'industrial_area'){
        this.addHouseholdRate = true;
      } else {
        this.addHouseholdRate = false;
      }
      if(this.form.detectId != null && this.form.detectId != ''){
        this.templateQuery.type = value;
        queryTemplateDict(this.templateQuery).then(response => {
          this.templateDict = response.data;
        });
      }
      this.form.templateId = null;
    },
    handleChangeDetectUnit(value){
      if(value != null && value != ''){
        this.templateQuery.detectId = value;
        this.templateQuery.type = this.form.type;
        queryTemplateDict(this.templateQuery).then(response => {
          this.templateDict = response.data;
        });
      } else {
        this.templateDict = [];
      }
      this.form.templateId = null;
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProject(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProject(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除项目编号为"' + ids + '"的数据项？').then(function() {
        return delProject(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('project/project/export', {
        ...this.queryParams
      }, `project_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
