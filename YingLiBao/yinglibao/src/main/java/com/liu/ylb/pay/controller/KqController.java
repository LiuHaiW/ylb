package com.liu.ylb.pay.controller;

import com.liu.ylb.db.entity.User;
import com.liu.ylb.front.service.UserService;
import com.liu.ylb.pay.service.KqService;
import com.liu.ylb.pay.vo.KqVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import javax.annotation.Resource;
import java.math.BigDecimal;

@Controller
public class KqController {
    @Resource
    private KqService kqService;
    @Resource
    private UserService userService;
    @GetMapping("/recharge/kq/entry")
    public String rechargeUp(@RequestParam Integer uid, @RequestParam BigDecimal money,Model model){
        String view = "error";
        if(uid > 0 && money.doubleValue() > 0){
            try {
                User user = userService.selectById(uid);
                if(user != null){
                    KqVo kqVo = kqService.createFormData(uid,money,user.getPhone());
                    kqService.createNewRechareRecord(uid,kqVo.getOrderId(),money);
                    model.addAttribute("kq",kqVo);
                    view = "kq";
                }
            } catch (Exception e) {
                view = "error";
            }
        }
        return view;
    }
}
