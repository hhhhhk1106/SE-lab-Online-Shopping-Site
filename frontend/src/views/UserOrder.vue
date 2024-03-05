
<template>
    <div id = "pagebox">
    <br>
    <h1 id = "order_page_title">个人订单</h1>
    <br>
    <!-- <el-tabs v-model="activeName" class="ordertabs">
        <el-tab-pane label="全部" name="first" @click = "this.getAllInfo"></el-tab-pane>
        <el-tab-pane label="待支付" name="second" @click = "this.getStaInfo(0)"></el-tab-pane>
        <el-tab-pane label="待发货" name="third" @click = "this.getStaInfo(1)"></el-tab-pane>
        <el-tab-pane label="待收货" name="fourth" @click = "this.getStaInfo(2)"></el-tab-pane>
        <el-tab-pane label="已完成" name="fifth" @click = "this.getStaInfo(3)"></el-tab-pane>
        <el-tab-pane label="已撤销" name="sixth" @click = "this.getStaInfo(4)"></el-tab-pane>
        <el-tab-pane label="已退款" name="seventh" @click = "this.getStaInfo(5)"></el-tab-pane>
    </el-tabs> -->
    
    <div id = "tabbox">
        <el-button @click = "this.getAllInfo(), this.getRestInfo()">全部</el-button>
        <el-button @click = "this.getStaInfo(0), this.getRestInfo()">待支付</el-button>
        <el-button @click = "this.getStaInfo(1), this.getRestInfo()">待发货</el-button>
        <el-button @click = "this.getStaInfo(2), this.getRestInfo()">待收货</el-button>
        <el-button @click = "this.getStaInfo(3), this.getRestInfo()">已完成</el-button>
        <el-button @click = "this.getStaInfo(4), this.getRestInfo()">已撤销</el-button>
        <el-button @click = "this.getStaInfo(5), this.getRestInfo()">已退款</el-button>
        <!-- <div class="flex-grow" ></div> -->
        <el-button type="primary" @click="pay()" style = "float:right;">批量支付所选订单</el-button>
    </div>
    <div style = "margin-left: 4.16%;">
        <el-checkbox v-model="isselectedall" @change = "changeSelectAll" >全选</el-checkbox>
    </div>
    <!--标签-->
    <el-row :gutter="10" type="flex" justify="center" style="max-width: 100%;">
            <el-col
            v-for="order in orderlistPage"
            :key="order"
            :span="22"
            :offset="0"
            :style="{ marginBottom: '12px' }"
            style="min-height:220px"
            >
            <el-card :body-style="{ padding: '20px' } " shadow="hover" >
                
                <template #header>
                <div class="account-card-header">
                    <span>订单编号：{{order.id}}</span>
                    
                </div>
                </template>
                <el-container>
                    <el-aside width = "220px">
                        <el-checkbox v-model="order.isselected" :disabled= "order.state!=0">.</el-checkbox>
                        <img
                        :src= "order.imageurl"
                        style="width: 200px; height: 200px"
                        />
                    </el-aside>
                    <el-main>
                        <el-row>
                            <!-- <el-tag>爷的ID</el-tag>
                            <span style="margin-right: 25%;">{{order.ownerid}}</span> -->

                            <el-tag>商品名称</el-tag>
                            <span style="margin-right: 25%;">{{order.category_name}}</span>

                            <el-tag>购买数量</el-tag>
                            <span style="margin-right: 25%;">{{order.quantity}}</span>
                            
                            <el-tag v-if="order.subsidy_id != 0" type="success">正在参加活动{{ order.subsidy_id }}</el-tag>
                        </el-row>
                        <el-row>
                            <el-tag>收货地址</el-tag>
                                <!-- ?? -->
                            <span style="margin-right: 40%;">{{order.addr_id}}</span>

                            <el-tag>购买时间</el-tag>
                            <span>{{order.buy_time}}</span>
                        </el-row>
                        <el-row>
                            <el-tag>补贴：</el-tag>
                            <span style="margin-right: 40%;">{{order.allowance}}</span>

                            <el-tag>实付款：</el-tag>
                            <span>{{order.payment}}</span>
                        </el-row>
                        <el-row>
                            <el-tag v-if="order.state == 0" type="danger">待支付</el-tag>
                            <el-tag v-if="order.state == 1" type="warning">待发货</el-tag>
                            <el-tag v-if="order.state == 2" type="warning">待收货</el-tag>
                            <el-tag v-if="order.state == 3" type="success">已完成</el-tag>
                            <el-tag v-if="order.state == 4" type="success">已撤销</el-tag>
                            <el-tag v-if="order.state == 5" type="success">已退款</el-tag>
                            <el-tag v-if="order.state == 6" type="warning">退款中</el-tag>
                            <el-tag v-if="order.state == 7" type="danger">已删除</el-tag>
                        </el-row>
                        <el-row>
                            <el-button v-if="order.state == 0" type = "primary" @click = "payOneOrder(order)">支付</el-button>
                            <el-button v-if="order.state == 0" type = "danger" @click = "cancelOrder(order)">撤销</el-button>
                            <el-button v-if="order.state == 1 || order.state == 2" type = "warning" @click = "refundOrder(order)">退货退款</el-button>
                            <el-button v-if="order.state == 2" type = "success" @click = "confirmOrder(order)">确认收货</el-button>
                            <el-button v-if="order.state == 3 || order.state == 4 || order.state == 5" type = "danger" @click = "deleteOrder(order)">删除</el-button>
                        </el-row>
                    </el-main>
                </el-container>


                <!-- <el-row></el-row> -->

                

                <!-- </el-row> -->
            </el-card>
            </el-col>
        </el-row>
        
        <br>
    
    
    
    
    
    
    <!--页码-->
        <div style="margin:auto ;width: 280px;">
    
            <div class="block">
            <span class="demonstration"></span>
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                background
                :current-page="currentpage"
                :page-sizes="[5, 10, 50, 100]"
                :page-size="pagesize"
                layout="total,  prev, pager, next, jumper"
                :total="total"
                
            >
            
                
            </el-pagination>
            
            </div>
        </div>
    
    <div class = "bottom_space">
    </div>
    </div>
    
</template>
    
    
    
    
    
    
    <script>
    
        export default {
    
        
    
    
            data() {
                return {
                    activeIndex: '1',
                    activeName: 'first',
                    isselectedall: false,

                    orderlist: [],
                    total:0,  //总数据条数
                    currentpage:1,  //当前所在页默认是第一页
                    pagesize:12,  //每页显示多少行数据 默认设置为12
                    orderlistPage:[{ id: 1,ownerid: 1, }],//分页后的当前页数据
                    
    
                    // avatarurl: "https://source.unsplash.com/1600x900/?shop,store,",
                    url: [],
                    fit: "cover",
                    value: "5.0",
    
    
    
    
                    
                };
            },
    
    
            methods: {
                checkSelectAll (){
                    for (let i = 0; i < this.orderlistPage.length; i++){
                        if (!this.orderlistPage[i].isselected && this.orderlistPage[i].state == 0) return false;
                    }
                    return true;
                },
                changeSelectAll (){
                    let tmp = !(this.checkSelectAll());
                    for (let i = 0; i < this.orderlistPage.length; i++){
                        if (this.orderlistPage[i].state == 0)this.orderlistPage[i].isselected = tmp;
                    }
                },
                getItemInfo(category_id, orderlist_i){
                    let imglist = [];
                    this.$axios
                    // .post(rechargePath, parseFloat(this.rechargeForm.money.toString()))
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
                            console.log(category_id);
                            console.log(response.data.data.price);
                            imglist = response.data.data.imagePath.split(",");
                            console.log(imglist[0])
                            this.orderlist[orderlist_i].imageurl = imglist[0];
                            this.orderlist[orderlist_i].category_name = response.data.data.name;
                            this.orderlist[orderlist_i].subsidy_id = response.data.data.subsidy_id;
                        }
                        else{
                        }
                    })
                    .catch(failResponse => {
                    })
                    // this.$axios.post("/category_info", category_id.toString()).then( (response)=>{
                    //     console.log(category_id);
                    //     console.log(response.data.data.price);
                    //     imglist = response.data.data.imagePath.split(",");
                    // }
                    // );
                    
                },
                getAddress(addr_id){
                    this.$axios.post("/address_info_id", addr_id.toString()).then( (response)=>{
                        return response.data.data;
                    }
                    );
                },

                handleClick() {
                    console.log( 'response.code');
                    let enter= {
                    pageNo:1,//!!
                    pageSize:100,
                    keyword: null
                    }
                    this.$axios.post('/store_list', enter).then(response => {
                    console.log( response);
                    });
                    
                },
                // 获取当前页的数据信息
                getPageInfo() {
                    //清空orderlistPage中的数据
                    this.orderlistPage = [];
                    // 获取当前页的数据
                    for (let i = (this.currentpage - 1) * this.pagesize; i < this.total; i++) {
                        //把遍历的数据添加到orderlistPage里面
                        // console.log(this.orderlist[i]);
                        this.orderlistPage.push(this.orderlist[i]);
                        //判断是否达到一页的要求
                        if (this.orderlistPage.length === this.pagesize) break;
                    }
                
                },
                getRestInfo(){
                    for (let i = 0; i < this.orderlist.length; i++){
                        console.log("!");
                        this.getItemInfo(this.orderlist[i].category_id, i);
                        // this.orderlist[i].imageurl = this.getItemPic(this.orderlist[i].category_id);
                        // console.log(this.orderlist[i].imageurl);
                        this.orderlist[i].isselected = false;
                    }
                },
                getAllInfo(){
                    this.currentpage = 1;
                    let that = this;
                    let enter= {
                        pageNo:1,//!!
                        pageSize:100000,
                        keyword: null
                        }
                    that.$axios.post('/order_show_user', enter).then((response) => {
                        if (response.data.code === 200){
                            that.orderlist = response.data.data.records;
                            that.total     = response.data.data.records.length;  
                            this.getRestInfo(); 
                            //获取当前页的数据
                            that.getPageInfo();
                        }         
                        else {
                            this.$alert(response.data.message, '读取订单信息失败', {
                                confirmButtonText: '确定',
                            })
                        }
        
                    
        
                    });
                    
                },
                
                getStaInfo(sta){//读取某个状态的订单
                    this.currentpage = 1;
                    let that = this;
                    let enter= {
                        pageNo:1,//!!
                        pageSize:100000,
                        keyword: null
                    }
                    that.$axios.post('/order_show_user', enter).then((response) => {
                        if (response.data.code === 200){
                            that.orderlist = response.data.data.records;
                            let tmp = [];
                            console.log('!');
                            for (let i = 0; i < that.orderlist.length; i++){
                                if (that.orderlist[i].state == sta){
                                    tmp.push(that.orderlist[i]);
                                }
                            }
                            that.orderlist = tmp;
                            that.total     = that.orderlist.length; 
                            this.getRestInfo(); 
                            that.getPageInfo();
                        }         
                        else {
                            this.$alert(response.data.message, '读取订单信息失败', {
                                confirmButtonText: '确定',
                            })
                        }
                    });
                },
                
                //分页时修改每页的行数,这里会自动传入一个size
                handleSizeChange(size) {
                    //修改当前每页的数据行数
                    this.pagesize = size;
                    //数据重新分页
                    this.getPageInfo();
                },
                //调整当前的页码
                handleCurrentChange(pageNumber) {
                    //修改当前的页码
                    this.currentpage = pageNumber;
                    //数据重新分页
                    this.getPageInfo()
                },
    
                payOneOrder(row){
                    let Data = [];
                    localStorage.setItem('payData', JSON.stringify(Data));
                    // console.log(this.orderlistPage.length);
                    Data.push(row);
                    localStorage.setItem('payData', JSON.stringify(Data));
                    this.$router.replace({path: '/pay'});
                },
                cancelOrder(row){
                    console.log(row);
                    this.$axios.post('/order_cancel', row).then((response) => {
                        if (response.data.code === 200) {
                                row.state = 4;
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
                confirmOrder(row){
                    console.log(row);
                    this.$axios.post('/order_confirm', row).then((response) => {
                        if (response.data.code === 200) {
                                row.state = 3;
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
                deleteOrder(row){
                    //删除之后getpageinfo一下会不会能直接去掉这个订单？最后记得试一下
                    console.log(row);
                    this.$axios.post('/order_delete', row).then((response) => {
                        if (response.data.code === 200) {
                                row.state = 7;
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
                refundOrder(row){
                    console.log(row);
                    this.$axios.post('/order_refund', row).then((response) => {
                        if (response.data.code === 200) {
                                row.state = 6;
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
                pay (){
                    //solution1: 直接支付，感觉展示折扣很不方便
                    //所以采用solution2，存localstorage，单独开页面展示（有点不人性化，但，这就是代价！
                    let Data = [];
                    localStorage.setItem('payData', JSON.stringify(Data));
                    // console.log(this.orderlistPage.length);
                    for (let i = 0; i < this.orderlistPage.length; i++){
                        if (this.orderlistPage[i].isselected && this.orderlistPage[i].state == 0){
                            console.log(this.orderlistPage[i]);
                            Data.push(this.orderlistPage[i]);
                        }
                    }
                    localStorage.setItem('payData', JSON.stringify(Data));
                    this.$router.replace({path: '/pay'});
                },
            },
    
            mounted() {
                
                this.getAllInfo();
                this.getRestInfo();
    
            },
    
            computed:{
                isselectedall: {
                    get(){
                        for (let i = 0; i < this.orderlistPage.length; i++){
                            if (!this.orderlistPage[i].isselected && this.orderlistPage[i].state == 0) return false;
                        }
                        return true;
                    },
                    set(){},
                },
            }
            
        
    
    
    
    
    }
    </script>
    
    
    <style>
        .ordertabs{
            margin-top: 20px;
            margin-left: 50px;
            margin-right: 50px;
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
        
        #tabbox{
            margin-left: 4.16%;
            margin-right: 5%;
        }

        .bottom {
        margin-top: 10px;
        line-height: 16px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        }
    
        .button {
        padding: 0;
        min-height: auto;
        }
    
        .image {
        width: 100%;
        display: block;
        }
    
        .el-card {
          min-width: 100%;
          height: 100%; 
          margin-right: 20px;
          transition: all .5s;
        }
    
        .flex-grow {
        flex-grow: 1;
        }
    
        .text_flow1{
            width: 300px;
            height: 45px;
            overflow: auto; 
            line-height: 16px;      
        }
        .text_flow1::-webkit-scrollbar {
          display: none;
    }
    .bottom_space{
        height: 100px;
    }
    #order_page_title{
        font-size: 40px;
        margin-top: 30px;
        letter-spacing: 10px;
        text-align: center;
        line-height: 30px;
        color: rgb(48, 48, 48);
        text-shadow: 3px 3px 3px rgba(90, 91, 96, 0.3);
    }
    
    
    </style>