package com.zfsoft.orcus.lang;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.zfsoft.orcus.lang.converters.ConvertUtil;
import com.zfsoft.orcus.lang.converters.Converter;

/**
 * 日期操作的工具类，包括获取日期、格式化日期、日期解析、日期计算、年龄计算
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-19
 * @version V1.0.0
 */
public class TimeUtil {
	public final static String  yyyyMMdd				= "yyyyMMdd";
	public final static String  yyyy_MM_dd				= "yyyy-MM-dd";
	public final static String  HH_mm_ss				= "HH:mm:ss";
	public final static String  HH_mm_ss_SSS			= "HH:mm:ss.SSS";
	public final static String  yyyy_MM_dd_HH_mm_ss		= "yyyy-MM-dd HH:mm:ss";
	public final static String  yyyy_MM_dd_HH_mm_ss_SSS	= "yyyy-MM-dd HH:mm:ss.SSS";
	
	private final static Format _dateFormat			= new SimpleDateFormat( yyyyMMdd );    
	private final static Format _dateFormatX		= new SimpleDateFormat( yyyy_MM_dd );
	private final static Format _timeFormat			= new SimpleDateFormat( HH_mm_ss );
	private final static Format _timeFormatS		= new SimpleDateFormat( HH_mm_ss_SSS );    
	private final static Format _dateTimeFormat		= new SimpleDateFormat( yyyy_MM_dd_HH_mm_ss );
	private final static Format _dateTimeFormatS	= new SimpleDateFormat( yyyy_MM_dd_HH_mm_ss_SSS );
	
	public final static long MillisecondsOfOneDay		= 24*60*60*1000;
	public final static long MillisecondsOfOneHour		= 60*60*1000;
	public final static long MillisecondsOfOneMinute	= 60*1000;
     
    /**
     * 返回一个表示当前时间的时间字符串，参数format指定时间的格式
     * 
     * @param format  时间格式，如"yyyy-MM-dd HH:mm:ss.SSS"，如果format等于null或""，则采用格式"yyyy-MM-dd HH:mm:ss"
     */
	public static String current( String format ) {
		Format formater = null;
		
		if( format == null || format.length() == 0 ) {
			formater = new SimpleDateFormat( yyyy_MM_dd_HH_mm_ss );
		} else {
			formater = new SimpleDateFormat( format );
		}
		
		Date date = new Date();
		
		return formater.format( date );         
	}
    
	/**
	 * 返回一个表示当前时间的时间字符串，时间的格式为"yyyyMMdd"
	 * @return String 如"20040619"
	 */
	public static String date() {           
		Date date = new Date();
		
		return _dateFormat.format( date );
	}
	
	/**
	 * 返回一个表示当前时间的时间字符串，时间的格式为"yyyy-MM-dd"
	 * @return String 如"2004-06-19"
     */         
    public static String dateX()
    {           
        Date date = new Date();
        
        return _dateFormatX.format( date );         
    }   
        
    /**
     * 返回一个表示当前时间的时间字符串，时间的格式为"HH:mm:ss"
     * 
     * @return String 如"13:02:56"
     */         
    public static String time()
    {           
        Date date = new Date();
        
        return _timeFormat.format( date );          
    }
    
    /**
     * 返回一个表示当前时间的时间字符串，时间的格式为"HH:mm:ss.SSS"
     * 
     * @return String 如"13:02:56.345"
     */         
    public static String timeWithMs()
    {           
        Date date = new Date();
        
        return _timeFormatS.format( date );         
    }
    
    /**
     * 返回一个表示当前时间的时间字符串，时间的格式为"yyyy-MM-dd HH:mm:ss"
     * 
     * @return String 如"2004-06-19 13:02:56"
     */         
    public static String dateTime()
    {           
        Date date = new Date();
        
        return _dateTimeFormat.format( date );          
    }
    
    /**
     * 返回一个表示当前时间的时间字符串，时间的格式为"yyyy-MM-dd HH:mm:ss.SSS"
     * 
     * @return String 如"2004-06-19 13:02:56.345"
     */         
    public static String dateTimeWithMs()
    {           
        Date date = new Date();

        return _dateTimeFormatS.format( date );         
    }
    
    /**
     * 返回表示当前年份的整数
     */         
    public static int year()
    {
        Calendar today = Calendar.getInstance();
        
        return today.get( Calendar.YEAR );          
    }   
    
    /**
     * 返回表示当前月份的整数
     */     
    public static int month()
    {
        Calendar today = Calendar.getInstance();
        
        return today.get( Calendar.MONTH );         
    }   
    
    /**
     * 返回表示当前日期(在月份中)的整数
     */     
    public static int dayOfMonth()
    {
        Calendar today = Calendar.getInstance();
        
        return today.get( Calendar.DAY_OF_MONTH );          
    }   
    
    /**
     * 返回表示当前日期(在星期中)的整数
     */     
    public static int dayOfWeek()
    {
        Calendar today = Calendar.getInstance();
        
        return today.get( Calendar.DAY_OF_WEEK );           
    }
    
    /**
     * 返回表示当前小时的整数
     */     
    public static int hour()
    {
        Calendar today = Calendar.getInstance();
        
        return today.get( Calendar.HOUR );          
    }
    
    /**
     * 返回表示当前分钟的整数
     */     
    public static int minute()
    {
        Calendar today = Calendar.getInstance();
        
        return today.get( Calendar.MINUTE );            
    }
    
    /**
     * 返回表示当前秒的整数
     */     
    public static int second()
    {
        Calendar today = Calendar.getInstance();
        
        return today.get( Calendar.SECOND );            
    }
    
    /**
     * 返回表示当前毫秒的整数
     */     
    public static int millisecond()
    {
        Calendar today = Calendar.getInstance();
        
        return today.get( Calendar.MILLISECOND );           
    }   
    
    /**
     * 返回当前月份所包含的天数
     */     
    public static int daysOfMonth()
    {
        Calendar today = Calendar.getInstance();
        
        return today.getActualMaximum( Calendar.DAY_OF_MONTH );         
    }
    
    /**
     * 返回指定时间中的月份所包含的天数, 如果time不是合法的时间字符串，则返回-1 
     * 
     * @param time  时间字符串，如  "200406" 、"20040619"、"2004-06-19"、"2004-06-19 13:02:56.345"
     */     
    public static int daysOfMonth( String time )
    {
        Calendar calendar = toCalendar( time );
        
        if( calendar == null )
            return -1;
            
        return calendar.getActualMaximum( Calendar.DAY_OF_MONTH );          
    }
        
    /**
     * 返回当前月份开始时的时间字符串
     * 
     * @return String 时间字符串，如"2004-06-01 00:00:00"
     */     
    public static String beginTimeOfMonth()
    {
        return current( "yyyy-MM-01 00:00:00" );            
    }
    
    /**
     * 返回指定时间中的月份开始时的时间字符串
     * 
     * @param time  时间字符串，如  "200406" 、"20040619"、"2004-06-19"、"2004-06-19 13:02:56.345"
     * @return String 时间字符串，如"2004-06-01 00:00:00"
     */     
    public static String beginTimeOfMonth( String time )
    {
        return format( time, "yyyy-MM-01 00:00:00" );           
    }   
    
    /**
     * 返回当前月份开始时的时间字符串
     * 
     * @return String 时间字符串，如"2004-06-01 00:00:00"
     */     
    public static String endTimeOfMonth()
    {
        NumberFormat format = new DecimalFormat( "00" );
        
        return current( "yyyy-MM" + format.format( daysOfMonth() ) + " 23:59:59" );         
    }
    
    /**
     * 返回指定时间中的月份开始时的时间字符串
     * 
     * @param time  时间字符串，如  "200406" 、"20040619"、"2004-06-19"、"2004-06-19 13:02:56.345"
     * @return String 时间字符串，如"2004-06-01 00:00:00"
     */     
    public static String endTimeOfMonth( String time )
    {
        NumberFormat format = new DecimalFormat( "00" );
        
        return format( time, "yyyy-MM-" + format.format( daysOfMonth( time ) ) + " 23:59:59" );          
    }
    
    /**
     * 解析时间字符串
     * 
     * @param time  时间字符串，如  "200406" 、"20040619"、"2004-06-19"、"2004-06-19 13:02:56.345"
     * @return 
     * 		<ul>
     * 			int[8]
     * 			<li>int[0] indicates count of fields</li>
     * 			<li>int[1] indicates YEAR</li>
     * 			<li>int[2] indicates MONTH</li>
     * 			<li>int[3] indicates DAY</li>
     * 			<li>int[4] indicates HOUR</li>
     * 			<li>int[5] indicates MINUTE</li>
     * 			<li>int[6] indicates SECOND</li>
     * 			<li>int[7] indicates MILLISECOND</li>
     * 		</ul>
     */         
    public static int[] parse( String time )
    {       
        int[] values = new int[8];
         
        if( time == null || time.length() < 4 )
            return values;
        
        String[] tokens = StringUtil.split( time, " -\\:'\";,./?<>！!@#$%^&*()_+={}{}|~`。，：、《》" );
        Converter cr = ConvertUtil.lookup( int.class );
        if( tokens.length > 1 )
        {
            values[0] = tokens.length;
            for( int i=0; i<7 && i<values[0]; i++ )
            {
                values[i+1] = ((Integer)cr.convert( int.class, tokens[i] )).intValue();
            }
            
            return values;
        }
        
        StringBuffer buffer = new StringBuffer( "" );
        
        for( int i=0; i<time.length(); i++ )
        {
            char c = time.charAt( i );
            
            if( c >= '0' && c <= '9' )
                buffer.append( c );
        }
        
        if( buffer.length() >= 4 )
        {
            values[0] = 1;
            values[1] = Integer.parseInt( buffer.substring( 0, 4 ) );
        }
        
        if( buffer.length() == 5 )
        {
            values[0]++;
            values[2] = Integer.parseInt( buffer.substring( 4, 5 ) );
            return values;
        }  
        
        if( buffer.length() >= 6 )
        {
            values[0]++;
            values[2] = Integer.parseInt( buffer.substring( 4, 6 ) );
        }       
        
        if( buffer.length() == 7 )
        {
            values[0]++;
            values[3] = Integer.parseInt( buffer.substring( 6, 7 ) );
            return values;
        }  
        
        if( buffer.length() >= 8 )
        {
            values[0]++;
            values[3] = Integer.parseInt( buffer.substring( 6, 8 ) );
        }
        
        if( buffer.length() >= 10 )
        {
            values[0]++;
            values[4] = Integer.parseInt( buffer.substring( 8, 10 ) );
        }
        
        if( buffer.length() >= 12 )
        {
            values[0]++;
            values[5] = Integer.parseInt( buffer.substring( 10, 12 ) );
        }
        
        if( buffer.length() >= 14 )
        {
            values[0]++;
            values[6] = Integer.parseInt( buffer.substring( 12, 14 ) );
        }
        
        if( buffer.length() >= 17 )
        {
            values[0]++;
            values[7] = Integer.parseInt( buffer.substring( 14, 17 ) );
        }
                
        return values;
                
    }
    
    /**
     * 返回与time对应的<code>Calendar</code>
     * 
     * @param time  时间字符串，如  "200406" 、"20040619"、"2004-06-19"、"2004-06-19 13:02:56.345"
     * @return Calendar 如果time不是合法的时间字符串，则返回null
     */         
    public static Calendar toCalendar( String time )
    {       
        int[] values = parse( time );
        
        if( values[0] == 0 )
            return null;
        
        Calendar ret = null;
        
        if( values[1] == 0 )
        {
            ret = new GregorianCalendar( 0, 0, 1, 0, 1,1 );
            
        }
        else if( values[1] == 9999 )
        {
            ret = new GregorianCalendar( 9999, 11, 30, 23, 59, 59 );
        }
        else
        {
            ret = new GregorianCalendar( values[1], values[2]==0 ? 0 : values[2]-1, values[3] == 0 ? 1 : values[3], values[4], values[5], values[6] );
        
            ret.set( Calendar.MILLISECOND, values[7] );         
        }

        return ret; 
    }
    
    /**
     * 返回与time对应的<code>Date</code>
     * 
     * @param time  时间字符串，如  "200406" 、"20040619"、"2004-06-19"、"2004-06-19 13:02:56.345"
     * @return Date 如果time不是合法的时间字符串，则返回null
     */         
    public static Date toDate( String time )
    {       
        Calendar calendar = toCalendar( time );
        
        if( calendar == null ) {
        	return null;
        } else {
        	return calendar.getTime();
        }
                
    }
        
    /**
     * 返回与time对应的<code>Long</code>(the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this date.)
     * 
     * @param time  时间字符串，如  "200406" 、"20040619"、"2004-06-19"、"2004-06-19 13:02:56.345"
     * @return Calendar 如果time不是合法的时间字符串，则返回-1L
     */         
    public static long toLong( String time )
    {       
   Date date = toDate( time );
    
    if( date == null ) {
        return -1L;
    } else {
        return date.getTime();
    }
                
    }
        
    /**
     * 返回人的年龄, 如果birthDay不是合法的时间字符串，则返回-1 
     * 
     * @param birthday  生日，如  1980-12-01
     */         
    public static int getAge( String birthday )
    {       
        Calendar birth = toCalendar( birthday );
        
        if( birth == null )
            return -1;
            
        Calendar today = Calendar.getInstance();
        
        int age = today.get( Calendar.YEAR ) - birth.get( Calendar.YEAR );
        
        birth.add( Calendar.YEAR, age );
        
        if( today.before( birth ) ) --age;
        
        return age;     
    }   
    
    /**
     * 格式化时间字符串(仅用于WEB)
     * 
     * @param time  时间字符串，如  "200406" 、"20040619"、"2004-06-19"、"2004-06-19 13:02:56.345"
     * @return String 如果time不是合法的时间字符串，则返回""
     */         
    public static String format( String time )
    {       
        int[] values = parse( time );
        
        if( values[0] == 0 )
            return "";
        else if( values[1] == 0 )
            return "";
        else if( values[1] == 9999 )
            return "?";
        
        NumberFormat yearFormat  = new DecimalFormat( "0000" ); 
        NumberFormat otherFormat = new DecimalFormat( "00" );
                
        String ret = yearFormat.format( values[1] );

        for( int i=2; i<values[0]+1; i++ )
        {
            if( i < 4 ) 
                ret += "-" + otherFormat.format( values[i] );
            else if( i == 4 )
                ret += " " + otherFormat.format( values[i] );
            else if( i > 4 && i < 7 )
                ret += ":" + otherFormat.format( values[i] );   
            else
                ret += "." + otherFormat.format( values[i] );       
        }
        
        return ret; 
    }
    
    /**
     * 格式化时间字符串
     * 
     * @param time  时间字符串，如  "200406" 、"20040619"、"2004-06-19"、"2004-06-19 13:02:56.345"
     * @param format  时间格式，如"yyyy-MM-dd HH:mm:ss.SSS"，如果format等于null或""，则采用格式"yyyy-MM-dd HH:mm:ss"
     * @return String 如果time不是合法的时间字符串，则返回""
     */         
    public static String format( String time, String format )
    {       
        Calendar calendar = toCalendar( time ); 
        
        if( calendar == null )
            return "";
        
        if( calendar.get( Calendar.YEAR ) == 1 )
            return "";
                
        if( calendar.get( Calendar.YEAR ) == 9999 )
            return "?";
                            
        Format formater = null;
        
        if( format == null || format.length() == 0 )
            formater = new SimpleDateFormat( yyyy_MM_dd_HH_mm_ss );
        else
            formater = new SimpleDateFormat( format );
            
        return  formater.format( calendar.getTime() );
    }
    
    /**
     * 格式化时间
     * 
     * @param time time in milliseconds
     * @param format  时间格式，如"yyyy-MM-dd HH:mm:ss.SSS"，如果format等于null或""，则采用格式"yyyy-MM-dd HH:mm:ss"
     * @return String 如果time不是合法的时间字符串，则返回""
     */         
    public static String format( long time, String format )
    {       
        return  format( new Date( time ), format );
    }
        
    /**
     * 格式化时间
     * 
     * @param date <code>Date</code>
     * @param format  时间格式，如"yyyy-MM-dd HH:mm:ss.SSS"，如果format等于null或""，则采用格式"yyyy-MM-dd HH:mm:ss"
     * @return String 如果time不是合法的时间字符串，则返回""
     */         
    public static String format( Date date, String format )
    {       
        if( date == null )
            return "";  
                                
        Format formater = null;
        
        if( format == null || format.length() == 0 )
            formater = new SimpleDateFormat( yyyy_MM_dd_HH_mm_ss );
        else
            formater = new SimpleDateFormat( format );
            
        return  formater.format( date );
    }   
    
    /**
     * 格式化时间
     * 
     * @param calendar <code>Calendar</code>
     * @param format  时间格式，如"yyyy-MM-dd HH:mm:ss.SSS"，如果format等于null或""，则采用格式"yyyy-MM-dd HH:mm:ss"
     * @return String 如果time不是合法的时间字符串，则返回""
     */         
    public static String format( Calendar calendar, String format )
    {       
        if( calendar == null )
            return "";  
                                        
        return format( calendar, format );
    }
    
    /**
     * 返回时间跨度的字符串表达式
     * 
     * @param span span time in milliseconds
     */         
    public static String spanToString( long span )
    {       
        if( span < 1000L )
            return span + "ms";
        else if( span < MillisecondsOfOneMinute )
            return ( (int)( span/1000 ) ) + " seconds, " + spanToString( span%1000 );
        else if( span < MillisecondsOfOneHour )
            return ( (int)( span/MillisecondsOfOneMinute ) ) + " minutes, " + spanToString( span%MillisecondsOfOneMinute );
        else if( span < MillisecondsOfOneDay )
            return ( (int)( span/MillisecondsOfOneHour ) ) + " hours, " + spanToString( span%MillisecondsOfOneHour );
        else
            return ( (int)( span/MillisecondsOfOneDay ) ) + " days, " + spanToString( span%MillisecondsOfOneDay );            
    }
        
    /**
     * 返回first和second之间相差的天数, first晚于second返回正整数，first早于second返回负整数，first等于second返回0
     * 
     * @param first <code>Date</code>
     * @param second  <code>Date</code>
     */         
    public static int spanInDay( Date first, Date second )
    {       
        long span = first.getTime() - second.getTime();
        
        return (int)( span/MillisecondsOfOneDay );
    }
        
    /**
     * 返回first和second之间相差的天数
     * 
     * @param first 时间字符串，如  "20040619"、"2004-06-19"、"2004-06-19 13:02:56.345"
     * @param second  时间字符串，如  "20040619"、"2004-06-19"、"2004-06-19 13:02:56.345"
     */         
    public static int spanInDay( String first, String second )
    {                                               
        return spanInDay( toDate( first), toDate( second ) );
    }   
    
    /**
     * 返回first和second之间相差的小时数, first晚于second返回正整数，first早于second返回负整数，first等于second返回0
     * 
     * @param first <code>Date</code>
     * @param second  <code>Date</code>
     */         
    public static int spanInHour( Date first, Date second )
    {       
        long span = first.getTime() - second.getTime();
        
        return (int)( span/MillisecondsOfOneHour );
    }
        
    /**
     * 返回first和second之间相差的小时数
     * 
     * @param first 时间字符串，如  "20040619"、"2004-06-19"、"2004-06-19 13:02:56.345"
     * @param second  时间字符串，如  "20040619"、"2004-06-19"、"2004-06-19 13:02:56.345"
     */         
    public static int spanInHour( String first, String second )
    {                                               
        return spanInHour( toDate( first ), toDate( second ) );
    }   
    
    /**
     * 返回first和second之间相差的分种数, first晚于second返回正整数，first早于second返回负整数，first等于second返回0
     * 
     * @param first <code>Date</code>
     * @param second  <code>Date</code>
     */         
    public static int spanInMinute( Date first, Date second )
    {       
        long span = first.getTime() - second.getTime();
        
        return (int)( span/MillisecondsOfOneMinute );
    }
        
    /**
     * 返回first和second之间相差的分钟数
     * 
     * @param first 时间字符串，如  "20040619"、"2004-06-19"、"2004-06-19 13:02:56.345"
     * @param second  时间字符串，如  "20040619"、"2004-06-19"、"2004-06-19 13:02:56.345"
     */         
    public static int spanInMinute( String first, String second )
    {                                               
        return spanInMinute( toDate( first ), toDate( second ) );
    }   
    
    /**
     * 返回first和second之间相差的秒数, first晚于second返回正整数，first早于second返回负整数，first等于second返回0
     * 
     * @param first <code>Date</code>
     * @param second  <code>Date</code>
     */         
    public static long spanInSecond( Date first, Date second )
    {       
        long span = first.getTime() - second.getTime();
        
        return (long)( span/1000L );
    }
        
    /**
     * 返回first和second之间相差的秒数
     * 
     * @param first 时间字符串，如  "20040619"、"2004-06-19"、"2004-06-19 13:02:56.345"
     * @param second  时间字符串，如  "20040619"、"2004-06-19"、"2004-06-19 13:02:56.345"
     */         
    public static long spanInSecond( String first, String second )
    {                                               
        return spanInSecond( toDate( first ), toDate( second ) );
    }
    
    /**
     * 返回first和second之间相差的毫秒数, first晚于second返回正整数，first早于second返回负整数，first等于second返回0
     * 
     * @param first <code>Date</code>
     * @param second  <code>Date</code>
     */         
    public static long spanInMillisecond( Date first, Date second )
    {       
        return first.getTime() - second.getTime();
    }
        
    /**
     * 返回first和second之间相差的毫秒数
     * 
     * @param first 时间字符串，如  "20040619"、"2004-06-19"、"2004-06-19 13:02:56.345"
     * @param second  时间字符串，如  "20040619"、"2004-06-19"、"2004-06-19 13:02:56.345"
     */         
    public static long spanInMillisecond( String first, String second )
    {                                               
        return spanInMillisecond( toDate( first ), toDate( second ) );
    }
    
    /**
     * 计算与给定时间相差years年的时间
     * 
     * @param base <code>Date</code>
     * @param years  增加的年数
     */         
    public static Date addYear( Date base, int years )
    {   
        Calendar calendar = new GregorianCalendar();
        
        calendar.setTime( base );
        
        calendar.add( Calendar.YEAR, years );
        
        return calendar.getTime();
    }
        
    /**
     * 计算与给定时间相差years年的时间
     * 
     * @param base 时间字符串，如  "20040619"、"2004-06-19"、"2004-06-19 13:02:56.345"
     * @param years  增加的年数
     */         
    public static Date addYear( String base, int years )
    {                                               
        return addYear( toDate( base ), years );
    }   
    
    /**
     * 计算与给定时间相差months月的时间
     * 
     * @param base <code>Date</code>
     * @param months  增加的月数
     */         
    public static Date addMonth( Date base, int months )
    {   
        Calendar calendar = new GregorianCalendar();
        
        calendar.setTime( base );
        
        calendar.add( Calendar.MONTH, months );
        
        return calendar.getTime();
    }
        
    /**
     * 计算与给定时间相差months月的时间
     * 
     * @param base 时间字符串，如  "20040619"、"2004-06-19"、"2004-06-19 13:02:56.345"
     * @param months  增加的月数
     */         
    public static Date addMonth( String base, int months )
    {                                               
        return addMonth( toDate( base ), months );
    }
    
    
    /**
     * 计算与给定时间相差days天的时间
     * 
     * @param base <code>Date</code>
     * @param days  增加的天数
     */         
    public static Date addDay( Date base, int days )
    {   
        Calendar calendar = new GregorianCalendar();
        
        calendar.setTime( base );
        
        calendar.add( Calendar.DATE, days );
        
        return calendar.getTime();
    }
        
    /**
     * 计算与给定时间相差days天的时间
     * 
     * @param base 时间字符串，如  "20040619"、"2004-06-19"、"2004-06-19 13:02:56.345"
     * @param days  增加的天数
     */         
    public static Date addDay( String base, int days )
    {                                               
        return addDay( toDate( base ), days );
    }
    
    /**
     * 计算与给定时间相差hours小时的时间
     * 
     * @param base <code>Date</code>
     * @param hours  增加的小时数
     */         
    public static Date addHour( Date base, int hours )
    {   
        Calendar calendar = new GregorianCalendar();
        
        calendar.setTime( base );
        
        calendar.add( Calendar.HOUR, hours );
        
        return calendar.getTime();
    }
        
    /**
     * 计算与给定时间相差hours小时的时间
     * 
     * @param base 时间字符串，如  "20040619"、"2004-06-19"、"2004-06-19 13:02:56.345"
     * @param hours  增加的小时数
     */         
    public static Date addHour( String base, int hours )
    {                                               
        return addHour( toDate( base ), hours );
    }
    
    /**
     * 计算与给定时间相差minutes分钟的时间
     * 
     * @param base <code>Date</code>
     * @param minutes  增加的分钟数
     */         
    public static Date addMinute( Date base, int minutes )
    {   
        Calendar calendar = new GregorianCalendar();
        
        calendar.setTime( base );
        
        calendar.add( Calendar.MINUTE, minutes );
        
        return calendar.getTime();
    }
        
    /**
     * 计算与给定时间相差minutes分钟的时间
     * 
     * @param base 时间字符串，如  "20040619"、"2004-06-19"、"2004-06-19 13:02:56.345"
     * @param minutes  增加的分钟数
     */         
    public static Date addMinute( String base, int minutes )
    {                                               
        return addMinute( toDate( base ), minutes );
    }
        
    /**
     * 计算与给定时间相差seconds秒的时间
     * 
     * @param base <code>Date</code>
     * @param seconds  增加的秒数
     */         
    public static Date addSecond( Date base, int seconds )
    {   
        Calendar calendar = new GregorianCalendar();
        
        calendar.setTime( base );
        
        calendar.add( Calendar.SECOND, seconds );
        
        return calendar.getTime();
    }
        
    /**
     * 计算与给定时间相差seconds秒的时间
     * 
     * @param base 时间字符串，如  "20040619"、"2004-06-19"、"2004-06-19 13:02:56.345"
     * @param seconds  增加的秒数
     */         
    public static Date addSecond( String base, int seconds )
    {                                               
        return addSecond( toDate( base ), seconds );
    }   
    
    /**
     * 计算与给定时间相差milliseconds毫秒的时间
     * 
     * @param base <code>Date</code>
     * @param milliseconds  增加的毫秒数
     */         
    public static Date addMillisecond( Date base, int milliseconds )
    {   
        Calendar calendar = new GregorianCalendar();
        
        calendar.setTime( base );
        
        calendar.add( Calendar.SECOND, milliseconds );
        
        return calendar.getTime();
    }
        
    /**
     * 计算与给定时间相差milliseconds毫秒的时间
     * 
     * @param base 时间字符串，如  "20040619"、"2004-06-19"、"2004-06-19 13:02:56.345"
     * @param milliseconds  增加的毫秒数
     */         
    public static Date addMillisecond( String base, int milliseconds )
    {                                               
        return addMillisecond( toDate( base ), milliseconds );
    }
}
