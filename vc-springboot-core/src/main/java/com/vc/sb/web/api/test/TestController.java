package com.vc.sb.web.api.test;

import com.vc.sb.common.enums.ResultCode;
import com.vc.sb.common.model.Result;
import com.vc.sb.common.util.RequestUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Author:       VinceChen
 * Date:         2019-10-16 23:29
 * Description:
 */
@RestController
@RequestMapping("api/test/")
public class TestController {

    public static String type;

    @GetMapping(value = "test")
    public Result<String> test(HttpServletRequest request) {
        boolean flag = RequestUtil.JudgeIsMobile(request);
        System.out.println(flag);
        Result<String> result = new Result<>();
        String data = "hello:" + type;
        return result.setResult(ResultCode.success).setData(data);
    }

    @Value("${spring.profiles.active}")
    public void setType(String type) {
        TestController.type = type;
    }

}