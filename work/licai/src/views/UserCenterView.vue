<template>
  <AppHeader></AppHeader>
  <div class="content clearfix">
    <!--个人信息-->
    <div class="user-head">
      <div class="user-head-left fl">
        <div class="user-head-img">
          <img src="@/assets/image/user-head.png" alt="">
        </div>
        <p>上传头像</p>
      </div>
      <div class="user-head-right fl">
        <ul class="user-head-name fl">
          <li><b>{{userinfo.name}}</b></li>
          <li>{{userinfo.phone}}</li>
          <li>最近登录：{{userinfo.loginTime}}</li>
        </ul>
        <div class="user-head-money fr">
          <p>可用余额：<span>￥{{ userinfo.avaiableMoney }}元</span></p>
          <a href="javascript:void(0);" @click="goLink('/user/pay')" style="color:red" class="user-head-a2">充值</a>
          <a href="javascript:void(0);" @click="goLink('/')" style="color:red"  class="user-head-a2">投资</a>
        </div>
      </div>

    </div>
    <!--记录-->
    <div class="user-record-box clearfix">
      <div class="user-record user-record-1">
        <h3 class="user-record-title">最近投资</h3>
        <table align="center" width="388" border="0" cellspacing="0" cellpadding="0">
          <thead class="datail_thead">
          <tr>
            <th>序号</th>
            <th>投资产品</th>
            <th>投资金额</th>
            <th>投资时间</th>
          </tr>
          </thead>
          <tbody v-for="(userBidList,index) in userBidListInfo" :key="userBidList.id">
          <tr>
            <td>{{index + 1}}</td>
            <td>{{userBidList.productName}}</td>
            <td>{{ userBidList.bidMoney }}</td>
            <td>{{ userBidList.bidTime }} </td>
          </tr>
          </tbody>
        </table>
        <!--无记录-->
        <p class="user-record-no" v-if="userBidListInfo.length ===0">还没有投资记录，请投资：<a href="user_center.html" target="_blank">投资</a></p>
      </div>
      <div class="user-record user-record-2">
        <h3 class="user-record-title">最近充值</h3>
        <table align="center" width="388" border="0" cellspacing="0" cellpadding="0">
          <thead class="datail_thead">
          <tr>
            <th>序号</th>
            <th>充值结果</th>
            <th>充值金额</th>
            <th>充值时间</th>
          </tr>
          </thead>
          <tbody v-for="(rechargeList,index) in rechargeListInfo" :key="rechargeList.id">
          <tr>
            <td>{{index + 1}}</td>
            <td>{{ rechargeList.rechargeStatus }}</td>
            <td>{{ rechargeList.rechargeMoney }}</td>
            <td>{{ rechargeList.rechargeTime }} </td>
          </tr>
          </tbody>
        </table>
        <!--无记录-->
        <p class="user-record-no" v-if="rechargeListInfo.length === 0">还没有充值记录，请充值：<a href="user_pay.html" target="_blank">充值</a></p>
      </div>
      <div class="user-record user-record-3">
        <h3 class="user-record-title ">最近收益</h3>
        <table align="center" width="388" border="0" cellspacing="0" cellpadding="0">
          <thead class="datail_thead">
          <tr>
            <th>序号</th>
            <th>项目名称</th>
            <th>到期日期</th>
            <th>收益金额</th>
          </tr>
          </thead>
          <tbody v-for="(userIncomeRecord,index) in userIncomeRecordInfo" :key="userIncomeRecord.id">
          <tr>
            <td>{{index + 1}}</td>
            <td>{{ userIncomeRecord.productName }}</td>
            <td>{{ userIncomeRecord.incomeDate }} </td>
            <td>{{userIncomeRecord.incomeMoney}}  </td>
          </tr>
          </tbody>
        </table>
        <!--无记录-->
        <p class="user-record-no" v-if="userIncomeRecordInfo.length === 0">还没有收益记录</p>
      </div>
    </div>
  </div>
  <AppFooter></AppFooter>
</template>

<script>
import AppHeader from "@/components/AppHeader";
import AppFooter from "@/components/AppFooter";
import {doGet} from "@/assets/api/api";

export default {
  name: "UserCenterView",
  components:{
    AppHeader,
    AppFooter
  },
  data(){
    return {
      userinfo: {
        id: 0,
        name:"",
        phone:"",
        headerImg: "",
        loginTime: "",
        avaiableMoney:0.00
      },
      userBidListInfo:[{
        id:0,
        productName:"",
        bidMoney:0.00,
        bidTime:"",
      }],
      rechargeListInfo:[{
        id:0,
        rechargeStatus:"",
        rechargeMoney:0.00,
        rechargeTime:"",
      }],
      userIncomeRecordInfo:[{
        id:0,
        productName:"",
        incomeMoney:0.00,
        incomeDate:"",
      }]
    }
  },
  mounted() {
    //查询用户信息
    doGet('/user/info').then(resp=>{
      if(resp.data.code === 1000){
        this.userinfo = resp.data.info;
      }
    })
    doGet('/invests/more',{pageNo:1,pageSize:6}).then(resp=>{
      if(resp.data.code === 1000){
        this.userBidListInfo = resp.data.info;
      }
    })
    doGet('/recharge/more',{pageNo:1,pageSize:6}).then(resp=>{
      if(resp.data.code === 1000){
        this.rechargeListInfo = resp.data.info;
      }
    })
    doGet('/incomeRecord/more',{pageNo:1,pageSize:6}).then(resp=>{
      if(resp.data.code === 1000){
        this.userIncomeRecordInfo = resp.data.info;
      }
    })
  },
  methods:{
    goLink(url, parameters) { //调整页面
      this.$router.push({
        path: url,
        query: parameters
      })
    }
  }
}
</script>

<style scoped>

</style>