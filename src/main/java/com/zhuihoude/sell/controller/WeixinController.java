package com.zhuihoude.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {

    @GetMapping("/auth")  // 访问地址 https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa9cb1707d51bbcff&redirect_uri=https://www.zhuihoude.com/sell/weixin/auth&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect
    public void auth(@RequestParam(value = "code",defaultValue = "null") String code,@RequestParam(value = "state",defaultValue = "null") String state) {
        log.info("进入 Auth 方法～");
        log.info("code = {}, state = {}",code,state);

        String get_access_token_url ="https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxa9cb1707d51bbcff&secret=ca8ec1b2d0981fb2cb871a1a7b513ac9&code="+ code +"&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(get_access_token_url,String.class);
        log.info("应该拿到openid？response={}",response);

        String url2 = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    }
}
