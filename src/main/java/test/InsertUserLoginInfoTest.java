package test;

import com.wulong.ssm.blog.entity.User;
import com.wulong.ssm.blog.entity.UserLoginInfo;
import com.wulong.ssm.blog.service.UserLoginInfoService;
import com.wulong.ssm.blog.util.HttpSenderAndRecv;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @version 5.0.0
 * @Author: loen
 * @date: 2020/8/13 10:47
 * copyright @2019 Beijing Morong Information Techology CO.,Ltd.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/spring-mybatis.xml")
public class InsertUserLoginInfoTest {

    @Test
    public void InsertUserLoginInfoTest() {
//        user.setUserLastLoginIp("59.40.180.90");
//        //查询ip对应信息
//        net.sf.json.JSONObject json = new net.sf.json.JSONObject();
//        json.put("ip", user.getUserLastLoginIp());
//        String sendbuf = json.toString();
//        String rsp = HttpSenderAndRecv.sendAndRecv("https://m.so.com/position",sendbuf);
//        String rspstring = rsp.toString();
//        net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(rspstring);
//        String data = jsonObject.getString("data");
//        net.sf.json.JSONObject positionjson = net.sf.json.JSONObject.fromObject(data);
//        net.sf.json.JSONObject position = net.sf.json.JSONObject.fromObject(positionjson.getString("position"));
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        userLoginInfo.setLoginIp(json.get("ip").toString());
//        userLoginInfo.setAdcode(position.getString("adcode"));
//        userLoginInfo.setAddress(position.getString("country")+"-"+position.getString("province")+
//                "-"+position.getString("city"));
//        userLoginInfo.setCityCode(position.getString("city_code"));
//        userLoginInfo.setCountry(position.getString("country"));
//        userLoginInfo.setIsp(position.getString("isp"));
//        userLoginInfo.setLoginStamp(sdf.format(new Date()));
//        userLoginInfo.setLoginUser(user.getUserName());
//        userLoginInfo.setWeathercode(position.getString("weathercode"));
//        int result= userLoginInfoService.insertUserLoginInfo(userLoginInfo);
        System.out.println("--------------------->");
    }
}
