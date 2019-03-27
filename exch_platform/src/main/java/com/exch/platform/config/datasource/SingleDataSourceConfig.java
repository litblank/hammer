
package com.exch.platform.config.datasource;

import cn.stylefeng.roses.core.config.properties.DruidProperties;
import cn.stylefeng.roses.core.datascope.DataScopeInterceptor;
import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.exch.platform.config.properties.GunsFlowableProperties;
import com.exch.platform.core.interceptor.RoleDataPower;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 数据源配置
 *
 */
@Configuration
@ConditionalOnProperty(prefix = "exch.muti-datasource", name = "open", havingValue = "false", matchIfMissing = true)
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(basePackages = {"com.exch.platform.modular.*.dao","com.exch.platform.modular.*.mapper"
,"org.flowable.db.mapping.mappings.xml"})
public class SingleDataSourceConfig {

    @Autowired
    GunsFlowableProperties gunsFlowableProperties;


    /**
     * druid配置
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidProperties druidProperties() {
        return new DruidProperties();
    }

    /**
     * 单数据源连接池配置
     */
    @Bean
    public DruidDataSource dataSource(DruidProperties druidProperties) {
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        gunsFlowableProperties.config(dataSource);
        return dataSource;
    }

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 数据范围mybatis插件
     */
    @Bean
    public DataScopeInterceptor dataScopeInterceptor() {
        return new DataScopeInterceptor();
    }

    /**
     * 乐观锁mybatis插件
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * SQL 拦截
     */
    @Bean
    public RoleDataPower mybatisSqlInterceptor(){
        return  new RoleDataPower();
    }
}

