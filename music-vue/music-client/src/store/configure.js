const configure = {
  state:{
    HOST: 'http://127.0.0.1:8887',
    activeName: '',
    showAside: false,           //是否显示播放中的歌曲列表
    loginIn: false,
    keywords: '',
    isActive: false
  },
  getters: {
    activeName: state => {
      let activeName = state.activeName
      if(!activeName){
        activeName = JSON.parse(window.sessionStorage.getItem('activeName'))
      }
      return activeName
    },
    showAside: state => {
      let showAside = state.showAside
      if(!showAside){
        showAside = JSON.parse(window.sessionStorage.getItem('showAside'))
      }
      return showAside
    },
    loginIn: state => {
      let loginIn = state.loginIn
      if(!loginIn){
        loginIn = JSON.parse(window.sessionStorage.getItem('loginIn'))
      }
      return loginIn
    },
    keywords: state => {
      let keywords = state.keywords
      if(!keywords){
        keywords = JSON.parse(window.sessionStorage.getItem('keywords'))
      }
      return keywords
    },
    isActive: state => {
      let isActive = state.isActive
      if(!isActive){
        isActive = JSON.parse(window.sessionStorage.getItem('isActive'))
      }
      return isActive
    },
  },
  mutations: {
    setActiveName: (state,activeName) => {
      state.activeName = activeName
      window.sessionStorage.setItem('activeName',JSON.stringify(activeName))
    },
    setShowAside: (state,showAside) => {
      state.showAside = showAside
      window.sessionStorage.setItem('showAside',JSON.stringify(showAside))
    },
    setLoginIn: (state,loginIn) => {
      state.loginIn = loginIn
      window.sessionStorage.setItem('loginIn',JSON.stringify(loginIn))
    },
    setKeywords: (state,keywords) => {
      state.keywords = keywords
      window.sessionStorage.setItem('keywords',JSON.stringify(keywords))
    },
    setIsActive: (state,isActive) => {
      state.isActive = isActive
      window.sessionStorage.setItem('isActive',JSON.stringify(isActive))
    },
  }
}

export default configure
