package com.liu.ylb.task;

import com.liu.ylb.front.service.IncomeRecordService;
import com.liu.ylb.pay.service.KqService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("taskManager")
public class TaskManager {
    @Resource
    private IncomeRecordService incomeRecordService;
    @Resource
    private KqService kqService;
    @Scheduled(cron = "0 0 1 * * ?")
    public void taskInvestPlan(){
        incomeRecordService.generateIncomePlan();
    }
    @Scheduled(cron = "0 0 2 * * ?")
    public void taskInvestBack(){
        incomeRecordService.generateIncomeBack();
    }
    @Scheduled(cron = "0 0/20 * * * ?")
    public void taskRechargeQuery(){
        kqService.executeQueryPayResult();
    }
}
