package com.zfsoft.hrm.baseinfo.search.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.config.IConstants;

/**
 * 条件实体
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-13
 * @version V1.0.0
 */
public class Condition implements Serializable {
	
	private static final long serialVersionUID = -8036687764154201001L;

	private String guid;				//全局ID
	
	private String title;				//标题
	
	private String express;				//表达式

	private String text;				//描述信息
	
	private String parentId;			//条件系列ID
	
	private int index;					//序号
	
	private String type;					//条件类型,学生或老师
	
	private List<Condition> children = new ArrayList<Condition>();	//条件集合
	
	private Properties result = new Properties(); //条件查询结果
	
	/**
	 * 返回全局ID
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * 设置全局ID
	 * @param guid 全局ID
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * 返回标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题
	 * @param title 标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 返回表达式合集，如果有子节点，联结子节点表达式返回之，没有则返回自身express
	 * @return
	 */
	public String getCondition(){
		if(children.size()>0){
//			if(children.size()==0||!StringUtils.isEmpty(getExpress())){
//				return getExpress();
//			}
			StringBuffer sb = new StringBuffer();
			for(Condition con : children){
				if(!StringUtils.isEmpty(con.getCondition())){
					if(sb.length()==0){
						sb.append("(");
					}
					if(sb.length()>1){
						sb.append(" or ");
					}
					sb.append(con.getCondition());
				}
			}
			if(sb.length()>0){
				sb.append(")");
			}
			return sb.toString();
		}else{
			return getExpress();
		}
	}

	/**
	 * 返回表达式
	 */
	public String getExpress() {
		return express;
	}

	/**
	 * 设置表达式
	 * @param expression 表达式
	 */
	public void setExpress(String express) {
		this.express = express;
	}

	/**
	 * 返回描述信息
	 */
	public String getText() {
		return text;
	}

	/**
	 * 设置描述信息
	 * @param text 描述信息
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 返回序号
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * 设置序号
	 * @param index 序号
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * 返回条件系列ID
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * 设置条件系列ID
	 * @param parentId 条件系列ID
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}



	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 返回条件集合
	 */
	public List<Condition> getChildren() {
		return children;
	}

	/**
	 * 设置条件集合
	 * @param children 条件集合
	 */
	public void setChildren(List<Condition> children) {
		this.children = children;
	}

	/**
	 * 
	 * 返回查询结果
	 */
	public Properties getResult() {
		return result;
	}

	/**
	 * 设置查询结果
	 * @param result 查询结果
	 */
	public void setResult(Properties result) {
		this.result = result;
	}
	
	public boolean getHasChild(){
		if(getChildren().size()>0)
			return true;
		else
			return false;
	}
}
