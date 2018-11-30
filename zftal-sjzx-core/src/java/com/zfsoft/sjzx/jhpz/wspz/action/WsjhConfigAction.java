package com.zfsoft.sjzx.jhpz.wspz.action;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.steps.webservices.wsdl.Wsdl;
import org.pentaho.di.trans.steps.webservices.wsdl.WsdlOpParameter;
import org.pentaho.di.trans.steps.webservices.wsdl.WsdlOpParameterList;
import org.pentaho.di.trans.steps.webservices.wsdl.WsdlOperation;
import org.quartz.Calendar;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import com.util.encode.MD5Util;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.framework.util.Constants;
import com.zfsoft.framework.util.JobTriggerBean;
import com.zfsoft.framework.util.SpringUtil;
import com.zfsoft.framework.util.WebServiceJobDetail;
import com.zfsoft.framework.util.WebServiceJobStart;
import com.zfsoft.framework.util.WebServiceSimpleJob;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.sjzx.common.util.DBEncrypt;
import com.zfsoft.sjzx.common.util.DateTimeUtil;
import com.zfsoft.sjzx.common.util.DbUtil;
import com.zfsoft.sjzx.jhpz.sjjhpzb.model.Dsqlx;
import com.zfsoft.sjzx.jhpz.sjjhpzb.model.Sjdwb;
import com.zfsoft.sjzx.jhpz.sjjhpzb.model.Sjlx;
import com.zfsoft.sjzx.jhpz.sjkpz.model.Sjkpz;
import com.zfsoft.sjzx.jhpz.sjkpz.model.SjkpzWrapper;
import com.zfsoft.sjzx.jhpz.sjkpz.service.SjkpzService;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhConfig;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhDsqConfig;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhParamsConfig;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhRzb;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhdzConfig;
import com.zfsoft.sjzx.jhpz.wspz.service.WsjhConfigService;
import com.zfsoft.sjzx.jhpz.wspz.util.XmlHelper;
import com.zfsoft.sjzx.wsdl.bean.Operations;
import com.zfsoft.sjzx.wsdl.bean.Parameters;
import com.zfsoft.sjzx.wsdl.bean.WSDLParser;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.zfca.tp.cas.util.Tool;

import edu.emory.mathcs.backport.java.util.Arrays;
/**
 * <p>WebService 交换配置</p>
 * @author wangjian
 *
 */
public class WsjhConfigAction extends HrmAction {

	private static final long serialVersionUID = 2168936513749516938L;

	private WsjhConfig query;	
	private WsjhConfigService wsjhConfigService;
	private SjkpzService sjkpzService;
	private WsjhDsqConfig wdc;
	private WsjhRzb wsjhrzb;
	 private final String separate = "!!";
	 private static final Integer WS_TYPE=1;
	/**
	 * <p>显示主页面</p>
	 * @return
	 * @throws Exception
	 */
	public String main() throws Exception{
		if (this.query == null) {
			this.query = new WsjhConfig();
		}
		if (this.getInt("toPage") != -1) {
			query.setToPage(this.getInt("toPage"));
		}
		query.setType(WS_TYPE);//默认是WebService交换配置
		List dsList = this.sjkpzService.getSjkpz();
		PageList pageList = this.wsjhConfigService.getPagingInfo(query);
		if(pageList!=null&&pageList.size()>0){
		    for(int i=0;i<pageList.size();i++){
		    	WsjhConfig wc = (WsjhConfig)pageList.get(i);
		    	if(wc!=null){
		    		//修改目标数据源名称
		    		wc.setWsjhds(getWsjhds(wc.getWsjhds(),dsList));
		    	}
		    }
		}
		this.setInActionContext("resourceDO", query);
		this.setInActionContext("list", pageList);
		this.setInActionContext("paginator", pageList.getPaginator());		
		this.setInActionContext("t", new Date().getTime());
		return SUCCESS;
	}
	/**
	 * <p>修改目标数据源名称</p>
	 * @param wsjhds
	 * @param db
	 * @return
	 */
    private String getWsjhds(String wsjhds, List dsList) {
		if(dsList!=null&&dsList.size()>0){
			for(int i=0;i<dsList.size();i++){
				Sjkpz sjkpz = (Sjkpz)dsList.get(i);
				if(sjkpz!=null&&!Tool.isNull(wsjhds)&&wsjhds.equals(sjkpz.getPzxh())){
					return sjkpz.getPzljmc();
				}
			}
		}
		return "";
	}
	/**
     * <p>显示增加页面</p>
     * @return
     * @throws Exception
     */
	public String add() throws Exception {
		this.setInActionContext("DB", this.sjkpzService.getSjkpz());
		this.setInActionContext("sjlscmsx", this.wsjhConfigService.getSjlx());
		this.setInActionContext("dsqlx", this.wsjhConfigService.getDsqlx());
		this.setInActionContext("sjdw", this.wsjhConfigService.getSjdw());
		return "add";
	}
	/**
	 * <p>编辑</p>
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		String name= getString("name");
		name=URLDecoder.decode(name,"UTF-8");
		query=query==null?new WsjhConfig():query;
		query.setWsjhmc(name);
		query.setType(WS_TYPE);
		query=this.wsjhConfigService.getWsjhConfig(query);		
		this.setInActionContext("DB", this.sjkpzService.getSjkpz());
		this.setInActionContext("sjlscmsx", this.wsjhConfigService.getSjlx());
		List dsqlxList = this.wsjhConfigService.getDsqlx();
		this.setInActionContext("dsqlx", dsqlxList);
		this.setInActionContext("sjdw", this.wsjhConfigService.getSjdw());
		WsjhDsqConfig wdc = new WsjhDsqConfig ();
		wdc.setJhpzmc(name);
		wdc.setJhlx(WS_TYPE);
		List<WsjhDsqConfig> wdcList = this.wsjhConfigService.getWsjhDsqConfig(wdc);
		wdcList = getWsjhDsqConfig(wdcList,dsqlxList,this.getWsjhConfigService().getSjdw());
		this.setInActionContext("dsqpzb",wdcList);
		return "edit";
	}
	private String getSjdwmc(Integer sjdwbh, List sjdwList) {
		if(sjdwList!=null&&sjdwList.size()>0){
			for(int i=0;i<sjdwList.size();i++){
				Sjdwb dsq = (Sjdwb)sjdwList.get(i);
				if(dsq!=null&&sjdwbh!=null&&dsq.getSjdwbh()==sjdwbh.intValue())
					return dsq.getSjdwmc();
			}
		}
		return "";
	}
	private List<WsjhDsqConfig> getWsjhDsqConfig(List<WsjhDsqConfig> wdcList,List dsqlxList,List sjdwList) {
		if(wdcList!=null&&wdcList.size()>0){
			for(WsjhDsqConfig wdc:wdcList){
				if(wdc!=null){
					wdc.setDsqlxmc(getDsqlxmc(wdc.getDsqlx(),dsqlxList));
					wdc.setSjdwmc(getSjdwmc(wdc.getSjdwbh(),sjdwList));
				}
			}
		}
		return wdcList;
	}
	/**
	 * <p>根据定时器类型ID获取对应的值</p>
	 * @param dsqlx
	 * @param dsqlxList
	 * @return
	 */
	private String getDsqlxmc(Integer dsqlx, List dsqlxList) {
		if(dsqlxList!=null&&dsqlxList.size()>0){
			for(int i=0;i<dsqlxList.size();i++){
				Dsqlx dsq = (Dsqlx)dsqlxList.get(i);
				if(dsq!=null&&dsq.getDsqlx()!=null&&dsq.getDsqlx().intValue()==dsqlx)
					return dsq.getDsqlxmc();
			}
		}
		return "";
	}
	/**
	 * <p>验证WebService 交换配置名称</p>
	 * @return
	 * @throws Exception
	 */
	public String varifyWsjhmc() throws Exception{
		String name= getString("name");
		name=URLDecoder.decode(name,"UTF-8");
		query=query==null?new WsjhConfig():query;
		query.setWsjhmc(name);
		query.setType(WS_TYPE);
		query=this.wsjhConfigService.getWsjhConfig(query);
		boolean flag=false;
		if(query!=null&&query.getWsjhmc()!=null&&query.getWsjhmc().equals(name)){
			flag=true;
		}
		JSONObject json = new JSONObject();
		json.put("result", String.valueOf(flag));
		writeResponse(json.toString());
		return null;
	}
	
	
	/**
	 * <p>保存</p>
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		
		String wsjhmc = getString("wsjhmc");
		if(!Tool.isNull(wsjhmc))wsjhmc = URLDecoder.decode(wsjhmc,"utf-8");
		String url = getString("url");
		String namespace = getString("namespace");
		String operate = getString("operate");
		String mdb = getString("mdb");//目数据库	
		String wsjhb = getString("wsjhb");//目表
		String params = getString("params");//方法参数
		String fhbl=getString("fhbl");//返回变量
		String wsjhzcb = getString("wsjhzcb");
		String sjcz  = getString("sjcz");//时间戳对应的变量
		String isSjc = getString("isSjc");//启用时间戳
		String isType=getString("isType");
		String gys = getString("gys");
		String zys = getString("zys");
		Integer oflag=getInt("oflag");
		query=query==null?new WsjhConfig():query;
		query.setWsjhmc(wsjhmc);
		query.setUrl(url);
		query.setNamespace(namespace);
		query.setOperate(operate);
		query.setWsjhds(mdb);
		query.setWsjhb(wsjhb);
		query.setFhbl(fhbl);
		query.setParams(params);
		query.setWsjhzcb(wsjhzcb);
		query.setIssjc(Integer.valueOf(isSjc==null?"0":isSjc).intValue());//0表示否1表示是
		query.setSjcz(sjcz);
		query.setIstype(Integer.valueOf(isType==null?"0":isType).intValue());//默认是JSON
		query.setGys(gys);
		query.setZys(zys);
		query.setType(WS_TYPE);
		query.setOflag(oflag);
		WsjhDsqConfig wdc = new WsjhDsqConfig();
		wdc.setJhpzmc(wsjhmc);
		wdc.setJhlx(WS_TYPE);
		List<WsjhDsqConfig> wdcList = wsjhConfigService.getWsjhDsqConfig(wdc);
		if(wdcList!=null&&wdcList.size()>0)query.setIsdzq("是");
		query.setXgsj(DateTimeUtil.getCurrDateTimeStr());		
		//JSONObject jo = new JSONObject();
		
		WsjhConfig wskjQuery = new WsjhConfig(); 
		wskjQuery.setWsjhmc(wsjhmc);
		wskjQuery.setType(WS_TYPE);
		WsjhConfig model = this.wsjhConfigService.getWsjhConfig(query);
		if(model != null){
			this.setErrorMessage("已存在此配置名称！");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		
		
		try{
		   wsjhConfigService.deleteWsjhConfig(query);
		   wsjhConfigService.insertWsjhConfig(query);
		   this.setSuccessMessage("webservice基本信息保存成功！");
		   getValueStack().set(DATA, getMessage());
		   return DATA;
		   //jo.put("result", "true");
		}catch(Exception e){
			e.printStackTrace();
			this.setErrorMessage("保存异常！");
			getValueStack().set(DATA, getMessage());
			return DATA;
		   //jo.put("result","false");
		}
		//writeResponse(jo.toString());
		//return null;
	}
	
	/**
	 * <p>更新</p>
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		
		String wsjhmc = getString("wsjhmc");
		if(!Tool.isNull(wsjhmc))wsjhmc = URLDecoder.decode(wsjhmc,"utf-8");
		String url = getString("url");
		String namespace = getString("namespace");
		String operate = getString("operate");
		String mdb = getString("mdb");//目数据库	
		String wsjhb = getString("wsjhb");//目表
		String params = getString("params");//方法参数
		String fhbl=getString("fhbl");//返回变量
		String wsjhzcb = getString("wsjhzcb");
		String sjcz  = getString("sjcz");//时间戳对应的变量
		String isSjc = getString("isSjc");//启用时间戳
		String wsjhxh = getString("wsjhxh");
		String isType=getString("isType");
		String gys = getString("gys");
		String zys = getString("zys");
		Integer oflag=getInt("oflag");
		query=query==null?new WsjhConfig():query;
		query.setWsjhmc(wsjhmc);
		query.setUrl(url);
		query.setNamespace(namespace);
		query.setOperate(operate);
		query.setWsjhds(mdb);
		query.setWsjhb(wsjhb);
		query.setFhbl(fhbl);
		query.setParams(params);
		query.setWsjhzcb(wsjhzcb);
		query.setIssjc(Integer.valueOf(isSjc==null?"0":isSjc).intValue());//0表示否1表示是
		query.setSjcz(sjcz);
		query.setWsjhxh(Integer.parseInt(wsjhxh));
		query.setType(WS_TYPE);
		query.setIstype(Integer.valueOf(isType==null?"0":isType).intValue());//默认是JSON
		query.setGys(gys);
		query.setZys(zys);
		query.setOflag(oflag);
		WsjhDsqConfig wdc = new WsjhDsqConfig();
		wdc.setJhpzmc(wsjhmc);
		wdc.setJhlx(WS_TYPE);
		List<WsjhDsqConfig> wdcList = wsjhConfigService.getWsjhDsqConfig(wdc);
		if(wdcList!=null&&wdcList.size()>0)query.setIsdzq("是");
		query.setXgsj(DateTimeUtil.getCurrDateTimeStr());		
		JSONObject jo = new JSONObject();
		try{
		   wsjhConfigService.updateWsjhConfig(query);
		   jo.put("result", "true");
		}catch(Exception e){
		   jo.put("result","false");
		}
		writeResponse(jo.toString());
		return null;
	}
	/**
	 * <p>加载WebService URL地址</p>
	 * @return
	 * @throws Exception
	 */
	public String load() throws Exception{
		String url = this.getString("url");			
		String result="{namespace:'',records:[]}";
		LOG.info(url+"webserviceurl1", "webserviceurl1");
		if(!Tool.isNull(url)){
			Wsdl wsdl = null;
			try {
				LOG.info(url+"webserviceurl2", "webserviceurl2");
				wsdl = new Wsdl(new URI(url), null, null);			
			} catch (URISyntaxException e) {
				LOG.info(url+"webserviceurl4", "webserviceurl4");
				writeResponse(result);
				return null;
			}
			
			/*List<WsdlOperation> listeOperations = wsdl.getOperations();
			JSONObject jo = new JSONObject();
			JSONArray ja = new JSONArray();
			for (Iterator<WsdlOperation> itr = listeOperations.iterator(); itr.hasNext();)
	        {
				WsdlOperation op = itr.next();
				JSONObject json = new JSONObject();
				StringBuffer params = new StringBuffer();
				String method = op.getOperationQName().getLocalPart();
				WsdlOpParameterList  wopl=op.getParameters();
				if(wopl!=null&&wopl.size()>0){
					for(WsdlOpParameter wop:wopl){
						if(wop!=null){
						 String ps=wop.getName().getLocalPart();
						 if(!Tool.isNull(ps)){
							 params.append(ps).append(",");
						   }
						}
					}
				}
				String param = params.toString();
				if(param.contains(","))param = param.substring(0,param.lastIndexOf(","));
				json.put("methods", method);    
				json.put("params", param);
	            ja.add(json); 
	            
	        }*/
			JSONObject jo = new JSONObject();
			JSONArray ja = new JSONArray();
			LOG.info(url+"webserviceurl3", "webserviceurl3");
			WSDLParser parser = new WSDLParser();
			List<Operations> ops = parser.parse(url);
			
			System.out.println("result1:"+result);
			System.out.println("ops.size():" + ops.size());
			if(ops!= null && ops.size() > 0){
				for(Operations oper : ops){
					JSONObject json = new JSONObject();
					json.put("methods", oper.getName());
					StringBuilder sb = new StringBuilder();
					StringBuilder sbType = new StringBuilder();
					if(oper.getParams() != null && oper.getParams().size() > 0){
						for(Parameters param : oper.getParams()){
							sb.append(param.getName()).append(",");
							sbType.append(param.getType()).append(",");
						}
					}
					String params = sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length()-1)
																	: "";
					String Types = 	sbType.toString().length() > 0 ? sbType.toString().substring(0, sbType.toString().length()-1)
							: "";		
					json.put("params", params);
					json.put("types", Types);
		            ja.add(json); 
				}
			}
			//jo.put("namespace", wsdl.getTargetNamespace());
			jo.put("namespace", parser.namespace);
			jo.put("records", ja);	
			result = jo.toString();
			System.out.println("result2:"+result);
		}	
		writeResponse(result);
		return null;
	}
	/**
	 * <p>删除</p>
	 * @return
	 */
	public String del()throws Exception {
		String name = this.getString("name");
		if(!Tool.isNull(name))name = URLDecoder.decode(name,"UTF-8");
		query=query==null?new WsjhConfig():query; 
		query.setWsjhmc(name);
		query.setType(WS_TYPE);
        String isdsq = this.getString("isdsq");
        if(!Tool.isNull(isdsq))isdsq=URLDecoder.decode(isdsq,"UTF-8");
        if(isdsq.equals("是")){
        	WsjhDsqConfig wdcp = new WsjhDsqConfig();
        	wdcp.setJhpzmc(name);
        	wdcp.setJhlx(WS_TYPE);
        	List<WsjhDsqConfig> wdcList = this.wsjhConfigService.getWsjhDsqConfig(wdcp);
        	boolean flag = false;
        	if(wdcList!=null&&wdcList.size()>0){
        		for(WsjhDsqConfig wdc:wdcList){
        			if(wdc!=null&&!Tool.isNull(wdc.getDsqzt())&&"0".equals(wdc.getDsqzt()))flag=true;
        		}
        	}
        	if(flag){  
        		this.setErrorMessage("存在定时器正在启动，请先删除定时器！");
    			getValueStack().set(DATA, getMessage());
    			return DATA;
        		//writeResponse("FAIL");
        		//return null;
        	}
         }
        	this.wsjhConfigService.deleteWsjhConfig(query);
        	WsjhParamsConfig wpc = new WsjhParamsConfig();
        	wpc.setJhmc(name);
        	wpc.setJhlx(WS_TYPE);
    		this.wsjhConfigService.deleteWsjhParamsConfig(wpc);
    		WsjhdzConfig wdcp = new WsjhdzConfig();
    		wdcp.setJhmc(name);
    		wdcp.setJhlx(WS_TYPE);
    		this.wsjhConfigService.deleteWsjhdzConfig(wdcp);
    		wdc=wdc==null?new WsjhDsqConfig():wdc;
    		wdc.setJhpzmc(name);
    		this.wsjhConfigService.deleteWsjhDsqConfigByJhmc(wdc);
    		reflushJobAndTrigger();
    		this.setSuccessMessage("成功删除wwebservice相关配置！");
			getValueStack().set(DATA, getMessage());
			return DATA;
    		//writeResponse("OK");
    		//return null;
	}
	/**
	 * <p>执行WebService交换</p>
	 * @return
	 * @throws Exception
	 */
	public String runWsjh() throws Exception {
		String name = this.getString("name");
		name = URLDecoder.decode(name,"UTF-8");
		query=query==null?new WsjhConfig():query; 
		query.setWsjhmc(name);
		query.setType(WS_TYPE);
		query = this.wsjhConfigService.getWsjhConfig(query);
		String msg="";
		if(query==null){
			 msg="WebService 交换配置有误！";
			 msg = "{\"success\":\"true\",\"msg\":\"执行失败："+msg+"\"}";
			 writeResponse(msg);
			 return null;
		}
		
		//方法参数对照
		WsjhParamsConfig wpcp=new WsjhParamsConfig();
		wpcp.setJhmc(name);
		wpcp.setJhlx(WS_TYPE);
		List<WsjhParamsConfig> wpcList = this.wsjhConfigService.getWsjhParamsConfigList(wpcp);
		if(wpcList!=null&&wpcList.size()>0){
			 boolean flag = verifyParams(query.getParams(),wpcList);
			 if(!flag){
				 msg="WebService 方法参数对照不匹配！";
				 msg = "{\"success\":\"true\",\"msg\":\"执行失败："+msg+"\"}";
				 writeResponse(msg);
				 return null;
			 }			
		}
		//结果对照
		WsjhdzConfig wdcp= new WsjhdzConfig();
		wdcp.setJhmc(name);
		wdcp.setJhlx(WS_TYPE);
		List<WsjhdzConfig> wcList =this.wsjhConfigService.getWsjhdzConfigList(wdcp);
		if(wcList!=null&&wcList.size()>0){
			boolean flag = false;
			for(WsjhdzConfig wc:wcList){
				if(wc!=null&&!Tool.isNull(wc.getSfzj())&&wc.getSfzj().equals("1")){
					flag=true;
				}
			}
			if(!flag){
				msg="WebService 结果对照中没有设置主键！";
				msg = "{\"success\":\"true\",\"msg\":\"执行失败："+msg+"\"}";
				writeResponse(msg);
				return null;
			}
		}else{
			msg="WebService 返回结果对照未配置！";
			msg = "{\"success\":\"true\",\"msg\":\"执行失败："+msg+"\"}";
			writeResponse(msg);
			return null;
		}
		try{
			msg=this.wsjhConfigService.getMessage(wpcList,wcList,query); 	
		}catch(Exception e){
			e.printStackTrace();	
			msg = "{\"success\":\"true\",\"msg\":\"执行失败："+e.getMessage()+"\"}";
		}			
		writeResponse(msg);
		return null;
	}
	

	

	
	/**
	 * <p>验证参数与保存参数是否匹配</p>
	 * @param params
	 * @param wpcList
	 * @return
	 */
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean verifyParams(String params, List<WsjhParamsConfig> wpcList) {
		if(!Tool.isNull(params)){
		    String[] param = params.split(",");
		    List  paramList = Arrays.asList(param);
		    List  list = new ArrayList();
		    for(WsjhParamsConfig wpc:wpcList){
		    	if(wpc!=null&&!Tool.isNull(wpc.getCszd())){
		    		list.add(wpc.getCszd());
		    	}
		    }
		   if( paramList.containsAll(list))return true;
		}
		return false;
	}
	/**
     * 保存定时器
     * @return
     */
    public String saveRunSet() throws Exception{ 
            wdc.setCjsj(DateTimeUtil.getCurrDateTimeStr());
            wdc.setXgsj(DateTimeUtil.getCurrDateTimeStr());
            wdc.setJhlx(WS_TYPE);
			String retu = this.wsjhConfigService.insertWsjhDsqConfig(wdc);
			query=query==null?new WsjhConfig():query;
			query.setWsjhmc(wdc.getJhpzmc());
			query.setType(WS_TYPE);
			query = this.wsjhConfigService.getWsjhConfig(query);
			query.setIsdzq("是");			
			query.setXgsj(DateTimeUtil.getCurrDateTimeStr());			
			this.wsjhConfigService.updateWsjhConfig(query);
    		writeResponse(retu);
    	return null;
    }
    /**
     * <p>删除定时器</p>
     * @return
     * @throws Exception
     */
    public String deleteRunset() throws Exception {   
    	    String str="";
    	    try{
    	    	wdc.setJhlx(WS_TYPE);
      		   this.wsjhConfigService.deleteWsjhDsqConfig(wdc);
      		   reflushJobAndTrigger();
      		   str = "{\"success\":\"true\"}";
    	    }catch(Exception e){
    	    	str = "{\"success\":\"false\",\"msg\":\"删除时，程序出现异常！\"}";
    	    }
    	   writeResponse(str);
    	return null;
    }
    
    private Class<?> getClass(String classname) {
		if (classname != null && !"".equals(classname)) {
			ClassLoader loader = getClass().getClassLoader();
			Class<?> c = null;
			try {
				c = loader.loadClass(classname);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return c;
		}
		return null;
	}
    
    public void reflushJobAndTrigger(){
    	Scheduler scheduler = (Scheduler) SpringUtil.getBean("localQuartzScheduler");
    	try {
			for (String triggerName : scheduler.getTriggerNames(Constants.JOB_GROUP)) {
				scheduler.unscheduleJob(triggerName, Constants.JOB_GROUP);
			}
			for (String jobName: scheduler.getJobNames(Constants.JOB_GROUP)) {
				scheduler.deleteJob(jobName, Constants.JOB_GROUP);
			}
    	
		
		JobTriggerBean jtb = null;
		WsjhConfigService wsjhConfigService = (WsjhConfigService) SpringUtil
				.getBean("wsjhConfigService");
		List<WsjhDsqConfig> wdcList = wsjhConfigService.getRunTasks();
		if (wdcList != null&&wdcList.size()>0) {
			for (WsjhDsqConfig wdc : wdcList) {
				String jobname = "com.zfsoft.sjzx.jhpz.wspz.service.WsjhConfigTask";
				int dsqxh = wdc.getDsqxh();
				Class<?> c = getClass(jobname);
				if (c != null) {
					WebServiceJobDetail jd = null;
					try {
						jd = (WebServiceJobDetail) c.newInstance();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					jtb = jd.getTrigger(wdc);
					jd.setName(dsqxh + "e");
					jd.setWdc(wdc);
					JobDataMap jobDataMap = jd.getJobDataMap();
					jobDataMap.put("wdc", wdc);
					jd.setGroup(Constants.JOB_GROUP);
					jd.setDescription("后台服务");
					jd.setJobClass(WebServiceSimpleJob.class);
					Trigger trigger = jtb.getTrigger();
					if(trigger!=null){
						trigger.setName(dsqxh + "e");
						trigger.setGroup(Constants.JOB_GROUP);
                        
						Calendar cal = jtb.getCalendar();
						if (cal != null) {
							scheduler.addCalendar(cal.getDescription(), cal,
									true, true);
							trigger.setCalendarName(cal.getDescription());
						}
						scheduler.scheduleJob(jd, trigger);
					}
				}

			}
			}
    	} catch (SchedulerException e1) {
    		// TODO Auto-generated catch block
    		e1.printStackTrace();
    	}
    }
    /**
     * <p>启动/停时定时器</p>
     * @return
     * @throws Exception
     */
    public String setRunStatus() throws Exception {
    	    String str="";
    	    try{
    	    	wdc.setJhlx(WS_TYPE);
    	    	this.wsjhConfigService.updataWsjhDsqConfig(wdc);
    	    	query=query==null?new WsjhConfig():query;
    	    	query.setWsjhmc(wdc.getJhpzmc());
    	    	query.setType(WS_TYPE);
    	    	query = this.wsjhConfigService.getWsjhConfig(query);
    	    	query.setIsdzq("是");
    	    	query.setXgsj(DateTimeUtil.getCurrDateTimeStr());    			
    			this.wsjhConfigService.updateWsjhConfig(query);
    			
    			reflushJobAndTrigger();
    			
    	    	str = "{\"success\":\"true\",\"msg\":\"操作成功\"}";
    	    }catch(Exception e){
    	    	str = "{\"success\":\"false\",\"msg\":\"操作失败\"}";
    	    }	
    		  		
    	writeResponse(str);
    	return null;
    }
    
    /**
     * 获取数据库中所有的表
     * @return
     */
    public String getAllTable() throws Exception{
    	String datasource = this.getString("datasource");
    	String type = this.getString("type");
    	SjkpzWrapper sjkpzWrapper = this.sjkpzService.getSjkpzDetail(datasource);
    	DatabaseMeta dbMeta = DbUtil.getPoolMeta("system", sjkpzWrapper.getLjlxmc(), sjkpzWrapper.getSjklxmc(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getSjkmc()), sjkpzWrapper.getIpdz(), sjkpzWrapper.getDkh(),com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getYhm()),com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getMm()));
    	String[] tables = DbUtil.getDBTables(dbMeta);
    		StringBuffer html = new StringBuffer("");
			StringBuffer el = new StringBuffer("");
			int lt = tables.length;
			for(int i=0;i<lt;i++){
				if(i%3==0){
					html.append("<tr class='data'>");
				}
				html.append("<td class='tabletd'><input type='radio' class='autocut' name='fruit' value ='").append(tables[i]).append("' />").append(tables[i]).append("</td>");
				el.append(tables[i]).append(",");
				if(i%3==2){
					html.append("</tr>");
				}
			}
			if(lt%2!=0){
				html.append("</tr>");
			}
			this.setInActionContext("tables", html.toString());
			this.setInActionContext("tableStr",StringUtils.substringBeforeLast(el.toString(), ","));  
			this.setInActionContext("type", type);
    	return "tables";
    }
    /**
     * 获取数据库中所有的表
     * @return
     */
    public String getAllTableAndView() throws Exception{
    	String datasource = this.getString("datasource");
    	String type = this.getString("type");
    	SjkpzWrapper sjkpzWrapper = this.sjkpzService.getSjkpzDetail(datasource);
    	DatabaseMeta dbMeta = DbUtil.getPoolMeta("system", sjkpzWrapper.getLjlxmc(), sjkpzWrapper.getSjklxmc(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getSjkmc()), sjkpzWrapper.getIpdz(), sjkpzWrapper.getDkh(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getYhm()), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getMm()));
    	String[] tables = DbUtil.getDBTablesAndView(dbMeta);
    		StringBuffer html = new StringBuffer("");
			StringBuffer el = new StringBuffer("");
			int lt = tables.length;
			for(int i=0;i<lt;i++){
				if(i%3==0){
					html.append("<tr class='data'>");
				}
				html.append("<td class='tabletd'><input type='radio' class='autocut' name='fruit' value ='").append(tables[i]).append("' />").append(tables[i]).append("</td>");
				el.append(tables[i]).append(",");
				if(i%3==2){
					html.append("</tr>");
				}
			}
			if(lt%2!=0){
				html.append("</tr>");
			}
			this.setInActionContext("tables", html.toString());
			this.setInActionContext("tableStr",StringUtils.substringBeforeLast(el.toString(), ","));  
			this.setInActionContext("type", type);
    	return "tables";
    }
    /**
     * <p>获取传参方法页面</p>
     * @return
     * @throws Exception
     */
	public String getParamsPage() throws Exception {
		String types = getString("types");
		String params = getString("params");		
		if(!Tool.isNull(params)){
		   List paramsList= Arrays.asList(params.split(","));
		   this.setInActionContext("types",types);
		   this.setInActionContext("paramsList",paramsList);
		}else{
			this.setInActionContext("paramsList", null);
		}
		return "paramsPage";
	}
	/**
	 * <p>获取返回变量</p>
	 * @return
	 * @throws Exception
	 */
	public String getFhbl() throws Exception {
		try{
			String params = getString("params");
			String url = getString("url");
			String namespace=getString("namespace");
			String operate=getString("operate");
			String isType=getString("isType");
			String gys = getString("gys");
			String zys = getString("zys");
			String types = getString("types");
			String[] typeArray = types.split(",");
			JSONObject jo=new JSONObject();
			if(!Tool.isNull(url)){
				if(url.contains(".asmx")){		
					    Service service = new Service();
					    Call   call   = (Call) service.createCall();
					    call.setTargetEndpointAddress( new java.net.URL(url) );
					    QName qnName = new QName(namespace,operate);
					    call.setOperationName(qnName);	
					    String objs=null;
					    if(params==null){
					    	 objs = (String)call.invoke(new Object[] {});
					    }else{
					    	 addParameterByCall(call,params,namespace,operate);				    	
					    	 Object[] obj = getObjects(params);
					    	 objs = (String)call.invoke(obj);
					    }	
					    if(isType!=null&&Integer.parseInt(isType)==1){//XML格式
					    	if(!Tool.isNull(objs)){
									List<String> variableList =XmlHelper.getList(objs,zys,0);
									jo.put("params", StringUtils.join(variableList,","));									
					    	}
					    }
								
				}else{
					url=url.replace("?wsdl","").replace("?WSDL","");
					RPCServiceClient serviceClient = new RPCServiceClient();
					Options options = serviceClient.getOptions();		
					EndpointReference targetEPR = new EndpointReference(url);
					options.setTo(targetEPR);
					Object[] opAddEntryArgs = getObjects(params);
					Class[] classes = getOpAddEntryArgs(opAddEntryArgs);
					QName opAddEntry = new QName(namespace,operate);
					
					
					Service service = new Service();
			        Call call;
			        call = (Call) service.createCall();
		            call.setTargetEndpointAddress(url);// 远程调用路径
		            //call.setOperationName("login");// 调用的方法名
		            call.setOperationName(new QName(namespace, operate));
		            Object[] paramsAyyay = getParams(params); 
					if(paramsAyyay != null && paramsAyyay.length > 0){
						for(int i = 0 ; i < paramsAyyay.length ; i++){
							if(paramsAyyay[i] == null || StringUtil.isEmpty(paramsAyyay[i].toString())) continue;
							//if(typeArray[i].equals("string")){
								call.addParameter(paramsAyyay[i].toString(), // 参数名
										XMLType.XSD_STRING,// 参数类型:String
										ParameterMode.IN);// 参数模式：'IN' or 'OUT'
							//}
							/*if(typeArray[i].equals("int")){
								call.addParameter(paramsAyyay[i].toString(), // 参数名
										XMLType.XSD_INT,// 参数类型:String
										ParameterMode.IN);// 参数模式：'IN' or 'OUT'
							}*/
						}
					}
					// 设置返回值类型：
		            call.setReturnType(XMLType.XSD_STRING);// 返回值类型：String			

		            System.out.println("opAddEntryArgs----"+opAddEntryArgs);
		            
		            
		            
		            Object result =  call.invoke(opAddEntryArgs);// 远程调用
			        //Object[] results = serviceClient.invokeBlocking(opAddEntry,opAddEntryArgs,classes); 
			        //LOG.info("start object=====");
			        /*if(results.length > 0){
			        	for(Object object :  results){
			        		if(object != null){
			        			LOG.info("object=====" + object, object.toString());
			        			
			        		}
			        	}
			        }*/
			        //LOG.info("end object=====", "end");
			        List<String> variableList = new ArrayList<String>();
			        //getFhblResult(results, variableList,isType,gys,zys);
			        getFhblResult(result.toString(), variableList,isType,gys,zys);
			        jo.put("params", StringUtils.join(variableList,","));	
			        Map<String, Object> data = new HashMap<String, Object>();
		            data.put("success", true);
		            data.put("params", StringUtils.join(variableList,","));
		            getValueStack().set(DATA, data);
		    		return DATA;
				}			
			}
	        writeResponse(jo.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	private void addParameterByCall(Call call, String params,String soapaction,String methodName) {
		if(!Tool.isNull(params)){
			String[] ps = params.split("&");			
			if(ps!=null&&ps.length>0){
				for(String s:ps){
					if(!Tool.isNull(s)){
						String[] sValue=s.split("=");
						if(sValue!=null&&sValue.length>0){
							   call.addParameter(new QName(soapaction,sValue[0]),
									    org.apache.axis.encoding.XMLType.XSD_STRING, 
									    javax.xml.rpc.ParameterMode.IN);
						}
					}
				}
			}
			   call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型
			   call.setUseSOAPAction(true); 
			   call.setSOAPActionURI(soapaction + methodName);  
		}
		
	}
	
	/**
	 * <p>获取返回变量 结果字符串</p>by zhangxu
	 * @param results
	 * @param variableList
	 * @throws DocumentException
	 */
	private void getFhblResult(String result, List<String> variableList,String isType,String gys,String zys)
			throws DocumentException {
		if(!StringUtil.isEmpty(result)){
        	if(!Tool.isNull(result)){
        		if(!Tool.isNull(isType)){
        			if(0==Integer.parseInt(isType)){//JSON
        				JSONArray ja = JSONArray.fromObject(result);
            			JSONObject jo = (JSONObject) ja.get(0);//默认取出第一条数据
            			if(jo!=null){
            				String rs = jo.toString();
            				if(!Tool.isNull(rs)){
            					rs = rs.replaceAll("\"", "").replace("{","").replace("}", "");
            					String[] s = rs.split(",");
            					if(s!=null&&s.length>0){
            	        			for(String r:s){
            	        				if(!Tool.isNull(r)){
            	        					String variable = r.split(":")[0];
            	        					if(!Tool.isNull(variable))variableList.add(variable.toUpperCase());
            	        				}
            	        			}
            	        		}
            				}
            			}
        			}else if(1==Integer.parseInt(isType)){//XML
        				Document doc = DocumentHelper.parseText(result);
            			Element rootElement = doc.getRootElement();
            			Element msgElement = rootElement.element(zys);
            			for(Iterator it=msgElement.elementIterator();it.hasNext();){
            				Element ele = (Element)it.next();
            				String name = ele.getName();
            				if(!Tool.isNull(name))variableList.add(name.toUpperCase());
            			}
        			}else if(2==Integer.parseInt(isType)){//Object
        				
        			}else{//其他
        				
        			}
        		}        		
        	}
        }
	}
	
	/**
	 * <p>获取返回变量 结果字符串</p>
	 * @param results
	 * @param variableList
	 * @throws DocumentException
	 */
	private void getFhblResult(Object[] results, List<String> variableList,String isType,String gys,String zys)
			throws DocumentException {
		if(results!=null&&results.length>0){
        	String result  = (String)results[0];
        	if(!Tool.isNull(result)){
        		if(!Tool.isNull(isType)){
        			if(0==Integer.parseInt(isType)){//JSON
        				JSONArray ja = JSONArray.fromObject(result);
            			JSONObject jo = (JSONObject) ja.get(0);//默认取出第一条数据
            			if(jo!=null){
            				String rs = jo.toString();
            				if(!Tool.isNull(rs)){
            					rs = rs.replaceAll("\"", "").replace("{","").replace("}", "");
            					String[] s = rs.split(",");
            					if(s!=null&&s.length>0){
            	        			for(String r:s){
            	        				if(!Tool.isNull(r)){
            	        					String variable = r.split(":")[0];
            	        					if(!Tool.isNull(variable))variableList.add(variable.toUpperCase());
            	        				}
            	        			}
            	        		}
            				}
            			}
        			}else if(1==Integer.parseInt(isType)){//XML
        				Document doc = DocumentHelper.parseText(result);
            			Element rootElement = doc.getRootElement();
            			Element msgElement = rootElement.element(zys);
            			for(Iterator it=msgElement.elementIterator();it.hasNext();){
            				Element ele = (Element)it.next();
            				String name = ele.getName();
            				if(!Tool.isNull(name))variableList.add(name.toUpperCase());
            			}
        			}else if(2==Integer.parseInt(isType)){//Object
        				
        			}else{//其他
        				
        			}
        		}        		
        	}
        }
	}
	/**
	 * <p>输出响应</p>
	 * @param obj
	 * @throws IOException
	 */
	private void writeResponse(String obj) throws IOException {
		HttpServletResponse response = this.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();		
		out.println(obj);
		out.flush();
	}
	/**
	 * <p>获取方法参数字段对照相关的内容</p>
	 * @return
	 * @throws Exception
	 */
	public String getTableFieldsAndParams() throws Exception{
		String mdbs = getString("mdbs");//数据源
		String mdb = getString("mdb");//对应的表
		String params=getString("params");//对应的返回变量
		SjkpzWrapper sjkpzWrapper = this.sjkpzService.getSjkpzDetail(mdbs);
		DatabaseMeta databaseMeta = DbUtil.getPoolMeta("system", sjkpzWrapper.getLjlxmc(), sjkpzWrapper.getSjklxmc(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getSjkmc()), sjkpzWrapper.getIpdz(), sjkpzWrapper.getDkh(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getYhm()), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getMm()));
		ExecutorService pool = Executors.newFixedThreadPool(1);
		Future<String> fieldsTypes1 = pool.submit(new GetTableFieldsAndTypesThread(databaseMeta,mdb,"1"));		
		StringBuffer result = new StringBuffer();
		if(!Tool.isNull(params)){			
			String[] ps = params.split(",");
			List<String> paramsList = new ArrayList<String>();
			if(ps!=null&&ps.length>0){
				for(String p:ps){
					if(!Tool.isNull(p)){
						if("PAGE".equals(p)){
							paramsList.add(p+":"+1+":String");//表示页数
						}else if("PAGESIZE".equals(p)){
							paramsList.add(p+":"+2+":String");//表示页条数
						}else if("SIGN".equals(p)){
							paramsList.add(p+":"+3+":String");//密钥
						}else{
							paramsList.add(p+":"+4+":"+getParamsType(p,fieldsTypes1));
						}
					}
				}
			}
			result.append(params).append(separate);
			result.append(fieldsTypes1.get()).append(separate);			
			result.append(StringUtils.join(paramsList,"@")).append(separate);
			String types="String,int,integer,date,short,boolean,double,long,float";//参数数据类型
			result.append(types);
		}		
		
		writeResponse(result.toString());
		return null;
	}
	
	private String getParamsType(String p, Future<String> fieldsTypes1) throws InterruptedException, ExecutionException {
		String result = fieldsTypes1.get();
		if(!Tool.isNull(result)){
			String[] rs = result.split(",");
			if(rs!=null&&rs.length>0){
				for(String r:rs){
					if(!Tool.isNull(r)){
						String[] s = r.split(":");
						if(s!=null&&s.length>0){
							if(s[0].equals(p)){
								return s[1];
							}
						}
					}
				}
			}
			
		}
		return "";
	}
	/**
	 * <p>获取已选择的方法参数字段对照内容</p>
	 * @return
	 * @throws Exception
	 */
	public String getAllTableFieldsAndParams() throws Exception{
		String mdbs = getString("mdbs");//数据源
		String mdb = getString("mdb");//对应的表
		String wsjhmc = getString("wsjhmc");//交换配置名称
		SjkpzWrapper sjkpzWrapper = this.sjkpzService.getSjkpzDetail(mdbs);
		DatabaseMeta databaseMeta = DbUtil.getPoolMeta("system", sjkpzWrapper.getLjlxmc(), sjkpzWrapper.getSjklxmc(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getSjkmc()), sjkpzWrapper.getIpdz(), sjkpzWrapper.getDkh(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getYhm()), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getMm()));
		ExecutorService pool = Executors.newFixedThreadPool(1);
		Future<String> fieldsTypes1 = pool.submit(new GetTableFieldsAndTypesThread(databaseMeta,mdb,"1"));		
		StringBuffer result = new StringBuffer();
		WsjhParamsConfig wpcp = new WsjhParamsConfig();
		wpcp.setJhmc(wsjhmc);
		wpcp.setJhlx(WS_TYPE);
		List<WsjhParamsConfig> wpcList = this.wsjhConfigService.getWsjhParamsConfigList(wpcp);
		List<String> paramsList = new ArrayList<String>();
		StringBuffer params = new StringBuffer();
		if(wpcList!=null&&wpcList.size()>0){
			for(WsjhParamsConfig wpc:wpcList){
				if(wpc!=null&&!Tool.isNull(wpc.getCszd())){
					params.append(wpc.getCszd()).append(",");
					if("PAGE".equals(wpc.getCszd())){
						paramsList.add(wpc.getCszd()+":"+1+":"+(wpc.getCsz()==null?"":wpc.getCsz())+":"+wpc.getCszdt());//表示页数
					}else if("PAGESIZE".equals(wpc.getCszd())){
						paramsList.add(wpc.getCszd()+":"+2+":"+(wpc.getCsz()==null?"":wpc.getCsz())+":"+wpc.getCszdt());//表示页条数
					}else if("SIGN".equals(wpc.getCszd())){
						paramsList.add(wpc.getCszd()+":"+3+":"+(wpc.getCsz()==null?"":wpc.getCsz())+":"+wpc.getCszdt());//密钥
					}else{
						paramsList.add(wpc.getCszd()+":"+4+":"+(wpc.getCsz()==null?"":wpc.getCsz())+":"+wpc.getCszdt());
					}
				}
			}
		}		
		String param = params.toString();
		if(param.contains(","))param=param.substring(0,param.lastIndexOf(","));
		result.append(param).append(separate);
		result.append(fieldsTypes1.get()).append(separate);			
		result.append(StringUtils.join(paramsList,"@")).append(separate);
		String types="String,int,integer,date,short,boolean,double,long,float";//参数数据类型
		result.append(types);
		writeResponse(result.toString());
		return null;
	}
	
	/**
	 * <p>获取返回字段对照相关的内容</p>
	 * @return
	 * @throws Exception
	 */
	public String getTableFieldsAndTypes() throws Exception{
		String mdbs = getString("mdbs");//数据源
		String mdb = getString("mdb");//对应的表
		String variable=getString("variable");//对应的返回变量
		String params = getString("params");//请求方法参数
		SjkpzWrapper sjkpzWrapper = this.sjkpzService.getSjkpzDetail(mdbs);
		DatabaseMeta databaseMeta = DbUtil.getPoolMeta("system", sjkpzWrapper.getLjlxmc(), sjkpzWrapper.getSjklxmc(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getSjkmc()), sjkpzWrapper.getIpdz(), sjkpzWrapper.getDkh(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getYhm()), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getMm()));
		ExecutorService pool = Executors.newFixedThreadPool(1);
		Future<String> fieldsTypes1 = pool.submit(new GetTableFieldsAndTypesThread(databaseMeta,mdb,"1"));			
		StringBuffer result = new StringBuffer();		
		if(params!=null&&!"".equals(params)){
			result.append(variable+","+params).append(separate);
		}else{
			result.append(variable).append(separate);
		}
		result.append(fieldsTypes1.get()).append(separate);
		String fields=fieldsTypes1.get();
		if(!Tool.isNull(fields)&&fields.contains(","))fields=fields.replaceAll(",", "@");
		result.append(fields);
		writeResponse(result.toString());
		return null;
	}
	/**
	 * <p>获取已选择返回字段相关的内容</p>
	 * @return
	 * @throws Exception
	 */
	public String getAllTableFieldsAndTypes() throws Exception {
		String mdbs = getString("mdbs");//数据源
		String mdb = getString("mdb");//对应的表
		String variable=getString("variable");//对应的返回变量
		String wsjhmc = getString("wsjhmc");//交换配置名称
		String params = getString("params");
		SjkpzWrapper sjkpzWrapper = this.sjkpzService.getSjkpzDetail(mdbs);
		DatabaseMeta databaseMeta = DbUtil.getPoolMeta("system", sjkpzWrapper.getLjlxmc(), sjkpzWrapper.getSjklxmc(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getSjkmc()), sjkpzWrapper.getIpdz(), sjkpzWrapper.getDkh(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getYhm()), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getMm()));
		ExecutorService pool = Executors.newFixedThreadPool(1);
		Future<String> fieldsTypes1 = pool.submit(new GetTableFieldsAndTypesThread(databaseMeta,mdb,"1"));		
		StringBuffer result = new StringBuffer();
		if(params!=null&&!"".equals(params)){
			result.append(variable+","+params).append(separate);
		}else{
			result.append(variable).append(separate);
		}
		
		result.append(fieldsTypes1.get()).append(separate);
		WsjhdzConfig wdcp = new WsjhdzConfig();
		wdcp.setJhmc(wsjhmc);
		wdcp.setJhlx(WS_TYPE);
		List<WsjhdzConfig> wcList = this.wsjhConfigService.getWsjhdzConfigList(wdcp);
		List<Sjlx> sjlxList =  this.wsjhConfigService.getSjlx();
		StringBuffer wcResult = new StringBuffer();
		if(wcList!=null&&wcList.size()>0){
			for(WsjhdzConfig wc:wcList){
				if(wc!=null){
					String mblx=getMbSjlx(wc.getMbzdsjlx(),sjlxList);
					wcResult.append(wc.getMbzd()).append(":").append(wc.getMbzdsjlx()+"-"+mblx).append(":").append(wc.getFhbl()).append(":").append(wc.getSfzj()).append("@");
				}
			}
		}
		String wcs = wcResult.toString();
		if(wcs.contains("@"))wcs=wcs.substring(0,wcs.lastIndexOf("@"));
		result.append(wcs);	
		writeResponse(result.toString());
		return null;
	}
	
	
	/**
	 * <p>根据下标获取对应的数据类型</p>
	 * @param mbzdsjlx
	 * @return
	 */
    private String getMbSjlx(Integer mbzdsjlx,List<Sjlx> sjlxList) {
    	if(mbzdsjlx==null)return "";    
    	if(sjlxList!=null&&sjlxList.size()>0){
    		for(Sjlx sjlx:sjlxList){
    			 if(sjlx!=null){
    				 if(sjlx.getSjlx()==mbzdsjlx.intValue())return sjlx.getSjlxmc();
    			 }
    		}
    	}
		return "";
	}
	private Class[] getOpAddEntryArgs(Object[] opAddEntryArgs) {
		if(opAddEntryArgs!=null&&opAddEntryArgs.length>0){
			Class[] clazz = new Class[opAddEntryArgs.length];
			for(int i=0;i<opAddEntryArgs.length;i++){
				clazz[i]=String.class;
			}
			return clazz;
		}
		return null;
	}
	
	
	
	private Object[] getObjects(String params) {
		if(!Tool.isNull(params)){
			String[] ps = params.split("&");
			List<String> paramsList = new ArrayList<String>();
			if(ps!=null&&ps.length>0){
				for(String s:ps){
					if(!Tool.isNull(s)){
						String[] sValue=s.split("=");
						if(sValue!=null&&sValue.length>0){
							paramsList.add(sValue[1]);
						}
					}
				}
			}
			return paramsList.toArray();
		}
		return null;
	}
	private Object[] getParams(String params) {
		if(!Tool.isNull(params)){
			String[] ps = params.split("&");
			List<String> paramsList = new ArrayList<String>();
			if(ps!=null&&ps.length>0){
				for(String s:ps){
					if(!Tool.isNull(s)){
						String[] sValue=s.split("=");
						if(sValue!=null&&sValue.length>0){
							paramsList.add(sValue[0]);
						}
					}
				}
			}
			return  paramsList.toArray();
		}
		return null;
	}
	/**
     * <p>保存字段对照表</p>
     * @return
     * @throws Exception
     */
    public String saveWsjhdzConfig()throws Exception {
    	List<WsjhdzConfig> list = new ArrayList<WsjhdzConfig>();    	
    	String wsjhmc = this.getString("wsjhmc");
    	wsjhmc = URLDecoder.decode(wsjhmc,"UTF-8");    	
    	String[] mbzd = this.getString("mbzd").split(",");
    	String[] mbzdsjlx = this.getString("mbzdsjlx").split(",");
    	String[] sfzj = this.getString("sfzj").split(",");
    	String[] sfgx = this.getString("sfgx").split(","); 
    	String[] variables=this.getString("variables").split(",");
    	int dsqzt = this.getInt("dsqzt");  //定时器状态
    	if(dsqzt==0){    	
    		WsjhdzConfig wdcp = new WsjhdzConfig();
    		wdcp.setJhmc(wsjhmc);
    		wdcp.setJhlx(WS_TYPE);
    		wsjhConfigService.deleteWsjhdzConfig(wdcp);
    		return null;
    	}
    	
    	int lt = mbzd.length;
    	for(int i=0;i<lt;i++){
    		WsjhdzConfig wc = new WsjhdzConfig();    		
    		wc.setMbzd(mbzd[i]);
    		wc.setMbzdsjlx(Integer.valueOf(mbzdsjlx[i]));
    		wc.setSfzj(sfzj[i]);
    		wc.setJhmc(wsjhmc);
    		wc.setSfgx(sfgx[i]); 
    		wc.setFhbl(variables[i]);
    		wc.setJhlx(WS_TYPE);
    		list.add(wc);
    	}
    	WsjhdzConfig wdcp = new WsjhdzConfig();
		wdcp.setJhmc(wsjhmc);
		wdcp.setJhlx(WS_TYPE);
		this.wsjhConfigService.batchInsert(list,wdcp);
		
		this.setSuccessMessage("结果对照保存成功");
	    getValueStack().set(DATA, getMessage());
	    return DATA;
    	//return null;
    }

    /**
     * <p>保存方法参数</p>
     * @return
     * @throws Exception
     */
    public String saveWsjhParamsConfig() throws Exception {
    	List<WsjhParamsConfig> list = new ArrayList<WsjhParamsConfig>();    	
    	String wsjhmc = this.getString("wsjhmc");
    	wsjhmc = URLDecoder.decode(wsjhmc,"UTF-8");    	
    	String[] pfields=getString("pfields").split(",");
    	String[] cszs = getString("csz").split(",");
    	String[] types = getString("types").split(",");
    	if((pfields!=null&&pfields.length>0)||(cszs!=null&&cszs.length>0)){
    		int lt = pfields.length;
        	for(int i=0;i<lt;i++){
        		WsjhParamsConfig wpc = new WsjhParamsConfig();    		
        		if(i<cszs.length){
        			String cszsv = cszs[i];
        			if(cszsv!=null&&cszsv.contains(" "))cszsv=cszsv.replaceAll(" ","+");
        			wpc.setCsz(cszsv);
        		}
        		wpc.setCszd(pfields[i]);
        		wpc.setJhmc(wsjhmc);
        		wpc.setCszdt(types[i]);
        		wpc.setJhlx(WS_TYPE);
        		list.add(wpc);
        	}
    	}
    	WsjhParamsConfig wpcp = new WsjhParamsConfig ();
    	wpcp.setJhmc(wsjhmc);
    	wpcp.setJhlx(WS_TYPE);
    	this.wsjhConfigService.batchInsertWsjhParamsConfig(list,wpcp);
    	
    	this.setSuccessMessage("方法参数保存成功");
	    getValueStack().set(DATA, getMessage());
	    return DATA;
    	//return null;
    }
    
    /**
     * <p>更新方法参数</p>
     * @return
     * @throws Exception
     */
    public String updateWsjhParamsConfig() throws Exception{
    	List<WsjhParamsConfig> list = new ArrayList<WsjhParamsConfig>();    	
    	String wsjhmc = this.getString("wsjhmc");
    	wsjhmc = URLDecoder.decode(wsjhmc,"UTF-8");    	
    	String[] pfields=getString("pfields").split(",");
    	String[] cszs = getString("csz").split(",");
    	String[] types = getString("types").split(",");
    	if((pfields!=null&&pfields.length>0)||(cszs!=null&&cszs.length>0)){
    		int lt = pfields.length;
        	for(int i=0;i<lt;i++){
        		WsjhParamsConfig wpc = new WsjhParamsConfig();    		
        		if(i<cszs.length)wpc.setCsz(cszs[i]);
        		if(pfields[i]!=null&&!pfields[i].equals("SIGN")){//针对密钥不在更新
        			wpc.setCszd(pfields[i]);
            		wpc.setJhmc(wsjhmc);
            		wpc.setCszdt(types[i]);
            		wpc.setJhlx(WS_TYPE);
            		list.add(wpc);
        		}
        	}
    	}
    	if(list!=null&&list.size()>0)this.wsjhConfigService.batchUpdateWsjhParamsConfig(list);
    	return null;
    }
    
    /**
     * <p>显示日志内容</p>
     * @return
     * @throws Exception
     */
    public String viewLog() throws Exception{
        wsjhrzb = wsjhrzb==null?new WsjhRzb():wsjhrzb;
        if (this.getInt("toPage") != -1) {
            wsjhrzb.setToPage(this.getInt("toPage"));
        }
        PageList pageList = this.wsjhConfigService.getPagingInfoByWsjhrzb(wsjhrzb);
        if(pageList!=null&&pageList.size()>0){
        	for(int i=0;i<pageList.size();i++){
        		WsjhRzb wr =(WsjhRzb) pageList.get(i);
        		Date startDate = DateTimeUtil.getFormatDate(wr.getStartDate(), "yyyy-MM-dd HH:mm:ss");
        		Date endDate = DateTimeUtil.getFormatDate(wr.getEndDate(),"yyyy-MM-dd HH:mm:ss");
        		long runTime = (endDate.getTime()-startDate.getTime())/1000;
        		wr.setRunTime(String.valueOf(runTime));
        	}
        }
        this.setInActionContext("resourceDO", wsjhrzb);
        this.setInActionContext("list", pageList);
        this.setInActionContext("paginator", pageList.getPaginator());

    	
    	return "viewLog";
    }
    
    /**
     * 显示详细的抽取日志
     * @return
     */
    public String viewDetailLog(){    	
    	String logField = this.wsjhConfigService.getLogs(wsjhrzb);   
    	this.setInActionContext("detailLog", logField);
    	return "viewDetail";
    }
    

	public WsjhConfig getQuery() {
		return query;
	}
	public void setQuery(WsjhConfig query) {
		this.query = query;
	}
	public WsjhConfigService getWsjhConfigService() {
		return wsjhConfigService;
	}

	public void setWsjhConfigService(WsjhConfigService wsjhConfigService) {
		this.wsjhConfigService = wsjhConfigService;
	}
	public SjkpzService getSjkpzService() {
		return sjkpzService;
	}
	public void setSjkpzService(SjkpzService sjkpzService) {
		this.sjkpzService = sjkpzService;
	}
	public WsjhDsqConfig getWdc() {
		return wdc;
	}
	public void setWdc(WsjhDsqConfig wdc) {
		this.wdc = wdc;
	}
	public WsjhRzb getWsjhrzb() {
		return wsjhrzb;
	}
	public void setWsjhrzb(WsjhRzb wsjhrzb) {
		this.wsjhrzb = wsjhrzb;
	}

	public String invokeRemoteFuc() {
        String endpoint = "http://202.117.255.187:8081/HWWebService/LibServicePort?wsdl";
        String result = "no result!";
        Service service = new Service();
        Call call;
        Object[] object = new Object[5];
        object[0] = "2015010097";//Object是用来存储方法的参数
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
		System.out.println(df.format(new Date()));
		object[1] = df.format(new Date());//Object是用来存储方法的参数
        try {
			object[2] = MD5Util.md5Encode("2015010097"+"huiwen"+"2016-06-13 10:08");
			System.out.println(object[2]);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}//Object是用来存储方法的参数
		object[3] = "1";
		object[4] = "2015010097";//Object是用来存储方法的参数
        try {
            call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);// 远程调用路径
            //call.setOperationName("login");// 调用的方法名
            call.setOperationName(new QName("http://service.login.newmobile.com/", "Login"));

            // 设置参数名:
            call.addParameter("arg0", // 参数名
                    XMLType.XSD_STRING,// 参数类型:String
                    ParameterMode.IN);// 参数模式：'IN' or 'OUT'
            call.addParameter("arg1", // 参数名
                    XMLType.XSD_STRING,// 参数类型:String
                    ParameterMode.IN);// 参数模式：'IN' or 'OUT'
            call.addParameter("arg2", // 参数名
                    XMLType.XSD_STRING,// 参数类型:String
                    ParameterMode.IN);// 参数模式：'IN' or 'OUT'
            call.addParameter("arg3", // 参数名
                    XMLType.XSD_STRING,// 参数类型:String
                    ParameterMode.IN);// 参数模式：'IN' or 'OUT'
            call.addParameter("arg4", // 参数名
                    XMLType.XSD_STRING,// 参数类型:String
                    ParameterMode.IN);// 参数模式：'IN' or 'OUT'

            // 设置返回值类型：
            call.setReturnType(XMLType.XSD_STRING);// 返回值类型：String			

            result = (String) call.invoke(object);// 远程调用
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }
	public static void main(String[] args) {
		
		WsjhConfigAction t = new WsjhConfigAction();
        String result = t.invokeRemoteFuc();
        System.out.println(result);
	}
}
