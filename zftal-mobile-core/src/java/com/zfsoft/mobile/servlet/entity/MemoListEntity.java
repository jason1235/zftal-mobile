/**
 * 
 */
package com.zfsoft.mobile.servlet.entity;

import java.util.List;

import com.zfsoft.mobile.webservices.entity.MemoDB;

/**
 * @author zhangxu
 * @description 备忘录列表
 * @date 2017-5-24 上午10:09:57
 */
public class MemoListEntity {

	private boolean isOvered;//是否结束
	private List<MemoDB> memoList;
	
	public boolean isOvered() {
		return isOvered;
	}
	public void setOvered(boolean isOvered) {
		this.isOvered = isOvered;
	}
	public List<MemoDB> getMemoList() {
		return memoList;
	}
	public void setMemoList(List<MemoDB> memoList) {
		this.memoList = memoList;
	}
	
	
}
