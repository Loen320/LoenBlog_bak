package com.wulong.ssm.blog.mapper;

import com.wulong.ssm.blog.entity.UserLoginInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author loen
 */
@Mapper
public interface UserLoginInfoMapper {


    /**
     *  登记登录信息
     * @param userLoginInfo
     * @return
     */
    int insert(UserLoginInfo userLoginInfo);

    /**
     * 根据ip获取登录信息
     * @param ip
     * @return
     */
    List<UserLoginInfo> getInfoByIp(String ip);

    /**
     * 查询用户登录信息
     * @return
     */
    List<UserLoginInfo> getInfoByIp();
}