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
     * 获取所有的数据库类型信息
     * @return
     */
    public List<Map<String,Object>> getSjklx();

    /**
     * 获到所有的数据库连接类型信息
     * @return
     */
    public List<Map<String,Object>> getSjkljlx();
    /**
     * 获取所有的数据库配置
     * @return
     */
    public List getSjkpz();
    /**
     * 获取所有的受监控的数据库配置
     * @return
     */
    public List <SjkpzWrapper> getAllJKSjkpz();

    public SjkpzWrapper getSjkpzDetail(String pzxh);

    /**
     * 获取和一个数据库类型相对应的连接类型配置
     * @param map
     * @return
     */
    public Map<String,Object> getSjklxAndLjlx(Map<String,Object> map);

    /**
     * 判断数据库服务器是否被引用
     * @param yyfwqlsh
     * @return
     */
    public boolean sjkfwqIfUsed(int pzxh);
}