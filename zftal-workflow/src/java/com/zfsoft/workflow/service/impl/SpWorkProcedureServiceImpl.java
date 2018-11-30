package com.zfsoft.workflow.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zfsoft.util.base.StringUtil;
import com.zfsoft.workflow.dao.ISpWorkLineDao;
import com.zfsoft.workflow.dao.ISpWorkNodeDao;
import com.zfsoft.workflow.dao.ISpWorkProcedureDao;
import com.zfsoft.workflow.dao.ISpWorkTaskDao;
import com.zfsoft.workflow.enumobject.NodeTypeEnum;
import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpLine;
import com.zfsoft.workflow.model.SpWorkLine;
import com.zfsoft.workflow.model.SpWorkNode;
import com.zfsoft.workflow.model.SpWorkProcedure;
import com.zfsoft.workflow.service.ISpWorkProcedureService;

/**
 * 工作审核节点连线管理接口实现类
 * 
 * @version 3.2.0
 */
public class SpWorkProcedureServiceImpl extends BaseInterfaceServiceImpl implements ISpWorkProcedureService {
	/* @model: 注入SpLine */
	public ISpWorkProcedureDao spWorkProcedureDao;
	public ISpWorkLineDao spWorkLineDao;
	public ISpWorkNodeDao spWorkNodeDao;
	public ISpWorkTaskDao spWorkTaskDao;

	public void setSpWorkProcedureDao(ISpWorkProcedureDao spWorkProcedureDao) {
		this.spWorkProcedureDao = spWorkProcedureDao;
	}

	/**
	 * @param spWorkLineDao
	 *            : set the property spWorkLineDao.
	 */

	public void setSpWorkLineDao(ISpWorkLineDao spWorkLineDao) {
		this.spWorkLineDao = spWorkLineDao;
	}

	/**
	 * @param spWorkNodeDao
	 *            : set the property spWorkNodeDao.
	 */

	public void setSpWorkNodeDao(ISpWorkNodeDao spWorkNodeDao) {
		this.spWorkNodeDao = spWorkNodeDao;
	}

	/**
	 * @param spWorkTaskDao
	 *            : set the property spWorkTaskDao.
	 */

	public void setSpWorkTaskDao(ISpWorkTaskDao spWorkTaskDao) {
		this.spWorkTaskDao = spWorkTaskDao;
	}

	@Override
	public void addSpWorkProcedure(SpWorkProcedure spWorkProcedure) throws WorkFlowException {
		try {
			spWorkProcedureDao.removeSpWorkProcedureByWid(spWorkProcedure.getWid());// 清除垃圾数据
			spWorkProcedureDao.addSpWorkProcedure(spWorkProcedure);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zfsoft.workflow.service.ISpWorkProcedureService#
	 * removeSpWorkProcedureByWid(java.lang.String)
	 */

	@Override
	public boolean removeSpWorkProcedureByWid(String wId) throws DataAccessException {
		try {
			if (StringUtil.isNotEmpty(wId)) {
				spWorkProcedureDao.removeSpWorkProcedureByWid(wId);
				spWorkLineDao.removeSpWorkLineByWId(wId);
				spWorkNodeDao.removeSpWorkNodeByWid(wId);
				spWorkTaskDao.removeSpWorkTaskByWid(wId);
				return true;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return false;
	}

	@Override
	public List<SpWorkProcedure> findWorkProcedureList(SpWorkProcedure spWorkProcedure) throws WorkFlowException {
		try {
			if (spWorkProcedure != null) {
				return spWorkProcedureDao.findWorkProcedureList(spWorkProcedure);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public SpWorkProcedure findWorkProcedureByWId(String wid) throws WorkFlowException {
		SpWorkProcedure spWorkProcedure = null;
		try {
			if (StringUtil.isEmpty(wid)) {
				throw new WorkFlowException("异常：工作ID为空！");
			}
			SpWorkProcedure workProcedure = new SpWorkProcedure();
			workProcedure.setWid(wid);
			List<SpWorkProcedure> list = spWorkProcedureDao.findWorkProcedureList(workProcedure);
			if (null != list && list.size() > 0) {
				spWorkProcedure = (SpWorkProcedure) list.get(0);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		spWorkProcedure = this.nodeSequence(spWorkProcedure, wid);
		return spWorkProcedure;
	}

	/**
	 * 
	 * 私有方法描述：工作流程节点排序
	 *
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-5-2 下午12:22:47
	 */
	private SpWorkProcedure nodeSequence(SpWorkProcedure spWorkProcedure, String wid) throws WorkFlowException {
		if(spWorkProcedure != null){
//			SpWorkNode spwn = new SpWorkNode();
//			spwn.setPid(spWorkProcedure.getPid());
//			spwn.setWid(wid);
//			spwn.setNodeType(NodeTypeEnum.START_NODE.getKey());
//			List<SpWorkNode> workNodeList = spWorkNodeDao.findWorkNodeList(spwn);
			List<SpWorkNode> workNodeList = new ArrayList<SpWorkNode>();
			
			Map<String,SpWorkNode> spNodeMap = new HashMap<String,SpWorkNode>();
			int step = 0;
			if (spWorkProcedure.getSpWorkNodeList() != null) {
				for (SpWorkNode spNode : spWorkProcedure.getSpWorkNodeList()) {
					spNodeMap.put(spNode.getNodeId(), spNode);
					if(NodeTypeEnum.START_NODE.getKey().equals(spNode.getNodeType())){
						spNode.setStep(step);
						workNodeList.add(spNode);
					}
				}
			}
			step++;
			if (workNodeList != null && workNodeList.size() > 0) {
				// 流程中的节点集合
				List<SpWorkNode> spWorkNodeList = new ArrayList<SpWorkNode>();
				spWorkNodeList.addAll(workNodeList);// 装载开始节点对象
				// 根据开始节点查询连线
				List<SpWorkNode> nextWorkNodes = workNodeList;
				List<SpWorkLine> lineList = new ArrayList<SpWorkLine>();
				lineList.addAll(spWorkProcedure.getSpWorkLineList());
				
				
				boolean condition = true;
				do {
					List<SpWorkNode> subNodes = new ArrayList<SpWorkNode>();
					for (SpWorkNode nextWorkNode : nextWorkNodes) {
						for (int i = lineList.size()-1; i >= 0; i--) {
							SpLine line = lineList.get(i);
							if(nextWorkNode.getNodeId().equals(line.getUnodeId())){
								lineList.remove(i);
								SpWorkNode n= spNodeMap.get(line.getDnodeId());
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
							if (nextWorkNode.getNodeType().equals(NodeTypeEnum.START_NODE.getKey())) {
								condition = false;
							} else {
								throw new WorkFlowException("异常：不完整的流程定义，其中一个可能的原因是节点无法正常联通或没有结束节点！" + "\n流程编号："
										+ spWorkProcedure.getPid() + "，流程名称：" + spWorkProcedure.getPname() + "中断节点编号："
										+ nextWorkNode.getNodeId() + "，节点名称：" + nextWorkNode.getNodeName());
							}
						}
						step++;
					}
					nextWorkNodes = subNodes;
					spWorkNodeList.addAll(subNodes);
				} while (condition);
				spWorkProcedure.setSpWorkNodeList(spWorkNodeList);
			}
		}
		return spWorkProcedure;
	}
}
