<template>
  <AppHeader></AppHeader>
  <div class="login-content">
    <div class="login-flex">
      <div class="login-left"></div>
      <!---->
      <div class="login-box">
        <h3 class="login-title">实名认证</h3>
        <form action="" id="renZ_Submit">
          <div class="alert-input">
            <input type="text" class="form-border user-name" @blur="checkName" v-model="name" name="username" placeholder="请输入您的真实姓名">
            <div class="err">{{nameErr}}</div>
            <p class="prompt_name"></p>
            <input type="text" class="form-border user-sfz" @blur="checkCard" v-model="card" name="sfz" placeholder="请输入15位或18位身份证号">
            <div class="err">{{cardErr}}</div>
            <p class="prompt_sfz"></p>
            <input type="text" class="form-border user-num" name="mobile" v-bind:value="phone" readonly>

          </div>
          <br/>
          <div class="alert-input-agree">
            <input type="checkbox" v-model="agree">我已阅读并同意<a href="javascript:;" target="_blank">《动力金融网注册服务协议》</a>
          </div>
          <div class="alert-input-btn">
            <input type="button" @click="realName" class="login-submit" value="认证">
          </div>
        </form>
        <div class="login-skip">
          暂不认证？ <a href="javascript:;">跳过</a>
        </div>
      </div>

    </div>
  </div>
  <AppFooter></AppFooter>
</template>

<script>
import AppHeader from "@/components/AppHeader";
import AppFooter from "@/components/AppFooter";
import layx from "vue-layx";
import {doPost} from "@/assets/api/api";

export default {
  name: "RealNameView",
  components:{
    AppHeader,
    AppFooter
  },
  data(){
    return{
      name:"",
      nameErr:'',
      card:'350721197702134399',
      cardErr:'',
      phone:'',
      agree:false
    }
  },
  mounted() {
    let userInfo = window.localStorage.getItem('local:userinfo');
    if(userInfo){
      this.phone = JSON.parse(userInfo).phone;
    }
  },
  methods:{
    checkName(){
      if( this.name === '' || this.name === null || this.name === undefined){
        this.nameErr='请输入有效的姓名';
      } else if( this.name.length < 2){
        this.nameErr='姓名至少两个字符';
      } else if( !/^[\u4e00-\u9fa5]{0,}$/.test(this.name)){
        this.nameErr='姓名必须是中文';
      } else {
        this.nameErr='';
      }
    },
    checkCard(){
      if( !this.card){
        this.cardErr='请输入身份证号';
      } else if(!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(this.card)){
        this.cardErr='身份证号不正确';
      } else {
        this.cardErr='';
      }
    },
    realName(){
      if(!this.agree){
        layx.msg('请阅读注册协议',{dialogIcon:'warn',position:['ct',800]});
        return;
      }
      this.checkName();
      this.checkCard();
      if(this.nameErr === '' && this.cardErr === ''){
        let param = {phone:this.phone,cardId:this.card,name:this.name};
        doPost('/user/realName',param).then(resp =>{
          if(resp.data.code === 1000){
            this.$router.push({
              path:'/user/center'
            })
          }
        })
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