package wordpress;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This is not actually a Unit test, but an example of usage.
 * 
 * @author Arseny Kovalchuk
 *
 */
class JUnitSimpleServiceTest extends Assert {
    
    static String URL         = "http://wp-novostroyka.by.local/xmlrpc.php"
    static String USER        = "admin";
    static String PASSWORD    = "admin";
    
    static WordpressServiceFactory wordpressServiceFactory;
    
    @BeforeClass
    public static void init() {
        wordpressServiceFactory = WordpressServiceFactory.newInstance(URL, USER, PASSWORD);
    }
    
    @Test
    void testPostService() throws Exception {
        PostsService postsService = wordpressServiceFactory.postsService
        postsService.getPostStatusList().each {
            println it
        }
        println()
        def filter = new PostFilter(post_type : 'page')
        def fieldsToRetreive = ['post_id', 'post_name', 'post_title']
        postsService.getPosts(filter, fieldsToRetreive).each {
            println it
        }
        println()
    }

    @Test
    void testOptionService() throws Exception {
        OptionsService optionsService = wordpressServiceFactory.optionsService
        def options = optionsService.getOptions(null)
        options.each {
            println it
        }
        def oldSize = options['medium_size_h']
        
        println()
        def result = optionsService.setOptions([medium_size_h: 777])
        result.each {
            println it
        }
        result = optionsService.setOptions([medium_size_h: oldSize.value])
        result.each {
            println it
        }
        println()
    }
}
