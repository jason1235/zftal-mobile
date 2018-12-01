package com.jw.test;

import com.util.MiddleWareUtil;

public class testJWstrKey {

	/**
	 * <p>Description: </p>
	 * @param args
	 *
	 * @since 2015-3-16 下午01:48:54
	 * @author yangz
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	String 	xn="2013-2014";
	String 	xq="2";
	String	classcode="";
	String	startdate="2011-01-01";
	String	enddate="2015-01-01";
	String	schoolname="工商管理学院";
	String	teaname="蔡树堂";
		String parameterList=classcode + "&";
		String strKey =MiddleWareUtil.getJWSign(parameterList);
		
		System.out.println(strKey);
	}

}
