package wordpress

import groovy.net.xmlrpc.*
import groovy.transform.Canonical
import groovy.transform.InheritConstructors

/**
 * 
 * @author Arseny Kovalchuk
 *
 */
@Canonical
class WordpressServiceFactory {
    
    PostsService postsService
    
    final TaxonomiesService taxonomiesService
    
    final MediaService mediaService
    
    final CommentsService commentsService
    
    final OptionsService optionsService
    
    final UsersService usersService
    
    private WordpressServiceFactory(wordpressUrl, wordpressUser, wordpressPassword) {
        def config = [wordpressUrl: wordpressUrl, wordpressUser: wordpressUser, wordpressPassword: wordpressPassword]
        this.postsService = new PostsServiceImpl(config)
        this.taxonomiesService = new TaxonomiesServiceImpl(config)
        this.mediaService = new MediaServiceImpl(config)
        this.commentsService = new CommentsServiceImpl(config)
        this.optionsService = new OptionsServiceImpl(config)
        this.usersService = new UsersServiceImpl(config)
    }
    
    static WordpressServiceFactory newInstance(wordpressUrl, wordpressUser, wordpressPassword) {
        new WordpressServiceFactory(wordpressUrl, wordpressUser, wordpressPassword)
    }
}
