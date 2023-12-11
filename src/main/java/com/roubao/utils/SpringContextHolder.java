package com.roubao.utils;

import cn.hutool.core.map.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * spring上下文持有工具类
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/22
 **/
@Component
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {
    private static final Logger log = LoggerFactory.getLogger(SpringContextHolder.class);

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * 获取spring上下文对象
     *
     * @return spring上下文对象
     */
    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }

    /**
     * 根据bean名称获取bean
     *
     * @param beanName bean名称
     * @return bean
     */
    public static Object getBean(String beanName) {
        return getBean(beanName, false);
    }

    /**
     * 根据bean名称获取bean
     *
     * @param beanName       bean名称
     * @param ignoreNotFound 是否忽略容器中是否含有bean
     * @return bean
     */
    public static Object getBean(String beanName, boolean ignoreNotFound) {
        try {
            return getApplicationContext().getBean(beanName);
        } catch (NoSuchBeanDefinitionException e) {
            log.error("SpringContextHolder => The Bean is not found. [beanName:{}, ignoreNotFound:{}]. ErrorMessage:"
                    + e.getMessage() + ". ", beanName, ignoreNotFound);
            if (ignoreNotFound) {
                return null;
            }
            throw e;
        }
    }

    /**
     * 根据bean类型获取bean
     *
     * @param beanClass bean类型
     * @param <T>       bean类型
     * @return bean
     */
    public static <T> T getBean(Class<T> beanClass) {
        return getBean(beanClass, false);
    }

    /**
     * 根据bean类型获取bean
     *
     * @param beanClass      bean类型
     * @param ignoreNotFound 是否忽略容器中是否含有bean
     * @param <T>            bean类型
     * @return bean
     */
    public static <T> T getBean(Class<T> beanClass, boolean ignoreNotFound) {
        try {
            return getApplicationContext().getBean(beanClass);
        } catch (NoSuchBeanDefinitionException e) {
            log.error(
                    "SpringContextHolder => SpringContextHolder => The Bean is not found. [beanClass:{}, ignoreNotFound:{}]. ErrorMessage:"
                            + e.getMessage() + ". ",
                    beanClass, ignoreNotFound);
            if (ignoreNotFound) {
                return null;

            }
            throw e;
        }
    }

    /**
     * 根据bean名称和bean类型获取bean
     *
     * @param beanName bean名称
     * @param clazz    bean类型
     * @param <T>      bean类型
     * @return bean
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        return getBean(beanName, clazz, false);
    }

    /**
     * 根据bean名称和bean类型获取bean
     *
     * @param beanName       bean名称
     * @param beanClass      bean类型
     * @param ignoreNotFound ignoreNotFound
     * @param <T>            bean类型
     * @return bean
     */
    public static <T> T getBean(String beanName, Class<T> beanClass, boolean ignoreNotFound) {
        try {
            return getApplicationContext().getBean(beanClass);
        } catch (NoSuchBeanDefinitionException e) {
            log.error("SpringContextHolder => The Bean is not found. [beanName:{}, beanClass:{}, ignoreNotFound:"
                    + ignoreNotFound + "]. ErrorMessage:" + e.getMessage() + ". ", beanName, beanClass);
            if (ignoreNotFound) {
                return null;
            }
            throw e;
        }
    }

    /**
     * 根据bean类型获取所有bean
     *
     * @param beanClass bean类型
     * @param <T>       枚举
     * @return bean
     */
    public static <T> Map<String, T> getBeanMapForType(Class<T> beanClass) {
        return getApplicationContext().getBeansOfType(beanClass);
    }

    /**
     * 根据bean类型获取所有bean
     *
     * @param beanClass bean类型
     * @param <T>       枚举
     * @return bean
     */
    public static <T> List<T> getBeanListForType(Class<T> beanClass) {
        List<T> beanList = new ArrayList<>();
        Map<String, T> beanMapForType = getBeanMapForType(beanClass);
        if (MapUtil.isNotEmpty(beanMapForType)) {
            beanMapForType.forEach((beanName, beanObj) -> beanList.add(beanObj));
        }
        return beanList;
    }

    /**
     * 是否存在bean
     *
     * @param beanName bean名称
     * @return boolean
     */
    public static boolean containsBean(String beanName) {
        return getApplicationContext().containsBean(beanName);
    }

    /**
     * 根据bean名称获取bean类型
     *
     * @param beanName bean名称
     * @return bean类型
     */
    public static Class<?> getBeanType(String beanName) {
        return getApplicationContext().getType(beanName);
    }

    /**
     * 根据bean类型获取bean名称
     *
     * @param beanClass bean类型
     * @param <T>       bean类型泛型
     * @return bean名称集合
     */
    public static <T> String[] getBeanNamesForType(Class<T> beanClass) {
        return getApplicationContext().getBeanNamesForType(beanClass);
    }

    /**
     * 注册单例bean（不存在才注册）
     *
     * @param beanName bean名称
     * @param beanObj  bean对象
     * @param <T>      bean对象类型泛型
     * @return bean对象
     */
    public static <T> T registerSingletonBeanIfAbsent(String beanName, T beanObj) {
        T bean;
        if (containsBean(beanName)) {
            bean = (T) getBean(beanName, beanObj.getClass());
        } else {
            bean = registerSingletonBean(beanName, beanObj);
        }
        return bean;
    }

    /**
     * 注册单例bean
     *
     * @param beanName bean名称
     * @param beanObj  bean对象
     * @param <T>      bean对象类型泛型
     * @return 注册的bean对象
     */
    public static <T> T registerSingletonBean(String beanName, T beanObj) {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) getApplicationContext()
                .getAutowireCapableBeanFactory();
        beanFactory.registerSingleton(beanName, beanObj);
        beanFactory.autowireBean(beanObj);
        return (T) beanFactory.getBean(beanName, beanObj.getClass());
    }

    /**
     * 判断是否单例bean
     *
     * @param beanName bean名称
     * @return 是否单例bean
     */
    public static boolean isSingletonBean(String beanName) {
        return getApplicationContext().isSingleton(beanName);
    }


    /**
     * 判断是否多例bean
     *
     * @param beanName bean名称
     * @return 是否多例bean
     */
    public static boolean isPrototypeBean(String beanName) {
        return getApplicationContext().isPrototype(beanName);
    }

    /**
     * 校验spring上下对象是否为空
     */
    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("SpringContextHolder => ApplicationContext is not injected.");
        }
    }

    @Override
    public void destroy() throws Exception {
        SpringContextHolder.clearHolder();
    }

    private static void clearHolder() {
        log.info("SpringContextHolder ==> Clean spring context.");
        SpringContextHolder.applicationContext = null;
    }
}
