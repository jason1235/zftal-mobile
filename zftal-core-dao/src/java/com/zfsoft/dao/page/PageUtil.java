package com.zfsoft.dao.page;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页计算工具类
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-5
 * @version V1.0.0
 */
public class PageUtil {
	
	/**
	 * 获取第一条记录的索引
	 * @param pageNumber 页码
	 * @param pageSize 分页大小
	 * @return
	 */
	public static int getFirstResult(int pageNumber, int pageSize) {
		if (pageSize <= 0) {
			throw new IllegalArgumentException( "[pageSize] must great than zero" );
		}
		
		return (pageNumber - 1) * pageSize;
	}

	/**
	 * 生成分页连接列表
	 * <p>
	 * 1 2 [3] 4 5 
	 * </p>
	 * @param currentPageNumber 当前页码
	 * @param lastPageNumber 最后页码
	 * @param count 列表长度
	 * @return
	 */
	public static List<Integer> generateLinkPageNumbers(int currentPageNumber, int lastPageNumber, int count) {
		int avg = count / 2;

		int startPageNumber = currentPageNumber - avg;
		if (startPageNumber <= 0) {
			startPageNumber = 1;
		}

		int endPageNumber = startPageNumber + count - 1;
		if (endPageNumber > lastPageNumber) {
			endPageNumber = lastPageNumber;
		}

		if (endPageNumber - startPageNumber < count) {
			startPageNumber = endPageNumber - count;
			if (startPageNumber <= 0) {
				startPageNumber = 1;
			}
		}

		List<Integer> result = new ArrayList<Integer>();
		for (int i = startPageNumber; i <= endPageNumber; i++) {
			result.add(new Integer(i));
		}
		return result;
	}

	/**
	 * 计算最后一页的页码
	 * @param totalElements 总记录数
	 * @param pageSize 分页大小
	 * @return
	 */
	public static int computeLastPageNumber(int totalElements, int pageSize) {
		int result = totalElements % pageSize == 0 ? totalElements / pageSize : totalElements / pageSize + 1;
		
		if (result <= 1) {
			result = 1;
		}
		
		return result;
	}

	/**
	 * 计算页码
	 * <p>
	 * 对页码的合法性进行验证，如果是非法数据，这对其进行修正
	 * </p>
	 * @param pageNumber 页码
	 * @param pageSize 分页大小
	 * @param totalElements 总记录数
	 * @return
	 */
	public static int computePageNumber(int pageNumber, int pageSize, int totalElements) {
		if (pageNumber <= 1) {
			return 1;
		}
		
		if (Integer.MAX_VALUE == pageNumber
				|| pageNumber > computeLastPageNumber(totalElements, pageSize)) {
			return computeLastPageNumber(totalElements, pageSize);
		}
		return pageNumber;
	}

}
