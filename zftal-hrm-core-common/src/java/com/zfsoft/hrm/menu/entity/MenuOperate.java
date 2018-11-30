package com.zfsoft.hrm.menu.entity;

/** 
 * @author jinjj
 * @date 2013-2-25 下午03:00:15 
 *  菜单操作
 */
public class MenuOperate {

	private String menuId;
	
	private String operate;
	
	private Boolean checked=false;

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		if(obj instanceof MenuOperate){
			MenuOperate menuOperate = (MenuOperate) obj;
			
			if((menuId!=null&&operate!=null)
				&&(menuId.equals(menuOperate.getMenuId())&&operate.equals(menuOperate.getOperate())))
			{
				return true;
			}
		}
		return super.equals(obj);
	}
	
	
}
