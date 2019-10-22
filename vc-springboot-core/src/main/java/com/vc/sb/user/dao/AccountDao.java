package com.vc.sb.user.dao;

import com.vc.sb.user.mapper.AccountMapper;
import com.vc.sb.user.model.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author:       VinceChen
 * Date:         2019-10-22 22:04
 * Description:
 */
@Mapper
public interface AccountDao extends AccountMapper {
    /**
     *
     * @param status
     * @return
     */
    List<Account> selectByStatus(@Param("status") int status);
}
