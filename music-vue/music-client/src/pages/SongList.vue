<template>
  <div class="song-list">
    <ul class="song-list-header">
      <li v-for="(item,index) in songStyle" :key="index" @click="handleChangeView(item.name)"
          :class="{active:item.name==activeName}">
        {{item.name}}
      </li>
    </ul>
    <div>
      <content-list :contentList="data"></content-list>
      <div class="pagination">
        <el-pagination @current-change="handleCurrentChange" background layout="total,prev,pager,next"
                       :current-page="currentPage" :page-size="pageSize" :total="albumDatas.length">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import ContentList from "../components/ContentList";
import {getAllSongList,getSongListByStyle} from "../api";
import {mixin} from "../mixins";
import {songStyle} from '../assets/data/songList';
export default {
  name: "SongList",
  components:{
    ContentList
  },
  data(){
    return {
      albumDatas: [],
      pageSize: 15,
      currentPage: 1,
      songStyle: [],           //风格
      activeName: '全部歌单'    //当前风格
    }
  },
  mounted(){
    this.songStyle = songStyle;
    this.getSongList();
  },
  methods:{
    async getSongList(){
      const {data:res} = await getAllSongList();
      this.albumDatas = res.data.songLists
    },
    //获取当前页
    handleCurrentChange(val){
      this.currentPage = val;
    },
    async goSongListOfStyle(style){
      const {data:res} = await getSongListByStyle(style)
      this.currentPage = 1
      console.log(res.data.songLists)
      this.albumDatas = res.data.songLists
    },
    //根据style显示对应的歌单
    handleChangeView(name){
      this.activeName = name;
      this.albumDatas = [];
      if(name == '全部歌单'){
        this.getSongList();
      }else{
        this.goSongListOfStyle(name)
      }
    },

  },
  computed:{
    data(){
      return this.albumDatas.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize)
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/song-list.scss';
</style>
