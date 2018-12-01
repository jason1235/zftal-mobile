package com.zfsoft.wechat.common.entity.user;

import java.util.List;

/**
 * 关注用户列表
 */
public class WeChatUserList {
    // 公众账号的总关注用户数
    private int total;
    // 获取的OpenID个数
    private int count;
    // OpenID列表
    private List<String> openIdList;
    // 拉取列表的后一个用户的OPENID
    private String nextOpenId;
    
    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }
    
    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * @return the openIdList
     */
    public List<String> getOpenIdList() {
        return openIdList;
    }

    /**
     * @param openIdList the openIdList to set
     */
    public void setOpenIdList(List<String> openIdList) {
        this.openIdList = openIdList;
    }

    /**
     * @return the nextOpenId
     */
    public String getNextOpenId() {
        return nextOpenId;
    }

    /**
     * @param nextOpenId the nextOpenId to set
     */
    public void setNextOpenId(String nextOpenId) {
        this.nextOpenId = nextOpenId;
    }

}

