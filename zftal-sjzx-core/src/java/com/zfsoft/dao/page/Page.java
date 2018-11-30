package com.zfsoft.dao.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 鍒嗛〉淇℃伅
 * @author <a href="mailto:Yongwu_Chen@126.com">闄堟案姝�/a>
 * @since 2012-6-5
 * @version V1.0.0
 */
@SuppressWarnings("unchecked")
public class Page implements Serializable, Iterable {

	private static final long serialVersionUID = -5387324331401445137L;

	protected List<?> result;	//缁撴灉闆�
	
	protected int pageNumber;	//椤电爜
	
	protected int pageSize;		//椤甸潰澶у皬
	
	protected int totalCount;	//鎬昏褰曟暟
	
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
	 * 杩斿洖缁撴灉闆�
	 */
	public List<?> getResult() {
		return result;
	}
	
	/**
	 * 鏄惁棣栭〉锛堢涓�〉锛�绗竴椤甸〉鐮佷负1
	 * @return
	 */
	public boolean isFirstPage(){
		return getThisPageNumber() == 1;
	}
	
	/**
	 * 鏄惁鏄渶鍚庝竴椤�
	 * @return
	 */
	public boolean isLastPage() {
		return getThisPageNumber() >= getLastPageNumber();
	}
	
	/**
	 * 鏄惁鏈変笂涓�〉
	 * @return
	 */
	public boolean isHasPreviousPage() {
		return getThisPageNumber() > 1;
	}
	
	/**
	 * 鏄惁鏈変笅涓�〉
	 * @return
	 */
	public boolean isHasNextPage() {
		return getThisPageNumber() > getLastPageNumber();
	}
	
	/**
	 * 鑾峰彇鏈�悗涓�〉椤电爜锛屼篃灏辨槸鎬婚〉鏁�
	 * @return
	 */
	public int getLastPageNumber() {
		return PageUtil.computeLastPageNumber(totalCount, pageSize);
	}
	
	/**
	 * 鎬荤殑鏁版嵁鏉＄洰鏁伴噺锛�琛ㄧず娌℃湁鏁版嵁
	 * @return
	 */
	public int getTotalCount() {
		return totalCount;
	}
	
	/**
	 * 杩斿洖褰撳墠椤电殑棣栨潯鏁版嵁鐨勮绱㈠紩
	 * @return
	 */
	public int getThisFirstElementNumber() {

		return (getThisPageNumber() - 1) * getPageSize() + 1;
	}
	
	/**
	 * 杩斿洖褰撳墠椤电殑鏈潯鏁版嵁鐨勮绱㈠紩
	 * @return
	 */
	public int getThisPageLastElementNumber() {
		int fullPage = getThisFirstElementNumber() + getPageSize() - 1;
		
		return getTotalCount() < fullPage ? getTotalCount() : fullPage;
	}
	
	/**
	 * 杩斿洖涓婁竴椤电殑椤电爜
	 * @return
	 */
	public int getPreviousPageNumber() {
		return getThisPageNumber() - 1;
	}
	
	/**
	 * 杩斿洖涓嬩竴椤电殑椤电爜
	 * @return
	 */
	public int getNextPageNumber() {
		return getThisPageNumber() + 1;
	}
	/**
	 * 姣忎竴椤垫樉绀虹殑鏉＄洰鏁�
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}
	
	/**
	 * 杩斿洖褰撳墠椤电殑椤电爜
	 * @return
	 */
	public int getThisPageNumber() {
		return 1;
	}
	
	/**
	 * 杩斿洖閾炬帴椤电爜鍒楄〃
	 * @param length 鍒楄〃闀垮害
	 * @return 1 2 [3] 4 5
	 */
	public List<Integer> getLinkPageNumbers(int length) {
		return PageUtil.generateLinkPageNumbers(getThisPageNumber(), getLastPageNumber(), length);
	}
	
	/**
	 * 杩斿洖绗竴鏉¤褰曠殑绱㈠紩
	 * @return
	 */
	public int getFirstResult() {
		return PageUtil.getFirstResult(pageNumber, pageSize);
	}

	public Iterator iterator() {
		return result == null ? Collections.emptyList().iterator() : result.iterator();
	}
	
}
