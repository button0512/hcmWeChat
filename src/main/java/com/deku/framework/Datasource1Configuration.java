package com.deku.framework;


import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.support.TransactionTemplate;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import javax.sql.DataSource;

/**
 * 手动创建GlobalDb的DataSource以及相关配置
 *
 * @Author FENGCHANGXUE
 * @Create 2018/12/06 16:53
 **/
@Configuration
public class Datasource1Configuration {

    @Bean("dataSource1")
    public static DataSource dataSource1(DataSourceConfig dataSourceConfig) {
        HikariDataSource dataSource = new HikariDataSource();
        BeanUtils.copyProperties(dataSourceConfig.getDataSourceMap().get("db1"), dataSource);
        return dataSource;
    }

    @Bean("db1SessionFactory")
    public static SqlSessionFactory db1SessionFactory(@Qualifier("dataSource1") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage("com.deku.domain");

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("classpath:/mapper/*.xml"));
        bean.setPlugins(DataSourceFactory.buildPageInterceptorArray());
        return bean.getObject();
    }

    @Bean("db1SqlSessionTemplate")
    public static SqlSessionTemplate db1SqlSessionTemplate(@Qualifier("db1SessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean("db1MapperScanner")
    public static MapperScannerConfigurer db1MapperScanner() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.deku.repository");
        mapperScannerConfigurer.setSqlSessionTemplateBeanName("db1SqlSessionTemplate");
        mapperScannerConfigurer.setAnnotationClass(Repository.class);
        return mapperScannerConfigurer;
    }

    @Bean(name = "db1DataSourceTransactionManager")
    public static PlatformTransactionManager db1DataSourceTransactionManager(@Qualifier("dataSource1") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "db1DataSourceTransactionTemplate")
    public static TransactionTemplate db1TransactionTemplate(@Qualifier("db1DataSourceTransactionManager") PlatformTransactionManager platformTransactionManager) {
        return new TransactionTemplate(platformTransactionManager);
    }

    /**
     * 事务拦截器
     */
    @Bean(name = "db1TransactionInterceptor")
    public static TransactionInterceptor db1TransactionInterceptor(@Qualifier("db1DataSourceTransactionManager") PlatformTransactionManager transactionManager) {
        return DataSourceFactory.buildTransactionInterceptor(transactionManager);
    }

    /**
     * 切面拦截规则
     */
    @Bean(name = "db1PointcutAdvisor")
    public static AspectJExpressionPointcutAdvisor db1PointcutAdvisor(@Qualifier("db1TransactionInterceptor") TransactionInterceptor txInterceptor) {
        AspectJExpressionPointcutAdvisor pointcutAdvisor = new AspectJExpressionPointcutAdvisor();
        pointcutAdvisor.setAdvice(txInterceptor);
        pointcutAdvisor.setExpression("execution(public * com.deku.service..*.*(..))");
        return pointcutAdvisor;
    }

}
