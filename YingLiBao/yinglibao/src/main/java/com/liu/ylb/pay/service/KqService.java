package com.liu.ylb.pay.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liu.ylb.common.consts.AppConsts;
import com.liu.ylb.common.consts.RedisKey;
import com.liu.ylb.common.util.HttpUtil;
import com.liu.ylb.common.util.Pkipair;
import com.liu.ylb.db.entity.Recharge;
import com.liu.ylb.front.service.RechargeService;
import com.liu.ylb.pay.vo.KqVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class KqService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RechargeService rechargeService;
    @Resource
    private NotifyService notifyService;

    public KqVo createFormData(Integer uid, BigDecimal money, String phone) {
        KqVo kqVo = new KqVo();
        kqVo.setPayerContact(phone);
        kqVo.setPayerId(uid.toString());
        kqVo.setOrderId(createOrderId());
        String fen = money.multiply(new BigDecimal("100")).stripTrailingZeros().toPlainString();
        kqVo.setOrderAmount(fen);
        String dateTime = DateFormatUtils.format(new Date(),"yyyyMMddHHmmss");
        kqVo.setOrderTime(dateTime);
        kqVo.setOrderTimestamp(dateTime);
        String signMsgVal = kqVo.getSignMsgValue();
        Pkipair pkipair = new Pkipair();
        String signMsg = pkipair.signMsg(signMsgVal);
        kqVo.setSignMsg(signMsg);
        return kqVo;
    }
    private String createOrderId(){
        String dataTime = DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");
        Long increment = stringRedisTemplate.boundValueOps(RedisKey.RECHARGE_ID_SEQ).increment();
        return "KQ" + dataTime + increment;
    }

    public void createNewRechareRecord(Integer uid, String orderId, BigDecimal money) {
        Recharge recharge = new Recharge();
        recharge.setRechargeNo(orderId);
        recharge.setRechargeDesc("????????????");
        recharge.setRechargeTime(new Date());
        recharge.setRechargeStatus(AppConsts.RECHARGE_STATUS_DOING);
        recharge.setChannel("KQ");
        recharge.setUid(uid);
        recharge.setRechargeMoney(money);
        rechargeService.addRechargeRecord(recharge);
        stringRedisTemplate.boundZSetOps(RedisKey.RECHARGE_ORDERID).add(orderId,recharge.getRechargeTime().getTime());
    }
    public void executeQueryPayResult(){
        Set<ZSetOperations.TypedTuple<String>> typedTuples = stringRedisTemplate.boundZSetOps(RedisKey.RECHARGE_ORDERID).rangeWithScores(0, -1);
        typedTuples.forEach(stringTypedTuple -> {
            String s = invokeKqGatewayOrderQuery(stringTypedTuple.getValue());
            if(StringUtils.isNotBlank(s)){
                JSONObject jsonObject = JSONObject.parseObject(s);
                if(jsonObject != null){
                    JSONArray orderDetail = jsonObject.getJSONArray("orderDetail");
                    if(orderDetail != null){
                        JSONObject jsonObject1 = orderDetail.getJSONObject(0);
                        notifyService.handlerQueryResult(jsonObject.getString("merchantAcctId"),jsonObject1.getString("orderId"),jsonObject1.getString("payAmount"),jsonObject1.getString("payResult"));
                    }else{
                        rechargeService.modifyRechargeStatus(stringTypedTuple.getValue(),AppConsts.RECHARGE_STATUS_FAIL);
                    }
                }
            }else{
                rechargeService.modifyRechargeStatus(stringTypedTuple.getValue(),AppConsts.RECHARGE_STATUS_FAIL);
            }
            stringRedisTemplate.boundZSetOps(RedisKey.RECHARGE_ORDERID).remove(stringTypedTuple.getValue());
        });
    }
    /*???????????????????????????*/
    private String invokeKqGatewayOrderQuery(String orderId){
        Map<String, Object> request = new HashMap<String, Object>();
        //????????????1??????UTF-8;
        String inputCharset = "1";
        //????????????v2.0 ??????
        String version = "v2.0";
        //1??????Md5???2 ??????PKI????????????  ??????
        String signType = "2";
        //??????????????? membcode+01  ??????
        String merchantAcctId = "1001214035601";
        //????????????0 ?????????????????????????????????1 ???????????????????????????????????????
        String queryType = "0";
        //????????????1	?????????????????? ??????
        String queryMode = "1";
        String startTime = "";
        String endTime = "";
        String requestPage = "";
        String key = "27YKWKBKHT2IZSQ4";

        request.put("inputCharset", inputCharset);
        request.put("version", version);
        request.put("signType", signType);
        request.put("merchantAcctId", merchantAcctId);
        request.put("queryType", queryType);
        request.put("queryMode", queryMode);
        request.put("startTime", startTime);
        request.put("endTime", endTime);
        request.put("requestPage", requestPage);
        request.put("orderId", orderId);

        String message="";
        message = appendParam(message,"inputCharset",inputCharset);
        message = appendParam(message,"version",version);
        message = appendParam(message,"signType",signType);
        message = appendParam(message,"merchantAcctId",merchantAcctId);
        message = appendParam(message,"queryType",queryType);
        message = appendParam(message,"queryMode",queryMode);
        message = appendParam(message,"startTime",startTime);
        message = appendParam(message,"endTime",endTime);
        message = appendParam(message,"requestPage",requestPage);
        message = appendParam(message,"orderId",orderId);
        message = appendParam(message,"key",key);

        Pkipair pki = new Pkipair();
        String sign = pki.signMsg(message);
        //???signMsg?????????map??????????????????????????????
        request.put("signMsg", sign);

        //sandbox????????????
        String reqUrl = "https://sandbox.99bill.com/gatewayapi/gatewayOrderQuery.do";
        String response = "";
        try {
            response = HttpUtil.doPostJsonRequestByHttps(JSON.toJSONString(request), reqUrl, 3000, 8000);
            System.out.println("??????json???==="+response);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return response;
    }
    private String appendParam(String returns, String paramId, String paramValue) {
        if (returns != "") {
            if (paramValue != "" && paramValue != null) {
                returns += "&" + paramId + "=" + paramValue;
            }
        } else {
            if (paramValue != ""  && paramValue != null) {
                returns = paramId + "=" + paramValue;
            }
        }
        return returns;
    }
}
