package by.novostroyka.migrate

import groovy.sql.Sql;

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component

import wordpress.WordpressServiceFactory;

@Configuration
@EnableAutoConfiguration
@ComponentScan
class AppConfig {

    @Value('${jdbc.url}')
    String jdbcUrl
    @Value('${jdbc.user}')
    String jdbcUser
    @Value('${jdbc.password}')
    String jdbcPassword
    @Value('${jdbc.driver}')
    String jdbcDriver
    
    @Value('${wordpress.url}')
    String wordpressUrl
    @Value('${wordpress.user}')
    String wordpressUser
    @Value('${wordpress.password}')
    String wordpressPassword

    
    @Bean
    def sql() {
        Sql.newInstance(jdbcUrl, jdbcUser, jdbcPassword, jdbcDriver);
    }
    
    @Bean
    def wordpressServiceFactory() {
        WordpressServiceFactory.newInstance(wordpressUrl, wordpressUser, wordpressPassword)
    }
}
