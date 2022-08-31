<template>
  <AppHeader></AppHeader>
  <div class="content clearfix">
    <div class="detail-left">
      <div class="detail-left-title">{{ product.productName }}（{{ product.productNo }}期）</div>
      <ul class="detail-left-number">
        <li>
          <span>历史年化收益率</span>
          <p><b>{{ product.rate }}</b>%</p>
          <span>历史年化收益率</span>
        </li>
        <li>
          <span>募集金额（元）</span>
          <p><b>{{ product.productMoney }}</b>元</p>
          <span v-if="product.leftProductMoney===0">已满标</span>
          <span v-else>募集中&nbsp;&nbsp;剩余募集金额{{ product.leftProductMoney }}元</span>
        </li>
        <li v-if="product.productType===0">
          <span>投资周期</span>
          <p><b>{{ product.cycle }}</b>天</p>
        </li>
        <li v-else>
          <span>投资周期</span>
          <p><b>{{ product.cycle }}</b>个月</p>
        </li>

      </ul>
      <div class="detail-left-way">
        <span>收益获取方式</span>
        <span>收益返还：<i>到期还本付息</i></span>
      </div>
      <!--投资记录-->
      <div class="datail-record">
        <h2 class="datail-record-title">投资记录</h2>
        <div class="datail-record-list">
          <table align="center" width="880" border="0" cellspacing="0" cellpadding="0">
            <colgroup>
              <col style="width: 72px" />
              <col style="width: 203px" />
              <col style="width: 251px" />
              <col style="width: 354px" />
            </colgroup>
            <thead class="datail_thead">
            <tr>
              <th>序号</th>
              <th>投资人</th>
              <th>投资金额（元）</th>
              <th>投资时间</th>
            </tr>
            </thead>
            <tbody v-for="(userBidVo,index) in userBidVoList" :key="userBidVo.id">
            <tr>
              <td>{{ index + 1 }}</td>
              <td class="datail-record-phone">{{ userBidVo.phone }}</td>
              <td>{{ userBidVo.bidMoney }}</td>
              <td>{{ userBidVo.bidTime }}</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>

    </div>
    <!--右侧-->
    <div class="detail-right">
      <div class="detail-right-title">立即投资</div>
      <div class="detail-right-mode">
        <h3 class="detail-right-mode-title">收益方式</h3>
        <p class="detail-right-mode-p"><span>到期还本付息</span></p>
        <h3 class="detail-right-mode-title">我的账户</h3>
        <div class="detail-right-mode-rmb" v-if="login">
          <p>账户资金（元）：{{ countMoney }}</p>
        </div>
        <div class="detail-right-mode-rmb" v-else>
          <a href="javascript:void(0);" @click="goLink('/user/login')">请登录</a>
        </div>
        <h3 class="detail-right-mode-title" v-if="allIncomeMoney > 0 ">预计本息收入（元）:{{allIncomeMoney}}</h3>
        <h3 class="detail-right-mode-title" v-else>预计本息收入（元）</h3>
        <form action="" id="number_submit">
          <p>请在下方输入投资金额</p>
          <input type="text" v-model="bidMoney" @blur="checkMoney" placeholder="请输入日投资金额，应为100元整倍数" name="" class="number-money" >
          <div class="err">{{ bidMoneyErr }}</div>
          <input type="button" @click="userInvest" value="立即投资" class="submit-btn">
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

export default {
  name: "ProdDetailView",
  components:{
    AppHeader,
    AppFooter
  },
  data(){
    return{
      product: {
        id: 0,
        productName: "",
        rate: 0,
        cycle: 0,
        releaseTime: "",
        productType: 0,
        productNo: "20170726",
        productMoney: 10000,
        leftProductMoney: 0,
        bidMinLimit: 100,
        bidMaxLimit: 2000
      },
      userBidVoList:[{
        id: 0,
        phone: "",
        bidMoney: 0,
        bidTime: ""
      }],
      countMoney: 0.00,
      login:false,
      allIncomeMoney:0.00,
      bidMoney: 100,
      bidMoneyErr: '',
    }
  },
  mounted() {
    if(window.localStorage.getItem("local:userinfo")){
      this.login = true;
    }
    let pid = this.$route.query.pid;
    if(pid){
      this.refresh(pid);
    }
  },
  methods:{
    refresh(pid){
      doGet('/product/detail',{pid:pid}).then(resp =>{
        if (resp.data.code === 1000) {
          this.product = resp.data.info.product;
          this.userBidVoList = resp.data.info.userBidVoList;
          this.countMoney = resp.data.info.countMoney;
        }
      })
    },
    goLink(url, parameters) { //调整页面
      this.$router.push({
        path: url,
        query: parameters
      })
    },
    checkMoney(){
      this.allIncomeMoney = 0.0;
      if (this.bidMoney === '') {
        this.bidMoneyErr = '请输入100整数倍';
      } else if (isNaN(this.bidMoney)) {
        this.bidMoneyErr = '请输入有效的整数';
      } else if (parseFloat(this.bidMoney) < 100) {
        this.bidMoneyErr = '投资资金最小是100元';
      } else if (parseFloat(this.bidMoney) % 100 != 0) {
        this.bidMoneyErr = '投资资金必须是100整数倍';
      }else {
        this.bidMoneyErr = '';
        let incomeMoney = 0;
        let dateRate = parseFloat(this.product.rate) / 360 / 100; //日利率
        if (this.product.productType === 0) { //新手宝
          incomeMoney = parseFloat(this.bidMoney) * parseInt(this.product.cycle) * dateRate;
        } else { //月为周期的
          incomeMoney = parseFloat(this.bidMoney) * parseInt(this.product.cycle) * 30 * dateRate;
        }
        this.allIncomeMoney =  (parseFloat(this.bidMoney) + parseFloat(incomeMoney)).toFixed(2);
      }
    },
    userInvest(){
      if(!this.login){
        layx.msg('登录后，才能投资',{dialogIcon:'warn',position:['ct',800]});
        return;
      }
      this.checkMoney();
      if( this.bidMoneyErr === ''){
        if( parseFloat(this.bidMoney) > this.countMoney ){
          layx.msg('账号余额不足',{dialogIcon:'warn',position:['ct',800]});
        } else if(parseFloat(this.bidMoney) > this.product.leftProductMoney){
          layx.msg('产品可投资金额不足',{dialogIcon:'warn',position:['ct',800]});
        } else {
          //调用服务器接口
          let pid  = this.$route.query.pid;
          doPost('/invest/product',{pid:pid,bidMoney:this.bidMoney}).then(resp =>{
            if(resp.data.code === 1000) {
              layx.msg(resp.data.message, {dialogIcon: 'success', position: ['ct', 800]});
              this.refresh(pid);
            }
          })
        }
      }
    }
  }
}
</script>

<style scoped>
.err {
  color: red;
  font-size: 18px;
}
</style>