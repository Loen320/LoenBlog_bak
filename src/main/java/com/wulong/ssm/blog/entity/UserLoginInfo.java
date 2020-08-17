package com.wulong.ssm.blog.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author loen
 */
@Data
public class UserLoginInfo implements Serializable{
    private static final long serialVersionUID = -4415517704211723485L;
    private Integer loginId;

    private String loginUser;

    private String loginIp;

    private String loginStamp;

    private String country;

    private String address;

    private String isp;

    private String adcode;

    private String cityCode;

    private String weathercode;

}