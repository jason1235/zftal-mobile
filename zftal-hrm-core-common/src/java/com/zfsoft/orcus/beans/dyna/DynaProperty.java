package com.zfsoft.orcus.beans.dyna;

import java.io.Serializable;

import com.zfsoft.orcus.lang.reflect.Property;
import com.zfsoft.orcus.lang.reflect.ReadPropertyException;
import com.zfsoft.orcus.lang.reflect.Type;
import com.zfsoft.orcus.lang.reflect.UnreadablePropertyException;
import com.zfsoft.orcus.lang.reflect.UnwritablePropertyException;
import com.zfsoft.orcus.lang.reflect.WritePropertyException;

/**
 * 动态JavaBean的属性
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-8
 * @version V1.0.0
 */
public class DynaProperty implements Property {

	private static final long serialVersionUID = 154982200329296408L;

	private String name;			//属性名
	
	private String ownerClassName;	//动态JavaBean的虚拟类名
	
	private Type type;				//属性类型描述
	
	private boolean readable;		//属性是否可读
	
	private boolean writable;		//属性是否可写
	
	private Serializable data;		//与属性相关的数据
	
	/**
	 * 构造函数
	 * @param name 属性名
	 * @param ownerClassName 动态JavaBean的虚拟类名
	 * @param type 属性类型描述
	 */
	public DynaProperty(String name, String ownerClassName, Type type) {
		this.name = name;
		this.ownerClassName = ownerClassName;
		this.type = type;
		this.readable = true;
		this.writable = true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.zfsoft.hrm.lang.reflect.Property#getName()
	 */
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * @see com.zfsoft.hrm.lang.reflect.Property#getOwnerClassName()
	 */
	public String getOwnerClassName() {
		return ownerClassName;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.zfsoft.hrm.lang.reflect.Property#getType()
	 */
	@Override
	public Type getType() {
		return type;
	}

	/*
	 * (non-Javadoc)
	 * @see com.zfsoft.hrm.lang.reflect.Property#isReadable()
	 */
	public boolean isReadable() {
		return readable;
	}

	/*
	 * (non-Javadoc)
	 * @see com.zfsoft.hrm.lang.reflect.Property#isWritable()
	 */
	public boolean isWritable() {
		return writable;
	}

	/**
	 * 设置属性是否可读
	 * @param readable 属性是否可读 
	 */
	public void setReadable(boolean readable) {
		this.readable = readable;
	}
	
	/**
	 * 设置属性是否可写
	 * @param writable 属性是否可写 
	 */
	public void setWritable(boolean writable) {
		this.writable = writable;
	}

	/**
	 * 返回与属性相关的数据
	 */
	public Serializable getData() {
		return data;
	}

	/**
	 * 设置与属性相关的数据
	 * @param data 与属性相关的数据
	 */
	public void setData(Serializable data) {
		this.data = data;
	}

	/*
	 * (non-Javadoc)
	 * @see com.zfsoft.hrm.lang.reflect.Property#read(java.lang.Object)
	 */
	public Object read(Object bean) throws UnreadablePropertyException,
			ReadPropertyException {
		if( !isReadable() ) {
			throw new UnreadablePropertyException("Property["+getName()+"] of "
					+ getOwnerClassName() + " is not readable property.");
		}
		
		try {
			return ((DynaBean) bean).get( getName() );
		} catch (Throwable t) {
			throw new ReadPropertyException(t);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.zfsoft.hrm.lang.reflect.Property#write(java.lang.Object, java.lang.Object)
	 */
	public void write(Object bean, Object propValue)
			throws UnwritablePropertyException, WritePropertyException {
		if( !isWritable() ) {
			throw new UnwritablePropertyException("Property[" + getName() + "] of "
					+ getOwnerClassName() + " is not writable property.");
		}
		
		try {
			((DynaBean) bean).set(getName(), propValue);
		} catch (Throwable t) {
			throw new WritePropertyException(t);
		}
		
	}

}
