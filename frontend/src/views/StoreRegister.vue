<template>
    <!-- <h1>Where the stores are registered.</h1> -->
    <div>
    <div id = "store_register_box">
        <h1 id = "register_title">商店注册</h1>
        <el-form 
            label-width="120px"
            :model="ruleForm"
            
            ref="ruleForm"
            :size="formSize"
            :rules="rules"
            id = "store_register_form"
            status-icon 
        >

            <el-form-item label="店名：" prop = "storename">
                <el-input v-model="ruleForm.storename" placeholder="长度为12个字符以内" clearable></el-input>
            </el-form-item>
            
            <el-form-item label="商品类别：" prop = "category">
                <el-input v-model="ruleForm.category" placeholder="用英文逗号隔开" clearable></el-input>
            </el-form-item>

            <el-form-item label="身份证号：" prop = "idnumber">
                <el-input v-model="ruleForm.idnumber" placeholder="" clearable></el-input>
            </el-form-item>

            <el-form-item label="商店简介：" prop = "intro">
                <el-input v-model="ruleForm.intro" placeholder="128字符以内" type = "textarea"></el-input>
            </el-form-item>

            <el-form-item label="备案地址：" prop = "address">
                <el-input v-model="ruleForm.address" placeholder="32字符以内" type = "textarea"></el-input>
            </el-form-item>

            <el-form-item label="注册资金：" prop = "fund">
                <el-input v-model="ruleForm.fund" placeholder="大于1000的浮点数"></el-input>
            </el-form-item>

            <el-form-item label="注册时间：" prop = "date">
                <el-date-picker
                    v-model="ruleForm.date"
                    type="date"
                    placeholder="Pick a date"
                    style="width: 100%"
                    value-format="YYYY-MM-DD"
                />
            </el-form-item>

            <el-form-item>
                <el-button id = "register_button" type="primary" @click="submitForm('ruleForm')" >注册</el-button>
            </el-form-item>
        </el-form>
    </div>
    </div>
</template>

<script >
    export default {
        name: 'StoreRegister',
        data(){
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
            }
            
            var checkFund = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('请输入注册资金！'))
                }
                setTimeout(() => {
                    
                    var fundformat = /[1-9]\d{3,}\.?\d*/
                    if (fundformat.test(value)){
                        callback()
                    }
                    else {
                        callback(new Error('注册资金不合要求！'))
                    }
                    
                }, 1000)
            }
            var checkStorename = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入店名！'))
                } else {
                    
                    callback()
                }
            }
            return{
                ruleForm: {
                    storename: '',
                    category: '',
                    idnumber: '',
                    intro: '',
                    address: '',
                    fund: '',
                    date: ''
                },
                rules: {
                    storename: [
                        { required: true, message: '请输入店名！', trigger: 'blur' },
                        { min: 1, max: 10, message: '长度为12字符以内！', trigger: 'blur' },
                        { validator: checkStorename, trigger: 'blur'},
                    ],
                    category: [
                        {
                        required: true,
                        message: '请输入商品类别！',
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
                    intro: [
                        {
                        required: true,
                        message: '请输入商店简介！',
                        trigger: 'change',
                        },
                    ],
                    address: [
                        {
                        required: true,
                        message: '请输入备案地址！',
                        trigger: 'change',
                        },
                    ],
                    fund: [
                        {
                        required: true,
                        message: '请输入注册资金！',
                        trigger: 'change',
                        },
                        {
                        validator: checkFund,
                        trigger: 'change',
                        },
                    ],
                    date: [
                        {
                        required: true,
                        message: '请选择注册日期！',
                        trigger: 'change',
                        },
                    ],
                }
            }
        },
        methods:{
            submitForm(formName) {
                console.log("!")
                this.$refs[formName].validate((valid) => {
                if (valid) {
                    console.log('registration submitted!');
                    console.log(this.ruleForm.date.toString());
                    let Data = {
                        storename:this.ruleForm.storename.toString(),
                        category:this.ruleForm.category.toString(),
                        idnumber:this.ruleForm.idnumber.toString(),
                        intro:this.ruleForm.intro.toString(),
                        address:this.ruleForm.address.toString(),
                        fund:parseFloat(this.ruleForm.fund.toString()),
                        date:this.ruleForm.date.toString()
                    }
                    this.$axios
                        .post('/store_register', Data)
                        .then(successResponse => {
                            if (successResponse.data.code === 200) {
                            this.$router.replace({path: '/main_page'})
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
    #store_register_form{
        justify-content: center;
        width: 450px;
        position: absolute;
        left: 45%;
        top: 56%;
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
    #store_register_box{
        width: 500px;
        height: 570px;
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