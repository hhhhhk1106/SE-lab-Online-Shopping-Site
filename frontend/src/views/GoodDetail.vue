<template>
  <div class="good-detail">
    <el-container>      
      <el-container>
        <el-aside width="400px">
            Aside
            <el-carousel trigger="click" height="300px" :autoplay="false" >
                <!--el-carousel-item v-for="item in good.imageSet" :key="item" -->
                  <img
                    src = "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
                    class="image"
                    :fit="fit"
                  /> 
                <!--/el-carousel-item-->
            </el-carousel>
        </el-aside>
        <el-main>
            <h1>商品名称</h1>
            <br>
            <h2>¥99</h2>
            <br><br>
            <el-button color = orange><el-icon><ShoppingCart /></el-icon>&ensp;加入购物车</el-button>
            <el-button color = #FF5C42>购买</el-button>
        </el-main>
      </el-container>
      <el-footer>Footer</el-footer>
    </el-container>
  </div>
</template>


<script>
export default{
    data() {
		return {
			

		};
	},
}
</script>


<style>
.good-detail {
  margin: auto;
  /* background: lime; */
  width: 86%;
}

</style>