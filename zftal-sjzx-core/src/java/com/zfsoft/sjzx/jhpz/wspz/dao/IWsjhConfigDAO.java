package com.zfsoft.sjzx.jhpz.wspz.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.sjzx.jhpz.sjjhpzb.model.Dsqlx;
import com.zfsoft.sjzx.jhpz.sjjhpzb.model.Sjdwb;
import com.zfsoft.sjzx.jhpz.sjjhpzb.model.Sjlx;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhConfig;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhDsqConfig;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhParamsConfig;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhRzb;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhdzConfig;

public interface IWsjhConfigDAO {
	public List<WsjhConfig> getWsjhConfig(WsjhConfig wsjhConfig);

	public void insertWsjhConfig(WsjhConfig wsjhConfig);

	public void updateWsjhConfig(WsjhConfig wsjhConfig);

	public void deleteWsjhConfig(WsjhConfig wsjhConfig);

	public List<Sjlx> getSjlx();

	public void deleteWsjhdzConfig(WsjhdzConfig wc);

	public void batchInsert(WsjhdzConfig wsjhdzConfig);

	public List<Dsqlx> getDsqlx();

	public void insertWsjhDsqConfig(WsjhDsqConfig wdc);
	
	public int getInsertWsjhDsqConfig(WsjhDsqConfig wdc);

	public void deleteWsjhDsqConfig(WsjhDsqConfig wdc);

	public void updateWsjhDsqConfig(WsjhDsqConfig wdc);

	public List<WsjhdzConfig> getWsjhdzConfigList(WsjhdzConfig wc);

	public List<WsjhParamsConfig> getWsjhParamsConfigList(WsjhParamsConfig wpc);

	public void insertWsjhParamsConfig(WsjhParamsConfig wpc);

	public void deleteWsjhParamsConfig(WsjhParamsConfig wpc);

	public void batchInsertWsjhParamsConfig(WsjhParamsConfig wsjhParamsConfig);

	public List<Sjdwb> getSjdw();

	public List<WsjhDsqConfig> getWsjhDsqConfig(WsjhDsqConfig wdc);

	//public PageList<WsjhRzb> getPagingInfoByWsjhrzb(WsjhRzb wsjhrzb);

	public int getPagingInfoByWsjhrzbCount(WsjhRzb wsjhrzb);
	
	public PageList<WsjhRzb> getPagingInfoByWsjhrzbList(WsjhRzb wsjhrzb);
	
	public void insertWsjhrzb(WsjhRzb wsjhrzb);
	
	public int getInsertWsjhrzb();

	public List<WsjhRzb> getLogs(WsjhRzb wsjhrzb);

	public List<WsjhDsqConfig> getRunTasks();

	public void batchUpdateWsjhParamsConfig(WsjhParamsConfig wsjhParamsConfig);

	public List<WsjhRzb> getLastWsjhRzbByWsjhxh(int wsjhxh);

	public int getWsjhConfigListCount(WsjhConfig wsjhconfig);

	public List<WsjhConfig> getWsjhConfigList(WsjhConfig wsjhconfig);

	public void deleteWsjhDsqConfigByJhmc(WsjhDsqConfig wdc);

}
