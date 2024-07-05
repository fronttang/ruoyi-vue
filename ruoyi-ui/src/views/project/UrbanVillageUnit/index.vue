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
      <el-table-column label="ID" align="center" width="60" prop="id" />
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="检测单位" align="center" prop="detectName" :formatter="detectFormat" :show-overflow-tooltip="true"/>
      <el-table-column label="区域" align="center" prop="area" :formatter="areaFormat" :show-overflow-tooltip="true" />
      <el-table-column label="管理员" align="center" prop="manager" width="100" :formatter="managerFormat" />
      <el-table-column label="网格员" align="center" prop="gridman" width="100"  :formatter="gridmanFormat" />
      <el-table-column label="检测地址" align="center" prop="address" :show-overflow-tooltip="true" />
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

    <!-- 添加或修改城中村电检对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
            <el-col :span="12">
              <el-form-item label="名称" label-width="100px" prop="name">
                <el-input v-model="form.name" placeholder="请输入名称" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="区域" label-width="100px" prop="area">
                <el-select v-model="form.area" placeholder="请选择区域" clearable filterable>
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
              <el-form-item label="项目名称" label-width="100px" prop="projectName">
                <el-input v-model="form.projectName" placeholder="请输入项目名称" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="委托单位" label-width="100px" prop="entrust">
                <el-input v-model="form.entrust" placeholder="请输入委托单位" />
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
              <el-form-item label="管理员" label-width="100px" prop="manager">
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
              <el-form-item label="网格员" label-width="100px" prop="gridman">
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
              <el-form-item label="检测地址" label-width="100px" prop="address">
                <el-input v-model="form.address" placeholder="请输入检测地址" />
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
              <el-form-item label="联系人" label-width="100px" prop="contact">
                <el-input v-model="form.contact" placeholder="请输入联系人" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="联系电话" label-width="100px" prop="phone">
                <el-input v-model="form.phone" placeholder="请输入联系电话" />
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
              <el-form-item label="建筑面积" label-width="100px" prop="acreage">
                <el-input v-model="form.acreage" placeholder="请输入建筑面积" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="建筑层数" label-width="100px" prop="layers">
                <el-input-number controls-position="right" v-model="form.layers" :min="0" />
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
              <el-form-item label="检测起止日期" label-width="100px" prop="testDate">
                <el-date-picker clearable
                  v-model="form.testDate"
                  type="daterange"
                  range-separator="-"
                  value-format="yyyy-MM-dd"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  size="small">
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="建筑使用性质" label-width="100px" prop="nature">
                <el-select v-model="form.nature" placeholder="请选择建筑使用性质" filterable>
                  <el-option
                    v-for="dict in dict.type.building_nature"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
              <el-form-item label="检测内容" label-width="100px" prop="testContent">
                <el-select v-model="form.testContent" placeholder="请选择检测内容" multiple filterable>
                  <el-option
                    v-for="dict in dict.type.detect_content"
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
import { getProject } from "@/api/project/project";
import { getProjectAreaDict, getProjectAreaDictByProjectId } from "@/api/project/ProjectArea";
import { getDetectUnitUserDictByTypeAndProjectId, getDetectUnitUserDict } from "@/api/projectrole/detectUnitUser";

export default {
  name: "OwnerUnit",
  dicts: ['detect_content', 'building_nature'],
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
      // 城中村电检表格数据
      OwnerUnitList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      projectInfo: {

      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        type: '1',
        detectId: null,
        detectName: null,
        projectId: this.$store.state.settings.projectId,
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
      gridmanDict: []
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询城中村电检列表 */
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
        type: '1',
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
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    detectFormat(row){
      return this.selectDictLabel(this.detectUnitDict, row.detectId);
    },
    managerFormat(row){
      return this.selectDictLabel(this.ownerUnitUserDict, row.manager);
    },
    gridmanFormat(row){
      return this.selectDictLabel(this.gridmanDict, row.gridman);
    },
    areaFormat(row) {
      return this.selectDictLabel(this.projectAreaDict, row.area);
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
      this.open = true;
      this.title = "添加城中村电检";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOwnerUnit(id).then(response => {
        this.form = response.data;
        if(this.form.testStartDate != null && this.form.testEndDate != null ){
          this.form.testDate = [this.form.testStartDate, this.form.testEndDate];
        }
        if(this.form.testContent != null) {
          this.form.testContent = this.form.testContent.split(',');
        }
        this.open = true;
        this.title = "修改城中村电检";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.testStartDate = this.form.testDate[0];
          this.form.testEndDate = this.form.testDate[1];
          this.form.testDate = this.form.testDate.join(",");
          this.form.testContent = this.form.testContent.join(",");
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
      this.$modal.confirm('是否确认删除城中村电检编号为"' + ids + '"的数据项？').then(function() {
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
