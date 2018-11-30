package com.zfsoft.wechat.common.entity.message;

/**
 * 音乐消息
 * @author Administrator
 *
 */
public class Music {
    // 缩略图的媒体ID(必须-是)
    private String thumb_media_id;
    // 消息的标题(必须-否)
    private String title;
    // 消息的描述(必须-否)
    private String description;
    // 音乐链接(必须-是)
    private String musicurl;
    // 高品质音乐链接，wifi环境优先使用该链接播放音乐(必须-是)
    private String hqmusicurl;
    
    /**
     * @return the thumb_media_id
     */
    public String getThumb_media_id() {
        return thumb_media_id;
    }

    /**
     * @param thumb_media_id the thumb_media_id to set
     */
    public void setThumb_media_id(String thumb_media_id) {
        this.thumb_media_id = thumb_media_id;
    }

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
     * @return the musicurl
     */
    public String getMusicurl() {
        return musicurl;
    }

    /**
     * @param musicurl the musicurl to set
     */
    public void setMusicurl(String musicurl) {
        this.musicurl = musicurl;
    }

    /**
     * @return the hqmusicurl
     */
    public String getHqmusicurl() {
        return hqmusicurl;
    }

    /**
     * @param hqmusicurl the hqmusicurl to set
     */
    public void setHqmusicurl(String hqmusicurl) {
        this.hqmusicurl = hqmusicurl;
    }
    
}
