<template>
  <div>
    <login-logo></login-logo>
    <div class="signUp">
      <div class="signUp-head">
        <span>用户注册</span>
      </div>
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
          <el-date-picker v-model="registerForm.birth" placeholder="选择日期" style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item prop="introduction" label="签名">
          <el-input v-model="registerForm.introduction" placeholder="签名"></el-input>
        </el-form-item>
        <el-form-item prop="location" label="地区">
          <el-select v-model="registerForm.location" placeholder="地区" style="width: 100%;">
            <el-option v-for=" item in cities" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <div class="login-btn">
          <el-button @click="goback(-1)">取消</el-button>
          <el-button type="primary" @click="SignUp">确定</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import LoginLogo from "../components/LoginLogo"
import {rules,cities} from '../assets/data/form'
import {mixin} from '../mixins'
import {signUp} from '../api/index'
export default {
  name: "SignUp",
  mixins: [mixin],
  components: {
    LoginLogo
  },
  data(){
    return {
      registerForm:{      //添加框
        username: '',
        password: '',
        sex: '1',
        phoneNum: '',
        email: '',
        birth: '',
        introduction: '',
        location: '',
        avator:'/img/consumerPic/hhh.jpg',
      },
      cities: [],            //所有的地区--省
      rules: {},               //表单提交的规则
    }
  },
  methods:{
    goback(index){
      this.$router.go(index);
    },
    SignUp(){
      this.$refs['registerForm'].validate(async valid=>{
        if(valid){
          const{ data:res } = await signUp(this.registerForm)
          this.$message.success("注册成功")
          this.registerForm = {}
          this.$router.push({path: '/'})
        }
      })
    }
  },
  created() {
    this.rules = rules;
    this.cities = cities;
  },
}
</script>

<style lang="scss" scoped>
@import "../assets/css/sign-up.scss";
</style>
