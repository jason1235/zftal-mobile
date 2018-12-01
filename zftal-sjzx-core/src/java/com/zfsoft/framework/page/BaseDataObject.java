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
	
	// �����ֶμ���ʽ �磺 "order asc"
	private String orderStr;

	// ��ǰҳ��
	private int toPage = 1;

	// ÿҳ��ʾ��¼��
	private int perPageSize = 10;

	// ��ѯ������������
	private int totalItem = 0;

	// ��ʼ��¼
	private int startRow = 0;

	// ��ֹ��¼
	private int endRow = 0;

	/**
	 * �жϵ�ǰҳ�Ƿ�Ϊ��һҳ
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
	 * �õ���һҳ��ҳ��
	 * 
	 * @return �����ǰҳû����һҳ������1
	 */
	public int getPreviousPage() {
		int back = toPage - 1;

		if (back <= 0) {
			back = 1;
		}

		return back;
	}

	/**
	 * �жϵ�ǰҳ�Ƿ�Ϊ���һҳ
	 * 
	 * @return boolean
	 */
	public boolean isLastPage() {
		return this.getTotalPage() == toPage;
	}

	/**
	 * �õ���һҳ��ҳ��
	 * 
	 * @return int �����ǰҲû����һҳ��������ҳ��
	 */
	public int getNextPage() {
		int back = toPage + 1;

		if (back > this.getTotalPage()) {
			back = this.getTotalPage();
		}

		return back;
	}

	/**
	 * �õ�Ҫ��ʾ����ҳ��
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
	 * �õ�Ҫ�����Ľ�������
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
	 * ʱ���ѯʱ,����ʱ��� 23:59:59
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
	 * ʱ���ѯʱ����ʼʱ��� 00:00:00
	 */
	protected String addDateStartPostfix(String datestring) {
		if ((datestring == null) || datestring.equals("")) {
			return null;
		}

		return datestring + " 00:00:00";
	}

	/**
	 * ��֤toPageҳ���Ƿ�Ϸ�
	 * 
	 * @param toPage
	 * @return ��֤���toPageҳ��
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
	 * ����PageInfo��������
	 * 
	 * @param rundata�е�params
	 * @param �ܼ�¼��
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
	 * ����PageInfo�������ݡ� PageInfo��ʵ����ר�ã���Ҫ����֤��ǰҳ���ĺϷ���
	 */
	public void validatePageInfo() {
		setToPage(validateToPage(toPage));
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
