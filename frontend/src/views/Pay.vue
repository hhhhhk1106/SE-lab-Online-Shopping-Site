<template>
<div class="pay-bg">

    <el-result       
        title="支付订单"
      >
      <template #icon>
        <el-icon size="90"><WalletFilled /></el-icon>
        </template>

        <template #extra>
            <br>
            <span style="display:inline-block;font-size:22px;font-weight:bold;">
                共{{ this.num }} 种商品
            </span>
            <br><br>
            <span style="display:inline-block;font-size:22px;font-weight:bold;">
                折扣: {{ this.discount }} 元
            </span>
            <br><br>
            <span style="display:inline-block;font-size:22px;font-weight:bold;">
                实付款: {{ this.payment }} 元
            </span>
            <br><br><br><br>
            <el-button type="primary" size="large" @click = "pay()">支付</el-button>
        </template>
      </el-result>
</div>

</template>



<script>
export default{
    data() {
		return {
			Data: [],
            payment: 0,
            discount: 0,
            num: 0,
		};
	},
    methods: {
        pay (){
            this.$axios.post('/order_pay_more', this.Data).then((response) => {
                if (response.data.code === 200){
                    this.$alert(response.data.message, '支付成功！', {
                        confirmButtonText: '确定',
                    })
                    this.$router.replace({path: '/user_order'});
                }
                else {
                    this.$alert(response.data.message, '支付失败', {
                        confirmButtonText: '确定',
                    })
                }
            });
        },
    },
    mounted(){
        this.Data = JSON.parse(localStorage.getItem('payData'));
        this.num = this.Data.length;
        for (let i = 0; i < this.Data.length; i++){
            console.log(this.Data[i]);
        }
        this.$axios.post('/order_show_discount', this.Data).then((response) => {
            if (response.data.code === 200){
                this.payment = response.data.data.payment;
                this.discount = response.data.data.discount;
            }
            else {
                this.$alert(response.data.message, '读取信息失败', {
                    confirmButtonText: '确定',
                })
            }
        });
    },
}
</script>



<style>
.pay-bg{
        /* display: flex; */
        width: 24%;
        min-height: 60%;
        margin-top: 30px;
        margin-left: 38%;
        margin-right: 38%;
        background-color: rgba(255, 255, 255,1);
        border-radius: 10px;
    }

</style>