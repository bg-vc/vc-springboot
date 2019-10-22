package com.vc.sb.user.service;

import com.vc.sb.common.model.BizData;
import com.vc.sb.common.model.Result;
import com.vc.sb.user.model.Account;

import java.util.List;

/**
 * Author:       VinceChen
 * Date:         2019-10-22 22:07
 * Description:
 */
public interface IUserService {
    /**
     *
     * @param id
     * @return
     */
    Result<Account> selectById(Integer id);

    /**
     *
     * @param status
     * @return
     */
    Result<BizData<List>> selectByStatus(Integer status);
}
