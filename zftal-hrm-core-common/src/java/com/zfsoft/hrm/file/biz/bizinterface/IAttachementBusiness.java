package com.zfsoft.hrm.file.biz.bizinterface;

import java.util.List;

import com.zfsoft.hrm.file.entity.Attachement;
import com.zfsoft.hrm.file.exception.AttachementException;
import com.zfsoft.hrm.file.query.AttachementQuery;

/**
 * {@link Attachement}业务操作接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-10-19
 * @version V1.0.0
 */
public interface IAttachementBusiness {
	
	/**
	 * 获取指定表单的附件列表
	 * @param formId 表单ID
	 * @return 指定表单的附件列表
	 * @throws DataAccessException 如果操作出现异常
	 */
	public List<Attachement> getFromAttachements( String formId ) throws AttachementException;
	
	/**
	 * 获取符合条件的附件列表
	 * @param query 附件查询条件
	 * @return 符合条件的附件列表
	 * @throws AttachementException 如果操作出现异常
	 */
	public List<Attachement> getAttachements( AttachementQuery query ) throws AttachementException;
	
	/**
	 * 获取指定的附件对象
	 * @param guid 指定附件对象的全局ID
	 * @return 单个附件信息
	 * @throws AttachementException 如果操作出现异常
	 */
	public Attachement getAttachement( String guid ) throws AttachementException;
	
	/**
	 * 保存附件信息
	 * <p>所有的附件不做修改只做增加/删除</p>
	 * @param bean 保存的附件信息
	 * @throws AttachementException 如果操作出现异常
	 */
	public void save( Attachement bean ) throws AttachementException;
	
	/**
	 * 删除指定附件全局ID附件信息
	 * @param guid 删除的附件信息的全局ID
	 * @throws AttachementException 如果操作出现异常
	 */
	public void removeById( String guid ) throws AttachementException;
	
	/**
	 * 删除指定表单下的所有附件信息
	 * @param formId 删除附件所属的表单ID
	 * @throws AttachementException 如果操作出现异常
	 */
	public void removeByFormId( String formId ) throws AttachementException;
	
}
