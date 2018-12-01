package com.zfsoft.hrm.baseinfo.dyna.entities;

import java.io.Serializable;

import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;

/**
 * 业务操作类
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-7-10
 * @version V1.0.0
 */
public class DynaBeanBusiness implements Serializable {
	
	private static final long serialVersionUID = 1472809024109820776L;

	private boolean lessThanOne;	//最少一条记录；用于删除判断
	
	private boolean moreThanOne;	//是否为多条记录； true： 多条；false：单条；用于新增判断
	
	private boolean existPerson;
	
	/**
	 * 构造函数
	 * @param clazz 动态Bean类型描述
	 * @param existPerson 是否已存在记录
	 */
	public DynaBeanBusiness( InfoClass clazz, boolean existPerson ) {
		this.moreThanOne = clazz.isMoreThanOne();
		this.lessThanOne = clazz.getLessThanOne();
		this.existPerson = existPerson;
	}
	
	/**
	 * 是否允许新增
	 * <p>
	 * 如果信息类为单条记录，且已经存在相关人员的数据信息，则不允许增加<br>
	 * 其他情况都视为可增加
	 * </p>
	 */
	public boolean isAdd() {
		// moreThanOne	existPerson	isAdd
		// true			--			true
		// false		true		false
		// false		false		true
		
		if( ( !moreThanOne ) && existPerson ) {
			return false;
		}
		return true;
	}
	
	/**
	 * 是否允许修改
	 * <p>
	 * 在任何情况下记录都将是允许修改的
	 * </p>
	 * @return
	 */
	public boolean isModify() {

		return true;
	}
	
	/**
	 * 是否允许删除
	 * <p>
	 * 如果信息类为单条信息类，且最少保留一条记录	则不允许删删除<br>
	 * 其他情况都将允许删除
	 * </p>
	 */
	public boolean isRemove() {
		//	moreThanOne	lessThanOne	isRemove
		//	true		--			true
		//	false		true		false
		//	false		false		true
		if( ( !moreThanOne ) && lessThanOne ) {
			return false;
		}
		
		return true;
	}
}
