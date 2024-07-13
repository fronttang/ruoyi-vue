<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="模板" prop="templateId">
        <el-select v-model="queryParams.templateId" disabled >
          <el-option
            v-for="item in TemplateDict"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="业主单元类型" label-width="100px" prop="unitType">
        <el-select v-model="queryParams.unitType" placeholder="请选择业主单元类型" @change="handleChangeUnitType" filterable clearable>
          <el-option
            v-for="dict in dict.type.high_risk_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="检测表标题" label-width="100px" prop="detectTitle">
        <el-select v-model="queryParams.detectTitle" placeholder="请选择检测表" filterable clearable>
          <el-option
            v-for="item in IntuitiveDetectList"
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
          v-hasPermi="['template:IntuitiveDetectData:add']"
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
          v-hasPermi="['template:IntuitiveDetectData:edit']"
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
          v-hasPermi="['template:IntuitiveDetectData:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['template:IntuitiveDetectData:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="IntuitiveDetectDataList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" width="60" prop="id" />
      <el-table-column label="上级检测表" align="center" prop="detectTitle" min-width="300" :show-overflow-tooltip="true" :formatter="detectTitleFormat"/>
      <el-table-column label="编号" align="center"  width="60" prop="firstCode" />
      <el-table-column label="内容" align="center" prop="firstContent" min-width="300" :show-overflow-tooltip="true" />
      <el-table-column label="业主单元类型" align="center"  width="100"  prop="unitType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.high_risk_type" :value="scope.row.unitType"/>
        </template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="updateTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" align="center" width="220" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['template:IntuitiveDetectData:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleSecondView(scope.row)"
          >二级展示模块</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['template:IntuitiveDetectData:remove']"
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

    <!-- 添加或修改直观检测表内容对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="业主单元类型" label-width="120px" prop="unitType">
          <el-select v-model="form.unitType" placeholder="请选择业主单元类型" @change="handleChangeUnitType" filterable clearable>
            <el-option
              v-for="dict in dict.type.high_risk_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="检测表" label-width="120px" prop="detectTitle">
          <el-select v-model="form.detectTitle" placeholder="请选择检测表" filterable>
            <el-option
              v-for="item in IntuitiveDetectList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="编号/内容" label-width="120px" prop="firstCode">
          <el-input v-model="form.firstCode" placeholder="请输入一级编号" />
          <el-input v-model="form.firstContent" type="textarea" placeholder="请输入内容" />
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
import { listIntuitiveDetectData, getIntuitiveDetectData, delIntuitiveDetectData, addIntuitiveDetectData, updateIntuitiveDetectData } from "@/api/template/IntuitiveDetectData";
import { getIntuitiveDetectDict, listIntuitiveDetectDict } from "@/api/template/IntuitiveDetect";
import { getTemplateDict } from "@/api/template/Template";

export default {
  name: "IntuitiveDetectData",
  dicts: ['intuitive_detect_data_type', 'hazard_level', 'high_risk_type'],
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
      // 直观检测表内容表格数据
      IntuitiveDetectDataList: [],
      IntuitiveDetectList: [],
      // 检测内容隐患表格数据
      IntuitiveDetectDangerList: [],
      TemplateDict: [],
      AllIntuitiveDetect: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        detectTitle: parseInt(this.$route.params.detectId),
        templateId: parseInt(this.$route.params.templateId),
        type: null,
        unitType: this.$route.params.unitType,
        firstCode: null,
        firstContent: null,
        secondaryCode: null,
        secondaryContent: null,
        weights: null,
        output: null,
        view: '1'
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        detectTitle: [
          { required: true, message: "请选择检测表", trigger: "change" }
        ],
        unitType: [
          { required: true, message: "请选择业主单元类型", trigger: "change" }
        ],
      },
      queryDetectDict: {
        templateId: parseInt(this.$route.params.templateId),
        unitType: this.$route.params.unitType
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询直观检测表内容列表 */
    getList() {
      this.loading = true;
      getTemplateDict().then(response => {
        this.TemplateDict = response.data;
      });
      getIntuitiveDetectDict(this.queryParams.templateId).then(response => {
        this.AllIntuitiveDetect = response.data;
      });
      listIntuitiveDetectDict(this.queryDetectDict).then(response => {
        this.IntuitiveDetectList = response.data;
      });
      listIntuitiveDetectData(this.queryParams).then(response => {
        this.IntuitiveDetectDataList = response.rows;
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
        detectTitle: null,
        templateId: this.$route.params.templateId,
        unitType: this.$route.params.unitType,
        type: null,
        firstCode: null,
        firstContent: null,
        secondaryCode: null,
        secondaryContent: null,
        dangers : [],
        weights: null,
        output: null,
        view: '1',
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
      this.queryDetectDict.unitType = this.queryParams.unitType;
      this.form.unitType = this.queryParams.unitType;
      this.form.detectTitle = this.queryParams.detectTitle;
      listIntuitiveDetectDict(this.queryDetectDict).then(response => {
        this.IntuitiveDetectList = response.data;
      });

      //this.handleChangeUnitType(this.form.unitType);
      this.form.detectTitle = this.queryParams.detectTitle;

      this.open = true;
      this.title = "添加一级展示模块";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getIntuitiveDetectData(id).then(response => {
        this.form = response.data;
        listIntuitiveDetectDict(this.queryDetectDict).then(response => {
          this.IntuitiveDetectList = response.data;
        });
        this.open = true;
        this.title = "修改一级展示模块";
      });
    },
    detectTitleFormat(row) {
      return this.selectDictLabel(this.AllIntuitiveDetect, row.detectTitle);
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
    handleChangeUnitType(value) {
      this.queryDetectDict.unitType = value;
      this.queryParams.detectTitle = null;
      this.form.detectTitle = null;
      listIntuitiveDetectDict(this.queryDetectDict).then(response => {
        this.IntuitiveDetectList = response.data;
      });
    },
    handleSecondView(row){
      const templateId = row.templateId;
      const unitType = row.unitType;
      const detectId = row.detectTitle;
      const parentId = row.id;
      const params = {};
      this.$tab.openPage("二级展示模块", '/template/HighFireRisk/view/secondlevel/' + templateId + '/' + unitType + '/' + detectId + '/' + parentId, params);
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateIntuitiveDetectData(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addIntuitiveDetectData(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除直观检测表内容编号为"' + ids + '"的数据项？').then(function() {
        return delIntuitiveDetectData(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 删除按钮操作 */
    handleDeleteDanger(scope) {
      this.IntuitiveDetectDangerList.splice(scope.$index, 1);
      this.$modal.msgSuccess("删除成功");
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('template/IntuitiveDetectData/export', {
        ...this.queryParams
      }, `IntuitiveDetectData_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
