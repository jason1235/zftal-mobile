package com.zfsoft.workflow.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.dybill.enums.BillType;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.workflow.dao.ISpProcedureBillDao;
import com.zfsoft.workflow.dao.ISpProcedureDao;
import com.zfsoft.workflow.enumobject.NodeTypeEnum;
import com.zfsoft.workflow.exception.DaoException;
import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpLine;
import com.zfsoft.workflow.model.SpNode;
import com.zfsoft.workflow.model.SpProcedure;
import com.zfsoft.workflow.model.SpProcedureBill;
import com.zfsoft.workflow.model.query.SpProcedureQuery;
import com.zfsoft.workflow.service.ISpLineService;
import com.zfsoft.workflow.service.ISpNodeService;
import com.zfsoft.workflow.service.ISpProcedureService;

/**
 * 流程管理service接口实现类
 * 
 * @version 3.2.0
 */
public class SpProcedureServiceImpl extends BaseInterfaceServiceImpl implements ISpProcedureService {
	/* @model: 注入SpProcedure */
	public ISpProcedureDao spProcedureDao;
	public ISpProcedureBillDao spProcedureBillDao;
	public ISpNodeService spNodeService;
	public ISpLineService spLineService;

	/**
	 * @param spNodeService
	 *            : set the property spNodeService.
	 */

	public void setSpNodeService(ISpNodeService spNodeService) {
		this.spNodeService = spNodeService;
	}

	/**
	 * @param spLineService
	 *            : set the property spLineService.
	 */

	public void setSpLineService(ISpLineService spLineService) {
		this.spLineService = spLineService;
	}

	public void setSpProcedureDao(ISpProcedureDao spProcedureDao) {
		this.spProcedureDao = spProcedureDao;
	}

	/**
	 * @param spProcedureBillDao
	 *            : set the property spProcedureBillDao.
	 */

	public void setSpProcedureBillDao(ISpProcedureBillDao spProcedureBillDao) {
		this.spProcedureBillDao = spProcedureBillDao;
	}

	@Override
	public void insert(SpProcedure spProcedure, String[] commitBillIds, String[] approveBillIds)
			throws WorkFlowException {
		try {
			int result = spProcedureDao.getCountByProcedureNameAndPtype(spProcedure);
			if (result > 0) {
				throw new WorkFlowException("异常：相同的流程名称和业务类型记录已经存在，不能执行新增操作！");
			} else {
				// 新增流程
				spProcedureDao.insert(spProcedure);
				// 新增表单
				this.addBill(spProcedure, commitBillIds, approveBillIds);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public void update(SpProcedure spProcedure, String[] commitBillIds, String[] approveBillIds)
			throws WorkFlowException {
		try {
			// 修改流程
			spProcedureDao.update(spProcedure);
			// 新增表单
			this.addBill(spProcedure, commitBillIds, approveBillIds);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * 私有方法描述：新增流程和表单关联关系
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-5-2 上午10:33:56
	 */
	private void addBill(SpProcedure spProcedure, String[] commitBillIds, String[] approveBillIds) {
		// 删除所有表单关联
		spProcedureBillDao.deleteByPId(spProcedure.getPid());
		// 新增提交类型表单
		if (commitBillIds != null && commitBillIds.length > 0) {
			SpProcedureBill spb = new SpProcedureBill();
			for (String cBillId : commitBillIds) {
				if(StringUtil.isNotEmpty(cBillId)){
					spb.setPid(spProcedure.getPid());
					spb.setBillId(cBillId);
					spb.setBillType(BillType.COMMIT.toString());
					spProcedureBillDao.insert(spb);
				}	
			}
		}
		// 新增审核类型表单
		if (approveBillIds != null && approveBillIds.length > 0) {
			SpProcedureBill spb = new SpProcedureBill();
			for (String aBillId : approveBillIds) {
				if(StringUtil.isNotEmpty(aBillId)){
					spb.setPid(spProcedure.getPid());
					spb.setBillId(aBillId);
					spb.setBillType(BillType.APPROVE.toString());
					spProcedureBillDao.insert(spb);
				}	
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zfsoft.workflow.service.ISpProcedureService#update(com.zfsoft.workflow
	 * .model.SpProcedure)
	 */

	@Override
	public void update(SpProcedure spProcedure) throws WorkFlowException {
		try {
			// 修改流程
			spProcedureDao.update(spProcedure);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(String pId) throws WorkFlowException {
		try {
			spProcedureDao.deleteTaskByPid(pId);// 删除流程节点下所有任务关联
			spNodeService.deleteByPid(pId);// 删除流程所有节点
			spProcedureBillDao.deleteByPId(pId);// 删除所有表单关联
			spProcedureDao.delete(pId);// 删除流程
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public List<SpProcedure> findSpProcedureList(SpProcedure spProcedure) throws WorkFlowException {
		try {
			List<SpProcedure> pList = spProcedureDao.findSpProcedureList(spProcedure);
			return pList;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}
	
    //流程配置分页显示
	@Override
	public PageList<SpProcedure> getPagedSpProcedureList(SpProcedureQuery query)
			throws WorkFlowException {
		PageList<SpProcedure> pageList = new PageList<SpProcedure>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(spProcedureDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<SpProcedure> list = spProcedureDao.getPagingList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public SpProcedure findSpProcedureByBCode(String bCode) throws DaoException {
		SpProcedure spProcedure = null;
		try {
			spProcedure = this.putNodeToProcedure(spProcedureDao.findSpProcedureByBCode(bCode),false);// 获取流程对象
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return spProcedure;
	}

	@Override
	public SpProcedure findSpProcedureByPid(String pid, boolean sequence) throws DaoException {
		SpProcedure sp = spProcedureDao.findSpProcedureById(pid);
		SpProcedure spProcedure = this.putNodeToProcedure(sp,sequence);
		return spProcedure;
	}

	/*
	 * 
	 * 方法描述：按顺序封装流程节点和连线的私有类
	 * 
	 * @param:
	 * 
	 * @return:
	 * 
	 * @version: 1.0
	 * 
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * 
	 * @since: 2013-3-17 下午03:08:01
	 */
	private SpProcedure putNodeToProcedure(SpProcedure spProcedure, boolean sequence) throws WorkFlowException {
//		SpNode node = new SpNode();
		if(spProcedure != null){
//			node.setPid(spProcedure.getPid());
//			node.setNodeType(NodeTypeEnum.START_NODE.getKey());
			List<SpNode> nodeList = new ArrayList<SpNode>();
			int step = 0;
			for (SpNode spNode : spProcedure.getSpNodeList()) {
				if(NodeTypeEnum.START_NODE.getKey().equals(spNode.getNodeType())){
					spNode.setStep(step);
					nodeList.add(spNode);
				}
			}
//			 获取节点有排序的流程信息
			if(sequence){
				if (nodeList != null && nodeList.size() > 0) {
					// 流程中的节点集合
					List<SpNode> spNodeList = new ArrayList<SpNode>();
					spNodeList.addAll(nodeList);// 装在开始节点对象
					// 根据开始节点查询连线
					List<SpNode> nextNodes = nodeList;
					List<SpLine> lineList = new ArrayList<SpLine>();
					lineList.addAll(spProcedure.getSpLineList());
					boolean condition = true;
					step++;
					do {
						List<SpNode> subNodes = new ArrayList<SpNode>();
						for (SpNode nextNode : nextNodes) {
							for (int i = lineList.size()-1; i >= 0; i--) {
								SpLine line = lineList.get(i);
								if(nextNode.getNodeId().equals(line.getUnodeId())){
									lineList.remove(i);
									SpNode n= spProcedure.getSpNodeMap().get(line.getDnodeId());
									if(n!=null){
										//已经存在的节点则不再重复添加
										//（避免多节点汇总到一个节点上的时候汇总节点被重复添加）
										if(!subNodes.contains(n)){
											n.setStep(step);
											subNodes.add(n);
										}
										//如果该节点为结束节点，则可以判断流程已经结束 此次循环结束后不再查询
										if(NodeTypeEnum.END_NODE.getKey().equalsIgnoreCase(n.getNodeType())){
											condition = false;
										}
									}
								}
							}
							if(subNodes.size()==0&&condition){
								if (nextNode.getNodeType().equals(NodeTypeEnum.START_NODE.getKey())) {
									condition = false;
								} else {
									throw new WorkFlowException("异常：不完整的流程定义，其中一个可能的原因是节点无法正常联通或没有结束节点！" + "\n流程编号："
											+ spProcedure.getPid() + "，流程名称：" + spProcedure.getPname() + "中断节点编号："
											+ nextNode.getNodeId() + "，节点名称：" + nextNode.getNodeName());
								}
							}
							step++;
						}
						nextNodes = subNodes;
						spNodeList.addAll(subNodes);
					} while (condition);
					spProcedure.setSpNodeList(spNodeList);
				}
			}		
		}
		return spProcedure;
	}
}
