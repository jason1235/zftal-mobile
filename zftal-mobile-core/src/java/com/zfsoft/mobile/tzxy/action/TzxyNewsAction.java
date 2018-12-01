package com.zfsoft.mobile.tzxy.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.tzxy.entity.OANewsEntity;
import com.zfsoft.mobile.tzxy.util.JdbcUtil;
import com.zfsoft.util.base.StringUtil;

/**
 * 台州学院oa新闻个性化开发
 * @author liucb
 * @date   2018-01-19
 *
 */
public class TzxyNewsAction extends HrmAction{
	private static Logger logger = Logger.getLogger(TzxyNewsAction.class);
	
	/**
	 * 跳转到新闻列表页面
	 * @return
	 */
	public String toList(){
		return "toList";
	}
	
	/**
	 * oa新闻列表
	 */
	public void list(){
	    JdbcUtil jdbcUtil = new JdbcUtil();  
		jdbcUtil.getConnection();  
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		Gson gson = new Gson();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			
			String rownum = request.getParameter("rownum");
			if(rownum==null||"".equals(rownum)){
				rownum = "10";
			}
			String title = request.getParameter("title");
			
			/*StringBuffer sql = new StringBuffer("select t1.* from(select t.id,t.title,t.time from news_tzxy t where 1=1 ");
			if(!StringUtil.isEmpty(title)){
				sql.append("and t.title like '%"+title+"%'");
			}
			sql.append("order by t.time desc) t1 where rownum < "+Long.valueOf(rownum));*/
			StringBuffer sql = new StringBuffer("select t1.* from(");
			sql.append("SELECT t.id,t.title,t.time "+
				  "FROM news_tzxy t "+
				 "WHERE id IN (SELECT id "+
				                "FROM (SELECT id "+
				                        "FROM (SELECT title, max(id) id "+
				                                "FROM news_tzxy "+
				                               "GROUP BY title "+
				                               "ORDER BY id DESC) "+
				                       "WHERE rownum < 7 "+
				                       "ORDER BY id DESC)) ");
			if(!StringUtil.isEmpty(title)){
				sql.append("AND t.title LIKE '%"+title+"%' ");
			}
			sql.append("ORDER BY t.time DESC) t1 WHERE rownum < "+Long.valueOf(rownum));
			System.out.println(sql.toString());
		    List<Map<String, Object>> result = jdbcUtil.findResult(sql.toString(), null);  
		    if(result==null){
		    	result = new ArrayList<Map<String,Object>>();
		    }
		    String resultStr = gson.toJson(result);
		    out.write(resultStr);
		    out.flush();
	        out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {  
		    jdbcUtil.releaseConn();  
		}  
	}
	
	/**
	 * 跳转到通知详情页面
	 * @return
	 */
	public String toNewsDetail(){
		JdbcUtil jdbcUtil = new JdbcUtil();  
		jdbcUtil.getConnection();  
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		Gson gson = new Gson();
		PrintWriter out = null;
		try {
			String newsId = request.getParameter("newsId");
			String sql = "select t.id,t.title,t.content,t.time from NEWS_TZXY t where 1=1 and t.id=? order by t.time desc";
		    OANewsEntity result = jdbcUtil.getNewsDetail(sql, newsId);  
		    if(result==null){
		    	result = new OANewsEntity();
		    }
		   getValueStack().set("result",result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {  
		    jdbcUtil.releaseConn();  
		}  
		return "newsDetail";
	}
	
	/**
	 * 获取oa新闻详情
	 */
	public void getNewsDetail(){
		JdbcUtil jdbcUtil = new JdbcUtil();  
		jdbcUtil.getConnection();  
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		Gson gson = new Gson();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String newsId = request.getParameter("newsId");
			String sql = "select t.id,t.title,t.content,t.time from NEWS_TZXY t where 1=1 and t.id=? order by t.time desc";
		    OANewsEntity result = jdbcUtil.getNewsDetail(sql, newsId);  
		    if(result==null){
		    	result = new OANewsEntity();
		    }
		    String resultStr = gson.toJson(result);
		    out.write(resultStr);
		    out.flush();
	        out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {  
		    jdbcUtil.releaseConn();  
		}  
	}
}
