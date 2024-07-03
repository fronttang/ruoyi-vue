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
      <el-form-item label="模块类型" prop="moduleType">
        <el-select v-model="queryParams.moduleType" placeholder="请选择模块类型" filterable clearable>
          <el-option
            v-for="dict in dict.type.highfirerisk_template_module_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="业主单元类型" label-width="100px" prop="unitType">
        <el-select v-model="queryParams.unitType" placeholder="请选择业主单元类型" filterable clearable >
          <el-option
            v-for="dict in dict.type.high_risk_type"
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
      <el-table-column label="编号" align="center" width="60" prop="firstCode" />
      <el-table-column label="名称" align="center" prop="firstContent"  :show-overflow-tooltip="true"/>
      <el-table-column label="业主单元类型" align="center" width="100" prop="unitType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.high_risk_type" :value="scope.row.unitType"/>
        </template>
      </el-table-column>
      <el-table-column label="模块类型" align="center" width="80" prop="moduleType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.highfirerisk_template_module_type" :value="scope.row.moduleType"/>
        </template>
      </el-table-column>
      <el-table-column label="最高扣分数" align="center" width="100" prop="maxScore" />
      <el-table-column label="最后修改时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="120" class-name="small-padding fixed-width">
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
          <el-select v-model="form.unitType" placeholder="请选择业主单元类型" filterable @change="handleUnitType">
            <el-option
              v-for="dict in dict.type.high_risk_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="模块类型" label-width="120px" prop="moduleType">
          <el-select v-model="form.moduleType" placeholder="请选择模块类型" filterable @change="handleChangeModuleType">
            <el-option
              v-for="dict in dict.type.highfirerisk_template_module_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <template  v-if="addDanger == true" >
          <el-form-item label="上级模块" label-width="120px" prop="parentId">
            <el-select v-model="form.parentId" placeholder="请选择上级模块" filterable>
              <el-option
                v-for="item in IntuitiveDetectDataDict"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </template>
        <el-form-item label="编号/内容" label-width="120px" prop="firstCode">
          <el-input v-model="form.firstCode" placeholder="请输入编号" />
          <el-input v-model="form.firstContent" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="最高扣分数" label-width="120px" prop="maxScore">
          <el-input-number v-model="form.maxScore" placeholder="请输入最高扣分数" :min="0" controls-position="right" />
        </el-form-item>
        <template  v-if="addDanger == true" >
          <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <el-button
                  type="primary"
                  plain
                  icon="el-icon-plus"
                  size="mini"
                  @click="handleAddDanger"
                >新增</el-button>
              </el-col>
              </el-col>
            </el-row>

            <el-table v-loading="loading" :data="IntuitiveDetectDangerList">
              <el-table-column label="隐患等级" width="80" align="center" prop="level">
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.hazard_level_high" :value="scope.row.level"/>
                </template>
              </el-table-column>
              <el-table-column label="隐患描述" align="center" prop="description" :show-overflow-tooltip="true" />
              <el-table-column label="整改建议" align="center" prop="suggestions" :show-overflow-tooltip="true" />
              <el-table-column label="累计方式" align="center" width="80" prop="accMethod">
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.accumulation_method" :value="scope.row.accMethod"/>
                </template>
              </el-table-column>
              <el-table-column label="扣分数" align="center" width="60" prop="score" />
              <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-edit"
                    @click="handleUpdateDanger(scope.row)"
                  >修改</el-button>
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-delete"
                    @click="handleDeleteDanger(scope)"
                  >删除</el-button>
                </template>
              </el-table-column>
            </el-table>
        </template>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改检测内容隐患对话框 -->
    <el-dialog :title="titleDanger" :visible.sync="openDanger" width="500px" append-to-body>
      <el-form ref="formDanger" :model="danger" :rules="dangerRules" label-width="80px">
        <el-form-item label="隐患等级" prop="level">
          <el-select v-model="danger.level" placeholder="请选择隐患等级">
            <el-option
              v-for="dict in dict.type.hazard_level_high"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="隐患描述" prop="description">
          <el-input v-model="danger.description" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="整改建议" prop="suggestions">
          <el-input v-model="danger.suggestions" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="累计方式" prop="accMethod">
          <el-radio-group v-model="danger.accMethod">
            <el-radio
              v-for="dict in dict.type.accumulation_method"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="扣分数" prop="score">
          <el-input-number v-model="danger.score" placeholder="请输入扣分数" :min="0" controls-position="right" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitDangerForm">确 定</el-button>
        <el-button @click="cancelDanger">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listIntuitiveDetectData, getIntuitiveDetectData, delIntuitiveDetectData, addIntuitiveDetectData, updateIntuitiveDetectData, getIntuitiveDetectDataDict } from "@/api/template/IntuitiveDetectData";
import { getIntuitiveDetectDict } from "@/api/template/IntuitiveDetect";
import { getTemplateDict } from "@/api/template/Template";

export default {
  name: "IntuitiveDetectData",
  dicts: ['intuitive_detect_data_type', 'hazard_level_high', 'high_risk_type', 'highfirerisk_template_module_type', 'accumulation_method'],
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
      IntuitiveDetectDataDict: [],
      // 弹出层标题
      title: "",
      titleDanger: "",
      // 是否显示弹出层
      open: false,
      openDanger: false,
      addDanger : false,
      updateDanger: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        detectTitle: null,
        type: null,
        unitType: null,
        moduleType: null,
        detectModule: null,
        parentId: null,
        firstCode: null,
        firstContent: null,
        secondaryCode: null,
        secondaryContent: null,
        weights: null,
        output: null,
        maxScore: null,
        view: '2',
        templateId: parseInt(this.$route.params.templateId)
      },
      dictQuery: {},
      // 表单参数
      form: {},
      // 隐患参数
      danger: {},
      // 表单校验
      rules: {
        unitType: [
          { required: true, message: "请选择业主单元类型", trigger: "change" }
        ],
        moduleType: [
          { required: true, message: "请选择模块类型", trigger: "change" }
        ]
      },
      dangerRules: {
        level: [
          { required: true, message: "请选择隐患等级", trigger: "change" }
        ],
        description: [
          { required: true, message: "请输入隐患描述", trigger: "blur" }
        ]
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
    cancelDanger() {
      this.openDanger = false;
      this.resetDanger();
    },
    resetDanger() {
      this.danger = {
        id: null,
        dataId: null,
        level: null,
        description: null,
        suggestions: null,
        accMethod: '1',
        score: null,
        templateId: this.$route.params.templateId
      };
      //this.resetForm("formDanger");
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        detectTitle: 0,
        templateId: this.$route.params.templateId,
        type: null,
        unitType: null,
        moduleType: null,
        detectModule: null,
        parentId: null,
        firstCode: null,
        firstContent: null,
        secondaryCode: null,
        secondaryContent: null,
        weights: null,
        output: null,
        maxScore: null,
        view: '2',
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.addDanger = false;
      this.IntuitiveDetectDangerList = [];
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
      this.title = "添加计分模块";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getIntuitiveDetectData(id).then(response => {
        this.form = response.data;
        this.IntuitiveDetectDangerList = response.dangers;
        this.handleChangeModuleType(this.form.moduleType);
        this.open = true;
        this.title = "修改计分模块";
      });
    },
    handleChangeModuleType(value) {
      this.dictQuery = {
        templateId: this.$route.params.templateId,
        unitType: this.form.unitType,
        moduleType: 1
      }

      getIntuitiveDetectDataDict(this.dictQuery).then(response => {
        this.IntuitiveDetectDataDict = response.data;
      });

      if(value == '2'){
        this.addDanger = true;
      } else {
        this.addDanger = false;
      }
    },
    handleUnitType(value){
      this.dictQuery = {
        templateId: this.$route.params.templateId,
        unitType: value,
        moduleType: 1
      }

      getIntuitiveDetectDataDict(this.dictQuery).then(response => {
        this.IntuitiveDetectDataDict = response.data;
      });
    },
    detectTitleFormat(row) {
      return this.selectDictLabel(this.IntuitiveDetectList, row.detectTitle);
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
    /** 新增按钮操作 */
    handleAddDanger() {
      this.resetDanger();
      this.updateDanger = false,
      this.openDanger = true;
      this.titleDanger = "添加隐患";
    },
    /** 修改按钮操作 */
    handleUpdateDanger(row) {
      this.danger = row;
      this.openDanger = true;
      this.updateDanger = true;
      this.titleDanger = "修改隐患";
    },
    submitDangerForm() {
      this.$refs["formDanger"].validate(valid => {
        if (valid) {
          if(!this.updateDanger){
            this.IntuitiveDetectDangerList.push(this.danger);
          }
          this.$modal.msgSuccess("操作成功");
          this.openDanger = false;
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
        this.form.dangers = this.IntuitiveDetectDangerList;
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
