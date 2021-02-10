<template>
  <div id="app">
    <the-header></the-header>
    <router-view class="music-content" v-if="isRouterAlive"/>
    <back-to-top :visibility-height="300" :back-position="50" transition-name="fade" />
    <the-footer></the-footer>
    <song-audio></song-audio>
    <the-aside></the-aside>
    <play-bar></play-bar>

  </div>
</template>

<script>
import BackToTop from './components/BackToTop'
import TheHeader from "./components/TheHeader";
import ScrollTop from "./components/ScrollTop";
import TheFooter from "./components/TheFooter";
import SongAudio from "./components/SongAudio";
import PlayBar from "./components/PlayBar";
import TheAside from "./components/TheAside";
export default {
  name: 'App',
  components: {
    TheHeader,
    ScrollTop,
    BackToTop,
    TheFooter,
    SongAudio,
    PlayBar,
    TheAside,
  },
  provide(){
    return{
      reload: this.reload
    }
  },
  data(){
    return{
      isRouterAlive: true
    }
  },
  methods:{
    reload(){
      this.isRouterAlive = false
      this.$nextTick(function(){
        this.isRouterAlive = true
      })
    }
  }
}
</script>

<style  lang="scss" scoped>
@import './assets/css/app.scss';
</style>
