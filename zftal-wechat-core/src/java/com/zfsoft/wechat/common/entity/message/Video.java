package com.zfsoft.wechat.common.entity.message;

/**
 * 视频消息
 * @author Administrator
 *
 */
public class Video {
    // 发送的媒体ID(必须-是)
    private String media_id;
    // 缩略图的媒体ID(必须-是)
    private String thumb_media_id;
    // 消息的标题(必须-否)
    private String title;
    // 消息的描述(必须-否)
    private String description;

    /**
     * @return the media_id
     */
    public String getMedia_id() {
        return media_id;
    }

    /**
     * @param media_id the media_id to set
     */
    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

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
    
}
