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
	 * <p>����WebService��������������ѯ����������Ϣ</p>
	 * @param wsjhConfig
	 * @return
	 * @throws DataAccessException
	 */
	public WsjhConfig getWsjhConfig(WsjhConfig wsjhConfig)
			throws DataAccessException;
    /**
     * <p>����WebService����������Ϣ</p>
     * @param wsjhConfig
     * @throws DataAccessException
     */
	public void insertWsjhConfig(WsjhConfig wsjhConfig)
			throws DataAccessException;
    /**
     * <p>����WebService����������Ϣ</p>
     * @param wsjhConfig
     * @return
     * @throws DataAccessException
     */
	public void updateWsjhConfig(WsjhConfig wsjhConfig)
			throws DataAccessException;
    /**
     * <p>ɾ��WebService����������Ϣ</p>
     * @param wsjhConfig
     * @throws DataAccessException
     */
	public void deleteWsjhConfig(WsjhConfig wsjhConfig)
			throws DataAccessException;
    /**
     * <p>��ȡ���з�ҳ��WebService�������ü���</p>
     * @param dataObject
     * @return
     * @throws DataAccessException
     */
	public PageList<WsjhConfig> getPagingInfo(WsjhConfig wsjhconfig);

	/**
	 * <p>��ȡ��������</p>
	 * @return
	 * @throws DataAccessException
	 */
	public List<Sjlx> getSjlx();

	/**
	 * <p>����WebService������������ ɾ��WebService�ֶ����ñ�</p>
	 * @param wdc
	 */
	public void deleteWsjhdzConfig(WsjhdzConfig wdc)throws DataAccessException;

	/**
	 * <p>��������WebService �����ֶ����ñ�</p>
	 * @param list
	 * @param wdc
	 */
	public void batchInsert(List<WsjhdzConfig> list, WsjhdzConfig wdc) throws SQLException;
	/**
	 * <p>��ȡ��ʱ������</p>
	 * @return
	 */
	public List<Dsqlx> getDsqlx();
	/**
	 * <p>����WebService ������ʱ������</p>
	 * @param wdc
	 * @return
	 */
	public String insertWsjhDsqConfig(WsjhDsqConfig wdc);
	/**
	 * <p>ɾ��WebService ������ʱ��</p>
	 * @param wdc
	 * @return
	 */
	public void deleteWsjhDsqConfig(WsjhDsqConfig wdc);
	/**
	 * <p>����/ֹͣWebService ������ʱ��</p>
	 * @param wdc
	 * @return
	 */
	public void updataWsjhDsqConfig(WsjhDsqConfig wdc);
	/**
	 * <p>���ݽ����������� ��ȡ��������������ñ�</p>
	 * @param wdc
	 * @return
	 */
	public List<WsjhdzConfig> getWsjhdzConfigList(WsjhdzConfig wdc);
	
	
	/**
	 * <P>����WebService ��������ʵ��</p>
	 * @param wpc
	 */
	public void insertWsjhParamsConfig(WsjhParamsConfig wpc);
	
	/**
	 * <p>����WebService ������������ ��ȡWebService��������</p>
	 * @param wpc
	 * @return
	 */
	public List<WsjhParamsConfig> getWsjhParamsConfigList(WsjhParamsConfig wpc);
	/**
	 * <p>��������WebService������������</p>
	 * @param list
	 * @param wsjhmc
	 */
	public void batchInsertWsjhParamsConfig(List<WsjhParamsConfig> list, WsjhParamsConfig wpc) throws SQLException;
	/**
	 * <p>����WebService��������ɾ������</p>
	 * @param wpc
	 */
	public void deleteWsjhParamsConfig(WsjhParamsConfig wpc);
	
	/**
	 * <p>���ݷ��ؽ����WebService ��������ֶ����û�ȡ��Ӧ�Ľ����</p>
	 * @param results
	 * @param wcList
	 * @param isType
	 * @param zys
	 * @return
	 */
	public List<List<WsjhResult>> getWsjhResultAll(Object[] results,	List<WsjhdzConfig> wcList,Integer isType,String zys)throws DocumentException;
	/**
	 * <p>����WebService ����������ɸ���SQL���</p>
	 * @param wrList
	 * @param tablename
	 * @return
	 */
	public String getUpdateSQLAll(List<WsjhResult> wrList,String tablename);
	/**
	 * <p>��ȡһ�� WebService ���������</p>
	 * @param results
	 * @param wcList
	 * @return
	 */
	public List<WsjhResult> getWsjhResult(Object[] results,	List<WsjhdzConfig> wcList)throws DocumentException;

	/**
	 * <p>ʱ������</p>
	 * @return
	 */
	public List<Sjdwb> getSjdw();
	/**
	 * <p>���ݽ����������ƻ�ȡ��Ӧ�Ķ�ʱ��</p>
	 * @param wdc
	 * @return
	 */
	public List<WsjhDsqConfig> getWsjhDsqConfig(WsjhDsqConfig wdc);
	/**
	 * <p>��ȡ��־��¼</p>
	 * @param wsjhrzb
	 * @return
	 */
	public PageList<WsjhRzb> getPagingInfoByWsjhrzb(WsjhRzb wsjhrzb);
	
	/**
	 * <p>������־��¼</p>
	 * @param wsjhrzb
	 */
	public Integer insertWsjhrzb(WsjhRzb wsjhrzb);
	/**
	 * <p>������־��¼������ȡ������־</p>
	 * @param wsjhrzb
	 * @return
	 */
	public String getLogs(WsjhRzb wsjhrzb);
	/**
	 * <p>��ȡWebService ��ʱ������</p>
	 * @return
	 */
	public List<WsjhDsqConfig> getRunTasks();
	/**
	 * <P>ִ��WebService �������ò�����</P>
	 * @param wpcList
	 * @param wcList
	 * @param wsjhConfig
	 * @return
	 */
	public String getMessage(List<WsjhParamsConfig> wpcList,List<WsjhdzConfig> wcList, WsjhConfig wsjhConfig)throws AxisFault, DocumentException;
	/**
	 * <p>�������·�������</p>
	 * @param list
	 */
	public void batchUpdateWsjhParamsConfig(List<WsjhParamsConfig> list)throws SQLException;
	/**
	 * <p>WebService �������ò�������Ϣ</p>
	 * @param wsjhConfig
	 * @param wpcList
	 * @return
	 */
	public String getMessageByWsbs(WsjhConfig wsjhConfig,	List<WsjhParamsConfig> wpcList);
	
	public void deleteWsjhDsqConfigByJhmc(WsjhDsqConfig wdc);
	

}
