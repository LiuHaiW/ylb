<template>
  <AppHeader></AppHeader>
  <div class="content clearfix">
    <!--排行榜-->
    <ul class="rank-list">
      <li v-for="(investTopVo,index) in investTopVoList" :key="index">
        <img src="@/assets/image/list-rank1.png"  v-if="index === 0">
        <img src="@/assets/image/list-rank2.png"  v-else-if="index === 1">
        <img src="@/assets/image/list-rank3.png"  v-else>
        <p class="rank-list-phone">{{ investTopVo.phone }}</p>
        <span>{{ investTopVo.bidSumMoney }}元</span>
      </li>
    </ul>
    <!--产品列表-->
    <ul class="preferred-select clearfix">
      <li v-for="product in productList" :key="product.id">
        <h3 class="preferred-select-title">
          <span>{{ product.productName }}</span>
          <img src="@/assets/image/1-bg1.jpg">
        </h3>
        <div class="preferred-select-number">
          <p><b>{{product.rate}}</b>%</p>
          <span>历史年化收益率</span>
        </div>
        <div class="preferred-select-date">
          <div>
            <span>投资周期</span>
            <p><b>{{product.cycle}}</b>个月</p>
          </div>
          <div>
            <span>利余可投资金额</span>
            <p><b>{{ product.leftProductMoney }}</b>元</p>
          </div>
        </div>
        <p class="preferred-select-txt">
          优选计划项目，投资回报周期{{product.cycle}}个月，起点低，适合短期资金周转、对流动性要求高的投资人。
        </p>
        <a href="javascript:void(0);" class="preferred-select-btn" @click="goLink('/product/detail',{pid:product.id})">立即投资</a>
      </li>
    </ul>

    <!--分页-->
    <div class="page_box">
      <ul class="pagination">
        <li class="disabled"><a href="javascript:void(0);" @click="doFirstPage">首页</a></li>
        <li><a href="javascript:void(0);" @click="doDec">上一页</a></li>
        <li class="active"><span>{{ pageInfo.pageNo }}</span></li>
        <li><a href="javascript:void(0);" @click="doAdd">下一页</a></li>
        <li><a href="javascript:void(0);" @click="doLastPage">尾页</a></li>
        <li class="totalPages"><span>共{{pageInfo.totalPage}}页</span></li>
      </ul>
    </div>
  </div>
  <AppFooter></AppFooter>
</template>

<script>
import AppHeader from "@/components/AppHeader";
import AppFooter from "@/components/AppFooter";
import {doGet, doPost} from "@/assets/api/api";
import layx from "vue-layx";

let productType = -1;
export default {
  name: "ProdListView",
  components: {
    AppHeader,
    AppFooter
  },
  beforeRouteUpdate(to){
    productType = to.query.ptype;
    this.refreshData(1);
  },
  data(){
    return{
      productList:[{
        id: 0,
        productName: "",
        rate: 0,
        cycle: 0,
        productType: 0,
        productNo: "",
        productMoney: 0,
        leftProductMoney: 0,
        bidMinLimit: 0,
        bidMaxLimit: 0
      }],
      investTopVoList:[{
        phone: "",
        bidSumMoney: 0.00
      }],
      pageInfo: {
        pageNo: 1,
        pageSize: 9,
        totalRecord: 0,
        totalPage: 1
      }
    }
  },
  mounted() {
    productType = this.$route.query.ptype;
    this.refreshData(1);
    doPost('/invest/top').then(resp =>{
      this.investTopVoList = resp.data.info;
    })
  },
  methods: {
    refreshData(pageNum){
        doGet('/product/more',{type: productType, pageNum: pageNum, pageSize: 9}).then(resq =>{
          if(resq.data.code === 1000){
            this.productList = resq.data.info.productList;
            this.pageInfo = resq.data.info.pageInfo;
          }
        })
    },
    doAdd(){
      if (this.pageInfo.pageNo >= this.pageInfo.totalPage) {
        layx.msg('已经是最后一页了.',{dialogIcon:'warn',position:['ct',800]});
      } else {
        this.refreshData(this.pageInfo.pageNo+1);
      }
    },
    doDec(){
      if (this.pageInfo.pageNo <= 1) {
        layx.msg('已经是第一页了.',{dialogIcon:'warn',position:['ct',800]});
      } else {
        this.refreshData(this.pageInfo.pageNo - 1);
      }
    },
    doLastPage(){
      if (this.pageInfo.pageNo == this.pageInfo.totalPage) {
        layx.msg('已经是最后一页了.',{dialogIcon:'warn',position:['ct',800]});
      } else {
        this.refreshData(this.pageInfo.totalPage);
      }
    },
    doFirstPage(){
      if (this.pageInfo.pageNo <= 1) {
        layx.msg('已经是第一页了.',{dialogIcon:'warn',position:['ct',800]});
      } else {
        this.refreshData(1);
      }
    },
    goLink(url,parameters){
        this.$router.push({
          path:url,
          query:parameters
        })
    }
  }
}
</script>

<style scoped>

</style>