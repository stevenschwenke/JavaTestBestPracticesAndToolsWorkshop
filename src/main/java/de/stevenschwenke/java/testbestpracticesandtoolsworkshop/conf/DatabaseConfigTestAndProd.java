package de.stevenschwenke.java.testbestpracticesandtoolsworkshop.conf;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile({"test", "prod"})
public class DatabaseConfigTestAndProd extends AbstractCloudConfig {

    @Bean
    public DataSource dataSource() {
        return connectionFactory().dataSource();
    }

    @Bean
    SpringLiquibase liquibase() {

        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource());
        liquibase.setChangeLog("classpath:/liquibase/liquibase-changelog.xml");
        return liquibase;
    }
}
