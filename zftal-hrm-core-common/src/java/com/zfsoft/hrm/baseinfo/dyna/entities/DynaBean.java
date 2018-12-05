package com.zfsoft.hrm.baseinfo.dyna.entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.CLOB;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.dyna.html.ParseFactory;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;

/** 
 * 动态类实体
 * @ClassName: DynaBean 
 * @author jinjj
 * @date 2012-6-20 上午10:20:25 
 *  
 */
public class DynaBean implements Serializable {
	
	private static final long serialVersionUID = -7994224807326239960L;
	
	private int index;
	
	private boolean cacheNew;						//最新false未保存缓存true已保存缓存

	private InfoClass clazz;					//动态类对象描述
	
	private Map<String, Object> values;			//动态类对象附属数据
	
	private Map<String, String> editHtml = new HashMap<String, String>();	//编辑HTML文本
	
	private Map<String, String> viewHtml = new HashMap<String, String>();	//显示HTML文本
	
	/**
	 * 构造函数
	 * @param clazz 动态类对象描述
	 */
	public DynaBean(InfoClass clazz) {
		this.clazz = clazz;
		this.values = new HashMap<String, Object>();
		
		for( InfoProperty prop : clazz.getProperties() ){
			//values.put(prop.getFieldName(), prop.getDefaultValue());
			viewHtml.put(prop.getFieldName(), getViewText(prop.getFieldName()));
			
			if( prop.getEditable() ) {
				editHtml.put( prop.getFieldName(), getEditText(prop.getFieldName()) );
			} else {
				editHtml.put( prop.getFieldName(), getViewText(prop.getFieldName()) );
			}
		}
	}
	
	/**
	 * 获取指定属性的值
	 * @param pName 属性名称
	 * @return 属性值
	 */
	public Object getValue( String pName ) {
		if(values.get( pName ) == null){
			return "";
		}
		return values.get( pName );

	}
	public String getValueString( String pName ) {
		if(values.get( pName )==null){
			return "";
		}
		return values.get( pName ).toString();

	}
	
	/**
	 * 设置属性值
	 * @param pName 属性名称
	 * @param value 属性值
	 */
	public void setValue( String pName, Object value ) {
		
		if(value instanceof CLOB){
			try {
				//System.out.println("aaa:"+CLOBToString(((CLOB)value)));
				values.put( StringUtils.lowerCase(pName), CLOBToString(((CLOB)value)) );
				String clobStr = CLOBToString(((CLOB)value));
				viewHtml.put( StringUtils.lowerCase(pName), StringUtils.isEmpty(clobStr) ? "无内容" : "有内容");
				return;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			if( !has( pName ) ) {
				values.put( StringUtils.lowerCase(pName), value );
				return;
			}
			
			//数据库返回的字段名称一律为大小，需将其大小写转换成InfoProper描述的fieldName一致
			pName = clazz.getPropertyByName(pName).getFieldName();
			
			//对value进行类型转换
			value = ParseFactory.ValueParse(clazz.getPropertyByName(pName), value);
			values.put(pName, value);	
			
			viewHtml.put( pName, getViewText( pName ) );
			
			if( clazz.getPropertyByName( pName ).getEditable() ) {
				editHtml.put( pName, getEditText( pName ) );
			} else {
				editHtml.put( pName, getViewText(pName) );
			}
		}
		
	}
	
public String CLOBToString(CLOB CLOB) throws SQLException, IOException {   
        
        String reString = "";   
        java.io.Reader is = CLOB.getCharacterStream();// 得到流   
        BufferedReader br = new BufferedReader(is);   
        String s = br.readLine();   
        StringBuffer sb = new StringBuffer();   
        while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING   
            sb.append(s);   
            s = br.readLine();   
        }   
        reString = sb.toString();   
        return reString;   
    }   
	
	/**
	 * 该动态实体是否含有指定的属性（忽略大小写）
	 * @param pName 属性名称
	 * @return
	 */
	public boolean has(String pName) {
//		InfoClass clazz = (InfoClass) InfoClassCache.instance().getInfoClass( this.clazz.getGuid() );

		if( clazz.getPropertyByName( pName ) == null ) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 获取编辑页面文本内容（用于编辑页面显示的HTML文本）
	 * @param pName 属性名
	 * @return 用于编辑页面显示的HTML文本
	 */
	private String getEditText( String pName ) {
		InfoProperty property = clazz.getPropertyByName( pName );
		Object value = values.get( pName );
		
		return ParseFactory.EditParse( property, value);
	}
	
	/**
	 * 获取用于显示的文本内容
	 * @param pName 属性名
	 * @return 用于显示的文本内容
	 */
	private String getViewText( String pName ) {
		InfoProperty property = clazz.getPropertyByName( pName );
		Object value = values.get( pName );
		if(value == null){
			value ="";
		}
		return ParseFactory.ViewParse(property, value);
	}
	
	//Getter and Stter Methods
	
	/**
	 * 动态类对象描述
	 * @return
	 */
	public InfoClass getClazz() {
		return clazz;
	}

	/**
	 * 动态类对象描述
	 * @param clazz 设置动态类对象描述
	 */
	public void setClazz(InfoClass clazz) {
		this.clazz = clazz;
	}

	/**
	 * 动态类对象附属数据
	 * @return
	 */
	public Map<String, Object> getValues() {
		return values;
	}

	/**
	 * 动态类对象附属数据
	 * @param values 动态类对象附属数据
	 */
	public void setValues(Map<String, Object> values) {
		if(values != null){
			for(String  key : values.keySet()){
				setValue( key, values.get(key) );
			}
		}else{
			this.values = values;
		}
	}

	/**
	 * 可编辑属性列表
	 * @return
	 */
	public List<InfoProperty> getEditables() {
		
		return getClazz().getEditables();
	}
	
	public void setEditables(List<InfoProperty> editables) {
		
		getClazz().setEditables(editables);
	}

	/**
	 * 可显示属性列表
	 * @return
	 */
	public List<InfoProperty> getViewables() {
		
		return  clazz.getViewables();
	}

	/**
	 * 返回编辑页面HTML对象
	 */
	public Map<String, String> getEditHtml() {
		return editHtml;
	}

	/**
	 * 返回显示页面HTML对象
	 */
	public Map<String, String> getViewHtml() {
		return viewHtml;
	}
	
	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * 是否多条数据
	 */
	public boolean isMoreThanOne(){
		return this.clazz.isMoreThanOne();
	}

	public boolean isCacheNew() {
		return cacheNew;
	}

	public void setCacheNew(boolean cacheNew) {
		this.cacheNew = cacheNew;
	}

}
