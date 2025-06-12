package com.xpp.gaia.redis;

/**
 * Redis[单节点]分布式可重入自旋锁(过期不续命)
 *
 * @author Akira
 * @since 2023/1/5
 */
public class RedisQasLock extends RedisReentrantLock {

    // 自旋尝试次数
    private int retryCount = 100;

    // 自旋尝试间隔(毫秒)
    private int retryInterval = 50;

    /**
     * 加锁
     *
     * @param key
     * @param timeout
     * @return
     */
    @Override
    public boolean lock(String key, int timeout) {
        super.init();
        Boolean isLocked = this.tryLock(key, timeout);
        if (isLocked) {
            super.increaseCount();
        }
        return isLocked;
    }

    public boolean lock(String key, int timeout, int retryCount, int retryInterval) {
        this.retryCount = retryCount;
        this.retryInterval = retryInterval;
        return this.lock(key, timeout);
    }

    @Override
    protected boolean tryLock(String key, int timeout) {
        key = KEY_PREFIX + key;
        Boolean isLocked = super.tryLock(key, timeout);
        if (!isLocked) {
            int count = 0;
            while (++count <= retryCount) {
                try {
                    Thread.sleep(retryInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isLocked = super.tryLock(key, timeout);
                if (isLocked) {
                    break;
                }
            }
        }
        return isLocked;
    }

    /**
     * 解锁
     *
     * @param key
     * @return
     */
    @Override
    public void unlock(String key) {
        super.unlock(key);
    }

}