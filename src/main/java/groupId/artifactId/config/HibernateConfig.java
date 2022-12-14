package groupId.artifactId.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.Properties;

@Configuration
public class HibernateConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.POSTGRESQL);
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("groupId.artifactId");

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect"); //DB Dialect
        jpaProperties.put("javax.persistence.jdbc.driver", "org.postgresql.Driver"); //DB Driver
        jpaProperties.put("javax.persistence.jdbc.url", "jdbc:postgresql://localhost/pizzeria"); //BD Mane
        jpaProperties.put("javax.persistence.jdbc.user", "postgres"); //DB User
        jpaProperties.put("javax.persistence.jdbc.password", "postgres"); //DB Password
        //Warning! Can rewrite the table
        jpaProperties.put("hibernate.hbm2ddl.auto", "update"); // create / create-drop / update
        jpaProperties.put("hibernate.id.new_generator_mappings", "true"); //directs how identity or sequence columns are generated when using @GeneratedValue
        //Configures the naming strategy that is used when Hibernate creates new database objects and schema elements
        jpaProperties.put("hibernate.physical_naming_strategy", "groupId.artifactId.config.CustomNamingStrategy");
        jpaProperties.put("hibernate.show_sql", "true"); // Show SQL in console
        jpaProperties.put("hibernate.format_sql", "true"); // Show SQL formatted
        jpaProperties.put("hibernate.use_sql_comments", "true");
        jpaProperties.put("hibernate.connection.pool_size", "10");
        jpaProperties.put("hibernate.default_schema", "pizza_manager");
        factory.setJpaProperties(jpaProperties);
        return factory;
    }
}
