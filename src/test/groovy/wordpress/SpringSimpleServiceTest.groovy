package wordpress

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowire
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * 
 * @author Arseny Kovalchuk
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = [TestConfig.class])
class SpringSimpleServiceTest {

    @Autowired
    WordpressServiceFactory wpServiceFactory;
    
    @Test
    void testServices() {
        TaxonomiesService ts = wpServiceFactory.taxonomiesService;
        ts.getTaxonomies().each {
            println(it)
        }
        ts.getTerms('category', null).each {
            println(it)
        }
    }

}
