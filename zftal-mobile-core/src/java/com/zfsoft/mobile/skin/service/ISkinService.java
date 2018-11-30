package com.zfsoft.mobile.skin.service;

import java.util.List;

import com.zfsoft.mobile.skin.entity.SkinEntity;
import com.zfsoft.mobile.skin.entity.SkinPreviewPicsEntity;

/**
 * 手机端皮肤service
 * @author liucb
 *
 */
public interface ISkinService {

    List<SkinEntity> getSkinList();
	
	List<SkinPreviewPicsEntity> getPreviewPicList();
}
