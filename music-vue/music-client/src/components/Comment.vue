<template>
  <div>
    <div class="comment">
      <h2>评论</h2>
      <div class="comment-msg">
        <div class="comment-img">
          <img :src="attachImageUrl(avator)">
        </div>
        <el-input class="comment-input" type="textarea" :rows="2" placeholder="请输入内容" v-model="textarea">
        </el-input>
      </div>
      <el-button type="primary" class="sub-btn" @click="postComment">评论</el-button>
    </div>
    <p>精彩评论：共{{commentList.length}}条评论</p>
    <hr>
    <ul class="popular" v-for="(item,index) in commentList" :key="index">
      <li>
        <div class="popular-img">
          <img :src="attachImageUrl(userPic[index])">
        </div>
        <div class="popular-msg">
          <ul>
            <li class="name">{{userName[index]}}</li>
            <li class="time">{{item.createTime}}</li>
            <li class="content">{{item.content}}</li>
          </ul>
        </div>
        <div class="up" ref="up" @click="postUp(item.id,item.up,index)">
          <svg class="icon">
            <use xlink:href="#icon-zan"></use>
          </svg>
          {{item.up}}
        </div>
      </li>
    </ul>

  </div>
</template>

<script>
import {mixin} from '../mixins';
import {mapGetters} from 'vuex';
import {addComment,getConsumerById,getAllComment,updateComment} from "../api";

export default {
  name: "Comment",
  mixins: [mixin],
  props: [
    'playId',       //歌曲或歌单id
    'type'          //0歌曲、1歌单
  ],
  computed:{
    ...mapGetters([
      'id',               //歌曲或歌单id
      'loginIn',          //用户是否已登录
      'userId',           //当前登录用户id
      'avator',           //当前登录用户头像
    ])
  },
  data(){
    return{
      textarea: '',        //存放输入的评论内容
      commentList: [],    //存放评论列表
      userPic: [],        //用户的头像
      userName: [],       //用户的昵称
      addCommentForm: {
        userId:'',
        type: '',
        songId: '',
        songListId: '',
        content: '',
        up: '',
      },
      editCommentForm:{
        id: '',
        up: ''
      }
    }
  },
  methods:{
    async postComment(){
      if(this.loginIn){
        if(this.type==0){
          this.addCommentForm.songId = this.playId
        }else{
          this.addCommentForm.songListId = this.playId
        }
        this.addCommentForm.userId = this.userId
        this.addCommentForm.type = this.type
        this.addCommentForm.content = this.textarea
        const {data:res} = await addComment(this.addCommentForm)
        this.$message.success("评论成功")
      }else{
        this.$message.info("请先登录")
      }
    },
    async getComment(){
      const {data:res} = await getAllComment(this.type,this.playId)
      this.commentList = res.data.comments
      for(let item of this.commentList){
        this.getUsers(item.userId)
      }
    },
    async getUsers(id){
      const {data:res} = await getConsumerById(id)
      this.userPic.push(res.data.consumer.avator);
      this.userName.push(res.data.consumer.username);
    },
    //给某一个评论点赞
    async postUp(id,up,index){
      if(this.loginIn){
        this.editCommentForm.id = id
        this.editCommentForm.up = up+1
        const {data:res} = await updateComment(this.editCommentForm)
        this.$refs.up[index].children[0].style.color = '#2796cd';
        this.getComment();
      }else{
        this.$message.info("请先登录")
      }
    }

  },
  mounted(){
    this.getComment()
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/comment.scss';
</style>

