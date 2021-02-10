<template>
  <div class="song-list-album">
    <div class="album-slide">
      <div class="album-img">
        <img :src="attachImageUrl(tempList.pic)">
      </div>
      <div class="album-info">
        <h2>简介：</h2>
        <span>
          {{tempList.introduction}}
        </span>
      </div>
    </div>
    <div class="album-content">
      <div class="album-title">
        <p>{{tempList.title}}</p>
      </div>
      <div class="album-score">
        <div>
          <h3>歌单评分：</h3>
          <div>
            <el-rate v-model="average" disabled></el-rate>
          </div>
        </div>
        <span>{{average*2}}</span>
        <div>
          <h3>评价：</h3>
          <div @click="setRank">
            <el-rate v-model="rank" allow-half show-text></el-rate>
          </div>
        </div>
      </div>
      <div class="songs-body">
        <album-content :songList="listOfSongs">
          <template slot="title">歌单</template>
        </album-content>
        <comment :playId="songListId" :type="1"></comment>
      </div>
    </div>
  </div>
</template>

<script>
import {mixin} from '../mixins';
import {mapGetters} from 'vuex';
import {getSongBySongListId,addRanks,average} from '../api/index';
import AlbumContent from "../components/AlbumContent";
import Comment from "../components/Comment";
export default {
  name: "SongListAlbum",
  mixins: [mixin],
  computed:{
    ...mapGetters([
      'listOfSongs',      //当前播放列表
      'tempList',         //当前歌单对象
      'loginIn',          //用户是否已登录
      'userId',           //当前登录用户id
    ])
  },
  components:{
    AlbumContent,
    Comment
  },
  data(){
    return {
      songLists: [],
      songListId: '',
      average: 0,
      rank: 0,
      addRanksForm:{
        songListId: '',
        consumerId: '',
        score: ''
      },
    }
  },
  created() {
    this.songListId = this.$route.params.id
    this.getSong()
    this.getRank()
  },
  methods:{
    async getSong(){
      const {data:res} = await getSongBySongListId(this.songListId)
      this.$store.commit('setListOfSongs',res.data.songs)
    },
    async getRank(){
      const {data:res} = await average(this.songListId)
      this.average = res.data.avge/2
      console.log(this.average)
    },
    async setRank(){
      if(this.loginIn){
        this.addRanksForm.songListId = this.songListId
        this.addRanksForm.consumerId = this.userId
        this.addRanksForm.score = this.rank * 2
        const {data:res} = await addRanks(this.addRanksForm)
        this.$message.success("评分成功")
        this.getRank()
      }else{
        this.randk = 0
        this.$message.info("请先登录")
      }
    }
  },
}
</script>

<style lang="scss" scoped>
@import '../assets/css/song-list-album.scss';
</style>
