<template>
    <div id = "pagebox">
    <br>
    <div id = "displaybox">
        <div id = "infobox">
        <!-- 个人信息区域 -->
            <div class = "title-box">
                <h1 class = "account-title">- 个人信息 -</h1>
            </div>
            
            <br>
            <el-form 
                label-width="120px"
                :model="ruleForm"
                
                ref="ruleForm"
                :size="formSize"
                :rules="rules"
                status-icon 
                id = "infoform"
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
                    <el-input v-model="ruleForm.idnumber" placeholder="" clearable disabled="true"></el-input>
                </el-form-item>

                <el-form-item label="邮箱：" prop = "email">
                    <el-input v-model="ruleForm.email" placeholder="" clearable></el-input>
                </el-form-item>

                <el-form-item label="密码：" prop = "password">
                    <el-input v-model="ruleForm.password" placeholder="6-32字符；字母，数字，特殊字符（-_）至少两种" show-password></el-input>
                </el-form-item>

                <div>
                    <el-form-item >
                        <!-- <div class = "flex-grow"></div> -->
                        <el-button id = "save_button" type="primary" @click="updateUserInfo('ruleForm')">保存</el-button>
                        <el-button id = "save_button" type="primary" plain @click="addressManageVisible = true">地址管理</el-button>
                    </el-form-item>
                </div>
            </el-form>
        </div>
        <div id = "accountbox">
            <!-- 账户信息区域 -->
            <div class = "title-box">
                <h1 class = "account-title">- 账户信息 -</h1>
            </div>
            
            <br>
            <el-col
            v-for="account in accountList"
            :key="account"
            :span="24"
            :offset="0"
            :style="{ marginBottom: '12px' }"
            style="height:150px"
            >
                <el-card class="account-card">
                    <template #header>
                    <div class="account-card-header">
                        <span>账户id：{{account.id}}  |  账户类型：{{account.type}}</span>
                        <el-button type = "primary" @click = "rechargeDialogVisible = true, curid = account.id">充值</el-button>
                    </div>
                    </template>
                    <div>{{account.balance}}</div>
                </el-card>
            </el-col>
        </div>
    </div>

    <div class = "bottom_space">
    </div>

    </div>
    <!-- 充值弹窗 -->
    <el-dialog title="账户充值" v-model="rechargeDialogVisible">
        <el-form
        :model="rechargeForm"
        ref="rechargeForm"
        :size="formSize"
        :rules="moneyrule"
        status-icon 
        >
            <el-form-item label="充值金额" prop = "money">
                <el-input v-model="rechargeForm.money"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="rechargeDialogVisible = false, rechargeForm.money = 0">取 消</el-button>
            <el-button type="primary" @click="rechargeDialogVisible = false, charge('rechargeForm')">确 定</el-button>
        </div>
    </el-dialog>

    <el-dialog title="地址管理" v-model="addressManageVisible">
        <el-table
            :data="addressData"
            highlight-current-row
            style="width: 100%"
            @current-change="handleCurrentChange"
            :row-class-name="tableRowClassName"
          >
            <el-table-column type="index" width="50" />
            <el-table-column property="name" label="姓名" width="120" />
            <el-table-column property="phone" label="电话" width="130" />
            <el-table-column property="address" label="地址" />
            <el-table-column fixed="right" label="操作" width="120">
                <template v-slot = "scope">
                    <el-button link type="primary" size="small" @click="preEditAddressDialogVisible(scope.row)"
                    >修改</el-button
                    >
                    <el-button link type="danger" size="small" @click="handleDelete(scope.row)">删除</el-button>
                </template>
            </el-table-column>
          </el-table>

                    <!-- 内嵌对话框 -->
                    <el-dialog v-model="addAddressDialogVisible" title="新增地址" append-to-body>
                    <el-form :model="newAddress" :rules="addressrule">
                        <el-form-item label="姓名" prop="name">
                            <el-input v-model="newAddress.name"></el-input>
                        </el-form-item>
                        <el-form-item label="电话" prop="phone">
                            <el-input v-model="newAddress.phone"></el-input>
                        </el-form-item>
                        <el-form-item label="地址" prop="address">
                            <el-input v-model="newAddress.address"></el-input>
                        </el-form-item>
                    </el-form>

                    <template #footer>
                        <span class="dialog-footer">
                        <el-button @click="addAddressDialogVisible = false">取消</el-button>
                        <el-button type="primary" @click="handleAddAddressConfirm()">
                            确定
                        </el-button>
                        </span>
                    </template>
                    </el-dialog>

                    <el-dialog v-model="editAddressDialogVisible" title="修改地址" append-to-body>
                        <el-form :model="newAddress" :rules="addressrule">
                        <el-form-item label="姓名" prop="name">
                            <el-input v-model="newAddress.name"></el-input>
                        </el-form-item>
                        <el-form-item label="电话" prop="phone">
                            <el-input v-model="newAddress.phone"></el-input>
                        </el-form-item>
                        <el-form-item label="地址" prop="address">
                            <el-input v-model="newAddress.address"></el-input>
                        </el-form-item>
                    </el-form>

                    <template #footer>
                        <span class="dialog-footer">
                        <el-button @click="editAddressDialogVisible = false">取消</el-button>
                        <el-button type="primary" @click="handleEditAddressConfirm()">
                            确定
                        </el-button>
                        </span>
                    </template>
                    </el-dialog>

          <template #footer>
            <span class="dialog-footer">
              <el-button @click="addAddressDialogVisible = true" type="primary" plain>新增地址</el-button>
              <el-button type="primary" @click="addressManageVisible = false">
                关闭
              </el-button>
            </span>
          </template> 
    </el-dialog>

    
</template>

<script>
    export default{
        
        name: 'Account',
        data(){
            //表单校验规则
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

            var checkMoney = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('请输入充值金额！'))
                }
                setTimeout(() => {
                    
                    var moneyformat = /^\d+(\.\d+)?$/
                    if (moneyformat.test(value)){
                        callback()
                    }
                    else {
                        callback(new Error('请输入非负数字作为充值金额'))
                    }
                    
                }, 1000)
            };

            return {
                addressrule:{
                    name: [
                        {required: true, message: '请输入姓名！', trigger: 'blur'}
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
                    address: [
                        {required: true, message: '请输入地址！', trigger: 'blur'}
                    ],
                },
                //用户信息表
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
                    
                },
                //账户信息列表
                accountList: [],
                //充值表单及其规则
                rechargeForm: {
                    money: 0,
                },
                moneyrule:{
                    money: [
                        {
                        required: true,
                        message: '请输入充值金额！',
                        trigger: 'change',
                        },
                        {
                        validator: checkMoney,
                        trigger: 'change',
                        },
                    ],
                },
                rechargeDialogVisible: false,
                addressManageVisible: false,
                addAddressDialogVisible: false,
                editAddressDialogVisible: false,

                newAddress:{
                    id: 0,
                    ownerid: 0,
                    name: "",
                    phone: "",
                    address: "",
                },

                //充值账户id
                curid: 0,

                addressData:[],
            }
            
        },
        mounted(){
            //取得用户信息和账户信息
            let name = localStorage.getItem('username')
            // let name = "ccc"
            let that = this
            let id = localStorage.getItem('userid')
            console.log(name)
            this.$axios
                ({
                    method: "post",
                    url:"/user_info",
                    data:name,
                    headers:{
                        'Content-Type': 'text/plain'
                    },
                })
                // .post('/user_info', name)
                .then(successResponse => {
                    if (successResponse.data.code === 200){
                        that.ruleForm.role = successResponse.data.data.role;
                        that.ruleForm.username = successResponse.data.data.username;
                        that.ruleForm.phone = successResponse.data.data.phone;
                        that.ruleForm.idnumber = successResponse.data.data.idnumber;
                        that.ruleForm.email = successResponse.data.data.email;
                        that.ruleForm.password = successResponse.data.data.password;
                    }
                    else{
                        this.$alert(successResponse.data.message, '读取用户信息失败，建议重新登陆', {
                            confirmButtonText: '确定',
                        })
                        this.$router.replace({path: '/login'});
                    }
                });
            console.log(id);
            let Data = {
                pageNo: 1,
                pageSize: 100,
                keyword: null
            }
            let accountPath = '/account_list?id=' + id.toString();
            this.$axios
                .post(accountPath, Data)
                // .post('/user_info', name)
                .then(successResponse => {
                    if (successResponse.data.code === 200){
                        console.log("read account info")
                        that.accountList = successResponse.data.data.records;
                    }
                    else{
                        
                        this.$alert(successResponse.data.message, '读取账户信息失败', {
                            confirmButtonText: '确定',
                        })
                    }
                })
            this.getAddress();
        },
        methods:{
            // 更新用户信息
            updateUserInfo(formName) {
                console.log("!")
                this.$refs[formName].validate((valid) => {
                if (valid) {
                    console.log('update userinfo!');
                    let Data = {
                        role:this.ruleForm.role.toString(),
                        username:this.ruleForm.username.toString(),
                        email:this.ruleForm.email.toString(),
                        idnumber:this.ruleForm.idnumber.toString(),
                        phone:this.ruleForm.phone.toString(),
                        password:this.ruleForm.password.toString(),
                        id: localStorage.getItem('userid')
                    }
                    localStorage.setItem('token', Data.role);
                    localStorage.setItem('username', Data.username);
                    this.$axios
                        .post('/user_update', Data)
                        .then(successResponse => {
                            if (successResponse.data.code === 200) {
                                this.$alert(successResponse.data.message, '信息保存成功', {
                                confirmButtonText: '确定',
                            })
                            }
                            else{
                                this.$alert(successResponse.data.message, '信息保存失败', {
                                confirmButtonText: '确定',
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
            //账户充值
            charge(formName){
                console.log("charge!")
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        let rechargePath = "/account_recharge?id=" + this.curid.toString();
                        let money = this.rechargeForm.money.toString();
                        console.log(money);
                        this.$axios
                            // .post(rechargePath, parseFloat(this.rechargeForm.money.toString()))
                            ({
                                method: "post",
                                url: rechargePath,
                                data: money,
                                headers:{
                                    'Content-Type': 'text/plain'
                                },
                            })
                            .then(successResponse => {
                                if (successResponse.data.code === 200) {
                                    this.$alert("", '充值成功！', {
                                        confirmButtonText: '确定',
                                    })
                                }
                                else{
                                    this.$alert("", '充值失败', {
                                    confirmButtonText: '确定',
                                })
                                }
                            })
                            .catch(failResponse => {
                            })
                    } else {
                        this.$alert("", '充值失败', {
                            confirmButtonText: '确定',
                        })
                        return false;
                    }
                });
                
            },
            save_info(){
                
            },
            getAddress(){
                this.$axios
                ({
                method: "post",
                url:"/address_show_id",
                data:{
                    pageNo:1,
                    pageSize:10000,
                    keyword: null,
                }, 
                
                })
                .then(response => {
                console.log("addr:",response);
                this.addressData = [];
                this.addressData = response.data.data.records;
                });
            },
            handleAddAddressConfirm() {
                this.addAddressDialogVisible=false;
                this.$axios
                ({
                    method: "post",
                    url:"/address_add",
                    data:this.newAddress,          
                })
                .then(response => {
                    console.log( "addresult",response);
                    this.getAddress();
                    this.$message({
                    message: '地址添加成功',
                    type: 'success'
                    });
                });
            },
            handleEditAddressConfirm() {
                this.editAddressDialogVisible = false;
                this.$axios
                ({
                    method: "post",
                    url:"/address_update",
                    data:this.newAddress,          
                })
                .then(response => {
                    console.log( "addresult",response);
                    this.getAddress();
                    this.$message({
                    message: '地址修改成功',
                    type: 'success'
                    });
                });
            },
            preEditAddressDialogVisible(row){
                this.editAddressDialogVisible = true;
                this.newAddress.id = row.id;
                this.newAddress.ownerid = row.ownerid;
                this.newAddress.name = row.name;
                this.newAddress.phone = row.phone;
                this.newAddress.address = row.address;
            },
            handleDelete(row){
                this.$axios
                ({
                    method: "post",
                    url:"/address_delete",
                    data:row,          
                })
                .then(response => {
                    console.log( "addresult",response);
                    this.getAddress();
                    this.$message({
                    message: '地址删除成功',
                    type: 'success'
                    });
                });
            },
        }
    }
</script>

<style>
    #displaybox{
        display: flex;
        margin-top: 30px;
        margin-left: 30px;
        margin-right: 30px;
        margin-bottom: 30px;
    }
    #infobox{
        width: 40%;
        margin-left: 5%;
        margin-right: 5%;
        
    }
    #infobox input {
        height: 40px;
        font-size: 15px;
    }
    #infoform .el-radio {
        font-size: 18px;
    }
    #infoform .el-form-item__label {
        font-size: 15px;
        font-weight: bold;
        /* font-family: "黑体"; */
    }
    #accountbox{
        width: 40%;
        margin-left: 5%;
        margin-right: 5%;
    }
    #pagebox{
        /* display: flex; */
        width: 90%;
        min-height: 80%;
        margin-top: 30px;
        margin-left: 5%;
        margin-right: 5%;
        background-color: rgba(255, 255, 255,0.9);
        border-radius: 10px;
    }
    .account-card {
        align-self: center;
        margin-right: 0%;
    }
    .title-box{
        padding-top: 50px;
    }
    .account-title{
        font-size: 30px;
        margin-top: 30px;
        letter-spacing: 5px;
        text-align: center;
        line-height: 30px;
        color: rgb(48, 48, 48);
    }
    .account-card-header{
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
</style>