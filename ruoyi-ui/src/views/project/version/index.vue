<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="版本名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入版本名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户端" prop="client">
        <el-select v-model="queryParams.client" placeholder="请选择客户端" clearable>
          <el-option
            v-for="dict in dict.type.client_type"
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
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['project:version:add']"
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
          v-hasPermi="['project:version:edit']"
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
          v-hasPermi="['project:version:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['project:version:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="versionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" width="60" />
      <el-table-column label="客户端" align="center" prop="client" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.client_type" :value="scope.row.client"/>
        </template>
      </el-table-column>
      <el-table-column label="版本名称" align="center" prop="name" min-width="200" :show-overflow-tooltip="true"  />
      <el-table-column label="版本代码" align="center" prop="version" min-width="100" :show-overflow-tooltip="true" />
      <el-table-column label="强制更新" align="center" prop="forced" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.forced"
            active-value="1"
            inactive-value="0"
            disabled
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="updateTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right"  width="160" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['project:version:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['project:version:remove']"
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

    <!-- 添加或修改版本更新对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="客户端" prop="client">
          <el-select v-model="form.client" placeholder="请选择客户端">
            <el-option
              v-for="dict in dict.type.client_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="版本名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入版本名称" />
        </el-form-item>
        <el-form-item label="版本代码" prop="version">
          <el-input v-model="form.version" placeholder="请输入版本代码,代码大于上次代码才能激活更新" />
        </el-form-item>
        <el-form-item label="强制更新" prop="forced">
          <el-switch v-model="form.forced" active-value="1" inactive-value="0" />
        </el-form-item>
        <el-form-item label="安装包" prop="downloadUrl">
          <file-upload v-model="form.downloadUrl" :uploadFileUrl="uploadFileUrl" :isShowTip="false" :fileSize="fileSize" :fileType="fileType" :limit="limit" />
        </el-form-item>
        <el-form-item label="更新内容" prop="content">
          <el-input v-model="form.content" :min-height="192" type="textarea" placeholder="请输入更新内容" />
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
import { listVersion, getVersion, delVersion, addVersion, updateVersion } from "@/api/project/version";

export default {
  name: "Version",
  dicts: ['client_type'],
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
      // 版本更新表格数据
      versionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      fileSize: null,
      fileType: null,
      limit: 1,
      uploadFileUrl: process.env.VUE_APP_BASE_API + "/common/version/upload",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        client: null,
        name: null,
        version: null,
        forced: null,
        content: null,
        downloadUrl: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        client: [
          { required: true, message: "客户端不能为空", trigger: "change" }
        ],
        name: [
          { required: true, message: "版本名称不能为空", trigger: "blur" }
        ],
        version: [
          { required: true, message: "版本代码不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询版本更新列表 */
    getList() {
      this.loading = true;
      listVersion(this.queryParams).then(response => {
        this.versionList = response.rows;
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
        client: null,
        name: null,
        version: null,
        forced: '0',
        content: null,
        downloadUrl: null,
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
      this.open = true;
      this.title = "添加版本更新";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getVersion(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改版本更新";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateVersion(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addVersion(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除版本更新编号为"' + ids + '"的数据项？').then(function() {
        return delVersion(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('project/version/export', {
        ...this.queryParams
      }, `version_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
