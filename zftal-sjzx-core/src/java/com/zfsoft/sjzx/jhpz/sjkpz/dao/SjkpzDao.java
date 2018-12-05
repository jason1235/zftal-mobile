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
     * 获取到所有的数据库类型信息
     * @return
     * @
     */

    public List getSjklxList() ;

    /**
     * 查询所有数据库连接类型信息
     * @return
     * @
     */
    public List getSjkljlxList() ;

    /**
     * 获取所有的数据库配置
     * @return
     */
    public List getSjkpz();
    /**
     * 获取所有的受监控数据库配置
     * @return
     */
    public List getAllJKSjkpz();

    public List<SjkpzWrapper> getSjkpzDetail(String pzxh);

    /**
     * 查询一条数据库类型和数据库连接类型信息
     * @return
     * @
     */
    public Map getSjklxAndLjlx(Map map) ;

    /**
     * 判断数据库服务器是否被引用
     * @param yyfwqlsh
     * @return
     */
    public int sjkfwqIfUsed(int pzxh);

    public int getPagingInfoCount(Sjkpz sjkpz);
}