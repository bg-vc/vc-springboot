package com.vc.sb.user.service.impl;

import com.vc.sb.common.enums.ResultCode;
import com.vc.sb.common.model.BizData;
import com.vc.sb.common.model.Result;
import com.vc.sb.user.dao.AccountDao;
import com.vc.sb.user.model.Account;
import com.vc.sb.user.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author:       VinceChen
 * Date:         2019-10-22 22:08
 * Description:
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private AccountDao accountDao;

    @Override
    public Result<Account> selectById(Integer id) {
        Result<Account> result = new Result<>();
        Account account = accountDao.selectByPrimaryKey(id);
        return result.setResult(ResultCode.success).setData(account);
    }

    @Override
    public Result<BizData<List>> selectByStatus(Integer status) {
        Result<BizData<List>> result = new Result<>();
        BizData<List> bizData = new BizData<>();
        List<Account> list = accountDao.selectByStatus(status);
        bizData.setTotal(list.size());
        bizData.setRows(list);
        return result.setResult(ResultCode.success).setData(bizData);
    }


}
