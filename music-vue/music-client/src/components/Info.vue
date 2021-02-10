<template>
  <div>
    <div class="info">
      <div class="title">
        <span>编辑个人资料</span>
      </div>
      <hr/>
      <div class="personal">
        <el-form :model="registerForm" ref="registerForm" label-width="70px" class="demo-ruleForm" :rules="rules">
          <el-form-item prop="username" label="用户名">
            <el-input v-model="registerForm.username" placeholder="用户名"></el-input>
          </el-form-item>
          <el-form-item prop="password" label="密码">
            <el-input type="password" v-model="registerForm.password" placeholder="密码"></el-input>
          </el-form-item>
          <el-form-item prop="sex" label="性别">
            <el-radio-group v-model="registerForm.sex">
              <el-radio :label="0">女</el-radio>
              <el-radio :label="1">男</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item prop="phoneNum" label="手机">
            <el-input v-model="registerForm.phoneNum" placeholder="手机"></el-input>
          </el-form-item>
          <el-form-item prop="email" label="邮箱">
            <el-input v-model="registerForm.email" placeholder="邮箱"></el-input>
          </el-form-item>
          <el-form-item prop="birth" label="生日">
            <el-date-picker type='date' :editable="false" v-model="registerForm.birth" placeholder="选择日期" style="width: 100%;"></el-date-picker>
          </el-form-item>
          <el-form-item prop="introduction" label="签名">
            <el-input v-model="registerForm.introduction" placeholder="签名"></el-input>
          </el-form-item>
          <el-form-item prop="location" label="地区">
            <el-select v-model="registerForm.location" placeholder="地区" style="width: 100%;">
              <el-option v-for=" item in cities" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div class="btn">
          <div @click="saveMsg">保存</div>
          <div @click="goback(-1)">取消</div>
        </div>
      </div>

    </div>
  </div>
</template>
<script>
import {mapGetters} from 'vuex'
import {rules,cities} from '../assets/data/form'
import {mixin} from '../mixins'
import {getConsumerById,updateConsumer} from '../api/index'

export default {
  name: 'info',
  mixins: [mixin],
  data() {
    return {
      registerForm: {
        id: '',
        username: '',       //用户名
        password: '',       //密码
        sex: '',            //性别
        phoneNum: '',       //手机
        email: '',          //邮箱
        birth: '',          //生日
        introduction: '',   //签名
        location: '',       //地区
      },
      cities: [],            //所有的地区--省
      rules: {}               //表单提交的规则
    }
  },
  computed:{
    ...mapGetters([
      'userId'
    ])
  },
  created() {
    this.rules = rules;
    this.cities = cities;
  },
  mounted(){
    this.getMsg(this.userId);
  },
  methods:{
    async getMsg(userId){
      const {data:res} = await getConsumerById(userId)
      this.registerForm = res.data.consumer
    },
    async saveMsg(){
      const {data:res} = await updateConsumer(this.registerForm)
      this.$store.commit('setUsername',this.registerForm.username);
      this.$message.success("修改成功")
      this.$router.push("/");
    },
    goback(index){
      this.$router.go(index);
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/info.scss';
</style>
