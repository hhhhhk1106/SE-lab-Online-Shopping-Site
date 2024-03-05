import { createRouter, createWebHistory } from 'vue-router'
// import HomeView from '../views/HomeView.vue'
import test from '../views/test.vue'
import login from '../views/Login.vue'
import userRegister from '../views/UserRegister.vue'
import storeRegister from '../views/StoreRegister.vue'
import mainPage from '../views/MainPage.vue'
import storeAudit from '../views/StoreAudit.vue'
import categoryAudit from '../views/CategoryAudit.vue'
import GoodsInfo from '../views/GoodsInfo.vue'
import MerchantForm from '../views/MerchantForm.vue'
import Trolley from '../views/Trolley.vue'
import Account from '../views/Account.vue'
import UserOrder from '../views/UserOrder.vue'
import GoodDetail from '../views/GoodDetail.vue'
import Order from '../views/Order.vue'
import Pay from '../views/Pay.vue'
import MerchantOrder from '../views/MerchantOrder.vue'
import AdminSubsidy from '../views/AdminSubsidy.vue'
import MerchantSubsidy from '../views/MerchantSubsidy.vue'
import SubsidyAudit from '../views/SubsidyAudit.vue'
import Ledger from '../views/Ledger.vue'
import SalesPage from '../views/SalesPage.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    // {
    //   path: '/',
    //   name: 'home',
    //   component: HomeView
    // },
    // {
    //   path: '/about',
    //   name: 'about',
    //   // route level code-splitting
    //   // this generates a separate chunk (About.[hash].js) for this route
    //   // which is lazy-loaded when the route is visited.
    //   component: () => import('../views/AboutView.vue')
    // },
    {
      path: '/test',
      name: 'test',
      component: test
    },
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/user_register',
      name: 'userRegister',
      component: userRegister
    },
    {
      path: '/store_register',
      name: 'storeRegister',
      component: storeRegister
    },
    {
      path: '/main_page',
      name: 'mainPage',
      component: mainPage
    },
    {
      path: '/store_audit',
      name: 'storeAudit',
      component: storeAudit
    },
    {
      path: '/category_audit',
      name: 'categoryAudit',
      component: categoryAudit
    },
    {
      path: '/goods_info/:store_id',
      name: 'GoodsInfo',
      component: GoodsInfo
    },
    {
      path: '/merchant_form',
      name: 'MerchantForm',
      component: MerchantForm
    },
    {
      path: '/trolley',
      name: 'Trolley',
      component: Trolley
    },
    {
      path: '/account',
      name: 'Account',
      component: Account
    },
    {
      path: '/user_order',
      name: 'UserOrder',
      component: UserOrder
    },
    {
      path: '/good_detail',
      name: 'GoodDetail',
      component: GoodDetail
    },
    {
      path: '/order',
      name: 'Order',
      component: Order
    },
    {
      path: '/merchant_order',
      name: 'MerchantOrder',
      component: MerchantOrder
    },
    {
      path: '/pay',
      name: 'Pay',
      component: Pay
    },
    {
      path: '/admin_subsidy',
      name: 'AdminSubsidy',
      component: AdminSubsidy
    },
    {
      path: '/merchant_subsidy',
      name: 'MerchantSubsidy',
      component: MerchantSubsidy
    },
    {
      path: '/subsidy_audit',
      name: 'SubsidyAudit',
      component: SubsidyAudit
    },
    {
      path: '/ledger',
      name: 'Ledger',
      component: Ledger
    },
    {
      path: '/sales_page',
      name: 'SalesPage',
      component: SalesPage
    },
    {
      path: '/', // 未匹配到路由时重定向
      redirect: '/login',
    }
  ]
})

router.beforeEach((to, from, next) => {
  const notLoggedPath = [
    '/account',
    '/store_register',
    '/main_page',
    '/store_audit',
    '/category_audit',
    '/merchant_form',
    '/trolley',
    '/user_order',
    // '/goods_info/:store_id',
    '/goods_info',
    '/user_order',
    '/merchant_order',
    '/admin_subsidy',
    '/merchant_subsidy',
    '/subsidy_audit',
    '/sales_page',
    '/pay',
    '/order',
    '/good_detail',
    '/ledger'
  ]
  
  const userPath = [
    '/store_register',
    '/store_audit',
    '/category_audit',
    '/merchant_form',
    '/merchant_order',
    '/admin_subsidy',
    '/merchant_subsidy',
    '/subsidy_audit',
    '/ledger'
  ]
  const merchantPath = [
    '/store_audit',
    '/category_audit',
    '/admin_subsidy'
  ]
  const adminPath = [
    '/merchant_form',
    '/user_order', //管理员没有购物车和个人订单页面
    '/trolley',
    '/user_order',//管理员也没有用户订单和店铺订单，也没有活动申请，也没有支付
    '/merchant_order',
    '/merchant_subsidy',
    '/pay',
    '/order'
  ]
  const token=localStorage.getItem('token')
  switch (token){
    case "user": if(userPath.indexOf(to.path) !== -1){
        next('/login');
      }
      else next();
      break;
    case "merchant": if(merchantPath.indexOf(to.path) !== -1){
        next('/login');
      }
      else next();
      break;
    case "admin": if(adminPath.indexOf(to.path) !== -1){
        next('/login');
      }
      else next();
      break;
    default : if(notLoggedPath.indexOf(to.path) !== -1 || to.path.indexOf("/goods_info") !== -1){
      next('/login');
    }
    else next();
  }
  
  
})

export default router
