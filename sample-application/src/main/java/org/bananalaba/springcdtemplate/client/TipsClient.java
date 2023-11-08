package org.bananalaba.springcdtemplate.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "tipsServiceClient", url = "${service.integration.tipsService.url}")
public interface TipsClient {

    @RequestMapping(method = RequestMethod.GET, path = "/tip-of-the-day")
    String getTipOfTheDay();

}
