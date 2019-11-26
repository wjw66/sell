package com.wjw.controller;

import com.wjw.enums.ResultEnum;
import com.wjw.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

/**
 * @author : wjwjava@163.com
 * @date : 23:31 2019/11/8
 */


@RequestMapping("/wechat")
@Controller
@Slf4j
public class WechatController {
    @Autowired
    private WxMpService wxMpService;
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl")String returnUrl){
        //1.配置
        //2.调用方法
       // wxMpService.oauth2buildAuthorizationUrl()
        String url = "http://spring.nat300.top/sell/wechat/userInfo";
        //返回的是一个code字符串
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE, URLEncoder.encode(returnUrl));
        log.info("【微信网页授权】获取code,redirectUrl={}",redirectUrl);
        //重定向到userInfo方法，获取code
        return "redirect:" + redirectUrl;
    }
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code")String code,@RequestParam("state")String returnUrl) {

        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        }catch (WxErrorException e){
            log.error("【微信网页授权错误】{}",e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        //若无异常则拿到openId
        String openId = wxMpOAuth2AccessToken.getOpenId();
        //携带openId重定向到页面
        return "redirect:" + returnUrl + "?openid=" +openId;
    }
}