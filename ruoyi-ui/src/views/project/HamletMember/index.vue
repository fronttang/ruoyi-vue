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
      <el-form-item label="一级区域" prop="district">
        <el-select v-model="queryParams.district" placeholder="请选择一级区域"  filterable clearable>
          <el-option
            v-for="dict in districtOptions"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="二级区域" prop="street">
        <el-select v-model="queryParams.street" placeholder="请选择二级区域"  filterable clearable>
          <el-option
            v-for="dict in streetOptions"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="三级区域" prop="community">
        <el-select v-model="queryParams.community" placeholder="请选择三级区域"  filterable clearable>
          <el-option
            v-for="dict in communityOptions"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="四级区域" prop="hamlet">
        <el-select v-model="queryParams.hamlet" placeholder="请选择四级区域"  filterable clearable>
          <el-option
            v-for="dict in hamletOptions"
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
          v-hasPermi="['project:HamletMember:add']"
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
          v-hasPermi="['project:HamletMember:edit']"
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
          v-hasPermi="['project:HamletMember:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['project:HamletMember:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="HamletMemberList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" width="60" prop="id" />
      <el-table-column label="检测单位" align="center" prop="detectName" :show-overflow-tooltip="true"  />
      <el-table-column label="项目名称" align="center" prop="projectName" :show-overflow-tooltip="true" />
      <el-table-column label="姓名" align="center" prop="name"  width="120"/>
      <el-table-column label="账号" align="center" prop="account"  width="120" />
      <el-table-column label="区域" align="center" prop="district" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="districtOptions" :value="scope.row.district"/>
          <dict-tag :options="streetOptions" :value="scope.row.street"/>
          <dict-tag :options="communityOptions" :value="scope.row.community"/>
          <dict-tag :options="hamletOptions" :value="scope.row.hamlet"/>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center"  width="60" prop="status">
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
      <el-table-column label="操作" align="center" width="160" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['project:HamletMember:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['project:HamletMember:remove']"
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

    <!-- 添加或修改街区账号对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="一级区域" prop="district">
          <el-select v-model="form.district" placeholder="请选择一级区域" filterable clearable>
            <el-option
              v-for="dict in districtOptions"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="二级区域" prop="street">
          <el-select v-model="form.street" placeholder="请选择二级区域" filterable clearable>
            <el-option
              v-for="dict in streetOptions"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="三级区域" prop="community">
          <el-select v-model="form.community" placeholder="请选择三级区域" filterable clearable>
            <el-option
              v-for="dict in communityOptions"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="四级区域" prop="hamlet">
          <el-select v-model="form.hamlet" placeholder="请选择四级区域" filterable clearable>
            <el-option
              v-for="dict in hamletOptions"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="账号" prop="account">
          <el-input v-model="form.account" placeholder="请输入账号" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入密码" type="password" maxlength="20" show-password/>
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
import { listDetectUnitUser, getDetectUnitUser, delDetectUnitUser, addDetectUnitUser, updateDetectUnitUser } from "@/api/projectrole/detectUnitUser";
import { detectUnitDict } from "@/api/projectrole/DetectUnit";
import { changeUserStatus } from "@/api/system/user";
import { listProjectAreaDict } from "@/api/project/AreaDict";
import { getProjectAreaDictByProjectIdAndType } from "@/api/project/ProjectArea";
import DictMeta from '@/utils/dict/DictMeta'

export default {
  name: "HamletMember",
  dicts: ['hamlet', 'account_status', 'community', 'district', 'street'],
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
      // 街区账号表格数据
      HamletMemberList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      districtOptions: [],
      streetOptions: [],
      communityOptions: [],
      hamletOptions: [],
      dictQuery: {
        dictType: null,
        projectId: this.$store.state.settings.projectId,
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectId: this.$store.state.settings.projectId,
        detectId: null,
        detectName: null,
        account: null,
        name: null,
        type: '03',
        status: null,
        district: null,
        street: null,
        community: null,
        hamlet: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        district: [
          { required: true, message: "一级区域不能为空", trigger: "change" }
        ],
        name: [
          { required: true, message: "姓名不能为空", trigger: "blur" }
        ],
        account: [
          { required: true, message: "账号不能为空", trigger: "blur" }
        ],
        password: [
          { required: true, message: "密码不能为空", trigger: "blur" }
        ],
      },
      // 检测单位字典选项
      detectUnitDict: []
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询街区账号列表 */
    getList() {
      this.loading = true;

      getProjectAreaDictByProjectIdAndType(this.$store.state.settings.projectId, 'district').then(response => {
        this.districtList = response.data;
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
        this.HamletMemberList = response.rows;
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
        status: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        type: '03',
        recorder: null,
        district: null,
        street: null,
        community: null,
        hamlet: null
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
      this.title = "添加街区账号";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getDetectUnitUser(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改街区账号";
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
      this.$modal.confirm('是否确认删除街区账号编号为"' + ids + '"的数据项？').then(function() {
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
      }, `HamletMember_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
