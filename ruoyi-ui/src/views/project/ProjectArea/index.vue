<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="区" prop="district">
        <el-select v-model="queryParams.district" placeholder="请选择区" clearable filterable>
          <el-option
            v-for="dict in districtOptions"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="街道" prop="street">
        <el-select v-model="queryParams.street" placeholder="请选择街道" clearable filterable>
          <el-option
            v-for="dict in streetOptions"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="社区" prop="community" v-if="projectType === '1' || projectType == '3'">
        <el-select v-model="queryParams.community" placeholder="请选择社区" clearable filterable>
          <el-option
            v-for="dict in communityOptions"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="村" prop="hamlet" v-if="projectType === '1'">
        <el-select v-model="queryParams.hamlet" placeholder="请选择村" clearable filterable>
          <el-option
            v-for="dict in hamletOptions"
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
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['project:ProjectArea:add']"
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
          v-hasPermi="['project:ProjectArea:edit']"
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
          v-hasPermi="['project:ProjectArea:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['project:ProjectArea:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ProjectAreaList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="项目名称" align="center" min-width="200" prop="projectName" :show-overflow-tooltip="true" />
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
      <el-table-column label="最后修改时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" align="center" width="160" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['project:ProjectArea:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['project:ProjectArea:remove']"
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

    <!-- 添加或修改项目区域对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="区" prop="district">
          <el-select v-model="form.district" placeholder="请选择区" clearable filterable>
            <el-option
              v-for="dict in districtOptions"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="街道" prop="street">
          <el-select v-model="form.street" placeholder="请选择街道" clearable filterable>
            <el-option
              v-for="dict in streetOptions"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="社区" prop="community" v-if="projectType === '1' || projectType == '3'">
          <el-select v-model="form.community" placeholder="请选择社区" clearable filterable>
            <el-option
              v-for="dict in communityOptions"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="村" prop="hamlet" v-if="projectType === '1'">
          <el-select v-model="form.hamlet" placeholder="请选择村" clearable filterable>
            <el-option
              v-for="dict in hamletOptions"
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
import { listProjectArea, getProjectArea, delProjectArea, addProjectArea, updateProjectArea } from "@/api/project/ProjectArea";
import { getProjectDict,getProject } from "@/api/project/project";
import { listProjectAreaDict } from "@/api/project/AreaDict";
import DictMeta from '@/utils/dict/DictMeta'

export default {
  name: "ProjectArea",
  dicts: ['hamlet', 'community', 'district', 'street'],
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
      // 项目区域表格数据
      ProjectAreaList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      districtOptions: [],
      streetOptions: [],
      communityOptions: [],
      hamletOptions: [],
      dictQuery: {
        dictType: null,
        projectId: this.$store.state.settings.projectId,
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        district: null,
        street: null,
        community: null,
        hamlet: null,
        projectId: this.$store.state.settings.projectId,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        district: [
          { required: true, message: "区不能为空", trigger: "change" }
        ],
        street: [
          { required: true, message: "街道不能为空", trigger: "change" }
        ],
        community: [
          { required: true, message: "社区不能为空", trigger: "change" }
        ],
        hamlet: [
          { required: true, message: "村不能为空", trigger: "change" }
        ],
      },
      projectDict: [],
      projectType: null
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询项目区域列表 */
    getList() {
      this.loading = true;

      getProject(this.queryParams.projectId).then(response => {
        this.projectType = response.data.type;
      });

      this.dictQuery.dictType = "district";
      listProjectAreaDict(this.dictQuery).then(response => {
        this.districtList = response.data;
        const dictMeta = DictMeta.parse("districtOptions");
        this.districtOptions = dictMeta.responseConverter(response.data, dictMeta);
      });

      this.dictQuery.dictType = "street";
      listProjectAreaDict(this.dictQuery).then(response => {
        const dictMeta = DictMeta.parse("streetOptions");
        this.streetOptions = dictMeta.responseConverter(response.data, dictMeta);
      });

      this.dictQuery.dictType = "community";
      listProjectAreaDict(this.dictQuery).then(response => {
        const dictMeta = DictMeta.parse("communityOptions");
        this.communityOptions = dictMeta.responseConverter(response.data, dictMeta);
      });

      this.dictQuery.dictType = "hamlet";
      listProjectAreaDict(this.dictQuery).then(response => {
        const dictMeta = DictMeta.parse("hamletOptions");
        this.hamletOptions = dictMeta.responseConverter(response.data, dictMeta);
      });

      listProjectArea(this.queryParams).then(response => {
        this.ProjectAreaList = response.rows;
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
        projectId: this.$store.state.settings.projectId,
        projectName: null,
        district: null,
        street: null,
        community: null,
        hamlet: null,
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
    districtFormat(row){
      return this.selectDictLabel(this.districtList, row.district);
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加项目区域";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getProjectArea(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改项目区域";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProjectArea(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProjectArea(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除项目区域编号为"' + ids + '"的数据项？').then(function() {
        return delProjectArea(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('project/ProjectArea/export', {
        ...this.queryParams
      }, `ProjectArea_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
