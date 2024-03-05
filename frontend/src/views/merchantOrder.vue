<template>
    <div id="pagebox">
    <br>
    <h1 id = "audit_page_title">商户订单</h1>
    <br>
    <div class = "form_container">
        <el-table :data="ordData" class = "table">
        <el-table-column prop="id" label="订单编号" width="130" class = "tableColumn"/>
        <el-table-column prop="category_name" label="商品名称" width="120" />
        <el-table-column prop="category_price" label="商品单价" width="90" />
        <el-table-column prop="quantity" label="购买数量" width="90" />
        <el-table-column prop="sum" label="订单总价" width="90" />
        <el-table-column prop="buy_time" label="购买时间" width="210" />
        <el-table-column prop="address" label="收货地址" width="120" />
        <el-table-column prop="buyer_name" label="收货人" width="80" />
        <el-table-column prop="phone" label="电话" width="130" />
        <el-table-column prop="state" label="状态" width="110">
            <template v-slot="scope">
                <el-tag v-if="scope.row.state == 0" type="danger">待支付</el-tag>
                <el-tag v-if="scope.row.state == 1" type="warning">待发货</el-tag>
                <el-tag v-if="scope.row.state == 2" type="warning">待收货</el-tag>
                <el-tag v-if="scope.row.state == 3" type="success">已完成</el-tag>
                <el-tag v-if="scope.row.state == 4" type="success">已撤销</el-tag>
                <el-tag v-if="scope.row.state == 5" type="success">已退款</el-tag>
                <el-tag v-if="scope.row.state == 6" type="warning">退款中</el-tag>
                <el-tag v-if="scope.row.state == 7" type="danger">已删除</el-tag>
            </template>
        </el-table-column> 
        <el-table-column fixed="right" label="操作" width="200">
            <template v-slot = "scope">
            <el-button type="success" size="small" @click="orderDeliver(scope.row)" v-if="scope.row.state == 1">发货</el-button>
            <el-button type="warning" size="small" @click="refundPass(scope.row)" v-if="scope.row.state == 6">退款</el-button>
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
            ordData: [],
        };
        
    },
    methods:{
        getBuyerInfo(addr_id, ordData_i){
            this.$axios
            ({
                method: "post",
                url: "/address_info_id",
                data: addr_id.toString(),
                headers:{
                    'Content-Type': 'text/plain'
                },
            })
            .then(response => {
                if (response.data.code === 200) {
                    console.log(response.data.data);
                    this.ordData[ordData_i].address = response.data.data.address;
                    this.ordData[ordData_i].buyer_name = response.data.data.name;
                    this.ordData[ordData_i].phone = response.data.data.phone;
                }
                else{
                }
            })
            .catch(failResponse => {
            })
            // this.$axios.post("/address_info_id", addr_id.toString()).then( (response)=>{
            //         this.ordData[ordData_i].address = response.data.data;
            //     }
            // );
        },
        getCateInfo(category_id, ordData_i){
            this.$axios
            ({
                method: "post",
                url: "/category_info",
                data: category_id.toString(),
                headers:{
                    'Content-Type': 'text/plain'
                },
            })
            .then(response => {
                if (response.data.code === 200) {
                    console.log(response.data.data);
                    this.ordData[ordData_i].category_name = response.data.data.name;
                    this.ordData[ordData_i].category_price = response.data.data.price;
                    this.ordData[ordData_i].sum = this.ordData[ordData_i].category_price * this.ordData[ordData_i].quantity;
                    
                }
                else{
                }
            })
            .catch(failResponse => {
            })
        },
        /* 获取网名
        getUsername(user_id, ordData_i){
            this.$axios
            ({
                method: "post",
                url: "/user_info_id",
                data: user_id.toString(),
                headers:{
                    'Content-Type': 'text/plain'
                },
            })
            .then(response => {
                if (response.data.code === 200) {
                    this.ordData[ordData_i].address = response.data.data;
                }
                else{
                }
            })
            .catch(failResponse => {
            })
            
        },
        */
        orderDeliver(row){
            console.log(row);
            
            this.$axios.post('/order_deliver', row).then((response) => {
                if (response.data.code === 200) {
                        row.state = 2;
                        this.$alert(response.data.message, '操作成功', {
                        confirmButtonText: '确定',
                    })
                }
                else{
                    this.$alert(response.data.message, '操作失败', {
                        confirmButtonText: '确定',
                    })
                }
            });
        },
        refundPass(row){
            console.log(row);
            this.$axios.post('/order_refund_pass', row).then((response) => {
                if (response.data.code === 200) {
                        row.state = 5;
                        this.$alert(response.data.message, '操作成功', {
                        confirmButtonText: '确定',
                    })
                }
                else{
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
            this.$axios.post('/order_deal_shop', enter).then((response) => {
                this.ordData = response.data.data.records;
                for (let i = 0; i < this.ordData.length; i++){
                    this.getCateInfo(this.ordData[i].category_id, i);
                    // this.getUsername(this.ordData[i].ownerid, i);
                    this.getBuyerInfo(this.ordData[i].addr_id, i);
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