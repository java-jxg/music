import axios from 'axios'
import {Message, MessageBox} from 'element-ui'

axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset-UTF-8'

const service = axios.create({
  baseURL: "http://localhost:8887",
  timeout: 20000,
  withCredentials: true,

})


// response 拦截器
service.interceptors.response.use(
  response => {
    /**
     * code为非20000是抛错 可结合自己业务进行修改
     */
    const res = response.data
    // debugger
    if (res.code !== 20000) {
      Message({
        message: res.message,
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject('error')
    } else {
      return response
    }
  },
  error => {
    console.log('err' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)



export function get(url,params={}){
  return new Promise((resolve,reject)=>{
    service.get(url,{params:params})
      .then(response => {
        resolve(response)
      })
      .catch(err => {
        reject(err)
      })
  })
}

/**
 * 封装post方法
 */
export function post(url,data={}){
  return new Promise((resolve,reject) => {
    service.post(url,data)
      .then(response =>{
        resolve(response);
      })
      .catch(err =>{
        reject(err);
      })
  });
}

/**
 * 封装put方法
 */
export function put(url,data={}){
  return new Promise((resolve,reject) => {
    service.put(url,data)
      .then(response =>{
        resolve(response);
      })
      .catch(err =>{
        reject(err);
      })
  });
}

/**
 * 封装delete方法
 */
export function del(url,data={}){
  return new Promise((resolve,reject) => {
    service.delete(url,data)
      .then(response =>{
        resolve(response);
      })
      .catch(err =>{
        reject(err);
      })
  });
}

export default service
