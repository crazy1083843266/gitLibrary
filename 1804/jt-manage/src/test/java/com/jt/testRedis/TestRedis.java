package com.jt.testRedis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class TestRedis {
	@Test
	public void test01(){
		Jedis jedis =new Jedis("192.168.220.138");
		
		String result = jedis.set("1802", "今天洗浴中心见");
		System.out.println("结果："+result+"， 获取数据:"+jedis.get("1802"));
	}
	
	/**
	 * 通过jedis客户端实现redis分片操作
	 * 虽然redis分片以后是多台节点，但是操作时看成一台
	 */
	@Test
	public void testShard(){
		//定义连接池的大小
		JedisPoolConfig poolConfig=new JedisPoolConfig();
		poolConfig.setMaxTotal(1000);
		poolConfig.setMaxIdle(10);//最大空闲数量
		poolConfig.setTestOnBorrow(true);
		
		//定义redis分片的详细信息
		List<JedisShardInfo> shards=new ArrayList<JedisShardInfo>();
		
		JedisShardInfo info1=new JedisShardInfo("192.168.220.138",6379);
		JedisShardInfo info2=new JedisShardInfo("192.168.220.138",6380);
		JedisShardInfo info3=new JedisShardInfo("192.168.220.138",6381);
		
		shards.add(info1);
		shards.add(info2);
		shards.add(info3);
		//定义操作redis分片的api
		/*System.out.println(poolConfig+"====");
		System.out.println(shards+"====");*/
		ShardedJedisPool pool=new ShardedJedisPool(poolConfig, shards);
		ShardedJedis shardJedis = pool.getResource();
		shardJedis.set("k1", "redis分片");
		System.out.println(" 获取数据:"+shardJedis.get("k1"));
	}
	
	@Test
	public void test03(){
		
		String masterName="mymaster";
		Set<String> sentinel=new HashSet<>();
		//添加哨兵的IP：端口
		sentinel.add("192.168.220.138:26379");
		sentinel.add("192.168.220.138:26380");
		sentinel.add("192.168.220.138:26381");
		//定义哨兵的连接池
		JedisSentinelPool pool=new JedisSentinelPool(masterName, sentinel);
		//获取连接
		Jedis jedis = pool.getResource();
		//为redis赋值
		jedis.set("sentinel", "哨兵测试完成");
		System.out.println(jedis.get("sentinel"));
		
	}
	@Test
	public void test04(){
		
		Set<HostAndPort> nodes=new HashSet<>();
		nodes.add(new HostAndPort("192.168.220.138",7000));
		nodes.add(new HostAndPort("192.168.220.138",7001));
		nodes.add(new HostAndPort("192.168.220.138",7002));
		nodes.add(new HostAndPort("192.168.220.138",7003));
		nodes.add(new HostAndPort("192.168.220.138",7004));
		nodes.add(new HostAndPort("192.168.220.138",7005));
		nodes.add(new HostAndPort("192.168.220.138",7006));
		nodes.add(new HostAndPort("192.168.220.138",7007));
		nodes.add(new HostAndPort("192.168.220.138",7008));
		JedisCluster jedisCluster=new JedisCluster(nodes);
		jedisCluster.set("name", "tomcat猫");
		String result = jedisCluster.get("name");
		System.out.println(result);
		
	}
	
	
	
	
	
	
	
	
	
}
