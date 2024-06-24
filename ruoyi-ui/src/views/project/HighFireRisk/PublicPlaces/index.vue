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
          v-hasPermi="['project:OwnerUnit:add']"
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
          v-hasPermi="['project:OwnerUnit:edit']"
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
          v-hasPermi="['project:OwnerUnit:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['project:OwnerUnit:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="OwnerUnitList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="检测单位" align="center" prop="detectName" :formatter="detectFormat"/>
      <el-table-column label="区域" align="center" prop="area" :formatter="areaFormat" />
      <el-table-column label="管理员" align="center" prop="manager" :formatter="managerFormat" />
      <el-table-column label="网格员" align="center" prop="gridman" :formatter="gridmanFormat" />
      <el-table-column label="检测地址" align="center" prop="address" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['project:OwnerUnit:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['project:OwnerUnit:remove']"
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

    <!-- 添加或修改公共场所对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="检测单位" label-width="130px" prop="detectId">
              <el-select v-model="form.detectId" placeholder="请选择检测单位" filterable
                @change="handleDetectUnitChange"
              >
                <el-option
                  v-for="item in detectUnitDict"
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
              <el-form-item label="消防安全重点单位" label-width="130px" prop="safetyKeyUnit">
                <el-radio-group v-model="form.safetyKeyUnit">
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
                    v-for="dict in dict.type.public_places_unit_type"
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
  </div>
</template>

<script>
import { listOwnerUnit, getOwnerUnit, delOwnerUnit, addOwnerUnit, updateOwnerUnit } from "@/api/project/OwnerUnit";
import { detectUnitDict } from "@/api/projectrole/DetectUnit";
import { getProjectAreaDict, getProjectAreaDictByProjectId } from "@/api/project/ProjectArea";
import { getDetectUnitUserDictByType, getDetectUnitUserDict } from "@/api/projectrole/detectUnitUser";

export default {
  name: "OwnerUnit",
  dicts: ['detect_content', 'building_nature', 'public_places_unit_type'],
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
      // 公共场所表格数据
      OwnerUnitList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        type: '3',
        detectId: null,
        detectName: null,
        projectId: null,
        projectName: null,
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
        safetyIncharge: null,
        safetyManager: null,
        staffs: null,
        licence: null,
        safetyKeyUnit: null,
        stationType: null,
        detectModule: null,
        operating: null,
        propertyType: null,
        panoramaPic: null,
        stationPic: null,
        highRiskType: '5',
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      // 检测单位字典选项
      detectUnitDict: [],
      projectAreaDict: [],
      ownerUnitUserDict: [],
      gridmanDict: [],
      allUser: [],
      allProjectArea: []
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询公共场所列表 */
    getList() {
      this.loading = true;
      detectUnitDict().then(response => {
        this.detectUnitDict = response.data;
      });
      getProjectAreaDict().then(response => {
        this.allProjectArea = response.data;
      });
      getDetectUnitUserDict().then(response => {
        this.allUser = response.data;
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
        projectId: null,
        projectName: null,
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
        safetyKeyUnit: '0',
        stationType: null,
        detectModule: null,
        operating: null,
        propertyType: null,
        panoramaPic: null,
        stationPic: null,
        highRiskType: '5',
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.projectAreaDict = [],
      this.ownerUnitUserDict = [],
      this.gridmanDict = [],
      this.resetForm("form");
    },
    detectFormat(row){
      return this.selectDictLabel(this.detectUnitDict, row.detectId);
    },
    managerFormat(row){
      return this.selectDictLabel(this.allUser, row.manager);
    },
    gridmanFormat(row){
      return this.selectDictLabel(this.allUser, row.gridman);
    },
    areaFormat(row) {
      return this.selectDictLabel(this.allProjectArea, row.area);
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
      detectUnitDict().then(response => {
        this.detectUnitDict = response.data;
      });
      getProjectAreaDictByProjectId(1).then(response => {
        this.projectAreaDict = response.data;
      });
      this.open = true;
      this.title = "添加公共场所";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      detectUnitDict().then(response => {
        this.detectUnitDict = response.data;
      });
      getProjectAreaDictByProjectId(1).then(response => {
        this.projectAreaDict = response.data;
      });
      const id = row.id || this.ids
      getOwnerUnit(id).then(response => {
        this.form = response.data;
        this.handleDetectUnitChange(this.form.detectId);
        this.open = true;
        this.title = "修改公共场所";
      });
    },
    handleDetectUnitChange(value) {
      getDetectUnitUserDictByType('05', value).then(response => {
        this.ownerUnitUserDict = response.data;
      });
      getDetectUnitUserDictByType('04', value).then(response => {
        this.gridmanDict = response.data;
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
      this.$modal.confirm('是否确认删除公共场所编号为"' + ids + '"的数据项？').then(function() {
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
    }
  }
};
</script>
