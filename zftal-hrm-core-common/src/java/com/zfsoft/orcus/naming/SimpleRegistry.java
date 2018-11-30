package com.zfsoft.orcus.naming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.zfsoft.orcus.lang.Cleaner;

/**
 * {@link Registry }的简单实现类
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-18
 * @version V1.0.0
 */
public class SimpleRegistry implements Registry {
	
	private Map<String, Object> _map = new HashMap<String, Object>();

	@Override
	public void bind(String name, Object object)
			throws BindingAlreadExistsException, BindingFailedException {
		if( name == null || "".equals(name) ) {
			throw new RuntimeException("parameter [name] is required; it cannot be null.");
		}
		
		if( object == null ) {
			throw new RuntimeException("parameter [object] is required; it cannot be null.");
		}
		
		synchronized (this) {
			if( _map.containsKey( name ) ) {
				throw new BindingAlreadExistsException( name );
			} else {
				_map.put(name, object);
			}
		}
	}

	@Override
	public List<String> list(String dir) throws RegistryException {
		if( dir == null || "".equals(dir) ) {
			throw new RuntimeException("parameter [dir] is required; it cannot be null.");
		}
		
		List<String> list = new ArrayList<String>(0);
		
		try {
			Iterator<String> iterable = _map.keySet().iterator();
			
			int len = dir.length();
			
			while ( iterable.hasNext() ) {
				String name = iterable.next();
				
				if( name.startsWith(dir) && name.length() > len ) {
					list.add( name );
				}
			}
			
			return list;
		} catch (Throwable t) {
			throw new RegistryException( dir, t);
		} finally {
			Cleaner.clean(list);
		}
		
	}

	@Override
	public Object lookup(String name) throws RegistryException {
		if( name == null || "".equals(name) ) {
			throw new RuntimeException("parameter [name] is required; it cannot be null.");
		}
		
		return _map.get( name );
	}

	@Override
	public void rebind(String name, Object object)
			throws BindingFailedException {
		if( name == null || "".equals(name) ) {
			throw new RuntimeException("parameter [name] is required; it cannot be null.");
		}
		
		if( object == null ) {
			throw new RuntimeException("parameter [object] is required; it cannot be null.");
		}
		
		synchronized (this) {
			_map.put(name, object);
		}
	}

	@Override
	public Object unbind(String name) throws UnbindingFailedException {
		if( name == null || "".equals(name) ) {
			throw new RuntimeException("parameter [name] is required; it cannot be null.");
		}
		
		synchronized (this) {
			return _map.remove(name);
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		Cleaner.clean( _map );
		
		super.finalize();
	}

}
