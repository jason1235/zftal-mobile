package com.zfsoft.dataprivilege.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dataprivilege.xentity.Context;
import com.zfsoft.dataprivilege.xentity.DataPrivilegeUrl;
import com.zfsoft.dataprivilege.xentity.Param;
import com.zfsoft.dataprivilege.xentity.Resource;
import com.zfsoft.dataprivilege.xentity.Resources;
/**
 * 资源文件工具
 * @author Patrick Shen
 */
public class ResourceUtil {
	
	public static Map<String,Context> contextMap;//存储过滤类型目录，主键过滤类型目录编号
	
	public static Map<String,Context> urlToContextMap;//存储过滤类型目录，主键是经过唯一性处理的url
	
	public static Resources resources;//资源策略文件缓存
	
	/**
	 * 获取资源文件，从固定位置conf/privilege/resources.xml
	 * @return
	 */
	public static Resources getResources() {
		if(resources!=null){
			return resources;
		}
		try {
			JAXBContext context = JAXBContext.newInstance(Resources.class);
			
			FileInputStream fr = new FileInputStream(Resources.class.getResource("/").toURI().getPath()+"conf/privilege/resources.xml");
	
			InputStreamReader inputStreamReader = new InputStreamReader(fr,"UTF-8");
	
			Unmarshaller um = context.createUnmarshaller();
	
			resources = (Resources) um.unmarshal(inputStreamReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return resources;
	}
	/**
	 * 获取过滤类型目录通过目录Id
	 * @param id
	 * @return
	 */
	public static Context getContextById(String id) {
		if(contextMap==null){
			contextMap=new HashMap<String, Context>();
			resources=getResources();
			List<Context> context=resources.getContext();
			for (Context c : context) {
				contextMap.put(c.getId(), c);
			}
		}
		
		return contextMap.get(id);
	}
	/**
	 * 获取过滤类型目录通过url
	 * @param url
	 * @return
	 */
	public static Context getContextByUrl(String url) {
		return getContextByUrl(getDataPrivilegeUrlByUrl(url));
	}
	/**
	 * 获取过滤类型目录通过dataPrivilegeUrl
	 * @param dataPrivilegeUrl
	 * @return
	 */
	public static Context getContextByUrl(DataPrivilegeUrl dataPrivilegeUrl) {
		String key=dataPrivilegeUrl.getPath();
		if(dataPrivilegeUrl.getUniqueparam()!=null){
			key+="?"+dataPrivilegeUrl.getUniqueparam().toString();
		}
		if(urlToContextMap==null){
			urlToContextMap=new HashMap<String, Context>();
			resources=getResources();
			List<Context> context=resources.getContext();
			for (Context c : context) {
				if(c.getUrl()==null){
					continue;
				}
				for (DataPrivilegeUrl privilegeUrl : c.getUrl()) {
					if(privilegeUrl.getUniqueparam()==null){
						urlToContextMap.put(privilegeUrl.getPath(), c);
					}else{
						urlToContextMap.put(privilegeUrl.getPath()+"?"+privilegeUrl.getUniqueparam().toString(), c);
					}
				}
			}
		}
		
		return urlToContextMap.get(key);
	}
	/**
	 * 取得url对应的DataPrivilegeUrl配置对象
	 * @param url
	 * @return
	 */
	public static DataPrivilegeUrl getDataPrivilegeUrlByUrl(String url) {
		resources=getResources();
		List<Context> context=resources.getContext();
		for (Context c : context) {
			if(c.getUrl()==null){
				continue;
			}
			for (DataPrivilegeUrl dataPrivilegeUrl : c.getUrl()) {
				
				boolean match=false;
				if(!dataPrivilegeUrl.getPath().equalsIgnoreCase(url)){
					continue;
				}else{
					match=true;
				}
				if(dataPrivilegeUrl.getUniqueparam()!=null){
					for (Param param : dataPrivilegeUrl.getUniqueparam()) {
						if(SessionFactory.getHttpRequest().getParameter(param.getName()).equals(param.getValue())){
							match=true;
						}else{
							match=false;
						}
					}
				}
				if(match){
					return dataPrivilegeUrl;
				}
			}
		}
		return null;
	}
		
	/**
	 * 获取url对应的资源
	 * @param url
	 * @param className
	 * @param methodName
	 * @return
	 */
	public static Resource getResourceById(String url, String className,
			String methodName) {
		
		DataPrivilegeUrl dataPrivilegeUrl=getDataPrivilegeUrlByUrl(url);
		
		if(dataPrivilegeUrl==null){
			return null;
		}
		
		if(dataPrivilegeUrl.getRes()!=null){
			for (Resource resource : dataPrivilegeUrl.getRes()) {
				if(className.equalsIgnoreCase(resource.getClassname())&& methodName.equalsIgnoreCase(resource.getMethod())){
					return resource;
				}
			}
		}
		
		return null;
	}
}
