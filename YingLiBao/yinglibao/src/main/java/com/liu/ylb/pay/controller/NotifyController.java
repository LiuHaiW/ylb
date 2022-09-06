package com.liu.ylb.pay.controller;

import com.liu.ylb.pay.service.NotifyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class NotifyController {
    @Resource
    private NotifyService notifyService;
    @GetMapping("/notify/kq")
    public String doNotify(HttpServletRequest request){
        notifyService.handleNotify(request);
        return "<result>1</result><redirecturl>http://localhost:8080</redirecturl>";
    }
}
