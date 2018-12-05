package com.zfsoft.sjzx.jhpz.wspz.service.impl;

import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.namespace.QName;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.pentaho.di.core.database.DatabaseMeta;
import org.springframework.dao.DataAccessException;

import com.common.Config;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.sjzx.common.util.ArrayUtil;
import com.zfsoft.sjzx.common.util.DateTimeUtil;
import com.zfsoft.sjzx.common.util.DbUtil;
import com.zfsoft.sjzx.jhpz.sjjhpzb.model.Dsqlx;
import com.zfsoft.sjzx.jhpz.sjjhpzb.model.Sjdwb;
import com.zfsoft.sjzx.jhpz.sjjhpzb.model.Sjlx;
import com.zfsoft.sjzx.jhpz.sjkpz.model.SjkpzWrapper;
import com.zfsoft.sjzx.jhpz.sjkpz.service.SjkpzService;
import com.zfsoft.sjzx.jhpz.wspz.dao.IWsjhConfigDAO;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhConfig;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhDsqConfig;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhParamsConfig;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhResult;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhRzb;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhdzConfig;
import com.zfsoft.sjzx.jhpz.wspz.service.WsjhConfigService;
import com.zfsoft.sjzx.jhpz.wspz.util.XmlHelper;
import com.zfsoft.zfca.tp.cas.util.Tool;

import edu.emory.mathcs.backport.java.util.Collections;


public class WsjhConfigServiceImpl implements WsjhConfigService{

	private IWsjhConfigDAO wsjhConfigDao;
	private SjkpzService sjkpzService;
	private static final Logger LOG = Logger.getLogger(WsjhConfigServiceImpl.class);

	public SjkpzService getSjkpzService() {
		return sjkpzService;
	}

	public void setSjkpzService(SjkpzService sjkpzService) {
		this.sjkpzService = sjkpzService;
	}

	public WsjhConfig getWsjhConfig(WsjhConfig wsjhConfig)
			throws DataAccessException {
		if(wsjhConfigDao.getWsjhConfig(wsjhConfig) != null &&
				wsjhConfigDao.getWsjhConfig(wsjhConfig).size() > 0)
			return wsjhConfigDao.getWsjhConfig(wsjhConfig).get(0);
		else return null;
	}

	public void insertWsjhConfig(WsjhConfig wsjhConfig)
			throws DataAccessException {
		wsjhConfigDao.insertWsjhConfig(wsjhConfig);
	}

	public void updateWsjhConfig(WsjhConfig wsjhConfig)
			throws DataAccessException {
		wsjhConfigDao.updateWsjhConfig(wsjhConfig);
	}

	public void deleteWsjhConfig(WsjhConfig wsjhConfig)
			throws DataAccessException {
		wsjhConfigDao.deleteWsjhConfig(wsjhConfig);
	}

	public PageList<WsjhConfig> getPagingInfo(WsjhConfig wsjhconfig) throws DataAccessException {
		PageList<WsjhConfig> pageList = new PageList<WsjhConfig>();
		Paginator paginator = new Paginator();
		if(wsjhconfig!=null){
			paginator.setItemsPerPage(wsjhconfig.getPerPageSize());
			paginator.setPage((Integer)wsjhconfig.getToPage());
			paginator.setItems(wsjhConfigDao.getWsjhConfigListCount(wsjhconfig));
			pageList.setPaginator(paginator);

			if(paginator.getBeginIndex() <= paginator.getItems()){
				wsjhconfig.setStartRow(paginator.getBeginIndex());
				wsjhconfig.setEndRow(paginator.getEndIndex());
				List<WsjhConfig> list = wsjhConfigDao.getWsjhConfigList(wsjhconfig);
				pageList.addAll(list);
			}
		}
		return pageList;
		//return wsjhConfigDao.getPagingInfo(dataObject);
	}

	public IWsjhConfigDAO getWsjhConfigDao() {
		return wsjhConfigDao;
	}

	public void setWsjhConfigDao(IWsjhConfigDAO wsjhConfigDao) {
		this.wsjhConfigDao = wsjhConfigDao;
	}

	public List<Sjlx> getSjlx() throws DataAccessException {

		return wsjhConfigDao.getSjlx();
	}

	public void deleteWsjhdzConfig(WsjhdzConfig wdc) throws DataAccessException {
		wsjhConfigDao.deleteWsjhdzConfig(wdc);
	}

	public void batchInsert(List<WsjhdzConfig> wcList,WsjhdzConfig wdc) throws SQLException {
		this.wsjhConfigDao.deleteWsjhdzConfig(wdc);
		for(WsjhdzConfig WsjhdzConfig : wcList){
			wsjhConfigDao.batchInsert(WsjhdzConfig);
		}
		//return wsjhConfigDao.batchInsert(wcList);
	}

	public List<Dsqlx> getDsqlx() {

		return wsjhConfigDao.getDsqlx();
	}

	public String insertWsjhDsqConfig(WsjhDsqConfig wdc) {
		wsjhConfigDao.insertWsjhDsqConfig(wdc);
		return String.valueOf(wsjhConfigDao.getInsertWsjhDsqConfig(wdc));
	}

	public void deleteWsjhDsqConfig(WsjhDsqConfig wdc) {

		wsjhConfigDao.deleteWsjhDsqConfig(wdc);
	}

	public void updataWsjhDsqConfig(WsjhDsqConfig wdc) {

		wsjhConfigDao.updateWsjhDsqConfig(wdc);
	}

	public List<WsjhdzConfig> getWsjhdzConfigList(WsjhdzConfig wdc) {

		return wsjhConfigDao.getWsjhdzConfigList(wdc);
	}

	public void insertWsjhParamsConfig(WsjhParamsConfig wpc) {
		wsjhConfigDao.insertWsjhParamsConfig(wpc);
	}

	public List<WsjhParamsConfig> getWsjhParamsConfigList(WsjhParamsConfig wpc) {

		return wsjhConfigDao.getWsjhParamsConfigList(wpc);
	}

	public void batchInsertWsjhParamsConfig(List<WsjhParamsConfig> list,
											WsjhParamsConfig wpc) throws SQLException {
		this.wsjhConfigDao.deleteWsjhParamsConfig(wpc);
		for(WsjhParamsConfig wsjhParamsConfig : list){
			wsjhConfigDao.batchInsertWsjhParamsConfig(wsjhParamsConfig);
		}
		//return wsjhConfigDao.batchInsertWsjhParamsConfig(list);
	}

	public void deleteWsjhParamsConfig(WsjhParamsConfig wpc) {
		wsjhConfigDao.deleteWsjhParamsConfig(wpc);
	}

	public List<List<WsjhResult>> getWsjhResultAll(Object[] results,	List<WsjhdzConfig> wcList,Integer isType,String zys) throws DocumentException {
		List<List<WsjhResult>> wrsList = new ArrayList<List<WsjhResult>>();
		if(results!=null&&results.length>0){
			for(Object result:results){
				if(result!=null&&result instanceof String){
					String res = (String)result;
					if(!Tool.isNull(res)){
						if(isType.intValue()==0){//表示返回JSON内容
							setWsjhResultByJSON(wcList, wrsList, res);
						}else if(isType.intValue()==1){//返回XML内容
							setWsjhResultByXML(wcList, wrsList, res,zys);
						}else {//返回其他
						}
					}
				}
			}
		}
		return wrsList;
	}

	private void setWsjhResultByXML(List<WsjhdzConfig> wcList,
									List<List<WsjhResult>> wrsList, String res,String zys) throws DocumentException {
		Document doc = DocumentHelper.parseText(res);
		Element rootElement = doc.getRootElement();
		for(Iterator its= rootElement.elementIterator(zys);its.hasNext();){
			Element msgElement =(Element) its.next();
			List<WsjhResult> wrList = new ArrayList<WsjhResult>();
			setWsjhResultAll(wrList,msgElement,wcList);
			if(wrList.size()>0)wrsList.add(wrList);
		}
	}
	/**
	 * <p>获取JSON返回结果的数据</p>
	 * @param wcList
	 * @param wrList
	 * @param res
	 */
	private void setWsjhResultByJSON(List<WsjhdzConfig> wcList,
									 List<List<WsjhResult>> wrsList, String res) {
		JSONArray ja = JSONArray.fromObject(res);
		for(int i=0;i<ja.size();i++){
			JSONObject jo = (JSONObject)ja.get(i);
			List<WsjhResult> wrList = new ArrayList<WsjhResult>();
			setWsjhResultAll(jo,wcList,wrList);
			if(wrList.size()>0) wrsList.add(wrList);
		}
	}

	public String getUpdateSQLAll(List<WsjhResult> wrList,String tablename) {
		StringBuffer updateSQL = new StringBuffer();
		StringBuffer whereSQL = new StringBuffer();
		if(wrList!=null&&wrList.size()>0){
			for(WsjhResult wr:wrList){
				if(wr!=null){
					if(wr.getSfzj()!=null&&wr.getSfzj()!=1){//表示不是主键
						updateSQL.append(wr.getMbzd());
						String value = wr.getValue().trim();
						if(wr.getMbzdsjlx()==2||wr.getMbzdsjlx()==3||wr.getMbzdsjlx()==7||wr.getMbzdsjlx()==8){
							updateSQL.append("='").append(value==null?"":value.replaceAll("'","")).append("',");
						}else{
							if(!Tool.isNull(value)){
								updateSQL.append("=").append(value==null?"":value.replaceAll("'","")).append(",");
							}else{
								updateSQL.append("='").append(value==null?"":value.replaceAll("'","")).append("',");
							}
						}
					}else{//表示主键
						whereSQL.append(wr.getMbzd());
						if(wr.getMbzdsjlx()==2||wr.getMbzdsjlx()==3||wr.getMbzdsjlx()==7||wr.getMbzdsjlx()==8){
							whereSQL.append("='").append(wr.getValue().trim()).append("' and ");
						}else{
							whereSQL.append("=").append(wr.getValue().trim()).append(" and ");
						}
					}
				}
			}
		}

		String upSQL = updateSQL.toString();
		String whSQL = whereSQL.toString();
		if(upSQL.contains(","))upSQL = upSQL.substring(0,upSQL.lastIndexOf(","));
		if(whSQL.contains("and"))whSQL = whSQL.substring(0,whSQL.lastIndexOf("and"));
		if(!Tool.isNull(upSQL)&&!Tool.isNull(whSQL)&&!Tool.isNull(tablename)){
			return "update " +tablename+ " set "+upSQL +" where "+whSQL;
		}
		return null;
	}

	public List<String> getInsertSQLAll(List<List<WsjhResult>> wrsList,String tablename,Integer oflag) {
		List<String> insertSQLList = new ArrayList<String>();
		if(wrsList!=null&&wrsList.size()>0){
			for(List<WsjhResult> wrs:wrsList){
				if(wrs!=null&&wrsList.size()>0){
					StringBuffer key=new StringBuffer();
					StringBuffer result=new StringBuffer();
					initInsertWsjhSQL(wrs, key, result);
					String keys= key.toString();
					if(keys.contains(","))keys=keys.substring(0,keys.lastIndexOf(","));
					String values=result.toString();
					if(values.contains(","))values=values.substring(0,values.lastIndexOf(","));
					if(oflag!=null&&oflag.intValue()==1){
						keys+=",OPERATOR_FLAG";
						values+=",'I'";
					}
					String insertSQL = "insert into "+tablename+" ("+keys+") values ("+(values==null?"":values)+")";
					insertSQLList.add(insertSQL);
				}
			}
		}
		return insertSQLList;
	}
	/**
	 * <p>初始化插入WebService 交换SQL语句</p>
	 * @param wrs
	 * @param key
	 * @param result
	 */
	private void initInsertWsjhSQL(List<WsjhResult> wrs, StringBuffer key,StringBuffer result) {
		for(WsjhResult wr:wrs){
			if(wr!=null){
				key.append(wr.getMbzd()).append(",");
				String value = wr.getValue().trim();
				if(wr.getMbzdsjlx()==2||wr.getMbzdsjlx()==3||wr.getMbzdsjlx()==7||wr.getMbzdsjlx()==8){
					result.append("'").append(value==null?"":value.replaceAll("'","")).append("',");
				}else{
					if(!Tool.isNull(value)){
						result.append("").append(value.replaceAll("'","")).append(",");
					}else{
						result.append("'").append(value==null?"":value.replaceAll("'","")).append("',");
					}
				}
			}
		}
	}

	public List<WsjhResult> getWsjhResult(Object[] results,	List<WsjhdzConfig> wcList) throws DocumentException {

		if(results!=null&&results.length>0){
			for(Object result:results){
				if(result!=null&&result instanceof String){
					String res = (String)result;
					if(!Tool.isNull(res)){
						if(res.contains("[{")){//表示返回JSON内容
							return  getWsjhResultByJSON(wcList, res);
						}else if(res.contains("<data><msg>")){//返回XML内容
							return  getWsjhResultByXML(wcList,  res);
						}else{//返回字符串内容

						}
					}
				}
			}
		}
		return null;
	}
	/**
	 * <p>获取单行WebService 交换结果集</p>
	 * @param wcList
	 * @param res
	 * @return
	 * @throws DocumentException
	 */
	private List<WsjhResult> getWsjhResultByXML(List<WsjhdzConfig> wcList,	String res) throws DocumentException {
		Document doc = DocumentHelper.parseText(res);
		Element rootElement = doc.getRootElement();
		Element msgElement =(Element) rootElement.element("msg");
		List<WsjhResult> wrList = new ArrayList<WsjhResult>();
		setWsjhResultAll(wrList,msgElement,wcList);
		return wrList;

	}

	/**
	 * <p>获取单行WebService 交换结果集</p>
	 * @param wcList
	 * @param res
	 * @return
	 */
	private List<WsjhResult> getWsjhResultByJSON(List<WsjhdzConfig> wcList,	String res) {
		JSONArray ja = JSONArray.fromObject(res);
		for(int i=0;i<ja.size();i++){
			JSONObject jo = (JSONObject)ja.get(i);
			List<WsjhResult> wrList = new ArrayList<WsjhResult>();
			setWsjhResultAll(jo,wcList,wrList);
			return wrList;
		}
		return null;
	}

	public List<String> getUpdateSQLAll(String tablename,List<List<WsjhResult>> wrsList,Integer oflag) {
		if(wrsList!=null&&wrsList.size()>0){
			List<String> SQLList = new ArrayList<String>();
			for(List<WsjhResult> wrList:wrsList){
				if(wrList!=null){
					StringBuffer updateSQL = new StringBuffer();
					StringBuffer whereSQL = new StringBuffer();
					initWsjhSQL(wrList, updateSQL, whereSQL);
					String upSQL = updateSQL.toString();
					String whSQL = whereSQL.toString();
					if(upSQL.contains(","))upSQL = upSQL.substring(0,upSQL.lastIndexOf(","));
					if(whSQL.contains("and"))whSQL = whSQL.substring(0,whSQL.lastIndexOf("and"));
					if(!Tool.isNull(upSQL)&&!Tool.isNull(whSQL)&&!Tool.isNull(tablename)){
						if(oflag!=null&&oflag.intValue()==1){//表示更新操作标志
							upSQL+=",OPERATOR_FLAG='U'";
						}
						String uSQL =  "update " +tablename+ " set "+upSQL +" where "+whSQL;
						SQLList.add(uSQL);
					}
				}
			}
			return SQLList;
		}
		return null;
	}
	/**
	 * <p>初始化WebService 交换SQL语句</p>
	 * @param wrList
	 * @param updateSQL
	 * @param whereSQL
	 */
	private void initWsjhSQL(List<WsjhResult> wrList, StringBuffer updateSQL,StringBuffer whereSQL) {
		if(wrList!=null&&wrList.size()>0){
			for(WsjhResult wr:wrList){
				if(wr!=null){
					if(wr.getSfzj()!=null&&wr.getSfzj()!=1){//表示不是主键
						updateSQL.append(wr.getMbzd());
						String value = wr.getValue().trim();
						//2String 3Date 7Serializable 8Binary
						if(wr.getMbzdsjlx()==2||wr.getMbzdsjlx()==3||wr.getMbzdsjlx()==7||wr.getMbzdsjlx()==8){
							updateSQL.append("='").append(value==null?"":value.replaceAll("'","")).append("',");
						}else{
							if(!Tool.isNull(value)){
								updateSQL.append("=").append(value).append(",");
							}else{
								updateSQL.append("='").append(value==null?"":value.replaceAll("'","")).append("',");
							}

						}
					}else{//表示主键
						whereSQL.append(wr.getMbzd());
						if(wr.getMbzdsjlx()==2||wr.getMbzdsjlx()==3||wr.getMbzdsjlx()==7||wr.getMbzdsjlx()==8){
							whereSQL.append("='").append(wr.getValue().trim()).append("' and ");
						}else{
							whereSQL.append("=").append(wr.getValue().trim()).append(" and ");
						}
					}
				}
			}
		}
	}

	/**
	 * <p>获取返回消息</p>
	 * @param wpcList
	 * @param wcList
	 * @return
	 * @throws AxisFault
	 * @throws DocumentException
	 */
	public  String getMessage(List<WsjhParamsConfig> wpcList,List<WsjhdzConfig> wcList,WsjhConfig wc) throws AxisFault, DocumentException {

		WsjhRzb rzb = new WsjhRzb();
		rzb.setStartDate(DateTimeUtil.getCurrDateTimeStr());
		rzb.setWsjhxh(wc.getWsjhxh());
		String url = wc.getUrl();
		String namespace=wc.getNamespace();
		String operate=wc.getOperate();
		if(!Tool.isNull(url)){
			if(url.contains(".asmx")){
				processWebService(wpcList,wcList,wc,rzb, url, namespace, operate);
			}else{
				url=url.replace("?wsdl","").replace("?WSDL","");
				processWebServiceByAxis(wpcList, wcList, wc, rzb, url, namespace,operate);
			}
		}
		return "";
	}

	/**
	 * <p>处理WebService 接口</p>
	 * @param wpcList
	 * @param wcList
	 * @param wc
	 * @param rzb
	 * @param url
	 * @param namespace
	 * @param operate
	 */
	private void processWebService(List<WsjhParamsConfig> wpcList,List<WsjhdzConfig> wcList,WsjhConfig wc,WsjhRzb rzb, String url, String namespace,String operate) {
		try{
			Service service = new Service();
			Call   call   = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(url));
			QName qnName = new QName(namespace,operate);
			call.setOperationName(qnName);
			String params = wc.getParams();
			if(params!=null){//有参数
				addParameterByCall(wpcList,call,namespace,operate,wc);
				insertMbkByParamsAndCall(wpcList, wcList, wc, rzb, call, params);
			}else{//没有参数
				insertMbkByNotParamsAndCall(wcList, wc, rzb, call);
			}

		}catch(Exception e){
			rzb.setErrors(rzb.getErrors()==null?1:rzb.getErrors());
			rzb.setLog(rzb.getLog()+" "+e.getMessage());
			e.printStackTrace();
			System.gc();
		}
		rzb.setEndDate(DateTimeUtil.getCurrDateTimeStr());
		rzb.setLogDate(DateTimeUtil.getCurrDateTimeStr());
		insertWsjhrzb(rzb);
	}

	private void insertMbkByNotParamsAndCall(List<WsjhdzConfig> wcList,	WsjhConfig wc, WsjhRzb rzb, Call call) throws RemoteException,
			DocumentException {
		String objs = (String)call.invoke(new Object[] {});
		if(!Tool.isNull(objs)&&objs.contains("<NewDataSet>")){//格式 <NewDataSet><Table></Table><Table></Table></NewDataSet>
			List<List<WsjhResult>> wrsList =XmlHelper.getList(objs,"Table",wcList,null,null);
			StringBuffer logs = new StringBuffer();
			if(wrsList!=null&&wrsList.size()>0){
				SjkpzWrapper sjkpzWrapper = this.sjkpzService.getSjkpzDetail(wc.getWsjhds());
				DatabaseMeta databaseMeta = DbUtil.getPoolMeta("system", sjkpzWrapper.getLjlxmc(), sjkpzWrapper.getSjklxmc(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getSjkmc()), sjkpzWrapper.getIpdz(), sjkpzWrapper.getDkh(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getYhm()), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getMm()));
				String tablename = wc.getWsjhb();
				int size = wrsList.size();//总记录数
				rzb.setReadLines(size);
				int count = 0;int pageSize = 1000;
				if(size%pageSize==0){
					count=size/pageSize;
				}else{
					count=size/pageSize+1;
				}
				execWsjh(rzb, databaseMeta, tablename, wrsList, logs,count, pageSize,wc.getOflag());
			}
			rzb.setLog(logs.toString());
		}
	}

	private void insertMbkByParamsAndCall(List<WsjhParamsConfig> wpcList,List<WsjhdzConfig> wcList, WsjhConfig wc, WsjhRzb rzb, Call call,
										  String params) throws RemoteException, DocumentException {
		SjkpzWrapper sjkpzWrapper = this.sjkpzService.getSjkpzDetail(wc.getWsjhds());
		DatabaseMeta databaseMeta = DbUtil.getPoolMeta("system", sjkpzWrapper.getLjlxmc(), sjkpzWrapper.getSjklxmc(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getSjkmc()), sjkpzWrapper.getIpdz(), sjkpzWrapper.getDkh(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getYhm()), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getMm()));
		String tablename = wc.getWsjhzcb();
		Integer isType=wc.getIstype();//返回类型
		//int isType=wc.getIstype();//返回类型
		String zys = wc.getZys();
		params = getParams(wpcList,params);
		String sql = "select "+params+" from "+tablename;
		List<Map<String,String>>  paramsMap = DbUtil.getValueByTable(databaseMeta,sql,wpcList,rzb);
		tablename=wc.getWsjhb();//目的表

		StringBuffer logs = new StringBuffer();
		if(paramsMap!=null&&paramsMap.size()>0){
			rzb.setReadLines(paramsMap.size());//总记录数
			int size = paramsMap.size();//总记录数
			int count = 0;int pageSize = 1000;
			if(size%pageSize==0){
				count=size/pageSize;
			}else{
				count=size/pageSize+1;
			}
			int pageNo=1; int updateLines = 0;int errors=0;
			while(count>=pageNo){
				List<List<WsjhResult>> wrsList=getWsjhResultAllList(wpcList, wcList, call, params, paramsMap, pageSize, pageNo,rzb,isType,zys);
				if(wrsList!=null){
					Map<String,Integer> map = execWsjhSQL(wrsList,databaseMeta,tablename,logs,wc.getOflag());
					Integer uLines = map.get("updateLines");
					Integer error = map.get("errors");
					if(uLines!=null)updateLines+=uLines;
					if(error!=null)errors+=error;
					pageNo++;
				}else{
					if(rzb.getErrors()>0){
						return ;
					}
				}
			}
			rzb.setUpdateLines(updateLines);
			rzb.setErrors(errors);
		}
		rzb.setLog(logs.toString());
	}

	@SuppressWarnings("unchecked")
	private  synchronized List<List<WsjhResult>> getWsjhResultAllList(List<WsjhParamsConfig> wpcList, List<WsjhdzConfig> wcList,
																	  Call call, String params, List<Map<String, String>> paramsMap,	int pageSize, int pageNo, WsjhRzb rzb,Integer type,String zys) throws RemoteException, DocumentException {
		List<List<WsjhResult>> wrsList = new ArrayList<List<WsjhResult>>();
		List<Map<String,String>> list =(List<Map<String,String>>) ArrayUtil.getList(paramsMap, pageNo, pageSize);

		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				LOG.info("传参次数:"+(i+1));
				Map<String,String> vmap= (Map<String,String>)list.get(i);
				if(vmap!=null&&vmap.size()>0){
					Object[] args = getValueParams(wpcList,params,vmap);
					String result = (String)call.invoke(args);
					LOG.info("传参返回结果："+result);
					if(1==type){//XML格式
						List<List<WsjhResult>> wrList = XmlHelper.getList(result,zys,wcList,vmap,wpcList);
						if(wrList!=null&&wrList.size()>0){
							wrsList.addAll(wrList);
						}
					}
				}
			}
		}
		return wrsList;
	}

	private void addParameterByCall(List<WsjhParamsConfig> wpcList, Call call,String namespace, String operate,WsjhConfig wc) {
		//if(wc.getPlbs()!=null&&wc.getPlbs().intValue()==1){//批量报送
		if(wc.getPlbs() == 1){
			call.addParameter(new QName(namespace,wc.getParams()),
					org.apache.axis.encoding.XMLType.XSD_STRING,
					javax.xml.rpc.ParameterMode.IN);
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型
			call.setUseSOAPAction(true);
			call.setSOAPActionURI(namespace+operate);
		}
		if(wpcList!=null&&wpcList.size()>0){//方法没有参数
			for(WsjhParamsConfig wpc :wpcList){
				if(wpc!=null&&!Tool.isNull(wpc.getCsz())){
					call.addParameter(new QName(namespace,wpc.getCszd()),
							org.apache.axis.encoding.XMLType.XSD_STRING,
							javax.xml.rpc.ParameterMode.IN);
				}
			}
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型
			call.setUseSOAPAction(true);
			call.setSOAPActionURI(namespace+operate);

		}
	}

	/**
	 * <p>根据Axis 处理WebService 调用接口</p>
	 * @param wpcList
	 * @param wcList
	 * @param wc
	 * @param rzb
	 * @param url
	 * @param namespace
	 * @param operate
	 * @throws AxisFault
	 */
	private void processWebServiceByAxis(List<WsjhParamsConfig> wpcList,List<WsjhdzConfig> wcList, WsjhConfig wc, WsjhRzb rzb, String url,
										 String namespace, String operate) throws AxisFault {
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		EndpointReference targetEPR = new EndpointReference(url);
		options.setTo(targetEPR);
		QName opAddEntry = new QName(namespace,operate);
		String params = wc.getParams();
		try{
			if(params!=null){
				//含有分页，密钥  (公司内部使用一定有参数）
				if(params.contains("PAGE")&&params.contains("PAGESIZE")&&params.contains("SIGN")){
					//参数只有分页密钥
					if(params.equals("PAGE,PAGESIZE,SIGN")){
						insertMbkByParamsInPageAndPageSize(wpcList, wcList, wc,serviceClient, opAddEntry,params,rzb);
					}else{//除了分页密钥外还有其他参数
						updateMbkByParamsInPageAndPageSizeAndOther(wpcList, wcList, wc,serviceClient, opAddEntry, params,rzb);
					}
				}else if(params.contains("SIGN")){//含有密钥
					//参数只有密钥
					if(params.equals("SIGN")){
						insetMbkByParamsInSIGN(wpcList, wcList, wc, serviceClient,opAddEntry,rzb);
					}else{//密钥与其他参数updateMbkByParamsInSIGNAndOther
						updateMbkByParamsInSIGNAndOther(wpcList, wcList, wc,serviceClient, opAddEntry, params,rzb);
					}
				}else{//调用其他WebService
					updateMbkByParamsInSIGNAnd(wpcList, wcList, wc,serviceClient, opAddEntry, params,rzb);
				}
			}else{//没有参数

			}

		}catch(Exception e){
			e.printStackTrace();
			rzb.setErrors(rzb.getErrors()==null?1:rzb.getErrors());
			rzb.setLog(rzb.getLog()+" "+e.getMessage());
			serviceClient.cleanup();
			System.gc();
		}
		rzb.setEndDate(DateTimeUtil.getCurrDateTimeStr());
		rzb.setLogDate(DateTimeUtil.getCurrDateTimeStr());
		insertWsjhrzb(rzb);
		//if(wc.getUtffzc()!=null&&wc.getUtffzc().intValue()==1){//表示更新时间参数值
		//	insertWsjhzcb(wc.getWsjhzcb(),wc.getUtffzcv(),(rzb.getSjc()==null?rzb.getTempTime():rzb.getSjc()),wc.getWsjhds());
		//}
		serviceClient.cleanup();
	}
	private void updateMbkByParamsInSIGNAnd(List<WsjhParamsConfig> wpcList, List<WsjhdzConfig> wcList,
											WsjhConfig wc, RPCServiceClient serviceClient, QName opAddEntry,String params,WsjhRzb rzb) throws AxisFault, DocumentException {
		//params=params.replace(",SIGN", "");
		String param = getParams(wpcList,params);
		SjkpzWrapper sjkpzWrapper = this.sjkpzService.getSjkpzDetail(wc.getWsjhds());
		DatabaseMeta databaseMeta = DbUtil.getPoolMeta("system", sjkpzWrapper.getLjlxmc(), sjkpzWrapper.getSjklxmc(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getSjkmc()), sjkpzWrapper.getIpdz(), sjkpzWrapper.getDkh(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getYhm()), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getMm()));
		String tablename = wc.getWsjhzcb();//传参数据库
		List<Map<String,String>> paramsMap =null;
		//Integer isSjc = wc.getIsSjc();//启用时间戳 1是0否
		int isSjc = wc.getIssjc();//启用时间戳 1是0否
		WsjhRzb wr = null;
		//if(isSjc!=null&&isSjc.intValue()==1){//启用时间戳
		if(isSjc == 1){
			wr = getLastWsjhRzbByWsjhxh(wc.getWsjhxh());
			List<List<WsjhResult>> wrsList = null;
			if(wr!=null&&!Tool.isNull(wr.getSjc())){//启用时间戳变量
				String type = DbUtil.getSjcType(wr.getSjc(),tablename,databaseMeta);
				type=type==null?"VARCHAR2":type;
				String sql = "select "+param+" from "+tablename+" where ";
				if("DATE".equals(type)){
					sql+=" to_char("+wc.getSjcz()+",'yyyymmdd') >='"+wr.getSjc()+"'";
				}else{
					sql+=wc.getSjcz()+ " >='"+wr.getSjc()+"'";
				}
				paramsMap = DbUtil.getValueByTable(databaseMeta,sql,wpcList,rzb);
			}else{//第一次没有记录
				String sql = "select "+param+" from "+tablename;
				paramsMap = DbUtil.getValueByTable(databaseMeta,sql,wpcList,rzb);
			}
			//获取最后一次时间
			String nextDay = getNextDay(wrsList,wc.getSjcz());
			rzb.setSjc(nextDay);
		}else{
			String sql = "select "+param+" from "+tablename;
			paramsMap = DbUtil.getValueByTable(databaseMeta,sql,wpcList,rzb);
		}
		tablename=wc.getWsjhb();//目标表
		execWsjh(wpcList, wcList, serviceClient, opAddEntry, params, rzb,databaseMeta, tablename, paramsMap, isSjc, wr,wc);
	}

	/**
	 * <p>插入更新时间参数值</p>
	 * @param wsjhzcb
	 * @param utffzcv
	 * @param sjc
	 * @param wsjhds
	 */
	private void insertWsjhzcb(String wsjhzcb, String utffzcv, String sjc,String wsjhds) {
		SjkpzWrapper sjkpzWrapper = this.sjkpzService.getSjkpzDetail(wsjhds);
		DatabaseMeta databaseMeta = DbUtil.getPoolMeta("system", sjkpzWrapper.getLjlxmc(), sjkpzWrapper.getSjklxmc(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getSjkmc()), sjkpzWrapper.getIpdz(), sjkpzWrapper.getDkh(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getYhm()), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getMm()));
		String updateSQL = "update "+utffzcv+"='"+sjc+"' from "+wsjhzcb;
		DbUtil.execUpdateSQL(databaseMeta, updateSQL);
	}

	/**
	 * <p>根据WebService 方法参数（包含密钥/分页/其他参数）获取交换结果并插入目标库</p>
	 * @param wpcList
	 * @param wcList
	 * @param wc
	 * @param serviceClient
	 * @param opAddEntry
	 * @param params
	 * @throws AxisFault
	 * @throws DocumentException
	 */
	private void updateMbkByParamsInPageAndPageSizeAndOther(List<WsjhParamsConfig> wpcList, List<WsjhdzConfig> wcList,
															WsjhConfig wc, RPCServiceClient serviceClient, QName opAddEntry,String params,WsjhRzb rzb) throws AxisFault, DocumentException {
		params = params.replace(",PAGE,PAGESIZE,SIGN","");
		String param =getParams(wpcList,params);
		SjkpzWrapper sjkpzWrapper = this.sjkpzService.getSjkpzDetail(wc.getWsjhds());
		DatabaseMeta databaseMeta = DbUtil.getPoolMeta("system", sjkpzWrapper.getLjlxmc(), sjkpzWrapper.getSjklxmc(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getSjkmc()), sjkpzWrapper.getIpdz(), sjkpzWrapper.getDkh(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getYhm()), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getMm()));
		String tablename = wc.getWsjhzcb();//传参数据表
		List<Map<String,String>> paramsMap =null;
		String sql = "";
		boolean flag = isQueryAll(wpcList);//判断两个分页参数是否为空/如果为空表示查询所有
		if(flag){
			sql = "select "+param+" from "+tablename;
		}else{
			sql = getSQLByPageAndPageSize(wpcList,param,tablename);
		}
		//Integer isSjc = wc.getIsSjc();//启用时间戳 1是0否
		int isSjc = wc.getIssjc();//启用时间戳 1是0否
		WsjhRzb wr = null;
		//if(isSjc!=null&&isSjc.intValue()==1){//启用时间戳
		if(isSjc == 1){
			wr = getLastWsjhRzbByWsjhxh(wc.getWsjhxh());
			List<List<WsjhResult>> wrsList = null;
			if(wr!=null&&!Tool.isNull(wr.getSjc())){//启用时间戳变量
				String type = DbUtil.getSjcType(wr.getSjc(),tablename,databaseMeta);
				type=type==null?"VARCHAR2":type;
				if("DATE".equals(type)){
					if(sql.contains("where")){
						sql+=" and to_char("+wc.getSjcz()+",'yyyymmdd') >='"+wr.getSjc()+"'";
					}else{
						sql+=" where to_char("+wc.getSjcz()+",'yyyymmdd') >='"+wr.getSjc()+"'";
					}
				}else{//字符串类型
					if(sql.contains("where")){
						sql+=" and "+wc.getSjcz()+" >='"+wr.getSjc()+"'";
					}else{
						sql+=" where "+wc.getSjcz()+">='"+wr.getSjc()+"'";
					}
				}
				paramsMap = DbUtil.getValueByTable(databaseMeta,sql,wpcList,rzb);
			}else{//第一次没有记录
				paramsMap = DbUtil.getValueByTable(databaseMeta,sql,wpcList,rzb);
			}
			//获取最后一次时间
			String nextDay = getNextDay(wrsList,wc.getSjcz());
			rzb.setSjc(nextDay);
		}else{
			paramsMap = DbUtil.getValueByTable(databaseMeta,sql,wpcList,rzb);
		}
		tablename=wc.getWsjhb();//目的表
		execWsjh(wpcList, wcList, serviceClient, opAddEntry, params, rzb,databaseMeta, tablename, paramsMap, isSjc, wr,wc);

	}



	private void execWsjh(List<WsjhParamsConfig> wpcList,List<WsjhdzConfig> wcList, RPCServiceClient serviceClient,
						  QName opAddEntry, String params, WsjhRzb rzb,DatabaseMeta databaseMeta, String tablename,List<Map<String, String>> paramsMap, Integer isSjc, WsjhRzb wr,WsjhConfig wc)
			throws AxisFault, DocumentException {
		StringBuffer logs = new StringBuffer();
		if(paramsMap!=null&&paramsMap.size()>0){
			rzb.setReadLines(paramsMap.size());//总记录数
			int size = paramsMap.size();//总记录数
			int count = 0;int pageSize = 1000;
			if(size%pageSize==0){
				count=size/pageSize;
			}else{
				count=size/pageSize+1;
			}
			int pageNo=1; int updateLines = 0;int errors=0;
			while(count>=pageNo){
				List<List<WsjhResult>> wrsList=getWsjhResultAllList(wpcList, wcList, serviceClient,opAddEntry, params, paramsMap, pageSize, pageNo,rzb,wc);
				if(wrsList!=null){
					Map<String,Integer> map = execWsjhSQL(wrsList,databaseMeta,tablename,logs,wc.getOflag());
					Integer uLines = map.get("updateLines");
					Integer error = map.get("errors");
					if(uLines!=null)updateLines+=uLines;
					if(error!=null)errors+=error;
					pageNo++;
				}else{
					if(rzb.getErrors()>0){
						return ;
					}
				}
			}
			rzb.setUpdateLines(updateLines);
			rzb.setErrors(errors);
			if(errors>0&&isSjc!=null&&isSjc.intValue()==1){//启用时间戳//如果错误发生，则标记为以前的时间戳
				if(wr!=null)rzb.setSjc(wr.getSjc());
			}
		}
		rzb.setLog(logs.toString());
	}

	/***
	 * MD5加密 生成32位md5码
	 * @param 待加密字符串
	 * @return 返回32位md5码
	 */
	public static String md5Encode(String inStr) throws Exception {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}

		byte[] byteArray = inStr.getBytes("UTF-8");
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	private List<List<WsjhResult>> getWsjhResultAllList(List<WsjhParamsConfig> wpcList,	List<WsjhdzConfig> wcList, RPCServiceClient serviceClient,
														QName opAddEntry, String params,	List<Map<String, String>> paramsMap, int pageSize, int pageNo,WsjhRzb rzb,WsjhConfig wc) throws AxisFault, DocumentException {
		List<List<WsjhResult>> wrsList = new ArrayList<List<WsjhResult>>();
		List<Map<String,String>> list =(List<Map<String,String>>) ArrayUtil.getList(paramsMap, pageNo, pageSize);
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Map<String,String> vmap= (Map<String,String>)list.get(i);
				//start write by zhangxu 对西北工业大学的通用加密处理
				String wcParams= wc.getParams();
				String yhm = vmap.get("un");
				if(!StringUtils.isEmpty(yhm)){
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
					String currentTime = df.format(new Date());
					LOG.info("调用西北工业大汇文接口：" + "un=" + yhm + "");
					System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
					vmap.put("time", currentTime);
					try {
						String verify = md5Encode(yhm + Config.getString("huiwen", "huiwen") + currentTime);
						vmap.put("verify", verify);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					wcParams += "time,verify";
					WsjhParamsConfig timeconfig = new WsjhParamsConfig();
					timeconfig.setCszd("time");
					timeconfig.setCszdt("String");
					WsjhParamsConfig verifyconfig = new WsjhParamsConfig();
					verifyconfig.setCszd("verify");
					verifyconfig.setCszdt("String");
					wpcList.add(timeconfig);
					wpcList.add(verifyconfig);
				}
				//end write by zhangxu

				if(vmap!=null&&vmap.size()>0){

					Object[] opAddEntryArgs = getValueParams(wpcList,wcParams,vmap);//by zhangxu
					//Object[] opAddEntryArgs = getValueParams(wpcList,wc.getParams(),vmap);
					Class[] classes = getOpAddEntryArgs(opAddEntryArgs);
					//Object[] results = serviceClient.invokeBlocking(opAddEntry,opAddEntryArgs,classes);
					Object[] results = serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes);
					boolean flag = verifyResults(results,rzb);
					if(flag){
						List<List<WsjhResult>> wrstList = getWsjhResultAll(results,wcList,new Integer(wc.getIstype()),wc.getZys());
						if(wrstList!=null&&wrstList.size()>0)wrsList.addAll(wrstList);
					}else{
						return null;//错误发生即中止
					}
				}
			}
		}
		return wrsList;
	}


	/**
	 * <p>根据WebServcie 方法参数(包含密钥/分页）获取交换结果并插入目标库</p>
	 * @param wpcList
	 * @param wcList
	 * @param wc
	 * @param serviceClient
	 * @param opAddEntry
	 * @throws AxisFault
	 * @throws DocumentException
	 */
	private void insertMbkByParamsInPageAndPageSize(List<WsjhParamsConfig> wpcList, List<WsjhdzConfig> wcList,
													WsjhConfig wc, RPCServiceClient serviceClient, QName opAddEntry,String params,WsjhRzb rzb) throws AxisFault, DocumentException {
		SjkpzWrapper sjkpzWrapper = this.sjkpzService.getSjkpzDetail(wc.getWsjhds());
		DatabaseMeta databaseMeta = DbUtil.getPoolMeta("system", sjkpzWrapper.getLjlxmc(), sjkpzWrapper.getSjklxmc(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getSjkmc()), sjkpzWrapper.getIpdz(), sjkpzWrapper.getDkh(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getYhm()), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getMm()));
		String tablename = wc.getWsjhb();
		Object[] opAddEntryArgs = getValueParams(wpcList,params);
		Class[] classes = getOpAddEntryArgs(opAddEntryArgs);
		Object[] results = serviceClient.invokeBlocking(opAddEntry,opAddEntryArgs,classes);
		boolean flag = verifyResults(results,rzb);//验证结果是否正确
		if(flag){//表示返回正确结果(返回结果为错误)
			//Integer isSjc = wc.getIsSjc();//启用时间戳 1是0否
			int isSjc = wc.getIssjc();//启用时间戳 1是0否
			//if(isSjc!=null&&isSjc.intValue()==1){//启用时间戳
			if(isSjc == 1){
				insertMbkByParamsInPageAndPageSizeAndSjc(wpcList,wcList,rzb,databaseMeta,tablename,results,wc);
			}else{
				insertMbkByParamsInPageAndPageSize(wpcList, wcList, rzb,databaseMeta, tablename, results,wc);
			}
		}
	}
	private  boolean  verifyResults(Object[] results, WsjhRzb rzb) {
		if(results!=null&&results.length>0){
			for(Object obj:results){
				if(obj!=null&&obj instanceof String){
					String res = (String)obj;
					if(!Tool.isNull(res)){
						if(res.contains("<errors><error>")){
							rzb.setErrors(1);
							rzb.setLog(res.replaceAll("<errors><error>", "").replaceAll("</errors></error>", ""));
							rzb.setLogDate(DateTimeUtil.getCurrDateTimeStr());
							return false;
						}else if (res.contains("[{")&&res.contains("\"error\"")){
							rzb.setErrors(1);
							JSONArray ja = JSONArray.fromObject(res);
							for(int i=0;i<ja.size();i++){
								JSONObject jo = (JSONObject)ja.get(i);
								rzb.setLog(String.valueOf(jo.get("error")));
							}
							rzb.setLogDate(DateTimeUtil.getCurrDateTimeStr());
							return false;
						}else{
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * <p>启用时间戳根据WebService方法参数（包含密钥/分页）获取交换结果并插入目标库</p>
	 * @param wpcList
	 * @param wcList
	 * @param rzb
	 * @param databaseMeta
	 * @param tablename
	 * @param results
	 * @throws DocumentException
	 */
	private void insertMbkByParamsInPageAndPageSizeAndSjc(List<WsjhParamsConfig> wpcList, List<WsjhdzConfig> wcList,
														  WsjhRzb rzb, DatabaseMeta databaseMeta, String tablename,Object[] results,WsjhConfig wc) throws DocumentException {
		WsjhRzb wr = getLastWsjhRzbByWsjhxh(wc.getWsjhxh());
		List<List<WsjhResult>> wrsList = null;
		if(wr!=null&&!Tool.isNull(wr.getSjc())){//启用时间戳变量
			wrsList = getWsjhResultAllBySjc(results,wcList,wc.getSjcz(),wr.getSjc(),new Integer(wc.getIstype()),wc.getZys());
			//根据时间标记删除当天以上的数据
			String type = DbUtil.getSjcType(wr.getSjc(),tablename,databaseMeta);
			type=type==null?"VARCHAR2":type;
			String deleteSQL = "delete from "+tablename+" where ";
			if("DATE".equals(type)){
				deleteSQL +=" to_char("+wc.getSjcz()+",'yyyymmdd') >='"+wr.getSjc()+"'";
			}else{
				deleteSQL+=wc.getSjcz()+ " >='"+wr.getSjc()+"'";
			}
			DbUtil.execUpdateSQL(databaseMeta,deleteSQL);
		}else{//第一次没有记录
			wrsList = getWsjhResultAllBySjc(results,wcList,wc.getSjcz(),null,new Integer(wc.getIstype()),wc.getZys());
		}
		//获取最后一次时间
		String nextDay = getNextDay(wrsList,wc.getSjcz());
		rzb.setSjc(nextDay);
		String logs = getWsjhLogs(rzb, databaseMeta, tablename, wc, wr, wrsList);
		rzb.setLog(logs);

	}
	/**
	 * <p>获取执行WebService 交换日志</p>
	 * @param rzb
	 * @param databaseMeta
	 * @param tablename
	 * @param wc
	 * @param wr
	 * @param wrsList
	 * @return
	 */
	private String getWsjhLogs(WsjhRzb rzb, DatabaseMeta databaseMeta,String tablename, WsjhConfig wc, WsjhRzb wr,
							   List<List<WsjhResult>> wrsList) {
		StringBuffer logs = new StringBuffer();
		if(wrsList!=null&&wrsList.size()>0){
			rzb.setReadLines(wrsList.size());
			int size = wrsList.size();//总记录数
			int count = 0;int pageSize = 1000;
			if(size%pageSize==0){
				count=size/pageSize;
			}else{
				count=size/pageSize+1;
			}
			int errors = execWsjh(rzb, databaseMeta, tablename, wrsList, logs,count, pageSize,wc.getOflag());
			//if(errors>0&&wc.getIsSjc()!=null&&wc.getIsSjc().intValue()==1){//启用时间戳//如果错误发生，则标记为以前的时间戳
			if(errors>0 && wc.getIssjc() == 1){
				if(wr!=null)rzb.setSjc(wr.getSjc());
			}
		}
		return logs.toString();
	}
	/**
	 * <p>执行WebService 交换并返回错误数</p>
	 * @param rzb
	 * @param databaseMeta
	 * @param tablename
	 * @param wrsList
	 * @param logs
	 * @param count
	 * @param pageSize
	 * @return
	 */
	private int execWsjh(WsjhRzb rzb, DatabaseMeta databaseMeta,String tablename, List<List<WsjhResult>> wrsList,
						 StringBuffer logs, int count, int pageSize,Integer oflag) {
		int pageNo=1;int updateLines=0;int errors=0;
		while(count>=pageNo){
			List<List<WsjhResult>> list =(List<List<WsjhResult>>) ArrayUtil.getList(wrsList, pageNo, pageSize);
			Map<String,Integer> map = execWsjhSQL(list,databaseMeta,tablename,logs,oflag);
			Integer uLines = map.get("updateLines");
			Integer error = map.get("errors");
			if(uLines!=null)updateLines+=uLines;
			if(error!=null)errors+=error;
			pageNo++;
		}
		rzb.setUpdateLines(updateLines);
		rzb.setErrors(errors);
		return errors;
	}

	private Map<String, Integer> execWsjhSQL(List<List<WsjhResult>> list,DatabaseMeta databaseMeta,String tablename,StringBuffer logs,Integer oflag) {
		Map<String,String> pkeyMap = getPrimaryKeyMap(list);
		List<Map<String,String>> existsKeyList = getExistsPrimaryKeyMap(databaseMeta,tablename,pkeyMap);
		if(existsKeyList!=null&&existsKeyList.size()>0){//表示不为空，则有部分数据需要进行UPDATE操作
			if(list.size()==existsKeyList.size()){//全部更新操作
				Map<String,Integer> map = execWsjhSQL(list,databaseMeta,logs,true,tablename,oflag);
				return map;
			}else{//局部更新操作
				List<List<WsjhResult>> updateList = getWsjhResultList(list,existsKeyList);
				Map<String,Integer> map = execWsjhSQL(updateList,databaseMeta,logs,true,tablename,oflag);
				if(updateList!=null&&updateList.size()>0)list.removeAll(updateList);
				Map<String,Integer> insertMap = execWsjhSQL(list,databaseMeta,logs,false,tablename,oflag);
				Integer uLines = map.get("updateLines");
				Integer error = map.get("errors");
				map.put("updateLines", (uLines==null?0:uLines)+(insertMap.get("updateLines")==null?0:insertMap.get("updateLines")));
				map.put("errors", (error==null?0:error)+(insertMap.get("error")==null?0:insertMap.get("error")));
				return map;
			}
		}else{//不存在此数据，需要进行insert操作
			Map<String,Integer> map = execWsjhSQL(list,databaseMeta,logs,false,tablename,oflag);
			return map;
		}
	}

	/**
	 * <p>执行WebService 交换SQL</p>
	 * @param list
	 * @param databaseMeta
	 * @param logs
	 * @param flag
	 * @param tablename
	 * @return
	 */
	private Map<String,Integer> execWsjhSQL(List<List<WsjhResult>> list,DatabaseMeta databaseMeta,StringBuffer logs,boolean flag,String tablename,Integer oflag){
		List<String> sqlList = null;
		if(flag){//表示更新
			sqlList = getUpdateSQLAll(tablename,list,oflag);
		}else{//插入
			sqlList = getInsertSQLAll(list,tablename,oflag);
		}
		Map<String,Integer> map = new HashMap<String,Integer>();
		if(sqlList!=null&&sqlList.size()>0){
			try{
				Set<String> sqlSet = new HashSet<String>();
				sqlSet.addAll(sqlList);
				DbUtil.batchUpdateSQL(databaseMeta,new ArrayList<String>(sqlSet));
				map.put("updateLines", sqlList.size());
			}catch(Exception e){
				map.put("errors", sqlList.size());
				logs.append(e.getMessage()).append(" :").append(sqlList.toString());;
			}
		}
		return map;
	}



	/**
	 * <p>根据过滤条件获取对应的WebService交换结果集</p>
	 * @param wrsList
	 * @param mapList
	 * @param flag
	 * @return
	 */
	private List<List<WsjhResult>> getWsjhResultList(List<List<WsjhResult>> wrsList,List<Map<String, String>> mapList) {
		if(mapList!=null&&mapList.size()>0){
			List<List<WsjhResult>> existsWrList = new ArrayList<List<WsjhResult>>();
			for(Map<String,String> map:mapList){
				if(map!=null&&map.size()>0){
					Set<String> mbzdSet =map.keySet();//所有的名称
					for(String mbzd:mbzdSet){
						if(!Tool.isNull(mbzd)){
							List<WsjhResult> wrList = getWsjhResultListByExistsKey(mbzd,map.get(mbzd),wrsList);
							if(wrList!=null&&wrList.size()>0){
								existsWrList.add(wrList);
							}
						}
					}
				}
			}
			return existsWrList;
		}
		return null;
	}

	private List<WsjhResult> getWsjhResultListByExistsKey(String mbzd,	String mbzdv, List<List<WsjhResult>> wrsList) {
		if(wrsList!=null&&wrsList.size()>0){
			for(List<WsjhResult> wrList:wrsList){
				if(wrList!=null&&wrList.size()>0){
					boolean flag = getWsjhResultListByExistsKey(wrList,mbzd,mbzdv);
					if(flag)return wrList;
				}
			}
		}
		return null;
	}

	private boolean getWsjhResultListByExistsKey(List<WsjhResult> wrList,String mbzd, String mbzdv) {
		if(wrList!=null&&wrList.size()>0){
			for(WsjhResult wr:wrList){
				if(wr!=null&&wr.getSfzj()!=null&&wr.getSfzj().intValue()==1){//表示主键
					if(!Tool.isNull(wr.getMbzd())&&wr.getMbzd().equals(mbzd)&&!Tool.isNull(wr.getValue())&&wr.getValue().equals(mbzdv))
						return true;
				}
			}
		}
		return false;
	}



	/**
	 * <p>获取存在主键的数据结果集</p>
	 * @param databaseMeta
	 * @param tablename
	 * @param pkeyMap
	 * @return
	 */
	private List<Map<String, String>> getExistsPrimaryKeyMap(DatabaseMeta databaseMeta, String tablename,Map<String, String> pkeyMap) {
		String sql = getQuerySQLByPrimaryKeyMap(pkeyMap,tablename);
		if(!Tool.isNull(sql))return DbUtil.execQuerySQL(databaseMeta,sql,pkeyMap.keySet());
		return null;
	}

	private String getQuerySQLByPrimaryKeyMap(Map<String, String> pkeyMap, String tablename) {
		if(pkeyMap!=null&&pkeyMap.size()>0){
			Set<String> set =pkeyMap.keySet();
			String params = StringUtils.join(set,",");
			StringBuffer queryKey = new StringBuffer();
			for(Iterator<Entry<String,String>> it = pkeyMap.entrySet().iterator();it.hasNext();){
				Entry<String,String> entry = it.next();
				String key = entry.getKey();
				String value = entry.getValue();
				if(!Tool.isNull(key)&&!Tool.isNull(value)){
					setQuerySQLByPrimaryKey(queryKey, key, value);
				}
			}
			String query = queryKey.toString();
			if(query.contains("and"))query = query.substring(0,query.lastIndexOf("and"));
			return  " select distinct "+params +" from "+tablename+" where "+query;
		}
		return null;
	}

	private void setQuerySQLByPrimaryKey(StringBuffer queryKey, String key,	String value) {
		StringBuffer vstr = new StringBuffer();
		String[] vs = value.split(",");
		if(vs!=null&&vs.length>0){
			int i=0;
			for(String s:vs){
				if(!Tool.isNull(s)){
					if((i%1000)==0&&i>0){
						vstr.deleteCharAt(vstr.length()-1);
						vstr.append(" ) or ").append(key).append(" in ( ").append("'").append(s.trim()).append("'").append(",");
					}else{
						vstr.append("'").append(s.trim()).append("'").append(",");
					}
					i++;
				}
			}
			vstr.deleteCharAt(vstr.length()-1);
			value=vstr.toString();
			queryKey.append(key).append(" in (").append(value.trim()).append(")").append(" and ");
		}
	}

	/**
	 * <p>获取主键对应的值</p>
	 * @param list
	 * @param wcList
	 * @return
	 */
	private Map<String, String> getPrimaryKeyMap(List<List<WsjhResult>> list) {
		Map<String, String> pkeyMap =  new HashMap<String,String>();
		if(list!=null&&list.size()>0){
			for(List<WsjhResult> wrList:list){
				if(wrList!=null&&wrList.size()>0){
					for(WsjhResult wr:wrList){
						if(wr!=null&&wr.getSfzj()!=null&&wr.getSfzj().intValue()==1){//表示主键
							String value= pkeyMap.get(wr.getMbzd());
							if(!Tool.isNull(value)&&!isNotContainsStr(value,wr.getValue().trim())){
								if(wr.getValue()!=null&&!wr.getValue().trim().equals(""))
									value+=wr.getValue().trim()+",";
							}else if(isNotContainsStr(value,wr.getValue().trim())){

							}else{
								if(wr.getValue()!=null&&!wr.getValue().trim().equals(""))
									value=wr.getValue().trim()+",";
							}
							pkeyMap.put(wr.getMbzd(), value);
						}
					}

				}
			}
		}
		return pkeyMap;
	}


	private static boolean isNotContainsStr(String str, String str2) {
		if(str!=null&&!"".equals(str)){
			String[] s = str.split(",");
			if(s!=null&&s.length>0){
				for(String ss:s){
					if(ss!=null&&ss.equals(str2))return true;
				}
			}
		}
		return false;
	}

	private String getNextDay(List<List<WsjhResult>> wrsList, String sjcz) {
		Set<String> daySet = new HashSet<String>();//所有时间
		if(wrsList!=null&&wrsList.size()>0){
			for(List<WsjhResult> wrs:wrsList){
				if(wrs!=null&&wrs.size()>0){
					for(WsjhResult wr:wrs){
						if(wr!=null&&!Tool.isNull(wr.getValue())&&wr.getMbzd().equals(sjcz)){
							String day = wr.getValue();
							String[] days = day.split(" ");
							if(days!=null&&days.length>0){
								String d = days[0];
								if(d.contains("-"))d=d.replaceAll("-", "");
								daySet.add(d);
							}
						}
					}
				}
			}
		}
		List<String> dayList = new ArrayList<String>(daySet);
		Collections.sort(dayList);
		if(dayList!=null&&dayList.size()>0){
			return dayList.get(dayList.size()-1);
		}
		return null;
	}

	private WsjhRzb getLastWsjhRzbByWsjhxh(int wsjhxh) {

		return wsjhConfigDao.getLastWsjhRzbByWsjhxh(wsjhxh).get(0);
	}

	/**
	 * <p>根据时间戳获取对应的结果集</p>
	 * @param results
	 * @param wcList
	 * @param sjczv时间戳变量值
	 * @param sjcv 时间标记
	 * @return
	 * @throws DocumentException
	 */
	private List<List<WsjhResult>> getWsjhResultAllBySjc(Object[] results,	List<WsjhdzConfig> wcList,String sjczv,String sjcv,Integer isType,String zys) throws DocumentException {
		List<List<WsjhResult>> wrsList = new ArrayList<List<WsjhResult>>();
		if(results!=null&&results.length>0){
			for(Object result:results){
				if(result!=null&&result instanceof String){
					String res = (String)result;
					if(!Tool.isNull(res)){
						if(1==isType){//XML格式
							setWsjhResultByXMLAndSjc(wcList, wrsList, res,sjczv,sjcv,zys);
						}else if(0==isType){//JSON格式
							setWsjhResultByJSONAndSjc(wcList, wrsList, res,sjczv,sjcv);
						}
					}
				}
			}
		}
		return wrsList;
	}
	/**
	 * <p>获取含有时间标记的结果集（解析XML）</p>
	 * @param wcList
	 * @param wrsList
	 * @param res
	 * @param sjczv
	 * @param sjcv
	 * @throws DocumentException
	 */
	private void setWsjhResultByXMLAndSjc(List<WsjhdzConfig> wcList,List<List<WsjhResult>> wrsList, String res, String sjczv,
										  String sjcv,String zys) throws DocumentException {
		Document doc = DocumentHelper.parseText(res);
		Element rootElement = doc.getRootElement();
		for(Iterator its= rootElement.elementIterator(zys);its.hasNext();){
			Element msgElement =(Element) its.next();
			List<WsjhResult> wrList = new ArrayList<WsjhResult>();
			if(!Tool.isNull(sjcv)){
				boolean flag = isWsjhResultBySjc(msgElement,sjczv,sjcv);
				if(flag){
					setWsjhResultAll(wrList,msgElement,wcList);
				}
			}else{
				setWsjhResultAll(wrList,msgElement,wcList);
			}

			if(wrList.size()>0)wrsList.add(wrList);
		}

	}

	private void setWsjhResultAll(List<WsjhResult> wrList,		Element msgElement,List<WsjhdzConfig> wcList) {
		for(Iterator it=msgElement.elementIterator();it.hasNext();){
			Element ele = (Element)it.next();
			String name = ele.getName();
			if(!Tool.isNull(name)){
				for(WsjhdzConfig wc:wcList){
					if(wc!=null&&!Tool.isNull(wc.getFhbl())&&wc.getFhbl().toLowerCase().equals(name)){
						WsjhResult wr = new WsjhResult();
						wr.setMbzd(wc.getMbzd());
						wr.setSfzj(Integer.parseInt(wc.getSfzj()==null?"0":wc.getSfzj()));
						wr.setMbzdsjlx(wc.getMbzdsjlx());
						wr.setValue(ele.getText()==null?"":ele.getText().trim());
						wrList.add(wr);
					}
				}
			}
		}
	}

	private boolean isWsjhResultBySjc(Element msgElement, String sjczv,	String sjcv) {
		for(Iterator it=msgElement.elementIterator();it.hasNext();){
			Element ele = (Element)it.next();
			String name = ele.getName();
			if(!Tool.isNull(name)&&name.equals(sjczv.toLowerCase())){
				String value = ele.getText().trim();
				value=value.replaceAll("-", "");
				String[] values=value.split(" ");
				Date date1 = DateTimeUtil.getFormatDateTime(sjcv, "yyyyMMdd");
				Date date2 = DateTimeUtil.getFormatDateTime(values[0],"yyyyMMdd");
				int day =  DateTimeUtil.getDaysBetweenDates(date1, date2);
				if(day>=0){
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * <p>获取含有时间标记的结果集（解析JSON）</p>
	 * @param wcList
	 * @param wrsList
	 * @param res
	 * @param sjczv
	 * @param sjcv
	 */
	private void setWsjhResultByJSONAndSjc(List<WsjhdzConfig> wcList,List<List<WsjhResult>> wrsList, String res, String sjczv,String sjcv) {
		JSONArray ja = JSONArray.fromObject(res);
		for(int i=0;i<ja.size();i++){
			JSONObject jo = (JSONObject)ja.get(i);
			List<WsjhResult> wrList = new ArrayList<WsjhResult>();
			if(!Tool.isNull(sjcv)){
				boolean flag = isWsjhResultBySjc(jo,sjczv,sjcv);
				if(flag){
					setWsjhResultAll(jo,wcList,wrList);
				}
			}else{
				setWsjhResultAll(jo,wcList,wrList);
			}
			if(wrList.size()>0) wrsList.add(wrList);
		}
	}

	private void setWsjhResultAll(JSONObject jo, List<WsjhdzConfig> wcList,List<WsjhResult> wrList) {
		if(jo!=null){
			for(WsjhdzConfig wc:wcList){
				if(wc!=null){
					WsjhResult wr = new WsjhResult();
					Object obj =  jo.get(wc.getFhbl().toLowerCase());
					wr.setMbzd(wc.getMbzd());
					wr.setSfzj(Integer.parseInt(wc.getSfzj()==null?"0":wc.getSfzj()));
					wr.setMbzdsjlx(wc.getMbzdsjlx());
					wr.setValue(String.valueOf(obj==null?"":obj).trim());
					wrList.add(wr);
				}
			}
		}

	}

	private boolean isWsjhResultBySjc(JSONObject jo, String sjczv, String sjcv) {
		if(jo!=null){
			Object obj = jo.get(sjczv.toLowerCase());
			if(obj!=null){
				String value=String.valueOf(obj).trim();
				value=value.replaceAll("-", "");
				String[] values=value.split(" ");
				Date date1 = DateTimeUtil.getFormatDateTime(sjcv, "yyyyMMdd");
				Date date2 = DateTimeUtil.getFormatDateTime(values[0],"yyyyMMdd");
				int day =  DateTimeUtil.getDaysBetweenDates(date1, date2);
				if(day>=0){
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * <p>根据WebService 方法参数（包含密钥/分页）获取交换结果并插入目标库</p>
	 * @param wpcList
	 * @param wcList
	 * @param rzb
	 * @param databaseMeta
	 * @param tablename
	 * @param results
	 * @throws DocumentException
	 */
	private void insertMbkByParamsInPageAndPageSize(List<WsjhParamsConfig> wpcList, List<WsjhdzConfig> wcList,WsjhRzb rzb, DatabaseMeta databaseMeta, String tablename,
													Object[] results,WsjhConfig wc) throws DocumentException {
		List<List<WsjhResult>> wrsList = getWsjhResultAll(results,wcList,new Integer(wc.getIstype()),wc.getZys());
		StringBuffer logs = new StringBuffer();
		if(wrsList!=null&&wrsList.size()>0){
			rzb.setReadLines(wrsList.size());
			int size = wrsList.size();//总记录数
			int count = 0;int pageSize = 1000;
			if(size%pageSize==0){
				count=size/pageSize;
			}else{
				count=size/pageSize+1;
			}
			execWsjh(rzb, databaseMeta, tablename, wrsList, logs,count, pageSize,wc.getOflag());
		}
		rzb.setLog(logs.toString());
	}
	/**
	 * <p>根据WebService 方法参数（包含密钥/其他） 获取交换结果并插入目标库</p>
	 * @param wpcList
	 * @param wcList
	 * @param wc
	 * @param serviceClient
	 * @param opAddEntry
	 * @param params
	 * @throws AxisFault
	 * @throws DocumentException
	 */
	private void updateMbkByParamsInSIGNAndOther(List<WsjhParamsConfig> wpcList, List<WsjhdzConfig> wcList,
												 WsjhConfig wc, RPCServiceClient serviceClient, QName opAddEntry,String params,WsjhRzb rzb) throws AxisFault, DocumentException {
		params=params.replace(",SIGN", "");
		String param = getParams(wpcList,params);
		SjkpzWrapper sjkpzWrapper = this.sjkpzService.getSjkpzDetail(wc.getWsjhds());
		DatabaseMeta databaseMeta = DbUtil.getPoolMeta("system", sjkpzWrapper.getLjlxmc(), sjkpzWrapper.getSjklxmc(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getSjkmc()), sjkpzWrapper.getIpdz(), sjkpzWrapper.getDkh(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getYhm()), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getMm()));
		String tablename = wc.getWsjhzcb();//传参数据库
		List<Map<String,String>> paramsMap =null;
		//Integer isSjc = wc.getIsSjc();//启用时间戳 1是0否
		int isSjc = wc.getIssjc();//启用时间戳 1是0否
		WsjhRzb wr = null;
		//if(isSjc!=null&&isSjc.intValue()==1){//启用时间戳
		if(isSjc == 1){
			wr = getLastWsjhRzbByWsjhxh(wc.getWsjhxh());
			List<List<WsjhResult>> wrsList = null;
			if(wr!=null&&!Tool.isNull(wr.getSjc())){//启用时间戳变量
				String type = DbUtil.getSjcType(wr.getSjc(),tablename,databaseMeta);
				type=type==null?"VARCHAR2":type;
				String sql = "select "+param+" from "+tablename+" where ";
				if("DATE".equals(type)){
					sql+=" to_char("+wc.getSjcz()+",'yyyymmdd') >='"+wr.getSjc()+"'";
				}else{
					sql+=wc.getSjcz()+ " >='"+wr.getSjc()+"'";
				}
				paramsMap = DbUtil.getValueByTable(databaseMeta,sql,wpcList,rzb);
			}else{//第一次没有记录
				String sql = "select "+param+" from "+tablename;
				paramsMap = DbUtil.getValueByTable(databaseMeta,sql,wpcList,rzb);
			}
			//获取最后一次时间
			String nextDay = getNextDay(wrsList,wc.getSjcz());
			rzb.setSjc(nextDay);
		}else{
			String sql = "select "+param+" from "+tablename;
			paramsMap = DbUtil.getValueByTable(databaseMeta,sql,wpcList,rzb);
		}
		tablename=wc.getWsjhb();//目标表
		execWsjh(wpcList, wcList, serviceClient, opAddEntry, params, rzb,databaseMeta, tablename, paramsMap, isSjc, wr,wc);
	}
	/**
	 * <p>根据WebService 方法参数（只含密钥) 获取交换结果并插入目标库 </p>
	 * @param wpcList
	 * @param wcList
	 * @param wc
	 * @param serviceClient
	 * @param opAddEntry
	 * @throws AxisFault
	 * @throws DocumentException
	 */
	private void insetMbkByParamsInSIGN(List<WsjhParamsConfig> wpcList,	List<WsjhdzConfig> wcList, WsjhConfig wc,
										RPCServiceClient serviceClient, QName opAddEntry,WsjhRzb rzb) throws AxisFault,	DocumentException {

		SjkpzWrapper sjkpzWrapper = this.sjkpzService.getSjkpzDetail(wc.getWsjhds());
		DatabaseMeta databaseMeta = DbUtil.getPoolMeta("system", sjkpzWrapper.getLjlxmc(), sjkpzWrapper.getSjklxmc(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getSjkmc()), sjkpzWrapper.getIpdz(), sjkpzWrapper.getDkh(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getYhm()), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getMm()));
		String tablename = wc.getWsjhb();
		//表示参数只有一条
		WsjhParamsConfig wpc = wpcList.get(0);
		Object[] opAddEntryArgs = new Object[]{ wpc.getCsz()};
		Class[] classes = getOpAddEntryArgs(opAddEntryArgs);
		Object[] results = serviceClient.invokeBlocking(opAddEntry,opAddEntryArgs,classes);
		if(results!=null&&results.length>0){
			for(Object obj:results){
				LOG.info("传参返回结果2："+String.valueOf(obj));
			}
		}
		boolean flag = verifyResults(results,rzb);//验证结果是否正确
		if(flag){
			//Integer isSjc = wc.getIsSjc();//启用时间戳 1是0否
			int isSjc = wc.getIssjc();//启用时间戳 1是0否
			//if(isSjc!=null&&isSjc.intValue()==1){//启用时间戳
			if(isSjc == 1){
				insetMbkByParamsInSIGNAndSjc(wpcList,wcList,rzb,databaseMeta,tablename,results,wc);
			}else{
				insetMbkByParamsInSIGN(wpcList, wcList, rzb,databaseMeta, tablename, results,wc);
			}
		}
	}



	private void insetMbkByParamsInSIGNAndSjc(List<WsjhParamsConfig> wpcList,List<WsjhdzConfig> wcList, WsjhRzb rzb, DatabaseMeta databaseMeta,
											  String tablename, Object[] results, WsjhConfig wc) throws DocumentException {
		WsjhRzb wr = getLastWsjhRzbByWsjhxh(wc.getWsjhxh());
		List<List<WsjhResult>> wrsList = null;
		if(wr!=null&&!Tool.isNull(wr.getSjc())){//启用时间戳变量
			wrsList = getWsjhResultAllBySjc(results,wcList,wc.getSjcz(),wr.getSjc(),new Integer(wc.getIstype()),wc.getZys());
			//根据时间标记删除当天以后的数据
			String type = DbUtil.getSjcType(wr.getSjc(),tablename,databaseMeta);
			type=type==null?"VARCHAR2":type;
			String sql = "delete  from "+tablename+" where ";
			if("DATE".equals(type)){
				sql+=" to_char("+wc.getSjcz()+",'yyyymmdd') >='"+wr.getSjc()+"'";
			}else{
				sql+=wc.getSjcz()+ " >='"+wr.getSjc()+"'";
			}
			DbUtil.execUpdateSQL(databaseMeta,sql);

		}else{//第一次没有记录
			wrsList = getWsjhResultAllBySjc(results,wcList,wc.getSjcz(),null,new Integer(wc.getIstype()),wc.getZys());
		}
		//获取最后一次时间
		String nextDay = getNextDay(wrsList,wc.getSjcz());
		rzb.setSjc(nextDay);
		String logs = getWsjhLogs(rzb, databaseMeta, tablename, wc, wr, wrsList);
		rzb.setLog(logs);

	}

	private void insetMbkByParamsInSIGN(List<WsjhParamsConfig> wpcList,	List<WsjhdzConfig> wcList, WsjhRzb rzb, DatabaseMeta databaseMeta,
										String tablename, Object[] results,WsjhConfig wc) throws DocumentException {
		StringBuffer logs = new StringBuffer();
		List<List<WsjhResult>> wrsList =getWsjhResultAll(results,wcList,new Integer(wc.getIstype()),wc.getZys());
		if(wrsList!=null&&wrsList.size()>0){
			int size = wrsList.size();//总记录数
			rzb.setReadLines(size);
			int count = 0;int pageSize = 1000;
			if(size%pageSize==0){
				count=size/pageSize;
			}else{
				count=size/pageSize+1;
			}
			execWsjh(rzb, databaseMeta, tablename, wrsList, logs,count, pageSize,wc.getOflag());
		}
		rzb.setLog(logs.toString());
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

	/**
	 * <p>根据参数获取含有分页的SQL语句</p>
	 * @param wpcList
	 * @param param
	 * @param tablename
	 * @return
	 */
	private String getSQLByPageAndPageSize(List<WsjhParamsConfig> wpcList,String param,String tablename) {
		String page=null;String pageSize=null;
		for(WsjhParamsConfig wpc:wpcList){
			if(wpc!=null&&!Tool.isNull(wpc.getCszd())){
				if(wpc.getCszd().equals("PAGE")&&!Tool.isNull(wpc.getCsz()))page=wpc.getCsz();
				if(wpc.getCszd().equals("PAGESIZE")&&!Tool.isNull(wpc.getCsz()))pageSize=wpc.getCsz();
			}
		}
		page=page==null?"1":page;
		pageSize=pageSize==null?"10":pageSize;
		int pageTotal = (Integer.parseInt(page)-1)*Integer.parseInt(pageSize)+1;
		int pageSizeTotal = (Integer.parseInt(page)*Integer.parseInt(pageSize));
		String sql = " select "+param+" from "+tablename+" where rownum >="+pageTotal+" and rownum <="+pageSizeTotal;
		return sql;
	}
	private Object[] getValueParams(List<WsjhParamsConfig> wpcList,	String params, Map<String, String> map) {
		String[] param = params.split(",");
		Object[] objs = new Object[param.length];
		for(int i=0;i<param.length;i++){
			objs[i]= getValueByParamsType(map.get(param[i]),wpcList,param[i]);
		}
		return objs;
	}

	/**
	 * <p>根据参数类型转义值</p>
	 * @param value
	 * @param wpcList
	 * @param params
	 * @return
	 */
	private Object getValueByParamsType(String value,List<WsjhParamsConfig> wpcList, String params) {
		for(WsjhParamsConfig wpc:wpcList){
			if(wpc!=null&&!Tool.isNull(wpc.getCszd())){
				if(wpc.getCszd().equals(params)){
					return formatValue(value,wpc.getCszdt());
				}
			}
		}
		return null;
	}

	private Object formatValue(String value, String cszdt) {
		if(Tool.isNull(value))return "";
		if(cszdt!=null){
			if(cszdt.equals("String")){
				return value;
			}else if(cszdt.equals("int")){
				return Integer.parseInt(value);
			}else if(cszdt.equals("integer")){
				return Integer.parseInt(value);
			}else if(cszdt.equals("short")){
				return Short.parseShort(value);
			}else if(cszdt.equals("boolean")){
				return Boolean.getBoolean(value);
			}else if(cszdt.equals("date")){
				return DateTimeUtil.getFormatDate(value);
			}else if(cszdt.equals("double")){
				return Double.parseDouble(value);
			}else if(cszdt.equals("float")){
				return Float.parseFloat(value);
			}else if(cszdt.equals("long")){
				return Long.parseLong(value);
			}
		}
		return null;
	}

	private boolean isQueryAll(List<WsjhParamsConfig> wpcList) {
		boolean flag = true,pFlag=true;
		for(WsjhParamsConfig wpc:wpcList){
			if(wpc!=null&&!Tool.isNull(wpc.getCszd())){
				if(wpc.getCszd().equals("PAGE")&&!Tool.isNull(wpc.getCsz()))flag=false;
				if(wpc.getCszd().equals("PAGESIZE")&&!Tool.isNull(wpc.getCsz()))pFlag=false;
			}
		}
		if(flag&&pFlag)return true;
		return false;
	}
	/**
	 * <p>获取已选择参数组合</p>
	 * @param wpcList
	 * @return
	 */
	private Object[] getValueParams(List<WsjhParamsConfig> wpcList,String params) {
		String[] param = params.split(",");
		Object[] objs = new Object[param.length];
		for(int i=0;i<param.length;i++){
			if(!Tool.isNull(param[i])){
				WsjhParamsConfig wpc = getWsjhParamsConfig(wpcList,param[i]);
				if(wpc!=null)objs[i]=wpc.getCsz()==null?null:wpc.getCsz().trim();
			}
		}
		return objs;
	}
	private WsjhParamsConfig getWsjhParamsConfig(List<WsjhParamsConfig> wpcList, String cszd) {
		for(WsjhParamsConfig wpc:wpcList){
			if(wpc!=null&&!Tool.isNull(wpc.getCszd())&&wpc.getCszd().equals(cszd))return wpc;
		}
		return null;
	}
	public List<Sjdwb> getSjdw() {

		return wsjhConfigDao.getSjdw();
	}

	public List<WsjhDsqConfig> getWsjhDsqConfig(WsjhDsqConfig wdc) {

		return wsjhConfigDao.getWsjhDsqConfig(wdc);
	}

	public PageList<WsjhRzb> getPagingInfoByWsjhrzb(WsjhRzb wsjhrzb) {
		PageList<WsjhRzb> pageList = new PageList<WsjhRzb>();
		Paginator paginator = new Paginator();
		if(wsjhrzb!=null){
			paginator.setItemsPerPage(wsjhrzb.getPerPageSize());
			paginator.setPage((Integer)wsjhrzb.getToPage());
			paginator.setItems(wsjhConfigDao.getPagingInfoByWsjhrzbCount(wsjhrzb));
			pageList.setPaginator(paginator);

			if(paginator.getBeginIndex() <= paginator.getItems()){
				wsjhrzb.setStartRow(paginator.getBeginIndex());
				wsjhrzb.setEndRow(paginator.getEndIndex());
				List<WsjhRzb> list = wsjhConfigDao.getPagingInfoByWsjhrzbList(wsjhrzb);
				pageList.addAll(list);
			}
		}
		return pageList;
		//return wsjhConfigDao.getPagingInfoByWsjhrzb(wsjhrzb);
	}

	public Integer insertWsjhrzb(WsjhRzb wsjhrzb) {
		wsjhConfigDao.insertWsjhrzb(wsjhrzb);
		return wsjhConfigDao.getInsertWsjhrzb();
		//return wsjhConfigDao.insertWsjhrzb(wsjhrzb);
	}

	public String getLogs(WsjhRzb wsjhrzb) {
		List<WsjhRzb> wsjhrzbList = wsjhConfigDao.getLogs(wsjhrzb);
		return wsjhrzbList.get(0).getLog();
	}

	public List<WsjhDsqConfig> getRunTasks() {
		return wsjhConfigDao.getRunTasks();
	}

	public void batchUpdateWsjhParamsConfig(List<WsjhParamsConfig> list) throws SQLException{
		for(WsjhParamsConfig wsjhParamsConfig : list){
			wsjhConfigDao.batchUpdateWsjhParamsConfig(wsjhParamsConfig);
		}
		//wsjhConfigDao.batchUpdateWsjhParamsConfig(list);
	}

	public String getMessageByWsbs(WsjhConfig wc,List<WsjhParamsConfig> wpcList) {
		WsjhRzb rzb = new WsjhRzb();
		rzb.setStartDate(DateTimeUtil.getCurrDateTimeStr());
		rzb.setWsjhxh(wc.getWsjhxh());
		String url = wc.getUrl();
		String namespace=wc.getNamespace();
		String operate=wc.getOperate();
		if(!Tool.isNull(url)){
			if(url.contains(".asmx")){
				processWebServiceByWsbs(wpcList,wc,rzb, url, namespace, operate);
			}
		}
		return "";
	}

	/**
	 * <p>WebService 报送配置处理请求</p>
	 * @param wpcList
	 * @param wc
	 * @param rzb
	 * @param url
	 * @param namespace
	 * @param operate
	 */
	private void processWebServiceByWsbs(List<WsjhParamsConfig> wpcList,WsjhConfig wc, WsjhRzb rzb, String url, String namespace,
										 String operate) {
		try{
			Service service = new Service();
			Call   call   = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(url));
			QName qnName = new QName(namespace,operate);
			call.setOperationName(qnName);
			String params = wc.getParams();
			if(params!=null){//有参数
				addParameterByCall(wpcList,call,namespace,operate,wc);
				insertMbkByParamsAndCallByWsbs(wpcList,wc,rzb,call,params);
			}else{//没有参数
				try{
					call.invoke(new Object[]{});
					rzb.setUpdateLines((rzb.getUpdateLines()==null?0:rzb.getUpdateLines())+1);
				}catch(Exception e){
					rzb.setErrors((rzb.getErrors()==null?1:rzb.getErrors())+1);
					rzb.setLog(rzb.getLog()+" "+e.getMessage());
				}
			}
		}catch(Exception e){
			rzb.setErrors((rzb.getErrors()==null?1:rzb.getErrors())+1);
			rzb.setLog(rzb.getLog()+" "+e.getMessage());
			System.gc();
		}
		rzb.setEndDate(DateTimeUtil.getCurrDateTimeStr());
		rzb.setLogDate(DateTimeUtil.getCurrDateTimeStr());
		insertWsjhrzb(rzb);
	}


	/**
	 * <p>根据参数与调用接口插入目标库(报送配置)</p>
	 * @param wpcList
	 * @param wc
	 * @param rzb
	 * @param call
	 * @param params
	 */
	private void insertMbkByParamsAndCallByWsbs(List<WsjhParamsConfig> wpcList,	WsjhConfig wc, WsjhRzb rzb, Call call, String params) {
		SjkpzWrapper sjkpzWrapper = this.sjkpzService.getSjkpzDetail(wc.getWsjhds());
		DatabaseMeta databaseMeta = DbUtil.getPoolMeta("system", sjkpzWrapper.getLjlxmc(), sjkpzWrapper.getSjklxmc(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getSjkmc()), sjkpzWrapper.getIpdz(), sjkpzWrapper.getDkh(), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getYhm()), com.zfsoft.sjzx.common.util.Tool.validateDecode(sjkpzWrapper.getMm()));
		String tablename = wc.getWsjhzcb();
		String sql=null;
		//if(wc.getPlbs()!=null&&wc.getPlbs().intValue()==1){//批量报送
		if(wc.getPlbs() == 1){
			sql = " select * from "+tablename;
		}else{
			params = getParams(wpcList,params);
			sql = "select "+params+" from "+tablename;
		}

		WsjhRzb wr = getLastWsjhRzbByWsjhxh(wc.getWsjhxh());//获取最后一次更新时间

		String sjcz = wc.getSjcz();//时间戳
		if(sjcz!=null&&!"".equals(sjcz)){
			String daySQL = "select "+wc.getSjcz()+" from "+tablename;
			if(wr!=null&&wr.getSjc()!=null&&!"".equals(wr.getSjc())){
				String type = DbUtil.getSjcType(wr.getSjc(),tablename,databaseMeta);
				type=type==null?"VARCHAR2":type;
				if("DATE".equals(type)){
					sql+=" where to_char("+wc.getSjcz()+",'yyyymmdd') >='"+wr.getSjc()+"'";
					daySQL+=" where to_char("+wc.getSjcz()+",'yyyymmdd') >='"+wr.getSjc()+"'";
				}else{//字符串类型
					sql+=" where "+wc.getSjcz()+">='"+wr.getSjc()+"'";
					daySQL+=" where "+wc.getSjcz()+">='"+wr.getSjc()+"'";
				}
			}
			daySQL+=" order by "+wc.getSjcz() ;
			String lastDay = DbUtil.getLastDayBySQL(databaseMeta, daySQL);
			rzb.setSjc((lastDay==null&&wr!=null)?wr.getSjc():lastDay);
		}
		List<Map<String,String>> paramsMap = null;
		if(wc.getPlbs() == 1){
			//if(wc.getPlbs()!=null&&wc.getPlbs().intValue()==1){//批量报送
			List<Map<String,String>> columnMapList = (List<Map<String,String>>)DbUtil.getDBColumnAndComments(databaseMeta, tablename);
			List<String> columnList = getColumnList(columnMapList);
			paramsMap = DbUtil.getValueByTable(databaseMeta, sql,  rzb,columnList);
		}else{
			paramsMap = DbUtil.getValueByTable(databaseMeta,sql,wpcList,rzb);
		}



		StringBuffer logs = new StringBuffer();
		logs.append(rzb.getLog()==null?"":rzb.getLog());//保留原来的日志
		if(paramsMap!=null&&paramsMap.size()>0){
			rzb.setReadLines(paramsMap.size());//总记录数
			int size = paramsMap.size();//总记录数
			int count = 0;int pageSize = 1000;
			if(size%pageSize==0){
				count=size/pageSize;
			}else{
				count=size/pageSize+1;
			}
			int pageNo=1; int updateLines = 0;int errors=0;
			List<Map<String,String>> tempList = new ArrayList<Map<String,String>>();
			while(count>=pageNo){
				List<Map<String,String>> list =(List<Map<String,String>>) ArrayUtil.getList(paramsMap, pageNo, pageSize);
				//if(wc.getPlbs()!=null&&wc.getPlbs().intValue()==1){//批量报送
				if(wc.getPlbs() == 1){
					try{
						String xml = XmlHelper.getXml(list,"TABLE","ROWS");
						Object objs = call.invoke(new Object[]{xml});
						if(objs!=null){
							String value = String.valueOf(objs);
							if(value!=null&&Integer.parseInt(value)>0){//更新成功
								updateLines++;
							}else{//更新失败
								tempList.addAll(list);//失败的记录加入tempList;
							}
						}
					}catch(Exception e){
						errors++;
						e.printStackTrace();
						logs.append(e.getMessage());
					}

				}else{//不是批量报送
					if(list!=null&&list.size()>0){
						for(int i=0;i<list.size();i++){
							Map<String,String> vmap= (Map<String,String>)list.get(i);
							if(vmap!=null&&vmap.size()>0){
								try{
									Object[] args = getValueParams(wpcList,params,vmap);
									Object objs =  call.invoke(args);
									if(objs!=null){
										String value = String.valueOf(objs);
										if(value!=null&&Integer.parseInt(value)>0){//更新成功
											updateLines++;
										}else{//更新失败
											tempList.add(vmap);//失败的记录加入tempList;
										}
									}
								}catch(Exception e){
									errors++;
									e.printStackTrace();
									logs.append(e.getMessage());
								}
							}
						}
					}
				}
				pageNo++;
			}
			if(tempList.size()>0){
				rzb.setFailid(getFailidByList(tempList));//保存失败的记录
			}
			if(errors>0&&wr!=null){
				rzb.setSjc(wr.getSjc());//如果失败，则还原上一次执行时间
			}
			rzb.setUpdateLines(updateLines);
			rzb.setErrors(errors);
		}
		rzb.setLog(logs.toString());
	}
	/**
	 * <p>列名集合</p>
	 * @param columnMapList
	 * @return
	 */
	private List<String> getColumnList(List<Map<String, String>> columnMapList) {
		List<String> columnList = new ArrayList<String>();
		if(columnMapList!=null&&columnMapList.size()>0){
			for(Map<String,String> map:columnMapList){
				if(map!=null&&map.size()>0){
					String columnName = map.get("COLUMN_NAME");
					columnList.add(columnName);
				}
			}
		}
		return columnList;
	}

	/**
	 * <p>把MAP集合转化为字符串</p>
	 * @param mapList
	 * @return
	 */
	private String getFailidByList(List<Map<String, String>> mapList) {
		StringBuffer result = new StringBuffer();
		for(Map<String,String> map:mapList){
			if(map!=null&&map.size()>0){
				for(Iterator<Entry<String,String>> it = map.entrySet().iterator();it.hasNext();){
					Entry<String,String> entry = it.next();
					String key = entry.getKey();
					String value= entry.getValue();
					if(key!=null&&value!=null){
						result.append(key+":"+value+" ");
					}
				}
			}
			result.append("|");
		}
		result.deleteCharAt(0);

		return result.toString();
	}



	private String getParams(List<WsjhParamsConfig> wpcList, String params) {
		String[] param = params.split(",");
		String[] objs = new String[param.length];
		for(int i=0;i<param.length;i++){
			if(!Tool.isNull(param[i])){
				WsjhParamsConfig wpc = getWsjhParamsConfig(wpcList,param[i]);
				if(wpc!=null)objs[i]=wpc.getCsz()==null?null:wpc.getCsz().trim();
			}
		}
		return StringUtils.join(objs,",");
	}

	@Override
	public void deleteWsjhDsqConfigByJhmc(WsjhDsqConfig wdc) {
		wsjhConfigDao.deleteWsjhDsqConfigByJhmc(wdc);
	}



}
