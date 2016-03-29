package jp.keio.jfn.wat;

/**
 * Created by jfn on 3/23/16.
 */
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "kwicEntityManagerFactory",
        transactionManagerRef = "kwicTransactionManager",
        basePackages = { "jp.keio.jfn.wat.KWIC.repository" })
public class KwicDataConfig {

    @Bean(name = "kwicDataSource")
    @ConfigurationProperties(prefix="kwic.datasource")
    public DataSource kwicDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "kwicEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean kwicEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("kwicDataSource") DataSource kwicDataSource) {
        return builder
                .dataSource(kwicDataSource)
                .packages("jp.keio.jfn.wat.KWIC.domain")
                .persistenceUnit("kwic")
                .build();
    }

    @Bean(name = "kwicTransactionManager")
    public PlatformTransactionManager kwicTransactionManager(
            @Qualifier("kwicEntityManagerFactory") EntityManagerFactory kwicEntityManagerFactory) {
        return new JpaTransactionManager(kwicEntityManagerFactory);
    }

}
