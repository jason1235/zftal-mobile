package com.zfsoft.mobile.webservices.entity;

import com.zfsoft.dao.query.BaseQuery;

public class NoticeInfoEntity extends BaseQuery{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5592803345345440480L;
	private String resource_id;//娴佹按鍙�
	private String title;//鏍囬
	private String user_id;//鍙戣〃浜�
	private String belong_unit;//鎵�睘鏈烘瀯
	private String plate_id;//鎵�睘鐗堥潰
	private byte[] content;//閫氱煡鍐呭
	private String content_sort;//鍐呭鍒嗙被(1:閫氱煡 2:鏂伴椈 3:鍏憡)
	private String create_time;//鍙戝竷鏃堕棿
	private String audit_uid;//瀹℃牳浜�
	private String audit_time;//瀹℃牳鏃堕棿
	private String view_count;//闃呰閲�
	private String comment_count;//璇勮閲�
	private String share_count;//鍒嗕韩閲�
	private String fav_count;//鏀惰棌閲�
	private String is_ess;//鏄惁绮惧崕(0:涓嶆槸 銆�鏄�
	private String topi;//缃《
	private String is_draft;//鏄惁涓鸿崏绋�0:涓嶆槸 銆�鏄�
	private String is_archive;//鏄惁宸插瓨妗�0:涓嶆槸 銆�鏄�
	private String archive_time;//瀛樻。鏃堕棿
	private String is_rel_cal;//鏄惁涓庢棩绋嬪叧鑱�0:涓嶆槸 锛�鏄�浼氳 2鏄�璁插骇 3銆佹槸鍏跺畠)
	private String cal_time;//鏃ョ▼鏃堕棿
	private String cal_position;//鏃ョ▼鍦扮偣
	private String cal_receipt;//鏃ュ織鏄惁鍥炴墽(0:涓嶆槸 銆�鏄�
	private String cal_end_time;//缁撴潫鏃堕棿
	private String pim_pic;//灏侀潰鍥剧墖鍘熷浘
	private String pim_picx;//灏侀潰鍥剧墖缂╃暐鍥�
	private String is_perm_reply;//鏄惁鍥炲
	public String getResource_id() {
		return resource_id;
	}
	public void setResource_id(String resource_id) {
		this.resource_id = resource_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getBelong_unit() {
		return belong_unit;
	}
	public void setBelong_unit(String belong_unit) {
		this.belong_unit = belong_unit;
	}
	public String getPlate_id() {
		return plate_id;
	}
	public void setPlate_id(String plate_id) {
		this.plate_id = plate_id;
	}
	public String getContent_sort() {
		return content_sort;
	}
	public void setContent_sort(String content_sort) {
		this.content_sort = content_sort;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getAudit_uid() {
		return audit_uid;
	}
	public void setAudit_uid(String audit_uid) {
		this.audit_uid = audit_uid;
	}
	public String getAudit_time() {
		return audit_time;
	}
	public void setAudit_time(String audit_time) {
		this.audit_time = audit_time;
	}
	public String getView_count() {
		return view_count;
	}
	public void setView_count(String view_count) {
		this.view_count = view_count;
	}
	public String getComment_count() {
		return comment_count;
	}
	public void setComment_count(String comment_count) {
		this.comment_count = comment_count;
	}
	public String getShare_count() {
		return share_count;
	}
	public void setShare_count(String share_count) {
		this.share_count = share_count;
	}
	public String getFav_count() {
		return fav_count;
	}
	public void setFav_count(String fav_count) {
		this.fav_count = fav_count;
	}
	public String getIs_ess() {
		return is_ess;
	}
	public void setIs_ess(String is_ess) {
		this.is_ess = is_ess;
	}
	public String getIs_draft() {
		return is_draft;
	}
	public void setIs_draft(String is_draft) {
		this.is_draft = is_draft;
	}
	public String getIs_archive() {
		return is_archive;
	}
	public void setIs_archive(String is_archive) {
		this.is_archive = is_archive;
	}
	public String getArchive_time() {
		return archive_time;
	}
	public void setArchive_time(String archive_time) {
		this.archive_time = archive_time;
	}
	public String getIs_rel_cal() {
		return is_rel_cal;
	}
	public void setIs_rel_cal(String is_rel_cal) {
		this.is_rel_cal = is_rel_cal;
	}
	public String getCal_time() {
		return cal_time;
	}
	public void setCal_time(String cal_time) {
		this.cal_time = cal_time;
	}
	public String getCal_position() {
		return cal_position;
	}
	public void setCal_position(String cal_position) {
		this.cal_position = cal_position;
	}
	public String getCal_receipt() {
		return cal_receipt;
	}
	public void setCal_receipt(String cal_receipt) {
		this.cal_receipt = cal_receipt;
	}
	public String getCal_end_time() {
		return cal_end_time;
	}
	public void setCal_end_time(String cal_end_time) {
		this.cal_end_time = cal_end_time;
	}
	public String getPim_pic() {
		return pim_pic;
	}
	public void setPim_pic(String pim_pic) {
		this.pim_pic = pim_pic;
	}
	public String getPim_picx() {
		return pim_picx;
	}
	public void setPim_picx(String pim_picx) {
		this.pim_picx = pim_picx;
	}
	public String getIs_perm_reply() {
		return is_perm_reply;
	}
	public void setIs_perm_reply(String is_perm_reply) {
		this.is_perm_reply = is_perm_reply;
	}
	public void setTopi(String topi) {
		this.topi = topi;
	}
	public String getTopi() {
		return topi;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public byte[] getContent() {
		return content;
	}
	
	
	
}
