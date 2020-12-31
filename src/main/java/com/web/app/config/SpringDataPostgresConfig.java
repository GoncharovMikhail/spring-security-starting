package com.web.app.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

/**
 * Configuration class for connection to the {@code PostgreSQL} database and further interaction with it.
 * <p>
 * Also, provides {@link javax.persistence.EntityManager} bean to use - it will manage {@code entities}.
 * It will be created under the hood, see
 * {@link #localContainerEntityManagerFactoryBean(DriverManagerDataSource, Properties)}.
 * <p>
 * {@link org.springframework.transaction.TransactionManager} bean is also created and configured in this class.
 * <p>
 * A documentation of {@link Configuration} annotation:
 * https://docs.spring.io/spring-framework/docs/4.0.4.RELEASE/javadoc-api/org/springframework/context/annotation/Configuration.html
 */
@Configuration
/* @EnableJpaRepositories enables JPA repositories. Basically, makes Spring data work. */
@EnableJpaRepositories(
        /* Specify a package where JpaRepositories may be used (maybe ?).
         * Set the root package name not to restrict the availability zone. */
        basePackages = "com.web.app",
        /* Specify bean definition name for <pre> EntityManagerFactory </pre>
         * Basically, name of the method, where <pre> EntityManagerFactory </pre> will be created, configured
         * and returned afterwards. */
        entityManagerFactoryRef = "localContainerEntityManagerFactoryBean",
        /* Specify bean definition name for <pre> TransactionManager </pre>.
         * Basically, name of the method, where <pre> TransactionManager </pre> will be created, configured
         * and returned afterwards. */
        transactionManagerRef = "jpaTransactionManager"
)
/* @EnableTransactionManagement enables Spring's annotation-driven transaction management capability. */
@EnableTransactionManagement
/* The <pre> @PropertySource("...") </pre> gets a relative path (to the <pre> "resources" </pre> folder)
 * to a property-file to read properties from it.
 *
 * In combination with <pre> @Value("${...}") </pre>, declared above a field,
 * we can inject a value into the annotated field, which was read
 * from the specified property-file.
 * <strong>Note:</strong>: write the name of a property (in the <pre> @Value("...") </pre> annotation)
 * in <pre> ${...} </pre> - otherwise, property won't be injected to the annotated field. */
@PropertySource("properties/db/springdata.properties")
public class SpringDataPostgresConfig {

    @Value("${db.driver-class-name}")
    private String driverClassName;

    @Value("${db.url}")
    private String url;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Value("${hibernate.packages_to_scan}")
    private String packages_to_scan;

    @Value("${hibernate.dialect}")
    private String dialect;

    @Value("${hibernate.show_sql}")
    private String show_sql;

    @Value("${hibernate.hbm2ddl.auto}")
    private String enable_lazy_load_no_trans;

    /**
     * Define the {@link DriverManagerDataSource} {@link Bean} to use -
     * a Spring's implementation of {@link javax.sql.DataSource}.
     * <p>
     * {@code DataSource} may provide {@link java.sql.Connection} to database,
     * then we may create different {@code statements} ({@code SQL(HQL/JPQL)} queries, mostly)
     * on this connection:
     * <ul>
     *     <li>
     *         {@link java.sql.Statement}
     *     </li>
     *     <li>
     *         {@link java.sql.PreparedStatement}
     *     </li>
     *     <li>
     *         {@link java.sql.CallableStatement}
     *     </li>
     * </ul>
     * and execute them.
     * <p>
     * You may see some of the differences between these types of {@code statements} here:
     * https://way2java.com/jdbc/difference-between-statement-preparedstatement-callablestatement/
     * Each of these {@code statements} has it's own features and purposes,
     * but we can execute {@code SQL} queries on each of them.
     * <p>
     * We need to specify:
     * <ul>
     *     <li>
     *         {@link java.sql.Driver} class name for the database.
     *         In my case, it is {@link org.postgresql.Driver} (as a {@link String}),
     *         because I use the {@code PostreSQL} database.
     *     </li>
     *     <li>
     *         {@code URL} (path) to my database.
     *     </li>
     *     <li>
     *         Username and password of my database to grant access.
     *     </li>
     * </ul>
     *
     * @return configured {@code DriverManagerDataSource}.
     */
    @Bean
    public DriverManagerDataSource driverManagerDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();

        driverManagerDataSource.setDriverClassName(driverClassName);
        driverManagerDataSource.setUrl(url);
        driverManagerDataSource.setUsername(username);
        driverManagerDataSource.setPassword(password);

        return driverManagerDataSource;
    }

    /**
     * Here I configure some of {@code Hibernate} settings and make a
     * {@link Properties} {@link Bean} out of it.
     * <p>
     * You can see more settings to configure here:
     * <ul>
     *     <li>
     *         {@link org.hibernate.jpa.AvailableSettings} -
     *         most are {@link Deprecated}, but some may be used and configured.
     *     </li>
     *     <li>
     *         {@link org.hibernate.cfg.AvailableSettings}.
     *     </li>
     * </ul>
     * <p>
     * I set {@code Hibernate's dialect}. A nice article about {@code Hibernate dialects}:
     * https://javabydeveloper.com/what-is-dialect-in-hibernate-and-list-of-dialects/
     * <p>
     * The {@code hibernate.show_sql} defines that {@code SQL(HQL/JPQL)} queries,
     * generated automatically by {@code Hibernate}, will be displayed in the console.
     * <p>
     * The {@code hibernate.hbm2ddl.auto} property automatically validates or exports {@code schema DDL}
     * to the database when the {@code SessionFactory} is created.
     * See documentation for available values of this property and their meanings:
     * https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#configurations-hbmddl
     *
     * @return configured {@code Hibernate} properties.
     */
    @Bean
    public Properties properties() {
        Properties hibernateProperties = new Properties();

        hibernateProperties.put("hibernate.dialect", dialect);
        hibernateProperties.put("hibernate.show_sql", show_sql);
        hibernateProperties.put("hibernate.hbm2ddl.auto", enable_lazy_load_no_trans);

        return hibernateProperties;
    }

    /**
     * Define and configure the {@link LocalContainerEntityManagerFactoryBean} - the {@code FactoryBean} of
     * {@link javax.persistence.EntityManagerFactory}, see {@link org.springframework.beans.factory.FactoryBean}
     * for more details.
     * {@code EntityManagerFactory} internally creates {@link javax.persistence.EntityManager},
     * which manages {@code entities}.
     * <p>
     *
     * <strong>NOTE:</strong> the {@link Autowired} above methods makes required params of a method
     * come from the {@code IOC container}.
     * <p>
     *
     * <strong>NOTE:</strong> It's not necessary to specify
     * {@link  javax.persistence.spi.PersistenceProvider} class manually when the
     * {@link org.springframework.orm.jpa.JpaVendorAdapter} is specified - each implementation of
     * {@code JpaVendorAdapter} has it's own {@code PersistenceProvider} - it is specified here:
     * {@link JpaVendorAdapter#getPersistenceProvider()}.
     * <p>
     * In {@link HibernateJpaVendorAdapter} the default {@code PersistenceProvider} is
     * {@link org.springframework.orm.jpa.vendor.SpringHibernateJpaPersistenceProvider},
     * it's almost the same to the one I use ({@link HibernatePersistenceProvider}),
     * but I'd like to use {@code HibernatePersistenceProvider},
     * as I saw in Eugeniy Borisov's talk about {@code Spring Data}.
     * <p>
     * Also, we specify {@code Hibernate} (JPA) properties
     * and packages, which contains all classes, annotated with the
     * {@link javax.persistence.Entity} annotation.
     *
     * @param dataSource the {@code DataSource}, used by {@code EntityManagers}.
     * @param properties {@code Hibernate} properties.
     * @return configured {@code LocalContainerEntityManagerFactoryBean}, but then, under the hood,
     * an {@code EntityManager} will be created and used to manage {@code entities}.
     */
    @Bean
    @Autowired
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(DriverManagerDataSource
                                                                                                 dataSource,
                                                                                         Properties properties) {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();

        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        localContainerEntityManagerFactoryBean.setJpaProperties(properties);
        localContainerEntityManagerFactoryBean.setPackagesToScan(packages_to_scan);

        return localContainerEntityManagerFactoryBean;
    }

    /**
     * A {@code transaction} is a unit of work that is performed against a database.
     * Meaning, {@code transactions} are units or sequences of work accomplished in a logical order,
     * whether in a manual fashion by a user.
     * In other words, {@code transaction} is a group of sequential commands that can be either executed in order
     * and as a whole, or not executed at all. {@code Transactions} are <strong>highly</strong> recommended to use.
     * <p>
     * To manage {@code transactions}, we should define a {@code TransactionManager}.
     * {@link EnableTransactionManagement} enables annotation-driven transaction management with
     * {@link org.springframework.transaction.annotation.Transactional}.
     * <p>
     *
     * <strong>NOTE:</strong> the {@link Autowired} above methods makes required params of a method
     * come from the {@code IOC container}.
     *
     * @param localContainerEntityManagerFactoryBean a wrapper for {@link javax.persistence.EntityManagerFactory},
     *                                               needed for {@code TransactionManager}.
     * @return {@link JpaTransactionManager} bean to use.
     */
    @Bean
    @Autowired
    public JpaTransactionManager jpaTransactionManager(LocalContainerEntityManagerFactoryBean
                                                               localContainerEntityManagerFactoryBean) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();

        jpaTransactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean.getObject());

        return jpaTransactionManager;
    }
}
