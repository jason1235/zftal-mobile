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
 * ���������ַ��������ĳ��ù�����
 */
public class StringUtil {

	/**
	 * ��һ���ַ������ղ�ַ��Ų�ֳ�һ�����鲢����
	 * @param original ԭ������Ҫ����ֵ��ַ���
	 * @param regex ��ַ���,����Ҫ�õ��˷���ȥ���ԭ��original.
	 * @return ���ز�ֺ���ַ�����,������ʧ���򷵻�null
	 */
	public static String[] split(String original,String regex){
		
		return original.split(regex);
	}
	
	/**
	 * �ж��ַ����Ƿ��ǿ�ֵ,������򷵻�true����������򷵻�false
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
     * URL���<br> 
     * <br> 
     * @param pInput     Ҫ�����ַ���<br> 
     * @return boolean   ���ؼ����<br> 
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
