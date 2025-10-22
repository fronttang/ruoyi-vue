<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
      <el-form-item label="社区" prop="community">
        <el-select v-model="queryParams.community" placeholder="请选择社区"  filterable clearable>
          <el-option
            v-for="dict in communityOptions"
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
          v-hasPermi="['project:Industrial:add']"
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
          v-hasPermi="['project:Industrial:edit']"
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
          v-hasPermi="['project:Industrial:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['project:Industrial:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5" >
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['project:Industrial:import']"
        >导入</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="OwnerUnitList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" width="60" prop="id" />
      <el-table-column label="名称" align="center" prop="name" min-width="200" :show-overflow-tooltip="true" />
      <el-table-column label="检测单位" align="center" prop="detectName" min-width="200" :formatter="detectFormat" :show-overflow-tooltip="true"/>
      <el-table-column label="区域" align="center" prop="area" min-width="200" :formatter="areaFormat"  :show-overflow-tooltip="true"/>
      <el-table-column label="管理员" align="center" prop="manager"  width="100" :formatter="managerFormat" />
      <el-table-column label="网格员" align="center" prop="gridman"  width="100" :formatter="gridmanFormat" />
      <el-table-column label="检测地址" align="center" prop="address" min-width="200" :show-overflow-tooltip="true"/>
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
            v-hasPermi="['project:Industrial:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['project:Industrial:remove']"
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

    <!-- 添加或修改工业企业对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
            <el-col :span="12">
              <el-form-item label="名称" label-width="130px" prop="name">
                <el-input v-model="form.name" placeholder="请输入名称" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="区域" label-width="130px" prop="area">
                <el-select v-model="form.area" placeholder="请选择区域" filterable>
                  <el-option
                    v-for="item in projectAreaDict"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
              <el-form-item label="场所类型" label-width="130px" prop="venueType">
                <el-input v-model="form.venueType" placeholder="请输入场所类型" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="经营范围" label-width="130px" prop="businessScope">
                <el-input v-model="form.businessScope" placeholder="请输入经营范围" />
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
              <el-form-item label="管理员" label-width="130px" prop="manager">
                <el-select v-model="form.manager" placeholder="请选择管理员" filterable>
                  <el-option
                    v-for="item in ownerUnitUserDict"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="网格员" label-width="130px" prop="gridman">
                <el-select v-model="form.gridman" placeholder="请选择网格员" filterable>
                  <el-option
                    v-for="item in gridmanDict"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
              <el-form-item label="场所地址" label-width="130px" prop="address">
                <el-input v-model="form.address" placeholder="请输入场所地址" />
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
              <el-form-item label="员工人数（人）" label-width="130px" prop="staffs">
                <el-input-number controls-position="right" v-model="form.staffs" placeholder="请输入员工人数" :min="0" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="使用面积（M²）" label-width="130px" prop="acreage">
                <el-input v-model="form.acreage" placeholder="请输入使用面积" />
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
              <el-form-item label="有无证照" label-width="130px" prop="licence">
                <el-radio-group v-model="form.licence">
                  <el-radio label="1">有</el-radio>
                  <el-radio label="0">无</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="消防安全重点单位" label-width="130px" prop="fireSafetyUnit">
                <el-radio-group v-model="form.fireSafetyUnit">
                  <el-radio label="1">是</el-radio>
                  <el-radio label="0">否</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
              <el-form-item label="消防安全负责人" label-width="130px" prop="safetyIncharge">
                <el-input v-model="form.safetyIncharge" placeholder="请输入消防安全负责人" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="联系电话" label-width="130px" prop="safetyInchargePhone">
                <el-input v-model="form.safetyInchargePhone" placeholder="请输入联系电话" />
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
              <el-form-item label="消防安全管理人" label-width="130px" prop="safetyManager">
                <el-input v-model="form.safetyManager" placeholder="请输入消防安全管理人" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="联系电话" label-width="130px" prop="safetyManagerPhone">
                <el-input v-model="form.safetyManagerPhone" placeholder="请输入联系电话" />
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
              <el-form-item label="单位类型" label-width="130px" prop="unitType">
                <el-select v-model="form.unitType" placeholder="请选择单位类型" filterable>
                  <el-option
                    v-for="dict in dict.type.industrial_unit_type"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
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
import { listOwnerUnit, getOwnerUnit, delOwnerUnit, addOwnerUnit, updateOwnerUnit } from "@/api/project/OwnerUnit";
import { detectUnitDict } from "@/api/projectrole/DetectUnit";
import { getProject } from "@/api/project/project";
import { getProjectAreaDict, getProjectAreaDictByProjectId, getProjectAreaDictByProjectIdAndType } from "@/api/project/ProjectArea";
import { getDetectUnitUserDictByTypeAndProjectId, getDetectUnitUserDict } from "@/api/projectrole/detectUnitUser";
import DictMeta from '@/utils/dict/DictMeta'
import { getToken } from "@/utils/auth";

export default {
  name: "Industrial",
  dicts: ['detect_content', 'building_nature', 'industrial_unit_type'],
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
      // 工业企业表格数据
      OwnerUnitList: [],
      districtOptions: [],
      streetOptions: [],
      communityOptions: [],
      hamletOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      projectInfo: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        type: '3',
        detectId: null,
        detectName: null,
        projectId: this.$store.state.settings.projectId,
        projectName: null,
        area: null,
        district: null,
        street: null,
        community: null,
        hamlet: null,
        entrust: null,
        manager: null,
        gridman: null,
        address: null,
        contact: null,
        phone: null,
        acreage: null,
        layers: null,
        nature: null,
        testStartDate: null,
        testEndDate: null,
        testDate: null,
        testContent: null,
        doorNumber: null,
        buildman: null,
        unitType: null,
        incharge: null,
        venueType: null,
        safetyIncharge: null,
        safetyManager: null,
        staffs: null,
        licence: null,
        fireSafetyUnit: null,
        stationType: null,
        detectModule: null,
        operating: null,
        propertyType: null,
        panoramaPic: null,
        stationPic: null,
        highRiskType: '4',
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "请输入名称", trigger: "blur" }
        ],
        area: [
          { required: true, message: "请选择区域", trigger: "change" }
        ],
      },
      // 检测单位字典选项
      detectUnitDict: [],
      projectAreaDict: [],
      ownerUnitUserDict: [],
      gridmanDict: [],
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
        url: process.env.VUE_APP_BASE_API + "/project/OwnerUnit/import/" + this.$store.state.settings.projectId + "/4"
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询工业企业列表 */
    getList() {
      this.loading = true;
      detectUnitDict().then(response => {
        this.detectUnitDict = response.data;
      });
      getProject(this.queryParams.projectId).then(response => {
        this.projectInfo = response.data;
        this.form.projectName = this.projectInfo.name;
      });

      getProjectAreaDictByProjectId(this.queryParams.projectId).then(response => {
        this.projectAreaDict = response.data;
      });

      getDetectUnitUserDictByTypeAndProjectId('05', this.queryParams.projectId).then(response => {
        this.ownerUnitUserDict = response.data;
      });

      getDetectUnitUserDictByTypeAndProjectId('04', this.queryParams.projectId).then(response => {
        this.gridmanDict = response.data;
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

      listOwnerUnit(this.queryParams).then(response => {
        this.OwnerUnitList = response.rows;
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
        type: '3',
        detectId: null,
        detectName: null,
        projectId: this.$store.state.settings.projectId,
        projectName: this.projectInfo.name,
        area: null,
        entrust: null,
        manager: null,
        gridman: null,
        address: null,
        contact: null,
        phone: null,
        acreage: null,
        layers: null,
        nature: null,
        testStartDate: null,
        testEndDate: null,
        testDate: null,
        testContent: null,
        doorNumber: null,
        buildman: null,
        unitType: null,
        incharge: null,
        venueType: null,
        businessScope: null,
        safetyIncharge: null,
        safetyInchargePhone: null,
        safetyManager: null,
        safetyManagerPhone: null,
        staffs: null,
        licence: '1',
        fireSafetyUnit: '0',
        stationType: null,
        detectModule: null,
        operating: null,
        propertyType: null,
        panoramaPic: null,
        stationPic: null,
        highRiskType: '4',
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    detectFormat(row){
      return this.selectDictVoLabel(this.detectUnitDict, row.detectId);
    },
    managerFormat(row){
      return this.selectDictVoLabel(this.ownerUnitUserDict, row.manager);
    },
    gridmanFormat(row){
      return this.selectDictVoLabel(this.gridmanDict, row.gridman);
    },
    areaFormat(row) {
      var area = [];
      var districtName = this.selectDictLabel(this.districtOptions, row.district);
      var streetName = this.selectDictLabel(this.streetOptions, row.street);
      var communityName = this.selectDictLabel(this.communityOptions, row.community);
      //var hamletName = this.selectDictLabel(this.hamletOptions, row.hamlet);

      area.push(districtName);
      area.push(streetName);
      area.push(communityName);
      //area.push(hamletName);

      return area.join('/');
    },
    selectDictVoLabel(datas, value) {
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
      this.title = "添加工业企业";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOwnerUnit(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改工业企业";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOwnerUnit(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOwnerUnit(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除工业企业编号为"' + ids + '"的数据项？').then(function() {
        return delOwnerUnit(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('project/OwnerUnit/export', {
        ...this.queryParams
      }, `OwnerUnit_${new Date().getTime()}.xlsx`)
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
      if(response.code == 200) {
        var resultUrl = process.env.VUE_APP_BASE_API + response.data;
        this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'><a href='" + resultUrl + "' _href='blank' style='color: blue;' >下载结果文件</a></div>", "导入结果", { dangerouslyUseHTMLString: true });
      } else {
        this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
      }
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }
  }
};
</script>
