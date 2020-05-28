package com.yuan.redis.service;

/**
 * @Author yuan
 * @Date 2020/3/22 16:07
 * @Version 1.0
 */
public interface LuaScript {

    /**
     * 脚本调用方式
     * Object object = jedisUtils.eval(LuaScript.getHbLua,//lua脚本
     * 4,//参数个数
     * RedisKeys.getHbPoolKey(orderId),//对应脚本里的KEYS[1]
     * RedisKeys.getDetailListKey(orderId),//对应脚本里的KEYS[2]
     * RedisKeys.getHbRdKey(orderId),//对应脚本里的KEYS[3]
     * String.valueOf(userId));//对应脚本里的KEYS[4]
     */
    public static String getHbLua =
            //查询用户是否已抢过红包，如果用户已抢过红包，则直接返回nil 
            "if redis.call('hexists', KEYS[3], KEYS[4]) ~= 0 then\n" +
                    //如果抢过红包 返回“1”
                    "return '1';\n" +
                    "else\n" +
                    //从红包池取出一个小红包
                    "local hb = redis.call('rpop', KEYS[1]);\n" +
                    //判断红包池的红包是否为不空
                    "if hb then\n" +
                    "local x = cjson.decode(hb);\n" +
                    //将红包信息与用户ID信息绑定，表示该用户已抢到红包 
                    "x['userId'] = KEYS[4];\n" +
                    "local re = cjson.encode(x);\n" +
                    //记录用户已抢过 比如 hset hb:rd:{orderId}  {userId}  1
                    "redis.call('hset', KEYS[3], KEYS[4], '1');\n" +
                    //将抢红包的结果详情存入hb:detailList:{orderId}
                    "redis.call('lpush', KEYS[2], re);\n" +
                    "return re;\n" +
                    "else\n" +
                    //如果红包已被抢完 返回“0”
                    "return '0';" +
                    "end\n" +
                    "end\n" +
                    "return nil";

    /**
     * /**
     * * local key = KEYS[1] -- 限流 KEY（一秒一个）
     * * local limit = tonumber(ARGV[1])        -- 限流大小
     * * local current = tonumber(redis.call('get', key) or "0")
     * * if current + 1 > limit then -- 如果超出限流大小
     * *     redis.call("INCRBY", key,"1") -- 如果不需要统计真是访问量可以不加这行
     * *     return 0
     * * else  -- 请求数 +1，并设置 2 秒过期
     * *     redis.call("INCRBY", key,"1")
     * *     if tonumber(ARGV[2]) > -1 then
     * *         redis.call("expire", key,tonumber(ARGV[2])) -- 时间窗口最大时间后销毁键
     * *     end
     * *     return 1
     * * end
     */

    public static String ga = "local key = KEYS[1] -- 限流 KEY（一秒一个）\n" +
            "local limit = ARGV[1]        -- 限流大小\n" +
            "local current = tonumber(redis.call('get', key) or \"0\")" +
            "if current + 1 > limit then -- 如果超出限流大小\n" +
            "    redis.call(\"INCRBY\", key,\"1\") -- 如果不需要统计真是访问量可以不加这行\n" +
            "    return 0\n" +
            "else  -- 请求数 +1，并设置 2 秒过期\n" +
            "    redis.call(\"INCRBY\", key,\"1\")\n" +
            "    if tonumber(ARGV[2]) > -1 then\n" +
            "        redis.call(\"expire\", key,tonumber(ARGV[2])) -- 时间窗口最大时间后销毁键\n" +
            "    end\n" +
            "    return 1\n" +
            "end";


    public static String limit = "local key=KEYS[1]\n " +
            "local limit=tonumber(KEYS[2])\n" +
            "local num = tonumber(redis.call('get', key) or \"0\")\n" +
            "if num+1 > limit then \n" +
            "    redis.call(\"INCRBY\", key,\"1\") \n" +
            "    return 1 \n" +
            "else  \n" +
            "    redis.call(\"INCRBY\", key,\"1\")\n" +
            "        redis.call(\"expire\", key,KEYS[3]) \n" +
            "    return num \n" +
            "end";

}
