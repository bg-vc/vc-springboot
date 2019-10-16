package com.vc.sb.common.model;

import lombok.Data;

/**
 * Author:       VinceChen
 * Date:         2019-10-16 21:56
 * Description:
 */
@Data
public class BizData<T> {

    private int total;

    private T rows;
}
