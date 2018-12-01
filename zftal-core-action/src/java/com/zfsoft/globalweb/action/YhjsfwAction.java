package com.zfsoft.globalweb.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.zfsoft.common.action.BaseAction;
import com.zfsoft.common.service.BaseLog;
import com.zfsoft.dao.entities.SjfwdxModel;
import com.zfsoft.dao.entities.YhjsfwModel;
import com.zfsoft.service.impl.LogEngineImpl;
import com.zfsoft.service.svcinterface.IJsglService;
import com.zfsoft.service.svcinterface.ISjfwdxService;
import com.zfsoft.service.svcinterface.IYhjsfwService;
import com.zfsoft.util.base.StringUtil;

/**
 * 
 * 类名称：YhjsfwAction 
 * 类描述：用户数据范围 
 * 创建人：caozf 
 * 创建时间：2012-7-10
 */
public class YhjsfwAction extends BaseAction implements	ModelDriven<YhjsfwModel> {

	private static final long serialVersionUID = 9114395616159540931L;
	private YhjsfwModel model = new YhjsfwModel();
	private IYhjsfwService yhjsfwService;
	private ISjfwdxService sjfwdxService;
	private IJsglService jsglService;

	private BaseLog baseLog = LogEngineImpl.getInstance();
	
	/**
	 * 保存用户角色数据范围
	 * @return
	 */
    public String bcSjfw() throws Exception{
		ValueStack vs = getValueStack();
    	try {

    		// 数据范围条件
    		Set<String> set = new HashSet<String> ();
    		// 数据范围组对应的数据范围名称。
    		Map<String,String> m = new HashMap<String,String>();
    		
    		String bmdm_ids[] = StringUtil.split(model.getBmdm_ids(),",");
    		String bmdm_njdm_ids[] = StringUtil.split(model.getBmdm_njdm_ids(),",");
    		String zydm_ids[] = StringUtil.split(model.getZydm_ids(),",");
    		String zydm_njdm_ids[] = StringUtil.split(model.getZydm_njdm_ids(),",");
    		String bjdm_ids[] = StringUtil.split(model.getBjdm_ids(),",");
    		String xxdm_ids = model.getXxdm_ids();
    		
    		
    		// 根据学院与年级的值对应关系，得到"学院"和"学院年级"的数据范围
    		if(isNotNull(bmdm_ids)){
				String key = "",value="";
    			if(isNotNull(bmdm_njdm_ids)){
        			for(String bmdm:bmdm_ids){
        				for(String bmdm_njdm:bmdm_njdm_ids){
        					if(bmdm_njdm.startsWith(bmdm+"_")){
        						String str[] = StringUtil.split(bmdm_njdm, "_");
        						key   = "bmdm_id=" + StringUtil.split(str[0],"&")[0]+ "," + "njdm_id=" +StringUtil.split(str[1],"&")[0];
        						value = StringUtil.split(str[0],"&")[1]+ "," + StringUtil.split(str[1],"&")[1];
        						set.add(key);m.put(key, value);
        					} else {
        						key   = "bmdm_id=" + StringUtil.split(bmdm,"&")[0];	
        						value = StringUtil.split(bmdm,"&")[1];
        						set.add(key);m.put(key,value);
        					}
        				}
        			}
    			} else{
    				for(String bmdm:bmdm_ids){
    					key = "bmdm_id="+StringUtil.split(bmdm,"&")[0];
    					value = StringUtil.split(bmdm,"&")[1];
    					set.add(key);m.put(key, value);
    				}
    			}
    		}
    		
    		// 根据专业与年级的值对应关系，得到"专业"和"专业年级"的数据范围
    		if(isNotNull(zydm_ids)){
				String key = "",value="";
    			if(isNotNull(zydm_njdm_ids)){
        			for(String zydm:zydm_ids){
        				for(String zydm_njdm:zydm_njdm_ids){
        					if(zydm_njdm.startsWith(zydm+"_")){
        						String str[] = StringUtil.split(zydm_njdm, "_");
        						key   = "zydm_id=" + StringUtil.split(str[0],"&")[0]+ "," + "njdm_id=" +StringUtil.split(str[1],"&")[0];
        						value = StringUtil.split(str[0],"&")[1]+ "," + StringUtil.split(str[1],"&")[1];
        						set.add(key); m.put(key, value);
        					} else {
        						key   = "zydm_id=" + StringUtil.split(zydm,"&")[0];
        						value = StringUtil.split(zydm,"&")[1];
        						set.add(key);m.put(key, value);
        					}
        				}
        			}
    				
    			}else{
    				for(String zydm:zydm_ids){
    					key   = "zydm_id=" + StringUtil.split(zydm,"&")[0];
    					value =  StringUtil.split(zydm,"&")[1];
    					set.add(key);m.put(key, value);
    				}
    			}   			
    		}
    		
    		// 得到"班级"的数据范围
    	    if(isNotNull(bjdm_ids)){
    	    	String key = "",value="";
    	    	for(String bjdm:bjdm_ids){
    	    		key   = "bjdm_id=" + StringUtil.split(bjdm,"&")[0];
    	    		value =  StringUtil.split(bjdm,"&")[1];
    	    		set.add(key);m.put(key, value);
    	    	}
    	    }
    	    
    	    //得到"全校"的数据范围
    	    if(!StringUtil.isEmpty(xxdm_ids)){
    	    	String key = "",value="全校";
    	    	key = "bmdm_id="+xxdm_ids;
        	    set.add(key);m.put(key,value);
    	    }
 
    	    List<String> allElement = removeElement(set);

    	    String ids[] = StringUtil.split(model.getYh_id(), ",");
    	    for(int i = 0 ;i<ids.length;i++){
    	    	model.setYh_id(ids[i]);
    			// 1、删除用户数据范围组
    			boolean flag = this.scSjfw();
    			if (flag) {
    				// 2、删除用户角色相应的数据范围
    				if (yhjsfwService.scYhjsfw(model)) {
    					// 记操作日志
    					String opDesc = getText("log.message.ywmc", new String[] {"批量删除角色数据范围", "职工号"});
    					baseLog.delete(getUser(), getText("log.message.ywmc", new
    					  String[] { "角色管理", "ZFTAL_XTGL_YHSJFWB" }), "系统管理", opDesc);
    					Map<String, String> map = null;
    					// 循环插入数据范围组表和用户角色数据范围表
    					for (String element : allElement) {
    						map = new HashMap<String, String>();
    						map.put("sjfwztj", element);
    						map.put("sjfwzmc", m.get(element));
    						map.put("yh_id", model.getYh_id());
    						map.put("js_id", model.getJs_id());
    						yhjsfwService.zjYhjsfw(map);
    					}
    					// 记操作日志
    					baseLog.update(getText("log.message.ywmc", new String[] { "角色管理", "ZFTAL_XTGL_YHSJFWB" }),
    					"系统管理", getText("log.message.czms", new
    					String[] { "修改角色数据范围", "职工号", getUser().getYhm() }));
    					vs.set(DATA, getText("I99001"));
    				} else {
    					vs.set(DATA, getText("I99002"));
    				}
    			} else {
    				vs.set(DATA, getText("I99002"));
    			}
    	    }
			
    	} catch (Exception e) {
			logException(e);
			vs.set(DATA, getText("I99002"));
		}
    	return DATA;
    }
    
	/**
	 * 删除角色数据范围
	 * @return
	 */    
    private boolean scSjfw() throws Exception{
		String bmdm_ids_not[] = StringUtil.split(model.getBmdm_ids_not(), ",");
		String zydm_ids_not[] = StringUtil.split(model.getZydm_ids_not(), ",");
		String bjdm_ids_not[] = StringUtil.split(model.getBjdm_ids_not(), ",");
		String bmdm_njdm_ids_not[] = StringUtil.split(model.getBmdm_njdm_ids_not(), ",");
		String zydm_njdm_ids_not[] = StringUtil.split(model.getZydm_njdm_ids_not(), ",");
		
		// 数据范围条件
		Set<String> set = new HashSet<String> ();
		// 数据范围组对应的数据范围名称。
		Map<String,String> m = new HashMap<String,String>();
		
		if(isNotNull(bmdm_ids_not)){
			String key = "",value="";
			if(isNotNull(bmdm_njdm_ids_not)){
    			for(String bmdm:bmdm_ids_not){
    				for(String bmdm_njdm:bmdm_njdm_ids_not){
    					if(bmdm_njdm.startsWith(bmdm+"_")){
    						String str[] = StringUtil.split(bmdm_njdm, "_");
    						key   = "bmdm_id=" + StringUtil.split(str[0],"&")[0]+ "," + "njdm_id=" +StringUtil.split(str[1],"&")[0];
    						value = StringUtil.split(str[0],"&")[1]+ "," + StringUtil.split(str[1],"&")[1];
    						set.add(key);m.put(key, value);
    					} else {
    						key   = "bmdm_id=" + StringUtil.split(bmdm,"&")[0];	
    						value = StringUtil.split(bmdm,"&")[1];
    						set.add(key);m.put(key,value);
    					}
    				}
    			}
			} else{
				for(String bmdm:bmdm_ids_not){
					key = "bmdm_id="+StringUtil.split(bmdm,"&")[0];
					value = StringUtil.split(bmdm,"&")[1];
					set.add(key);m.put(key, value);
				}
			}
		}
		
		if(isNotNull(bmdm_njdm_ids_not)){
			String key = "",value="";
			for(String bmdm_njdm:bmdm_njdm_ids_not){
				String str[] = StringUtil.split(bmdm_njdm, "_");
				key   = "bmdm_id=" + StringUtil.split(str[0],"&")[0]+ "," + "njdm_id=" +StringUtil.split(str[1],"&")[0];
				value = StringUtil.split(str[0],"&")[1]+ "," + StringUtil.split(str[1],"&")[1];
				set.add(key);m.put(key, value);
			}			
		}
		
		
		// 根据专业与年级的值对应关系，得到"专业"和"专业年级"的数据范围
		if(isNotNull(zydm_ids_not)){
			String key = "",value="";
			if(isNotNull(zydm_njdm_ids_not)){
    			for(String zydm:zydm_ids_not){
    				for(String zydm_njdm:zydm_njdm_ids_not){
    					if(zydm_njdm.startsWith(zydm+"_")){
    						String str[] = StringUtil.split(zydm_njdm, "_");
    						key   = "zydm_id=" + StringUtil.split(str[0],"&")[0]+ "," + "njdm_id=" +StringUtil.split(str[1],"&")[0];
    						value = StringUtil.split(str[0],"&")[1]+ "," + StringUtil.split(str[1],"&")[1];
    						set.add(key); m.put(key, value);
    					} else {
    						key   = "zydm_id=" + StringUtil.split(zydm,"&")[0];
    						value = StringUtil.split(zydm,"&")[1];
    						set.add(key);m.put(key, value);
    					}
    				}
    			}
				
			}else{
				for(String zydm:zydm_ids_not){
					key   = "zydm_id=" + StringUtil.split(zydm,"&")[0];
					value =  StringUtil.split(zydm,"&")[1];
					set.add(key);m.put(key, value);
				}
			}  
		}
		
		if(isNotNull(zydm_njdm_ids_not)){
			String key = "",value="";
			for(String zydm_njdm:zydm_njdm_ids_not){
				String str[] = StringUtil.split(zydm_njdm, "_");
				key   = "zydm_id=" + StringUtil.split(str[0],"&")[0]+ "," + "njdm_id=" +StringUtil.split(str[1],"&")[0];
				value = StringUtil.split(str[0],"&")[1]+ "," + StringUtil.split(str[1],"&")[1];
				set.add(key); m.put(key, value);				
			}			
		}
		
		 
		// 得到"班级"的数据范围
	    if(isNotNull(bjdm_ids_not)){
	    	String key = "",value="";
	    	for(String bjdm:bjdm_ids_not){
	    		key   = "bjdm_id=" + StringUtil.split(bjdm,"&")[0];
	    		value =  StringUtil.split(bjdm,"&")[1];
	    		set.add(key);m.put(key, value);
	    	}
	    }
		
		List<String> allElement = new ArrayList<String>();
	    Iterator<String> t = set.iterator();
	    while(t.hasNext()){
	    	allElement.add(t.next()+"");
	    }
		
		boolean flag = false;
		try {
			if(allElement.size()>0){
				model.setLists(allElement);
			}else{
				model.setLists(null);
			}

			flag = yhjsfwService.scSjfwz(model);
 
		} catch (Exception e) {
			logException(e);
			throw new Exception("删除角色数据范围");

		}
		return flag;
    }
    
	
	/**
	 * 设置数据授权
	 * @return
	 */
	public String szSjsq() throws Exception{
		ValueStack vs = getValueStack();
		try {
			vs.set("model", model);
			//数据范围对象列表
			List<SjfwdxModel> lists = sjfwdxService.cxSjfwdx();
			vs.set("lists", lists);
			//已授权用户角色列表
			Map<String,String> map = new HashMap<String,String>();
			map.put("yh_id", model.getYh_id());
			map.put("js_id", model.getJs_id());
			List<YhjsfwModel> yhjsModels = yhjsfwService.cxSjfwYh(map);
			StringBuffer buffer = new StringBuffer();
			for(YhjsfwModel model:yhjsModels){
				if(model.getSjfwztj()!=null){
					String ids[]   = StringUtil.split(model.getSjfwztj(),",");
					String names[] = StringUtil.split(model.getSjfwzmc(),",");	
					if(ids.length>1 && names.length>1){
						buffer.append(StringUtil.split(ids[0], "=")[1] + "&" +names[0]);
						buffer.append("|");
						buffer.append(StringUtil.split(ids[0], "=")[1] + "&" +names[0] + "_" + StringUtil.split(ids[1], "=")[1] + "&" +names[1]);
					}
					else{
						buffer.append(StringUtil.split(ids[0], "=")[1] + "&" +names[0]);
					}
					buffer.append("|");
				}
			}
			vs.set("objects", buffer.toString());
			vs.set("yhjsModels", yhjsModels);
		} catch (Exception e) {
			logException(e);
			throw new Exception("设置数据授权出错");
		}

		return "szSjsq";
	}	
	
	//测试拦截学生家庭表
	public String cxXsjtb() throws Exception{
		try {
			Map<String,String> map = new HashMap<String,String>();
			List<HashMap<String, String>> lists = yhjsfwService.queryXsjtb(map);
			 
		} catch (Exception e) {
			logException(e);
		}
		return "aa";
	}
	
	/**
	 * 判断数组是否为空
	 * @param args
	 * @return
	 */
    private boolean isNotNull(final String[] args) throws Exception{
    	if(args!=null && args.length>0)return true;
    	else return false;
    }	
    
    /**
     * 去除学院/班级、专业/班级的重复元素
     * @param sets
     * @return
     */
	@SuppressWarnings({ "unchecked" })
	private List<String> removeElement(Set sets) throws Exception{
		Iterator array = sets.iterator();
	    List<String> temps = new ArrayList<String>();
	    List<String> compTemps = new ArrayList<String>();
	    List<String> all = new ArrayList<String>();
	    List<String> matchStr = new ArrayList<String>();
	    String value ="";
	    while(array.hasNext()){
	    	value = String.valueOf(array.next());
	    	if(value.contains(",")){
	    		compTemps.add(value);
	    	}
	    	else{
	    		temps.add(value);
	    	}
	    }
	    
	    for(int i = 0;i<temps.size();i++){
	    	for(String compTemp:compTemps){
	    		if(compTemp.startsWith(temps.get(i))){
	    			matchStr.add(temps.get(i));
	    			break;
	    		} 
	    	}
	    }
	    for(String match:matchStr){
	    	 temps.remove(match);
	   	}
	    all.addAll(temps);
	    all.addAll(compTemps);
		return all;  	
    }

	@Override
	public YhjsfwModel getModel() {

		return model;
	}

	public IYhjsfwService getYhjsfwService() {
		return yhjsfwService;
	}

	public void setYhjsfwService(IYhjsfwService yhjsfwService) {
		this.yhjsfwService = yhjsfwService;
	}

	public ISjfwdxService getSjfwdxService() {
		return sjfwdxService;
	}

	public void setSjfwdxService(ISjfwdxService sjfwdxService) {
		this.sjfwdxService = sjfwdxService;
	}

	public IJsglService getJsglService() {
		return jsglService;
	}

	public void setJsglService(IJsglService jsglService) {
		this.jsglService = jsglService;
	}

}
