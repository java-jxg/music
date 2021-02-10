<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
        <el-input v-model="select_word" size="mini" placeholder="请输入歌手名" class="handle-input"></el-input>
        <el-button type="primary" size="mini" @click="centerDialogVisible = true">添加歌手</el-button>
      </div>
    </div>
    <el-table size="mini" border style="width:100%" :data="pageTableData" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column label="歌手图片" width="110" align="center">
        <template slot-scope="scope">
          <div class="singer-img">
            <img :src="getUrl(scope.row.pic)" style="width:100%"/>
          </div>
          <el-upload :action="uploadUrl(scope.row.id)" :before-upload="beforeAvatorUpload"
                     :on-success="handleAvatorSuccess">
            <el-button size="mini">更新图片</el-button>
          </el-upload>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="歌手" width="120" align="center"></el-table-column>
      <el-table-column label="性别" width="50" align="center">
        <template slot-scope="scope">
          {{changeSex(scope.row.sex)}}
        </template>
      </el-table-column>
      <el-table-column label="生日" width="120" align="center">
        <template slot-scope="scope">
          {{attachBirth(scope.row.birth)}}
        </template>
      </el-table-column>
      <el-table-column prop="location" label="地区" width="100" align="center"></el-table-column>
      <el-table-column label="简介">
        <template slot-scope="scope">
          <p style="height:100px;overflow:scroll">{{scope.row.introduction}}</p>
        </template>
      </el-table-column>
      <el-table-column label="歌曲管理" width="110" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="songEdit(scope.row.id,scope.row.name)">歌曲管理</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="deleteSigner(scope.row.id)">删除</el-button>
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
    <el-dialog title="添加歌手" :visible.sync="centerDialogVisible" width="400px" center>
      <el-form :model="registerForm" ref="registerForm" label-width="80px">
        <el-form-item prop="name" label="歌手名" size="mini">
          <el-input v-model="registerForm.name" placeholder="歌手名"></el-input>
        </el-form-item>
        <el-form-item label="性别" size="mini">
          <el-radio-group v-model="registerForm.sex">
            <el-radio :label="0">女</el-radio>
            <el-radio :label="1">男</el-radio>
            <el-radio :label="2">组合</el-radio>
            <el-radio :label="3">不明</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="birth" label="生日" size="mini">
          <el-date-picker type="date" placeholder="选择日期" v-model="registerForm.birth" style="width:100%"></el-date-picker>
        </el-form-item>
        <el-form-item prop="location" label="地区" size="mini">
          <el-input v-model="registerForm.location" placeholder="地区"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="简介" size="mini">
          <el-input v-model="registerForm.introduction" placeholder="简介" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="mini" @click="centerDialogVisible = false">取消</el-button>
        <el-button size="mini" @click="addSinger">确定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改歌手" :visible.sync="editDialogVisible" width="400px" center>
      <el-form :model="editForm" ref="editForm" label-width="80px">
        <el-form-item prop="name" label="歌手名" size="mini">
          <el-input v-model="editForm.name" placeholder="歌手名"></el-input>
        </el-form-item>
        <el-form-item label="性别" size="mini">
          <el-radio-group v-model="editForm.sex">
            <el-radio :label="0">女</el-radio>
            <el-radio :label="1">男</el-radio>
            <el-radio :label="2">组合</el-radio>
            <el-radio :label="3">不明</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="birth" label="生日" size="mini">
          <el-date-picker type="date" placeholder="选择日期" v-model="editForm.birth" style="width:100%"></el-date-picker>
        </el-form-item>
        <el-form-item prop="location" label="地区" size="mini">
          <el-input v-model="editForm.location" placeholder="地区"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="简介" size="mini">
          <el-input v-model="editForm.introduction" placeholder="简介" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="mini" @click="editDialogVisible = false">取消</el-button>
        <el-button size="mini" @click="editSinger">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {addSigner,getAllSinger,editSigner,deleteSigner} from '../api/signer'
import { mixin } from '../mixins/index';
export default {
  mixins: [mixin],
  name: "Singer",
  data(){
    return {
      centerDialogVisible: false,
      editDialogVisible: false,
      registerForm:{      //添加框
        name: '',
        sex: '',
        birth: '',
        location: '',
        introduction: '',
        pic:'/img/singerPic/hhh.jpg',
      },
      tableData: [],
      tempData: [],
      select_word: '',
      pageSize: 10,    //分页每页大小
      currentPage: 1,  //当前页
      editForm:[],
      multipleSelection: [] //多选项
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
    //查询所有歌手
    async getData(){
      const{ data:res } = await getAllSinger()
      this.tableData = res.data.singerList
      this.tempData = res.data.singerList
    },
    //添加歌手
    async addSinger(){
      const{ data:res } = await addSigner(this.registerForm)
      this.$message.success("添加成功")
      this.centerDialogVisible = false;
      this.getData()
      this.registerForm = {}
    },
    //更新图片
    uploadUrl(id){
      return `${this.$store.state.HOST}/singer/updateSingerPic?id=${id}`
    },
    async editSinger(){
      const {data,res} = await editSigner(this.editForm)
      this.$message.success("修改成功")
      this.editDialogVisible = false;
    },
    handleEdit(row){
      this.editForm = row
      console.log(this.editForm)
      this.editDialogVisible = true
    },
    async deleteSigner(id){
      const confirmResult = await this.$confirm('此操作将永久删除该歌手, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err)
      console.log(confirmResult)
      if(confirmResult == 'cancel'){
        return this.$message.info('已取消删除')
      }
      const {data:res} = await deleteSigner(id)
      this.$message.success('删除成功!')
      this.getData()
    },
    async delAll(){
      for(let item of this.multipleSelection){
        await deleteSigner(item.id);
      }
      this.multipleSelection = [];
      this.$message.success('删除成功!')
      this.getData()
    },
    songEdit(id,name){
      this.$router.push({path:`/Song`,query:{id,name}})
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
          if(item.name.includes(this.select_word)){
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
.singer-img{
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
