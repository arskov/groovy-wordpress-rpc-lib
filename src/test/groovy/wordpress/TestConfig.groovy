package wordpress

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * 
 * @author Arseny Kovalchuk
 *
 */
@Configuration
@PropertySources([
    @PropertySource("classpath:application.properties")
])
class TestConfig {

    @Value('${wordpress.url}')
    String wordpressUrl
    @Value('${wordpress.user}')
    String wordpressUser
    @Value('${wordpress.password}')
    String wordpressPassword

    @Bean
    def static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
       new PropertySourcesPlaceholderConfigurer()
    }
    
    @Bean
    def wordpressServiceFactory() {
        WordpressServiceFactory.newInstance(wordpressUrl, wordpressUser, wordpressPassword)
    }

}

