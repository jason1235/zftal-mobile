package com.zfsoft.common.cache;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.log4j.Logger;

public class NewMemcachedClient implements ICacheClient{
	
	private static final Logger log = Logger.getLogger(NewMemcachedClient.class);
	
	private MemcachedClient memcachedClient;
	
	private int expiry;
	
	@Override
	public boolean set(String key, Object value) {
		boolean flag  = false;
		try {
			flag =  memcachedClient.set(key,expiry, value);
		} catch (Exception  e) {
			log.error("set：存储key为"+key+"的缓存值发生异常!异常信息如下："+e.getMessage());
		} 
		return flag;
		
	}
	
	public boolean setPersistence( String key, Object value){
		boolean flag  = false;
		try {
			flag =  memcachedClient.set(key,0, value);
		} catch (Exception  e) {
			log.error("setPersistence：存储key为"+key+"的持久缓存值发生异常!异常信息如下："+e.getMessage());
		} 
		return flag;
	}
	
	@Override
	public boolean add(String key, Object value) {
		boolean flag  = false;
		try {
			flag =  memcachedClient.add(key, expiry, value);
		} catch (Exception  e) {
			log.error("add：新增key为"+key+"的缓存值发生异常!异常信息如下："+e.getMessage());
		} 
		return flag;
		
	}
	
	public boolean addPersistence(String key, Object value){
		boolean flag  = false;
		try {
			flag =  memcachedClient.add(key, 0, value);
		} catch (Exception  e) {
			log.error("addPersistence：新增key为"+key+"的持久缓存值发生异常!异常信息如下："+e.getMessage());
		} 
		return flag;
	}
	
	public Object get( String key ){
		Object object = null;
		try {
			object =  memcachedClient.get(key);
		} catch (Exception  e) {
			log.error("getAttribute：获得key的缓存值发生异常!异常信息如下："+e.getMessage());
		} 
		return object;
	}
	
	@Override
	public boolean delete(String key) {
		boolean flag  = false;
		try {
			flag = memcachedClient.delete(key);
		} catch (Exception  e) {
			log.error("delete：删除key的缓存值发生异常!异常信息如下："+e.getMessage());
		} 
		return flag;
	}
	
	public void deleteWithNoReply(String key){
		try {
			memcachedClient.deleteWithNoReply(key);
		} catch (Exception  e) {
			log.error("deleteWithNoReply：删除key的缓存值发生异常!异常信息如下："+e.getMessage());
		} 
	}
	
	public void flushAll(){
		try {
			memcachedClient.flushAll();
		} catch (Exception e) {
			log.error("flushAll：清空所有缓存发生异常!异常信息如下："+e.getMessage());
		} 
	}

	public MemcachedClient getMemcachedClient() {
		return memcachedClient;
	}

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	public int getExpiry() {
		return expiry;
	}

	public void setExpiry(int expiry) {
		this.expiry = expiry;
	}




	

}
