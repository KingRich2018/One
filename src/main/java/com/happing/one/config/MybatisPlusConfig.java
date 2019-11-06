package com.happing.one.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

@Configuration
public class MybatisPlusConfig {

  @Autowired
  private DataSource dataSource;

  @Autowired
  private MybatisProperties properties;

  @Autowired
  private ResourceLoader resourceLoader = new DefaultResourceLoader();

  @Autowired(required = false)
  private Interceptor[] interceptors;

  @Autowired(required = false)
  private DatabaseIdProvider databaseIdProvider;


  @Bean
  public PerformanceInterceptor performanceInterceptor() {
    PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
    /*<!-- SQL 执行性能分析，开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长 -->*/
    performanceInterceptor.setMaxTime(1000);
    /*<!--SQL是否格式化 默认false-->*/
    performanceInterceptor.setFormat(true);
    return performanceInterceptor;
  }

  @Bean
  public PaginationInterceptor paginationInterceptor() {
    return new PaginationInterceptor();
  }

  @Bean
  public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean() {
    MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
    mybatisPlus.setDataSource(dataSource);
    mybatisPlus.setVfs(SpringBootVFS.class);
    if (StringUtils.hasText(this.properties.getConfigLocation())) {
      mybatisPlus.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
    }
    mybatisPlus.setConfiguration(properties.getConfiguration());
    if (!ObjectUtils.isEmpty(this.interceptors)) {
      mybatisPlus.setPlugins(this.interceptors);
    }
    MybatisConfiguration mc = new MybatisConfiguration();
    mc.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
    mybatisPlus.setConfiguration(mc);
    if (this.databaseIdProvider != null) {
      mybatisPlus.setDatabaseIdProvider(this.databaseIdProvider);
    }
    if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
      mybatisPlus.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
    }
    if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
      mybatisPlus.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
    }
    if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
      mybatisPlus.setMapperLocations(this.properties.resolveMapperLocations());
    }
    return mybatisPlus;
  }
}
