/**
 * 
 */
package com.zfsoft.framework.page;

import java.util.ArrayList;
import java.util.Collection;

/**
 * ��������ҳ����Ϣ��<code>List</code>��
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
@SuppressWarnings("unchecked")
public class PageList extends ArrayList {
	
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3257568390985103409L;
    
    /** ��ҳ�� */
    private Paginator paginator;

    /**
     * ����һ��<code>PageList</code>��
     */
    public PageList() {
        paginator = new Paginator();
    }

    /**
     * ����<code>PageList</code>������ָ��<code>Collection</code>�е����ݸ��Ƶ��µ�list�С�
     *
     * @param c Ҫ���Ƶ�<code>Collection</code>
     */
    public PageList(Collection c) {
        this(c, null);
    }

    /**
     * ����<code>PageList</code>������ָ��<code>Collection</code>�е����ݸ��Ƶ��µ�list�С�
     *
     * @param c Ҫ���Ƶ�<code>Collection</code>
     */
	public PageList(Collection c, Paginator paginator) {
        super(c);
        this.paginator = (paginator == null) ? new Paginator()
                                             : paginator;
    }

    /**
     * ȡ�÷�ҳ�����ɴ���ȡ���йط�ҳ��ҳ���������Ϣ��
     *
     * @return ��ҳ������
     */
    public Paginator getPaginator() {
        return paginator;
    }

    /**
     * ���÷�ҳ����
     *
     * @param paginator Ҫ���õķ�ҳ������
     */
    public void setPaginator(Paginator paginator) {
        if (paginator != null) {
            this.paginator = paginator;
        }
    }
    
}
