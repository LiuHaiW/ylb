<template>
  <!--头部-->
  <div class="public-head">
    <div class="public-head-nav">
      <div class="public-head-left">
        <h1 class="public-head-logo"><a href="javascript:;">
          <img src="@/assets/image/logo.png" alt="">
        </a></h1>
        <ul class="public-head-list">
          <li><a href="/" >主页</a></li>
          <li class="public-head-hover">
            <a href="javascript:void(0);">我要投资</a>
            <!--二级导航-->
            <div class="two-title">
              <a href="javascript:void(0);" @click="goLink('/product/list',{ptype:1})">优选类产品</a>
              <a href="javascript:void(0);" @click="goLink('/product/list',{ptype:2})">散标类产品</a>
            </div>
          </li>
          <li><a href="javascript:void(0);" >借款人信息</a></li>
          <li><a href="javascript:void(0);" >信息披露</a></li>
          <li><a href="javascript:void(0);" >安全计划</a></li>
        </ul>
      </div>
      <div class="public-head-right" v-if="check">
        <a href="javascript:void(0);" @click="goLink('/user/realName')">实名认证</a>
        <a href="javascript:void(0);" @click="goLink('/user/center')">用户中心</a>
        <a href="javascript:void(0);" @click="loginUp">退出</a>
      </div>
      <div class="public-head-right" v-else>
        <a href="javascript:void(0);" @click="goLink('/user/login')">登录</a>
        <a href="javascript:void(0);" @click="goLink('/user/register')">注册</a>
      </div>
    </div>
  </div>
  <!--end-->
</template>

<script>
import {doGet} from "@/assets/api/api";

export default {
  name: "AppHeader",
  methods:{
    goLink(url, parameters) { //调整页面
      this.$router.push({
        path: url,
        query: parameters
      })
    },
    loginUp(){
      doGet('/user/loginUp').then(resp =>{
        if(resp.data.code === 1000){
          window.localStorage.removeItem("local:userinfo");
          window.location.href = '/';
        }
      })
    }
  },
  data(){
    return{
      check:false
    }
  },
  mounted() {
    if(window.localStorage.getItem("local:userinfo")){
      this.check = true;
    }
  }
}
</script>

<style scoped>

</style>