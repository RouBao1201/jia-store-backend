package com.roubao.helper;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 事务处理类
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/17
 **/
@Component
public class TransactionHelper implements InitializingBean {
    private static DataSourceTransactionManager dataSourceTransactionManagerStatic;

    private final DataSourceTransactionManager dataSourceTransactionManager;

    @Autowired
    public TransactionHelper(DataSourceTransactionManager dataSourceTransactionManager) {
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        dataSourceTransactionManagerStatic = this.dataSourceTransactionManager;
    }

    /**
     * 开启事务
     *
     * @return 事务状态
     */
    public static TransactionStatus begin() {
        return begin(dataSourceTransactionManagerStatic);
    }

    /**
     * 提交事务
     *
     * @param status 事务状态
     */
    public static void commit(TransactionStatus status) {
        dataSourceTransactionManagerStatic.commit(status);
    }

    /**
     * 回滚事务
     *
     * @param status 事务状态
     */
    public static void rollback(TransactionStatus status) {
        dataSourceTransactionManagerStatic.rollback(status);
    }

    /**
     * 回滚事务
     *
     * @param dataSourceTransactionManager 事务管理器
     * @param status                       事务状态
     */
    public static void rollback(DataSourceTransactionManager dataSourceTransactionManager,
                                TransactionStatus status) {
        dataSourceTransactionManager.rollback(status);
    }

    /**
     * 提交事务
     *
     * @param dataSourceTransactionManager 事务管理器
     * @param status                       事务状态
     */
    public static void commit(DataSourceTransactionManager dataSourceTransactionManager,
                              TransactionStatus status) {
        dataSourceTransactionManager.commit(status);
    }

    /**
     * 开启事务
     *
     * @param dataSourceTransactionManager 事务管理器
     * @return 事务状态
     */
    public static TransactionStatus begin(DataSourceTransactionManager dataSourceTransactionManager) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        return dataSourceTransactionManager.getTransaction(def);
    }
}
