package com.zfsoft.sjzx.jhpz.wspz.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.axis2.AxisFault;
import org.dom4j.DocumentException;
import org.springframework.dao.DataAccessException;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.sjzx.jhpz.sjjhpzb.model.Dsqlx;
import com.zfsoft.sjzx.jhpz.sjjhpzb.model.Sjdwb;
import com.zfsoft.sjzx.jhpz.sjjhpzb.model.Sjlx;
import com.zfsoft.sjzx.jhpz.sjkpz.model.Sjkpz;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhConfig;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhDsqConfig;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhParamsConfig;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhResult;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhRzb;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhdzConfig;

public interface WsjhConfigService {
	/**
	 * <p>根据WebService交换配置主键查询交换配置信息</p>
	 * @param wsjhConfig
	 * @return
	 * @throws DataAccessException
	 */
	public WsjhConfig getWsjhConfig(WsjhConfig wsjhConfig)
			throws DataAccessException;
	/**
	 * <p>插入WebService交换配置信息</p>
	 * @param wsjhConfig
	 * @throws DataAccessException
	 */
	public void insertWsjhConfig(WsjhConfig wsjhConfig)
			throws DataAccessException;
	/**
	 * <p>更新WebService交换配置信息</p>
	 * @param wsjhConfig
	 * @return
	 * @throws DataAccessException
	 */
	public void updateWsjhConfig(WsjhConfig wsjhConfig)
			throws DataAccessException;
	/**
	 * <p>删除WebService交换配置信息</p>
	 * @param wsjhConfig
	 * @throws DataAccessException
	 */
	public void deleteWsjhConfig(WsjhConfig wsjhConfig)
			throws DataAccessException;
	/**
	 * <p>获取含有分页的WebService交换配置集合</p>
	 * @param dataObject
	 * @return
	 * @throws DataAccessException
	 */
	public PageList<WsjhConfig> getPagingInfo(WsjhConfig wsjhconfig);

	/**
	 * <p>获取数据类型</p>
	 * @return
	 * @throws DataAccessException
	 */
	public List<Sjlx> getSjlx();

	/**
	 * <p>根据WebService交换配置名称 删除WebService字段配置表</p>
	 * @param wdc
	 */
	public void deleteWsjhdzConfig(WsjhdzConfig wdc)throws DataAccessException;

	/**
	 * <p>批量插入WebService 交换字段配置表</p>
	 * @param list
	 * @param wdc
	 */
	public void batchInsert(List<WsjhdzConfig> list, WsjhdzConfig wdc) throws SQLException;
	/**
	 * <p>获取定时器类型</p>
	 * @return
	 */
	public List<Dsqlx> getDsqlx();
	/**
	 * <p>插入WebService 交换定时器内容</p>
	 * @param wdc
	 * @return
	 */
	public String insertWsjhDsqConfig(WsjhDsqConfig wdc);
	/**
	 * <p>删除WebService 交换定时器</p>
	 * @param wdc
	 * @return
	 */
	public void deleteWsjhDsqConfig(WsjhDsqConfig wdc);
	/**
	 * <p>启动/停止WebService 交换定时器</p>
	 * @param wdc
	 * @return
	 */
	public void updataWsjhDsqConfig(WsjhDsqConfig wdc);
	/**
	 * <p>根据交换配置名称 获取交换结果对照配置表</p>
	 * @param wdc
	 * @return
	 */
	public List<WsjhdzConfig> getWsjhdzConfigList(WsjhdzConfig wdc);


	/**
	 * <P>插入WebService 交换参数实体</p>
	 * @param wpc
	 */
	public void insertWsjhParamsConfig(WsjhParamsConfig wpc);

	/**
	 * <p>根据WebService 交换配置名称 获取WebService交换参数</p>
	 * @param wpc
	 * @return
	 */
	public List<WsjhParamsConfig> getWsjhParamsConfigList(WsjhParamsConfig wpc);
	/**
	 * <p>批量插入WebService交换参数内容</p>
	 * @param list
	 * @param wsjhmc
	 */
	public void batchInsertWsjhParamsConfig(List<WsjhParamsConfig> list, WsjhParamsConfig wpc) throws SQLException;
	/**
	 * <p>根据WebService交换名称删除参数</p>
	 * @param wpc
	 */
	public void deleteWsjhParamsConfig(WsjhParamsConfig wpc);

	/**
	 * <p>根据返回结果及WebService 交换结果字段配置获取对应的结果集</p>
	 * @param results
	 * @param wcList
	 * @param isType
	 * @param zys
	 * @return
	 */
	public List<List<WsjhResult>> getWsjhResultAll(Object[] results,	List<WsjhdzConfig> wcList,Integer isType,String zys)throws DocumentException;
	/**
	 * <p>根据WebService 交换结果生成更新SQL语句</p>
	 * @param wrList
	 * @param tablename
	 * @return
	 */
	public String getUpdateSQLAll(List<WsjhResult> wrList,String tablename);
	/**
	 * <p>获取一条 WebService 交换结果集</p>
	 * @param results
	 * @param wcList
	 * @return
	 */
	public List<WsjhResult> getWsjhResult(Object[] results,	List<WsjhdzConfig> wcList)throws DocumentException;

	/**
	 * <p>时间类型</p>
	 * @return
	 */
	public List<Sjdwb> getSjdw();
	/**
	 * <p>根据交换配置名称获取对应的定时器</p>
	 * @param wdc
	 * @return
	 */
	public List<WsjhDsqConfig> getWsjhDsqConfig(WsjhDsqConfig wdc);
	/**
	 * <p>获取日志记录</p>
	 * @param wsjhrzb
	 * @return
	 */
	public PageList<WsjhRzb> getPagingInfoByWsjhrzb(WsjhRzb wsjhrzb);

	/**
	 * <p>插入日志记录</p>
	 * @param wsjhrzb
	 */
	public Integer insertWsjhrzb(WsjhRzb wsjhrzb);
	/**
	 * <p>根据日志记录主键获取错误日志</p>
	 * @param wsjhrzb
	 * @return
	 */
	public String getLogs(WsjhRzb wsjhrzb);
	/**
	 * <p>获取WebService 定时器配置</p>
	 * @return
	 */
	public List<WsjhDsqConfig> getRunTasks();
	/**
	 * <P>执行WebService 交换配置并返回</P>
	 * @param wpcList
	 * @param wcList
	 * @param wsjhConfig
	 * @return
	 */
	public String getMessage(List<WsjhParamsConfig> wpcList,List<WsjhdzConfig> wcList, WsjhConfig wsjhConfig)throws AxisFault, DocumentException;
	/**
	 * <p>批量更新方法参数</p>
	 * @param list
	 */
	public void batchUpdateWsjhParamsConfig(List<WsjhParamsConfig> list)throws SQLException;
	/**
	 * <p>WebService 报送配置并返回消息</p>
	 * @param wsjhConfig
	 * @param wpcList
	 * @return
	 */
	public String getMessageByWsbs(WsjhConfig wsjhConfig,	List<WsjhParamsConfig> wpcList);

	public void deleteWsjhDsqConfigByJhmc(WsjhDsqConfig wdc);


}
