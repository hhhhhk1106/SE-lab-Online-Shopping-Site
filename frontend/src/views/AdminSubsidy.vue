<template>
    <div id="pagebox">
    <br>
    <h1 id = "audit_page_title">活动管理</h1>
    <br>
    <div class = "form_container">
        <el-row>
            <div class="flex-grow" ></div>
            <el-button type="success" size="large" @click="this.addSubsidyFormVisible = true; clearAddSubsidyForm();">
                新建活动
            </el-button>
        </el-row>
        <el-table :data="subsidyData" class = "table">
        <el-table-column prop="id" label="活动编号" width="80" class = "tableColumn"/>
        <el-table-column prop="start_time" label="开始时间" width="120" />
        <el-table-column prop="end_time" label="结束时间" width="120" />
        <el-table-column prop="cate_list" label="商品类别" width="120" />
        <el-table-column prop="fund" label="资金" width="100" />
        <el-table-column prop="full" label="满" width="80" />
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
            <el-button type="primary" size="small" @click="this.updateSubsidyFormVisible = true; getUpdateSubsidyForm(scope.row);">
                修改
            </el-button>
            <el-button type="success" size="small" @click="openSubsidy(scope.row)">开启</el-button>
            </template>
        </el-table-column>
        </el-table>
    </div>
    
    <div class = "bottom_space">
    </div>

    </div>
    <!-- 新建弹窗 -->
    <el-dialog title="新建活动" v-model="addSubsidyFormVisible">
        <el-form
        :model="newSubsidyForm"
        ref="newSubsidyForm"
        :size="formSize"
        >
            <el-form-item label="起始时间：" prop = "start_time">
                <el-date-picker
                    v-model="newSubsidyForm.start_time"
                    type="date"
                    placeholder="Pick a date"
                    style="width: 100%"
                    value-format="YYYY-MM-DD"
                />
            </el-form-item>
            <el-form-item label="结束时间：" prop = "end_time">
                <el-date-picker
                    v-model="newSubsidyForm.end_time"
                    type="date"
                    placeholder="Pick a date"
                    style="width: 100%"
                    value-format="YYYY-MM-DD"
                />
            </el-form-item>
            <el-form-item label="商品类别" prop = "cate_list">
                <el-input v-model="newSubsidyForm.cate_list"></el-input>
            </el-form-item>
            <el-form-item label="活动资金" prop = "fund">
                <el-input v-model="newSubsidyForm.fund"></el-input>
            </el-form-item>
            <el-form-item label="满" prop = "full">
                <el-input v-model="newSubsidyForm.full"></el-input>
            </el-form-item>
            <el-form-item label="减" prop = "discount">
                <el-input v-model="newSubsidyForm.discount"></el-input>
            </el-form-item>
            <el-form-item label="注册资金阈值" prop = "fund_limit">
                <el-input v-model="newSubsidyForm.fund_limit"></el-input>
            </el-form-item>
            <el-form-item label="月销量阈值" prop = "sales_limit">
                <el-input v-model="newSubsidyForm.sales_limit"></el-input>
            </el-form-item>
            <el-form-item label="销售额阈值" prop = "saleroom_limit">
                <el-input v-model="newSubsidyForm.saleroom_limit"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="addSubsidyFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="addSubsidyFormVisible = false, addSubsidy()">确 定</el-button>
        </div>
    </el-dialog>
    <!-- 修改弹窗 -->
    <el-dialog title="修改活动" v-model="updateSubsidyFormVisible">
        <el-form
        :model="updateSubsidyForm"
        ref="updateSubsidyForm"
        :size="formSize"
        >
            <el-form-item label="活动编号" prop = "id" disabled="true">
                <el-input v-model="updateSubsidyForm.id"></el-input>
            </el-form-item>
            <el-form-item label="起始时间：" prop = "start_time">
                <el-date-picker
                    v-model="updateSubsidyForm.start_time"
                    type="date"
                    placeholder="Pick a date"
                    style="width: 100%"
                    value-format="YYYY-MM-DD"
                />
            </el-form-item>
            <el-form-item label="结束时间：" prop = "end_time">
                <el-date-picker
                    v-model="updateSubsidyForm.end_time"
                    type="date"
                    placeholder="Pick a date"
                    style="width: 100%"
                    value-format="YYYY-MM-DD"
                />
            </el-form-item>
            <el-form-item label="商品类别" prop = "cate_list">
                <el-input v-model="updateSubsidyForm.cate_list"></el-input>
            </el-form-item>
            <el-form-item label="活动资金" prop = "fund">
                <el-input v-model="updateSubsidyForm.fund"></el-input>
            </el-form-item>
            <el-form-item label="满" prop = "full">
                <el-input v-model="updateSubsidyForm.full"></el-input>
            </el-form-item>
            <el-form-item label="减" prop = "discount">
                <el-input v-model="updateSubsidyForm.discount"></el-input>
            </el-form-item>
            <el-form-item label="注册资金阈值" prop = "fund_limit">
                <el-input v-model="updateSubsidyForm.fund_limit"></el-input>
            </el-form-item>
            <el-form-item label="月销量阈值" prop = "sales_limit">
                <el-input v-model="updateSubsidyForm.sales_limit"></el-input>
            </el-form-item>
            <el-form-item label="销售额阈值" prop = "saleroom_limit">
                <el-input v-model="updateSubsidyForm.saleroom_limit"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="updateSubsidyFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="updateSubsidyFormVisible = false, updateSubsidy()">确 定</el-button>
        </div>
    </el-dialog>
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
        openSubsidy(row){
            this.$axios.post('/subsidy_open', row).then((response) => {
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
        addSubsidy(){
            let Data = {
                start_time: this.newSubsidyForm.start_time.toString(),
                end_time: this.newSubsidyForm.end_time.toString(),
                cate_list: this.newSubsidyForm.cate_list.toString(),
                fund: parseFloat(this.newSubsidyForm.fund.toString()),
                full: parseFloat(this.newSubsidyForm.full.toString()),
                discount: parseFloat(this.newSubsidyForm.discount.toString()),
                fund_limit: parseFloat(this.newSubsidyForm.fund_limit.toString()),
                sales_limit: parseInt(this.newSubsidyForm.sales_limit.toString()),
                saleroom_limit: parseFloat(this.newSubsidyForm.saleroom_limit.toString()),
            }
            this.$axios.post('/subsidy_add', Data).then((response) => {
                if (response.data.code === 200) {
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
        updateSubsidy(){
            let Data = {
                id: this.updateSubsidyForm.id,
                start_time: this.updateSubsidyForm.start_time.toString(),
                end_time: this.updateSubsidyForm.end_time.toString(),
                cate_list: this.updateSubsidyForm.cate_list.toString(),
                fund: parseFloat(this.updateSubsidyForm.fund.toString()),
                full: parseFloat(this.updateSubsidyForm.full.toString()),
                discount: parseFloat(this.updateSubsidyForm.discount.toString()),
                fund_limit: parseFloat(this.updateSubsidyForm.fund_limit.toString()),
                sales_limit: parseInt(this.updateSubsidyForm.sales_limit.toString()),
                saleroom_limit: parseFloat(this.updateSubsidyForm.saleroom_limit.toString()),
            }
            this.$axios.post('/subsidy_update', Data).then((response) => {
                if (response.data.code === 200) {
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
        clearAddSubsidyForm(){
            this.newSubsidyForm.start_time = "";
            this.newSubsidyForm.end_time = "";
            this.newSubsidyForm.cate_list = "";
            this.newSubsidyForm.fund = "";
            this.newSubsidyForm.full = "";
            this.newSubsidyForm.discount = "";
            this.newSubsidyForm.fund_limit = "";
            this.newSubsidyForm.sales_limit = "";
            this.newSubsidyForm.saleroom_limit = "";
        },
        getUpdateSubsidyForm(row){
            this.updateSubsidyForm.id = row.id;
            this.updateSubsidyForm.start_time = row.start_time;
            this.updateSubsidyForm.end_time = row.end_time;
            this.updateSubsidyForm.cate_list = row.cate_list;
            this.updateSubsidyForm.fund = row.fund;
            this.updateSubsidyForm.full = row.full;
            this.updateSubsidyForm.discount = row.discount;
            this.updateSubsidyForm.fund_limit = row.fund_limit;
            this.updateSubsidyForm.sales_limit = row.sales_limit;
            this.updateSubsidyForm.saleroom_limit = row.saleroom_limit;
        },
        
    },
    mounted() {
            //this.storelist = this.Data.value.data.records;
            let enter= {
                pageNo:1,//!!
                pageSize:100,
                keyword: null
                }
            this.$axios.post('/subsidy_list_all', enter).then((response) => {
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