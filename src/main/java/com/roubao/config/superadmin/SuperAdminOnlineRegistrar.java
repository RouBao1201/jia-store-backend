package com.roubao.config.superadmin;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 超级管理员上线自动配置类
 *
 * @author: SongYanBin
 * @date: 2023-12-12
 */
public class SuperAdminOnlineRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(SuperAdminOnlineRegistrar.class);

    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean existedBean = registry.containsBeanDefinition(SuperAdmin.class.getName());
        if (!existedBean) {
            AnnotationAttributes annoAttr = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EnableSuperAdminOnline.class.getName()));
            List<String> includeProfiles = Arrays.asList(Objects.requireNonNull(annoAttr).getStringArray("includeProfiles"));
            int userId = Objects.requireNonNull(annoAttr).getNumber("userId");

            // 校验启动环境信息是否满足超级管理员加载条件
            String[] activeProfiles = environment.getActiveProfiles();
            if (activeProfiles.length == 0) {
                logger.error("SuperAdminOnlineRegistrar ==> 超级管理员[{}]加载失败，未指定环境配置文件!", userId);
                return;
            }
            for (String activeProfile : environment.getActiveProfiles()) {
                if (!includeProfiles.contains(activeProfile)) {
                    logger.error("SuperAdminOnlineRegistrar ==> 超级管理员[{}]加载失败，环境配置文件：[{}]，注解配置：[{}].", userId, activeProfile, StrUtil.join("/", includeProfiles));
                    return;
                }
            }
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(SuperAdmin.class);
            ConstructorArgumentValues constructorArgumentValues = beanDefinition.getConstructorArgumentValues();
            constructorArgumentValues.addIndexedArgumentValue(0, userId);
            registry.registerBeanDefinition(SuperAdmin.BEAN_NAME, beanDefinition);
            logger.info("SuperAdminOnlineRegistrar ==> 超级管理员[{}]，已上线！环境配置文件：[{}]，注解配置：[{}].",
                    userId, StrUtil.join(",", environment.getActiveProfiles()), StrUtil.join("/", includeProfiles));
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
