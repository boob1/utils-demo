package com.xpp.gaia.redis;

import static com.xpp.gaia.toolkit.action.ActionHandler.assertCheck;

import com.google.common.base.Preconditions;
import com.google.common.hash.Funnel;
import com.google.common.hash.Hashing;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

/**
 * Redis布隆过滤器
 *
 * @author Akira
 * @since 2023/3/3
 */
public class RedisBloomFilter<T> {

    private int hashFunctions;

    private int bitSize;

    private Funnel<T> funnel;

    private final String DEFAULT_PREFIX = "_Bloom_";

    // 默认180天失效
    private final int DEFAULT_EXPIRE = 180;


    @Autowired
    private RedisTemplate redisTemplate;

    public RedisBloomFilter(Funnel<T> funnel, int expectedInsertions, double fpp) {
        Preconditions.checkArgument(funnel != null, "funnel不能为空");
        this.funnel = funnel;
        // 计算bit数组长度
        this.bitSize = optimalNumOfBits(expectedInsertions, fpp);
        // 计算hash方法执行次数
        this.hashFunctions = optimalNumOfHashFunctions(expectedInsertions, bitSize);
    }

    /*
     * 计算bit数组长度
     */
    private int optimalNumOfBits(long n, double p) {
        if (p == 0) {
            // 设定最小期望长度
            p = Double.MIN_VALUE;
        }
        int sizeOfBitArray = (int) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
        return sizeOfBitArray;
    }

    /*
     * 计算hash方法执行次数
     */
    private int optimalNumOfHashFunctions(long n, long m) {
        int countOfHash = Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
        return countOfHash;
    }

    /*
     * hash
     */
    private int[] murmurHashOffset(T value) {
        int[] offset = new int[this.hashFunctions];
        long hash64 = Hashing.murmur3_128().hashObject(value, funnel).asLong();
        int hash1 = (int) hash64;
        int hash2 = (int) (hash64 >>> 32);
        for (int i = 1; i <= this.hashFunctions; i++) {
            int nextHash = hash1 + i * hash2;
            if (nextHash < 0) {
                nextHash = ~nextHash;
            }
            offset[i - 1] = nextHash % bitSize;
        }
        return offset;
    }

    /**
     * 添加值
     *
     * @param key   过滤器名称
     * @param value 要过滤的值
     */
    public void add(String key, T value) {
        assertCheck(key != null, "过滤器名称不能为空");
        final String keyBlm = DEFAULT_PREFIX + key;
        int[] offset = this.murmurHashOffset(value);
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                for (int i : offset) {
                    operations.opsForValue().setBit(keyBlm, i, true);
                }
                // 只要调用了add，就认为对该bitmap进行续期
                operations.expire(keyBlm, DEFAULT_EXPIRE, TimeUnit.DAYS);
                return null;
            }
        });
    }

    /**
     * 判断值是否存在(不存在则肯定不存在，存在则有0.01可能性存在)
     *
     * @param key   过滤器名称
     * @param value 要过滤的值
     * @return true为不存在，false是存在
     */
    public boolean notExists(String key, T value) {
        assertCheck(key != null, "过滤器名称不能为空");
        final String keyBlm = DEFAULT_PREFIX + key;
        int[] offset = this.murmurHashOffset(value);
        Object obj = redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                for (int i : offset) {
                    if (!operations.opsForValue().getBit(keyBlm, i)) {
                        return true;
                    }
                }
                return false;
            }
        });
        return (boolean) obj;
    }

    /**
     * 任意值不存在
     *
     * @param key
     * @param values
     * @return
     */
    public boolean notExistsAny(String key, T[] values) {
        assertCheck(key != null, "过滤器名称不能为空");
        final String keyBlm = DEFAULT_PREFIX + key;
        for (T value : values) {
            if (this.notExists(keyBlm, value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对布隆过滤器设置有效期
     *
     * @param key
     * @param expire
     * @param timeUnit
     */
    public void expire(String key, long expire, TimeUnit timeUnit) {
        redisTemplate.expire(DEFAULT_PREFIX + key, expire, timeUnit);
    }
}
