package com.common;


@SuppressWarnings("serial")
public class ConfigException extends RuntimeException {
	/**
	 * 
	 */
	public ConfigException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public ConfigException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public ConfigException(final Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ConfigException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
