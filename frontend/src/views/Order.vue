<template>
  <div class="order">
    <el-container>
      <el-header>
        <h1>&ensp;创建订单</h1>
      </el-header>
      <el-main>
        <el-space direction="vertical" :size="0">
          <el-card v-for="item in orderList" :key="item" class="box-card" style="width: 1200px ;height: 140px;" shadow="never">
            <el-container>
              <el-divider direction="vertical" border-style="hidden" />
              <el-aside width="120px">
                <img :src= item.image 
                style="width: 100px; height: 100px" />
              </el-aside>
              <el-main>
                <el-row :gutter="40">
                  <el-col :span="16">
                    <div class="trolleycard"><span style="display:inline-block;font-size:22px;font-weight:bold;">
                        {{item.name}}
                      </span></div>
                  </el-col>
                  <el-col :span="4">
                    <div class="trolleycard"></div>
                  </el-col>
                  <el-col :span="2" :offset="1">
                    <div class="trolleycard">
                      <i> &ensp;&ensp; x{{ item.quantity }}</i>
                    </div>
                  </el-col>
                </el-row>
                <el-row :gutter="26">
                  <el-col :span="4">
                    <div class="trolleycard"><i>单价: {{ item.price }} 元</i></div>
                  </el-col>
                  <el-col :span="3" :offset="16">
                    <div class="trolleycard">
                      <span style="display:inline-block;font-size:18px;font-weight:bold;float:right;text-align:right;">
                        ¥ {{ item.price * item.quantity }}
                      </span>
                    </div>
                  </el-col>
                </el-row>

              </el-main>
            </el-container>
          </el-card>
        </el-space>
      </el-main>
      <el-main>
        <h2>
          &ensp;收货信息
        </h2>

        <el-descriptions class="add-desc" :title="'地址' + (addressIndex+1)" border :column="3" :size="small" :style="blockMargin">
          <template #extra>
            <el-button type="primary" text bg @click="addAddressDialogVisible=true"><el-icon>
                <Plus />
              </el-icon>新增地址</el-button>
            <el-button type="primary" text bg @click="switchAddressDialogVisible=true"><el-icon>
                <Switch />
              </el-icon>切换地址</el-button>
          </template>

          <el-descriptions-item label="姓名">{{ addressData[addressIndex].name }}</el-descriptions-item>
          <el-descriptions-item label="电话">{{ addressData[addressIndex].phone }}</el-descriptions-item>
          <el-descriptions-item label="地址">{{ addressData[addressIndex].address }}
          </el-descriptions-item>
        </el-descriptions>

        <el-dialog v-model="switchAddressDialogVisible" title="切换地址">
          <el-table
            ref="singleTable"
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
          </el-table>

          <template #footer>
            <span class="dialog-footer">
              <el-button @click="switchAddressDialogVisible = false">取消</el-button>
              <el-button type="primary" @click="handleSwitchAddressConfirm()">
                确定
              </el-button>
            </span>
          </template> 
        </el-dialog>

        <el-dialog v-model="addAddressDialogVisible" title="新增地址">
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

      </el-main>
      <el-footer>

        <el-row :gutter="20">
          <el-col :span="17">
            <div class="grid-content bg-purple"></div>
          </el-col>
          <el-col :span="4">
            <div class="grid-content bg-purple">
              <span style="display:inline-block;font-size:21px;font-weight:bold;">
                总价: {{ total }} 元
              </span>
            </div>
          </el-col>
          <el-col :span="3">
            <div class="grid-content bg-purple"><el-button color=red size="large" @click="handleCreateOrder()">提交订单</el-button></div>
          </el-col>
      </el-row>
    </el-footer>
  </el-container>
</div>

<el-dialog
  title="提示"
  v-model="confirmPayVisible"
  width="30%"
  :before-close="handleClose">
  <span>订单创建成功！是否立即支付？</span>
  <template #footer>
    <span class="dialog-footer">
      <el-button @click="turnToOrders()">取消</el-button>
      <el-button type="primary" @click="turnToPay()">
        支付
      </el-button>
    </span>
  </template>
</el-dialog>

<br><br><br></template>



<script>

export default{
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
      good :this.$route.query.goods,
			switchAddressDialogVisible: false,
      addAddressDialogVisible: false,
      confirmPayVisible: false,
      orderList:[],
      total: 0,

      newAddress:{
        id: 0,
        ownerid: 0,
        name: "",
        phone: "",
        address: "",
      },
      preAddressIndex: null,
      currentRow: null,
      addressIndex: 0,
      addressData:[
        {
          name: "",
          tel: "",
          addr: "",
        },
      ],
      singleOrder:{
        ownerid: "",
        addr_id: "",
        category_id: "",
        quantity: "",
        buy_time: "",
        payment: 0,
        allowance: 0,
        state: 0,
      }

		};
	},
  methods:{
    async getOrderList() {
    this.good.sort();
    this.total = 0;
    this.orderList = [];

    const getOrderData = async (good) => {
      const response = await this.$axios({
        method: "post",
        url: "/shoppingCart_get_id",
        data: good.toString(),
        headers: {
          'Content-Type': 'text/plain'
        },
      });

      const orderData = response.data.data;

      const response2 = await this.$axios({
        method: "post",
        url: "/category_info",
        data: orderData.categoryid.toString(),
        headers: {
          'Content-Type': 'text/plain'
        },
      });

      orderData.price = response2.data.data.price;
      orderData.name = response2.data.data.name;
      orderData.imagePath = response2.data.data.imagePath;
      orderData.image = orderData.imagePath.split(",")[0];
      orderData.totalPrice = orderData.price * orderData.quantity;

      return orderData;
    };

    const orderPromises = this.good.map(good => getOrderData(good));
    this.orderList = await Promise.all(orderPromises);

    this.total = this.orderList.reduce((acc, order) => acc + order.totalPrice, 0);
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
    handleCurrentChange(val){
      this.currentRow = val;
      this.preAddressIndex = val.index;
      console.log("index:",val);
    },
    tableRowClassName ({row, rowIndex}) {
      row.index = rowIndex;
    },
    handleSwitchAddressConfirm(){
      this.switchAddressDialogVisible = false;
      this.addressIndex = this.preAddressIndex;
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
    handleCreateOrder(){
      var myDate = new Date;
      var year = myDate.getFullYear(); //获取当前年
      var mon = myDate.getMonth() + 1; //获取当前月
      var date = myDate.getDate(); //获取当前日
      var hours = myDate.getHours(); //获取当前小时
      var minutes = myDate.getMinutes(); //获取当前分钟
      var seconds = myDate.getSeconds(); //获取当前秒
      var time = year + "-" + mon + "-" + date + " " + hours + ":" + minutes + ":" + seconds;

      let Data = [];
      localStorage.setItem('payData', JSON.stringify(Data));
      for(let i=0;i<this.orderList.length;i++){

        let singleOrder = { // 创建一个新的单个订单对象
          addr_id: this.addressData[this.addressIndex].id,
          category_id: this.orderList[i].categoryid,
          quantity: this.orderList[i].quantity,
          buy_time: time,
          payment: 0,
          allowance: 0,
          state: 0
        };

        // Data.push(singleOrder);
        console.log("data:",this.orderList);

        this.$axios
        ({
          method: "post",
          url:"/order_create",
          data:singleOrder,          
        })
        .then(response => {
          console.log( "createOrder:",response);
          Data.push(response.data.data);
          localStorage.setItem('payData', JSON.stringify(Data));
        });
      }
      
      console.log("locsto:",localStorage);
      this.confirmPayVisible = true;
    },
    turnToOrders(){
      this.$router.replace({path: '/user_order'});
    },
    turnToPay(){
      this.$router.replace({path: '/pay'});
    },

  },
  mounted(){
    console.log("passed:",this.$route.query.goods);
    this.getOrderList();
    this.getAddress();
  },
}
</script>



<style>
.order {
  margin: auto;
   background: white; 
  width: 75%;
}
.right {
  float: right;
  width: 120px;
  padding: 10px;
}
.add-desc{
    margin-top: 30px;
    margin-left: 30px;
    margin-right: 30px;
    margin-bottom: 30px;
}

</style>