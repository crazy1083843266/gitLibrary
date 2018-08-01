package com.jt.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.TimeUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class RedisService {
	@Autowired(required=false)
	//rivate ShardedJedisPool jedisPool;
	private JedisSentinelPool sentinelPool;
	public void set(String key,String value){
		 Jedis jedis= sentinelPool.getResource();
		jedis.set(key, value);
		
		//将连接还回池中
		sentinelPool.returnResource(jedis);
	}
	
	public String get(String key){
		Jedis jedis = sentinelPool.getResource();
		String result = jedis.get(key);
		sentinelPool.returnResource(jedis);
		return result;
	}
	
	public void set(String key,int seconds,String value){
		Jedis jedis= sentinelPool.getResource();
		jedis.setex(key, seconds, value);
		sentinelPool.returnResource(jedis);

	}
}
