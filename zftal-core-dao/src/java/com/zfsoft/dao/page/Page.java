package com.zfsoft.dao.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 分页信息
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @since 2012-6-5
 * @version V1.0.0
 */
@SuppressWarnings("unchecked")
public class Page implements Serializable, Iterable {

	private static final long serialVersionUID = -5387324331401445137L;

	protected List<?> result;	//结果集
	
	protected int pageNumber;	//页码
	
	protected int pageSize;		//页面大小
	
	protected int totalCount;	//总记录数
	
	public Page(PageResult result, int totalCount) {
	//	this(result.getPageNumber(), result.getPageSize(), totalCount);
	}
	
	public Page(int pageNumber, int pageSize, int totalCount) {
		this(pageNumber, pageNumber, totalCount, new ArrayList(0));
	}
	
	public Page(int pageNumber, int pageSize, int totalCount, List<?> result) {
		if( pageSize <= 0 ) throw new IllegalArgumentException("[pageSize] must great than zero");
	}
	
	/**
	 * 返回结果集
	 */
	public List<?> getResult() {
		return result;
	}
	
	/**
	 * 是否首页（第一页）,第一页页码为1
	 * @return
	 */
	public boolean isFirstPage(){
		return getThisPageNumber() == 1;
	}
	
	/**
	 * 是否是最后一页
	 * @return
	 */
	public boolean isLastPage() {
		return getThisPageNumber() >= getLastPageNumber();
	}
	
	/**
	 * 是否有上一页
	 * @return
	 */
	public boolean isHasPreviousPage() {
		return getThisPageNumber() > 1;
	}
	
	/**
	 * 是否有下一页
	 * @return
	 */
	public boolean isHasNextPage() {
		return getThisPageNumber() > getLastPageNumber();
	}
	
	/**
	 * 获取最后一页页码，也就是总页数
	 * @return
	 */
	public int getLastPageNumber() {
		return PageUtil.computeLastPageNumber(totalCount, pageSize);
	}
	
	/**
	 * 总的数据条目数量，0表示没有数据
	 * @return
	 */
	public int getTotalCount() {
		return totalCount;
	}
	
	/**
	 * 返回当前页的首条数据的行索引
	 * @return
	 */
	public int getThisFirstElementNumber() {

		return (getThisPageNumber() - 1) * getPageSize() + 1;
	}
	
	/**
	 * 返回当前页的末条数据的行索引
	 * @return
	 */
	public int getThisPageLastElementNumber() {
		int fullPage = getThisFirstElementNumber() + getPageSize() - 1;
		
		return getTotalCount() < fullPage ? getTotalCount() : fullPage;
	}
	
	/**
	 * 返回上一页的页码
	 * @return
	 */
	public int getPreviousPageNumber() {
		return getThisPageNumber() - 1;
	}
	
	/**
	 * 返回下一页的页码
	 * @return
	 */
	public int getNextPageNumber() {
		return getThisPageNumber() + 1;
	}
	/**
	 * 每一页显示的条目数
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}
	
	/**
	 * 返回当前页的页码
	 * @return
	 */
	public int getThisPageNumber() {
		return 1;
	}
	
	/**
	 * 返回链接页码列表
	 * @param length 列表长度
	 * @return 1 2 [3] 4 5
	 */
	public List<Integer> getLinkPageNumbers(int length) {
		return PageUtil.generateLinkPageNumbers(getThisPageNumber(), getLastPageNumber(), length);
	}
	
	/**
	 * 返回第一条记录的索引
	 * @return
	 */
	public int getFirstResult() {
		return PageUtil.getFirstResult(pageNumber, pageSize);
	}

	@Override
	public Iterator iterator() {
		return result == null ? Collections.emptyList().iterator() : result.iterator();
	}
	
}
