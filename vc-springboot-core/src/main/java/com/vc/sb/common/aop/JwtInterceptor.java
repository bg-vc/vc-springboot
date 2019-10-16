package com.vc.sb.common.aop;

import com.vc.sb.common.configurer.JwtConfigurer;
import com.vc.sb.common.constant.Const;
import com.vc.sb.common.exception.AuthNotAllowException;
import com.vc.sb.common.exception.ServiceNotFoundException;
import com.vc.sb.common.exception.TokenInvalidException;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author:       VinceChen
 * Date:         2019-10-16 23:11
 * Description:
 */
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private JwtConfigurer jwtConfigurer;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws RuntimeException {

        String uri = request.getRequestURI();
        if (uri.contains("/error")) {
            throw new ServiceNotFoundException();
        }
        if (uri.contains("/api/test/test") ) {
            return true;
        }

        String token = request.getHeader(Const.jwt_header);
        if (StringUtils.isBlank(token)) {
            throw new AuthNotAllowException();
        }
        Claims claims = jwtConfigurer.getTokenClaim(token);
        if (null == claims) {
            throw new TokenInvalidException();
        }
        if (jwtConfigurer.isTokenExpired(claims.getExpiration())) {
            throw new TokenInvalidException();
        }
        return true;
    }
}