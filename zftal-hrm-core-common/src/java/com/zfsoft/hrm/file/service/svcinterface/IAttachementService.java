package com.zfsoft.hrm.file.service.svcinterface;

import java.util.List;

import com.zfsoft.hrm.file.entity.Attachement;
import com.zfsoft.hrm.file.query.AttachementQuery;

/**
 * 附件操作业务逻辑接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-10-19
 * @version V1.0.0
 */
public interface IAttachementService {
	
	/**
	 * 返回附件实体列表
	 * @param query 查询条件实体
	 * @return
	 * @throws RuntimeException 如果操作出现异常
	 */
	public List<Attachement> getList(AttachementQuery query) throws RuntimeException;
	
	/**
	 * 返回指定的附件实体
	 * @param guid 附件实体全局ID
	 * @return
	 * @throws RuntimeException 如果操作出现异常
	 */
	public Attachement getById(String guid) throws RuntimeException;
	
	/**
	 * 删除指定的附件实体
	 * @param guid 附件实体的全局ID
	 * @throws RuntimeException 如果操作出现异常
	 */
	public void removeById(String guid) throws RuntimeException;
}
