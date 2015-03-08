package by.novostroyka.migrate

import org.springframework.boot.SpringApplication
import org.springframework.context.ApplicationContext

import wordpress.Post
import wordpress.MediaService
import wordpress.PostFilter;
import wordpress.PostsService
import wordpress.UploadData
import wordpress.WordpressServiceFactory

class Main {
    
    final static String SQL = '''
select ci.*, cci.position, c.*
from category_catalogue_items cci left join category c on cci.category_id=c.category_id 
left join catalogue_item ci on cci.catalogue_item_id=ci.catalogue_item_id
order by ci.CATALOGUE_ITEM_ID desc'''
    
    static def void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(AppConfig.class, args);
        
        String fileName = ctx.getEnvironment().getProperty("test.media.file");
        
        //Sql sql = ctx.getBean("sql", groovy.sql.Sql.class);
        // sql.eachRow(SQL) { println it }
        //println sql.rows(SQL).size()
        
        WordpressServiceFactory wpServiceFactory = ctx.getBean(WordpressServiceFactory.class);
        
        PostsService postsService = wpServiceFactory.postsService
        MediaService mediaService = wpServiceFactory.mediaService

        println('List company posts')
        def filter = new PostFilter(post_type:'company', number:10, offset:0)
        postsService.getPosts(filter, null).each { println it }

        println('List media library')
        mediaService.getMediaLibrary().each { println it }
        
        //println('Upload file')
        //def bytes = new File(fileName).getBytes()
        //def uplaodData = new UploadData([name: 'groovy-test-img.jpg', type: 'image/jpeg', overwrite: true, post_id: 22140, bits: bytes])
        //def attachment = mediaService.uploadFile(uplaodData)
        //println("Attachment created: ${attachment}")
        //println("Media item: " + mediaService.getMediaItem(attachment.id))
        
    }
    
}
