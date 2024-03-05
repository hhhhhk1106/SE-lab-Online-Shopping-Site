<template>
    <!-- <h1>Where the users register.</h1> -->
    <div>
    <div id = "user_register_box">
        <h1 id = "register_title">用户注册</h1>
        <el-form 
            label-width="120px"
            :model="ruleForm"
            
            ref="ruleForm"
            :size="formSize"
            :rules="rules"
            id="user_register_form"
            status-icon 
        >
            <el-form-item label="角色：" prop = "role">
                <el-radio-group v-model="ruleForm.role">
                    <el-radio label="user">普通用户</el-radio>
                    <el-radio label="merchant">商户</el-radio>
                </el-radio-group>
            </el-form-item>

            <el-form-item label="用户名：" prop = "username">
                <el-input v-model="ruleForm.username" placeholder="英文字符、数字与下划线，长度为3-10个字符" clearable></el-input>
            </el-form-item>

            <el-form-item label="手机号：" prop = "phone">
                <el-input v-model="ruleForm.phone" placeholder="" clearable>
                    <template #prepend>+86</template>
                </el-input>
            </el-form-item>
            
            <el-form-item label="身份证号：" prop = "idnumber">
                <el-input v-model="ruleForm.idnumber" placeholder="" clearable></el-input>
            </el-form-item>

            <el-form-item label="邮箱：" prop = "email">
                <el-input v-model="ruleForm.email" placeholder="" clearable></el-input>
            </el-form-item>

            <el-form-item label="密码：" prop = "password">
                <el-input v-model="ruleForm.password" placeholder="6-32字符；字母，数字，特殊字符（-_）至少两种" show-password></el-input>
            </el-form-item>

            <el-form-item>
                <el-button id = "register_button" type="primary" @click="submitForm('ruleForm')" >注册</el-button>
                <el-link id = "log_link" href = "/login">已有账号？前往登录</el-link>
            </el-form-item>
        </el-form>
    </div>
    </div>
    
</template>

<script>

    export default{
        name: 'UserRegister',
        data() {
            var checkPhone = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('请输入手机号码！'))
                }
                setTimeout(() => {
                    if (isNaN(value)) {
                    callback(new Error('请输入正确手机号码！'))
                    } else {
                    if (value < 10000000000 ||  value > 99999999999) {
                        callback(new Error('请输入正确手机号码！'))
                    } else {
                        callback()
                    }
                    }
                }, 1000)
            };

            var checkIdnumber = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('请输入身份证号！'))
                }
                setTimeout(() => {
                    
                    var idformat = /^[1-9]\d{5}(18|19|20|(3\d))\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/
                    if (idformat.test(value)){
                        callback()
                    }
                    else {
                        callback(new Error('请输入正确身份证号！'))
                    }
                    
                }, 1000)
            };

            var checkEmail = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入电子邮箱！'))
                } else {
                    var emailformat = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/
                    if (emailformat.test(value)){
                        callback()
                    }
                    else {
                        callback(new Error('请输入正确电子邮箱！'))
                    }
                }
            };

            var checkUsername = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入用户名！'))
                } else {
                    
                    var banned = /[^0-9a-zA-Z_]/
                    if (banned.test(value)){
                        callback(new Error('含有非法字符！'))
                    }
                    callback()
                }
            };

            var checkPassword = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码！'))
                } else {
                    var numbers = /[0-9]/
                    var letters = /[a-zA-Z]/
                    var special = /[-_]/
                    var banned = /[^0-9a-zA-Z-_]/
                    var complex = 0
                    if (numbers.test(value)){
                        ++ complex
                    }
                    if (letters.test(value)){
                        ++ complex
                    }
                    if (special.test(value)){
                        ++ complex
                    }
                    if (banned.test(value)){
                        callback(new Error('含有非法字符！'))
                    }
                    if (complex < 2) {
                        callback(new Error('字母，数字，特殊字符（-_）至少包含两种！'))
                    }
                    callback()
                }
            };
            return {
                ruleForm: {
                    role: '',
                    username: '',
                    phone: '',
                    idnumber: '',
                    email: '',
                    password: ''

                },
                rules: {
                    username: [
                        { required: true, message: '请输入用户名！', trigger: 'blur' },
                        { min: 3, max: 10, message: '长度为3~10字符！', trigger: 'blur' },
                        { validator: checkUsername, trigger: 'blur'},
                    ],
                    role: [
                        {
                        required: true,
                        message: '请选择角色！',
                        trigger: 'change',
                        },
                    ],
                    email: [
                        {
                        required: true,
                        message: '请输入电子邮箱！',
                        trigger: 'change',
                        },
                        {
                        validator: checkEmail,
                        trigger: 'change',
                        },
                    ],
                    idnumber: [
                        {
                        required: true,
                        message: '请输入身份证号！',
                        trigger: 'change',
                        },
                        {
                        validator: checkIdnumber,
                        trigger: 'change',
                        },
                    ],
                    phone: [
                        {
                        required: true,
                        message: '请输入手机号码！',
                        trigger: 'change',
                        },
                        {
                        validator: checkPhone,
                        trigger: 'change',
                        },
                    ],
                    password: [
                        { required: true, message: '请输入密码！', trigger: 'blur' },
                        { min: 6, max: 32, message: '长度为6~32字符！', trigger: 'blur' },
                        { validator: checkPassword, trigger: 'blur'},
                    ],
                    
                }
            }
        },
        methods: {
            submitForm(formName) {
                console.log("!")
                this.$refs[formName].validate((valid) => {
                if (valid) {
                    console.log('registration submitted!');
                    let Data = {
                        role:this.ruleForm.role.toString(),
                        username:this.ruleForm.username.toString(),
                        email:this.ruleForm.email.toString(),
                        idnumber:this.ruleForm.idnumber.toString(),
                        phone:this.ruleForm.phone.toString(),
                        password:this.ruleForm.password.toString()
                    }
                    this.$axios
                        .post('/user_register', Data)
                        .then(successResponse => {
                            if (successResponse.data.code === 200) {
                            this.$router.replace({path: '/login'})
                            }
                            else{
                                this.$alert(successResponse.data.message, '注册失败', {
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
                } else {
                    console.log('error submit!!');
                    return false;
                }
                });
            },
        }
    }
    
   
</script>

<style>
    #register_all{
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
    #register_title{
        margin-top: 30px;
        letter-spacing: 10px;
        text-align: center;
        line-height: 30px;
        color: white;
        text-shadow: 3px 3px 3px rgba(90, 91, 96, 0.3);
    }
    #user_register_form{
        justify-content: center;
        width: 450px;
        position: absolute;
        left: 45%;
        top: 58%;
        transform: translate(-50%, -50%);
    }
    #register_button{
        width: 400px;
        /* justify-content: center;
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%); */
    }
    #log_link{
        margin-left: 100px;
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
    /* #user_register_container{
        background-color: rgb(173, 199, 230);
        height: 100%;
    } */
    #user_register_box{
        width: 500px;
        height: 500px;
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