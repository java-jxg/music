<template>
  <div class="home">
    <swiper></swiper>
    <div class="section" v-for="(item,index) in songsList" :key="index">
      <div class="section-title">{{item.name}}</div>
      <content-list :contentList = "item.list"></content-list>
    </div>
  </div>
</template>

<script>
import Swiper from "../components/Swiper"
import contentList from "../components/ContentList"
import {getAllSinger,getAllSongList} from "../api/index";

export default {
  name: 'home',
  components: {
    Swiper,
    contentList
  },
  data () {
    return {
      songsList: [
        {name:"歌单",list:[]},
        {name:"歌手",list:[]}
      ]
    }
  },
  methods: {
    async getSongList(){
      const {data:res} = await getAllSongList()
      this.songsList[0].list = res.data.songLists.slice(0,10)
    },
    async getSinger(){
      const {data:res} = await getAllSinger()
      this.songsList[1].list = res.data.singerList.slice(0,10)
    },
  },
  created(){
    this.getSinger()
    this.getSongList()
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/home.scss';
</style>
