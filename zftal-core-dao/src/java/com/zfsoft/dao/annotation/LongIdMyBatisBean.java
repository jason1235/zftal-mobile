package com.zfsoft.dao.annotation;

/**
 * 为了便于mybatis配置文件编写而构建的类，提供获取所有sql字段的方法等
 * @author 沈鲁威 Patrick Shen
 * @since 2012-8-8
 * @version V1.0.0
 */
public class LongIdMyBatisBean extends MyBatisBean{
	
	@SQLField(key=true)
	protected Long id;
	
	public Long getId() {
		if(new Long(0).equals(id)){
			id=null;
		}
		return id;
	}


	public void setId(Long id) {
		if(new Long(0).equals(id)){
			id=null;
		}
		this.id = id;
	}
	
	public void setId(String id) {
		if(id==null){
			this.id=null;
		}else{
			this.id=new Long(id);
		}
	}

	public LongIdMyBatisBean(){
		super();
	}
	
}
