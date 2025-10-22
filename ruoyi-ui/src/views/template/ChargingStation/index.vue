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
          v-hasPermi="['template:Template:edit']"
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
          v-hasPermi="['template:Template:edit']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['template:Template:edit']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5" >
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['template:Template:edit']"
        >导入</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="IntuitiveDetectDataList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" width="60" prop="id" />
      <el-table-column label="编号" align="center" width="60" prop="firstCode" />
      <el-table-column label="检测板块" align="center" width="160" prop="detectModule">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.detect_module" :value="scope.row.detectModule"/>
        </template>
      </el-table-column>
      <el-table-column label="内容" align="center" prop="firstContent" min-width="300" :show-overflow-tooltip="true" />
      <el-table-column label="最后修改时间" align="center" prop="updateTime" width="160">
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
            v-hasPermi="['template:Template:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['template:Template:edit']"
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
        <el-form-item label="检测板块" label-width="100px" prop="detectModule">
          <el-select v-model="form.detectModule" placeholder="请选择检测板块" >
            <el-option
              v-for="dict in dict.type.detect_module"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="编号/内容" label-width="100px" prop="firstCode">
          <el-input v-model="form.firstCode" placeholder="请输入编号" />
          <el-input v-model="form.firstContent" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="权重" label-width="100px" prop="weights">
          <el-input v-model="form.weights" placeholder="请输入权重" />
        </el-form-item>
        <el-form-item label="归属" label-width="100px" prop="attribution" v-if="this.form.detectModule == '6'">
          <el-checkbox-group type="" v-model="form.attribution">
            <el-checkbox key="非车载充电桩" label="非车载充电桩" value="非车载充电桩">非车载充电桩</el-checkbox>
            <el-checkbox key="交流充电桩" label="交流充电桩" value="交流充电桩">交流充电桩</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label-width="0px">
          <el-card class="box-card" shadow="always">
            <div slot="header" class="clearfix">
              <span>权重</span>
            </div>
            <div class="text item">
              <el-form-item label="集中式含储能" label-width="120px" prop="weightsCEs">
                <el-input v-model="form.weightsCEs" placeholder="请输入权重(集中式含储能)" />
              </el-form-item>
              <el-form-item label="集中式不含储能" label-width="120px" prop="weightsCNes">
                <el-input v-model="form.weightsCNes" placeholder="请输入权重(集中式不含储能)" />
              </el-form-item>
              <el-form-item label="分散式含储能" label-width="120px" prop="weightsDEs">
                <el-input v-model="form.weightsDEs" placeholder="请输入权重(分散式含储能)" />
              </el-form-item>
              <el-form-item label="分散式不含储能" label-width="120px" prop="weightsDNes">
                <el-input v-model="form.weightsDNes" placeholder="请输入权重(分散式不含储能)" />
              </el-form-item>
            </div>
          </el-card>
        </el-form-item>
        <template>
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
            </el-row>

            <el-table v-loading="loading" :data="IntuitiveDetectDangerList">
              <el-table-column label="隐患等级" align="center" width="80" prop="level">
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.hazard_level_charging_station" :value="scope.row.level"/>
                </template>
              </el-table-column>
              <el-table-column label="隐患描述" align="center" prop="description" :show-overflow-tooltip="true"/>
              <el-table-column label="整改建议" align="center" prop="suggestions" :show-overflow-tooltip="true"/>
              <el-table-column label="操作" align="center" width="160" class-name="small-padding fixed-width">
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
              v-for="dict in dict.type.hazard_level_charging_station"
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitDangerForm">确 定</el-button>
        <el-button @click="cancelDanger">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?delete=' + upload.delete"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.delete" /> 是否删除已经存在的模板数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listIntuitiveDetectData, getIntuitiveDetectData, delIntuitiveDetectData, addIntuitiveDetectData, updateIntuitiveDetectData } from "@/api/template/IntuitiveDetectData";
import { getIntuitiveDetectDict } from "@/api/template/IntuitiveDetect";
import { getTemplateDict } from "@/api/template/Template";
import { getToken } from "@/utils/auth";

export default {
  name: "IntuitiveDetectData",
  dicts: ['intuitive_detect_data_type', 'hazard_level_charging_station', 'detect_module'],
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
      // 弹出层标题
      title: "",
      titleDanger: "",
      // 是否显示弹出层
      open: false,
      openDanger: false,
      updateDanger: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        detectTitle: 0,
        type: null,
        detectModule: null,
        firstCode: null,
        firstContent: null,
        secondaryCode: null,
        secondaryContent: null,
        weights: null,
        output: null,
        templateId: parseInt(this.$route.params.templateId)
      },
      // 表单参数
      form: {},
      // 隐患参数
      danger: {},
      // 表单校验
      rules: {
        detectModule: [
          { required: true, message: "请选择检测板块", trigger: "change" }
        ],
      },
      dangerRules: {
        level: [
          { required: true, message: "请选择隐患等级", trigger: "change" }
        ],
        description: [
          { required: true, message: "请输入隐患描述", trigger: "blur" }
        ],
      },
      // 导入参数
      upload: {
        // 是否显示弹出层（导入）
        open: false,
        // 弹出层标题（导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否删除已经存在的数据
        delete: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/template/IntuitiveDetect/station/import/" + parseInt(this.$route.params.templateId)
      },
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
        type: '2',
        detectModule: null,
        firstCode: null,
        firstContent: null,
        secondaryCode: null,
        secondaryContent: null,
        weightsCEs: null,
        weightsCNes: null,
        weightsDEs: null,
        weightsDNes: null,
        attribution: [],
        dangers : [],
        weights: null,
        output: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
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
      getIntuitiveDetectDict(this.queryParams.templateId).then(response => {
        this.IntuitiveDetectList = response.data;
      });
      this.open = true;
      this.title = "添加直观检测项";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getIntuitiveDetectData(id).then(response => {
        this.form = response.data;
        this.IntuitiveDetectDangerList = response.dangers;
        this.open = true;
        this.title = "修改直观检测项";
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
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      //this.download('system/user/importTemplate', {
      //}, `user_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }
  }
};
</script>
