<template>
  <div class="table">
    <div class="crumbs">
      <i class="el-icon-tickets"></i>收藏信息
    </div>
    <div class="container">
      <div class="handle-box">
        <el-input v-model="select_word" size="mini" placeholder="筛选关键词" class="handle-input"></el-input>
      </div>
    </div>
    <el-table size="mini" ref="multipleTable" border style="width:100%" height="680px" :data="tableData" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column prop="name" label="歌手-歌名" align="center"></el-table-column>
      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { mixin } from '../mixins/index';
import {getByUserId,deleteCollectByUidSid} from '../api/collect'
import {getSongByKId} from '../api/song'


export default {
  mixins: [mixin],
  props: ['id'],
  data(){
    return{
      delVisible: false,          //删除弹窗是否显示
      tableData: [],
      tempData: [],
      select_word: '',
      idx: -1,          //当前选择项
      multipleSelection: [],   //哪些项已经打勾
    }
  },
  watch:{
    //搜索框里面的内容发生变化的时候，搜索结果table列表的内容跟着它的内容发生变化
    select_word: function(){
      if(this.select_word == ''){
        this.tableData = this.tempData;
      }else{
        this.tableData = [];
        for(let item of this.tempData){
          if(item.name.includes(this.select_word)){
            this.tableData.push(item);
          }
        }
      }
    }
  },
  created(){
    this.getData();
  },
  methods:{
    //查询该用户所有收藏的歌曲
    getData(){
      this.tempData = [];
      this.tableData = [];
      getByUserId(this.$route.query.id).then(res => {
        for(let item of res.data.data.collectList){
          this.getSong(item.songId);
        }
      })
    },
    //根据歌曲id查询歌曲对象，放到tempData和tableData里面
    getSong(id){
      getSongByKId(id)
        .then(res => {
          this.tempData.push(res.data.data.song);
          this.tableData.push(res.data.data.song);
        })
        .catch(err => {
          console.log(err);
        });
    },
    //删除一条歌曲
    async handleDelete(idxi){
      console.log(idxi)
      const {data:res} = await deleteCollectByUidSid(this.$route.query.id,idxi)
      this.$message.success("删除成功")
      this.getData()
    },
  }
}
</script>

<style scoped>
.handle-box{
  margin-bottom: 20px;
}
.handle-input{
  width: 300px;
  display: inline-block;
}
</style>
