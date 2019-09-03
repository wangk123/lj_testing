package com.lj.testing.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by wangk on 2017/7/12.
 */
@Configuration
@MapperScan(basePackages = BasealticDataSourceConfig.PACKAGE, sqlSessionTemplateRef = "basealticSqlSessionTemplate")
public class BasealticDataSourceConfig {

    static final String PACKAGE = "com.lj.testing.mapper.basealtic";
    static final String MAPPER_LOCATION = "classpath:mapper/basealtic/*.xml";

    @ConfigurationProperties(prefix = "spring.data-source.basealtic")
    @Bean(name = "basealticDataSource")
    public DataSource basealticDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "basealticSqlSessionFactory")
    public SqlSessionFactory basealticSqlSessionFactory(@Qualifier("basealticDataSource") DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(BasealticDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

    @Bean(name = "basealticTransactionManager")
    public DataSourceTransactionManager basealticTransactionManager(@Qualifier("basealticDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "basealticSqlSessionTemplate")
    public SqlSessionTemplate basealticSqlSessionTemplate(@Qualifier("basealticSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
