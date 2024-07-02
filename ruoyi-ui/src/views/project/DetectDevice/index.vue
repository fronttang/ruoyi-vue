<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择类型" clearable>
          <el-option
            v-for="dict in dict.type.detect_device_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="检测单位" prop="detectId">
        <el-select v-model="queryParams.detectId" placeholder="请选择检测单位" clearable>
          <el-option
            v-for="item in detectUnitDict"
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
          v-hasPermi="['project:DetectDevice:add']"
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
          v-hasPermi="['project:DetectDevice:edit']"
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
          v-hasPermi="['project:DetectDevice:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['project:DetectDevice:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="DetectDeviceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="检测单位" align="center" prop="detectName" :show-overflow-tooltip="true" />
      <el-table-column label="仪器编号" align="center" prop="deviceId" />
      <el-table-column label="类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.detect_device_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="仪器名称" align="center" prop="name" />
      <el-table-column label="校准日期" align="center" prop="calibrationDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.calibrationDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['project:DetectDevice:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['project:DetectDevice:remove']"
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

    <!-- 添加或修改检测仪器对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="检测单位" prop="detectId">
          <el-select v-model="form.detectId" placeholder="请选择检测单位">
            <el-option
              v-for="item in detectUnitDict"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="仪器类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择仪器类型">
            <el-option
              v-for="dict in dict.type.detect_device_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="仪器编号" prop="deviceId">
          <el-input v-model="form.deviceId" placeholder="请输入仪器编号" />
        </el-form-item>
        <el-form-item label="仪器名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入仪器名称" />
        </el-form-item>
        <el-form-item label="校准日期" prop="calibrationDate">
          <el-date-picker clearable
            v-model="form.calibrationDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择校准日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="是否过期" prop="isExpired">
          <el-radio-group v-model="form.isExpired">
            <el-radio label="0">未过期</el-radio>
            <el-radio label="1">已过期</el-radio>
          </el-radio-group>
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
import { listDetectDevice, getDetectDevice, delDetectDevice, addDetectDevice, updateDetectDevice } from "@/api/project/DetectDevice";
import { detectUnitDict } from "@/api/projectrole/DetectUnit";

export default {
  name: "DetectDevice",
  dicts: ['detect_device_type'],
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
      // 检测仪器表格数据
      DetectDeviceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        detectId: null,
        deviceId: null,
        type: null,
        name: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        detectId: [
          { required: true, message: "请选择检测单位", trigger: "change" }
        ],
        type: [
          { required: true, message: "请选择仪器类型", trigger: "change" }
        ],
      },
      // 检测单位字典选项
      detectUnitDict: [],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询检测仪器列表 */
    getList() {
      this.loading = true;
      detectUnitDict().then(response => {
        this.detectUnitDict = response.data;
      });
      listDetectDevice(this.queryParams).then(response => {
        this.DetectDeviceList = response.rows;
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
        detectId: null,
        detectName: null,
        deviceId: null,
        type: null,
        name: null,
        calibrationDate: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        isExpired: '0'
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
      detectUnitDict().then(response => {
        this.detectUnitDict = response.data;
      });
      this.open = true;
      this.title = "添加检测仪器";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      detectUnitDict().then(response => {
        this.detectUnitDict = response.data;
      });
      const id = row.id || this.ids
      getDetectDevice(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改检测仪器";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDetectDevice(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDetectDevice(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除检测仪器编号为"' + ids + '"的数据项？').then(function() {
        return delDetectDevice(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('project/DetectDevice/export', {
        ...this.queryParams
      }, `DetectDevice_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
