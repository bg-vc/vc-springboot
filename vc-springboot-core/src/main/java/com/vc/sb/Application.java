package com.vc.sb;

import com.vc.sb.common.util.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Author:       VinceChen
 * Date:         2019-10-16 22:07
 * Description:
 */
@SpringBootApplication
@Import(value = {SpringUtil.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

