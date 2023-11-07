package org.bananalaba.springcdtemplate.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("tipsService")
public interface TipsClient {

    @RequestMapping("/tip-of-the-day")
    String getTipOfTheDay();

}
