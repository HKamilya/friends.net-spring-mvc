package ru.itis.javalab;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import ru.itis.javalab.repositories.*;
import ru.itis.javalab.services.*;
import ru.itis.javalab.repositories.*;
import ru.itis.javalab.services.*;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "ru.itis.javalab")
@PropertySource("classpath:db.properties")
public class AppConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    public ReviewsRepository reviewsRepository() {
        return new ReviewsRepositoryImpl(dataSource(), eventsService(), usersService());
    }

    @Bean
    public RequestsService requestsService() {
        return new RequestsServiceImpl(requestsRepository());
    }

    @Bean
    public ReviewsService reviewsService() {
        return new ReviewsServiceImpl(reviewsRepository());
    }

    @Bean
    public CategoriesRepository categoriesRepository() {
        return new CategoriesRepositoryIml(dataSource());
    }

    @Bean
    public CategoriesService categoriesService() {
        return new CategoriesServiceImpl(categoriesRepository());
    }

    @Bean
    public EventsRepository eventsRepository() {
        return new EventsRepositoryImpl(dataSource(), usersService(), categoriesService(), imagesService());
    }

    @Bean
    public EventsService eventsService() {
        return new EventsServiceImpl(eventsRepository());
    }

    @Bean
    public ImagesRepositoryImpl imagesRepository() {
        return new ImagesRepositoryImpl(dataSource());
    }

    @Bean
    public RequestsRepository requestsRepository() {
        return new RequestsRepositoryImpl(dataSource(), eventsService(), usersService());
    }

    @Bean
    public UsersRepositoryImpl usersRepository() {
        return new UsersRepositoryImpl(dataSource(), imagesService());
    }

    @Bean
    ImagesService imagesService() {
        return new ImagesServiceImpl(imagesRepository());
    }

    @Bean
    public UsersService usersService() {
        return new UsersServiceImpl(usersRepository());
    }


    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(environment.getProperty("db.url"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(environment.getProperty("db.hikari.max-pool-size")));
        hikariConfig.setUsername(environment.getProperty("db.username"));
        hikariConfig.setPassword(environment.getProperty("db.password"));
        hikariConfig.setDriverClassName(environment.getProperty("db.driver.classname"));
        return hikariConfig;
    }

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    public FreeMarkerViewResolver freemarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setPrefix("");
        resolver.setSuffix(".ftlh");
        resolver.setContentType("text/html;charset=UTF-8");
        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("/WEB-INF/ftl/");
        return configurer;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
