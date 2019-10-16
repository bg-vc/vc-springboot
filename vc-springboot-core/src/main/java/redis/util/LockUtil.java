package redis.util;

import com.vc.sb.common.util.SpringUtil;
import com.vc.sb.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Author:       VinceChen
 * Date:         2019-10-16 22:21
 * Description:
 */
@Slf4j
public class LockUtil {

    private static RedisService redisService = (RedisService) SpringUtil.getBean("redisService");

    public static Object get(String key) {
        return redisService.get(key);
    }

    public static void set(String key, Object value) {
        redisService.set(key, value);
    }

    public static void remove(String key) {
        redisService.del(key);
    }

    private static Long default_expire_time = 10L;

    private LockUtil() {
    }

    /**
     * @param obj
     * @param seconds
     * @param <T>
     * @return
     */
    public synchronized static <T> T lockObj(T obj, long seconds) {
        try {
            if (obj != null) {
                String lockKey = getLockKey(obj);
                log.info("lockObj lockKey:{}", lockKey);
                Object value = redisService.get(lockKey);
                if (value != null) {
                    log.info("data hash been locked before, key:{}", lockKey);
                    return null;
                }
                if (redisService.setnx(lockKey, "", seconds)) {
                    log.info("data has been locked, key:{}", lockKey);
                    return obj;
                }
            }

        } catch (Exception e) {
            log.error("data locked error:{}", e.getMessage());
        }
        return null;
    }

    /**
     * @param obj
     * @param <T>
     * @return
     */
    public synchronized static <T> T lockObj(T obj) {
        try {
            if (obj != null) {
                String lockKey = getLockKey(obj);
                Object value = redisService.get(lockKey);
                if (value != null) {
                    log.info("lockObj data hash been locked before, key:{}", lockKey);
                    return null;
                }
                if (redisService.setnx(lockKey, "", 0L)) {
                    log.info("lockObj data has been locked, key:{}", lockKey);
                    return obj;
                }
            }
        } catch (Exception e) {
            log.error("lockObj data locked error:{}", e.getMessage());
        }
        return null;
    }

    /**
     * @param obj
     * @param <T>
     */
    public synchronized static <T> void unLockObj(T obj) {
        try {
            if (obj != null) {
                String lockKey = getLockKey(obj);
                Object value = redisService.get(lockKey);
                if (value != null) {
                    redisService.del(lockKey);
                }
                log.info("unLockObj data has been unlocked, key:{}", lockKey);
            }
        } catch (Exception e) {
            log.error("unLockObj data unlocked error:{}", e.getMessage());
        }
    }

    /**
     * @param rbList
     * @param <T>
     * @return
     */
    public synchronized static <T> List<T> lockObjs(List<T> rbList) {
        try {
            if (rbList != null) {
                List<T> resultList = new ArrayList<>();
                Iterator<T> it = rbList.iterator();
                while (it.hasNext()) {
                    T rb = it.next();
                    String lockKey = getLockKey(rb);
                    Object value = redisService.get(lockKey);
                    if (value != null) {
                        log.info("lockObjs data has been locked before, key:{}", lockKey);
                        continue;
                    }
                    if (redisService.setnx(lockKey, "", default_expire_time)) {
                        resultList.add(rb);
                        log.info("lockObjs data has been locked, key:{}", lockKey);
                    }
                }
                log.info("lockObjs need to lock total:{}, success to lock size:{}", rbList.size(), resultList.size());
                return resultList;
            }
        } catch (Exception e) {
            log.error("lockObjs lockObjs error:{}", e.getMessage());
        }
        return null;
    }

    /**
     * @param rbList
     */
    public static void unLockObjs(List<?> rbList) {
        try {
            if (rbList != null) {
                Iterator<?> it = rbList.iterator();
                while (it.hasNext()) {
                    Object rb = it.next();
                    String lockKey = getLockKey(rb);
                    Object value = redisService.get(lockKey);
                    if (value != null) {
                        redisService.del(lockKey);
                    }
                }
                log.info("unLockObjs success to unlock size:{}", rbList.size());
            }
        } catch (Exception e) {
            log.error("unLockObjs unLockObjs error:{}", e.getMessage());
        }
    }

    public static String getLockKey(Object obj) {
        String locKey = "com:vc:sb:";
        /*if (obj instanceof Order) {
            Object payType = PropertyUtils.getProperty(obj, "payType");
            Object tradeNo = PropertyUtils.getProperty(obj, "tradeNo");
            String key = payType + ":" + tradeNo;
            locKey = "com:vc.sb:" + key;
        }*/

        return locKey;
    }
}
