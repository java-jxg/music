export const mixin = {
  methods:{
    //提示信息
    notify(title,type){
      this.$notify({
        title: title,
        type: type
      })
    },
    //根据相对地址获取绝对地址
    getUrl(url){
      return `${this.$store.state.HOST}/${url}`
    },
    //获取性别中文
    changeSex(value){
      if(value == 0){
        return '女';
      }
      if(value == 1){
        return '男';
      }
      if(value == 2){
        return '组合';
      }
      if(value == 3){
        return '不明';
      }
      return value;
    },
    //获取生日
    attachBirth(val){
      return String(val).substr(0,10);
    },
    //上传图片之前的校验
    beforeAvatorUpload(file){
      const isJPG = (file.type === 'image/jpeg')||(file.type === 'image/png');
      if(!isJPG){
        this.$message.error('上传头像图片只能是jpg或png格式');
        return false;
      }
      const isLt2M = (file.size / 1024 /1024) < 2;
      if(!isLt2M){
        this.$message.error('上传头像图片大小不能超过2MB');
        return false;
      }
      return true;
    },
    //上传图片成功之后要做的工作
    handleAvatorSuccess(res){
      let _this = this;
      if(res.code == 20000){
        _this.getData();
        _this.$notify({
          title: '上传成功',
          type: 'success'
        });
      }else{
        _this.$notify({
          title: '上传失败',
          type: 'error'
        });
      }
    },
    handleSelectionChange(val){
      this.multipleSelection = val
    }
  }
}
