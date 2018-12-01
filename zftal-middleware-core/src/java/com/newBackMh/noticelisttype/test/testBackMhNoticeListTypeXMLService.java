package com.newBackMh.noticelisttype.test;

import java.util.Arrays;
import java.util.List;

import com.common.JavaXML;
import com.common.backMhConfig;

public class testBackMhNoticeListTypeXMLService {

	/**
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param args
	 * 
	 * @since 2015-2-4 下午02:37:42
	 * @author yangz
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// List list = XMLParser.ReadXMl();
		String a = "验证失败";
		String b = "101";
		String tabNames = JavaXML.BuildXMLReturnErro(a, b);
		System.out.println(tabNames);

		
		String str=null;
		String TabName = backMhConfig.getString("TeaBackMhNoticeListTypeName");
		String TabType = backMhConfig.getString("TeaBackMhNoticeListType");
		String TabStauts = backMhConfig.getString("TeaBackMhNoticeListStatus");

		// String NoticeType =
		// backMhConfig.getString("backMhNoticeListName");
		String[] tabmane = TabName.split(";");
		String[] tabtype = TabType.split(";");
		String[] tabstatus = TabStauts.split(";");
		// String[] noticetype = NoticeType.split(";");
		List tabmanelist = Arrays.asList(tabmane);
		List tabtypelist = Arrays.asList(tabtype);
		List tabstatuslist = Arrays.asList(tabstatus);
		// List tabnoticetype = Arrays.asList(noticetype);
		// List list = XMLParser.ReadXMl();
		str = JavaXML.BuildXMLForBackMhNewNoticeList(tabmanelist, tabtypelist,
				tabstatuslist);
		System.out.println(str);
		
	}

}
