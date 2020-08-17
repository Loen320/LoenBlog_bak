package com.wulong.ssm.blog.controller.verifycode;

import com.wulong.ssm.blog.util.VerifyCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @version 5.0.0
 * @Author: loen
 * @date: 2020/8/10 20:31
 * copyright @2019 Beijing Morong Information Techology CO.,Ltd.
 */

@Controller
public class VerifyCodeController {

    @RequestMapping("getVerifiCode")
    @ResponseBody
    public void getVerifiCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*
             1.生成验证码
             2.把验证码上的文本存在session中
             3.把验证码图片发送给客户端
             */
        VerifyCode ivc = new VerifyCode();     //用我们的验证码类，生成验证码类对象
        BufferedImage image = ivc.getImage();  //获取验证码
        request.getSession().setAttribute("text", ivc.getText()); //将验证码的文本存在session中
        ivc.output(image, response.getOutputStream());//将验证码图片响应给客户端
    }

    @RequestMapping(value = "/VerifyCode/VerifyCode")
    @ResponseBody
    public String test(String name){
        return name.hashCode()+"";
    }
}
