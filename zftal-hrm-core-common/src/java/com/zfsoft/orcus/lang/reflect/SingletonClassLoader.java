package com.zfsoft.orcus.lang.reflect;

import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLClassLoader;

import com.zfsoft.orcus.lang.LocalReference;
import com.zfsoft.orcus.lang.StringUtil;

/**
 * 单态类加载器，方法#getInstance()返回该类的实例。
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-11
 * @version V1.0.0
 */
@SuppressWarnings("unchecked")
public class SingletonClassLoader extends URLClassLoader{

	private static Class[] PRIMITIVETYPES = new Class[]{
		boolean.class,
		byte.class,
		char.class,
		short.class,
		int.class,
		long.class,
		float.class,
		double.class,
		void.class
	};
	
	private static final LocalReference REF = new LocalReference() {

		@Override
		protected Object createInstance() {
			return new SingletonClassLoader(new URL[0]);
		}
		 
	};
	
	/**
	 * 构造函数
	 * @param urls
	 */
	private SingletonClassLoader(URL[] urls) {
		super(urls, Thread.currentThread().getContextClassLoader());
	}
	
	/**
	 * 返回该类的实例
	 * @return
	 */
	public static SingletonClassLoader getInstance() {
		return (SingletonClassLoader) REF.get();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.net.URLClassLoader#addURL(java.net.URL)
	 */
	@Override
	public void addURL(URL url) {
		if( url == null) {
			return;
		}
		
		if( hasURL(url) ) {
			return;
		}
		
		super.addURL(url);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.ClassLoader#loadClass(java.lang.String)
	 */
	@Override
	public Class loadClass( String name ) throws ClassNotFoundException{
		if( name != null && name.indexOf( "[]" ) > 0 ) {
			int idx = name.indexOf( "[]" );
			int len = StringUtil.appear( name, "[]" );
			String ec = name.substring( 0, idx );
			
			return Array.newInstance( forName(ec), new int[len] ).getClass();
		}
		
		return forName(name);
	}

	/**
	 * 判断类加载器的URL列表中是否存在指定的URL
	 * @param url 被判断的URL
	 * @return true-指定的URL已在URL列表中；false-指定的URL不在URL列表中。
	 */
	public boolean hasURL( URL url ) {
		if( url == null ) {
			return false;
		}
		
		URL[] urls = getURLs();
		
		if(urls == null || urls.length == 0) {
			return false;
		}
		
		for(int i = 0; i < urls.length; i++ ) {
			if( urls[i].equals(url) ) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 重新加载所有的类路径
	 */
	public void reload() {
		URL[] urls = getURLs();
		
		if( urls == null || urls.length == 0 ) {
			return;
		}
		
		REF.set( new SingletonClassLoader(urls));
	}
	
	private Class forName(String name) throws ClassNotFoundException {
		for( int i=0; i<PRIMITIVETYPES.length; i++ ) {
			 if( PRIMITIVETYPES[i].getName().equals( name ) ) {
				 return PRIMITIVETYPES[i];
			 }
		}
		
		return Class.forName(name);
	}

}
