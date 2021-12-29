import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '主页', icon: 'dashboard' }
    }]
  },

  {
    path: '/roles',
    component: Layout,
    redirect: '/roles',
    children: [{
      path: 'roles',
      name: 'roles',
      component: () => import('@/views/roles/index'),
      meta: { title: '角色设置', icon: 'dashboard', permission: '权限设置' }
    }]
  },

  {
    path: '/record',
    component: Layout,
    redirect: '/record',
    meta: { title: '档案管理', icon: 'dashboard'},
    children:[
      {
        path: 'record',
        name: 'record',
        component: () => import('@/views/record/index'),
        meta: { title: '查询档案', icon: 'dashboard', permission: '档案查询' }
      },
      {
        path: 'addRecord',
        name: 'addRecord',
        component: () => import('@/views/record/addRecord'),
        meta: { title: '登记档案', icon: 'dashboard', permission: '档案登记' }
      },
      {
        path: 'updateRecord',
        name: '变更档案',
        component: () => import('@/views/record/updateRecord'),
        meta: { title: '变更档案', icon: 'dashboard', permission: '档案变更'},
        hidden:true
      },
      {
        path: '/checkRecord',
        name: '复核',
        component: () => import('@/views/record/check/index'),
        meta: { title: '复核管理', icon: 'dashboard', permission: '档案复核' },
        children:[
          {
            path: 'checkRecord',
            name: '复核档案',
            component: () => import('@/views/record/check/checkRecord'),
            meta: { title: '复核档案', icon: 'dashboard', permission: '档案复核' }
          },
          {
            path: '/deleteRecord',
            name: '删除档案',
            component: () => import('@/views/record/check/deleteRecord'),
            meta: { title: '删除档案', icon: 'dashboard', permission: '档案删除' }
          },
          {
            path:"/recoverRecord",
            name:"恢复档案",
            component: () => import('@/views/record/check/recoverRecord'),
            meta: { title: '恢复档案', icon: 'dashboard', permission: '档案删除' }
          }
        ]
      },
      {
        path: 'transferRecord',
        name: '调动档案',
        component: () => import('@/views/record/transferRecord'),
        meta: { title: '调动档案', icon: 'dashboard', permission: '档案调动' }
      },
    ]
  },

  {
    path: '/payment',
    component: Layout,
    redirect: '/payment',
    meta: { title: '薪酬标准管理', icon: 'dashboard'},
    children:[
      {
        path: 'payment',
        name: 'payment',
        component: () => import('@/views/payment/index'),
        meta: { title: '标准查询', icon: 'dashboard', permission: '薪酬标准查询' }
      },
      {
        path: 'insert',
        name: 'insert',
        component: () => import('@/views/payment/insert'),
        meta: { title: '标准登记', icon: 'dashboard', permission: '薪酬标准登记' }
      },
      {
        path: 'update',
        name: 'update',
        component: () => import('@/views/payment/update'),
        meta: { title: '标准变更', icon: 'dashboard', permission: '薪酬标准变更' }
      },
      {
        path: 'check',
        name: 'check',
        component: () => import('@/views/payment/check/index'),
        meta: { title: '标准复核', icon: 'dashboard', permission: '薪酬标准复核' }
      },
    ]
  },

  {
    path: '/serial',
    component: Layout,
    redirect: '/serial',
    meta: { title: '薪酬发放管理', icon: 'dashboard'},
    children:[
      {
        path: 'serial',
        name: 'serial',
        component: () => import('@/views/serial/index'),
        meta: { title: '发放登记', icon: 'dashboard', permission: '发放登记' }
      },
      {
        path: 'check',
        name: 'check',
        component: () => import('@/views/serial/check/index'),
        meta: { title: '发放复核', icon: 'dashboard', permission: '发放复核' }
      },
    ]
  },
  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}
export default router
