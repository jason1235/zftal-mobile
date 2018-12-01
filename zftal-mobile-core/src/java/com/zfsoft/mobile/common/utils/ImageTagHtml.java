package com.zfsoft.mobile.common.utils;

import com.zfsoft.hrm.baseinfo.dyna.html.ParseFactory;
import com.zfsoft.hrm.baseinfo.dyna.html.ViewParse;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;

/**
 * 
 * @author Administrator
 *
 */
public class ImageTagHtml {

    /**
     * 上传图片
     * @param fieldName 控件的名称
     * @param fieldType 控件展示的图片类型（com.zfsoft.hrm.baseinfo.dyna.html.Type.IMAGE、com.zfsoft.hrm.baseinfo.dyna.html.Type.PHOTO）
     * @param size 图片大小
     * @param width 图片宽度
     * @param height 图片高度
     * @param imageId 图片ID
     * @param modify 是否可修改(true 可修改 false 不可修改)
     * @return imageHtml 图片上传控件的HTML
     */
    public static String getImageHtml(String fieldName, String fieldType, int size, int width, int height, String imageId, boolean modify) {
        String imageHtml = "";
        InfoProperty prop = new InfoProperty();
        
        prop.setFieldName(fieldName);
        prop.setFieldType(fieldType);
        prop.setSize(size);
        prop.setWidth(width);
        prop.setHeight(height);
        
        if (modify) {
            imageHtml = ParseFactory.EditParse(prop, imageId);
        } else {
            imageHtml = ViewParse.parse(prop, imageId);
        }
        
        return imageHtml;
    }

	public static String getFileHtml(String fieldName, String fieldType,String fileType, int size, int width, int height, String imageId) {
		// TODO Auto-generated method stub
		String fileHtml = "";
        InfoProperty prop = new InfoProperty();
        
        prop.setFieldName(fieldName);
        prop.setFieldType(fieldType);
        prop.setFileType(fileType);
        prop.setSize(size);
        prop.setWidth(width);
        prop.setHeight(height);
        
        fileHtml = ParseFactory.EditParse(prop, imageId);
        
        return fileHtml;
	}
}
