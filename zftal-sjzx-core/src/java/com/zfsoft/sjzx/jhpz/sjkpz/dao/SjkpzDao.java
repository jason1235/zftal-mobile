package com.zfsoft.sjzx.jhpz.sjkpz.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.sjzx.jhpz.sjkpz.model.Sjkpz;
import com.zfsoft.sjzx.jhpz.sjkpz.model.SjkpzWrapper;
@SuppressWarnings("unchecked")
public interface SjkpzDao{

    public List<Sjkpz> getSjkpzOne(Sjkpz sjkpz) ;

    public void insertSjkpz(Sjkpz sjkpz) ;

    public void updateSjkpz(Sjkpz sjkpz) ;

    public void deleteSjkpz(Sjkpz sjkpz) ;

    public PageList<SjkpzWrapper> getPagingInfo(Object dataObject) ;
    
    /**
     * ��ȡ�����е����ݿ�������Ϣ
     * @return
     * @
     */
    
    public List getSjklxList() ;
    
    /**
     * ��ѯ�������ݿ�����������Ϣ
     * @return
     * @
     */
    public List getSjkljlxList() ;
    
    /**
     * ��ȡ���е����ݿ�����
     * @return
     */
    public List getSjkpz();
    /**
     * ��ȡ���е��ܼ�����ݿ�����
     * @return
     */
    public List getAllJKSjkpz();
    
    public List<SjkpzWrapper> getSjkpzDetail(String pzxh);

    /**
     * ��ѯһ�����ݿ����ͺ����ݿ�����������Ϣ
     * @return
     * @
     */
    public Map getSjklxAndLjlx(Map map) ;
    
    /**
     * �ж����ݿ�������Ƿ�����
     * @param yyfwqlsh
     * @return
     */
    public int sjkfwqIfUsed(int pzxh);

	public int getPagingInfoCount(Sjkpz sjkpz);
}