package com.Rheo.Rheo2020;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

public class JedisUtils {
    private static JedisPool jp = null;

    static {

        JedisPoolConfig jpc = new JedisPoolConfig();
        //最大
        jpc.setMaxTotal(30);
        //活动
        jpc.setMaxIdle(10);
        jp = new JedisPool(jpc,"122。114。178。53",6379);
    }

    public static Jedis getJedis(){
        return jp.getResource();
    }
    public static void main(String[] args){
        JedisUtils.getJedis();
    }
}
