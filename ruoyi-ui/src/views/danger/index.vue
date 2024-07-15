<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称/隐患位置" prop="location" label-width="120px">
        <el-input
          v-model="queryParams.location"
          placeholder="请输入名称/隐患位置"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status" >
        <el-select v-model="queryParams.status" placeholder="请选择状态" filterable clearable>
          <el-option
            v-for="dict in dict.type.again_test_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="业主单元类型" label-width="100px" prop="highRiskType"  v-if="projectType === '3'">
        <el-select v-model="queryParams.highRiskType" placeholder="请选择业主单元类型" filterable clearable>
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
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dangerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" width="60" prop="id" />
      <el-table-column label="业主单元" align="center" prop="unitName" min-width="200" :show-overflow-tooltip="true" />
      <el-table-column label="业主单元类型" width="100" align="center" prop="highRiskType" v-if="projectType === '3'">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.high_risk_type" :value="scope.row.highRiskType"/>
        </template>
      </el-table-column>
      <el-table-column label="隐患位置" align="center" prop="location" min-width="200" :show-overflow-tooltip="true" />
      <el-table-column label="隐患等级" align="center" prop="level" width="80" :formatter="dangerLevelFormat"  />
      <el-table-column label="隐患描述" align="center" prop="description" min-width="200" :show-overflow-tooltip="true" />
      <el-table-column label="状态" align="center" prop="status" width="120" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.again_test_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="检测员" align="center" width="120" prop="inspector" />
      <el-table-column label="初检时间" align="center" prop="initialTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.initialTime, '{y}-{m}-{d}') }}</span>
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
  </div>
</template>

<script>
import { listDanger} from "@/api/danger/danger";
import { getProject } from "@/api/project/project";

export default {
  name: "OwnerUnit",
  dicts: ['high_risk_type', 'again_test_status', 'hazard_level', 'hazard_level_high', 'hazard_level_charging_station'],
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
      // 报告表格数据
      dangerList: [],
      detectUnitDict: [],
      districtOptions: [],
      streetOptions: [],
      communityOptions: [],
      hamletOptions: [],
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
        location: null,
        highRiskType: null,
        projectId: this.$store.state.settings.projectId,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      projectType: null
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {

      this.loading = true;

      getProject(this.queryParams.projectId).then(response => {
        this.projectInfo = response.data;
        this.projectType = response.data.type;
      });

      listDanger(this.queryParams).then(response => {
        this.dangerList = response.rows;
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
      };
      this.resetForm("form");
    },
    detectFormat(row){
      return this.selectDictVoLabel(this.detectUnitDict, row.detectId);
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
    dangerLevelFormat(row){
      if(row.level == null || row.level == ''){
        return "/";
      }
      if(this.projectType === '3') {
        return this.selectDictLabel(this.dict.type.hazard_level_high, row.level);
      } else if(this.projectType === '4'){
        return this.selectDictLabel(this.dict.type.hazard_level_charging_station, row.level);
      } else {
        return this.selectDictLabel(this.dict.type.hazard_level, row.level);
      }
    }
  }
};
</script>
