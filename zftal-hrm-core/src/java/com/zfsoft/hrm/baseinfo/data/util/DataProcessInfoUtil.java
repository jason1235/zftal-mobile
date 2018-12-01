package com.zfsoft.hrm.baseinfo.data.util;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.hrm.baseinfo.data.entity.ImportProcessStep;
import com.zfsoft.hrm.baseinfo.data.entity.InfoEntity;
import com.zfsoft.hrm.baseinfo.data.entity.InfoType;

/** 
 * @author jinjj
 * @date 2012-10-23 上午10:21:04 
 *  
 */
public class DataProcessInfoUtil {

	private static final String DATA_PROCESS_INFO = "DATA_PROCESS_INFO";
	private static final String DATA_PROCESS_STEP = "DATA_PROCESS_STEP";
	private static Logger log = LoggerFactory.getLogger(DataProcessInfoUtil.class);
	
	@SuppressWarnings("unchecked")
	public static String getInfo(){
		Queue<InfoEntity> queue = (Queue<InfoEntity>)SessionFactory.getSession().getAttribute(DATA_PROCESS_INFO);
		if(queue == null){
			return null;
		}else{
			InfoEntity entity = queue.poll();
			if(entity == null){
				return null;
			}else{
				return entity.getFomatInfo();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public synchronized static void setInfo(String msg, InfoType infoType){
		if(infoType == null){
			infoType = InfoType.INFO;
		}
		Queue<InfoEntity> queue = (Queue<InfoEntity>)SessionFactory.getSession().getAttribute(DATA_PROCESS_INFO);
		if(queue == null){
			queue = new LinkedList<InfoEntity>();
			SessionFactory.getSession().setAttribute(DATA_PROCESS_INFO, queue);
		}
		InfoEntity info = new InfoEntity();
		info.setCreateTime(new Date());
		info.setInfo(msg);
		info.setInfoType(infoType);
		queue.add(info);
		log.debug("数据上传信息："+msg);
	}
	
	@SuppressWarnings("unchecked")
	public static void clear(){
		Queue<InfoEntity> queue = (Queue<InfoEntity>)SessionFactory.getSession().getAttribute(DATA_PROCESS_INFO);
		if(queue == null){
			queue = new LinkedList<InfoEntity>();
			SessionFactory.getSession().setAttribute(DATA_PROCESS_INFO, queue);
		}else{
			queue.clear();
		}
		SessionFactory.getSession().setAttribute(DATA_PROCESS_STEP, null);
	}
	
	public synchronized static void setStep(String msg, int step, int total){
		ImportProcessStep pStep = (ImportProcessStep)SessionFactory.getSession().getAttribute(DATA_PROCESS_STEP);
		if(pStep == null){
			pStep = new ImportProcessStep();
			SessionFactory.getSession().setAttribute(DATA_PROCESS_STEP, pStep);
		}
		pStep.setMsg(msg);
		pStep.setStep(step);
		pStep.setTotal(total);
	}
	
	public static ImportProcessStep getStep(){
		ImportProcessStep pStep = (ImportProcessStep)SessionFactory.getSession().getAttribute(DATA_PROCESS_STEP);
		return pStep;
	}
}
