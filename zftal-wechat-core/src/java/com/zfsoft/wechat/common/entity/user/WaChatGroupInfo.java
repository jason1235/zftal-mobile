package com.zfsoft.wechat.common.entity.user;

/**
 * 公众账号分组信息
 */
public class WaChatGroupInfo {
    // 分组id
    private int id;
    // 分组名称
    private String name;
    // 分组内的用户数
    private int count;
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
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

}
