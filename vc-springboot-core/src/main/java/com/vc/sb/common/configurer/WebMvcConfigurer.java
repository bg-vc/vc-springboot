package com.vc.sb.common.configurer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.vc.sb.common.aop.JwtInterceptor;
import com.vc.sb.common.enums.ResultCode;
import com.vc.sb.common.exception.AuthNotAllowException;
import com.vc.sb.common.exception.ServiceNotFoundException;
import com.vc.sb.common.exception.TokenInvalidException;
import com.vc.sb.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Author:       VinceChen
 * Date:         2019-10-16 23:22
 * Description:
 */
@Configuration
@Slf4j
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
    @Resource
    private JwtInterceptor jwtInterceptor;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.DisableCircularReferenceDetect);
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName("UTF-8"));

        converters.add(converter);
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/**");
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add((request, response, handler, e) -> {
            {
                Result<String> result = new Result<>();
                if (e instanceof AuthNotAllowException) {
                    log.error("AuthNotAllowException:{}", e.getMessage());
                    result.setResult(ResultCode.auth_not_allow);
                } else if (e instanceof TokenInvalidException) {
                    log.error("TokenInvalidException:{}", e.getMessage());
                    result.setResult(ResultCode.token_invalid);
                } else if (e instanceof ServiceNotFoundException) {
                    log.error("ServiceNotFoundException:{}", e.getMessage());
                    result.setResult(ResultCode.service_not_found);
                } else {
                    e.printStackTrace();
                    log.error("Exception:{}", e.getMessage());
                    result.setResult(ResultCode.internal_server_error);
                }
                responseResult(response, result);
                return new ModelAndView();
            }
        });
    }

    private void responseResult(HttpServletResponse response, Result result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSONObject.toJSONString(result));
        } catch (IOException e) {
            log.error("responseResult:{}", e.getMessage());
        }
    }
}
