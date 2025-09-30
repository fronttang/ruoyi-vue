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
          v-hasPermi="['projectrole:detectUnitUser:add']"
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
          v-hasPermi="['projectrole:detectUnitUser:edit']"
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
          v-hasPermi="['projectrole:detectUnitUser:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['projectrole:detectUnitUser:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="gridmanList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" width="60" prop="id" />
      <el-table-column label="检测单位" align="center" prop="detectName" min-width="300" :show-overflow-tooltip="true" />
      <el-table-column label="项目名称" align="center" prop="projectName" min-width="200" :show-overflow-tooltip="true" />
      <el-table-column label="姓名" align="center" width="120" prop="name" />
      <el-table-column label="账号" align="center" width="120" prop="account" />
      <el-table-column label="状态" align="center" width="60" prop="status">
      <template slot-scope="scope">
        <el-switch
          v-model="scope.row.status"
          active-value="0"
          inactive-value="1"
          @change="handleStatusChange(scope.row)"
        ></el-switch>
      </template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="updateTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" align="center" width="180" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)" >
            <el-button size="mini" type="text" icon="el-icon-edit">操作</el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="edit" icon="el-icon-edit" >修改</el-dropdown-item>
              <el-dropdown-item command="delete" icon="el-icon-delete" >删除</el-dropdown-item>
              <el-dropdown-item command="logout" icon="el-icon-user" >下线</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>

          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUnit(scope.row, 0)"
            v-hasPermi="['projectrole:detectUnitUser:edit']"
          >分配</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUnit(scope.row, 1)"
            v-hasPermi="['projectrole:detectUnitUser:edit']"
          >已分配</el-button>

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

    <!-- 添加或修改网格员账号对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="账号" prop="account">
          <el-input v-model="form.account" placeholder="请输入账号" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入密码" type="password" maxlength="20" show-password/>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword" v-if="this.form.password !== '********'">
          <el-input v-model="form.confirmPassword" placeholder="请确认密码" type="password" maxlength="20" show-password/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


    <!-- 分配对话框 -->
    <el-dialog :title="unitTitle" :visible.sync="userUnitVisible" width="1200px" append-to-body>
      <el-form :model="queryUnitParams" ref="queryUnitForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="名称" prop="name">
          <el-input
            v-model="queryUnitParams.name"
            placeholder="请输入名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="类型" prop="highRiskType"  v-if="projectType === '3'">
          <el-select v-model="queryUnitParams.highRiskType" placeholder="请选择业主单元类型" filterable clearable>
            <el-option
              v-for="dict in dict.type.high_risk_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="区" prop="district">
          <el-select v-model="queryUnitParams.district" placeholder="请选择区"  filterable clearable>
            <el-option
              v-for="dict in districtOptions"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="街道" prop="street">
          <el-select v-model="queryUnitParams.street" placeholder="请选择街道"  filterable clearable>
            <el-option
              v-for="dict in streetOptions"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="社区" prop="community" v-if="projectType === '1' || projectType == '3'">
          <el-select v-model="queryUnitParams.community" placeholder="请选择社区"  filterable clearable>
            <el-option
              v-for="dict in communityOptions"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="村" prop="hamlet"  v-if="projectType === '1'">
          <el-select v-model="queryUnitParams.hamlet" placeholder="请选择村"  filterable clearable>
            <el-option
              v-for="dict in hamletOptions"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleUnitQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetUnitQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="success"
            plain
            icon="el-icon-edit"
            size="mini"
            :disabled="unitSelected"
            @click="submitUnit"
          >{{ buttonName }}</el-button>
        </el-col>
      </el-row>
      <el-table v-loading="unitLoading" :data="OwnerUnitList" @selection-change="handleSelectionChangeUnit">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="ID" align="center" width="60" prop="id" />
        <el-table-column label="名称" align="center" prop="name" min-width="200" :show-overflow-tooltip="true" />
        <el-table-column label="检测单位" align="center" prop="detectName" min-width="300" :formatter="detectFormat" :show-overflow-tooltip="true"/>
        <el-table-column label="区域" align="center" prop="area" min-width="200" :formatter="areaFormat" :show-overflow-tooltip="true" />
        <el-table-column label="管理员" align="center" prop="manager" width="100" :formatter="managerFormat" />
        <el-table-column label="网格员" align="center" prop="gridman" width="100"  :formatter="gridmanFormat" />
        <el-table-column label="检测地址" align="center" prop="address" min-width="200" :show-overflow-tooltip="true" />
      </el-table>
      <pagination
        v-show="unitTotal>0"
        :total="unitTotal"
        :page.sync="queryUnitParams.pageNum"
        :limit.sync="queryUnitParams.pageSize"
        @pagination="getUnitList"
      />
    </el-dialog>

  </div>
</template>

<script>
import { listDetectUnitUser, getDetectUnitUser, delDetectUnitUser, addDetectUnitUser, updateDetectUnitUser, userLogout, getDetectUnitUserDictByTypeAndProjectId } from "@/api/projectrole/detectUnitUser";
import { detectUnitDict } from "@/api/projectrole/DetectUnit";
import { changeUserStatus } from "@/api/system/user";
import DictMeta from '@/utils/dict/DictMeta'
import { getProject } from "@/api/project/project";
import { getProjectAreaDictByProjectId, getProjectAreaDictByProjectIdAndType } from "@/api/project/ProjectArea";
import { listOwnerUnit,setGridmain} from "@/api/project/OwnerUnit";

export default {
  name: "DetectUnitUser",
  dicts: ['account_status', 'high_risk_type'],
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.form.password !== value) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    };
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
      // 网格员账号表格数据
      gridmanList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        detectId: null,
        account: null,
        type: '04',
        projectId: this.$store.state.settings.projectId,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "姓名不能为空", trigger: "blur" }
        ],
        account: [
          { required: true, message: "账号不能为空", trigger: "blur" }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' },
          { pattern: /^[^<>"'|\\]+$/, message: "不能包含非法字符：< > \" ' \\\ |", trigger: "blur" }
        ],
        confirmPassword: [
          { required: true, message: '请确认密码', trigger: 'blur' },
          { required: true, validator: equalToPassword, trigger: 'blur' }
        ],
      },
      // 检测单位字典选项
      detectUnitDict: [],

      queryUnitParams: {
        pageNum: 1,
        pageSize: 10,
        account: null,
        highRiskType: null,
        detectId: null,
        name: null,
        district: null,
        street: null,
        community: null,
        hamlet: null,
        gridman: 0,
        projectId: this.$store.state.settings.projectId,
      },

      projectAreaDict: [],
      ownerUnitUserDict: [],
      gridmanDict: [],  
      OwnerUnitList: [],
      districtOptions: [],
      streetOptions: [],
      communityOptions: [],
      hamletOptions: [],
      projectType: null,
      projectInfo: {

      },
      unitTitle: '分配管辖的业主单元',
      userUnitVisible: false,
      buttonName: '分配',
      unitSelected: false,
      unitLoading: false,
      unitTotal: 0,
      unitIds: [],
      unitType: 0,
      selectGridman: null,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询检测单位账号列表 */
    getList() {
      this.loading = true;

      getProject(this.$store.state.settings.projectId).then(response => {
        this.projectInfo = response.data;
        this.form.projectName = this.projectInfo.name;
        this.projectType = response.data.type;
      });

      detectUnitDict().then(response => {
        this.detectUnitDict = response.data;
      });

      getProjectAreaDictByProjectId(this.$store.state.settings.projectId).then(response => {
        this.projectAreaDict = response.data;
      });

      getDetectUnitUserDictByTypeAndProjectId('05', this.$store.state.settings.projectId).then(response => {
        this.ownerUnitUserDict = response.data;
      });
      getDetectUnitUserDictByTypeAndProjectId('04', this.$store.state.settings.projectId).then(response => {
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

      listDetectUnitUser(this.queryParams).then(response => {
        this.gridmanList = response.rows;
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
        projectId: this.$store.state.settings.projectId,
        projectName: null,
        detectId: null,
        detectName: null,
        account: null,
        name: null,
        password: null,
        confirmPassword: null,
        status: null,
        type: '04',
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },

    resetUnitQuery() {
      this.queryUnitParams.pageNum = 1;
      this.resetForm("queryUnitForm");
    },

    handleUnitQuery() {
      this.getUnitList();
    },

    getUnitList() {
      this.unitLoading = true;
      listOwnerUnit(this.queryUnitParams).then(response => {
        this.OwnerUnitList = response.rows;
        this.unitTotal = response.total;
        this.unitLoading = false;
      });
    },

    handleSelectionChangeUnit(selection) {
      this.unitIds = selection.map(item => item.id)
      this.unitSelected = !selection.length
    },

    submitUnit() {
      //alert(this.unitIds);
      setGridmain(this.unitType, this.selectGridman, this.unitIds ).then(response => {
        this.$modal.msgSuccess("操作成功");
        this.queryUnitParams.pageNum = 1;
        this.getUnitList();
      });
    },

    handleUnit(row, type) {
      if(type == '0') {
        this.queryUnitParams.gridman = 0;
        this.buttonName = "分配";
      } else {
        this.queryUnitParams.gridman = row.id;
        this.buttonName = "取消分配";
      }
      this.selectGridman = row.id,
      this.unitType = type;
      this.resetUnitQuery();
      this.userUnitVisible = true;
      this.getUnitList();
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
      this.title = "添加网格员";
    },

    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case "edit":
          this.handleUpdate(row);
          break;
        case "delete":
          this.handleDelete(row);
          break;
        case "logout":
          this.handleLogout(row);
          break;
        default:
          break;
      }
    },

    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getDetectUnitUser(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改网格员";
      });
    },
    handleLogout(row){
      const ids = row.id || this.ids;
      this.$modal.confirm('确认要强制下线编号为' + ids + '的用户吗？').then(function() {
        return userLogout(ids);
      }).then(() => {
        this.$modal.msgSuccess("下线成功");
      }).catch(function() {
      });
    },
    // 用户状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '""' + row.account + '"用户吗？').then(function() {
        return changeUserStatus(row.id, row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    /** 提交按钮 */
    submitForm() {

      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDetectUnitUser(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDetectUnitUser(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除网格员账号编号为"' + ids + '"的数据项？').then(function() {
        return delDetectUnitUser(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('projectrole/detectUnitUser/export', {
        ...this.queryParams
      }, `detectUnitUser_${new Date().getTime()}.xlsx`)
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
      var hamletName = this.selectDictLabel(this.hamletOptions, row.hamlet);

      area.push(districtName);
      area.push(streetName);
      area.push(communityName);
      area.push(hamletName);

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
  }
};
</script>
