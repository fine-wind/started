import axios from 'axios';
import {ElMessage} from "element-plus";
import {getToken} from "@/auth";

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
const service = axios.create({
    baseURL: `${location.origin}/API`,
    withCredentials: true,
    timeout: 5000
})

// request拦截器
service.interceptors.request.use(config => {
    // 是否需要设置 token
    const isToken = (config.headers || {}).isToken === false
    // 是否需要防止数据重复提交
    const isRepeatSubmit = (config.headers || {}).repeatSubmit === false
    if (!isToken && getToken()) {
        config.headers['Authorization'] = 'Bearer ' + getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
    }
    // // get请求映射params参数
    // if (config.method === 'get' && config.params) {
    //   let url = config.url + '?';// + tansParams(config.params);
    //   url = url.slice(0, -1);
    //   config.params = {};
    //   config.url = url;
    // }
    // console.debug('请求拦截器', config)
    if (!isRepeatSubmit && (config.method === 'post' || config.method === 'put' || config.method === 'delete')) {
        if (typeof config.data !== 'string') {
            config.data = JSON.stringify(config.data)
        }
    }
    return config
}, error => {
    console.error(error)
    Promise.reject(error)
})

// 响应拦截器
service.interceptors.response.use(res => {
        // 未设置状态码则默认成功状态
        const code = res.data.code || 200;
        // 获取错误信息
        const msg = res.data?.msg || '';
        // 二进制数据则直接返回
        if (res.request.responseType === 'blob' || res.request.responseType === 'arraybuffer') {
            return res.data
        }
        if (code === 401) {
            ElMessage({message: msg, type: 'error'})
            return Promise.reject('无效的会话，或者会话已过期，请重新登录。')
        } else if (code === 500) {
            ElMessage({message: msg, type: 'error'})
            return Promise.reject(new Error(msg))
        } else if (code !== 200) {
            ElMessage.error(msg)
            return Promise.reject(res)
        } else {
            return Promise.resolve(res.data)
        }
    },
    error => {
        console.log('err', error)
        let {message} = error;
        if (message === "Network Error") {
            message = "后端接口连接异常";
        } else if (message.includes("timeout")) {
            message = "系统接口请求超时";
        } else if (message.includes("Request failed with status code")) {
            message = "系统接口" + message.substr(message.length - 3) + "异常";
        }
        ElMessage({message: message, type: 'error', duration: 5 * 1000})
        return Promise.reject(error)
    }
)

const HTTP = {
    GET: (url, params) => {
        return service({
            url: url,
            method: "get",
            params: params
        })
    },
    POST: (url, data, params) => {
        return service({
            url: url,
            method: "POST",
            data: data,
            params: params
        })
    },
    POST_FROM: (url, data, params) => {
        return service({
            url: url,
            method: "POST",
            data: data,
            params: params,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
        })
    },
}

export default HTTP;
