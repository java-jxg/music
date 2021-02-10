<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
        <el-input v-model="select_word" size="mini" placeholder="请输入歌单名" class="handle-input"></el-input>
        <el-button type="primary" size="mini" @click="centerDialogVisible = true">添加歌单</el-button>
      </div>
    </div>
    <el-table size="mini" border style="width:100%" :data="pageTableData" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column label="歌单图片" width="110" align="center">
        <template slot-scope="scope">
          <div class="songList-img">
            <img :src="getUrl(scope.row.pic)" style="width:100%"/>
          </div>
          <el-upload :action="uploadUrl(scope.row.id)" :before-upload="beforeAvatorUpload"
                     :on-success="handleAvatorSuccess">
            <el-button size="mini">更新图片</el-button>
          </el-upload>
        </template>
      </el-table-column>
      <el-table-column prop="title" label="标题" width="120" align="center"></el-table-column>
      <el-table-column label="简介">
        <template slot-scope="scope">
          <p style="height:100px;overflow:scroll">{{scope.row.introduction}}</p>
        </template>
      </el-table-column>
      <el-table-column prop="style" label="风格" width="120" align="center"></el-table-column>
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
    <el-dialog title="添加歌单" :visible.sync="centerDialogVisible" width="400px" center>
      <el-form :model="registerForm" ref="registerForm" label-width="80px">
        <el-form-item prop="title" label="标题" size="mini">
          <el-input v-model="registerForm.title" placeholder="标题"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="简介" size="mini">
          <el-input v-model="registerForm.introduction" placeholder="简介" type="textarea"></el-input>
        </el-form-item>
        <el-form-item prop="style" label="风格" size="mini">
          <el-input v-model="registerForm.style" placeholder="风格"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="mini" @click="centerDialogVisible = false">取消</el-button>
        <el-button size="mini" @click="addSongList">确定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改歌单" :visible.sync="editDialogVisible" width="400px" center>
      <el-form :model="editForm" ref="form" label-width="80px">
        <el-form-item prop="title" label="标题" size="mini">
          <el-input v-model="editForm.title" placeholder="标题"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="简介" size="mini">
          <el-input v-model="editForm.introduction" placeholder="简介" type="textarea"></el-input>
        </el-form-item>
        <el-form-item prop="style" label="风格" size="mini">
          <el-input v-model="editForm.style" placeholder="风格"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="mini" @click="editVisible = false">取消</el-button>
        <el-button size="mini" @click="editSongList">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {addSongList,getAllSongList,editSongList,deleteSongList} from "../api/songList";
import { mixin } from '../mixins/index';
export default {
  mixins: [mixin],
  name: "SongList",
  data(){
    return {
      centerDialogVisible: false,
      editDialogVisible: false,
      registerForm:{      //添加框
        title: '',
        introduction: '',
        style: '',
        pic:'/img/songListPic/hhh.jpg',
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
    //查询所有歌单
    async getData(){
      const{ data:res } = await getAllSongList()
      this.tableData = res.data.songLists
      this.tempData = res.data.songLists
    },
    //添加歌单
    async addSongList(){
      const{ data:res } = await addSongList(this.registerForm)
      this.$message.success("添加成功")
      this.centerDialogVisible = false;
      this.getData()
      this.registerForm = {}
    },
    //更新图片
    uploadUrl(id){
      return `${this.$store.state.HOST}/songList/updateSongListPic?id=${id}`
    },
    async editSongList(){
      const {data,res} = await editSongList(this.editForm)
      this.$message.success("修改成功")
      this.editDialogVisible = false;
    },
    handleEdit(row){
      this.editForm = row
      console.log(this.editForm)
      this.editDialogVisible = true
    },
    async deleteSigner(id){
      const confirmResult = await this.$confirm('此操作将永久删除该歌单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err)
      console.log(confirmResult)
      if(confirmResult == 'cancel'){
        return this.$message.info('已取消删除')
      }
      const {data:res} = await deleteSongList(id)
      this.$message.success('删除成功!')
      this.getData()
    },
    async delAll(){
      for(let item of this.multipleSelection){
        await deleteSongList(item.id);
      }
      this.multipleSelection = [];
      this.$message.success('删除成功!')
      this.getData()
    },
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
.songList-img{
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
