package com.zfsoft.wechat.common.entity.token;

/**
 * 网页授权信息
 */
public class WeChatOauth2Token {
    // 网页授权接口调用凭证
    private String accessToken;
    // 凭证有效时长
    private int expiresIn;
    // 用于刷新凭证
    private String refreshToken;
    // 用户标识
    private String openId;
    // 用户授权作用域
    private String scope;
    // 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
    private String unionid;
    
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
    }

    /**
     * @return the refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * @param refreshToken the refreshToken to set
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * @return the openId
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * @param openId the openId to set
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * @return the scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * @param scope the scope to set
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * @return the unionid
     */
    public String getUnionid() {
        return unionid;
    }

    /**
     * @param unionid the unionid to set
     */
    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

}
