import auth from '@/plugins/auth'
import router, { constantRoutes, dynamicRoutes, urbanVillageUnitRouters, highFireRiskUnitRouters, industrialAreaUnitRouters, chargingStationRouters } from '@/router'
import { getRouters } from '@/api/menu'
import Layout from '@/layout/index'
import ParentView from '@/components/ParentView'
import InnerLink from '@/layout/components/InnerLink'
import { getProject } from "@/api/project/project";
import store from "@/store";

const permission = {
  state: {
    routes: [],
    addRoutes: [],
    defaultRoutes: [],
    topbarRouters: [],
    sidebarRouters: []
  },
  mutations: {
    SET_ROUTES: (state, routes) => {
      state.addRoutes = routes
      state.routes = constantRoutes.concat(routes)
    },
    SET_DEFAULT_ROUTES: (state, routes) => {
      state.defaultRoutes = constantRoutes.concat(routes)
    },
    SET_TOPBAR_ROUTES: (state, routes) => {
      state.topbarRouters = routes
    },
    SET_SIDEBAR_ROUTERS: (state, routes) => {
      state.sidebarRouters = routes
    },
    UPDATE_SETTING_ROUTES: (state, [routes, insertIndex]) => {
      if (!insertIndex) {
        state.sidebarRouters = state.sidebarRouters.concat(routes);
      } else {
        let arr = [...state.sidebarRouters];
        arr.splice(insertIndex, 0, ...routes);
        state.sidebarRouters = arr;
      }
    },
    REMOVE_SETTING_ROUTES: (state, routePath) => {
      state.sidebarRouters = state.sidebarRouters.filter(
        (item) => item.path !== routePath
      );
    }
  },
  actions: {
    // 生成路由
    GenerateRoutes({ commit }) {
      return new Promise(resolve => {
        // 向后端请求路由数据
        getRouters().then(res => {
          const sdata = JSON.parse(JSON.stringify(res.data))
          const rdata = JSON.parse(JSON.stringify(res.data))
          const sidebarRoutes = filterAsyncRouter(sdata)
          const rewriteRoutes = filterAsyncRouter(rdata, false, true)
          const asyncRoutes = filterDynamicRoutes(dynamicRoutes);
          rewriteRoutes.push({ path: '*', redirect: '/404', hidden: true })
          router.addRoutes(asyncRoutes);
          commit('SET_ROUTES', rewriteRoutes)
          commit('SET_SIDEBAR_ROUTERS', constantRoutes.concat(sidebarRoutes))
          commit('SET_DEFAULT_ROUTES', sidebarRoutes)
          commit('SET_TOPBAR_ROUTES', sidebarRoutes)
          resolve(rewriteRoutes)
        })
      })
    },
    // 动态路由
    GeneProAppRoutes({ commit }, projectId) {
      return new Promise((resolve) => {
        // 根据项目id生成项目库应用路由列表
        getProject(projectId).then((res) => {
          if(res.data == null) return;
          const type = res.data.type;
          let newRoutes = [];
          if(type === '1'){
            newRoutes = urbanVillageUnitRouters;
          } else if(type === '2'){
            newRoutes = industrialAreaUnitRouters;
          } else if(type === '3'){
            newRoutes = highFireRiskUnitRouters;
          } else if(type === '4'){
            newRoutes = chargingStationRouters;
          }

          let routers = permission.state.sidebarRouters;

          let settingRoutes = routers.filter(
            (route) => route.path === '/setting'
          );

          settingRoutes = settingRoutes.filter(
            (route) => {
              var children = []
              route.children.forEach((child, index) =>{
                if(child.name !== 'ownerunit'){
                  children = children.concat(child);
                }
              })
              children = children.concat(newRoutes);
              route.children = children;
              return true;
            }
          )

          //newRoutes.push({ path: '*', redirect: '/404', hidden: true })
          router.addRoutes(settingRoutes);
          // 在原路由的基础上清除、更新
          commit("REMOVE_SETTING_ROUTES", "/setting");
          commit("UPDATE_SETTING_ROUTES", [settingRoutes, 12]);
          resolve(settingRoutes);
        });

      });
    },
  }
}

// 遍历后台传来的路由字符串，转换为组件对象
function filterAsyncRouter(asyncRouterMap, lastRouter = false, type = false) {
  return asyncRouterMap.filter(route => {
    if (type && route.children) {
      route.children = filterChildren(route.children)
    }
    if (route.component) {
      // Layout ParentView 组件特殊处理
      if (route.component === 'Layout') {
        route.component = Layout
      } else if (route.component === 'ParentView') {
        route.component = ParentView
      } else if (route.component === 'InnerLink') {
        route.component = InnerLink
      } else {
        route.component = loadView(route.component)
      }
    }
    if (route.children != null && route.children && route.children.length) {
      route.children = filterAsyncRouter(route.children, route, type)
    } else {
      delete route['children']
      delete route['redirect']
    }
    return true
  })
}

function filterChildren(childrenMap, lastRouter = false) {
  var children = []
  childrenMap.forEach((el, index) => {
    if (el.children && el.children.length) {
      if (el.component === 'ParentView' && !lastRouter) {
        el.children.forEach(c => {
          c.path = el.path + '/' + c.path
          if (c.children && c.children.length) {
            children = children.concat(filterChildren(c.children, c))
            return
          }
          children.push(c)
        })
        return
      }
    }
    if (lastRouter) {
      el.path = lastRouter.path + '/' + el.path
      if (el.children && el.children.length) {
        children = children.concat(filterChildren(el.children, el))
        return
      }
    }
    children = children.concat(el)
  })
  return children
}

// 动态路由遍历，验证是否具备权限
export function filterDynamicRoutes(routes) {
  const res = []
  routes.forEach(route => {
    if (route.permissions) {
      if (auth.hasPermiOr(route.permissions)) {
        res.push(route)
      }
    } else if (route.roles) {
      if (auth.hasRoleOr(route.roles)) {
        res.push(route)
      }
    }
  })
  return res
}

export const loadView = (view) => {
  if (process.env.NODE_ENV === 'development') {
    return (resolve) => require([`@/views/${view}`], resolve)
  } else {
    // 使用 import 实现生产环境的路由懒加载
    return () => import(`@/views/${view}`)
  }
}

export default permission
