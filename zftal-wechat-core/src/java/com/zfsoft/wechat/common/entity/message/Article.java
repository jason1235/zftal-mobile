package com.zfsoft.wechat.common.entity.message;

/**
 * 图文model
 */
public class Article {
    // 图文消息名称(必须-否)
    private String title;
    // 图文消息描述(必须-否)
    private String description;
    // 图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80(必须-否)
    private String picurl;
    // 点击图文消息跳转链接(必须-否)
    private String url;
    
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
    * @return the picurl
    */
   public String getPicurl() {
      return picurl;
   }

   /**
    * @param picurl the picurl to set
    */
   public void setPicurl(String picurl) {
      this.picurl = picurl;
   }

   /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

}

