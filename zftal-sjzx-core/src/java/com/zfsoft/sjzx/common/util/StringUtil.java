/**
 * 
 */
package com.zfsoft.sjzx.common.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author cuitongxin
 * 用来处理字符串操作的常用工具类
 */
public class StringUtil {

	/**
	 * 把一个字符串按照拆分符号拆分成一个数组并返回
	 * @param original 原串即需要被拆分的字符串
	 * @param regex 拆分符号,即需要用到此符号去拆分原串original.
	 * @return 返回拆分后的字符数组,如果拆分失败则返回null
	 */
	public static String[] split(String original,String regex){
		
		return original.split(regex);
	}
	
	/**
	 * 判断字符串是否是空值,如果是则返回true，如果不是则返回false
	 * @param original
	 * @return
	 */
	public static boolean isBlank(String original) {

		if (original == null || "".equals(original)) {
			return true;
		}
		return false;

	}
	/** 
     * URL检查<br> 
     * <br> 
     * @param pInput     要检查的字符串<br> 
     * @return boolean   返回检查结果<br> 
     * ^(https|http|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$
     */ 
	public static boolean isUrl (String pInput) { 
        if(pInput == null){ 
            return false; 
        } 
        String regEx = "^(http|https|ftp)//://([a-zA-Z0-9//.//-]+(//:[a-zA-" 
            + "Z0-9//.&%//$//-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{" 
            + "2}|[1-9]{1}[0-9]{1}|[1-9])//.(25[0-5]|2[0-4][0-9]|[0-1]{1}" 
            + "[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)//.(25[0-5]|2[0-4][0-9]|" 
            + "[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)//.(25[0-5]|2[0-" 
            + "4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0" 
            + "-9//-]+//.)*[a-zA-Z0-9//-]+//.[a-zA-Z]{2,4})(//:[0-9]+)?(/" 
            + "[^/][a-zA-Z0-9//.//,//?//'///////+&%//$//=~_//-@]*)*$"; 
        Pattern p = Pattern.compile(regEx); 
        Matcher matcher = p.matcher(pInput); 
        return matcher.matches(); 
    }
	
	 public static String join(Object[] array)
	  {
	    return join(array, null);
	  }

	  public static String join(Object[] array, char separator)
	  {
	    if (array == null) {
	      return null;
	    }

	    return join(array, separator, 0, array.length);
	  }

	  public static String join(Object[] array, char separator, int startIndex, int endIndex)
	  {
	    if (array == null) {
	      return null;
	    }
	    int bufSize = endIndex - startIndex;
	    if (bufSize <= 0) {
	      return "";
	    }

	    bufSize *= ((array[startIndex] == null ? 16 : array[startIndex].toString().length()) + 1);
	    StringBuilder buf = new StringBuilder(bufSize);

	    for (int i = startIndex; i < endIndex; i++) {
	      if (i > startIndex) {
	        buf.append(separator);
	      }
	      if (array[i] != null) {
	        buf.append(array[i]);
	      }
	    }
	    return buf.toString();
	  }

	  public static String join(Object[] array, String separator)
	  {
	    if (array == null) {
	      return null;
	    }
	    return join(array, separator, 0, array.length);
	  }

	  public static String join(Object[] array, String separator, int startIndex, int endIndex)
	  {
	    if (array == null) {
	      return null;
	    }
	    if (separator == null) {
	      separator = "";
	    }

	    int bufSize = endIndex - startIndex;
	    if (bufSize <= 0) {
	      return "";
	    }

	    bufSize *= ((array[startIndex] == null ? 16 : array[startIndex].toString().length()) + separator.length());

	    StringBuilder buf = new StringBuilder(bufSize);

	    for (int i = startIndex; i < endIndex; i++) {
	      if (i > startIndex) {
	        buf.append(separator);
	      }
	      if (array[i] != null) {
	        buf.append(array[i]);
	      }
	    }
	    return buf.toString();
	  }
	  public static String toString(Object obj)
	  {
	    return obj == null ? "" : obj.toString();
	  }
	  public static String join(Iterator iterator, char separator)
	  {
	    if (iterator == null) {
	      return null;
	    }
	    if (!iterator.hasNext()) {
	      return "";
	    }
	    Object first = iterator.next();
	    if (!iterator.hasNext()) {
	      return toString(first);
	    }

	    StringBuilder buf = new StringBuilder(256);
	    if (first != null) {
	      buf.append(first);
	    }

	    while (iterator.hasNext()) {
	      buf.append(separator);
	      Object obj = iterator.next();
	      if (obj != null) {
	        buf.append(obj);
	      }
	    }

	    return buf.toString();
	  }

	  public static String join(Iterator iterator, String separator)
	  {
	    if (iterator == null) {
	      return null;
	    }
	    if (!iterator.hasNext()) {
	      return "";
	    }
	    Object first = iterator.next();
	    if (!iterator.hasNext()) {
	      return toString(first);
	    }

	    StringBuilder buf = new StringBuilder(256);
	    if (first != null) {
	      buf.append(first);
	    }

	    while (iterator.hasNext()) {
	      if (separator != null) {
	        buf.append(separator);
	      }
	      Object obj = iterator.next();
	      if (obj != null) {
	        buf.append(obj);
	      }
	    }
	    return buf.toString();
	  }

	  public static String join(Collection collection, char separator)
	  {
	    if (collection == null) {
	      return null;
	    }
	    return join(collection.iterator(), separator);
	  }

	  public static String join(Collection collection, String separator)
	  {
	    if (collection == null) {
	      return null;
	    }
	    return join(collection.iterator(), separator);
	  }
	

	  public static String substringBeforeLast(String str, String separator)
	  {
	    if (str==null || separator==null) {
	      return str;
	    }
	    int pos = str.lastIndexOf(separator);
	    if (pos == -1) {
	      return str;
	    }
	    return str.substring(0, pos);
	  }
}
