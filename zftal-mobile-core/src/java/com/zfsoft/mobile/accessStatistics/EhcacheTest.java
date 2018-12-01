/**
 * 
 */
package com.zfsoft.mobile.accessStatistics;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @author zhangxu
 * @description 
 * @date 2017-6-13 下午04:40:33
 */
public class EhcacheTest {
	 public static void main(String[] args) {
         Resource res = new ClassPathResource("classpath*:/conf/spring/common.xml");
         BeanFactory factory = new XmlBeanFactory(res);

         CacheManager cacheManager = (CacheManager) factory.getBean("cacheManager");
         Cache levelOneCache = cacheManager.getCache("visitCache");
         String cacheObject = null;
         for (int i = 0; i < 10; i++) {
             Element element = levelOneCache.get("key");

             if (element == null) {
                 cacheObject ="test";
                 element = new Element("key", cacheObject);
                 levelOneCache.put(element);
                 System.out.println("cacheObject[" + cacheObject + "]" + ",无法从缓存中取到");
             } else {
                 cacheObject = (String) element.getValue();
                 System.out.println("cacheObject[" + cacheObject + "]" + ",从缓存中取到");
             }
         }
     }
}
