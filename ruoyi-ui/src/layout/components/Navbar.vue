<template>
  <div class="navbar">
    <hamburger id="hamburger-container" :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb id="breadcrumb-container" class="breadcrumb-container" v-if="!topNav"/>
    <top-nav id="topmenu-container" class="topmenu-container" v-if="topNav"/>

    <div class="right-menu">
      <template v-if="device!=='mobile'" >
        <!--<search id="header-search" class="right-menu-item" />


        <el-tooltip content="源码地址" effect="dark" placement="bottom">
          <ruo-yi-git id="ruoyi-git" class="right-menu-item hover-effect" />
        </el-tooltip>

        <el-tooltip content="文档地址" effect="dark" placement="bottom">
          <ruo-yi-doc id="ruoyi-doc" class="right-menu-item hover-effect" />
        </el-tooltip>


        <screenfull id="screenfull" class="right-menu-item hover-effect" />

        <el-tooltip content="布局大小" effect="dark" placement="bottom">
          <size-select id="size-select" class="right-menu-item hover-effect" />
        </el-tooltip>

        -->

      </template>

      <el-select v-model="projectId" placeholder="请选择项目" filterable class="avatar-container right-menu-item hover-effect" style="font-size: 14px;max-width:400px;" @change="handleChangeProject">
          <el-option 
            v-for="item in projectDict"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          ></el-option>
      </el-select>

      <el-dropdown v-if="false" @command="handleChangeProject" class="avatar-container right-menu-item hover-effect" trigger="click">
        <div class="avatar-wrapper" style="font-size: 14px;max-width:400px;display: inline-block;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
          {{selectedProject}}
          <i class="el-icon-arrow-down el-icon--right"></i>
        </div>
        <el-dropdown-menu slot="dropdown" style="max-width:400px">
          <el-dropdown-item style="width:100%;display: inline-block;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;" v-for="item in projectDict" :command="item" >{{item.name}}</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>

      <el-dropdown @command="handleChangeWorkerRole" class="avatar-container right-menu-item hover-effect" trigger="click">
        <div class="avatar-wrapper" style="font-size: 14px;">
          {{nickName}}{{workerRoleName}}
          <i class="el-icon-arrow-down el-icon--right"></i>
        </div>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item v-for="item in workerRoles" :command="item" >{{item.dictLabel}}</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>

      <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
        <div class="avatar-wrapper">
          <img :src="avatar" class="user-avatar">
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/user/profile">
            <el-dropdown-item>个人中心</el-dropdown-item>
          </router-link>
          <el-dropdown-item @click.native="setting = true">
            <span>布局设置</span>
          </el-dropdown-item>
          <el-dropdown-item divided @click.native="logout">
            <span>退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import TopNav from '@/components/TopNav'
import Hamburger from '@/components/Hamburger'
import Screenfull from '@/components/Screenfull'
import SizeSelect from '@/components/SizeSelect'
import Search from '@/components/HeaderSearch'
import RuoYiGit from '@/components/RuoYi/Git'
import RuoYiDoc from '@/components/RuoYi/Doc'
import { getProjectWorkerRole, setProjectWorkerRole } from "@/api/project/ProjectWorker";
import store from "@/store";

export default {
  data() {
    return {
      selectedProject: "请选择项目",
      projectId: this.$store.state.settings.projectId,
      //projectDict: this.$store.state.user.projects,
      nickName: this.$store.state.user.nickName,
      workerRoles: [],
      workerRoleName: ''
    };
  },
  created(){
    if(this.projectDict != null && this.projectDict.length > 0){
      //console.log(this.projectId);
      if(this.projectId == null || this.projectId == '') {
        this.projectId = this.projectDict[0].id;
      }
      
      this.handleChangeProject(this.projectId);
      store.dispatch("GeneProAppRoutes", this.projectId);
    }
  },
  components: {
    Breadcrumb,
    TopNav,
    Hamburger,
    Screenfull,
    SizeSelect,
    Search,
    RuoYiGit,
    RuoYiDoc
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'device'
    ]),
    setting: {
      get() {
        return this.$store.state.settings.showSettings
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'showSettings',
          value: val
        })
      }
    },
    topNav: {
      get() {
        return this.$store.state.settings.topNav
      }
    },
    projectDict: {
      get() {
        return this.$store.state.user.projects
      }
    },
    tagsView: {
      get() {
        return this.$store.state.settings.tagsView
      }
    },
    fixedHeader: {
      get() {
        return this.$store.state.settings.fixedHeader
      }
    },
    sidebarLogo: {
      get() {
        return this.$store.state.settings.sidebarLogo
      }
    },
    dynamicTitle: {
      get() {
        return this.$store.state.settings.dynamicTitle
      }
    },
    sideTheme: {
      get() {
        return this.$store.state.settings.sideTheme
      }
    },
    theme: {
      get() {
        return this.$store.state.settings.theme
      }
    }
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    handleChangeProject(projectId){
      this.$store.dispatch('settings/changeSetting', {
        key: 'projectId',
        value: projectId
      })

      getProjectWorkerRole(projectId).then((res) => {
        this.workerRoles = res.data;
        if(this.workerRoles != null && this.workerRoles.length > 0){
          this.handleChangeWorkerRole(this.workerRoles[0]);
        }
      });

      this.$cache.local.set(
        "layout-setting",
        `{
            "topNav": ${this.topNav},
            "tagsView": ${this.tagsView},
            "fixedHeader": ${this.fixedHeader},
            "sidebarLogo": ${this.sidebarLogo},
            "dynamicTitle": ${this.dynamicTitle},
            "sideTheme": "${this.sideTheme}",
            "theme": "${this.theme}",
            "projectId": ${this.projectId}
          }`
      );

      //this.selectedProject = project.name;
      this.$tab.closeAllPage();
      //this.$tab.closeOtherPage();
      this.$tab.openPage("首页", "/index");
      //this.$tab.closeOtherPage();
      this.$tab.refreshPage();
    },
    handleChangeWorkerRole(role){
      var data = {
        id: role.dictValue,
        name: role.dictLabel
      }
      setProjectWorkerRole(data).then((res) => {
        this.workerRoleName = '（' + role.dictLabel + '）';
      });

      this.$store.dispatch('settings/changeSetting', {
        key: 'workerRoleId',
        value: role.dictValue
      })
    },
    async logout() {
      this.$confirm('确定注销并退出系统吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$store.dispatch('LogOut').then(() => {
          location.href = '/admin/index';
        })
      }).catch(() => {});
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .topmenu-container {
    position: absolute;
    left: 50px;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
