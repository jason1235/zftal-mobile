package com.zfsoft.orcus.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 序列号生成器
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-7-10
 * @version V1.0.0
 */
public class Sequencer {
	private final static SimpleDateFormat TIMEFORMATTER = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.SSS" );
	private final static DecimalFormat    NUMFOMMATTER  = new DecimalFormat( "00000" );
	
	private final static SimpleDateFormat TIMEFORMATTER1 = new SimpleDateFormat( "yyyy-MM-dd HH_mm_ss_SSS" );
	
	private static int COUNTER1 = 0;
	private static int COUNTER2 = 0;
	private static int COUNTER3 = 0;
	private static int COUNTER4 = 0;
	
	private static Object LOCK1 = new Object();
	private static Object LOCK2 = new Object();
	private static Object LOCK3 = new Object();
	private static Object LOCK4 = new Object();
	
	/**
	 * 返回基于时间的字符串型序列号，例："2004-01-02 16:43:35.125 00000"
	 */
	public static String timeSequence() {
		synchronized( LOCK1 ) {
			return TIMEFORMATTER.format( new Date() ) + " "
					+ NUMFOMMATTER.format( ( ( COUNTER1++ ) % 100000 ) );
		}
	}

	/**
	 * 返回基于时间的字符串型序列号，例："2004-01-02 16_43_35_125 00000"
	 */
	public static String timeSequenceEx() {
		synchronized( LOCK4 ) {
			return TIMEFORMATTER1.format( new Date() ) + " "
					+ NUMFOMMATTER.format( ( ( COUNTER4++ ) % 100000 ) );
		}
	}

	/**
	 * 返回基于时间和指定后缀的字符串型序列号，例："2004-01-02 16:44:19.438 00000 suffix"
	 * 
	 * @param suffix 序列号后缀
	 */
	public static String timeSequence( String suffix ) {
		synchronized( LOCK2 ) {
			return TIMEFORMATTER.format( new Date() ) + " "
					+ NUMFOMMATTER.format( ( ( COUNTER2++ ) % 100000 ) ) + " "
					+ suffix;
		}
	}

	/**
	 * 返回基于时间长整型序列号，例：1125156745265545216
	 */
	public static long numberSequence() {
		synchronized( LOCK3 ) {
				return ( System.currentTimeMillis() << 20 ) + (long) ( ( COUNTER3++ ) % 100000 );
		}
	}
}
