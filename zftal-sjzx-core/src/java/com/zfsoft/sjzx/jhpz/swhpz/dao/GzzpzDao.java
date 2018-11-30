package com.zfsoft.sjzx.jhpz.swhpz.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.sjzx.jhpz.dsqpzb.model.Dsqpzb;
import com.zfsoft.sjzx.jhpz.sjjhpzb.model.Dsqlx;
import com.zfsoft.sjzx.jhpz.sjjhpzb.model.Sjdwb;
import com.zfsoft.sjzx.jhpz.sjjhpzb.model.Sjjhpzb;
import com.zfsoft.sjzx.jhpz.swhpz.model.GzzDsq;
import com.zfsoft.sjzx.jhpz.swhpz.model.GzzLog;
import com.zfsoft.sjzx.jhpz.swhpz.model.Gzzpz;
import com.zfsoft.sjzx.jhpz.swhpz.model.Gzzys;
import com.zfsoft.sjzx.jhpz.yyfwq.model.Yyfwq;

public interface GzzpzDao{

    public Gzzpz getGzzpz(Gzzpz gzzpz);

    public void insertGzzpz(Gzzpz gzzpz);

    public void updateGzzpz(Gzzpz gzzpz);

    public void deleteGzzpz(Gzzpz gzzpz);

    public PageList<Gzzpz> getPagingInfo(Gzzpz gzzpz);
    
    /**
     * 查询所有的配置类型
     * <p>ETL配置</p>
     * <p>WEBserver配置</p>
     * @return
     * @
     */
    @SuppressWarnings("unchecked")
	public List getAllPzlx();
    
    /**
     * 获取事务化配置的所有工作组信息
     * @return
     * @
     */
    public List<Gzzpz> getAllGzz();
    
    /**
     * 获取所有的数据交换配置项,作为事务化配置的组元素
     * @return
     */
    public List<Sjjhpzb> getAllSjjhpzList();
    
    public List<Sjjhpzb> getAllSjjhpz(String searchKey);
    
    /**
     * 获取所有的应用服务器配置项
     * @return
     * @
     */
    public List<Yyfwq> getAllYyfwq();
    
    /**
     * 获取所有的定时器类型信息
     * @return
     * @
     */
    public List<Dsqlx> getAllDsqlx();
    
    /**
     * 根据工作组名称获取工作组编号
     * @param gzzmc
     * @return
     * @
     */
    public int getGzzbh(String gzzmc);
    
    /**
     * 获取所有的时间单位
     * @return
     * @
     */
    public List<Sjdwb> getAllSjdw();
    
    /**
     * 更新定时器状态
     * @param dsqpzb
     * @return
     */
    public void updateDsqzt(Dsqpzb dsqpzb);
    
    /**
     * 根据工作组编号获取所有的工作组元素
     * @param gzzbh
     * @return
     */
    public List<Gzzys> getGzzys(int gzzbh);
    
    /**
     * 根据对象流水号获取对应所有的定时器信息
     * @param dxlsh
     * @return
     */
    public List<GzzDsq> getDsqpz(int dxlsh);
    
    /**
     * 获取模糊查询页的信息
     * @param gzzpz
     * @return
     */
    //public PageList getSearchPagingInfo(Gzzpz gzzpz);//job中暂时没有用到，先不管
    
    /**
     * 获取新的工作组编号,作为新插入的工作组编号
     * @return
     * @
     */
    public int getGzzbhNextval();
    
    /**
     * 获取新的定时器序号，作为新插入定时器项的定时器序号
     * @return
     * @
     */
    //public Object getDsqxh();//好像没有用到，by zhangxu
    
    /**
     * 插入定时器配置项
     * @return
     * @
     */
    public void insertDsq(Dsqpzb dsqpzb);//数据中心中有返回int值，为当前序列值
    
    /**
     * 修改更新工作组配置项信息
     * @param gzzpz
     * @return
     * @
     */
    public void updateGzz(Gzzpz gzzpz);
    
    /**
     * 根据对象流水号获取所有该对象流水好对应的定时器状态
     * @param dxlsh
     * @return
     * @
     */
    public List<String> getAllDsqzt(int dxlsh);
    
    
    
    
    
    
    
    /**
     * 获取事务化配置的日志分页信息
     * @param gzzLog
     * @return
     */
    //public PageList<GzzLog> getGzzLogPagingInfo(GzzLog gzzLog);
    
    /**
     * 根据日志ID来获取一条日志的详细信息
     * @param logid
     * @return
     */
    public Object getLogXqByLogid(Integer logid);
    
    /**
     * 根据定时器序号获取定时器状态
     * @param dsqxh
     * @return
     * @
     */
    public String getDsqztByDsqxh(int dsqxh);
    
    /**
     * 获取工作组的总数
     * @return
     */
    public int getGzzTotalCount();
}