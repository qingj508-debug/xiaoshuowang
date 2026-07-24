import axios from 'axios'
import router from '@/router'
import { ElMessage } from 'element-plus'
import {getToken ,removeToken,removeNickName} from '@/utils/auth'
import JSONBIG from 'json-bigint';

// axios.create 方法创建一个axios实例, 在实例当中进行统一配置,所用通过该实例发送请求 使用同一配置
axios.defaults.baseURL = 'http://localhost:8888/api'
axios.defaults.timeout = 10000
axios.defaults.withCredentials = true
axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
axios.defaults.headers.post['Content-Type'] = 'application/json'

/* 解决前端处理19位id精度丢失问题 */
axios.defaults.transformResponse = [
   function (data) {
     const json = JSONBIG({
       storeAsString: true
     })
     const res = json.parse(data)
     return res
   }
]


axios.interceptors.request.use(config => {
  config.headers['Authorization'] = getToken()
  return config
}, error => {
    console.log(error)
    Promise.reject(error)
})


axios.interceptors.response.use(res => {
    if (typeof res.data !== 'object') {
      ElMessage.error('服务端异常！')
      return Promise.reject(res)
    }
    if (res.data.code != "200") {
      if (res.data.msg) {
        ElMessage.error(res.data.msg)
      }
      // 登录已过期
      if (res.data.code == '1001') {
        // 移除 token 
        removeToken();
        removeNickName();
        router.push({ path: '/login' })
      }
  
      return Promise.reject(res.data)
    }
    return res.data
  }, error => {
    ElMessage.error('网络异常！')
    console.log(error)
    Promise.reject(error)
  })
  


export default axios