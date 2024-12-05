package com.apply.applyKerja.config;

import com.apply.applyKerja.security.Crypto;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 03/12/24 20.38
@Last Modified 03/12/24 20.38
Version 1.0
*/
@Configuration
public class MainConfig {

    @Autowired
    private Environment environment;

    @Primary
    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(environment.getProperty("spring.datasource.driverClassName"));
        dataSourceBuilder.url(Crypto.performDecrypt(environment.getProperty("spring.datasource.url")));
        dataSourceBuilder.username(Crypto.performDecrypt(environment.getProperty("spring.datasource.username")));//environment.getProperty("spring.datasource.user")
        dataSourceBuilder.password(Crypto.performDecrypt(environment.getProperty("spring.datasource.password")));//environment.getProperty("spring.datasource.password")
        return dataSourceBuilder.build();
    }


    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

}
