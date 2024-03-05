<template>

<div class="center">
<h1>商家管理&ensp;&ensp;
    <el-button type="primary" color=#d0f8c4 @click="dialogFormVisible = true">新增商品</el-button>
    <el-button type="primary" color=pink @click="deleteStoreConfirm()">删除店铺</el-button>
</h1>
    <el-dialog v-model="dialogFormVisible" title="添加商品">
        <el-form :model="form">
            <el-form-item label="名称">
                <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="图片链接">
                <el-input v-model="form.imagePath" placeholder="若上传多张图片，请在链接之间加上逗号(,)"></el-input>
            </el-form-item>
            <el-form-item label="价格">
                <el-input v-model="form.price"></el-input>
            </el-form-item>
            <el-form-item label="简介">
                <el-input v-model="form.intro"></el-input>
            </el-form-item>
        </el-form>

        <template #footer>
        <span class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取消</el-button>
            <el-button type="primary" @click=addGood()>
            提交申请
            </el-button>
        </span>
        </template>
    </el-dialog>
</div>


<div class="center">
    <div id = "form_container" >
     
        <el-table :data="goodsList" :default-sort = "{prop: 'state'}" style="width: 100%"  :key="tableKey" >
        <el-table-column fixed prop="name" label="名称" width="150" />
        <el-table-column prop="price" label="价格" width="150" />
        <el-table-column fixed prop="sales" label="销量" width="100" />
        <el-table-column fixed prop="subsidy_id" label="参与活动" width="150" />
        <el-table-column prop="intro" label="商品简介"  />
        <el-table-column prop="state" fixed="right" label="状态" width="140" align="center" sortable :sort-method="sortByState">
            <template v-slot="scope">
                <el-tag v-if="scope.row.state == 0" type="warning">申请上架中</el-tag>
                <el-tag v-if="scope.row.state == 1" type="success">上架中</el-tag>
                <el-tag v-if="scope.row.state == 2" type="danger">申请拒绝</el-tag>
                <el-tag v-if="scope.row.state == 3" type="success">上架中（待修改）</el-tag>
                <el-tag v-if="scope.row.state == 4" type="warning">申请修改中</el-tag>
                <el-tag v-if="scope.row.state == 5" type="danger">已下架</el-tag>
                <el-tag v-if="scope.row.state == 6" type="warning">申请活动中</el-tag>
            </template>
        </el-table-column>

        <el-table-column fixed="right" label="操作" width="240" align="center">
            
            <template v-slot = "scope">
            <el-button  v-if="scope.row.state == 1" type="primary" size="small" @click="handlePreEdit(scope.row)" >修改</el-button>
            <el-dialog v-model="modifyDialogFormVisible" title="修改商品" @close="handleClose()" :append-to-body="true">
                <el-form :model="form">
                    <el-form-item label="名称">
                        <el-input v-model="modify.name"></el-input>
                    </el-form-item>
                    <el-form-item label="图片链接">
                        <el-input v-model="modify.imagePath"></el-input>
                    </el-form-item>
                    <el-form-item label="价格">
                        <el-input v-model="modify.price"></el-input>
                    </el-form-item>
                    <el-form-item label="简介">
                        <el-input v-model="modify.intro"></el-input>
                    </el-form-item>
                    <el-form-item label="销量">
                        <el-input v-model="modify.sales" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="参与活动">
                        <el-input v-model="modify.subsidy_id" disabled></el-input>
                    </el-form-item>
                </el-form>

                <template #footer>
                <span class="dialog-footer">
                    <el-button @click="modifyDialogFormVisible = false">取消</el-button>
                    <el-button type="primary" @click=handleEdit()>
                    提交申请
                    </el-button>
                </span>
                </template>
            </el-dialog>
            &ensp;
            <el-button  v-if="scope.row.state == 1" type="danger" size="small" @click="handlePreRemove(scope.row);removeConfirm()">下架</el-button>
            <el-button  v-if="scope.row.state == 1" type="success" size="small" @click="subsidyApply(scope.row);">申请活动</el-button>
            </template>

        </el-table-column>
        </el-table>
  
    </div>
</div>

<br><br><br>

</template>


<script>


export default {
    data() {
        return {
            tableKey:0,
            modifyDialogFormVisible: false,
            dialogFormVisible: false,
            form:{
                imagePath:"",
                name:"",
                intro:"",
                price:"",
                state:0,
                shopID:0,
            },
            modify:{
                id:0,
                imagePath:"",
                name:"",
                intro:"",
                price:0,
                state:0,
                shopID:0,
                ownerid:0,
                sales: 0,
                subsidy_id: 0,
            },
            changeId:0,

            storeList: [],
            goodsList: [],     
            };
    },

    methods: {
        subsidyApply(row){
            console.log(row);
            this.$axios.post('/category_subsidy_apply', row).then((response) => {
                if (response.data.code === 200) {
                    row.state = 6;
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
        sortByState(a,b){
            if(a.state==1){return -1;}
            else if(b.state==1){return 1;}
            else if(a.state==3){return -1;}
            else if(b.state==3){return 1;}
            else {return a.state-b.state;}
        },
        deleteStoreConfirm() {
            this.$confirm('将申请删除店铺, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            this.handleDeleteStore();
        
        }).catch(() => {
            this.$message({
                type: 'info',
                message: '申请已取消'
            });          
        });
        },
        handleDeleteStore(){
            this.$axios
            ({
                method: "post",
                url:"/store_delete",
                data:this.storeList[0],          
            })
            .then(response => {
                this.$message({
                    type: 'success',
                    message: '申请已提交'
                });
				console.log( "DelStoreresult",response);
            });
        },
        handleClose(){
            this.modifyDialogFormVisible=false;
        },
        handlePreRemove(row){
            this.modify.id=row.id;
            this.modify.imagePath=row.imagePath;
            this.modify.name=row.name;
            this.modify.intro=row.intro;
            this.modify.price=row.price;
            this.modify.state=row.state;
            this.modify.sales=row.sales;
            this.modify.subsidy_id=row.subsidy_id;
            console.log("modi:",this.modify);
        },
        handlePreEdit(row){
            this.modifyDialogFormVisible=true;
            this.modify.id=row.id;
            this.modify.imagePath=row.imagePath;
            this.modify.name=row.name;
            this.modify.intro=row.intro;
            this.modify.price=row.price;
            this.modify.state=row.state;
            this.modify.sales=row.sales;
            this.modify.subsidy_id=row.subsidy_id;
            console.log("modi:",this.modify);
        },
        handleEdit() {
            this.modifyDialogFormVisible=false;
            this.modify.state=3;
            console.log("modi:",this.modify.id.toString());
            this.$axios
            ({
                method: "post",
                url:"/category_update?oldCateID="+this.modify.id.toString(),
                data:this.modify,          
            })
            .then(response => {
				console.log( "addresult",response);
                this.getStore();
            });

        },
        getStore(){
            this.storeList=[];
            this.goodsList=[];
            this.$axios
            ({
                method: "post",
                url:"/store_list_owner",
                data:{
					pageNo:1,
					pageSize:10000,
					keyword: null,
					}, 
				         
            })
            .then(response => {
                this.storeList=response.data.data.records;
				console.log( "sl",this.storeList);
                this.$axios
                ({
                    method: "post",
                    url:"/category_list_shop?shopID="+this.storeList[0].id.toString(),
                    data:{
                        pageNo:1,
                        pageSize:10000,
                        keyword: null,
                        }, 
                            
                })
                .then(response2 => {
                    this.goodsList=response2.data.data.records;
                    this.tableKey=Math.random();
                    console.log( "key",this.tableKey);
                    console.log( "goods",this.goodsList);
                    this.form.shopID=this.storeList[0].id;
                    this.modify.shopID=this.storeList[0].id;
                    this.modify.ownerid=this.storeList[0].ownerid;
                });
                });
        },
        addGood(){   
            this.dialogFormVisible = false;
            this.$axios
            ({
                method: "post",
                url:"/category_register",
                data:this.form,          
            })
            .then(response => {
				console.log( "addresult",response);
                this.getStore();
            });
		
		},
        removeConfirm() {
            this.$confirm('将下架商品, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            this.handleRemove();
        
        }).catch(() => {
            this.$message({
                type: 'info',
                message: '下架已取消'
            });          
        });
        },
        handleRemove(){
            this.$axios
            ({
                method: "post",
                url:"/category_delete",
                data:this.modify,          
            })
            .then(response => {
                this.$message({
                    type: 'success',
                    message: '下架成功'
                });
                this.getStore();
				console.log( "RMresult",response);
            });
        },

    },

    mounted(){
        this.getStore();
    },
};
</script>

<style>

.center {
  margin: auto;
  /*background: lime;*/
  width: 85%;
}

/*选中颜色*/		
.el-tabs--card>.el-tabs__header .el-tabs__item.is-active {
	background-color: rgba(143, 190, 237, 0);
	color: rgb(236, 233, 233);
}  

/*悬停颜色*/
.el-tabs__item:hover {
    color: #c7fecd;     
}
	





</style>