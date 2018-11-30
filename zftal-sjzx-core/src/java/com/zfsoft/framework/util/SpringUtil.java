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
 * @since 2012-12-11 ����4:20:29
 * @author liuchaoyong
 * @version 1.0
 */

public class SpringUtil {


    private static final WebApplicationContext appContext = ContextLoader.getCurrentWebApplicationContext();

    /**
     * ��ȡʵ����
     *
     * @param clazz �ӿ�class
     * @param <C>   �ӿ�����
     * @return beanʵ��
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
     * ��spring������ע��bean
     *
     * @param id        bean id
     * @param beanClass ע���bean ��
     */
    public static void registerBean(String id, Class<?> beanClass) {
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) ((ConfigurableApplicationContext) appContext).getBeanFactory();
        registry.registerBeanDefinition(id, new AnnotatedGenericBeanDefinition(beanClass));
    }

    /**
     * ��spring������ע��bean id Ϊbean ����������ĸСд
     *
     * @param beanClass
     */
    public static void registerBean(Class<?> beanClass) {
        String id = lowerFirst(beanClass.getSimpleName());
        registerBean(id, beanClass);
    }

    /**
     * �ж��������Ƿ����ĳ�����ʵ��
     *
     * @param beanClass
     * @return
     */
    public static boolean containsBean(Class<?> beanClass) {
        return containsBean(lowerFirst(beanClass.getSimpleName()));
    }

    /**
     * �ж��������Ƿ����ĳ��id���ʵ��
     *
     * @param id
     * @return
     */
    public static boolean containsBean(String id) {
        return appContext.containsBean(id);
    }

    /**
     * ���ַ�������ĸСд
     *
     * @param s �ַ���
     * @return ����ĸСд������ַ���
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
