<template>
    <div id="pagebox">
    <br>
    <h1 id = "audit_page_title">商店审核</h1>
    <br>
    <div class = "form_container">
        <h1 class = "audit_form_title">- 开店申请审核 -</h1>
        <el-table :data="appData" class = "table">
        <el-table-column prop="date" label="注册时间" width="130" class = "tableColumn"/>
        <el-table-column prop="storename" label="店名" width="120" />
        <el-table-column prop="category" label="商品类别" width="120" />
        <el-table-column prop="intro" label="商店简介" width="120" />
        <el-table-column prop="id" label="店铺编号" width="120" />
        <el-table-column prop="idnumber" label="店主身份证号" width="120" />
        <el-table-column prop="fund" label="注册资金" width="120" />
        <el-table-column prop="address" label="备案地址" width="200" />
        <el-table-column prop="state" label="状态" width="110">
            <template v-slot="scope">
                <el-tag v-if="scope.row.state == 0" type="warning">待处理</el-tag>
                <el-tag v-if="scope.row.state == 1" type="success">已批准</el-tag>
                <el-tag v-if="scope.row.state == 10" type="danger">已驳回</el-tag>
                <!-- <el-text >{{
                    scope.row.state == "0"
                    ? "待处理"
                    : scope.row.state == "5"
                    ? "已批准"
                    : scope.row.state == "10"
                    ? "已驳回"
                    : "错误"
                }}</el-text> -->
            </template>
        </el-table-column> 
        <el-table-column fixed="right" label="操作" width="200">
            <template v-slot = "scope">
            <el-button type="success" size="small" @click="appApprove(scope.row)" :disabled="scope.row.state != 0"
                >批准</el-button
            >
            <el-button type="danger" size="small" @click="appReject(scope.row)" :disabled="scope.row.state != 0">驳回</el-button>
            </template>
        </el-table-column>
        </el-table>
    </div>
    <div class = "form_container">
        <h1 class = "audit_form_title">- 商店删除审核 -</h1>
        <el-table :data="delData" class = "table" >
        <el-table-column prop="date" label="注册时间" width="130" class = "tableColumn"/>
        <el-table-column prop="storename" label="店名" width="120" />
        <el-table-column prop="category" label="商品类别" width="120" />
        <el-table-column prop="intro" label="商店简介" width="120" />
        <el-table-column prop="id" label="店铺编号" width="120" />
        <el-table-column prop="idnumber" label="店主身份证号" width="120" />
        <el-table-column prop="fund" label="注册资金" width="120" />
        <el-table-column prop="address" label="备案地址" width="200" />
        <el-table-column prop="state" label="状态" width="110">
            <template v-slot="scope">
                <el-tag v-if="scope.row.state == 2" type="warning">可批准</el-tag>
                <el-tag v-if="scope.row.state == 3" type="danger">无资格</el-tag>
                <el-tag v-if="scope.row.state == 20" type="success">已批准</el-tag>
                <el-tag v-if="scope.row.state == 30" type="danger">已驳回</el-tag>
            </template>
        </el-table-column> 
        <el-table-column fixed="right" label="操作" width="200">
            <template v-slot = "scope">
            <el-button type="success" size="small" @click="delApprove(scope.row)" :disabled="scope.row.state != 2">
                批准
            </el-button>
            <el-button type="danger" size="small" @click="delReject(scope.row)" :disabled="scope.row.state != 2 && scope.row.state != 3">
                驳回
            </el-button>
            </template>
        </el-table-column>
        </el-table>
    </div>
    <div class = "bottom_space">
    </div>
    </div>
</template>
  
<script>
  export default{
    
    data(){
        return {
            appData: [],
            delData: [],
        };
        
    },
    methods:{
        appApprove(row){
            console.log(row);
            row.state = 1;
            this.$axios.post('/store_pass', row).then((response) => {
                if (response.data.code === 200) {
                        this.$alert(response.data.message, '操作成功', {
                        confirmButtonText: '确定',
                    })
                }
            });
        },
        appReject(row){
            console.log(row);
            row.state = 10;
            this.$axios.post('/store_not_pass', row).then((response) => {
                if (response.data.code === 200) {
                        this.$alert(response.data.message, '操作成功', {
                        confirmButtonText: '确定',
                    })
                }
            });
        },
        delApprove(row){
            console.log(row);
            row.state = 20;
            this.$axios.post('/store_pass_delete', row).then((response) => {
                if (response.data.code === 200) {
                        this.$alert(response.data.message, '操作成功', {
                        confirmButtonText: '确定',
                    })
                }
            });
        },
        delReject(row){
            console.log(row);
            row.state = 30;
            this.$axios.post('/store_not_pass_delete', row).then((response) => {
                if (response.data.code === 200) {
                        this.$alert(response.data.message, '操作成功', {
                        confirmButtonText: '确定',
                    })
                }
            });
        }
    },
    mounted() {
            //this.storelist = this.Data.value.data.records;
            let enter= {
                pageNo:1,//!!
                pageSize:100,
                keyword: null
                }
            this.$axios.post('/store_new_list', enter).then((response) => {
            this.appData = response.data.data.records;
            });
            this.$axios.post('/store_delete_list', enter).then((response) => {
            this.delData = response.data.data.records;
            });
    },
  }
 
  


</script>

<style>
    .table{
        width: 100%;
        background-color:rgba(231, 235, 239, 0.3);
    }
    .form_container{
        margin-left:5%;
        margin-right: 5%;
        /* background-color:rgba(231, 235, 239, 0.6); */
        /* text-align: -webkit-center; */
    }
    .table_column{
        text-align: center;
    }
    .audit_form_title{
        font-size: 30px;
        margin-top: 30px;
        letter-spacing: 5px;
        text-align: center;
        line-height: 30px;
        color: rgb(48, 48, 48);
    }
    #pagebox{
        width: 90%;
        min-height: 80%;
        margin-top: 30px;
        margin-left: 5%;
        margin-right: 5%;
        margin-bottom: 100px;
        background-color:rgba(255, 255, 255, 0.9);
        box-shadow: 10px 10px 20px rgba(90, 91, 96, 0.3);
        border-radius: 10px;
    }
    .bottom_space{
        height: 100px;
    }
    #audit_page_title{
        font-size: 40px;
        margin-top: 30px;
        letter-spacing: 10px;
        text-align: center;
        line-height: 30px;
        color: rgb(48, 48, 48);
        text-shadow: 3px 3px 3px rgba(90, 91, 96, 0.3);
    }
</style>