package com.zfsoft.workflow.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.workflow.dao.ISpBusinessDao;
import com.zfsoft.workflow.dao.ISpProcedureBillDao;
import com.zfsoft.workflow.enumobject.BusinessEnum;
import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpBusiness;
import com.zfsoft.workflow.model.SpProcedureBill;
import com.zfsoft.workflow.model.query.SpBusinessQuery;
import com.zfsoft.workflow.service.ISpBusinessService;

/**
 * 类说明：业务管理SERVICE实现类
 * 
 * @author yingjie.fan
 * @version 1.0
 */
public class SpBusinessServiceImpl extends BaseInterfaceServiceImpl implements ISpBusinessService {
	/* @model: 注入SpBusiness */
	public ISpBusinessDao spBusinessDao;
	public ISpProcedureBillDao spProcedureBillDao;

	public void setSpBusinessDao(ISpBusinessDao spBusinessDao) {
		this.spBusinessDao = spBusinessDao;
	}

	public void setSpProcedureBillDao(ISpProcedureBillDao spProcedureBillDao) {
		this.spProcedureBillDao = spProcedureBillDao;
	}

	@Override
	public void insert(SpBusiness spBusiness, String[] calssIds, String[] calssPrivilege) throws WorkFlowException {
		try {
			int result = spBusinessDao.getCountByNameAndType(spBusiness);
			if (result > 0) {
				throw new WorkFlowException("业务名称和业务类型相同的记录已经存在，不能执行新增操作！");
			} else {
				if (calssIds != null) {
					String classPrivilegeStr = "";
					if (calssIds != null && calssIds.length > 0) {
						for (int i = 0; i < calssIds.length; i++) {
							classPrivilegeStr += calssIds[i] + "-" + calssPrivilege[i] + ",";
						}
					}
					spBusiness.setClassesPrivilege(StringUtil.removeLast(classPrivilegeStr));
				}
				spBusinessDao.insert(spBusiness);
				SpProcedureBill spProcedureBill = new SpProcedureBill();
				spProcedureBill.setBillId(spBusiness.getBillId());
				spProcedureBill.setClassesPrivilege(spBusiness.getClassesPrivilege());
				spProcedureBill.setPid(spBusiness.getPid());
				spProcedureBillDao.update(spProcedureBill);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	
	/* (non-Javadoc)
	 * @see com.zfsoft.workflow.service.ISpBusinessService#insert(com.zfsoft.workflow.model.SpBusiness)
	 */
	
	@Override
	public void insert(SpBusiness spBusiness) throws WorkFlowException {
		try {
			if(spBusiness != null && StringUtil.isNotEmpty(spBusiness.getRelDetail())){
				spBusinessDao.deleteByRelDetail(spBusiness.getRelDetail());
				spBusinessDao.insert(spBusiness);
			}else{
				throw new WorkFlowException("SpBusiness Object is null");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public void update(SpBusiness spBusiness, String[] calssIds, String[] calssPrivilege) throws WorkFlowException {
		try {
			if (calssIds != null) {
				String classPrivilegeStr = "";
				if (calssIds != null && calssIds.length > 0) {
					for (int i = 0; i < calssIds.length; i++) {
						classPrivilegeStr += calssIds[i] + "-" + calssPrivilege[i] + ",";
					}
				}
				spBusiness.setClassesPrivilege(StringUtil.removeLast(classPrivilegeStr));
			}
			spBusinessDao.update(spBusiness);
			if(StringUtil.isNotEmpty(spBusiness.getBillId())){
				SpProcedureBill spProcedureBill = new SpProcedureBill();
				spProcedureBill.setBillId(spBusiness.getBillId());
				spProcedureBill.setClassesPrivilege(spBusiness.getClassesPrivilege());
				spProcedureBill.setPid(spBusiness.getPid());
				spProcedureBillDao.update(spProcedureBill);
			}			
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(String bId) throws WorkFlowException {
		try {
			spBusinessDao.delete(bId);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	
	/* (non-Javadoc)
	 * @see com.zfsoft.workflow.service.ISpBusinessService#deleteByRelDetail(java.lang.String)
	 */
	
	@Override
	public void deleteByRelDetail(String relDetail) throws WorkFlowException {
		try {
			spBusinessDao.deleteByRelDetail(relDetail);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public List<SpBusiness> findSpBusiness(SpBusiness spBusiness) throws WorkFlowException {
		try {
			if (spBusiness != null) {
				return spBusinessDao.findSpBusiness(spBusiness);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public SpBusiness findSpBusinessById(String bid) {
		return spBusinessDao.findSpBusinessById(bid).get(0);
	}

	@Override
	public SpBusiness findSpBusinessByIdAndBType(String bid, String bType) {
		SpBusiness spbusiness = null;
		List<SpBusiness> list = spBusinessDao.findSpBusinessById(bid);
		if(list != null && list.size() > 0){
			for(SpBusiness sb:list){
				if(StringUtil.isNotEmpty(sb.getBillType())){ 
					if(sb.getBillType().equals(bType)){
						spbusiness = sb;
						break;
					}
				}else{
					spbusiness = sb;
					break;
				}
			}
		}		
		return spbusiness;
	}
	
	@Override
	public SpBusiness findSpBusinessByBcode(String bcode, String workId) {
		SpBusiness sb = new SpBusiness();
		List<SpBusiness> list = null;
		try {
			if(StringUtil.isEmpty(bcode) && StringUtil.isEmpty(workId)){
				throw new WorkFlowException("异常：业务编码和工作ID不能都为空！");
			}
			// 如果工作ID为空，则表单权限串到模板中取
			if(StringUtil.isNotEmpty(bcode) && StringUtil.isEmpty(workId)){
				sb.setBcode(bcode);
				list = spBusinessDao.findSpBusiness(sb);
			}
			// 如果工作ID不为空，则表单权限串到实例表中取
			if(StringUtil.isNotEmpty(bcode) && StringUtil.isNotEmpty(workId)){
				sb.setBcode(bcode);
				sb.setWorkId(workId);
				list = spBusinessDao.findSpWorkBusiness(sb);
				// 如果根据工作ID查找实例不存在，则取初始化模板中的信息
				if(list == null || list.size() <= 0){
					list = spBusinessDao.findSpBusiness(sb);
				}
			}
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public SpBusiness findSpBusinessByRelDetail(String relDetail, String workId) {
		SpBusiness result = null;
		String bcode = null;
		try {
			if (StringUtil.isNotEmpty(relDetail)) {
				bcode = BusinessEnum.SH_GRXX.getKey() + "_" + relDetail;
				result = this.findSpBusinessByBcode(bcode, workId);
			}
			if (result == null) {
				throw new WorkFlowException("【个人信息审核业务】：不存在记录（BCODE:" + bcode
						+ ";WORKID:" + workId + "）");
			} else {
				return result;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}
	
	//分页业务流程配置列表显示
	@Override
	public PageList<SpBusiness> getPagingBusiness(SpBusinessQuery query)
			throws WorkFlowException {
		PageList<SpBusiness> pageList = new PageList<SpBusiness>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(spBusinessDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<SpBusiness> list = spBusinessDao.getPagingList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}
}
