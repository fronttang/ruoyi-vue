import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'
import ParentView from '@/components/ParentView'

/**
 * Note: 路由配置项
 *
 * hidden: true                     // 当设置 true 的时候该路由不会再侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
 * alwaysShow: true                 // 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
 *                                  // 只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
 *                                  // 若你想不管路由下面的 children 声明的个数都显示你的根路由
 *                                  // 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由
 * redirect: noRedirect             // 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
 * name:'router-name'               // 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
 * query: '{"id": 1, "name": "ry"}' // 访问路由的默认传递参数
 * roles: ['admin', 'common']       // 访问路由的角色权限
 * permissions: ['a:a:a', 'b:b:b']  // 访问路由的菜单权限
 * meta : {
    noCache: true                   // 如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
    title: 'title'                  // 设置该路由在侧边栏和面包屑中展示的名字
    icon: 'svg-name'                // 设置该路由的图标，对应路径src/assets/icons/svg
    breadcrumb: false               // 如果设置为false，则不会在breadcrumb面包屑中显示
    activeMenu: '/system/user'      // 当路由设置了该属性，则会高亮相对应的侧边栏。
  }
 */

// 公共路由
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login'),
    hidden: true
  },
  {
    path: '/register',
    component: () => import('@/views/register'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error/401'),
    hidden: true
  },
  {
    path: '',
    component: Layout,
    redirect: 'index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/index'),
        name: 'Index',
        meta: { title: '首页', icon: 'dashboard', affix: true }
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'profile',
        component: () => import('@/views/system/user/profile/index'),
        name: 'Profile',
        meta: { title: '个人中心', icon: 'user' }
      }
    ]
  }
]

// 动态路由，基于用户权限动态去加载
export const dynamicRoutes = [
  {
    path: '/system/user-auth',
    component: Layout,
    hidden: true,
    permissions: ['system:user:edit'],
    children: [
      {
        path: 'role/:userId(\\d+)',
        component: () => import('@/views/system/user/authRole'),
        name: 'AuthRole',
        meta: { title: '分配角色', activeMenu: '/system/user' }
      }
    ]
  },
  {
    path: '/system/role-auth',
    component: Layout,
    hidden: true,
    permissions: ['system:role:edit'],
    children: [
      {
        path: 'user/:roleId(\\d+)',
        component: () => import('@/views/system/role/authUser'),
        name: 'AuthUser',
        meta: { title: '分配用户', activeMenu: '/system/role' }
      }
    ]
  },
  {
    path: '/system/dict-data',
    component: Layout,
    hidden: true,
    permissions: ['system:dict:list'],
    children: [
      {
        path: 'index/:dictId(\\d+)',
        component: () => import('@/views/system/dict/data'),
        name: 'Data',
        meta: { title: '字典数据', activeMenu: '/system/dict' }
      }
    ]
  },
  {
    path: '/monitor/job-log',
    component: Layout,
    hidden: true,
    permissions: ['monitor:job:list'],
    children: [
      {
        path: 'index/:jobId(\\d+)',
        component: () => import('@/views/monitor/job/log'),
        name: 'JobLog',
        meta: { title: '调度日志', activeMenu: '/monitor/job' }
      }
    ]
  },
  {
    path: '/tool/gen-edit',
    component: Layout,
    hidden: true,
    permissions: ['tool:gen:edit'],
    children: [
      {
        path: 'index/:tableId(\\d+)',
        component: () => import('@/views/tool/gen/editTable'),
        name: 'GenEdit',
        meta: { title: '修改生成配置', activeMenu: '/tool/gen' }
      }
    ]
  },
  {
    path: '/template/UrbanVillage/IntuitiveDetect',
    component: Layout,
    hidden: true,
    permissions: ['template:Template:edit'],
    children: [
      {
        path: 'index/:templateId(\\d+)',
        component: () => import('@/views/template/UrbanVillage/IntuitiveDetect'),
        name: 'IntuitiveDetect',
        meta: { title: '直观检测表标题', activeMenu: '/template/Template' }
      }
    ]
  },
  {
    path: '/template/UrbanVillage/IntuitiveDetectData',
    component: Layout,
    hidden: true,
    permissions: ['template:Template:edit'],
    children: [
      {
        path: 'index/:templateId(\\d+)',
        component: () => import('@/views/template/UrbanVillage/IntuitiveDetectData'),
        name: 'IntuitiveDetectData',
        meta: { title: '直观检测表内容', activeMenu: '/template/Template' }
      }
    ]
  },
  {
    path: '/template/ChargingStation',
    component: Layout,
    hidden: true,
    permissions: ['template:Template:edit'],
    children: [
      {
        path: 'index/:templateId(\\d+)',
        component: () => import('@/views/template/ChargingStation'),
        name: 'IntuitiveDetectData',
        meta: { title: '直观检测表内容', activeMenu: '/template/Template' }
      }
    ]
  },
  {
    path: '/template/HighFireRisk/score',
    component: Layout,
    hidden: true,
    permissions: ['template:Template:edit'],
    children: [
      {
        path: 'index/:templateId(\\d+)',
        component: () => import('@/views/template/HighFireRisk/score'),
        name: 'HighFireRiskScore',
        meta: { title: '直观检测计分模块', activeMenu: '/template/Template' }
      }
    ]
  },
  {
    path: '/template/HighFireRisk/view',
    component: Layout,
    hidden: true,
    permissions: ['template:Template:edit'],
    children: [
      {
        path: 'index/:templateId(\\d+)',
        component: () => import('@/views/template/HighFireRisk/view'),
        name: 'HighFireRiskView',
        meta: { title: '展示检测表', activeMenu: '/template/Template' }
      },
      {
        path: 'firstlevel/:templateId(\\d+)/:unitType(\\d+)/:detectId(\\d+)',
        component: () => import('@/views/template/HighFireRisk/view/firstlevel'),
        name: 'HighFireRiskViewFirstLevel',
        meta: { title: '一级展示模块', activeMenu: '/template/Template' }
      },
      {
        path: 'secondlevel/:templateId(\\d+)/:unitType(\\d+)/:detectId(\\d+)/:parentId(\\d+)',
        component: () => import('@/views/template/HighFireRisk/view/secondlevel'),
        name: 'HighFireRiskViewSecondLevel',
        meta: { title: '二级展示模块', activeMenu: '/template/Template' }
      }
    ]
  },
  {
    path: '/danger/list',
    component: Layout,
    hidden: true,
    permissions: ['*:*:*'],
    children: [
      {
        path: 'index/:unitId(\\d+)',
        component: () => import('@/views/danger/danger/index'),
        name: 'OwnerUnitDanger',
        meta: { title: '隐患数据汇总', activeMenu: '/danger/group' }
      },
      {
        path: 'index/:unitId(\\d+)/:buildingId(\\d+)',
        component: () => import('@/views/danger/danger/index'),
        name: 'OwnerUnitDanger',
        meta: { title: '隐患数据汇总', activeMenu: '/danger/group' }
      }
    ]
  },
  {
    path: '/danger/building',
    component: Layout,
    hidden: true,
    permissions: ['*:*:*'],
    children: [
      {
        path: 'index/:unitId(\\d+)',
        component: () => import('@/views/danger/building/index'),
        name: 'OwnerUnitBuildingDanger',
        meta: { title: '楼栋隐患数据汇总', activeMenu: '/danger/group' }
      }
    ]
  },
  {
    path: '/report/onlyoffice',
    component: Layout,
    hidden: true,
    permissions: ['*:*:*'],
    children: [
      {
        path: 'index',
        component: () => import('@/views/report/onlyoffice/index'),
        name: 'onlyoffice',
        meta: { title: '报告编辑', activeMenu: '/report/Initial' }
      }
    ]
  },
  {
    path: '/report/weboffice',
    component: Layout,
    hidden: true,
    permissions: ['*:*:*'],
    children: [
      {
        path: 'index',
        component: () => import('@/views/report/weboffice/index'),
        name: 'weboffice',
        meta: { title: '报告编辑', activeMenu: '/report/Initial' }
      },
      {
        path: 'weboffice/:reportId(\\d+)/:type(\\d+)',
        component: () => import('@/views/report/weboffice/weboffice'),
        name: 'webOfficeContent',
        meta: { title: '报告编辑', activeMenu: '/report/Initial' }
      }
    ]
  }
]

export const urbanVillageUnitRouters = [
  {
    path: 'UrbanVillageUnit',
    component: () => import('@/views/project/UrbanVillageUnit/index'),
    name: 'ownerunit',
    meta: { title: '业主单元', icon: 'row'}
  }
]

export const industrialAreaUnitRouters = [
  {
    path: 'IndustrialAreaUnit',
    component: () => import('@/views/project/IndustrialAreaUnit/index'),
    name: 'ownerunit',
    meta: { title: '业主单元', icon: 'row'}
  }
]

export const highFireRiskUnitRouters = [
  {
    path: 'HighFireRisk',
    component: ParentView,
    name: 'ownerunit',
    meta: { title: '业主单元', icon: 'row'},
    children: [
      {
        path: 'HighFireRisk/RentalHouse',
        component: () => import('@/views/project/HighFireRisk/RentalHouse/index'),
        name: 'RentalHouse',
        meta: { title: '出租屋', icon: 'logininfor'}
      },
      {
        path: 'HighFireRisk/Small',
        component: () => import('@/views/project/HighFireRisk/Small/index'),
        name: 'Small',
        meta: { title: '三小场所', icon: 'date-range'}
      },
      {
        path: 'HighFireRisk/Residential',
        component: () => import('@/views/project/HighFireRisk/Residential/index'),
        name: 'Residential',
        meta: { title: '住宅小区', icon: 'number'}
      },
      {
        path: 'HighFireRisk/Industrial',
        component: () => import('@/views/project/HighFireRisk/Industrial/index'),
        name: 'Industrial',
        meta: { title: '工业企业', icon: 'checkbox'}
      },
      {
        path: 'HighFireRisk/PublicPlaces',
        component: () => import('@/views/project/HighFireRisk/PublicPlaces/index'),
        name: 'PublicPlaces',
        meta: { title: '公共场所', icon: 'drag'}
      },
      {
        path: 'HighFireRisk/Complex',
        component: () => import('@/views/project/HighFireRisk/Complex/index'),
        name: 'Complex',
        meta: { title: '大型综合体', icon: 'international'}
      }
    ]
  }
]

export const chargingStationRouters = [
  {
    path: 'ChargingStation',
    component: () => import('@/views/project/ChargingStation/index'),
    name: 'ownerunit',
    meta: { title: '业主单元', icon: 'row'}
  }
]

// 防止连续点击多次路由报错
let routerPush = Router.prototype.push;
let routerReplace = Router.prototype.replace;
// push
Router.prototype.push = function push(location) {
  return routerPush.call(this, location).catch(err => err)
}
// replace
Router.prototype.replace = function push(location) {
  return routerReplace.call(this, location).catch(err => err)
}

export default new Router({
  mode: 'history', // 去掉url中的#
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})
