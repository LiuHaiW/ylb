<template>
  <AppHeader></AppHeader>
  <div class="login-content">
    <div class="login-flex">
      <div class="login-left">
        <p>万民用户知心托付&nbsp;&nbsp;&nbsp;&nbsp;<span>{{ averageRate }}%</span>历史年化收益</p>
        <p>千万级技术研发投入&nbsp;&nbsp;&nbsp;&nbsp;亿级注册资本平台  </p>
      </div>
      <!---->
      <div class="login-box">
        <h3 class="login-title">用户注册</h3>
        <form action="" id="register_Submit">
          <div class="alert-input">
            <input type="text" class="form-border user-num" v-model="phone" @blur="checkPhone" placeholder="请输入11位手机号">
            <div class="err">{{phoneErr}}</div>
            <p class="prompt_num"></p>
            <input type="password" placeholder="请输入6-20位英文和数字混合密码" v-model="secret" @blur="checkSecret" class="form-border user-pass" autocomplete name="password">
            <div class="err">{{secretErr}}</div>
            <p class="prompt_pass"></p>
            <div class="form-yzm form-border">
              <input class="yzm-write" type="text" v-model="code" @blur="checkCode" placeholder="输入短信验证码">
              <input class="yzm-send" type="button" v-model="yzmText" @click="sendCode" >
            </div>
            <div class="err">{{codeErr}}</div>
            <p class="prompt_yan"></p>
          </div>
          <div class="alert-input-agree">
            <input type="checkbox" v-model="agree">我已阅读并同意<a href="javascript:;" target="_blank">《动力金融网注册服务协议》</a>
          </div>
          <div class="alert-input-btn">
            <input type="button" class="login-submit" @click="userRegister" value="注册">
          </div>
        </form>
        <div class="login-skip">
          已有账号？ <a href="javascript:void(0);" @click="goLink('/user/login')">登录</a>
        </div>
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
import md5 from 'js-md5';

export default {
  name: "LoginView",
  components: {
    AppHeader,
    AppFooter
  },
  data(){
    return{
      averageRate: 0.0,
      phone:'',
      phoneErr:'',
      secret:'',
      secretErr:'',
      code:'',
      codeErr:'',
      yzmText:'获取验证码',
      leftTimeDoing:false,
      agree:false
    }
  },
  mounted() {
    doGet('/application/info').then(resp =>{
      this.averageRate = resp.data.info.averageRate
    })
  },
  methods:{
    goLink(url, parameters) { //跳转页面
      this.$router.push({
        path: url,
        query: parameters
      })
    },
    checkPhone(){ //检查手机号格式是否正确
      if(this.phone==='' || this.phone === null ||this.phone===undefined){
        this.phoneErr='手机号必须输入';
      } else if(this.phone.length != 11){
        this.phoneErr='手机号是11位的';
      } else if( !/^1[1-9]\d{9}$/.test(this.phone)){
        this.phoneErr='请检查手机号的格式';
      } else {
        this.phoneErr='';
      }
    },
    checkSecret() { //密码
      if (this.secret === '' || this.secret === null || this.secret === undefined) {
        this.secretErr = '请输入密码';
      } else if (!/^(([a-zA-Z]+[0-9]+)|([0-9]+[a-zA-Z]+))[a-zA-Z0-9]*/.test(this.secret)) {
        this.secretErr = '密码是包含字母和数字组合的';
      } else if ( this.secret.length < 6 || this.secret.length > 20) {
        this.secretErr='密码长度是6-20位';
      }else {
        this.secretErr='';
      }
    },
    checkCode(){
      if(this.code === '' || this.code === null || this.code === undefined){
        this.codeErr = '请输入验证码';
      }else if(this.code.length != 4){
        this.codeErr = '验证码是4位的';
      }else {
        this.codeErr = '';
      }
    },
    sendCode(){
      if(this.leftTimeDoing){
        return;
      }
      this.checkPhone();
      if(this.phoneErr === ''){
        let second = 10;
        this.leftTimeDoing = true;
        this.yzmText = second + "秒后重新获取";
        let interval = setInterval(()=>{
            second = second - 1;
            if(second <= 0){
              this.leftTimeDoing=false;
              this.yzmText = "获取验证码";
              clearInterval(interval);
            }else{
              this.yzmText = second + "秒后重新获取";
            }
        } ,1000);
        doGet('/sms/code',{cmd:'reg',phone:this.phone}).then(resp => {
          if(1000 === resp.data.code){
            layx.msg('短信发送成功，请查收',{dialogIcon:'success',position:['ct',800]});
          }
        })
      }
    },
    userRegister(){
      this.checkPhone();
      this.checkCode();
      this.checkSecret();
      if(this.phoneErr === '' && this.codeErr === '' && this.secretErr === ''){
        if(this.agree){
          let param = {phone:this.phone,secret:md5(this.secret),code:this.code};
          doPost('/user/register',param).then(resp=>{
            if( resp.data.code === 1000){
              layx.msg('注册成功',{dialogIcon:'success',position:['ct',800]});
              //跳转到登录页面
              this.$router.push({
                path:'/user/login'
              })
            }
          })
        }else {
          layx.msg('请阅读注册协议',{dialogIcon:'warn',position:['ct',800]});
        }
      }
    }
  }
}
</script>

<style scoped>
.err{
  color: red;
  font-size: 18px;
}
</style>