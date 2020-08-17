package com.wulong.ssm.blog.controller.admin;

import com.wulong.ssm.blog.entity.Article;
import com.wulong.ssm.blog.entity.Comment;
import com.wulong.ssm.blog.entity.User;
import com.wulong.ssm.blog.entity.UserLoginInfo;
import com.wulong.ssm.blog.mapper.UserLoginInfoMapper;
import com.wulong.ssm.blog.service.ArticleService;
import com.wulong.ssm.blog.service.CommentService;
import com.wulong.ssm.blog.service.UserLoginInfoService;
import com.wulong.ssm.blog.service.UserService;
import com.wulong.ssm.blog.util.HttpSenderAndRecv;
import com.wulong.ssm.blog.util.MyUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wulong.ssm.blog.util.MyUtils.getIpAddr;

/**
 * @author loen
 */
@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserLoginInfoService userLoginInfoService;

    /**
     * 后台首页
     *
     * @return
     */
    @RequestMapping("/admin")
    public String index(Model model)  {
        //文章列表
        List<Article> articleList = articleService.listRecentArticle(5);
        model.addAttribute("articleList",articleList);
        //评论列表
        List<Comment> commentList = commentService.listRecentComment(5);
        model.addAttribute("commentList",commentList);
        return "Admin/index";
    }

    /**
     * 登录页面显示
     *
     * @return
     */
    @RequestMapping("/login")
    public String loginPage() {
        return "Admin/login";
    }

    /**
     * 登录验证
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/loginVerify",method = RequestMethod.POST)
    @ResponseBody
    public String loginVerify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();

        String imageText  = request.getParameter("image");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberme = request.getParameter("rememberme");
        User user = userService.getUserByNameOrEmail(username);
        //图片的验证码
        String text = (String) request.getSession().getAttribute("text");
        if (!text.equalsIgnoreCase(imageText)||imageText.isEmpty()) {
            map.put("code", "0");
            map.put("msg", "验证码输入错误!");
        }else {
            if (user == null) {
                map.put("code", 0);
                //用户名不存在
                map.put("msg", "用户名或密码错误！");
            } else if (!user.getUserPass().equals(MyUtils.strToMd5(password))) {
                map.put("code", 0);
                map.put("msg", "用户名或密码错误！");
            } else {
                //登录成功
                map.put("code", 1);
                map.put("msg", "");
                //添加session
                request.getSession().setAttribute("user", user);
                //添加cookie
                if (rememberme != null) {
                    //创建两个Cookie对象
                    Cookie nameCookie = new Cookie("username", username);
                    //设置Cookie的有效期为3天
                    nameCookie.setMaxAge(60 * 60 * 24 * 3);
                    Cookie pwdCookie = new Cookie("password", password);
                    pwdCookie.setMaxAge(60 * 60 * 24 * 3);
                    response.addCookie(nameCookie);
                    response.addCookie(pwdCookie);
                }

                String ipaddress=getIpAddr(request);//获取IP地址
                //本地跑不登记
                if (!"0:0:0:0:0:0:0:1".equals(ipaddress)){
                    //更新user登录信息
                    user.setUserLastLoginTime(new Date());
                    user.setUserLastLoginIp(ipaddress);
                    userService.updateUser(user);

                    UserLoginInfo userLoginInfo=new UserLoginInfo();
                    List<UserLoginInfo> userLoginInfoList = userLoginInfoService.listUserLoginInfo(ipaddress);//按ip查询用户登录数据
                    net.sf.json.JSONObject result=null;
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    userLoginInfo.setLoginStamp(sdf.format(new Date()));
                    userLoginInfo.setLoginUser(user.getUserName());
                    if (userLoginInfoList.isEmpty()){
                        result=HttpSenderAndRecv.loadJSON("http://ip-api.com/json/"+ipaddress+"?lang=zh-CN");
                        userLoginInfo.setLoginIp(result.getString("query"));
                        //拼接状态码
                        String status = result.getString("status");
                        userLoginInfo.setAdcode(status.substring(0,status.length()<20?status.length():20));
                        //截取拼接地址信息
                        String address=result.getString("country")+"-"+result.getString("city")+"-"+
                                result.getString("regionName");
                        userLoginInfo.setAddress(address.substring(0,address.length()<20?address.length():20));
                        //截取拼接城市代码（坐标？）
                        String city_code=result.getString("lat")+"-"+result.getString("lon");
                        userLoginInfo.setCityCode(city_code.substring(0,city_code.length()<20?address.length():20));
                        userLoginInfo.setCountry(result.getString("country"));
                        //截取拼接isp
                        String isp=result.getString("isp");
                        userLoginInfo.setIsp(isp.substring(0,isp.length()<20?isp.length():20));
                        //截取拼接as:运营商
                        String as=result.getString("as");
                        userLoginInfo.setWeathercode(as.substring(0,as.length()<20?as.length():20));
                    }else{
                        userLoginInfo.setLoginIp(userLoginInfoList.get(userLoginInfoList.size()%5).getLoginIp());
                        userLoginInfo.setAdcode(userLoginInfoList.get(userLoginInfoList.size()%5).getAdcode());
                        userLoginInfo.setAddress(userLoginInfoList.get(userLoginInfoList.size()%5).getAddress());
                        userLoginInfo.setCityCode(userLoginInfoList.get(userLoginInfoList.size()%5).getCityCode());
                        userLoginInfo.setCountry(userLoginInfoList.get(userLoginInfoList.size()%5).getCountry());
                        userLoginInfo.setIsp(userLoginInfoList.get(userLoginInfoList.size()%5).getIsp());
                        userLoginInfo.setWeathercode(userLoginInfoList.get(userLoginInfoList.size()%5).getWeathercode());
                    }
                    //登记用户登录信息
                    userLoginInfoService.insertUserLoginInfo(userLoginInfo);

//                    //登记用户登录信息(使用360ip地址查询返回403)
//                    net.sf.json.JSONObject json = new net.sf.json.JSONObject();
//                    json.put("ip", user.getUserLastLoginIp());
//                    String sendbuf = json.toString();
//                    String rsp = HttpSenderAndRecv.sendAndRecv("https://m.so.com/position",sendbuf);//360ip地址查询（403）
//                    String rspstring = rsp.toString();
//                    net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(rspstring);
//                    String data = jsonObject.getString("data");
//                    net.sf.json.JSONObject positionjson = net.sf.json.JSONObject.fromObject(data);
//                    net.sf.json.JSONObject position = net.sf.json.JSONObject.fromObject(positionjson.getString("position"));
//                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    UserLoginInfo userLoginInfo=new UserLoginInfo();
//                    userLoginInfo.setLoginIp(json.get("ip").toString());
//                    userLoginInfo.setAdcode(position.getString("adcode"));
//                    userLoginInfo.setAddress(position.getString("country")+"-"+position.getString("province")+
//                            "-"+position.getString("city"));
//                    userLoginInfo.setCityCode(position.getString("city_code"));
//                    userLoginInfo.setCountry(position.getString("country"));
//                    userLoginInfo.setIsp(position.getString("isp"));
//                    userLoginInfo.setLoginStamp(sdf.format(new Date()));
//                    userLoginInfo.setLoginUser(user.getUserName());
//                    userLoginInfo.setWeathercode(position.getString("weathercode"));
//                    int result=userLoginInfoService.insertUserLoginInfo(userLoginInfo);
                }
            }
        }
        String result = new JSONObject(map).toString();
        return result;
    }

    /**
     * 退出登录
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/admin/logout")
    public String logout(HttpSession session)  {
        session.removeAttribute("user");
        session.invalidate();
        return "redirect:/login";
    }


}
