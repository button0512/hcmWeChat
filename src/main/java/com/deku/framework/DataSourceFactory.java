package com.deku.framework;


import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Properties;

/**
 * DataSource工厂类，提供了获取各个数据源的工厂方法
 *
 * @Author FENGCHANGXUE
 * @Create 2018/12/06 16:53
 **/
public class DataSourceFactory {

    public static Interceptor[] buildPageInterceptorArray() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("properties", "dialect=mariadb");
        pageHelper.setProperties(properties);

        return new Interceptor[]{pageHelper};
    }

    public static TransactionInterceptor buildTransactionInterceptor(PlatformTransactionManager transactionManager) {
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();

        // 有事务嵌入事务，没有事务开启新事务
        DefaultTransactionAttribute txAttr_REQUIRES_NEW = new DefaultTransactionAttribute();
        // 不支持事务和只读
        DefaultTransactionAttribute txAttr_NOT_SUPPORTED = new DefaultTransactionAttribute();

        txAttr_REQUIRES_NEW.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        txAttr_NOT_SUPPORTED.setReadOnly(Boolean.TRUE);
        txAttr_NOT_SUPPORTED.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);

        source.addTransactionalMethod("exists", txAttr_NOT_SUPPORTED);
        source.addTransactionalMethod("do*", txAttr_REQUIRES_NEW);
        source.addTransactionalMethod("add*", txAttr_REQUIRES_NEW);
        source.addTransactionalMethod("getId*", txAttr_NOT_SUPPORTED);

        return new TransactionInterceptor(transactionManager, source);
    }


}
