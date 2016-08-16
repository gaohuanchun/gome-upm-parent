package com.gome.upm.controler.base;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.alibaba.fastjson.JSON;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;


public class RedisClientFactoryBean {
	
	private static List<String>  masterConfList;
	
	private static  JedisCluster instance;  
	

	public List<String> getMasterConfList() {
		return masterConfList;
	}


	public void setMasterConfList(List<String> masterConfList) {
		this.masterConfList = masterConfList;
	}

	public static JedisCluster getInstance()
	{
		if (instance == null)
		{
		
	      synchronized(JedisCluster.class) { 
	      if (instance == null) {       
	    	//只给集群里一个实例就可以     
            Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();  
            for(String url : masterConfList){
            	 String  host = url.split(":")[0]; 
            	 int  port = Integer.parseInt(url.split(":")[1]); 
            	 jedisClusterNodes.add(new HostAndPort(host , port));  
            }
            instance = new JedisCluster(jedisClusterNodes);  
	      }
	    }
	  }
	  return instance;
	}
	
	
	public  void set(String key ,Object object)
	{
		 String json = JSON.toJSONString(object);
		 getInstance().set(key, json);  
	}
	
	
	public  String get(String key)
	{
		String  json = getInstance().get(key);
		return json;
	    
	}
	
	

}
