package com.vc.sb.common.configurer;

import com.vc.sb.common.constant.Const;
import com.vc.sb.redis.service.RedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Author:       VinceChen
 * Date:         2019-10-16 23:12
 * Description:
 */
@Component
@Slf4j
public class JwtConfigurer {

    @Resource
    private RedisService redisService;

    public JwtConfigurer() {
    }

    /**
     * 根据身份ID标识, 生成Token
     *
     * @param identityId
     * @return
     */
    public String generateToken(String identityId) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + Const.jwt_expire * 1000);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(identityId)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, Const.jwt_secret)
                .compact();
    }

    /**
     * 获取Token中注册信息
     *
     * @param token
     * @return
     */
    public Claims getTokenClaim(String token) {
        try {
            return Jwts.parser().setSigningKey(Const.jwt_secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.error("getTokenClaim error:{}", e.getMessage());
            return null;
        }
    }

    /**
     * Token是否过期验证
     *
     * @param expirationTime
     * @return
     */
    public boolean isTokenExpired(Date expirationTime) {
        return expirationTime.before(new Date());
    }


}