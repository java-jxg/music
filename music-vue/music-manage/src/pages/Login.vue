<template>
  <div class="login-wrap">
    <div class="ms-title">music 后台管理登录</div>
    <div class="ms-login">
      <el-form :model="admin" :rules="rules" ref="ruleForm">
        <el-form-item prop="username">
          <el-input v-model="admin.name" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" v-model="admin.password" placeholder="密码"></el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm">登录</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import {getLoginStatus} from '../api/index'
  export default{
    data: function (){
      return {
        admin:{
          name: 'admin',
          password: '123'
        },
        rules: {
          username: [
            {required: true, message:'请输入用户名',trigger: 'blur'},
          ],
          password: [
            {required: true, message:'请输入密码',trigger: 'blur'}
          ]
        }
      }
    },
    methods:{
      async submitForm(){
        const{ data:res } = await getLoginStatus(this.admin)
        this.$router.push("/Info")
        localStorage.setItem('userName',this.admin.name);
        this.$message.success("登录成功")
      }
    }
  }


</script>


<style scoped>
.login-wrap {
  position: relative;
  background: url("../assets/img/background.jpg");
  background-attachment: fixed;
  background-position: center;
  background-size: cover;
  width: 100%;
  height: 100%;
}
.ms-title {
  position: absolute;
  top: 50%;
  width: 100%;
  margin-top: -230px;
  text-align: center;
  font-size: 30px;
  font-weight: 600;
  color: #fff;
}
.ms-login{
  position: absolute;
  left: 50%;
  top: 50%;
  width: 300px;
  height: 160px;
  margin-left:  -150px;
  margin-top: -150px;
  padding: 40px;
  border-right: 5px;
  background: #fff;
}
.login-btn{
  text-align: center;
}
.login-btn button{
  width: 100%;
  height: 36px;
}
</style>
