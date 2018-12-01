package com.zfsoft.dao.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLField {
	String value()  default"[unsignedField]";
	String tableName()  default"[unsignedtable]";
	boolean key () default false;
}
