import axios from "axios";
import layx from "vue-layx";

//封装axios
//设置axios的默认值
axios.defaults.baseURL='http://localhost:8000/ylb';
axios.defaults.timeout=18000;


//封装get请求方式
export function doGet(url,parameters){
    return axios({
        url: url,
        method:'get',
        params:parameters
    })
}

//封装post请求, 参数是对象，axios将obj转为json格式，发送给服务器
export function doPost(url,obj){
    return axios({
        url:url,
        method:'post',
        data: obj
    })
}

axios.interceptors.response.use(resp=>{
    if(resp.data.code > 1000){
        layx.msg(resp.data.message,{dialogIcon:'error',width:260,position:['ct',800]});
    }
    return resp;
},error => {
    console.log("应答拦截器错误："+error);
    window.location.href="/";
})