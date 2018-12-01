package com.zfsoft.mobile.version.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.util.GeneralCalendar;

/**
 * <p>Description:时间处理函数 </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2011-10-23下午04:26:49
 * @author:xxh
 * @version 1.0
 */
public class DateUtil {
    private static Calendar cal = Calendar.getInstance();
    public DateUtil() {
    }

    /**
     * 获取当前月份（2位数）
     * @return String 月份
     */
    public static String getMonth() {
    int m = cal.get(Calendar.MONTH);
        if (m < 10) {
            return Integer.toString(cal.get(Calendar.YEAR))
                + "0" + Integer.toString(m);
        }
        else {
            return Integer.toString(cal.get(Calendar.YEAR))
                + Integer.toString(m);

        }
    }
    /**
     * 将Date类型数据转换成"yyyy-MM-dd"格式的string类型数据.
     * @return String
     * @xxh
     */
    public static String toString(Date date){
		String str="";
		if (date!=null)
		    str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
		
		return str;
    }
    
    /**
     * <p>Description:将Date类型数据转换成format(如："yyyy-MM-dd")格式的string类型数据. </p>
     * @param date
     * @param format
     * @return
     *
     * @since Jul 12, 2012 6:33:18 PM
     * @author xuxinghua
     */
    public static String toDateFormatString(Date date,String format){
		String str="";
		if (date!=null)
		    str = (new SimpleDateFormat(format)).format(date);
		
		return str;
    }

	/**
	 * @获取下一天的日期
	 * @getNextDate
	 * @param Date
	 * @return Date
	 * @author cyj
	 * @since 20080829
	 */
	public static Date getNextDate(Date oldDate) {
		try{
			if(oldDate!=null){
				SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
				String tempDate1 = simpleDate.format(oldDate);
				int tempDate2 = (new Integer(tempDate1.replaceAll("-", ""))).intValue();
				tempDate2 = GeneralCalendar.getNextDate(tempDate2, 1);
				tempDate1 = (new Integer(tempDate2)).toString();
				tempDate1 = tempDate1.substring(0, 4) + "-" + tempDate1.substring(4, 6) + "-" + tempDate1.substring(6, 8);
				oldDate = simpleDate.parse(tempDate1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return oldDate;
	}

	/**
	 * @获取当前日期N天的日期
	 * @
	 * @param Date
	 * @return Date
	 * @author llj
	 * @since 20081008
	 */
	public static Date getDaysDate(Date oldDate, int days) {
		try{
			if(oldDate!=null){
				SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
				String tempDate1 = simpleDate.format(oldDate);
				int tempDate2 = (new Integer(tempDate1.replaceAll("-", ""))).intValue();
				tempDate2 = GeneralCalendar.getNextDate(tempDate2, days);
				tempDate1 = (new Integer(tempDate2)).toString();
				tempDate1 = tempDate1.substring(0, 4) + "-" + tempDate1.substring(4, 6) + "-" + tempDate1.substring(6, 8);
				oldDate = simpleDate.parse(tempDate1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return oldDate;
	}
	
	
	
    /**
     * 将Date类型数据转换成"yyyymmdd"格式的int类型数据.
     * @return Integer
     * 
     */
    public static Integer toInteger(Date date){
		String str="";
		Integer idate=0;
		if (date!=null) {
		    str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
		    str = str.replaceAll("-", "");
		    idate = Integer.valueOf(str);
		}
		return idate;
    }    
    /**
     * 将Date类型数据转换成"yyyymmdd"格式的int类型数据.
     * @return Integer
     * 
     */
    public static int toInt(Date date){
		String str="";
		Integer idate=0;
		if (date!=null) {
		    str = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
		    str = str.replaceAll("-", "");
		    idate = Integer.valueOf(str).intValue();
		}
		return idate.intValue();
    }    

    /**
     * 将"yyyyMMdd"格式Sting类型数据转换成"yyyy-MM-dd"格式的string类型数据.
     * @return String
     * @xxh
     * @since 2008-07-16
     */
    public static String DateFormatToStr1(String date){
		String str="";
		if (date!=null)
		    str = date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8);
		
		return str;
    }
    /**
     * 将"yyyy-MM-dd"格式Sting类型数据转换成"yyyyMMdd"格式的string类型数据.
     * @return String
     * @xxh
     * @since 2008-07-16
     */
    public static String DateFormatToStr2(String date){
		String str="";
		if (date!=null)
		    str = date.substring(0,4)+date.substring(5,7)+date.substring(8,10);
		
		return str;
    }
    /**
     * 将"yyyy-MM-dd"格式Sting类型数据转换成"yyyyMMdd"格式的Integer类型数据.
     * @return String
     * @xxh
     * @since 2008-07-16
     */
    public static Integer DateFormatStrToInteger(String date){
		Integer str = null;
		if (date!=null)
		    str = new Integer(date.substring(0,4)+date.substring(5,7)+date.substring(8,10));
		
		return str;
    }
    
    /**
     * 将"HHmmss"格式Sting类型数据转换成"HH:mm:ss"格式的string类型数据.
     * @return String
     * @xxh
     * @since 2008-07-16
     */
    public static String TimeFormatToStr1(String date){
		String str="";
		if (date!=null){
			if (date.length()<6 && date.length()>4)//Hmmss
				str = date.substring(0,1)+":"+date.substring(1,3)+":"+date.substring(3,5);
			else if (date.length()==6)//HHmmss
				str = date.substring(0,2)+":"+date.substring(2,4)+":"+date.substring(4,6);
			else//其他数据
				str = date;
		}
		
		return str;
    }
    /**
     * 将"HH:mm:ss"格式Sting类型数据转换成"HHmmss"格式的string类型数据.
     * @return String
     * @xxh
     * @since 2008-07-16
     */
    public static String TimeFormatToStr2(String date){
		String str="";
		if (date!=null){
			if (date.length()<8 && date.length()>6)
				str = date.substring(0,1)+date.substring(2,4)+date.substring(5,7);
			else if (date.length() == 8)
				str = date.substring(0,2)+date.substring(3,5)+date.substring(6,8);
			else
				str = date;
		}
		
		return str;
    }
    
    /**
     * 返回整型时间
     * @return
     */
    public static int getTimeInt(){
    	Calendar cal = Calendar.getInstance();
    	return cal.get(Calendar.HOUR_OF_DAY) * 10000
    		+ cal.get(Calendar.MINUTE) * 100
    		+ cal.get(Calendar.SECOND);
    }
    
}
