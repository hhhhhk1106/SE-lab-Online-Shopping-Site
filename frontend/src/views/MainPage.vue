

<template>

<!--页头菜单-->   
    <!-- <el-menu  
    default-active="activeIndex"
    class="el-menu-demo"
    mode="horizontal"
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04b"
    @click=handleClick()
    >

    <el-menu-item index="0">店铺展示</el-menu-item>

        <div class="flex-grow" ></div>
        
        <el-menu-item index="1" >
            <router-link to="/login">登录</router-link>
        </el-menu-item>

        <el-sub-menu index="2">
            <template #title>注册</template>
            <el-menu-item index="2-1">
                <router-link to="/user_register">用户注册</router-link>
            </el-menu-item>
            <el-menu-item index="2-2">
                <router-link to="/store_register">商店注册</router-link>
            </el-menu-item>
        </el-sub-menu>
        <el-menu-item index="3" title="商店信息审核">信息管理</el-menu-item>
     
    </el-menu>

    <br><br> -->

<!--标签-->
    <el-row :gutter="10" type="flex" justify="center" style="max-width: 100%;">
        <el-col
        v-for="store in storelistPage"
        :key="store"
        :span="5"
        :offset="0"
        :style="{ marginBottom: '12px' }"
        style="height:220px"
        >
        <el-card :body-style="{ padding: '20px' } " >


            <el-row :span="12">
                <el-avatar shape="square" :size="110" :src="url[ store.id ]" :fit="fit" />

                <el-divider direction="vertical" border-style="hidden"/>

                <el-col :span="12">

                    <router-link :to="'/goods_info/'+store.id" >
                        <el-button text class="button"><span style="display:inline-block;font-size:22px;font-weight:bold;"><u>
                            {{ store.storename }}
                        </u></span></el-button>  
                    </router-link>

                    
                    <span style="font-size:13px"></span>
                    <br>
                   
                    <span style="font-size:10px;color:darkgray">{{ store.category }}</span>

                    <br><br>
                    <el-rate
                    v-model="value"
                    disabled
                    show-score
                    text-color="#ff9900"
                    score-template="{value} "
                    ></el-rate>

                </el-col>   
                
                
            </el-row>
            
            <div style="padding: 5px">
                
                
                
                                     
                <!-- <div class="bottom"> -->
                    
                    <div class="text_flow1">
                    <span style="font-size:8px"><i>{{ store.intro }}</i></span>
                    </div>
                    
                    
                <!-- </div> -->
                
            </div>
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



</template>






<script>

    export default {

    


        data() {
            return {
                activeIndex: '1',

                

                storelist: [],
                total:0,  //总数据条数
                currentpage:1,  //当前所在页默认是第一页
                pagesize:12,  //每页显示多少行数据 默认设置为12
                storelistPage:[],//分页后的当前页数据
                            
                // storelist: [
                //     // { id: 1,ownerid: "张三", storename: "良心旗舰店", desc: "这是一个好店，非常有良心，给你最好的，给你最棒的，爱你哟", date: "2022-01-01", address: "上海市浦东新区", fund: 1500.0, idnumber: "110101199001011234", state: 1, category: "服装" },
                //     // { id: 2,ownerid: "李四", storename: "品牌旗舰店", desc: "这是一个品牌店", date: "2022-01-02", address: "北京市海淀区", fund: 2000.0, idnumber: "110101199001012345", state: 1, category: "鞋子" },
                //     // { id: 3,ownerid: "王五", storename: "高端旗舰店", desc: "这是一个高端店", date: "2022-01-03", address: "广州市天河区", fund: 2500.0, idnumber: "110101199001013456", state: 1, category: "数码产品" },
                //     // { id: 4,ownerid: "赵六", storename: "鲜花店", desc: "新鲜好看的鲜花,送给你的TA", date: "2022-01-04", address: "成都市锦江区", fund: 1200.0, idnumber: "110101199001014567", state: 1, category: "鲜花" },
                //     // { id: 5,ownerid: "孙七", storename: "美妆小店", desc: "打造完美妆容，让你更美丽", date: "2022-01-05", address: "南京市鼓楼区", fund: 1800.0, idnumber: "110101199001015678", state: 1, category: "美妆" },
                //     // { id: 6,ownerid: "周八", storename: "生活用品店", desc: "给你带来更方便的生活", date: "2022-01-06", address: "深圳市福田区", fund: 1600.0, idnumber: "110101199001016789", state: 1, category: "生活用品" },
                //     // { id: 7,ownerid: "钱九", storename: "绿色蔬菜店", desc: "新鲜有机蔬菜，让你健康吃饭", date: "2022-01-07", address: "上海市徐汇区", fund: 1300.0, idnumber: "110101199001017890", state: 1, category: "蔬菜" },
                //     // { id: 8,ownerid: "吴十", storename: "手工艺品店", desc: "精致手工艺品，让你更有品位", date: "2022-01-08", address: "北京市朝阳区", fund: 2200.0, idnumber: "110101199001018901", state: 1, category: "手工艺品" },
                //     // { id: 9,ownerid: "郑十一", storename: "家居生活店", desc: "打造温馨家居，让你的家更美好", date: "2022-01-09", address: "重庆市渝中区", fund: 1900.0, idnumber: "110101199001019012", state: 1, category: "家居用品" },
                //     // { id: 10,ownerid: "马十二", storename: "宠物店", desc: "给你的宠物带来更好的生活", date: "2022-01-10", address: "天津市河西区", fund: 2500.0, idnumber: "110101199001011223", state: 1, category: "宠物用品" },
                //     // { id: 11,ownerid: "郑十一", storename: "家居生活店", desc: "打造温馨家居，让你的家更美好", date: "2022-01-09", address: "重庆市渝中区", fund: 1900.0, idnumber: "110101199001019012", state: 1, category: "家居用品" },
                //     // { id: 12,ownerid: "马十二", storename: "宠物店", desc: "给你的宠物带来更好的生活", date: "2022-01-10", address: "天津市河西区", fund: 2500.0, idnumber: "110101199001011223", state: 1, category: "宠物用品" },                                       
                // ],

                avatarurl: "https://source.unsplash.com/1600x900/?shop,store,",
                url: [],
                fit: "cover",
                value: "5.0",




                
            };
        },


        methods: {
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
                //清空storelistPage中的数据
                this.storelistPage = [];
                // 获取当前页的数据
                for (let i = (this.currentpage - 1) * this.pagesize; i < this.total; i++) {
                    //把遍历的数据添加到storelistPage里面
                    this.storelistPage.push(this.storelist[i]);
                    //判断是否达到一页的要求
                    if (this.storelistPage.length === this.pagesize) break;
                }
            
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

            
            
        },

        mounted() {
            //刷新一次页面，以重新渲染导航栏，展示用户名
            if (window.location.href.indexOf("#reloaded") == -1) {
                window.location.href = window.location.href + "#reloaded";
                window.location.reload();
            }
            //this.storelist = this.Data.value.data.records;
            let that = this;
            let enter= {
                pageNo:1,//!!
                pageSize:100000,
                keyword: null
                }
            that.$axios.post('/store_list', enter).then((response) => {           
            that.storelist = response.data.data.records;
            that.total     = response.data.data.records.length;   
            //获取当前页的数据
            that.getPageInfo();

            //随机头像输入
            for (let i = 0; i < this.total+1; i++){
                var result = '';
                for (var j = 0; j < 3; j++) {
                    result += Math.floor(Math.random() * 10); // 生成0到9之间的随机整数
                }

                this.url.push("https://source.unsplash.com/1600x900/?shop,store," + result);
                
                
            }

            });

            

        },

        
        
    




}

// Mounted:{
//     this.$axios.get('http://localhost:8088/api/store_list').then((response) => {
//       this.storelist = response.data;
//     });
//   };





</script>


<style>

    

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
    
    


</style>