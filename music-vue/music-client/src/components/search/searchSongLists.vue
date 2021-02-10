<template>
  <div class="search-song-Lists">
    <content-list :contentList="albumDatas"></content-list>
  </div>
</template>

<script>
import ContentList from '../ContentList';
import {getSongListByTitle} from '../../api/index';
import {mapGetters} from "vuex";
export default {
  name: "searchSongLists",
  components:{
    ContentList
  },
  computed: {
    ...mapGetters([
      'keywords'
    ])
  },
  data(){
    return{
      albumDatas: []
    }
  },
  mounted(){
    this.getSearchList();
  },
  methods:{
    async getSearchList(){
      if(!this.keywords){
        this.$message.error("您输入的内容为空")
      }else{
        const {data:res} = await getSongListByTitle(this.keywords)
        this.albumDatas = res.data.songLists

      }
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../../assets/css/search-song-lists.scss';
</style>
