<template>
    <!-- <h1>Here you want to let users login, if succeeded, jump to main page</h1> -->
    <div>
        <div class = "login_box">
            <h1 id = "login_title">登录</h1>
            <div id = "login_form">
                <el-form label-width="0px"
                style = "max-width: 500px"
                >
                    <el-form-item>
                        <el-input v-model="form.username" placeholder="用户名" clearable></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-input v-model="form.password" placeholder="密码" show-password></el-input>
                    </el-form-item>
                    <div id = "btn_box">
                    <el-form-item >
                        <el-button id = "login_button" type="primary" @click="login" >登录</el-button>
                        <el-link id = "reg_link" href = "/user_register">没有账号？立即注册</el-link>
                    </el-form-item>
                    </div>
                </el-form>
            </div>
        </div>
    </div>
    
</template>

<script>
    export default{
        name: 'Login',
        data (){
            return {
                form:{
                    username: '',
                    password: ''

                },
                responseResult: []
            }
        },
        methods: {
            login () {
                let Data = {
                    username:this.form.username.toString(),//!!
                    password:this.form.password.toString()
                }
                this.$axios
                    .post('/login', Data)
                    .then(successResponse => {
                        if (successResponse.data.code === 200) {
                            // console.log (successResponse.data.data.user.role);
                            // localStorage.setItem("token", "hello");
                            localStorage.setItem("token", successResponse.data.data.user.role);
                            localStorage.setItem("username", successResponse.data.data.user.username);
                            localStorage.setItem("userid", successResponse.data.data.user.id);
                            this.$router.push({
                                path: '/main_page',
                                query: {
                                }
                            })
                            
                            
                        }
                        else{
                            this.$alert(successResponse.data.message, '登陆失败', {
                            confirmButtonText: '确定',
                            // callback: action => {
                            //     this.$message({
                            //     type: 'info',
                            //     message: `action: ${ action }`
                            //     });
                            // }
                        })
                        }
                    })
                    .catch(failResponse => {
                    })
            }
        }
    }
</script>


<style>
    #login_all{
        position: fixed;
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
        animation: myanimation 10s infinite;
    }
    #login_title{
        margin-top: 30px;
        letter-spacing: 10px;
        text-align: center;
        line-height: 30px;
        color: white;
        text-shadow: 3px 3px 3px rgba(90, 91, 96, 0.3);
    }
    #login_form{
        justify-content: center;
        width: 400px;
        position: absolute;
        left: 50%;
        top: 60%;
        transform: translate(-50%, -50%);
    }
    #login_button{
        width: 400px;
        /* justify-content: center;
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%); */
    }
    #reg_link{
        margin-left: 136px;
    }
    @keyframes myanimation {
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
    #btn_box{
        width: 400px;
        justify-content: center;
    }
    .login_container{
        background-color: rgb(173, 199, 230);
        height: 100%;
    }
    .login_box{
        width: 500px;
        height: 300px;
        background-color:rgba(231, 235, 239, 0.6);
        border-radius: 10px;
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        box-shadow: 10px 10px 20px rgba(90, 91, 96, 0.3);
        animation-name: fade;
        animation-duration: .7s;
        animation-iteration-count: 1;
        opacity: 1;
    }
    @keyframes fade {
        0%{
            
            opacity: 0;
        }
        50%{
            opacity: 0;
            transform: translate(-50%, -60%);
            /* transform: translateY(-50px); */
        }
        100%{
            opacity: 1;
            transform: translate(-50%, -50%);
            /* transform: translateY(0px); */
        }
    }
</style>

<!-- <script setup>
    // import { ref } from 'vue'
    import { reactive } from 'vue'
    
    
    import { getCurrentInstance, onBeforeMount} from "vue";

    // const input1 = ref('')
    // const input2 = ref('')

    
    const form = reactive({
        username: '',
        password: ''
    })

    let { proxy } = getCurrentInstance();
    const onLogin = () => {
        console.log('Ask for verification.')
        console.log(proxy)
        proxy.$axios
          .post('/login', {
            username: proxy.form.username,
            password: proxy.form.password
          })
          .then(successResponse => {
            if (successResponse.data.code === 200) {
              this.$router.replace({path: '/'})
            }
          })
          .catch(failResponse => {
          })
    }
    
</script> -->

<!-- <script>
    export default{
        data(){
            return{
                input:''
            }
         }
    }
</script> -->