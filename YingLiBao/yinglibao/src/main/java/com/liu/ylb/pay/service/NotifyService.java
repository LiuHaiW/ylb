package com.liu.ylb.pay.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liu.ylb.common.consts.AppConsts;
import com.liu.ylb.common.consts.RedisKey;
import com.liu.ylb.common.util.Pkipair;
import com.liu.ylb.db.entity.Recharge;
import com.liu.ylb.db.mapper.FinanceAccountMapper;
import com.liu.ylb.db.mapper.RechargeMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Service
public class NotifyService {
    private RechargeMapper rechargeMapper;
    private FinanceAccountMapper accountMapper;
    private StringRedisTemplate stringRedisTemplate;

    public NotifyService(RechargeMapper rechargeMapper, FinanceAccountMapper accountMapper, StringRedisTemplate stringRedisTemplate) {
        this.rechargeMapper = rechargeMapper;
        this.accountMapper = accountMapper;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Transactional(rollbackFor = Exception.class)
    public synchronized void handleNotify(HttpServletRequest request) {
        int rows = 0;
        boolean check = checkSignMsg(request);
        String merchantAcctId = request.getParameter("merchantAcctId");
        String orderId = request.getParameter("orderId");
        if(check){
            if("1001214035601".equals(merchantAcctId)){
                Recharge recharge = rechargeMapper.selectOne(new QueryWrapper<Recharge>().eq("recharge_no", orderId).last("for update"));
                if(recharge != null){
                    if(AppConsts.RECHARGE_STATUS_DOING.equals(recharge.getRechargeStatus())){
                        String orderAmount = request.getParameter("orderAmount");
                        String fen = recharge.getRechargeMoney().multiply(new BigDecimal("100")).stripTrailingZeros().toPlainString();
                        if(fen.equals(orderAmount)){
                            String payResult = request.getParameter("payResult");
                            if("10".equals(payResult)){
                                System.out.println("????????????"+merchantAcctId+ "???????????????"+orderId+"??????????????????"+payResult);
                                rows = accountMapper.updateMoneyByRecharge(recharge.getUid(),recharge.getRechargeMoney());
                                if(rows < 1){
                                    throw new RuntimeException("?????????????????????????????????");
                                }
                                recharge.setRechargeStatus(AppConsts.RECHARGE_STATUS_SUCCESS);
                                rows = rechargeMapper.updateById(recharge);
                                if (rows <1){
                                    throw new RuntimeException("????????????????????????????????????????????????");
                                }
                            }else{
                                recharge.setRechargeStatus(AppConsts.RECHARGE_STATUS_FAIL);
                                rows = rechargeMapper.updateById(recharge);
                                if (rows <1){
                                    throw new RuntimeException("????????????????????????????????????????????????");
                                }
                            }
                        }else{
                            System.out.println("????????????"+merchantAcctId+ "???????????????"+orderId+"?????????????????????");
                        }
                    }else{
                        System.out.println("????????????"+merchantAcctId+ "???????????????"+orderId+"????????????????????????");
                    }
                }else{
                    System.out.println("????????????"+merchantAcctId+ "???????????????"+orderId+"????????????????????????");
                }
            }else{
                System.out.println("????????????"+merchantAcctId+ "???????????????"+orderId+"???????????????");
            }
        }else {
            System.out.println("????????????"+merchantAcctId+ "???????????????"+orderId+"????????????");
        }
        stringRedisTemplate.boundZSetOps(RedisKey.RECHARGE_ORDERID).remove(orderId);
    }
    private boolean checkSignMsg(HttpServletRequest request) {
        String merchantAcctId = request.getParameter("merchantAcctId");
        String version = request.getParameter("version");
        String language = request.getParameter("language");
        String signType = request.getParameter("signType");
        String payType = request.getParameter("payType");
        String bankId = request.getParameter("bankId");
        String orderId = request.getParameter("orderId");
        String orderTime = request.getParameter("orderTime");
        String orderAmount = request.getParameter("orderAmount");
        String bindCard = request.getParameter("bindCard");
        if (request.getParameter("bindCard") != null) {
            bindCard = request.getParameter("bindCard");
        }
        String bindMobile = "";
        if (request.getParameter("bindMobile") != null) {
            bindMobile = request.getParameter("bindMobile");
        }
        String bankDealId = request.getParameter("bankDealId");
        String dealTime = request.getParameter("dealTime");
        String payAmount = request.getParameter("payAmount");
        String fee = request.getParameter("fee");
        String ext1 = request.getParameter("ext1");
        String ext2 = request.getParameter("ext2");
        String payResult = request.getParameter("payResult");
        String aggregatePay = request.getParameter("aggregatePay");
        String errCode = request.getParameter("errCode");
        String signMsg = request.getParameter("signMsg");
        String dealId = request.getParameter("dealId");

        String merchantSignMsgVal = "";
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "merchantAcctId", merchantAcctId);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "version", version);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "language", language);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "signType", signType);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "payType", payType);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "bankId", bankId);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderId", orderId);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderTime", orderTime);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderAmount", orderAmount);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "bindCard", bindCard);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "bindMobile", bindMobile);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "dealId", dealId);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "bankDealId", bankDealId);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "dealTime", dealTime);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "payAmount", payAmount);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "fee", fee);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "ext1", ext1);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "ext2", ext2);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "payResult", payResult);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "aggregatePay", aggregatePay);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "errCode", errCode);
        System.out.println("merchantSignMsgVal=" + merchantSignMsgVal);

        Pkipair pki = new Pkipair();
        boolean flag = pki.enCodeByCer(merchantSignMsgVal, signMsg);
        System.out.println("????????????????????????"+flag);
        return flag;

    }

    /*???????????????????????????*/
    @Transactional(rollbackFor = Exception.class)
    public void handlerQueryResult(String merchantAcctId,String orderId,String payAmount,String payResult) {
        int rows  =0;
        //??????????????????
        Recharge recharge = rechargeMapper.selectOne(new QueryWrapper<Recharge>()
                .eq("recharge_no", orderId).last(" for update "));
        if (recharge != null) {
            if (AppConsts.RECHARGE_STATUS_DOING == recharge.getRechargeStatus()) {
                //????????????????????????????????? ??? ???????????????????????????????????????????????????
                String fen = recharge.getRechargeMoney().multiply(new BigDecimal("100"))
                        .stripTrailingZeros().toPlainString();
                if (fen.equals(payAmount)) {
                    //?????????????????????????????????
                    if ("10".equals(payResult)) { //????????????
                        System.out.println("????????????" + merchantAcctId + "???????????????" + orderId + "??????????????????" + payResult);
                        //??????????????????
                        rows = accountMapper.updateMoneyByRecharge(recharge.getUid(), recharge.getRechargeMoney());
                        if (rows < 1) {
                            throw new RuntimeException("?????????????????????????????????");
                        }
                        //????????????????????????????????????
                        recharge.setRechargeStatus(AppConsts.RECHARGE_STATUS_SUCCESS);
                        rows = rechargeMapper.updateById(recharge);
                        if (rows < 1) {
                            throw new RuntimeException("????????????????????????????????????????????????");
                        }
                    } else {
                        //????????????
                        //????????????????????????????????????
                        recharge.setRechargeStatus(AppConsts.RECHARGE_STATUS_FAIL);
                        rows = rechargeMapper.updateById(recharge);
                        if (rows < 1) {
                            throw new RuntimeException("????????????????????????????????????????????????");
                        }
                    }
                } else {
                    System.out.println("????????????" + merchantAcctId + "???????????????" + orderId + "?????????????????????");
                }
            } else {
                System.out.println("????????????" + merchantAcctId + "???????????????" + orderId + "????????????????????????");
            }
        } else {
            System.out.println("????????????" + merchantAcctId + "???????????????" + orderId + "????????????????????????");
        }
    }

    private String appendParam(String returns, String paramId, String paramValue) {
        if (!returns.equals("")) {
            if (paramValue != null && !paramValue.equals("")) {
                returns += "&" + paramId + "=" + paramValue;
            }
        } else {

            if (paramValue != null && !paramValue.equals("")) {
                returns = paramId + "=" + paramValue;
            }
        }
        return returns;
    }
}
