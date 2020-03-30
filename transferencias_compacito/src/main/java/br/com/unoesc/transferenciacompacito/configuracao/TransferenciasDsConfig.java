/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.unoesc.transferenciacompacito.configuracao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "transferenciasEntityManagerFactory",
        transactionManagerRef = "transferenciasTransactionManager",
        basePackages = "br.com.unoesc.transferenciacompacito.repositorys.transferencias"
)
@EnableTransactionManagement
public class TransferenciasDsConfig {

    @Autowired
    private Environment env;

    @Bean
    @ConfigurationProperties(prefix="transferencias.datasource")
    public DataSourceProperties transferenciasDataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean
    public DataSource transferenciasDataSource() {
        DataSourceProperties transferenciasDataSourceProperties = transferenciasDataSourceProperties();

        return DataSourceBuilder.create()
            .driverClassName(transferenciasDataSourceProperties.getDriverClassName())
            .url(transferenciasDataSourceProperties.getUrl())
            .username(transferenciasDataSourceProperties.getUsername())
            .password(transferenciasDataSourceProperties.getPassword())
            .build();
    }

    @Bean
    public PlatformTransactionManager transferenciasTransactionManager() {
        EntityManagerFactory factory = transferenciasEntityManagerFactory().getObject();

        return new JpaTransactionManager(factory);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean transferenciasEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(transferenciasDataSource());
        factory.setPackagesToScan(new String[]{"br.com.unoesc.transferenciacompacito.models.transferencias"});
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        return factory;
    }

    @Bean
    public DataSourceInitializer transferenciasDataSourceInitializer() {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(transferenciasDataSource());
        dataSourceInitializer.setEnabled(env.getProperty("transferencias.datasource.initialize", Boolean.class, false));

        return dataSourceInitializer;
    }
}
