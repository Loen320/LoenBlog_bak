package com.wulong.ssm.blog.util;

import com.wulong.ssm.blog.service.UserLoginInfoService;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @version 5.0.0
 * @Author: loen
 * @date: 2020/8/12 13:51
 */
public class HttpSenderAndRecv {
    @Autowired
    private static UserLoginInfoService userLoginInfoService;

    private static CloseableHttpClient httpClient;
    public static String sendAndRecv(String url, String jsonstr) throws IOException {
        httpClient = HttpClients.custom().build();
        HttpPost httpPost = new HttpPost(url);
//        System.out.println(("url is :"+url);
        StringEntity postEntity = new StringEntity(jsonstr, "UTF-8");
        postEntity.setContentType("application/json;charset=utf-8");
        httpPost.setEntity(postEntity);
        HttpResponse response;
        String result = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            httpPost.abort();
            httpClient.close();
        }
        return result;
    }
    public static void main(String[] args) throws IOException {
        String password="123456";
        System.out.println(MyUtils.strToMd5(password));
//        net.sf.json.JSONObject json = new net.sf.json.JSONObject();
//        json.put("ip", "115.191.200.34");
//        String sendbuf = json.toString();
//        String rsp = HttpSenderAndRecv.sendAndRecv("http://ip-api.com/json/",sendbuf);//360ip地址查询（403）
//        System.out.println("返回数据："+rsp);
        String ip="115.191.200.34";
//        JSONObject result=loadJSON("http://ip-api.com/json/"+ip+"?lang=zh-CN");
//        System.out.println(result);
        JSONObject jsonObject=JSONObject.fromObject("{\"status\":\"success\",\"country\":\"中国\",\"countryCode\":\"CN\",\"region\":\"BJ\",\"regionName\":\"北京市\",\"city\":\"北京\",\"zip\":\"\",\"lat\":39.9042,\"lon\":116.407,\"timezone\":\"Asia/Shanghai\",\"isp\":\"GWBN-WUHAN's IP\",\"org\":\"\",\"as\":\"AS7497 Computer Network Information Center\",\"query\":\"115.191.200.34\"}");
        System.out.println(jsonObject.getString("status"));


        System.out.println(jsonObject.getString("query"));
        System.out.println(jsonObject.getString("query"));
        System.out.println(jsonObject.getString("country")+jsonObject.getString("city")+
                jsonObject.getString("regionName"));
        String pos=jsonObject.getString("lat")+"-"+jsonObject.getString("lon");
        System.out.println(pos.substring(0,pos.length()));
        System.out.println(jsonObject.getString("country"));
        String ll=jsonObject.getString("isp");
        System.out.println(ll.substring(0,ll.length()));
        String lll=jsonObject.getString("as");
        System.out.println(lll.substring(0,lll.length()));

        String status="1111111111111111111";
        System.out.println(status.substring(0,status.length()<20?status.length():20));
    }

    public static JSONObject loadJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream(), "utf-8"));//防止乱码
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return JSONObject.fromObject(json.toString());
    }
}
