<script setup>
// import HelloWorld from './components/HelloWorld.vue'

// import { computed } from '@vue/reactivity';
</script>

<template>
  <div id = "background">
    <el-menu
        :default-active="1"
        mode="horizontal"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b"
        @select="handleSelect"
        router
    >
        
        <el-menu-item index="/main_page">店铺展示</el-menu-item>
        <!-- 由于将东西放到右边会导致超出页面并折叠，达不到预期效果。该问题未解决，只是将所有项目左对齐 -->
        <!-- <div class="flex-grow" ></div> -->
        <el-menu-item index="/sales_page" v-if = "!checkNotLog() && !checkAdmin()">
            活动入口
        </el-menu-item>
        <el-sub-menu index="2">
            <template #title>注册</template>
            <el-menu-item index="/user_register">
                用户注册
            </el-menu-item>
            <el-menu-item index="/store_register" v-if = "checkMerchant()">
                商店注册
            </el-menu-item>
        </el-sub-menu>
        <el-menu-item v-if = "checkNotLog()" index="/login" >
            登录
        </el-menu-item>
        <el-sub-menu v-if = "!checkNotLog()" index="4">
            <template #title>{{ showUsername() }}</template>
            <el-menu-item index="/account">
                个人中心
            </el-menu-item>
            <el-menu-item index="/trolley" v-if = "!checkNotLog() && !checkAdmin()">
                购物车
            </el-menu-item>
            <el-menu-item index="/merchant_form" v-if = "checkMerchant()">
                我的商店
            </el-menu-item>
            <el-menu-item index="/store_audit" v-if = "checkAdmin()">
                商店审核
            </el-menu-item>
            <el-menu-item index="/category_audit" v-if = "checkAdmin()">
                商品审核
            </el-menu-item>
            <el-menu-item index="/ledger" v-if = "checkAdmin() || checkMerchant()">
                流水记录
            </el-menu-item>
            <el-menu-item index="/user_order" v-if = "!checkNotLog() && !checkAdmin()">
                我的订单
            </el-menu-item>
            <el-menu-item index="/merchant_order" v-if = "checkMerchant()">
                商店订单
            </el-menu-item>
            <el-menu-item index="/merchant_subsidy" v-if = "checkMerchant()">
                参与活动
            </el-menu-item>
            <el-menu-item index="/subsidy_audit" v-if = "checkAdmin()">
                活动审批
            </el-menu-item>
            <el-menu-item index="/admin_subsidy" v-if = "checkAdmin()">
                活动创建
            </el-menu-item>
            <!-- <el-menu-item index="/sales_page" v-if = "!checkNotLog() && !checkAdmin()">
                活动入口
            </el-menu-item> -->
            <el-menu-item @click = "logOut()" index="/login">
                登出
            </el-menu-item>
        </el-sub-menu>
    </el-menu>
    <!-- 导航栏版本1（lab2） -->
  <!-- <el-menu  
    default-active="activeIndex"
    mode="horizontal"
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04b"
    @click=handleClick()
    >
        <el-menu-item index="0"><router-link to="/main_page">店铺展示</router-link></el-menu-item>
        <div class="flex-grow" ></div>
        
        <el-menu-item index="1" >
            <router-link to="/login">登录</router-link>
        </el-menu-item>

        <el-sub-menu index="2">
            <template #title>注册</template>
            <el-menu-item index="2-1">
                <el-link href="/user_register" style="color: #fff">用户注册</el-link>
            </el-menu-item>
            <el-menu-item index="2-2">
                <el-link href="/store_register" style="color: #fff">商店注册</el-link>
            </el-menu-item>
        </el-sub-menu>
        <el-link href="/store_audit" style="color: #fff">
          <el-menu-item index="3" title="商店信息审核">信息管理</el-menu-item>
        </el-link>
     
        </el-menu> -->

    <br>

  <RouterView />
</div>
</template>

<script>
    export default {
        data(){
            return {

            }
            
        },
        methods:{
            //点击登出，清理用户登陆状态
            logOut(){
                console.log("logged out")
                localStorage.removeItem('token');
                localStorage.removeItem('username');
                localStorage.removeItem('userid');
                window.location.reload();
            },
            //展示信息用
            checkNotLog(){
                return !(localStorage.getItem('token'));
            },
            checkMerchant(){
                return (localStorage.getItem('token') == "merchant");
            },
            checkAdmin(){
                return (localStorage.getItem('token') == "admin");
            },
            showUsername(){
                return localStorage.getItem('username');
            }
        },
    }
</script>

<style>
.el-menu {
  border-right-width: 0;
}
.el-menu--collapse .el-menu .el-submenu, .el-menu--popup{
    min-width: 120px!important;
}

#background{
        /* position: fixed; */
        position:absolute;
        height: 100%;
        width: 100%;
        top: 0;
        left: 0;
        background-image: linear-gradient(
            90deg,
            rgb(34, 149, 215),
            rgb(216, 175, 196)
        );
        background-size: 400%;
        background-repeat: "repeat";
        animation: bganimation 10s infinite;
        overflow: auto;
    }
    @keyframes bganimation {
        0%{
            background-position: 0% 50%;
        }
        50%{
            background-position: 100% 50%;
        }
        100%{
            background-position: 0% 50%;
        }
    }
</style>
