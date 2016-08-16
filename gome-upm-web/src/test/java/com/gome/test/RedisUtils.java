package com.gome.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.Assert;
import redis.Gcache;
import redis.GcacheClient;

public class RedisUtils {
	public static void main(String[] args) throws InterruptedException {
		// 进程启动时创建GcacheClient(zookeeperAddress,businessName)
		Gcache gcache = new GcacheClient("10.126.53.168:2181,10.126.53.169:2181,10.126.53.170:2181", "SERVER_NETWORK_MONITOR");
		String set = gcache.set("testKey", "testValue");
		String get = gcache.get("testKey");
		System.out.println(set + "|" + get);

		// 进程关闭时关闭,linux/unix操作系统gcache会自动关闭,windows测试时需要手工关闭
		gcache.close();
	}
	
	
	@RunWith(SpringJUnit4ClassRunner.class)
	@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
	public class GcacheSpringTest {
	    @Resource(name = "monitorGcache")
	    Gcache monitorGcache;
	    @Test
	    public void lockTest() throws InterruptedException {
	        String set = monitorGcache.set("testKey", "testValue");
	        String get = monitorGcache.get("testKey");
	        Assert.assertEquals("OK", set);
	        Assert.assertEquals("testValue", get);
	    }
	}
}
