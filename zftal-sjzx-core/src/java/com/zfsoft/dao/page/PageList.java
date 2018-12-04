package com.zfsoft.dao.page;
import java.util.ArrayList;
import java.util.Collection;

import com.zfsoft.common.query.QueryModel;

/**
 *
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


    private Paginator paginator;

    /**
     *
     */
    public PageList() {
        paginator = new Paginator();
    }

    /**
     *
     *
     *
     */
    public PageList(Collection<? extends E> c) {
        this(c, null);
    }

    /**
     *
     *
     *
     */
	public PageList(Collection<? extends E> c, Paginator paginator) {
        super(c);
        this.paginator = (paginator == null) ? new Paginator()
                                             : paginator;
    }

    /**
     *
     *
     *
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
     *
     *
     *
     */
    public void setPaginator(Paginator paginator) {
        if (paginator != null) {
            this.paginator = paginator;
        }
    }

}
