package com.zfsoft.common.spring;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import com.zfsoft.util.base.StringUtil;

/**
 * spring bean 工具类
 * User: yxx
 * Date: 10-12-8
 * Time: 下午6:15
 *
 * @author ohun@live.cn
 */
public final class SpringHolder implements ApplicationContextAware {
	
    private static ApplicationContext appContext =null;

    /**
     * 获取实现类
     *
     * @param clazz 接口class
     * @param <C>   接口类型
     * @return bean实例
     */
    @SuppressWarnings("unchecked")
    public static <C> C getImpl(Class<C> clazz) {
        return (C) appContext.getBean(StringUtil.lowerFirst(clazz.getSimpleName()) + "Impl");
    }

    @SuppressWarnings("unchecked")
    public static <C> C getBean(Class<C> clazz) {
        return (C) appContext.getBean(StringUtil.lowerFirst(clazz.getSimpleName()));
    }

    public static Object getBean(String beanName) {
        return appContext.getBean(beanName);
    }

    @SuppressWarnings("unchecked")
    public static <C> C getBean(String beanName, Class<C> clazz) {
        return (C) appContext.getBean(beanName);
    }

    public static <C>  Map<String, C> getBeansOfType(Class<C> clazz) {
    	return appContext.getBeansOfType(clazz);
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
        String id = StringUtil.lowerFirst(beanClass.getSimpleName());
        registerBean(id, beanClass);
    }

    /**
     * 判断容器中是否包含某个类的实例
     *
     * @param beanClass
     * @return
     */
    public static boolean containsBean(Class<?> beanClass) {
        return containsBean(StringUtil.lowerFirst(beanClass.getSimpleName()));
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

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		appContext=applicationContext;
	}

}
