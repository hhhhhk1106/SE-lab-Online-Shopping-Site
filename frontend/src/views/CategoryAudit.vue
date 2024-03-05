<template>
    <div id="pagebox">
    <br>
    <h1 id = "audit_page_title">商品审核</h1>
    <br>
    <div class = "form_container">
        <h1 class = "audit_form_title">- 新增商品审核 -</h1>
        <el-table :data="addData" class = "table">
        <el-table-column prop="id" label="申请编号" width="200" class = "tableColumn"/>
        <el-table-column prop="new_category_id" label="新增商品ID" width="200" />
        <el-table-column prop="ownerid" label="店主ID" width="200" />
        <el-table-column prop="operation" label="申请操作" width="240" />
        <el-table-column prop="state" label="状态" width="200">
            <template v-slot="scope">
                <el-tag v-if="scope.row.state == 0" type="warning">待处理</el-tag>
                <el-tag v-if="scope.row.state == 1" type="success">已批准</el-tag>
                <el-tag v-if="scope.row.state == 2" type="danger">已驳回</el-tag>
            </template>
        </el-table-column> 
        <el-table-column fixed="right" label="操作" width="320">
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
        <h1 class = "audit_form_title">- 更新商品审核 -</h1>
        <el-table :data="updData" class = "table" >
        <el-table-column prop="id" label="申请编号" width="200" class = "tableColumn"/>
        <el-table-column prop="old_category_id" label="待更新商品ID" width="140" />
        <el-table-column prop="new_category_id" label="更新后商品ID" width="140" />
        <el-table-column prop="ownerid" label="店主ID" width="120" />
        <el-table-column prop="operation" label="申请操作" width="240" />
        <el-table-column prop="state" label="状态" width="200">
            <template v-slot="scope">
                <el-tag v-if="scope.row.state == 0" type="warning">待处理</el-tag>
                <el-tag v-if="scope.row.state == 1" type="success">已批准</el-tag>
                <el-tag v-if="scope.row.state == 2" type="danger">已驳回</el-tag>
            </template>
        </el-table-column> 
        <el-table-column fixed="right" label="操作" width="320">
            <template v-slot = "scope">
            <el-button type="success" size="small" @click="appApprove(scope.row)" :disabled="scope.row.state != 0">
                批准
            </el-button>
            <el-button type="danger" size="small" @click="appReject(scope.row)" :disabled="scope.row.state != 0">
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
            addData: [],
            updData: []
        };
        
    },
    methods:{
        appApprove(row){
            console.log(row);
            row.state = 1;
            this.$axios.post('/category_pass', row).then((response) => {
                if (response.data.code === 200) {
                    this.$alert(response.data.message, '操作成功', {
                        confirmButtonText: '确定',
                    })
                }
            });
        },
        appReject(row){
            console.log(row);
            row.state = 2;
            this.$axios.post('/category_not_pass', row).then((response) => {
                if (response.data.code === 200) {
                        this.$alert(response.data.message, '操作成功', {
                        confirmButtonText: '确定',
                    })
                }
            });
        },
        
    },
    mounted() {
            //this.storelist = this.Data.value.data.records;
            let enter= {
                pageNo:1,//!!
                pageSize:100,
                keyword: null
                }
            this.$axios.post('/cate_record_register', enter).then((response) => {
            this.addData = response.data.data.records;
            });
            this.$axios.post('/cate_record_update', enter).then((response) => {
            this.updData = response.data.data.records;
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