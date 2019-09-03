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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


/**
 * Created by wangk on 2017/7/5.
 */
@Configuration
@MapperScan(basePackages = HuamiDataSourceConfig.PACKAGE, sqlSessionTemplateRef = "huamiSqlSessionTemplate")
public class HuamiDataSourceConfig {

    static final String PACKAGE = "com.lj.testing.mapper.basealtic";
    static final String MAPPER_LOCATION = "classpath:mapper/huami/*.xml";

    @Bean(name = "huamiDataSource")
    @ConfigurationProperties(prefix = "spring.data-source.huami")
    @Primary
    public DataSource huamiDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "huamiSqlSessionFactory")
    @Primary
    public SqlSessionFactory huamiSqlSessionFactory(@Qualifier("huamiDataSource") DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(HuamiDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

    @Bean(name = "huamiTransactionManager")
    @Primary
    public DataSourceTransactionManager huamiTransactionManager(@Qualifier("huamiDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "huamiSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate huamiSqlSessionTemplate(@Qualifier("huamiSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
