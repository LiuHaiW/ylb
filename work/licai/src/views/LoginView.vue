<template>
  <AppHeader></AppHeader>
  <div class="login-content">
    <div class="login-flex">
      <div class="login-left">
        <h3>加入动力金融网</h3>
        <p>坐享<span>{{ averageRate }}%</span>历史年化收益</p>
        <p>平台用户<span>{{ registerUsers }}</span>位  </p>
        <p>累计成交金额<span>{{allBidMoney}}</span>元</p>
      </div>
      <!---->
      <div class="login-box">
        <h3 class="login-title">欢迎登录</h3>
        <form action="" id="login_Submit">
          <div class="alert-input">
            <!--<input class="form-border user-name" name="username" type="text" placeholder="您的姓名">
            <p class="prompt_name"></p>-->
            <input type="text" class="form-border user-num" v-model="phone" placeholder="请输入11位手机号">
            <p class="prompt_num"></p>
            <input type="password" placeholder="请输入登录密码" v-model="secret" class="form-border user-pass" autocomplete name="password">
            <p class="prompt_pass"></p>
            <div class="form-yzm form-border">
              <input class="yzm-write" type="text" v-model="code" placeholder="输入短信验证码">
              <input class="yzm-send" type="text" value="获取验证码" @click="sendSmsCode" id="yzmBtn" readonly="readonly" >
            </div>
            <p class="prompt_yan"></p>
          </div>
          <div class="alert-input-btn">
            <input type="button" class="login-submit" @click="userLogin" value="登录">
          </div>
        </form>

      </div>

    </div>
  </div>
  <AppFooter></AppFooter>
</template>

<script>
import AppHeader from "@/components/AppHeader";
import AppFooter from "@/components/AppFooter";
import {doGet, doPost} from "@/assets/api/api";
import layx from "vue-layx";
import md5 from "js-md5";

export default {
  name: "LoginView",
  components: {
    AppHeader,
    AppFooter
  },
  data(){
    return{
      registerUsers: 0,
      allBidMoney: 0,
      averageRate: 0.0,
      phone: '13700000000',
      secret: '111aaa',
      code: ''
    }
  },
  mounted() {
    doGet('/application/info').then(resp => {
      if (resp.data.code === 1000) {
        this.registerUsers = resp.data.info.registerUsers;
        this.allBidMoney = resp.data.info.allBidMoney;
        this.averageRate = resp.data.info.averageRate;
      }
    })
  },
  methods:{
    sendSmsCode() {
      doGet('/sms/code', {phone: this.phone, cmd: 'LOGIN'}).then(resp => {
        if (resp.data.code === 1000) {
          layx.msg('短信发送成功，请查收', {dialogIcon: 'success', position: ['ct', 800]});
        }
      })
    },
    userLogin(){
      let param = {phone:this.phone,secret:md5(this.secret),code:this.code};
      doPost('/user/login',param).then(resp =>{
        if(resp.data.code === 1000){
          let userInfo = resp.data.info;
          window.localStorage.setItem("local:userinfo",JSON.stringify(userInfo));
          let name = userInfo.name;
          let path = '';
          if (name === '') {
            path = '/user/realName'
          } else {
            path = '/user/center'
          }
          this.$router.push({
            path: path
          });
        }
      })
    }
  }
}
</script>

<style scoped>

</style>