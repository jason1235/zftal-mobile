package com.zfsoft.wechat.common.entity.token;

import java.util.Date;

/**
 * 微信通用接口凭证
 * @author Administrator
 *
 */
public class AccessToken {
    // 获取到的凭证
    private String accessToken;
    // 凭证有效时间，单位：秒
    private int expiresIn;
    
    private Date createDate;
    
    
    /**
     * @return the accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }
    
    /**
     * @param accessToken the accessToken to set
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    /**
     * @return the expiresIn
     */
    public int getExpiresIn() {
        return expiresIn;
    }
    
    /**
     * @param expiresIn the expiresIn to set
     */
    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
        createDate = new Date();
    }

	/**
	 * 返回
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 判断是否过期
	 * 200秒之内都算过期
	 * @return
	 */
	public boolean isOut(){
		if(createDate==null) return true;
		System.out.println(new Date().getTime()/1000+200);
		System.out.println(createDate.getTime()/1000+expiresIn);
		System.out.println(new Date().getTime()/1000+200>createDate.getTime()/1000+expiresIn?"OUT TIME":"NOT OUT TIME");
		if(new Date().getTime()/1000+200>createDate.getTime()/1000+expiresIn){
			return true; 
		}
		return false;
	}
    
}
