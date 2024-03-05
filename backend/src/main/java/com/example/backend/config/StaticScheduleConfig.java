package com.example.backend.config;

import com.example.backend.entity.Subsidy;
import com.example.backend.mapper.SubsidyMapper;
import com.example.backend.service.SubsidyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
public class StaticScheduleConfig {
    @Autowired
    SubsidyMapper subsidyMapper;
    @Autowired
    SubsidyService subsidyService;

    @Scheduled(cron = "30 * * * * ?")
    public void checkSubsidyTime() {
        System.out.println("check subsidy");
        List<Subsidy> list = subsidyMapper.checkSubsidy();
        for(Subsidy subsidy:list) {
            subsidy.setState(2);    //过期
            subsidyService.refreshSubsidy(subsidy);
            subsidyMapper.updateById(subsidy);
        }
    }
}
