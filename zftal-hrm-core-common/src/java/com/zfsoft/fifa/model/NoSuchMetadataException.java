package com.zfsoft.fifa.model;

/**
 * 对应资源的BeanMetadata元数据文件不存在.
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-18
 * @version V1.0.0
 */
public class NoSuchMetadataException extends RuntimeException {

	private static final long serialVersionUID = -3509382823746717197L;

	public NoSuchMetadataException() {
		super();
	}

	public NoSuchMetadataException(String message) {
		super(message);
	}
	
	public NoSuchMetadataException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchMetadataException(Throwable cause) {
		super(cause);
	}
	
}
