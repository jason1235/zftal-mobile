package com.zfsoft.sjzx.jhpz.sjkpz.service;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.sjzx.jhpz.sjkpz.model.Sjkpz;
import com.zfsoft.sjzx.jhpz.sjkpz.model.SjkpzWrapper;

public interface SjkpzService{

    public Object getSjkpz(Sjkpz sjkpz);

    public PageList getPagingInfo(Sjkpz sjkpz);

    public void insertSjkpz(Sjkpz sjkpz);

    public void updateSjkpz(Sjkpz sjkpz);

    public void deleteSjkpz(Sjkpz sjkpz);
    
    /**
     * ��ȡ���е����ݿ�������Ϣ
     * @return
     */
    public List<Map<String,Object>> getSjklx();

    /**
     * �����е����ݿ�����������Ϣ
     * @return
     */
    public List<Map<String,Object>> getSjkljlx();
    /**
     * ��ȡ���е����ݿ�����
     * @return
     */
    public List getSjkpz();
    /**
     * ��ȡ���е��ܼ�ص����ݿ�����
     * @return
     */
    public List <SjkpzWrapper> getAllJKSjkpz();
    
    public SjkpzWrapper getSjkpzDetail(String pzxh);
    
    /**
     * ��ȡ��һ�����ݿ��������Ӧ��������������
     * @param map
     * @return
     */
    public Map<String,Object> getSjklxAndLjlx(Map<String,Object> map);
    
    /**
     * �ж����ݿ�������Ƿ�����
     * @param yyfwqlsh
     * @return
     */
    public boolean sjkfwqIfUsed(int pzxh);
}