package com.vc.sb.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Author:       VinceChen
 * Date:         2019-10-16 22:10
 * Description:
 */

@Component
public class RedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 指定失效时间
     *
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                return redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取失效时间
     *
     * @param key
     * @return
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public boolean hashKey(final String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除
     *
     * @param key
     */
    public void del(final String key) {
        if (hashKey(key)) {
            redisTemplate.delete(key);
        }
    }


    /**
     * 批量删除
     *
     * @param keys
     */
    public void del(final String... keys) {
        for (String key : keys) {
            del(key);
        }
    }


    /**
     * 读取
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 写入
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 写入 秒
     *
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        try {
            if (expireTime > 0) {
                redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 写入 秒
     * key不存在，将key的值设置为value，返回true
     * key存在，不做任何动作，返回false
     *
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public boolean setnx(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            result = redisTemplate.opsForValue().setIfAbsent(key, value);
            if (expireTime > 0) {
                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}

