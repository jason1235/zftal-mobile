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
     * ��ѯ���е���������
     * <p>ETL����</p>
     * <p>WEBserver����</p>
     * @return
     * @
     */
    @SuppressWarnings("unchecked")
	public List getAllPzlx();
    
    /**
     * ��ȡ�������õ����й�������Ϣ
     * @return
     * @
     */
    public List<Gzzpz> getAllGzz();
    
    /**
     * ��ȡ���е����ݽ���������,��Ϊ�������õ���Ԫ��
     * @return
     */
    public List<Sjjhpzb> getAllSjjhpzList();
    
    public List<Sjjhpzb> getAllSjjhpz(String searchKey);
    
    /**
     * ��ȡ���е�Ӧ�÷�����������
     * @return
     * @
     */
    public List<Yyfwq> getAllYyfwq();
    
    /**
     * ��ȡ���еĶ�ʱ��������Ϣ
     * @return
     * @
     */
    public List<Dsqlx> getAllDsqlx();
    
    /**
     * ���ݹ��������ƻ�ȡ��������
     * @param gzzmc
     * @return
     * @
     */
    public int getGzzbh(String gzzmc);
    
    /**
     * ��ȡ���е�ʱ�䵥λ
     * @return
     * @
     */
    public List<Sjdwb> getAllSjdw();
    
    /**
     * ���¶�ʱ��״̬
     * @param dsqpzb
     * @return
     */
    public void updateDsqzt(Dsqpzb dsqpzb);
    
    /**
     * ���ݹ������Ż�ȡ���еĹ�����Ԫ��
     * @param gzzbh
     * @return
     */
    public List<Gzzys> getGzzys(int gzzbh);
    
    /**
     * ���ݶ�����ˮ�Ż�ȡ��Ӧ���еĶ�ʱ����Ϣ
     * @param dxlsh
     * @return
     */
    public List<GzzDsq> getDsqpz(int dxlsh);
    
    /**
     * ��ȡģ����ѯҳ����Ϣ
     * @param gzzpz
     * @return
     */
    //public PageList getSearchPagingInfo(Gzzpz gzzpz);//job����ʱû���õ����Ȳ���
    
    /**
     * ��ȡ�µĹ�������,��Ϊ�²���Ĺ�������
     * @return
     * @
     */
    public int getGzzbhNextval();
    
    /**
     * ��ȡ�µĶ�ʱ����ţ���Ϊ�²��붨ʱ����Ķ�ʱ�����
     * @return
     * @
     */
    //public Object getDsqxh();//����û���õ���by zhangxu
    
    /**
     * ���붨ʱ��������
     * @return
     * @
     */
    public void insertDsq(Dsqpzb dsqpzb);//�����������з���intֵ��Ϊ��ǰ����ֵ
    
    /**
     * �޸ĸ��¹�������������Ϣ
     * @param gzzpz
     * @return
     * @
     */
    public void updateGzz(Gzzpz gzzpz);
    
    /**
     * ���ݶ�����ˮ�Ż�ȡ���иö�����ˮ�ö�Ӧ�Ķ�ʱ��״̬
     * @param dxlsh
     * @return
     * @
     */
    public List<String> getAllDsqzt(int dxlsh);
    
    
    
    
    
    
    
    /**
     * ��ȡ�������õ���־��ҳ��Ϣ
     * @param gzzLog
     * @return
     */
    //public PageList<GzzLog> getGzzLogPagingInfo(GzzLog gzzLog);
    
    /**
     * ������־ID����ȡһ����־����ϸ��Ϣ
     * @param logid
     * @return
     */
    public Object getLogXqByLogid(Integer logid);
    
    /**
     * ���ݶ�ʱ����Ż�ȡ��ʱ��״̬
     * @param dsqxh
     * @return
     * @
     */
    public String getDsqztByDsqxh(int dsqxh);
    
    /**
     * ��ȡ�����������
     * @return
     */
    public int getGzzTotalCount();
}