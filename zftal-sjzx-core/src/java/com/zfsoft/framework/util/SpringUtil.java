package com.zfsoft.framework.util;

import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
/**
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2012-12-11 下午4:20:29
 * @author liuchaoyong
 * @version 1.0
 */

public class SpringUtil {


    private static final WebApplicationContext appContext = ContextLoader.getCurrentWebApplicationContext();

    /**
     * 获取实现类
     *
     * @param clazz 接口class
     * @param <C>   接口类型
     * @return bean实例
     */
    @SuppressWarnings("unchecked")
    public static <C> C getImpl(Class<C> clazz) {
        return (C) appContext.getBean(lowerFirst(clazz.getSimpleName()) + "Impl");
    }

    @SuppressWarnings("unchecked")
    public static <C> C getBean(Class<C> clazz) {
        return (C) appContext.getBean(lowerFirst(clazz.getSimpleName()));
    }

    public static Object getBean(String beanName) {
        return appContext.getBean(beanName);
    }

    @SuppressWarnings("unchecked")
    public static <C> C getBean(String beanName, Class<C> clazz) {
        return (C) appContext.getBean(beanName);
    }

    /**
     * 往spring容器中注册bean
     *
     * @param id        bean id
     * @param beanClass 注册的bean 类
     */
    public static void registerBean(String id, Class<?> beanClass) {
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) ((ConfigurableApplicationContext) appContext).getBeanFactory();
        registry.registerBeanDefinition(id, new AnnotatedGenericBeanDefinition(beanClass));
    }

    /**
     * 往spring容器中注册bean id 为bean 的类名首字母小写
     *
     * @param beanClass
     */
    public static void registerBean(Class<?> beanClass) {
        String id = lowerFirst(beanClass.getSimpleName());
        registerBean(id, beanClass);
    }

    /**
     * 判断容器中是否包含某个类的实例
     *
     * @param beanClass
     * @return
     */
    public static boolean containsBean(Class<?> beanClass) {
        return containsBean(lowerFirst(beanClass.getSimpleName()));
    }

    /**
     * 判断容器中是否包含某个id类的实例
     *
     * @param id
     * @return
     */
    public static boolean containsBean(String id) {
        return appContext.containsBean(id);
    }

    /**
     * 将字符串首字母小写
     *
     * @param s 字符串
     * @return 首字母小写后的新字符串
     */
    private static String lowerFirst(CharSequence s) {
        int len = s.length();
        if (len == 0) return "";
        char c = s.charAt(0);
        if (Character.isLowerCase(c)) return s.toString();
        return new StringBuilder(len).append(Character.toLowerCase(c))
                .append(s.subSequence(1, len)).toString();
    }

}
