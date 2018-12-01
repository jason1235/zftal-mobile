package com.zfsoft.hrm.seniorreport.enums;

/**
 * 
 * @author ChenMinming
 * @date 2014-5-26
 * @version V1.0.0
 */
public enum ViewItemType {
	/**
	 * 根据行列的查询条件进行查询取得记录条目数
	 * 优先级最低 	当且仅当行列均为统计项时才有意义
	 */
	DATA_TYPE("数据统计项","DATA_TYPE"),
	/**
	 * 根据行列的查询条件进行查询取得记录条目数
	 * 优先级最低 	当且仅当行列均为统计项时才有意义
	 */
	COUNT_TYPE("条目统计项","COUNT_TYPE"),
	/**
	 * 该列/行 不进行任何统计操作  取默认值
	 * 优先级中等  	与统计项冲突时以固定值为准,
	 * 			   	行列均为固定值时以列上的固定值为准
	 */
	VALUE_TYPE("默认值","VALUE_TYPE"),
	/**
	 * 该列/行 不进行任何统计操作 也不生成任何数据 留空
	 * 优先级最高 任何受影响的行列均无值
	 **/
	NULL_TYPE("不统计","NULL_TYPE");

	private String text;
	private String key;
	private ViewItemType(String text, String key){
		this.text = text;
		this.key = key;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
