/**
 *
 */
package com.zfsoft.framework.page;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * @author Administrator
 *
 */
public class BaseDataObject implements Serializable {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	// 排序字段及方式 如： "order asc"
	private String orderStr;

	// 当前页码
	private int toPage = 1;

	// 每页显示记录数
	private int perPageSize = 10;

	// 查询出的数据总数
	private int totalItem = 0;

	// 起始记录
	private int startRow = 0;

	// 截止记录
	private int endRow = 0;

	/**
	 * 判断当前页是否为第一页
	 *
	 * @return boolean
	 */
	public boolean isFirstPage() {
		return toPage == 1;
	}

	public Paginator toPaginator(){
		Paginator paginator = new Paginator();
		paginator.setItemsPerPage(getPerPageSize());
		paginator.setPage(getToPage());

		paginator.setItems(this.getTotalItem());

		return paginator;
	}
	public String getOrderStr() {
		return orderStr;
	}

	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}

	public int getToPage() {
		if (toPage <= 0) {
			toPage = 1;
		}

		return toPage;
	}

	public void setToPage(int toPage) {
		this.toPage = toPage;
	}

	public int getPerPageSize() {
		if (perPageSize <= 0) {
			perPageSize = 10;
		}

		return perPageSize;
	}

	public void setPerPageSize(int perPageSize) {
		this.perPageSize = perPageSize;
	}

	public int getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}

	/**
	 * 得到上一页的页数
	 *
	 * @return 如果当前页没有上一页，返回1
	 */
	public int getPreviousPage() {
		int back = toPage - 1;

		if (back <= 0) {
			back = 1;
		}

		return back;
	}

	/**
	 * 判断当前页是否为最后一页
	 *
	 * @return boolean
	 */
	public boolean isLastPage() {
		return this.getTotalPage() == toPage;
	}

	/**
	 * 得到下一页的页数
	 *
	 * @return int 如果当前也没有下一页，返回总页数
	 */
	public int getNextPage() {
		int back = toPage + 1;

		if (back > this.getTotalPage()) {
			back = this.getTotalPage();
		}

		return back;
	}

	/**
	 * 得到要显示的总页数
	 *
	 * @return int
	 */
	public int getTotalPage() {
		if (totalItem == 0) {
			return 0;
		}

		int result = totalItem / perPageSize;

		if ((totalItem % perPageSize) != 0) {
			result++;
		}

		return result;
	}

	/**
	 * 得到要检索的结束行数
	 *
	 * @return Returns the endRow.
	 */
	protected String getSQLBlurValue(String value) {
		if (value == null) {
			return null;
		}

		return value + '%';
	}

	protected String formatDate(String datestring) {
		if ((datestring == null) || datestring.equals("")) {
			return null;
		} else {
			return (datestring + " 00:00:00");
		}
	}

	/**
	 * 时间查询时,结束时间的 23:59:59
	 */
	protected String addDateEndPostfix(String datestring) {
		if ((datestring == null) || datestring.equals("")) {
			return null;
		}

		return datestring + " 23:59:59";
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	/**
	 * 时间查询时，开始时间的 00:00:00
	 */
	protected String addDateStartPostfix(String datestring) {
		if ((datestring == null) || datestring.equals("")) {
			return null;
		}

		return datestring + " 00:00:00";
	}

	/**
	 * 验证toPage页数是否合法
	 *
	 * @param toPage
	 * @return 验证后的toPage页数
	 */
	public int validateToPage(int toPage) {
		int pageNum = toPage;
		if (pageNum < 1)
			pageNum = 1;
		if (pageNum > getTotalPage())
			pageNum = getTotalPage();
		return pageNum;
	}

	/**
	 * 构建PageInfo对象数据
	 *
	 * @param rundata中的params
	 * @param 总记录数
	 */
	public void buildPageInfo(Map params) {
		String pageSize = (String) params.get("perPageSize");
		String toPage = (String) params.get("toPage");
		String orderStr = (String) params.get("orderStr");
		if (pageSize!=null && !"".equals(pageSize)) {
			setPerPageSize(Integer.parseInt(pageSize));
		}
		if (toPage!=null && !"".equals(toPage)) {
			int page = 1;
			try {
				page = Integer.parseInt(toPage);
			} catch (Exception e) {
			}
			setToPage(page);
		}
		startRow = (getToPage() - 1) * perPageSize + 1;
		endRow = getToPage() * perPageSize;
		if (orderStr!=null && !"".equals(orderStr)) {
			setOrderStr(orderStr);
		}
	}

	/**
	 * 构建PageInfo对象数据。 PageInfo的实现类专用，主要是验证当前页数的合法性
	 */
	public void validatePageInfo() {
		setToPage(validateToPage(toPage));
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
