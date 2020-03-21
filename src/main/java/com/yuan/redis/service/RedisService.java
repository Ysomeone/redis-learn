package com.yuan.redis.service;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author yuan
 * @Date 2020/3/14 18:12
 * @Version 1.0
 */
public interface RedisService {

    public void delete(String key);

    public void delete(Collection<String> keys);

    public byte[] dump(String key);

    public Boolean hasKey(String key);

    public Boolean expire(String key, long timeout, TimeUnit unit);

    public Boolean expireAt(String key, Date date);

    public Set<String> keys(String pattern);

    public Boolean move(String key, int dbIndex);

    public Boolean persist(String key);

    public Long getExpire(String key, TimeUnit unit);

    public String randomKey();

    public void rename(String oldKey, String newKey);

    public Boolean renameIfAbsent(String oldKey, String newKey);

    public DataType type(String key);

    public void set(String key, String value);

    public String get(String key);

    public String getRange(String key, long start, long end);

    public String getAndSet(String key, String value);

    public Boolean getBit(String key, long offset);

    public List<String> multiGet(Collection<String> keys);

    public boolean setBit(String key, long offset, boolean value);


    public void setEx(String key, String value, long timeout, TimeUnit unit);

    public boolean setIfAbsent(String key, String value);

    public void setRange(String key, String value, long offset);

    public Long size(String key);

    public void multiSet(Map<String, String> maps);

    public boolean multiSetIfAbsent(Map<String, String> maps);

    public Long incrBy(String key, long increment);


    public Double incrByFloat(String key, double increment);

    public Integer append(String key, String value);

    public Object hGet(String key, String field);

    public Map<Object, Object> hGetAll(String key);

    public List<Object> hMultiGet(String key, Collection<Object> fields);

    public void hPut(String key, String hashKey, String value);

    public void hPutAll(String key, Map<String, String> maps);

    public Boolean hPutIfAbsent(String key, String hashKey, String value);

    public Long hDelete(String key, Object... fileds);

    public boolean hExists(String key, String field);

    public Long hIncrBy(String key, Object field, long increment);

    public Double hIncrByFloat(String key, Object field, double delta);

    public Set<Object> hKeys(String key);

    public Long hSize(String key);

    public List<Object> hValues(String key);

    public Cursor<Map.Entry<Object, Object>> hScan(String key, ScanOptions options);

    public String lIndex(String key, long index);

    public List<String> lRange(String key, long start, long end);

    public Long lLeftPush(String key, String value);

    public Long lLeftPushAll(String key, String... value);

    public Long lLeftPushAll(String key, Collection<String> value);

    public Long lLeftPushIfPresent(String key, String value);

    public Long lLeftPush(String key, String pivot, String value);

    public Long lRightPush(String key, String value);

    public Long lRightPushAll(String key, String... value);

    public void lSet(String key, long index, String value);

    public String lBLeftPop(String key, long timeout, TimeUnit unit);

    public String lRightPop(String key);

    public String lRightPopAndLeftPush(String sourceKey, String destinationKey);

    public Long lRemove(String key, long index, String value);

    public Long sAdd(String key, String... value);

    public Boolean isMember(String key, Object value);

    public Set<String> intersect(String key, String otherKey);

    public Set<String> intersect(String key, Collection<String> otherKeys);

    public Long intersectAndStore(String key, String otherKey, String destKey);

    public Set<String> union(String key, String otherKeys);

    public Set<String> union(String key, Collection<String> otherKeys);

    public Long unionAndStore(String key, String otherKey, String destKey);

    public Set<String> difference(String key, String otherKey);

    public Set<String> setMembers(String key);

    public String randomMember(String key);

    public List<String> randomMembers(String key, long count);

    public Set<String> distinctRandomMembers(String key, long count);

    public Cursor<String> scan(String key, ScanOptions options);

    public Boolean zAdd(String key, String value, double score);

    public Long zAdd(String key, Set<ZSetOperations.TypedTuple<String>> values);

    public Long zRemove(String key, Object... values);

    public Double zIncrementScore(String key, String value, double delta);

    public Long zRank(String key, Object value);

    public Set<String> zRange(String key, long start, long end);

    public Set<ZSetOperations.TypedTuple<String>> zRangeWithScores(String key, long start
            , long end);

    public Set<String> zRangeByScore(String key, double min, double max);

    public Set<ZSetOperations.TypedTuple<String>> zRangeByScoreWithScores(String key, double min, double max);

    public Set<ZSetOperations.TypedTuple<String>> zRangeByScoreWithScores(String key, double min
            , double max, long start, long end);

    public Set<String> zReverseRange(String key, long start, long end);

    public Long zCount(String key, double min, double max);

    public Long zSize(String key);

    public double zScore(String key, Object value);

    public Long zRemoveRange(String key, long start, long end);

    public Long zRemoveRangeByScore(String key, double min, double max);

    public Long zUnionAndStore(String key, String otherKey, String destKey);

    public Cursor<ZSetOperations.TypedTuple<String>> zScan(String key, ScanOptions options);
}