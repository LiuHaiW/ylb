import axios from "axios";

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