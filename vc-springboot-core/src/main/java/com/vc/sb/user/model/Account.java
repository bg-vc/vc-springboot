package com.vc.sb.user.model;

import lombok.Data;

import java.util.Date;

@Data
public class Account {
    private Integer id;

    private String name;

    private String salt;

    private String pwdCrypt;

    private Byte status;

    private Byte isAdmin;

    private Date createTime;

    private Date updateTime;

}