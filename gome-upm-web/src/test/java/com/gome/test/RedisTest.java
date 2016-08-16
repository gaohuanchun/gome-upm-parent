package com.gome.test;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.Gcache;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-config.xml" })
public class RedisTest {
    @Resource(name = "monitorGcache")
    private Gcache monitorGcache;
    
   // @Ignore
    @Test
    public void demo01Test(){
    	
    	String set = monitorGcache.set("testKey", "testValue2");
    	monitorGcache.expire("testKey", 10);
        String get = monitorGcache.get("testKey");
        System.out.println("set:"+set+"|get:"+get);

    }
 //   @Ignore
    @Test
    public void demo02Test(){
    	
    	String get = monitorGcache.get("testKey");
        System.out.println("|get:"+get);

    }
}
