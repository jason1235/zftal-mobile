package com.zfsoft.util.base;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class StringUtil extends StringUtils{
	private static final int[] allChineseScope = { 1601, 1637, 1833, 2078,
			2274, 2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730,
			3858, 4027, 4086, 4390, 4558, 4684, 4925, 5249, 5600,
			Integer.MAX_VALUE };
	public static final char unknowChar = '*';
	private static final char[] allEnglishLetter = { 'A', 'B', 'C', 'D', 'E',
			'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'W', 'X', 'Y', 'Z', unknowChar };

	private StringUtil() {

	}
	
	
	public static String collapseString(String target, int size, String insert){  
	      
	    if(StringUtils.isEmpty(target)) return target; //目标字符串为空，返回目标字符串  
	      
	    int target_length = target.length();  
	      
	    if(target_length <= size) return target; //目标字符串长度等于间隔长度 ， 返回目标字符串  
	      
	    insert = StringUtils.isEmpty(insert) ? "<br/>" : insert;  
	      
	    //插入次数  
	    int times = 0;  
	    if(target_length % size == 0){  
	        times = target_length / size - 1;  
	    }else{  
	        times = target_length / size;  
	    }  
	      
	    //结果字符集  
	    char [] result_chars = new char[target_length + times];  
	      
	    //目标字符集  
	    char [] target_chars = new char[target_length];  
	      
	    //将字符串数据装入目标字符集  
	    target.getChars(0, target_length, target_chars, 0);  
	      
	    //遍历目标字符集，将值插入到结果字符集  
	    int j = 0;  
	    for(int i = 0; i < target_chars.length; i++){  
	          
	        //间隔处插入值  
	        if(i > 0 && i % size == 0){  
	            result_chars[j] = '`';  
	            j = j + 1;  
	        }  
	          
	        result_chars[j] = target_chars[i];  
	        j = j + 1;  
	    }  
	      
	    String resultStr = new String(result_chars);  
	    return resultStr.replaceAll("`", insert);  
	}  

	/**
	 * 字串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null) {
			return true;
		} else if (str.length() == 0) {
			return true;
		} else if ("NULL".equals(str.toUpperCase())) {
			return true;
		}
		return false;
	}

	/**
	 * 生成查询字串Map
	 * 
	 * @param str
	 * @return
	 */
	public static Map<String, String> getMapFromQueryParamString(String str) {
		Map<String, String> param = new HashMap<String, String>();
		String keyValues[] = str.split("`");
		for (int i = 0; i < keyValues.length; i++) {

		}
		return param;
	}

	/**
	 * 全替换
	 * 
	 * @param src
	 *            替换字串
	 * @param tar
	 *            替换目标
	 * @param str
	 *            主字串
	 * @return
	 */
	public static String replaceAll(String src, String tar, String str) {
		StringBuilder sb = new StringBuilder();
		byte bytesSrc[] = src.getBytes();

		byte bytes[] = str.getBytes();
		int point = 0;
		for (int i = 0; i < bytes.length; i++) {

			if (isStartWith(bytes, i, bytesSrc, 0)) {

				sb.append(new String(bytes, point, i));
				sb.append(tar);
				i += bytesSrc.length;
				point = i;
			}

		}
		sb.append(new String(bytes, point, bytes.length));
		return sb.toString();
	}

	/**
	 * 
	 * @param bytesSrc
	 * @param bytesTar
	 * @return
	 */
	private static boolean isStartWith(byte bytesSrc[], int startSrc,
			byte bytesTar[], int startTar) {
		StringBuilder sb = new StringBuilder();
		for (int j = startTar; j < bytesTar.length; j++) {
			if (bytesSrc[startSrc + j] != bytesTar[j]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 取中文拼音首字符
	 * 
	 * @param str
	 * @return
	 */
	public static char getFirstLetterFromChinessWord(String str) {
		char result = '*';
		String temp = str.toUpperCase();
		try {
			byte[] bytes = temp.getBytes("gbk");
			if (bytes[0] < 128 && bytes[0] > 0) {
				return (char) bytes[0];
			}

			int gbkIndex = 0;

			for (int i = 0; i < bytes.length; i++) {
				bytes[i] -= 160;
			}
			gbkIndex = bytes[0] * 100 + bytes[1];
			for (int i = 0; i < allEnglishLetter.length; i++) {
				if (i == 22) {
					// System.out.println(allEnglishLetter.length
					// +" "+allChineseScope.length);
				}
				if (gbkIndex >= allChineseScope[i]
						&& gbkIndex < allChineseScope[i + 1]) {
					result = allEnglishLetter[i];
					break;
				}
			}

		} catch (Exception e) {

		}
		return result;
	}

	/**
	 * 字串分割
	 * 
	 * @param src
	 * @param letter
	 * @return
	 */
	public static String[] split(String src, char letter) {
		if (src == null) {
			return new String[0];
		}
		List<String> ret = new ArrayList();
		byte bytes[] = src.getBytes();
		int curPoint = 0;
		for (int i = 0; i < bytes.length; i++) {
			if (bytes[i] == letter) {
				String s = new String(bytes, curPoint, i - curPoint);
				ret.add(s);
				curPoint=i+1;
			}
		}
		if (ret.size() == 0) {
			return new String[] { src };
		}
		//ret.add(new String(bytes, curPoint, src.length() - curPoint));
		String[] retStr = new String[ret.size()];
		for (int i = 0; i < ret.size(); i++) {
			retStr[i] = ret.get(i);
		}
		return retStr;
	}
	
	public static String[] split(String src ,String letter){
		String retStr[] = new String[0];
		try{
			 retStr = StringUtils.split(src, letter);
			
		}catch(Exception e){
			return retStr;
		}
		return retStr;
	}
	
	/**
	 * 去除最后一个字符
	 * @param str
	 * @return
	 */
	public static String removeLast(String str){
		if(str == null){
			return null;
		}
		return str.substring(0,str.length()-1);
		
	} 
	
	
	
	public static void main(String argv[]) throws UnsupportedEncodingException {
		
		//System.out.println(split("aa&dd_dd&dd","&"));
		String[] split = StringUtils.split("aa&dd_dd&dd", "323");
		//for(String a:split){
			System.out.println(split[0]);
		//}

		
		//String bmdm_id = "102120";String bmdm_bjdm_id = "102120_1";
		//System.out.println(bmdm_id.matches(bmdm_bjdm_id));
		
		// System.out.println(StringUtil.getFirstLetterFromChinessWord("裘"));
		// int b = 0;
		// for (int i = 0; i < 50; i++) {
		// for (int j = 0; j < 50; j++) {
		// byte bytes[] = new byte[] { (byte) (56 + 160 + i),
		// (byte) (00 + 160 + j) };
		// String aa = new String(bytes, "gbk");
		// if (!isEmpty(aa)) {
		// b++;
		// System.out.println(aa + " " + i + " " + j + " "
		// + (i * 50 + j));
		// }
		// }
		// }
		// bytes="竺".getBytes("gbk");
		// System.out.println(new String(bytes,"gbk"));
		// System.out.println(StringUtil.getFirstLetterFromChinessWord("竺"));
		//String[] ret = split("S||||a&r", 's');
		//System.out.println(ret.length);
//		for (int i = 0; i < ret.length; i++) {
//			System.out.println(ret[i]);
//		}
		
//		String filterField = "bmdm_id,zydm_id,njdm_id";
//		List<String> list = new ArrayList<String>();
//		list.add("bmdm_id=100001,njdm_id=1");
//		list.add("bmdm_id=102120,njdm_id=1");
//		list.add("bmdm_id=2");
//		list.add("bmdm_id=3,njdm_id=1");
//		list.add("zydm_id=1,zydm_id=1");
//		list.add("zydm_id=2,zydm_id=1");
//		list.add("zydm_id=3");
//		
//		
//		StringBuilder builder = new StringBuilder();
//		String f1 = "", f2 = "", reSql = "";
//		String filterFields[] = StringUtil.split(filterField, ',');
//		for (int i = 0; i < filterFields.length; i++) {
//			for (int j = i + 1; j < filterFields.length; j++) {
//				f1 = filterFields[i];
//				f2 = filterFields[j];
//				for (String temp : list) {
//					if (temp.contains(",")) {
//						temp = temp.replace(",", " and ");
//						if (temp.contains(f1) && temp.contains(f2)) {
// 
//								builder.append("(" + temp + ")" + " or ") ;
//							 
//						}
//
//					}
//				}
//			}
//			for (String temp : list) {
//				if(!temp.contains(",")){
//					if(temp.contains(filterFields[i])){
//	 
//							builder.append(temp + " or ");
//						 					
//					}
//				}
//			}
//		}
//		
//		reSql = builder.toString();
//		reSql = reSql.substring(0,reSql.lastIndexOf(" or "));
//		System.out.println(reSql.substring(0,reSql.lastIndexOf(" or ")));

	}
	
	  /**
     * 将字符串首字母小写
     *
     * @param s 字符串
     * @return 首字母小写后的新字符串
     */
    public static String lowerFirst(CharSequence s) {
        int len = s.length();
        if (len == 0) return "";
        char c = s.charAt(0);
        if (Character.isLowerCase(c)) return s.toString();
        return new StringBuilder(len).append(Character.toLowerCase(c))
                .append(s.subSequence(1, len)).toString();
    }

    /**
     * 将字符串首字母大写
     *
     * @param s
     * @return
     */
    public static String upperFirst(CharSequence s) {
        int len = s.length();
        if (len == 0) return "";
        char c = s.charAt(0);
        if (Character.isUpperCase(c)) return s.toString();
        return new StringBuilder(len).append(Character.toUpperCase(c))
                .append(s.subSequence(1, len)).toString();
    }
    
    /**
     * 检查两个字符串的忽略大小写后是否相等.
     *
     * @param s1 字符串A
     * @param s2 字符串B
     * @return true 如果两个字符串忽略大小写后相等,且两个字符串均不为null
     */
    public static boolean equalsIgnoreCase(String s1, String s2) {
        return s1 == null ? s2 == null : s1.equalsIgnoreCase(s2);
    }

    /**
     * 检查两个字符串是否相等.
     *
     * @param s1 字符串A
     * @param s2 字符串B
     * @return true 如果两个字符串相等,且两个字符串均不为null
     */
    public static boolean equals(CharSequence s1, CharSequence s2) {
        return s1 == null ? s2 == null : s1.equals(s2);
    }

    /**
     * 判断字符串是否以特殊字符开头
     *
     * @param s 字符串
     * @param c 特殊字符
     * @return 是否以特殊字符开头
     */
    public static boolean startsWithChar(CharSequence s, char c) {
        return null != s ? (s.length() == 0 ? false : s.charAt(0) == c) : false;
    }

    /**
     * 判断字符串是否以特殊字符结尾
     *
     * @param s 字符串
     * @param c 特殊字符
     * @return 是否以特殊字符结尾
     */
    public static boolean endsWithChar(CharSequence s, char c) {
        return null != s ? (s.length() == 0 ? false : s.charAt(s.length() - 1) == c) : false;
    }

    public static boolean isEmpty(CharSequence... css) {
        for (CharSequence cs : css) {
            if (isEmpty(cs)) return true;
        }
        return false;
    }

    /**
     * @param cs 字符串
     * @return 是不是为空字符串
     */
    public static boolean isEmpty(CharSequence cs) {
        return null == cs || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return null != cs && cs.length() > 0;
    }

    public static boolean isNotEmpty(CharSequence... css) {
        for (CharSequence cs : css) {
            if (isEmpty(cs)) return false;
        }
        return true;
    }

    /**
     * @param cs 字符串
     * @return 是不是为空白字符串
     */
    public static boolean isBlank(CharSequence cs) {
        int L;
        if (cs == null || (L = cs.length()) == 0)
            return true;
        for (int i = 0; i < L; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 去掉字符串前后空白
     *
     * @param cs 字符串
     * @return 新字符串
     */
    public static String trim(CharSequence cs) {
        if (null == cs)
            return null;
        if (cs instanceof String)
            return ((String) cs).trim();
        int length = cs.length();
        if (length == 0)
            return cs.toString();
        int l = 0;
        int last = length - 1;
        int r = last;
        for (; l < length; l++) {
            if (!Character.isWhitespace(cs.charAt(l)))
                break;
        }
        for (; r > l; r--) {
            if (!Character.isWhitespace(cs.charAt(r)))
                break;
        }
        if (l > r)
            return "";
        else if (l == 0 && r == last)
            return cs.toString();
        return cs.subSequence(l, r + 1).toString();
    }

    public static String valueOf(Object o, String defaultVal) {
        return o == null ? defaultVal : o.toString();
    }

    /**
     * 将String中的所有pattern匹配的字串替换掉
     *
     * @param sequence    代替换的字符串
     * @param regex       替换查找的正则表达式对象
     * @param replacement 替换函数
     * @return 替换后的新字符串
     */
    public static String replace(CharSequence sequence, String regex, IReplaceCallback replacement) {
        if (sequence == null) return null;
        Matcher m = Pattern.compile(regex).matcher(sequence.toString());
        if (m.find()) {
            StringBuffer sb = new StringBuffer();
            int index = 0;
            while (true) {
                m.appendReplacement(sb, Matcher.quoteReplacement(replacement.replace(m.group(0), index++, m)));
                if (m.find()) continue;
                else break;
            }
            m.appendTail(sb);
            return sb.toString();
        }
        return sequence.toString();
    }

    /**
     * 将String中的regex第一次匹配的字串替换掉
     *
     * @param string      代替换的字符串
     * @param regex       替换查找的正则表达式
     * @param replacement 替换函数
     * @return 替换后的新字符串
     */
    public static String replaceFirst(String string, String regex, IReplaceCallback replacement) {
        if (string == null) return null;
        Matcher m = Pattern.compile(regex).matcher(string);
        StringBuffer sb = new StringBuffer();
        if (m.find()) {
            m.appendReplacement(sb, Matcher.quoteReplacement(replacement.replace(m.group(0), 0, m)));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 字符串替换的回调接口
     */
    public interface IReplaceCallback {
        /**
         * 将text转化为特定的字串返回
         *
         * @param text    指定的字符串
         * @param index   替换的次序
         * @param matcher Matcher对象
         * @return
         */
        public String replace(String text, int index, Matcher matcher);
    }

    /**
     * 抽象的字符串替换接口
     * <p>主要是添加了$(group)方法来替代matcher.group(group)
     */
    public static abstract class ReplaceCallback implements IReplaceCallback {
        protected Matcher matcher;

        public abstract String replace(String text, int index);

        /**
         * 将text转化为特定的字串返回
         *
         * @param text    指定的字符串
         * @param index   替换的次序
         * @param matcher Matcher对象
         * @return 替换后的新字符串
         */
        public final String replace(String text, int index, Matcher matcher) {
            this.matcher = matcher;
            try {
                return replace(text, index);
            } finally {
                this.matcher = null;
            }
        }

        /**
         * 获得matcher中的组数据
         * <p>等同于matcher.group(group)
         * <p> 该函数只能在{@link #replace(String, int)} 中调用
         *
         * @param group 组
         * @return 组数据
         */
        protected final String $(int group) {
            String data = matcher.group(group);
            return data == null ? "" : data;
        }
    }
}