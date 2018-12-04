package com.zfsoft.sjzx.jhpz.dsqpzb.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.sjzx.jhpz.dsqpzb.model.Dsqpzb;
import com.zfsoft.sjzx.jhpz.dsqpzb.model.DsqpzbWrapper;

public interface DsqpzbDao{

    public List<Dsqpzb> getDsqpzb(Dsqpzb dsqpzb);

    public void insertDsqpzb(Dsqpzb dsqpzb);

    public int getInsertDsqpzb();

    public void updateDsqpzb(Dsqpzb dsqpzb);

    public void deleteDsqpzb(Dsqpzb dsqpzb);

    //public PageList<Dsqpzb> getPagingInfo(Dsqpzb dsqpzb);

    public int getDsqpzbListCount(Dsqpzb dsqpzb);

    public PageList<Dsqpzb> getDsqpzbList(Dsqpzb dsqpzb);

    public List<DsqpzbWrapper> getRunSet(int dxlsh);

    public List<DsqpzbWrapper> getRunSetBywjbh(int wjbh);

    public void deleteDsqpzbByDxlsh(Dsqpzb dsqpzb);

    //public int getDsqxh();

    public void updataDsqpzStatus(Dsqpzb dsqpzb);

    /**
     * 根据对象流水号删除定时器
     * @param dxlsh
     * @return
     * @throws DataAccessException
     */
    public void deleteDsqByDxlsh(int dxlsh);

    public int getDsqpzbByZt(Dsqpzb dsqpzb);

    public void closeDsq(Dsqpzb dsqpzb);

    public List<Dsqpzb> getRunTasks();

    public void updateLastRunTime(Dsqpzb dsqpzb);

    //public PageList getRunTasksPagingInfo(Object dataObject);

    public List<DsqpzbWrapper> getRunSetBydsqxh(int dsqxh);



    public List<Dsqpzb> getXtjkSjjhrzb();

    public void updateDsqpzbIsjk(Dsqpzb dsqpzb);
}