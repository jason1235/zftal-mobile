package com.zfsoft.mobile.skin.dao;

import java.util.List;

import com.zfsoft.mobile.skin.entity.SkinEntity;
import com.zfsoft.mobile.skin.entity.SkinPreviewPicsEntity;

public interface ISkinDao {
	
	List<SkinEntity> getSkinList();
	
	List<SkinPreviewPicsEntity> getPreviewPicList();
}
