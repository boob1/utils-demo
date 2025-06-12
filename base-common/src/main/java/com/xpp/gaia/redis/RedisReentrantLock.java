package com.xpp.gaia.redis;

import com.xpp.gaia.boot.utils.SpringUtil;
import java.util.Arrays;
import java.util.UUID;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

/**
 * Redis[单节点]分布式可重入锁(过期不续命)
 *
 * @author Akira
 * @since 2023/1/6
 */
public class RedisReentrantLock {

    protected RedisTemplate<String, Object> redisTemplate;

    protected final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    protected String lockScript = "if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then redis.call('pexpire', KEYS[1], ARGV[2]) return 1 else return 0 end";
    protected String unlockScript = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    private ThreadLocal<Integer> threadLocalCount = new ThreadLocal<Integer>();

    final String KEY_PREFIX = "__LOCK_";

    public void init() {
        if (this.redisTemplate == null)
            this.redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("redisTemplate");
    }

    /**
     * 加锁
     *
     * @param key
     * @param timeout
     * @return
     */
    public boolean lock(String key, int timeout) {
        this.init();
        Boolean isLocked = this.tryLock(key, timeout);
        if (isLocked) {
            this.increaseCount();
        }
        return isLocked;
    }

    protected boolean tryLock(String key, int timeout) {
        if (threadLocal.get() != null) {
            return true;
        }
        key = KEY_PREFIX + key;
        String uuid = UUID.randomUUID().toString();
        RedisScript<Long> redisScript = new DefaultRedisScript<Long>(lockScript, Long.class);
        Long res = redisTemplate.execute(redisScript, Arrays.asList(key), uuid, timeout);
        boolean flag = res != null && res == 1;
        if (flag) {
            threadLocal.set(uuid);
        }
        return flag;
    }

    /**
     * 解锁
     *
     * @param key
     * @return
     */
    public void unlock(String key) {
        key = KEY_PREFIX + key;
        Integer called = threadLocalCount.get();
        // 计数器减为0时才能释放锁
        if (called == null || --called <= 0) {
            RedisScript<Long> redisScript = new DefaultRedisScript<Long>(unlockScript, Long.class);
            // 确保判断 + 解锁原子化
            // 线程归一化，即只有指定线程才能删除指定锁
            redisTemplate.execute(redisScript, Arrays.asList(key), threadLocal.get());
            threadLocal.remove();
            threadLocalCount.remove();
        } else {
            threadLocalCount.set(called);  // 仅更新计数器，不释放锁
        }
    }

    protected void increaseCount() {
        Integer count = threadLocalCount.get() == null ? 0 : threadLocalCount.get();
        threadLocalCount.set(++count);
    }
}