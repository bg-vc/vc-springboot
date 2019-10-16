package com.vc.sb.common.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Author:       VinceChen
 * Date:         2019-10-16 23:13
 * Description:
 */
@Configuration
public class Const {

    public static String jwt_secret;

    public static long jwt_expire;

    public static String jwt_header;


    @Value("${jwt.secret}")
    public void setJwtSecret(String jwtSecret) {
        Const.jwt_secret = jwtSecret;
    }

    @Value("${jwt.expire}")
    public void setJwtExpire(long jwtExpire) {
        Const.jwt_expire = jwtExpire;
    }

    @Value("${jwt.header}")
    public void setJwtHeader(String jwtHeader) {
        Const.jwt_header = jwtHeader;
    }


}