<template>
  <div class="the-header">
    <div class="header-logo" @click="goHome">
      <svg class="icon">
        <use xlink:href="#icon-erji"></use>
      </svg>
      <span>music</span>
    </div>
    <ul class="navbar">
      <li :class="{active: item.name == activeName}" v-for="item in navMsg" :key="item.path" @click="goPath(item.path,item.name)">
        {{item.name}}
      </li>
      <li>
        <div class="header-search">
          <input type="text" placeholder="搜索音乐" @keyup.enter="goSearch()" v-model="keywordse">
          <div class="search-btn" @click="goSearch()">
            <svg class="icon">
              <use xlink:href = "#icon-sousuo"></use>
            </svg>
          </div>
        </div>
      </li>
      <li v-show="!loginIn" :class="{active: item.name == activeName}" v-for="item in loginMsg" :key="item.path" @click="goPath(item.path,item.name)">
        {{item.name}}
      </li>
    </ul>
  <div class="header-right" v-show="loginIn">
    <div id="user">
      <img :src="attachImageUrl(avator)">
    </div>
    <ul class="menu">
      <li v-for="(item,index) in menuList" :key="index" @click="goMenuList(item.path)">{{item.name}}</li>
    </ul>
  </div>
  </div>
</template>

<script>
import {navMsg,loginMsg,menuList} from '../assets/data/header'
import {mapGetters} from 'vuex'
export default {
  name: "TheHeader",
  inject:['reload'],
  data(){
    return {
      navMsg: [],
      keywordse: '',
      loginMsg: [],
      menuList: [],
    }
  },
  methods:{
    goHome(){
      this.$router.push({path:"/"})
    },
    goPath(path,name){
      if(!this.loginIn && path=='/my-music'){
        this.$message.error("请先登录")
      }else{
        console.log(path)
        this.$store.commit('setActiveName',name)
        this.$router.push({path: path})
      }
    },
    goSearch(){
      this.$router.push({path:'/search',query:{keywords: this.keywordse}})
      this.$store.commit('setKeywords',this.keywordse)
      this.reload()
    },
    attachImageUrl(srcUrl){
      console.log(this.$store.state.configure.HOST+srcUrl)
      return srcUrl ? this.$store.state.configure.HOST+srcUrl : '../assets/img/user.jpg'
    },
    goMenuList(path){
      if(path == 0){
        this.$store.commit('setLoginIn',false);
        this.$router.go(0);
      }else{
        this.$router.push({path:path});
      }
    },
  },
  created () {
    this.navMsg = navMsg
    this.loginMsg = loginMsg
    this.menuList = menuList
  },
  computed:{
    ...mapGetters([
      'activeName',
      'loginIn',
      'avator',
      'keywords'
    ])
  },
  mounted(){
    document.querySelector('#user').addEventListener('click',function(e){
      document.querySelector('.menu').classList.add("show");
      e.stopPropagation()           //关键在于阻止冒泡
    },false);
    document.querySelector('.menu').addEventListener('click',function(e){
      e.stopPropagation()           //点击菜单内部时，阻止时间冒泡，这样，点击内部时，菜单不会关闭
    },false);
    document.addEventListener('click',function(){
      document.querySelector('.menu').classList.remove('show');
    },false);
  },
}
</script>

<style lang="scss" scoped>
@import "../assets/css/the-header.scss";
</style>
