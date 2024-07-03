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
        <el-select v-model="queryParams.detectTitle" placeholder="请选择检测表" @change="handleChangeDetectTitle" filterable clearable>
          <el-option
            v-for="item in IntuitiveDetectList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="一级展示模块" label-width="100px" prop="viewParentId">
        <el-select v-model="queryParams.viewParentId" placeholder="请选择一级展示模块" filterable clearable>
          <el-option
            v-for="item in IntuitiveDetectDataDict"
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
      <el-table-column label="编号" align="center" width="60" prop="firstCode" />
      <el-table-column label="业主单元类型" align="center" width="100" prop="unitType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.high_risk_type" :value="scope.row.unitType"/>
        </template>
      </el-table-column>
      <el-table-column label="上级检测表" align="center" prop="detectTitle" :show-overflow-tooltip="true" :formatter="detectTitleFormat"/>
      <el-table-column label="一级展示模块" align="center" prop="viewParentId" :show-overflow-tooltip="true" :formatter="firstLevelFormat"/>
      <el-table-column label="二级计分模块" align="center" prop="firstContent" :show-overflow-tooltip="true"/>
      <el-table-column label="最后修改时间" align="center" prop="updateTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="160" class-name="small-padding fixed-width">
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
        <el-form-item label="检测表" label-width="120px" prop="detect_title">
          <el-select v-model="form.detect_title" placeholder="请选择检测表" filterable clearable @change="handleChangeDetectTitle">
            <el-option
              v-for="item in IntuitiveDetectList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="一级展示模块" label-width="120px" prop="viewParentId">
          <el-select v-model="form.viewParentId" placeholder="请选择一级展示模块" filterable clearable >
            <el-option
              v-for="item in IntuitiveDetectDataDict"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="二级计分模块" label-width="120px" prop="id">
          <el-select v-model="form.id" placeholder="请选择二级计分模块" filterable clearable>
            <el-option
              v-for="item in IntuitiveDetectDataScoreDict"
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
import { listIntuitiveDetectData, listIntuitiveDetectViewData, getIntuitiveDetectDataView, getIntuitiveDetectData, delIntuitiveDetectData, delIntuitiveDetectDataView, addIntuitiveDetectData, updateIntuitiveDetectData, getIntuitiveDetectDataDict } from "@/api/template/IntuitiveDetectData";
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
      IntuitiveDetectDataDict: [],
      IntuitiveDetectDataScoreDict: [],
      TemplateDict: [],
      AllIntuitiveDetect: [],
      AllIntuitiveDetectData: [],
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
        unitType: this.$route.params.unitType,
        type: null,
        firstCode: null,
        firstContent: null,
        secondaryCode: null,
        secondaryContent: null,
        weights: null,
        output: null,
        view: '2',
        viewParentId: parseInt(this.$route.params.parentId),
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        detect_title: [
          { required: true, message: "请选择检测表", trigger: "change" }
        ],
        unitType: [
          { required: true, message: "请选择业主单元类型", trigger: "change" }
        ],
        viewParentId: [
          { required: true, message: "请选择一级展示模块", trigger: "change" }
        ],
        id: [
          { required: true, message: "请选择二级计分模块", trigger: "change" }
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

      listIntuitiveDetectDict(this.queryDetectDict).then(response => {
        this.IntuitiveDetectList = response.data;
      });

      getIntuitiveDetectDict(this.queryParams.templateId).then(response => {
        this.AllIntuitiveDetect = response.data;
      });

      this.dictQuery = {
        templateId: this.queryParams.templateId,
        detectTitle: this.queryParams.detectTitle,
        unitType: this.queryParams.unitType,
        view: 1
      }
      getIntuitiveDetectDataDict(this.dictQuery).then(response => {
        this.IntuitiveDetectDataDict = response.data;
      });

      this.AlldataQuery = {
        templateId: parseInt(this.$route.params.templateId),
      }
      getIntuitiveDetectDataDict(this.AlldataQuery).then(response => {
        this.AllIntuitiveDetectData = response.data;
      });

      listIntuitiveDetectViewData(this.queryParams).then(response => {
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
        detect_title: null,
        templateId: parseInt(this.$route.params.templateId),
        viewParentId: parseInt(this.$route.params.parentId),
        unitType: this.$route.params.unitType,
        type: null,
        firstCode: null,
        firstContent: null,
        secondaryCode: null,
        secondaryContent: null,
        dangers : [],
        weights: null,
        output: null,
        view: '2',
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
      this.ids = selection.map(item => item.dangerId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    handleChangeUnitType(value) {

      this.queryDetectDict.unitType = value;
      this.queryParams.detectTitle = null;
      this.queryParams.viewParentId = null;
      //this.form.detect_title = null;
      //this.form.viewParentId = null;
      //this.form.id = null;


      listIntuitiveDetectDict(this.queryDetectDict).then(response => {
        this.IntuitiveDetectList = response.data;
      });

      this.IntuitiveDetectDataDict = [];

      this.dictQuery = {
        templateId: this.form.templateId,
        unitType: value,
        moduleType: 2,
        view: 2
      }
      getIntuitiveDetectDataDict(this.dictQuery).then(response => {
        this.IntuitiveDetectDataScoreDict = response.data;
      });
    },
    handleChangeDetectTitle(value) {
      this.dictQuery = {
        templateId: this.queryParams.templateId,
        detectTitle: value,
        unitType: this.queryParams.unitType,
        view: 1
      }
      this.queryParams.viewParentId = null;
      getIntuitiveDetectDataDict(this.dictQuery).then(response => {
        this.IntuitiveDetectDataDict = response.data;
      });
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.queryDetectDict.unitType = this.queryParams.unitType;
      this.form.unitType = this.queryParams.unitType;
      listIntuitiveDetectDict(this.queryDetectDict).then(response => {
        this.IntuitiveDetectList = response.data;
      });
      this.form.detect_title = this.queryParams.detectTitle;
      this.form.viewParentId = this.queryParams.viewParentId;

      this.dictQuery = {
        templateId: this.form.templateId,
        unitType: this.form.unitType,
        moduleType: 2,
        view: 2
      }
      getIntuitiveDetectDataDict(this.dictQuery).then(response => {
        this.IntuitiveDetectDataScoreDict = response.data;
      });

      this.open = true;
      this.title = "添加二级展示模块";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids

      getIntuitiveDetectDataView(id).then(response => {
        this.form = response.data;
        this.form.detect_title = this.form.detectTitle;

        this.handleChangeUnitType(this.form.unitType);
        this.handleChangeDetectTitle(this.form.detectTitle);

        this.open = true;
        this.title = "修改二级展示模块";
      });
    },
    detectTitleFormat(row) {
      return this.selectDictLabel(this.AllIntuitiveDetect, row.detectTitle);
    },
    firstLevelFormat(row){
      return this.selectDictLabel(this.AllIntuitiveDetectData, row.viewParentId);
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
    handleSecondView(row){
      const templateId = row.templateId;
      const detectId = row.id;
      const params = {};
      this.$tab.openPage("二级展示模块", '/template/HighFireRisk/view/firstlevel/' + templateId + '/' + detectId, params);
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
      this.$modal.confirm('是否确认删除二级模块编号为"' + ids + '"的数据项？').then(function() {
        return delIntuitiveDetectDataView(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
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
