<template>
    <div id="pagebox">
    <br>
    <h1 id = "audit_page_title">活动申请</h1>
    <br>
    <div class = "form_container">
        <el-table :data="subsidyData" class = "table">
        <el-table-column prop="id" label="活动编号" width="80" class = "tableColumn"/>
        <el-table-column prop="start_time" label="开始时间" width="120" />
        <el-table-column prop="end_time" label="结束时间" width="120" />
        <el-table-column prop="cate_list" label="商品类别" width="120" />
        <el-table-column prop="fund" label="资金" width="100" />
        <el-table-column prop="full" label="满" width="70" />
        <el-table-column prop="discount" label="减" width="70" />
        <el-table-column prop="fund_limit" label="注册资金阈值" width="120" />
        <el-table-column prop="sales_limit" label="月销量阈值" width="120" />
        <el-table-column prop="saleroom_limit" label="销售额阈值" width="120" />
        <el-table-column prop="state" label="状态" width="120">
            <template v-slot="scope">
                <el-tag v-if="scope.row.state == 0" type="warning">新建</el-tag>
                <el-tag v-if="scope.row.state == 1" type="success">进行中</el-tag>
                <el-tag v-if="scope.row.state == 2" type="danger">已结束</el-tag>
                <el-tag v-if="scope.row.state == 3" type="danger">金额不够满减</el-tag>
            </template>
        </el-table-column> 
        <el-table-column fixed="right" label="操作" width="200">
            <template v-slot = "scope">
            <el-button type="success" size="small" @click="applySubsidy(scope.row)">
                申请参与
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
            newSubsidyForm:{
                start_time:"",
                end_time:"",
                cate_list:"",
                fund:"",
                full:"",
                discount:"",
                fund_limit:"",
                sales_limit:"",
                saleroom_limit:"",
            },
            updateSubsidyForm:{
                id:0,
                start_time:"",
                end_time:"",
                cate_list:"",
                fund:"",
                full:"",
                discount:"",
                fund_limit:"",
                sales_limit:"",
                saleroom_limit:"",
            },
            addSubsidyFormVisible: false,
            updateSubsidyFormVisible: false,
            subsidyData: [],
        };
        
    },
    methods:{
        applySubsidy(row){
            this.$axios.post('/shop_subsidy_apply', row).then((response) => {
                if (response.data.code === 200) {
                    row.state = 1;
                    this.$alert(response.data.message, '操作成功', {
                        confirmButtonText: '确定',
                    })
                }
                else {
                    this.$alert(response.data.message, '操作失败', {
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
            this.$axios.post('/subsidy_list_now', enter).then((response) => {
                if (response.data.code === 200){
                    this.subsidyData = response.data.data.records;
                }
                else {
                    this.$alert(response.data.message, '读取活动信息失败', {
                        confirmButtonText: '确定',
                    })
                }
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