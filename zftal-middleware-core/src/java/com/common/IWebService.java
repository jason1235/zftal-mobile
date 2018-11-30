package com.common;

import org.apache.axis.client.Call;
import org.springframework.stereotype.Service;

/**
 * <p>Description: webservice接口</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2010-10-8上午11:57:18
 * @author:xxh
 * @version 1.0
 */
@Service
public interface IWebService {
	public Call getCall();
}
