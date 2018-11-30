package com.zfsoft.mobile.servlet.service.oaHttp;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.common.Config;
import com.google.gson.Gson;
import com.util.WebServiceUtil;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.mobile.servlet.entity.TodoTaskEntity;
import com.zfsoft.mobile.servlet.entity.TodoTaskItemEntity;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;

public class OAWebserviceHttp {

	private static Logger logger = Logger.getLogger(OAWebserviceHttp.class);
	private final String infromation=Config.getString("mobile.infromation");
	
	public String getTodoTaskList(String username, String start, String size,
			String sign,String apptoken){
		Gson gson = new Gson();
		TodoTaskEntity todoTaskEntity = new TodoTaskEntity();
		List<TodoTaskItemEntity> todoTaskList = new ArrayList<TodoTaskItemEntity>();
		
		try {
			if(!ApptokenUtils.compare(username, apptoken)){
				 ResultEntity result = new ResultEntity<TodoTaskEntity>(2, "app_token error!", todoTaskEntity);
				 return gson.toJson(result); 
			}
			username  		= CodeUtil.decode(username, apptoken);
			start  			= CodeUtil.decode(start, apptoken);
			size  			= CodeUtil.decode(size, apptoken);
			sign  			= CodeUtil.decode(sign, apptoken);
			if(infromation.equals("0")){
				logger.error("调用getTodoTaskList待办事宜列表："+"username="+username+",start="+start+",size="+size+",sign="+sign);
			}
			
			String xml=null;
			xml=WebServiceUtil.createServiceOa().getTodoTaskList(username,
					Integer.valueOf(start), Integer.valueOf(size), sign);
			if(infromation.equals("0")){
				logger.error("调用getTodoTaskList待办事宜列表返回为："+xml);
			}
			
			SAXReader reader = new SAXReader();
	        ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes());
	        Document doc = reader.read(stream);
	        Element root = doc.getRootElement();
	        TodoTaskItemEntity todoTaskItemEntity;
		    for (Iterator<?> i =root.elementIterator("task");i.hasNext();){
		    	Element foo = (Element) i.next();
		    	todoTaskItemEntity= new TodoTaskItemEntity(
		    				foo.elementText("id").toString(),
		    				foo.elementText("title").toString(),
		    				foo.elementText("time").toString(),
		    				foo.elementText("qcr").toString(),
		    				foo.elementText("lx").toString(),
		    				foo.elementText("tablename").toString()
		    			);
		    	todoTaskList.add(todoTaskItemEntity);
	        }
	        for (Iterator<?> i = root.elementIterator("page");i.hasNext();){
	            Element foo = (Element) i.next();
	            todoTaskEntity.setSum(Integer.parseInt(foo.elementText("sum").toString()));
	        }
	        todoTaskEntity.setTodoTaskList(todoTaskList);
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		ResultEntity result = new ResultEntity<TodoTaskEntity>(1, "成功", todoTaskEntity);
		return gson.toJson(result); 
	}
	
	public String getDoneTaskList(String username, String start, String size,
			String sign,String apptoken){
		Gson gson = new Gson();
		TodoTaskEntity todoTaskEntity = new TodoTaskEntity();
		List<TodoTaskItemEntity> todoTaskList = new ArrayList<TodoTaskItemEntity>();
		
		try {
			if(!ApptokenUtils.compare(username, apptoken)){
				 ResultEntity result = new ResultEntity<TodoTaskEntity>(2, "app_token error!", todoTaskEntity);
				 return gson.toJson(result); 
			}
			username  		= CodeUtil.decode(username, apptoken);
			start  			= CodeUtil.decode(start, apptoken);
			size  			= CodeUtil.decode(size, apptoken);
			sign  			= CodeUtil.decode(sign, apptoken);
			if(infromation.equals("0")){
				logger.error("调用getDoneTaskList获取已办事宜列表："+"username="+username+",start="+start+",size="+size+",sign="+sign);
			}
			
			String xml=null;
			xml=WebServiceUtil.createServiceOa().getDoneTaskList(username,
					Integer.valueOf(start), Integer.valueOf(size), sign);
			if(infromation.equals("0")){
				logger.error("调用getDoneTaskList获取已办事宜列表返回为："+xml);
			}
			
			SAXReader reader = new SAXReader();
	        ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes());
	        Document doc = reader.read(stream);
	        Element root = doc.getRootElement();
	        TodoTaskItemEntity todoTaskItemEntity;
		    for (Iterator<?> i =root.elementIterator("task");i.hasNext();){
		    	Element foo = (Element) i.next();
		    	todoTaskItemEntity= new TodoTaskItemEntity(
		    				foo.elementText("id").toString(),
		    				foo.elementText("title").toString(),
		    				foo.elementText("time").toString(),
		    				foo.elementText("qcr").toString(),
		    				foo.elementText("lx").toString(),
		    				foo.elementText("tablename").toString()
		    			);
		    	todoTaskList.add(todoTaskItemEntity);
	        }
	        for (Iterator<?> i = root.elementIterator("page");i.hasNext();){
	            Element foo = (Element) i.next();
	            todoTaskEntity.setSum(Integer.parseInt(foo.elementText("sum").toString()));
	        }
	        todoTaskEntity.setTodoTaskList(todoTaskList);
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		ResultEntity result = new ResultEntity<TodoTaskEntity>(1, "成功", todoTaskEntity);
		return gson.toJson(result); 
	}
	
	
	public String getSoluteTaskList(String username, String start, String size,
			String sign,String apptoken){
		Gson gson = new Gson();
		TodoTaskEntity todoTaskEntity = new TodoTaskEntity();
		List<TodoTaskItemEntity> todoTaskList = new ArrayList<TodoTaskItemEntity>();
		
		try {
			if(!ApptokenUtils.compare(username, apptoken)){
				 ResultEntity result = new ResultEntity<TodoTaskEntity>(2, "app_token error!", todoTaskEntity);
				 return gson.toJson(result); 
			}
			username  		= CodeUtil.decode(username, apptoken);
			start  			= CodeUtil.decode(start, apptoken);
			size  			= CodeUtil.decode(size, apptoken);
			sign  			= CodeUtil.decode(sign, apptoken);
			if(infromation.equals("0")){
				logger.error("调用getSoluteTaskList获取办结事宜列表："+"username="+username+",start="+start+",size="+size+",sign="+sign);
			}
			
			String xml=null;
			xml=WebServiceUtil.createServiceOa().getSoluteTaskList(username,
					Integer.valueOf(start), Integer.valueOf(size), sign);
			if(infromation.equals("0")){
				logger.error("调用getSoluteTaskList获取办结事宜列表返回为："+xml);
			}
			
			SAXReader reader = new SAXReader();
	        ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes());
	        Document doc = reader.read(stream);
	        Element root = doc.getRootElement();
	        TodoTaskItemEntity todoTaskItemEntity;
		    for (Iterator<?> i =root.elementIterator("task");i.hasNext();){
		    	Element foo = (Element) i.next();
		    	todoTaskItemEntity= new TodoTaskItemEntity(
		    				foo.elementText("id").toString(),
		    				foo.elementText("title").toString(),
		    				foo.elementText("time").toString(),
		    				foo.elementText("qcr").toString(),
		    				foo.elementText("lx").toString(),
		    				foo.elementText("tablename").toString()
		    			);
		    	todoTaskList.add(todoTaskItemEntity);
	        }
	        for (Iterator<?> i = root.elementIterator("page");i.hasNext();){
	            Element foo = (Element) i.next();
	            todoTaskEntity.setSum(Integer.parseInt(foo.elementText("sum").toString()));
	        }
	        todoTaskEntity.setTodoTaskList(todoTaskList);
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		ResultEntity result = new ResultEntity<TodoTaskEntity>(1, "成功", todoTaskEntity);
		return gson.toJson(result); 
	}
	
	
	
}
