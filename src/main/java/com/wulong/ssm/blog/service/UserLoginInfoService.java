package com.wulong.ssm.blog.service;

import com.wulong.ssm.blog.entity.User;
import com.wulong.ssm.blog.entity.UserLoginInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 用户登录实现类接口
 * @author loen
 * @date 2017/8/24
 */

public interface UserLoginInfoService {

    /**
     * 获取用户登录信息
     * @return
     */
    List<UserLoginInfo> listUserLoginInfo();

    /**
     * 根据登录ip查询登录信息
     * @param ip
     * @return
     */
    List<UserLoginInfo> listUserLoginInfo(String ip);

    /**
     * 登记用户登录信息
     * @param userLoginInfo
     * @return
     */
    int insertUserLoginInfo(UserLoginInfo userLoginInfo);

}
