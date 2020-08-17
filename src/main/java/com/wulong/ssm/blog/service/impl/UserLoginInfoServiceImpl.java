package com.wulong.ssm.blog.service.impl;

import com.wulong.ssm.blog.entity.User;
import com.wulong.ssm.blog.entity.UserLoginInfo;
import com.wulong.ssm.blog.mapper.ArticleMapper;
import com.wulong.ssm.blog.mapper.UserLoginInfoMapper;
import com.wulong.ssm.blog.mapper.UserMapper;
import com.wulong.ssm.blog.service.UserLoginInfoService;
import com.wulong.ssm.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户登录实现类
 *
 * @author loen
 * @date 2017/8/24
 */
@Service
public class UserLoginInfoServiceImpl implements UserLoginInfoService {

    @Autowired(required = false)
    private UserLoginInfoMapper userLoginInfoMapper;

    @Override
    public List<UserLoginInfo> listUserLoginInfo() {
        return userLoginInfoMapper.getInfoByIp();
    }

    @Override
    public List<UserLoginInfo> listUserLoginInfo(String ip) {
        return userLoginInfoMapper.getInfoByIp();
    }

    @Override
    public int insertUserLoginInfo(UserLoginInfo userLoginInfo) {
        return userLoginInfoMapper.insert(userLoginInfo);
    }

}
