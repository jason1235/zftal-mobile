package com.zfsoft.dao.page;
import java.util.ArrayList;
import java.util.Collection;

import com.zfsoft.common.query.QueryModel;

/**
 * 包含“分页”信息的<code>List</code>。
 * 
 * hzmy-fms - PageList.java
 *
 * com.hzmy.fms.common.pager
 *
 * Copyright 2009 Hangzhou Port International Logistics Co.,ltd
 * All right reserved. 
 *
 * Created on 2009-4-15
 * @author jianghao
 */
public class PageList<E> extends ArrayList<E> {
	
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3257568390985103409L;
    
    /** 翻页器 */
    private Paginator paginator;

    /**
     * 创建一个<code>PageList</code>。
     */
    public PageList() {
        paginator = new Paginator();
    }

    /**
     * 创建<code>PageList</code>，并将指定<code>Collection</code>中的内容复制到新的list中。
     *
     * @param c 要复制的<code>Collection</code>
     */
    public PageList(Collection<? extends E> c) {
        this(c, null);
    }

    /**
     * 创建<code>PageList</code>，并将指定<code>Collection</code>中的内容复制到新的list中。
     *
     * @param c 要复制的<code>Collection</code>
     */
	public PageList(Collection<? extends E> c, Paginator paginator) {
        super(c);
        this.paginator = (paginator == null) ? new Paginator()
                                             : paginator;
    }

    /**
     * 取得分页器，可从中取得有关分页和页码的所有信息。
     *
     * @return 分页器对象
     */
    public Paginator getPaginator() {
        return paginator;
    }
    
    public void setPaginator(QueryModel queryModel){
    	if (queryModel != null) {
    		this.paginator.setItems(queryModel.getTotalResult());
    		this.paginator.setItemsPerPage(queryModel.getShowCount());
    		this.paginator.setItem(queryModel.getCurrentPage());
    		this.paginator.setPage(queryModel.getCurrentPage());
    	}
    }

    /**
     * 设置分页器。
     *
     * @param paginator 要设置的分页器对象
     */
    public void setPaginator(Paginator paginator) {
        if (paginator != null) {
            this.paginator = paginator;
        }
    }
    
}
