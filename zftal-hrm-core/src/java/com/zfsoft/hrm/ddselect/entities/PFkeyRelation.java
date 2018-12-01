package com.zfsoft.hrm.ddselect.entities;

import java.io.Serializable;

/**
 * 
 * 类描述：表中的主外键关联关系
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-8-14 下午01:03:58
 */
public class PFkeyRelation implements Serializable {

	/* serialVersionUID: serialVersionUID */
	
	private static final long serialVersionUID = 6967111617060420819L;

	private String pk_constraint_name;
	
	private String pk_table_name;
	
	private String pk_column_name;

	private String fk_constraint_name;
	
	private String fk_table_name;
	
	private String fk_column_name;
	
	private String ke_seq;

	/**
	 * @return pk_constraint_name : return the property pk_constraint_name.
	 */
	
	public String getPk_constraint_name() {
		return pk_constraint_name;
	}

	/**
	 * @param pk_constraint_name : set the property pk_constraint_name.
	 */
	
	public void setPk_constraint_name(String pk_constraint_name) {
		this.pk_constraint_name = pk_constraint_name;
	}

	/**
	 * @return pk_table_name : return the property pk_table_name.
	 */
	
	public String getPk_table_name() {
		return pk_table_name;
	}

	/**
	 * @param pk_table_name : set the property pk_table_name.
	 */
	
	public void setPk_table_name(String pk_table_name) {
		this.pk_table_name = pk_table_name;
	}

	/**
	 * @return pk_column_name : return the property pk_column_name.
	 */
	
	public String getPk_column_name() {
		return pk_column_name;
	}

	/**
	 * @param pk_column_name : set the property pk_column_name.
	 */
	
	public void setPk_column_name(String pk_column_name) {
		this.pk_column_name = pk_column_name;
	}

	/**
	 * @return fk_constraint_name : return the property fk_constraint_name.
	 */
	
	public String getFk_constraint_name() {
		return fk_constraint_name;
	}

	/**
	 * @param fk_constraint_name : set the property fk_constraint_name.
	 */
	
	public void setFk_constraint_name(String fk_constraint_name) {
		this.fk_constraint_name = fk_constraint_name;
	}

	/**
	 * @return fk_table_name : return the property fk_table_name.
	 */
	
	public String getFk_table_name() {
		return fk_table_name;
	}

	/**
	 * @param fk_table_name : set the property fk_table_name.
	 */
	
	public void setFk_table_name(String fk_table_name) {
		this.fk_table_name = fk_table_name;
	}

	/**
	 * @return fk_column_name : return the property fk_column_name.
	 */
	
	public String getFk_column_name() {
		return fk_column_name;
	}

	/**
	 * @param fk_column_name : set the property fk_column_name.
	 */
	
	public void setFk_column_name(String fk_column_name) {
		this.fk_column_name = fk_column_name;
	}

	/**
	 * @return ke_seq : return the property ke_seq.
	 */
	
	public String getKe_seq() {
		return ke_seq;
	}

	/**
	 * @param ke_seq : set the property ke_seq.
	 */
	
	public void setKe_seq(String ke_seq) {
		this.ke_seq = ke_seq;
	}
}
