<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-input v-model="select_word" size="mini" placeholder="请输入歌曲名" class="handle-input"></el-input>
        <el-button type="primary" size="mini" @click="addDialog = true">添加歌曲</el-button>
      </div>
    </div>

    <el-table size="mini" ref="multipleTable" border style="width:100%" height="680px" :data="pageTableData">
      <el-table-column label="歌曲图片" width="110" align="center">
        <template slot-scope="scope">
          <div class="singer-img">
            <img :src="getUrl(scope.row.pic)" style="width:100%"/>
          </div>
          <div class="play" @click="setSongUrl(scope.row.url,scope.row.name)">
            <div v-if="toggle == scope.row.name">
              <svg class="icon">
                <use xlink:href="#icon-zanting"></use>
              </svg>
            </div>
            <div v-if="toggle != scope.row.name">
              <svg class="icon">
                <use xlink:href="#icon-bofanganniu"></use>
              </svg>
            </div>
          </div>
          <el-upload :action="uploadUrl(scope.row.id)" :before-upload="beforeAvatorUpload"
                     :on-success="handleAvatorSuccess">
            <el-button size="mini">更新图片</el-button>
          </el-upload>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="歌手-歌名" width="120" align="center"></el-table-column>
      <el-table-column prop="introduction" label="专辑" width="150" align="center"></el-table-column>
      <el-table-column label="歌词" align="center">
        <template slot-scope="scope">
          <ul style="height:100px;overflow:scroll;">
            <li v-for="(item,index) in parseLyric(scope.row.lyric)" :key="index">
              {{item}}
            </li>
          </ul>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
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

    <el-dialog title="添加歌曲" :visible.sync="addDialog" width="400px" center>
      <el-form :model="registerForm" ref="registerForm" label-width="80px" action="">
        <div>
          <label>歌名</label>
          <el-input type="text" v-model="registerForm.name"></el-input>
        </div>
        <div>
          <label>专辑</label>
          <el-input type="text" v-model="registerForm.introduction"></el-input>
        </div>
        <div>
          <label>歌词</label>
          <el-input type="textarea" v-model="registerForm.lyric"></el-input>
        </div>
        <div>
          <label>歌曲上传</label>
          <el-upload
            class="upload-demo"
            :action="getUrl('/song/addSongFile')"
            :on-success="filesucess"
            multiple>
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </div>
      </el-form>
      <span slot="footer">
                <el-button size="mini" @click="addDialog = false">取消</el-button>
                <el-button size="mini" @click="addSong">确定</el-button>
            </span>
    </el-dialog>

    <el-dialog title="修改歌曲" :visible.sync="editVisible" width="400px" center>
      <el-form :model="editForm" ref="form" label-width="80px">
        <el-form-item prop="name" label="歌手-歌名" size="mini">
          <el-input v-model="editForm.name" placeholder="歌手-歌名"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="专辑" size="mini">
          <el-input v-model="editForm.introduction" placeholder="专辑"></el-input>
        </el-form-item>
        <el-form-item prop="lyric" label="歌词" size="mini">
          <el-input v-model="editForm.lyric" placeholder="歌词" type="textarea"></el-input>
        </el-form-item>
        <label>歌曲上传</label>
        <label>歌曲上传</label>
        <el-upload
          class="upload-demo"
          :action="getUrl('/song/addSongFile')"
          :on-success="filesucess"
          multiple>
          <el-button size="small" type="primary">点击上传</el-button>
        </el-upload>
      </el-form>
      <span slot="footer">
                <el-button size="mini" @click="editVisible = false">取消</el-button>
                <el-button size="mini" @click="editSong">确定</el-button>
            </span>
    </el-dialog>

  </div>
</template>

<script>
import { mixin } from '../mixins/index';
import {mapGetters} from 'vuex';
import '@/assets/js/iconfont.js';
import {getSongById,addSong,updateSong,deleteSong} from "../api/song";

export default {
  mixins: [mixin],
  name: "Song",
  data(){
    return {
      singerId: '',               //歌手id
      singerName: '',             //歌手名
      tableData: [],
      addDialog: false,
      editVisible: false,
      registerForm:{
        name: '',
        singerId: '',
        introduction: '',
        lyric: '',
        url:''
      },
      editForm:[],
      toggle: false,           //播放器的图标状态
      select_word: '',
      pageSize: 10,    //分页每页大小
      currentPage: 1,  //当前页
      tempData: [],
    }
  },
  computed:{
    ...mapGetters([
      'isPlay'
    ]),
    pageTableData(){
      return this.tableData.slice((this.currentPage - 1)*this.pageSize,this.currentPage * this.pageSize)
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
    async getData(){
      const{ data:res } = await getSongById(this.singerId)
      this.tableData = res.data.songList
      this.tempData = res.data.songList
      console.log(this.tableData)
    },
    async addSong(){
      this.registerForm.singerId = this.singerId
      this.registerForm.name = this.singerName + "-" + this.registerForm.name
      const{ data:res } = await addSong(this.registerForm)
      this.$message.success("添加成功")
      this.addDialog = false
      this.getData()
    },
    async editSong(){
      console.log(this.editForm)
      const{ data:res } = await updateSong(this.editForm)
      this.$message.success("修改成功")
      this.editVisible = false;
      this.getData()
    },
    filesucess(response){
      this.$message.success(response.message)
      this.registerForm.url = response.data.fileName
      this.editForm.url = response.data.fileName
    },
    handleEdit(row){
      this.editForm = row
      this.editVisible = true
    },
    async handleDelete(id){
      const confirmResult = await this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err)
      console.log(confirmResult)
      if(confirmResult == 'cancel'){
        return this.$message.info('已取消删除')
      }
      await deleteSong(id)
      this.$message.success('删除成功!')
      this.getData()
    },
    //解析歌词
    parseLyric(text){
      let lines = text.split("\n");
      let pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g;
      let result = [];
      for(let item of lines){
        let value = item.replace(pattern,'');
        result.push(value);
      }
      return result;
    },
    //更新图片
    uploadUrl(id){
      return `${this.$store.state.HOST}/song/updateSongPic?id=${id}`
    },
  //切换播放歌曲
    setSongUrl(url,name) {
      this.toggle = name;
      this.$store.commit('setUrl',this.$store.state.HOST + url);
      if(this.isPlay){
        this.$store.commit('setIsPlay',false);
      }else{
        this.$store.commit('setIsPlay',true);
      }
    }

  },
  created(){
    this.singerId = this.$route.query.id;
    this.singerName = this.$route.query.name;
    console.log(this.singerId)
    console.log(this.singerName)
    this.getData();
  },
  destroyed() {
    this.$store.commit('setIsPlay',false);
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

}
</script>

<style scoped>
.handle-box{
  margin-bottom: 20px;
}
.song-img{
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
.play {
  position: absolute;
  z-index: 100;
  width: 80px;
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  top: 18px;
  left: 15px;
}

.icon {
  width: 2em;
  height: 2em;
  color: white;
  fill: currentColor;
  overflow: hidden;
}
</style>
