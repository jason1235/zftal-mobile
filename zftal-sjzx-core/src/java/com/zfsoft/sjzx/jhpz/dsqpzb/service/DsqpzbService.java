package com.zfsoft.sjzx.jhpz.dsqpzb.service;

import java.sql.SQLException;
import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.sjzx.jhpz.dsqpzb.model.Dsqpzb;
import com.zfsoft.sjzx.jhpz.dsqpzb.model.DsqpzbWrapper;

public interface DsqpzbService{

    public Dsqpzb getDsqpzb(Dsqpzb dsqpzb);

    public PageList<Dsqpzb> getPagingInfo(Dsqpzb dsqpzb);

    public Object insertDsqpzb(Dsqpzb dsqpzb);

    public Object updateDsqpzb(Dsqpzb dsqpzb);
    
    public Object updateDsqpzb1(Dsqpzb dsqpzb);

    public Object deleteDsqpzb(Dsqpzb dsqpzb);
    
    public Object deleteDsqpzb1(Dsqpzb dsqpzb);
    public Object deleteDsqpzbEtl(Dsqpzb dsqpzb);

    public Object saveDsqpzbBatch(List<Dsqpzb> list);
    
    public List<DsqpzbWrapper> getRunSet(int jhpzxh);
    public List getRunSetBydsqxh(int  dsqxh);
    public List<DsqpzbWrapper> getRunSetBywjbh(int wjbh);
    public void deleteDsqpzbByDxlsh(Dsqpzb dsqpzb);
    
    //public int getDsqxh();
    
    public Object updataDsqpzStatus(Dsqpzb dsqpzb);
    
    public int getDsqpzbByZt(Dsqpzb dsqpzb);
    
    public Object closeDsq(Dsqpzb dsqpzb);
    
    public List<Dsqpzb> getRunTasks(); //��ȡ��̨��ʱ����
    public List<Dsqpzb> getXtjkSjjhrzb();//��ȡ�ܼ�صĶ�ʱ��
    
    public void updateLastRunTime(Dsqpzb dsqpzb); //�����������ʱ��
    
    //public PageList getRunTasksPagingInfo(Dsqpzb dsqpzb);
    
    public int updateDsqpzbIsjk(Dsqpzb dsqpzb); //�޸��Ƿ���
}