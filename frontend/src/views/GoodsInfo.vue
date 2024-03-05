<template>
<div class="center1">
  <div class="common-layout">
    <el-container>  
      <div class="titlebackground"><el-container>

      <el-aside>
        <div class="avatar">
        <el-avatar shape="square" :size="220" :src="avatarurl" :fit="fit" />   
        </div>    
      </el-aside>
             
      <el-main>
        
        
        <el-row :gutter="20">
          <el-col :span="20">
            <div class="grid-content bg-purple">
              <div class="title">
                {{storeInfo.storename}}
                &ensp;
                <el-rate
                    v-model="value"
                    size="large"
                    disabled
                    show-score
                    text-color="#ff9900"
                    score-template="{value} "
                    ></el-rate>                
              </div>
            </div>
          </el-col>
          <el-col :span="4"><div class="grid-content bg-purple"></div></el-col>          
        </el-row>

        

        <el-row :gutter="20">
          <el-col :span="8"><div class="grid-content bg-purple">店主：{{ ownerInfo.username }}</div></el-col>
          <el-col :span="8"><div class="grid-content bg-purple">类别：{{ storeInfo.category }}</div></el-col>
          <el-col :span="8"><div class="grid-content bg-purple">注册时间：{{ storeInfo.date }}</div></el-col>
          
        </el-row>
        <el-row :gutter="20">
          <el-col :span="24"><div class="grid-content bg-purple">简介：{{ storeInfo.intro }}</div></el-col>          
        </el-row>

      </el-main>
    

    </el-container> </div>
    <el-divider></el-divider>

      <el-footer>
        <el-space wrap :size="25" >
          
            <el-card :body-style="{ padding: '0px' }" v-for="good in goodsList" :key="good" class="box-card" style="width: 307px" >                         
              
              <el-carousel trigger="click" height="300px" :autoplay="false" >
                <el-carousel-item v-for="item in good.imageSet" :key="item" >
                  <img
                    :src = item
                    class="image"
                    :fit="fit"
                  /> 
                </el-carousel-item>
              </el-carousel>

              <div style="padding: 14px">
                <span>{{ good.name }}</span><br>
                  <div class="text_flow">
                    <span style="font-size:8px"><i>{{ good.intro }}</i></span>
                  </div>
                  <span 
                    v-if="good.subsidy_id != 0"
                    style="font-size:15px;font-weight: bold;color: brown;"
                  > <el-icon><Coin /></el-icon> 正在参加活动 {{ good.subsidy_id }}  </span>
                  <span 
                    v-if="good.subsidy_id == 0"
                  > &ensp; </span>
                <div class="bottom">
                  <span>¥{{ good.price }}</span>

                  <div class="button-right">
                    <el-popover :visible=good.purchaseVisible placement="top" :width="160">
                      <span style="font-size:14px"><el-icon><Goods /></el-icon>&ensp;购买数量</span>
                      <div style="align:center ;padding: 8px;">
                        
                      <el-input-number
                          v-model="purchaseNum"
                          :min="1"
                          size="small"
                          controls-position="right"
                          @change="handleChange"
                        />
                      </div>
                      <div style="text-align: center; margin: 0">
                        <el-button size="small" text @click="handlePurchaseVisible(good)">取消</el-button>
                        <el-button size="small" color="red" @click="handleImmOrder(good)">确定</el-button>
                      </div>
                      <template #reference>
                        <el-button  text class="button" @click="handlePurchaseVisible(good)"><el-icon><Goods /></el-icon>&ensp;购买</el-button>
                      </template>
                    </el-popover>  
                  <el-button text class="button" @click="handleAddTrolley(good)"><el-icon><ShoppingCart /></el-icon>&ensp;加入购物车</el-button>
                  </div>
                </div>
              </div>
            </el-card>
          
        </el-space>
      </el-footer>
     
    </el-container>
  </div>
</div>

<el-dialog title="购买商品" v-model="immPurchaseDialogVisible" style="width: 80%;">

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

        <el-dialog v-model="switchAddressDialogVisible" title="切换地址" append-to-body>
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
            <span class="dialog-footer" >
              <el-button @click="addAddressDialogVisible = false">取消</el-button>
              <el-button type="primary" @click="handleAddAddressConfirm()">
                确定
              </el-button>
            </span>
          </template>
        </el-dialog>

      </el-main>
  </el-container>


  <template #footer >
      <el-button @click="immPurchaseDialogVisible = false">取消</el-button>
      <el-button type="primary" @click="handleImmPurchase">提交订单</el-button>
  </template>
</el-dialog>

</template>

<script>
export default {
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
			goodsList: [],
			storeInfo: [],
			ownerInfo: [],

      orderList:[],

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
      nowGood: null,

			avatarurl: "https://source.unsplash.com/1600x900/?shop,store,",
			url: [],
			fit: "cover",
			value: "5.0",
      purchaseNum: 1,
      purchaseVisible: false,
      immPurchaseDialogVisible: false,
      switchAddressDialogVisible: false,
      addAddressDialogVisible: false,
      confirmPayVisible: false,
		};
	},

	methods:{
		getGoodsInfo(){
            this.$axios
            ({
                method: "post",
                url:"/category_list_shop?shopID="+this.$route.params.store_id,
                data:{
                  pageNo:1,
                  pageSize:10000,
                  keyword: null,
                }, 
				         
            })
            .then(response => {
                for(let i=0;i<response.data.data.records.length;i++){
                  if(response.data.data.records[i].state == 1 || response.data.data.records[i].state == 3){
                    this.goodsList.push(response.data.data.records[i]);
                  }
                }
				        for( let i=0;i<this.goodsList.length;i++){
                  this.goodsList[i]["imageSet"] = this.goodsList[i].imagePath.split(",");
                  this.goodsList[i]["purchaseVisible"] = false;
                }
                console.log( "goods",this.goodsList);
            });
		},
		getStoreInfo(){
			this.$axios
            ({
                method: "post",
                url:"/store_info_id",
                data:this.$route.params.store_id,
				headers:{
                        'Content-Type': 'text/plain'
                    },	         
            })
            .then(response => {
                this.storeInfo=response.data.data;
				console.log("商店",this.storeInfo);

				this.$axios
				({
					method: "post",
					url:"/user_info_id",
					data:this.storeInfo.ownerid,
					headers:{
							'Content-Type': 'text/plain'
						},	
							
				})
				.then(response2 => {
					this.ownerInfo=response2.data.data;
					console.log("name",this.ownerInfo);
				});
            });
		},
		handleAddTrolley(good){
			this.$axios
            ({
                method: "post",
                url:"/shoppingCart_register?quantity=1",
                data:good,             
            })
            .then(response2 => {
                this.$message({
				message: '添加成功',
				type: 'success'
				});
            });
		},
    handlePurchaseVisible(good){
      good.purchaseVisible = !good.purchaseVisible;
    },
    handleImmOrder(good){
      this.immPurchaseDialogVisible = true;
      this.nowGood = good;
      this.nowGood["quantity"] = this.purchaseNum;
  
      this.orderList[0] = this.nowGood;
      console.log("nowg:",this.nowGood);
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
    handleImmPurchase(){
      
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

      let singleOrder = { // 创建一个新的单个订单对象
        addr_id: this.addressData[this.addressIndex].id,
        category_id: this.nowGood.id,
        quantity: this.nowGood.quantity,
        buy_time: time,
        payment: 0,
        allowance: 0,
        state: 0
      };

      
      console.log("data:",singleOrder);

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
        this.$router.replace({path: '/pay'});
      });
      
      
    },

	},

	mounted(){
		this.getGoodsInfo();
		this.getStoreInfo();
    this.getAddress();
	},


}
</script>

<style>
.center1 {
  margin: auto;
  /*background: lime;*/
  width: 86%;
}

.avatar{
  position: relative;
  margin: auto ;
  top: 0; left: 0; bottom: 0; right: 0;
}

.title{
  font-size: 30px;
  font-weight: bold;
}

.image {
  width: 100%;
  height: inherit;
  
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.button {
  padding: 0;
  min-height: auto;
}

.el-row {
  margin-bottom: 20px;
  /* &:last-child {
    margin-bottom: 0;
  } */
}
.el-col {
  border-radius: 4px;
}

.titlebackground{
  background: rgba(164, 156, 211, 0.5);
}

.text_flow{
  width: 300px;
  height: 55px;
  overflow: auto;       
}
.text_flow::-webkit-scrollbar {
  	display: none;
}

.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: none;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
.button-right {
  float: right;
}
</style>