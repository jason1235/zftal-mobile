package com.zfsoft.mobile.classCommunity.query;

/**
 * 评论实体
 * @author H110MF
 *
 */
public class DynamicCommentEntityQuery {
	    private String id;            //唯一主键
	    private String publisherId;   //评论人id
	    private String publisherName; //评论人姓名
	    private String content;       // 评论内容
	    private String createTtime;   //评论时间
	    private String dynamicId;     //帖子id
	    
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getPublisherId() {
			return publisherId;
		}
		public void setPublisherId(String publisherId) {
			this.publisherId = publisherId;
		}
		public String getPublisherName() {
			return publisherName;
		}
		public void setPublisherName(String publisherName) {
			this.publisherName = publisherName;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getCreateTtime() {
			return createTtime;
		}
		public void setCreateTtime(String createTtime) {
			this.createTtime = createTtime;
		}
		public String getDynamicId() {
			return dynamicId;
		}
		public void setDynamicId(String dynamicId) {
			this.dynamicId = dynamicId;
		}
}
