<template>
<div class="ledger-bg">
    <el-container>
        <el-header>
            <h1 class = "ledger-title">流水记录</h1>
            
            <el-row :gutter="0">
            <el-col :span="8"><div class="grid-content ep-bg-purple" /></el-col>
            <el-col :span="8"><div class="grid-content ep-bg-purple" /></el-col>
            <el-col :span="4"><div class="grid-content ep-bg-purple" /></el-col>
            <el-col :span="4"><div class="grid-content ep-bg-purple" />
                
            </el-col>           
            </el-row>

            
        </el-header>
        <el-header style="margin-bottom: 10px;">
            <el-row :gutter="0">
            <el-col :span="8"><div class="grid-content ep-bg-purple" />
                <span style="font-size:20px;font-weight:bold;text-align: right;">
                   &ensp;&ensp;&ensp;&ensp;纯利润: {{ profit }} 元
                </span>
            </el-col>
            <el-col :span="8"><div class="grid-content ep-bg-purple" />
                <div class="ledger-select" style="text-align: center; margin-bottom: 20px;">
                <el-radio-group v-model="radio1" size="large" >
                    <el-radio-button :label="leftTitle" @click="handleLeftTitle()"/>
                    <el-radio-button :label="rightTitle" @click="handleRightTitle()"/>
                </el-radio-group>
                </div>
            </el-col>
            <el-col :span="4"><div class="grid-content ep-bg-purple" />
                
            </el-col>
            <el-col :span="4"><div class="grid-content ep-bg-purple" />
                <div class="ledger-time" style="align: right; margin-top: 10px;">
                <el-radio-group v-model="radio2">
                    <el-radio-button label="全部" @click="handleAllData()"/>
                    <el-radio-button label="近七天" @click="hangdleWeekData()"/>
                    <el-radio-button label="近一个月" @click="handleMonthData()"/>
                </el-radio-group>
            </div>
            </el-col>
            
            </el-row>

            
            
        </el-header>
        <el-main>
            <el-table :data="ledgerData" style="width: 94%; margin-left:3%; margin-right: 3%;">
                <el-table-column prop="account_out_id" label="转出方" width="250" />
                <el-table-column prop="account_in_id" label="转入方" width="250" />
                <el-table-column prop="money" label="金额" width="230" />
                <el-table-column prop="timestamp" label="时间" width="300" />
                <el-table-column prop="message" label="备注信息" />
            </el-table>
        </el-main>
    </el-container>
</div>
</template>

<script>
export default{
    data(){
        return {
            identity: localStorage.getItem('token'),
            idList: [],
            currentid:-1,
            profit: 0,

            leftTitle: "个人账户",
            rightTitle: "商店账户",
            radio1: "个人账户",
            radio2: "全部",

            ledgerData:[],
        }
    },
    methods:{
        handleLeftTitle(){
            if(this.identity == "admin"){
                this.radio1 = "利润账户";
            }
            else{
                this.radio1 = "个人账户";
            }
            this.radio2 = "全部";
            this.handleAllData();
        },
        handleRightTitle(){
            if(this.identity == "admin"){
                this.radio1 = "中间账户";
            }
            else{
                this.radio1 = "商店账户";
            }
            this.radio2 = "全部";
            this.handleAllData();
        },
        getCurrentId(){
            if(this.radio1 == "个人账户"){
                for(let i=0;i<this.idList.length;i++){
                    if(this.idList[i].type == "user"){
                        this.currentid = this.idList[i].id;
                        break;
                    }                  
                }
            }else if(this.radio1 == "商店账户"){
                for(let i=0;i<this.idList.length;i++){
                    if(this.idList[i].type == "shop"){
                        this.currentid = this.idList[i].id;
                        break;
                    }                  
                }
            }else if(this.radio1 == "利润账户"){
                for(let i=0;i<this.idList.length;i++){
                    if(this.idList[i].type == "profit"){
                        this.currentid = this.idList[i].id;
                        break;
                    }                  
                }
            }else if(this.radio1 == "中间账户"){
                for(let i=0;i<this.idList.length;i++){
                    if(this.idList[i].type == "intermediate"){
                        this.currentid = this.idList[i].id;
                        break;
                    }                  
                }
            }
        },
        handleAllData(){
            this.getCurrentId();
            this.$axios
            ({
                method: "post",
                url:"/transfer_list_id?id=" + this.currentid.toString(),
                data:{
                    pageNo:1,
                    pageSize:10000,
                    keyword: null
                },             
            })
            .then(response => {
                console.log("all:",response);
                this.ledgerData = response.data.data.records;
                this.getAllProfit();
            });
        },
        hangdleWeekData(){
            this.$axios
            ({
                method: "post",
                url:"/transfer_list_id_week?id=" + this.currentid.toString(),
                data:{
                    pageNo:1,
                    pageSize:10000,
                    keyword: null
                },             
            })
            .then(response => {
                this.ledgerData = response.data.data.records;
                this.getWeekProfit();
            });
        },
        handleMonthData(){
            this.$axios
            ({
                method: "post",
                url:"/transfer_list_id_month?id=" + this.currentid.toString(),
                data:{
                    pageNo:1,
                    pageSize:10000,
                    keyword: null
                },             
            })
            .then(response => {
                this.ledgerData = response.data.data.records;
                this.getMonthProfit();
            });
        },
        getAllProfit(){
            this.$axios
            ({
                method: "post",
                url:"/transfer_profit_id?id=" + this.currentid.toString(),          
            })
            .then(response => {
                this.profit = response.data;
            });
        },
        getWeekProfit(){
            this.$axios
            ({
                method: "post",
                url:"/transfer_profit_id_week?id=" + this.currentid.toString(),          
            })
            .then(response => {
                this.profit = response.data;
            });
        },
        getMonthProfit(){
            this.$axios
            ({
                method: "post",
                url:"/transfer_profit_id_month?id=" + this.currentid.toString(),          
            })
            .then(response => {
                this.profit = response.data;
            });
        },
    },
    mounted(){
        if(this.identity == "admin"){
            this.leftTitle = "利润账户";
            this.rightTitle = "中间账户";
            this.radio1 = "利润账户";
        }

        this.$axios
            ({
                method: "post",
                url:"/account_list?id="+localStorage.getItem('userid').toString(),
                data:{
                    pageNo:1,
                    pageSize:10000,
                    keyword: null
                },             
            })
            .then(response => {
                this.idList = response.data.data.records;
                console.log("idlist:",this.idList);
                this.handleAllData();
            });
    },
}
</script>

<style>
.ledger-bg{
    /* display: flex; */
    width: 90%;
    min-height: 80%;
    margin-top: 30px;
    margin-left: 5%;
    margin-right: 5%;
    background-color: rgba(255, 255, 255,0.9);
    border-radius: 10px;
}
.ledger-title{
    font-size: 30px;
    margin-top: 30px;
    letter-spacing: 5px;
    text-align: center;
    line-height: 30px;
    color: rgb(48, 48, 48);
}


</style>