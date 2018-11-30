package com.zfsoft.common.cache;



public interface ICacheClient {

	/**
	 * 存储指定key的缓存值,不管key是否已经存储过.
	 * 生命周期根据配置文件设置.
	 */
	public boolean set( String key, Object value);
	
	/**
	 * 存储指定key的缓存值,不管key是否已经存储过.
	 * 生命周期为永久
	 */
	public boolean setPersistence( String key, Object value);

	/**
	 * 新增指定key的缓存值，当key已经存储过，则存储失败并返回false.
	 * 生命周期根据配置文件设置.
	 */
	public boolean add(String key, Object value);
	
	/**
	 * 新增指定key的缓存值,当key已经存储过，则存储失败并返回false.
	 * 生命周期为永久.
	 */
	public boolean addPersistence(String key, Object value);

	/**获取指定key的缓存值.*/
	public Object get( String key );
	
	/**删除指定key的缓存值，并返回删除结果.*/
	public boolean delete( String key );
	
	/**删除指定key的缓存值，不返回删除结果，适合批量删除.*/
	public void deleteWithNoReply(String key);

	/**清空所有缓存，使cache中所有的数据项失效，如果是连接多个节点的memcached，那么所有的memcached中的数据项都将失效.*/
	public void flushAll();


}
