<template>
<div class="center">
    <div class="common-layout">
        <el-container>

        <el-header>
            <el-row :gutter="20">
                <el-col :span="20">
                    <div class="title"><el-icon><ShoppingCart /></el-icon>&ensp;购物车</div>                   
                </el-col>
                <el-col :span="2"><el-button type="danger" @click="dialogTableVisible = true" :disabled= "deleteAbility">批量删除</el-button></el-col> 
                <el-dialog v-model="dialogTableVisible" title="批量删除" width="40%">
                    <el-row>
                        <el-col :span="21"></el-col>
                        <el-col :span="3">
                            <el-button type="danger" @click="multiDeleteConfirm">删除</el-button>
                        </el-col>
                    </el-row>
                    <el-table
                        border
                        ref="multipleTable"
                        :data="showList"
                        tooltip-effect="dark"
                        style="width: 100%"
                        @selection-change="handleSelectionChange">
                        <el-table-column type="selection" width="53" align="center" />
                        <el-table-column
                        align="center"
                        label="商品名称"
                        width="390"
                        prop="name">  
                        </el-table-column>
                        <el-table-column
                        align="center"
                        prop="price"
                        label="价格"
                        width="200">
                        </el-table-column>
                        
                    </el-table>
                </el-dialog>

                <el-col :span="2"><el-button type="danger" :disabled="deleteAbility"    @click="deleteAllConfirm()">清空购物车</el-button></el-col>          
            </el-row>            
        </el-header>

        <el-main>
            <el-empty :style="showEmpty" description=" " ><span style="font-size:15px;color: white;">购物车空空如也~</span></el-empty>
            <el-space direction="vertical">
                <el-card v-for="(item,index) in showList" :key="item.id" class="box-card" style="width: 1370px ;height: 140px;">
                    <el-container>
                        <el-aside width="20px">
                           <div class="checkbox">
                           <input type="checkbox" 
                           :value="item.trolleyid" v-model="chosenID" @change="handleCheckbox(item)" :disabled="ifDown(item)" style="zoom:120%;" 
                           />
                           </div> 
                        </el-aside>
                        <el-divider direction="vertical" border-style="hidden"/>
                        <el-aside width="120px">
                            <img
                            :src = item.imageSet[0] 
                            style="width: 100px; height: 100px"
                            />
                        </el-aside>
                        <el-main>
                            <el-row :gutter="40">
                                <el-col :span="16"><div class="trolleycard" ><span style="display:inline-block;font-size:22px;font-weight:bold;">
                                    {{item.name}} 
                                </span></div></el-col>
                                <el-col :span="4"><div class="trolleycard" ><span style="display:inline-block;font-size:18px;font-weight:bold;float:right;text-align:right;">
                                    小计：{{ item.price*trolleyList[index].quantity }} 元
                                </span></div></el-col>
                                <el-col :span="2" :offset="1"><div class="trolleycard">
                                    <el-input-number v-model="trolleyList[index].quantity" size="small" :min="1"  @change="handleNumChange($event,index)" :disabled="ifDown(item)"/>
                                </div></el-col>
                            </el-row>
                            <el-row :gutter="26">
                                <el-col :span="4"><div class="trolleycard" ><i>¥{{ item.price }}</i></div></el-col>
                                <el-col :span="1" :offset="18"><div class="trolleycard">
                                    <el-button type="danger" @click="handleDelete($event,index,item);openCenter()" > <el-icon><Delete /></el-icon></el-button>
                                </div></el-col>
                            </el-row>
                                                       
                        </el-main>
                    </el-container>
                    
                    
                    
                
                </el-card>
            </el-space>
            
        </el-main>

        <el-footer></el-footer>
        </el-container>

        
    </div>
    
</div>
    <el-affix position="bottom" :offset="-22">
    <div class="foot">
        <el-container>
            <el-main>
                <div class="footinside">
                <el-row :gutter="20">
                    <el-col :span="2"><div class="footercontent" ></div></el-col>
                    <el-col :span="14"><div class="footercontent" ><div><input type="checkbox" v-model="checkAll" style="zoom:120%;"/>
                        全选
                    </div></div></el-col>
                    <el-col :span="3"><div class="footercontent" ><span style="font-size:20px;font-weight:bold"></span></div></el-col>
                    <el-col :span="3"><div class="footercontent" ><span style="font-size:20px;font-weight:bold">合计: {{total}} 元</span></div></el-col>
                    <el-col :span="2"><div class="footercontent" >
                        <el-button type="danger" color="orange" @click="handleSettle()" :disabled="deleteAbility">
                            结算
                        </el-button>
                    </div></el-col>
                    
                </el-row>
                </div>
            </el-main>
        </el-container>
    </div>    
   
    </el-affix>

</template>

<script>
export default {
    computed: {
        checkAll: {
        get() {
            let k = 0;
            for(let i=0;i<this.showList.length;i++){
                if(this.showList[i].state == 5){k++;}
            }
            return this.chosenID.length === this.showList.length - k;
        },
        set(val) {
            if (val) {
            this.chosenID = [];
            this.showList.forEach((item) => {
                if(item.state != 5){
                    this.chosenID.push(item.trolleyid);
                    item.ifChosen = true;                   
                } 
            });
            this.getTotal();
            } else {
            this.chosenID = [];
            this.showList.forEach((item) => {
                if(item.state != 5){
                    item.ifChosen = false;                   
                } 
            });
            this.getTotal();
            }
        },
        },
    },
    data() {
        return{
            showEmpty:{
                display: "none",
            },
            deleteAbility: false,
            dialogTableVisible: false,
            total:0,
            trolleyList:[
                
            ],
            showList:[
                
            ],
            selectDelete:[

            ],
            chosenID:[],
            
        }
    },

    
    methods: {
        getTrolleyInfo(){
            this.dialogTableVisible=false;

            let enter= {
                pageNo:1,
                pageSize:10000,
                keyword: null,
                }
            this.$axios.post('/shoppingCart_list_user', enter).then(response => {
            this.trolleyList.length=0;
            this.trolleyList = response.data.data.records;
            this.trolleyList.sort(function(a,b){return a.categoryid - b.categoryid});
            console.log("res" ,this.trolleyList);

            this.total = 0;
            this.showList.length=0;
            for(let i = 0; i<this.trolleyList.length; i++){
                let enter2 = this.trolleyList[i].categoryid.toString();
                this.$axios
                ({
                    method: "post",
                    url:"/category_info",
                    data:enter2,
                    headers:{
                        'Content-Type': 'text/plain'
                    },
                })
                .then(response2 => {
                //TODO: 根据response2.data.data.state判断是否push  覆盖图片为已失效  记得改商店管理的tag
                if(response2.data.data.state == 1 || response2.data.data.state == 3 || response2.data.data.state == 5){
                    this.showList.push(response2.data.data);
                    this.showList[this.showList.length-1]["trolleyid"]=this.trolleyList[i].id;
                }
                if(response2.data.data.state == 5){
                    this.showList[this.showList.length-1].imagePath = "https://img1.imgtp.com/2023/04/22/8DIQ4IiX.jpg";
                }
                console.log("showList",this.showList);
                for( let i=0;i<this.showList.length;i++){
                    this.showList[i]["imageSet"] = this.showList[i].imagePath.split(",");
                    if( !(this.showList[i].ifChosen == true) ){this.showList[i]["ifChosen"] = false;}
                }
                for(let i=0;i<this.chosenID.length;i++){
                    for(let j=0;j<this.showList.length;j++){
                        if(this.chosenID[i] == this.showList[j].id){
                            console.log("id:",this.showList[j].id);
                            this.showList[j].ifChosen = true;
                        }
                    }
                }
                this.showList.sort(function(a,b){return a.id - b.id});
                if(response2.data.data.state != 5 && response2.data.data.ifChosen == true){
                    this.total += this.trolleyList[i].quantity * response2.data.data.price;
                }
                });   
            }
            if(this.trolleyList.length == 0){
                this.showEmpty.display="";
                this.deleteAbility=true;
            }
            }); 

            

        },
        ifDown(item){
            if(item.state == 5){return true;}
            else return false;
        },
        getTotal(){
            this.total=0;
            for(let j = 0; j<this.trolleyList.length; j++){
                if(this.showList[j].state != 5 && this.showList[j].ifChosen == true){
                    this.total += this.trolleyList[j].quantity * this.showList[j].price;
                }         
            }
        },

        handleClick() {
            console.log( 'response.code');
            let enter= {
            pageNo:1,
            pageSize:10000,
            keyword: null
            }
            this.$axios.post('/shoppingCart_list_user', enter).then(response => {
            console.log( response);
            });
        }, 
        handleNumChange($event,index){
            let enter3={
                id:this.trolleyList[index].id,
                quantity:this.trolleyList[index].quantity,
                userid:this.trolleyList[index].userid,
                categoryid:this.trolleyList[index].categoryid,
                catestate:this.trolleyList[index].catestate,               
            }
            this.$axios
            ({
                method: "post",
                url:"/shoppingCart_change",
                data:enter3,
                
            })
            .then(response2 => {
                this.getTotal();
            });
        },
        handleDelete($event,index,item){
            console.log("index",index,this.trolleyList[index].categoryid);
            let enter4={
                id:this.trolleyList[index].id,
                quantity:this.trolleyList[index].quantity,
                userid:this.trolleyList[index].userid,
                categoryid:this.trolleyList[index].categoryid,
                catestate:this.trolleyList[index].catestate,               
            }
            this.$axios
            ({
                method: "post",
                url:"/shoppingCart_delete",
                data:enter4,             
            })
            .then(response2 => {
                this.chosenID = this.chosenID.filter(k => k != item.trolleyid)
                this.getTrolleyInfo();
            });
            
            
        },
        handleSelectionChange(selection){
            this.selectDelete = [];
            this.selectDelete = selection;
            console.log(this.selectDelete);
        },
        handleMultiDelete(){
            for( let i = 0; i < this.selectDelete.length; i++){
                console.log("find",this.trolleyList.filter( (item) => item.categoryid == this.selectDelete[i].id ));
                this.$axios
                ({
                    method: "post",
                    url:"/shoppingCart_delete",
                    data:this.trolleyList.filter( (item) => item.categoryid == this.selectDelete[i].id )[0],            
                })
                .then(response2 => {       
                });
            }
            location.reload();
        },
        handleAllDelete(){
            for( let i = 0; i < this.trolleyList.length; i++){
                let enter5={
                    id:this.trolleyList[i].id,
                    quantity:this.trolleyList[i].quantity,
                    userid:this.trolleyList[i].userid,
                    categoryid:this.trolleyList[i].categoryid,
                    catestate:this.trolleyList[i].catestate,               
                }
                this.$axios
                ({
                    method: "post",
                    url:"/shoppingCart_delete",
                    data:enter5,             
                })
                .then(response2 => {
                    this.ifChosen = [];
                    this.getTrolleyInfo();
                });
            }
        },
        openCenter() {
        this.$message({
          message: '删除成功',
          center: true,
          type: 'success',
        });
        },
        multiDeleteConfirm() {
            this.$confirm('将删除选中的所有商品, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            this.handleMultiDelete();           
        }).catch(() => {       
        });
        },
        deleteAllConfirm() {
            this.$confirm('将清空购物车, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            this.handleAllDelete();
            this.$message({
            type: 'success',
            message: '删除成功'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
        },
        handleCheckbox(item){
            item.ifChosen = !item.ifChosen;
            console.log("chosen:",this.chosenID);
            this.getTotal();
        },
        handleSettle(){
            
            this.$router.push({
			            path: '/order',
			            query: {
			                goods: this.chosenID,
			            }
			        })
        },
            
    },

    mounted() {
        this.getTrolleyInfo(); 
    },

}
</script>


<style>

.trolleycard{
    background: none;
}

.foot{
    /* position: relative; */
    background-color: rgba(12, 30, 58, 0.167);
    /* max-height: 200px; */
    /* color: var(--el-text-color-primary); */
}

.footinside{
    /* position: relative; */
    /* margin: auto; */
    top: 0; left: 0; bottom: 0; right: 0;
}

.footercontent{
    background: none;
}
.checkbox {
    padding: 40px 0;
}
</style>