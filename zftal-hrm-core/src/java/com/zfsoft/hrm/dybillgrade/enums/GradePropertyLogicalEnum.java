package com.zfsoft.hrm.dybillgrade.enums;

/**
 * 
 * @author ChenMinming
 * @date 2014-6-10
 * @version V1.0.0
 */
public enum GradePropertyLogicalEnum {
		AND("并且","AND","&&"),
		OR("或者","OR","||");
		
		private final String key;
		private final String text;
		private final String rel;
		private GradePropertyLogicalEnum(String text, String key, String rel) {
			this.text = text;
			this.rel = rel;
			this.key = key;
		}
		public String getKey() {
			return key;
		}
		public String getText() {
			return text;
		}
		public String getRel() {
			return rel;
		}
		public static GradePropertyLogicalEnum getGradePropertyLogicalEnum(String key){
			try {
				return valueOf(key);
			} catch (Exception e) {
				return AND;
			}
		}
		
}
