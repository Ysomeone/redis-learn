package com.yuan.redis.toolkit;

/**
 * @Author yuan
 * @Date 2020/3/15 22:58
 * @Version 1.0
 */
public class RedisKeyUtils {
    /**
     * 保存用户点赞数据的key
     */
    public static final String MAP_KEY_USER="MAP_USER_LIKED";
    /**
     * 保存用户被点赞数量的key
     */
    public static final String MAP_KEY_USER_LIKED_COUNT="MAP_USER_LIKED_COUNT";

    /**
     *拼接被点赞的用户id和点赞的人的id作为key
     * @param likedUserId
     * @param likedPostId
     * @return
     */
    public static String getLikedKey(String likedUserId,String likedPostId){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(likedUserId);
        stringBuilder.append("::");
        stringBuilder.append(likedPostId);
        return stringBuilder.toString();
    }



}
