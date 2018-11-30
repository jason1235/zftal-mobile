package com.zfsoft.util.collection;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * 数组类和列表类的工具类，提供非空判断，类型转换等功能。
 * 
 * @author zhangqy
 * @version v 1.0.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CollectionUtil {

	/**
	 * 将字符串数组转换成BigDecimal数组。
	 * 
	 * @param strs
	 *            字符数组
	 * @return BigDecimal[]
	 */
	public static BigDecimal[] String2BigDecimals(String strs[]) {
		if (isEmpty(strs)) {
			return null;
		}

		BigDecimal bd[] = new BigDecimal[strs.length];
		for (int i = 0; i < strs.length; i++) {
			if (strs[i] != null)
				bd[i] = new BigDecimal(strs[i]);
		}
		return bd;

	}
	/**
	 * 去处List中所有重复的值
	 * @param list
	 * @return 去重后的list
	 */
	public static List removeDupValue(List list) {
		Set set = new HashSet();
		set.addAll(list);
		List retList = new ArrayList();
		retList.addAll(set);

		return retList;
	}
	/**
	 * 从Map中批量去处值
	 * @param Map数据集合
	 * @param 需要去处的数据的KeyList
	 */
	public static void removeKeyList(Map map, List keyList) {
		if (isEmpty(map) || isEmpty(keyList)) {
			return;
		}

		for (Iterator it = keyList.iterator(); it.hasNext();) {
			map.remove(it.next());
		}
	}

	/**
	 * 将一个大的List分割成小的List。
	 * 
	 * @param list
	 *            要分割的List
	 * @param size
	 *            每个小List的大小
	 * @return 元素为小List的List
	 */
	public static List splitList(List list, int size) {
		List retList = new ArrayList();
		if (isEmpty(list)) {
			return retList;
		}

		int start = 0;
		int end = 0;
		int length = list.size();
		for (int i = 0; i <= length / size; i++) {
			start = i * size;
			end = (i + 1) * size;
			if (end > length) {
				end = length;
			}

			if (end > start) {
				List sublist = list.subList(start, end);
				retList.add(sublist);
			}
		}

		return retList;
	}

	/**
	 * 将String类型的List数据 转换为String[]型数组。
	 * 
	 * @param List strings
	 * @return String[]
	 */
	public static String[] stringListToStringArr(List strings) {
		if (strings == null) {
			return new String[0];
		}
		String[] target = new String[strings.size()];
		for (int i = 0; i < strings.size(); i++) {
			target[i] = new String(strings.get(i).toString());
		}
		return target;
	}
	
	/**
	 * 将String类型的List数据 转换为String。
	 * 
	 * @param List strings
	 * @return String
	 */
	public static String stringListToStr(List strings) {
		if (strings == null) {
			return "";
		}
		
		StringBuffer sb = new StringBuffer(); 
		for (int i = 0; i < strings.size(); i++) {
			sb.append(strings.get(i));
			if(i<strings.size()-1)
				sb.append("");
		}
		return sb.toString();
	}

	/**
	 * 将String数组 转换为Long[]型数组。
	 * 
	 * @param longList
	 *            包含Long型原素的List
	 * @return Long[]
	 */
	public static Long[] stringArrToLongArr(String[] strings) {
		if (strings == null) {
			return new Long[0];
		}
		Long[] target = new Long[strings.length];
		for (int i = 0; i < strings.length; i++) {
			target[i] = new Long(strings[i]);
		}
		return target;
	}

	/**
	 * 将Long类型List转换为Long[]型数组。
	 * 
	 * @param longList
	 *            包含Long型原素的List
	 * @return Long[]
	 */
	public static Long[] longListToLongArr(List longList) {
		if (longList == null) {
			return new Long[0];
		}
		Long[] target = new Long[longList.size()];
		for (int i = 0; i < longList.size(); i++) {
			target[i] = (Long) longList.get(i);
		}
		return target;
	}

	/**
	 * 把一个list of string变成string,使用<code>splitStr</code>分开
	 * 
	 * @param List
	 *            要转换的List。
	 * @param splitStr
	 *            连接字符串
	 * @return String 以回车符\n分开后的String
	 */
	public static String listToString(List lst, String splitStr) {
		String str = "";
		if (isEmpty(lst)) {
			return str;
		}

		for (int i = 0; i < lst.size(); i++) {
			str = str.concat(lst.get(i).toString()).concat(splitStr);
		}

		return str;
	}

	/**
	 * 将List转换为BigDecimal[]型数组。
	 * 
	 * @param bigDecimalList
	 *            包含BigDecimal型原素的List
	 * @return BigDecimal[]
	 */
	public static BigDecimal[] stringListToBigDecimalArr(List bigDecimalList) {
		if (bigDecimalList == null) {
			return new BigDecimal[0];
		}
		BigDecimal[] target = new BigDecimal[bigDecimalList.size()];
		for (int i = 0; i < bigDecimalList.size(); i++) {
			target[i] = new BigDecimal(bigDecimalList.get(i).toString());
		}
		return target;
	}

	/**
	 * 判断List是否为空，包括<code>null</code>和没有一个元素的判断。
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(List list) {
		if (list == null || list.isEmpty()) {
			return true;
		}

		return false;
	}

	/**
	 * 判断String[]是否为空，包括<code>null</code>和没有一个元素的判断。
	 * 
	 * @param strArray
	 * @return
	 */
	public static boolean isEmpty(String[] strArray) {
		if (strArray == null || strArray.length <= 0) {
			return true;
		}

		return false;
	}

	/**
	 * 判断Long[]是否为空，包括<code>null</code>和没有一个元素的判断。
	 * 
	 * @param longArr
	 * @return
	 */
	public static boolean isEmpty(Long[] longArr) {
		if (longArr == null || longArr.length <= 0) {
			return true;
		}

		return false;
	}

	/**
	 * 判断Map是否为空，包括<code>null</code>和没有一个元素的判断。
	 * 
	 * @param map
	 * @return
	 */
	public static boolean isEmpty(Map map) {
		if (map == null || map.isEmpty()) {
			return true;
		}

		return false;
	}

	/**
	 * 判断Set是否为空，包括<code>null</code>和没有一个元素的判断。
	 * 
	 * @param map
	 * @return
	 */
	public static boolean isEmpty(Set set) {
		if (set == null || set.isEmpty()) {
			return true;
		}

		return false;
	}

	/**
	 * 将数组转换成List。
	 * 
	 * @param objs
	 * @return
	 */
	public static List arrayToList(Object[] objs) {
		List ret = new ArrayList();
		if (objs == null || objs.length <= 0) {
			return ret;
		}

		for (int i = 0; i < objs.length; i++) {
			ret.add(objs[i]);
		}
		return ret;
	}

	public static List stringArrayToLongList(String[] objs) {
		List ret = new ArrayList();
		if (objs == null || objs.length <= 0) {
			return ret;
		}

		for (int i = 0; i < objs.length; i++) {
			Long longobj = new Long(objs[i]);
			ret.add(longobj);
		}
		return ret;
	}

	public static List defaultIfNull(List list) {
		if (list == null) {
			return new ArrayList();
		}

		return list;
	}

	/**
	 * 把一个<code>elmLst</code>从<code>orgLst</code>中除去
	 * 
	 * @param elmLst
	 *            the List 要被除去的部分
	 * @param orgLst
	 *            the List 原始list
	 * @return 剩余的list
	 */
	public static List removeList(List elmLst, List orgLst) {
		List rst = new ArrayList();

		if (orgLst != null && elmLst != null && elmLst.size() > 0) {
			for (int i = 0; i < orgLst.size(); i++) {
				if (!elmLst.contains(orgLst.get(i))) {
					rst.add(orgLst.get(i));
				}
			}
			return rst;
		} else if (elmLst != null && elmLst.size() == 0) {
			return orgLst;
		}
		return rst;
	}

	/**
	 * 将Set转换为List
	 * 
	 * @param set
	 * @return
	 */
	public static List setToList(Set set) {
		List list = new ArrayList();
		list.addAll(set);
		return list;
	}
	/**
	 * 将Object[]中的数据转换为List
	 * @param Object[] objArray
	 * @return List
	 */
	public static List Array2List(Object[] objArray) {
		List vList = new ArrayList();

		for (int i = 0; i < objArray.length; i++) {
			Object obj = objArray[i];
			vList.add(obj);
		}

		return vList;
	}
	/**
	 * 将Object[]中的数据转换为用<code>,</code>分开的字符串
	 * @param objArray
	 * @return String
	 */
	public static String Array2String(Object[] objArray) {
		if ((objArray == null) || (objArray.length == 0)) {
			return "";
		}

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < objArray.length; i++) {
			String value = (String) objArray[i];
			sb.append(value).append(',');
		}

		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}

		return sb.toString();
	}
	/**
	 * 将Object[]中的数据转换为用<code>,</code>分开的字符串
	 * @param objArray
	 * @return String
	 */
	public static String LongArray2String(Object[] objArray) {
		if ((objArray == null) || (objArray.length == 0)) {
			return "";
		}

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < objArray.length; i++) {
			Long value = (Long) objArray[i];
			sb.append(value.toString()).append(',');
		}

		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}

		return sb.toString();
	}
	/**
	 * 将collection中的ids数据转换为用<code>,</code>分开的字符串
	 * @param ids
	 * @return String 
	 */
	public static String List2String(Collection ids) {
		if ((ids == null) || ids.isEmpty()) {
			return "";
		}

		StringBuffer sb = new StringBuffer();

		for (Iterator it = ids.iterator(); it.hasNext();) {
			Object obj = it.next();

			if (obj != null) {
				sb.append(obj).append(',');
			}
		}

		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}

		return sb.toString();
	}

	/**
	 * 返回列表，传入的参数以\n分割
	 * 
	 * @param str
	 *            the input String
	 * @return list of String
	 */
	public static List stringToList(String str) {
		List vList = new ArrayList();

		if (str != null) {
			String[] strArr = str.split("\n");

			for (int i = 0; i < strArr.length; i++) {
				vList.add(StringUtils.trim(strArr[i]));
			}
		}

		return vList;
	}
	
	/**
	 * 返回列表，传入的参数定义分割符
	 * 
	 * @param str
	 *            the input String
	 * @return list of String
	 */
	public static List stringToList(String str, String token) {
		List vList = new ArrayList();

		if (str != null) {
			String[] strArr = str.split(token);

			for (int i = 0; i < strArr.length; i++) {
				vList.add(StringUtils.trim(strArr[i]));
			}
		}

		return vList;
	}

	/**
	 * 输入参数的格式为： 10 鲜花,1.73323 礼品,1.43434 送礼,1.32300 收礼,1.32300 只收脑白金,0.1343252
	 * 
	 * @param text
	 *            the input String
	 * @return 列表，内容为： 鲜花 礼品 送礼 收礼 只收脑白金
	 */
	public static List stringToList(String text, String firstToken,
			String twoToken) {
		List vList = new ArrayList();

		if (text != null) {
			String[] firstStringArray = text.split(firstToken);

			for (int i = 0; i < firstStringArray.length; i++) {
				String[] twoStringArray = firstStringArray[i].split(twoToken);
				vList.add(StringUtils.trim(twoStringArray[0]));
			}
		}

		return vList;
	}

}
