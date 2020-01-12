package com.zhuihoude.sell.controller;

import com.zhuihoude.sell.enums.ResultEnum;
import com.zhuihoude.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    //1.getCode
    @GetMapping("/authorized")
    public String authorized(@RequestParam(value = "returnUrl", defaultValue = "null") String returnUrl) {

        //1.配置
        //2.调用方法
        String url = "https://www.zhuihoude.com/sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, returnUrl);
        log.info("【微信访问授权】请求Url,url = {}", redirectUrl);

        return "redirect:" + redirectUrl;
    }

    //2.getOpenid
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code, @RequestParam("state") String returnUrl) {

        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.info("【微信网页授权】Error");
            throw new SellException(ResultEnum.WX_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("【微信访问授权】获取信息：code = {}, state = {}, openId = {}", code,returnUrl,openId);
        return "redirect:https://www.zhuihoude.com" + "?openid=" + openId;
    }
}
