<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="区域类型" prop="dictType">
        <el-select v-model="queryParams.dictType" placeholder="请选择区域类型" filterable clearable>
          <el-option
            v-for="dict in areaOptions"
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
          v-hasPermi="['project:AreaDict:add']"
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
          v-hasPermi="['project:AreaDict:edit']"
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
          v-hasPermi="['project:AreaDict:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['project:AreaDict:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="AreaDictList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编码" align="center" prop="dictValue" />
      <el-table-column label="名称" align="center" prop="dictLabel" />
      <el-table-column label="类型" align="center" prop="dictType">
        <template slot-scope="scope">
          <dict-tag :options="areaOptions" :value="scope.row.dictType"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['project:AreaDict:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['project:AreaDict:remove']"
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

    <!-- 添加或修改区域字典对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="类型" prop="dictType">
          <el-select v-model="form.dictType" placeholder="请选择类型">
            <el-option
              v-for="dict in areaOptions"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="名称" prop="dictLabel">
          <el-input v-model="form.dictLabel" placeholder="请输入名称" />
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
import { listAreaDict, getAreaDict, delAreaDict, addAreaDict, updateAreaDict } from "@/api/project/AreaDict";
import { getProject } from "@/api/project/project";
import DictMeta from '@/utils/dict/DictMeta'
import { districtDictData,streetDictData,communityDictData,hamletDictData } from "@/api/project/AreaDict";

export default {
  name: "AreaDict",
  dicts: ['area_type'],
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
      // 区域字典表格数据
      AreaDictList: [],
      areaOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        dictType: null,
        projectId: this.$store.state.settings.projectId
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        dictLabel: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        dictType: [
          { required: true, message: "请选择类型", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询区域字典列表 */
    getList() {
      this.loading = true;
      this.areaOptions = [];
      getProject(this.queryParams.projectId).then(response => {
        let type = response.data.type;
        this.areaOptions.push(districtDictData);
        this.areaOptions.push(streetDictData);
        if(type === '1' || type === '3'){
          this.areaOptions.push(communityDictData);
        }
        if(type === '1') {
          this.areaOptions.push(hamletDictData);
        }
        const dictMeta = DictMeta.parse("areaOptions");
        this.areaOptions = dictMeta.responseConverter(this.areaOptions, dictMeta);
      });

      listAreaDict(this.queryParams).then(response => {
        this.AreaDictList = response.rows;
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
        dictCode: null,
        dictSort: null,
        dictLabel: null,
        dictValue: null,
        dictType: null,
        cssClass: null,
        listClass: 'default',
        isDefault: null,
        status: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        projectId: this.$store.state.settings.projectId
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
      this.ids = selection.map(item => item.dictCode)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加区域字典";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const dictCode = row.dictCode || this.ids
      getAreaDict(dictCode).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改区域字典";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.dictCode != null) {
            updateAreaDict(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAreaDict(this.form).then(response => {
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
      const dictCodes = row.dictCode || this.ids;
      this.$modal.confirm('是否确认删除区域字典名称为"' + row.dictLabel + '"的数据项？').then(function() {
        return delAreaDict(dictCodes);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('project/AreaDict/export', {
        ...this.queryParams
      }, `AreaDict_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
