<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="姓名/账号" prop="account" label-width="100px">
        <el-input
          v-model="queryParams.account"
          placeholder="请输入姓名/账号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="绑定类型" prop="bindType">
        <el-select v-model="queryParams.bindType" placeholder="请选择绑定类型" clearable>
          <el-option
            v-for="dict in dict.type.project_worker_bind_type"
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
        <el-dropdown size="mini" @command="handleCommand">
          <el-button size="mini" type="primary" plain icon="el-icon-plus">绑定人员<i class="el-icon-arrow-down el-icon--right"></i></el-button>
          <el-dropdown-menu slot="dropdown">
              <el-dropdown-item v-for="type in dict.type.project_worker_bind_type" :command="type.value" >{{type.label}}</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="multiple"
          @click="handleBatchBindArea"
        >批量绑定区域</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="multiple"
          @click="handleBatchEditArea"
        >批量编辑权限</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['project:ProjectWorker:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['project:ProjectWorker:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ProjectWorkerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" width="60" prop="id" />
      <el-table-column label="检测单位" align="center" prop="detectName" :show-overflow-tooltip="true" />
      <el-table-column label="姓名" align="center" width="120" prop="nickName" :show-overflow-tooltip="true"/>
      <el-table-column label="账号" align="center" width="120" prop="userName" :show-overflow-tooltip="true"/>
      <el-table-column label="绑定类型" align="center" width="80" prop="bindType" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.project_worker_bind_type" :value="scope.row.bindType"/>
        </template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="updateTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleBindArea(scope.row, '1')"
          >绑定区域</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleBindArea(scope.row, '2')"
          >编辑权限</el-button>
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

    <!-- 绑定工作人员对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form :model="queryUserParams" ref="queryUserForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="姓名/账号" prop="account" label-width="100px">
          <el-input
            v-model="queryUserParams.account"
            placeholder="请输入姓名/账号"
            clearable
            @keyup.enter.native="handleUserQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleUserQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetUserQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="success"
            plain
            icon="el-icon-edit"
            size="mini"
            :disabled="userSelected"
            @click="submitForm"
          >绑定</el-button>
        </el-col>
      </el-row>
      <el-table v-loading="loading" :data="detectUnitUserList" @selection-change="handleSelectionChangeUser">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="ID" align="center" width="60" prop="id" />
        <el-table-column label="姓名" align="center" prop="name" />
        <el-table-column label="账号" align="center" prop="account" />
      </el-table>
      <pagination
        v-show="userTotal>0"
        :total="userTotal"
        :page.sync="queryUserParams.pageNum"
        :limit.sync="queryUserParams.pageSize"
        @pagination="getUserList"
      />
    </el-dialog>

    <!-- 绑定区域对话框 -->
    <el-dialog :title="areaTitle" :visible.sync="areaOpen" width="600px" append-to-body>
      <el-form ref="areaForm" :model="areaForm" :rules="rules" label-width="100px">
        <el-form-item label="区域">
          <el-input placeholder="输入关键字进行过滤" v-model="filterText">
          </el-input>
          <el-tree
            class="filter-tree"
            :data="areaOptions"
            show-checkbox
            default-expand-all
            ref="area"
            node-key="id"
            :check-strictly="checkStrictly"
            empty-text="加载中，请稍候"
            :props="defaultProps"
            :filter-node-method="filterNode"
          ></el-tree>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitAreaForm">确 定</el-button>
        <el-button @click="cancelArea">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listProjectWorker, getProjectWorker, delProjectWorker, addProjectWorker, updateProjectWorker, saveProjectWorkerArea } from "@/api/project/ProjectWorker";
import { listDetectUnitUser} from "@/api/projectrole/detectUnitUser";
import { getProject } from "@/api/project/project";
import { getProjectAreaTree } from "@/api/project/ProjectArea";


export default {
  name: "ProjectWorker",
  dicts: ['project_worker_bind_type'],
  watch: {
    filterText(val) {
      this.$refs.area.filter(val);
    }
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      userIds: [],
      userSelected: true,
      userTotal: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 项目工作人员表格数据
      ProjectWorkerList: [],
      detectUnitUserList: [],
      areaOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      areaOpen: false,
      areaTitle: "",
      filterText: '',
      projectInfo: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectId: this.$store.state.settings.projectId,
        detectId: null,
        userId: null,
        bindType: null,
        account: null,
      },
      queryUserParams: {
        pageNum: 1,
        pageSize: 10,
        account: null,
        type: '02',
        detectId: null,
      },
      // 表单参数
      form: {},
      areaForm: {
        type: null,
        workerIds: null,
        areaIds: null
      },
      defaultProps: {
        children: "children",
        label: "label"
      },
      checkStrictly: false,
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询项目工作人员列表 */
    getList() {
      this.loading = true;

      getProject(this.queryParams.projectId).then(response => {
        this.projectInfo = response.data;
        this.queryUserParams.detectId = this.projectInfo.detectId;
      });

      listProjectWorker(this.queryParams).then(response => {
        this.ProjectWorkerList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getUserList(){
      this.loading = true;
      listDetectUnitUser(this.queryUserParams).then(response => {
        this.detectUnitUserList = response.rows;
        this.userTotal = response.total;
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
        projectId: this.$store.state.settings.projectId,
        detectId: null,
        userIds: null,
        bindType: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    resetArea(){
      this.areaForm = {
        type: null,
        workerIds: null,
        areaIds: null
      };
      this.resetForm("areaForm");
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
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
    handleSelectionChangeUser(selection){
      this.userIds = selection.map(item => item.id)
      this.userSelected = !selection.length
    },
    handleUserQuery(){
      this.queryParams.pageNum = 1;
      this.getUserList();
    },
    resetUserQuery() {
      this.resetForm("queryUserForm");
      this.handleUserQuery();
    },
    handleCommand(command){
      this.reset();
      this.form.bindType = command;
      this.getUserList();
      this.open = true;
      this.title = "绑定工作人员";
    },
    /** 提交按钮 */
    submitForm() {
      this.form.userIds = this.userIds;
      addProjectWorker(this.form).then(response => {
        this.$modal.msgSuccess("绑定成功");
        this.open = false;
        this.getList();
      });
    },
    cancelArea(){
      this.areaOpen = false;
      this.resetArea();
    },
    // 所有区域节点数据
    getAreaAllCheckedKeys() {
      // 目前被选中的节点
      let checkedNodes = this.$refs.area.getCheckedNodes(true, false);
      let checkedIds = checkedNodes.map(d => {
        return d.area.id;
      });
      console.log(checkedIds);
      return checkedIds;
    },
    handleBatchBindArea(){
      this.resetArea();
      const ids = this.ids;
      this.areaForm.workerIds = ids;
      this.areaForm.type = "1";
      getProjectAreaTree(this.queryParams.projectId).then(response => {
        this.areaOptions = response.data;
      });
      this.areaOpen = true;
      this.areaTitle = "批量绑定区域";
    },
    handleBatchEditArea(){
      this.resetArea();
      const ids = this.ids;
      this.areaForm.workerIds = ids;
      this.areaForm.type = "2";
      getProjectAreaTree(this.queryParams.projectId).then(response => {
        this.areaOptions = response.data;
      });
      this.areaOpen = true;
      this.areaTitle = "批量编辑权限";
    },
    submitAreaForm(){
      this.areaForm.areaIds = this.getAreaAllCheckedKeys();
      saveProjectWorkerArea(this.areaForm).then(response => {
        this.$modal.msgSuccess("操作成功");
        this.areaOpen = false;
      });
    },
    handleBindArea(row, type){
      this.resetArea();
      const id = row.id || this.ids
      this.areaForm.workerIds = [id];

      getProjectAreaTree(this.queryParams.projectId).then(response => {
        this.areaOptions = response.data;
      });

      getProjectWorker(id).then(response => {
        this.areaForm.type = type;
        this.areaOpen = true;

        if(type == '1'){
          this.checkedKeys = response.data.viewAreas;
          this.areaTitle = "绑定区域";
        } else{
          this.checkedKeys = response.data.editAreas;
          this.areaTitle = "编辑权限";
        }
        console.log(this.checkedKeys);
        this.$nextTick(()=>{
            this.$refs.area.setCheckedKeys(this.checkedKeys, true);
        })
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除绑定数据？').then(function() {
        return delProjectWorker(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('project/ProjectWorker/export', {
        ...this.queryParams
      }, `ProjectWorker_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
