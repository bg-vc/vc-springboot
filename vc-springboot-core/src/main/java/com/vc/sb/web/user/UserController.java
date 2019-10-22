package com.vc.sb.web.user;

import com.vc.sb.common.enums.ResultCode;
import com.vc.sb.common.model.BizData;
import com.vc.sb.common.model.Result;
import com.vc.sb.user.model.Account;
import com.vc.sb.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author:       VinceChen
 * Date:         2019-10-22 22:13
 * Description:
 */
@RequestMapping("api/user")
@RestController
@Slf4j
public class UserController {

    @Resource
    private IUserService userService;

    @GetMapping(value = "/getAccountById")
    public Result<Account> getAccount(Integer id) {
        Result<Account> result = new Result<>();
        if (null == id || id == 0) {
            result.setResult(ResultCode.err_param);
        }
        result = userService.selectById(id);
        return result;
    }

    @GetMapping(value = "/selectByStatus")
    public Result<BizData<List>> selectByStatus(Integer status) {
        Result<BizData<List>> result;
        if (null == status) {
            status = 0;
        }
        result = userService.selectByStatus(status);
        return result;
    }
}
