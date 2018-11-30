package com.zfsoft.util.base;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Java Bean 应用类 扩展Apache Commons BeanUtils, 提供一些反射方面缺失功能的封装.
 * 
 */
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {

    protected static final Log logger = LogFactory.getLog(BeanUtils.class);

    private BeanUtils()
    {
    }

  
    /**
	 * 判断字符是否为空或者为NULL 不为空并且不为NULL return 字符 为空返回空-->""
	 * 
	 * @param str
	 * @return
	 */

	public static String getNotNullStr(String str) {
		if (!(isEmpty(str))) {
			return str;
		} else {
			return "";
		}
	}

    /**
     * 获取声明属性
     * 
     */
    public static String[] getDeclaredPropertys(Class clazz)
    {
        String[] props = null;
        try
        {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            Vector<String> propVector = new Vector<String>();
            for (PropertyDescriptor propDes : propertyDescriptors)
            {
                // 如果是class属性 跳过
                if (propDes.getName().equals("class") || propDes.getReadMethod() == null
                        || propDes.getWriteMethod() == null)
                    continue;
                propVector.add(propDes.getName());
            }
            props = new String[propVector.size()];
            propVector.toArray(props);
        }
        catch (IntrospectionException e)
        {
            logger.error(e.getMessage(), e);
        }
        return props;
    }

    /**
     * 检测类是否包含属性
     */
    public static boolean hasProperty(Class clazz, String propertyName)
    {
        boolean has = false;
        try
        {
            new PropertyDescriptor(propertyName, clazz);
            has = true;
        }
        catch (IntrospectionException e)
        {
            logger.info(clazz.getName()+":"+e.getMessage());
            return false;
        }
        return has;
    }

    public static PropertyDescriptor[] getPropertyDescriptors(Class clazz)
    {
        BeanInfo beanInfo = getBeanInfo(clazz);
        return beanInfo.getPropertyDescriptors();
    }

    /**
     * 根据bean的类取得bean的信息
     * 
     * @param clazz
     * @return
     */
    public static BeanInfo getBeanInfo(Class clazz)
    {
        BeanInfo beanInfo = null;
        try
        {
            beanInfo = Introspector.getBeanInfo(clazz);
        }
        catch (IntrospectionException e)
        {
            logger.error(e.getMessage(), e);
        }
        return beanInfo;
    }

    /**
     * 
     * 判断字符串为空或者为NULL return true 
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
    	return ((str == null) || (str.length() == 0));
    }
    /**
     * 
     * 判断对象为空或者为NULL return true 
     * @param str
     * @return
     */
    public static boolean isEmpty(Object obj){
    	return ((obj == null));
    }
    /**
     * 判断字符串是否为空或者为NULL 
     * 不为空并且不为NULL return true 
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str){
    	 return (!(isEmpty(str)));
    }
    /**
     * 判断对象是否为空或者为NULL 
     * 不为空并且不为NULL return true 
     * @param str
     * @return
     */
    public static boolean isNotEmpty(Object obj){
    	 return (!(isEmpty(obj)));
    }
    /**
     * 字符串的比较
     * @param str1,str2
     * @return
     */
    public static boolean equals(String str1, String str2){
    	if(str1 == null && str2 != null){return false;}
    	if(str2 == null && str1 != null){return false;}
    	if(str1 == null && str2 == null){return false;}
    	return str1.equals(str2);
    }
    /**
     * 比较2个字符串，忽略大小写
     * 相等 return true 
     * @param str1, str2
     * @return
     */
    public static boolean equalsIgnoreCase(String str1, String str2){
    	if(str1 == null && str2 != null){return false;}
    	if(str2 == null && str1 != null){return false;}
    	if(str1 == null && str2 == null){return false;}
    	return org.apache.commons.lang.StringUtils.equalsIgnoreCase(str1, str2);
    }
    
    /**
     * 页面URL传参时候用到encodeURI转码时后台处理
     * @param str
     * @return
     */
    public static String encodeStr(String str) throws UnsupportedEncodingException{
    	if(isNotEmpty(str)){
    		return java.net.URLDecoder.decode(str, "UTF-8");
    	}else{
    		return "";
    	}
    }
    

}
