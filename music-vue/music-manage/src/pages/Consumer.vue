<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
        <el-input v-model="select_word" size="mini" placeholder="请输入用户名" class="handle-input"></el-input>
        <el-button type="primary" size="mini" @click="centerDialogVisible = true">添加用户</el-button>
      </div>
    </div>
    <el-table size="mini" border style="width:100%" :data="pageTableData" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column label="用户图片" width="110" align="center">
        <template slot-scope="scope">
          <div class="consumer-img">
            <img :src="getUrl(scope.row.avator)" style="width:100%"/>
          </div>
          <el-upload :action="uploadUrl(scope.row.id)" :before-upload="beforeAvatorUpload"
                     :on-success="handleAvatorSuccess">
            <el-button size="mini">更新图片</el-button>
          </el-upload>
        </template>
      </el-table-column>
      <el-table-column prop="username" label="用户名" width="120" align="center"></el-table-column>
      <el-table-column label="性别" width="50" align="center">
        <template slot-scope="scope">
          {{changeSex(scope.row.sex)}}
        </template>
      </el-table-column>
      <el-table-column prop="phoneNum" label="手机号" width="120" align="center"></el-table-column>
      <el-table-column prop="email" label="电子邮箱" width="240" align="center"></el-table-column>
      <el-table-column label="生日" width="120" align="center">
        <template slot-scope="scope">
          {{attachBirth(scope.row.birth)}}
        </template>
      </el-table-column>
      <el-table-column prop="introduction" label="签名" align="center"></el-table-column>
      <el-table-column prop="location" label="地区" width="100" align="center"></el-table-column>
      <el-table-column label="收藏" width="80" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="getCollect(scope.row.id)">收藏</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="deleteConsumer(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5, 10, 20, 50]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="tableData.length">
      </el-pagination>
    </div>
    <el-dialog title="添加用户" :visible.sync="centerDialogVisible" width="400px" center>
      <el-form :model="registerForm" ref="registerForm" label-width="80px" :rules="rules">
        <el-form-item prop="username" label="用户名" size="mini">
          <el-input v-model="registerForm.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码" size="mini">
          <el-input type="password" v-model="registerForm.password" placeholder="密码"></el-input>
        </el-form-item>
        <el-form-item label="性别" size="mini">
          <input type="radio" name="sex" value="0" v-model="registerForm.sex">&nbsp;女&nbsp;&nbsp;
          <input type="radio" name="sex" value="1" v-model="registerForm.sex">&nbsp;男
        </el-form-item>
        <el-form-item prop="phoneNum" label="手机号" size="mini">
          <el-input v-model="registerForm.phoneNum" placeholder="手机号"></el-input>
        </el-form-item>
        <el-form-item prop="email" label="电子邮箱" size="mini">
          <el-input v-model="registerForm.email" placeholder="电子邮箱"></el-input>
        </el-form-item>
        <el-form-item prop="birth" label="生日" size="mini">
          <el-date-picker type="date" placeholder="选择日期" v-model="registerForm.birth" style="width:100%"></el-date-picker>
        </el-form-item>
        <el-form-item prop="introduction" label="签名" size="mini">
          <el-input v-model="registerForm.introduction" placeholder="签名"></el-input>
        </el-form-item>
        <el-form-item prop="location" label="地区" size="mini">
          <el-input v-model="registerForm.location" placeholder="地区"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="mini" @click="centerDialogVisible = false">取消</el-button>
        <el-button size="mini" @click="addConsumer">确定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改用户" :visible.sync="editDialogVisible" width="400px" center>
      <el-form :model="editForm" ref="editForm" label-width="80px" :rules="rules">
        <el-form-item prop="username" label="用户名" size="mini">
          <el-input v-model="editForm.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码" size="mini">
          <el-input type="password" v-model="editForm.password" placeholder="密码"></el-input>
        </el-form-item>
        <el-form-item label="性别" size="mini">
          <input type="radio" name="sex" value="0" v-model="editForm.sex">&nbsp;女&nbsp;&nbsp;
          <input type="radio" name="sex" value="1" v-model="editForm.sex">&nbsp;男
        </el-form-item>
        <el-form-item prop="phoneNum" label="手机号" size="mini">
          <el-input v-model="editForm.phoneNum" placeholder="手机号"></el-input>
        </el-form-item>
        <el-form-item prop="email" label="电子邮箱" size="mini">
          <el-input v-model="editForm.email" placeholder="电子邮箱"></el-input>
        </el-form-item>
        <el-form-item prop="birth" label="生日" size="mini">
          <el-date-picker type="date" placeholder="选择日期" v-model="editForm.birth" style="width:100%"></el-date-picker>
        </el-form-item>
        <el-form-item prop="introduction" label="签名" size="mini">
          <el-input v-model="editForm.introduction" placeholder="签名"></el-input>
        </el-form-item>
        <el-form-item prop="location" label="地区" size="mini">
          <el-input v-model="editForm.location" placeholder="地区"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="mini" @click="editDialogVisible = false">取消</el-button>
        <el-button size="mini" @click="editConsumer">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {getAllConsumer,addConsumer,editConsumer,deleteConsumer} from '../api/consumer'
import { mixin } from '../mixins/index';
export default {
  mixins: [mixin],
  name: "Consumer",
  data(){
    return {
      centerDialogVisible: false,
      editDialogVisible: false,
      registerForm:{      //添加框
        username: '',
        password: '',
        sex: '1',
        phoneNum: '',
        email: '',
        birth: '',
        introduction: '',
        location: '',
        avator:'/img/consumerPic/hhh.jpg',
      },
      tableData: [],
      tempData: [],
      select_word: '',
      pageSize: 10,    //分页每页大小
      currentPage: 1,  //当前页
      editForm:[],
      multipleSelection: [], //多选项
      rules: {
        username: [
          {required: true,message: '请输入用户名',trigger: 'blur'}
        ],
        password: [
          {required: true,message: '请输入密码',trigger: 'blur'}
        ],
        phoneNum: [
          {required: true,message: '请输入手机号',trigger: 'blur'}
        ],
        email: [
          {required: true,message: '请输入电子邮箱',trigger: 'blur'},
          {type: 'email',message:'请输入正确的电子邮箱地址',trigger:['blur','change']}
        ],
        introduction: [
          {required: true,message: '请输入签名',trigger: 'blur'}
        ],
        location: [
          {required: true,message: '请输入地区',trigger: 'blur'}
        ]
      }
    }
  },
  methods:{
    //获取当前页
    handleCurrentChange(currentPage){
      this.currentPage = currentPage;
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize
    },
    //查询所有用户
    async getData(){
      const{ data:res } = await getAllConsumer()
      this.tableData = res.data.consumerList
      this.tempData = res.data.consumerList
    },
    //添加用户
    addConsumer(){
      this.$refs['registerForm'].validate(async valid=>{
        if(valid){
          const{ data:res } = await addConsumer(this.registerForm)
          this.$message.success("添加成功")
          this.centerDialogVisible = false;
          this.getData()
          this.registerForm = {}
        }
      })

    },
    //更新图片
    uploadUrl(id){
      return `${this.$store.state.HOST}/consumer/updateConsumerPic?id=${id}`
    },
    editConsumer(){
      this.$refs['editForm'].validate(async valid=>{
          if(valid){
            const {data,res} = await editConsumer(this.editForm)
            this.$message.success("修改成功")
            this.editDialogVisible = false;
          }
      })
    },
    handleEdit(row){
      this.editForm = row
      console.log(this.editForm)
      this.editDialogVisible = true
    },
    async deleteConsumer(id){
      const confirmResult = await this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err)
      console.log(confirmResult)
      if(confirmResult == 'cancel'){
        return this.$message.info('已取消删除')
      }
      const {data:res} = await deleteConsumer(id)
      this.$message.success('删除成功!')
      this.getData()
    },
    async delAll(){
      for(let item of this.multipleSelection){
        await deleteConsumer(item.id);
      }
      this.multipleSelection = [];
      this.$message.success('删除成功!')
      this.getData()
    },
    songEdit(id,name){
      this.$router.push({path:`/Song`,query:{id,name}})
    },
    //转向该用户的收藏列表
    getCollect(id){
      this.$router.push({path: '/collect',query:{id}})
    }

  },
  created(){
    this.getData()
  },
  watch:{
    select_word: function(){
      if(this.select_word == ''){
        this.tableData = this.tempData
      }else{
        this.tableData = []
        for(let item of this.tempData){
          if(item.username.includes(this.select_word)){
            this.tableData.push(item)
          }
        }
      }
    }
  },
  computed:{
    pageTableData(){
      return this.tableData.slice((this.currentPage - 1)*this.pageSize,this.currentPage * this.pageSize)
    }
  }
}
</script>

<style scoped>
.handle-box{
  margin-bottom: 20px;
}
.consumer-img{
  width: 100%;
  height: 80px;
  border-radius: 5px;
  margin-bottom: 5px;
  overflow: hidden;
}
.handle-input{
  width: 300px;
  display: inline-block;
}
.pagination{
  display: flex;
  justify-content: center;
}
</style>
