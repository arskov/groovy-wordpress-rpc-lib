package wordpress

import java.util.Date;

import groovy.transform.Canonical;
import groovy.transform.InheritConstructors;
/**
 * See <a href="http://codex.wordpress.org/XML-RPC_WordPress_API/Posts">XML-RPC_WordPress_API (Posts)</a>
 * 
 * @author Arseny Kovalchuk
 *
 */

public interface PostsService {

    abstract def getPost(int postId, fields)
    abstract def getPosts(PostFilter filter, fields)
    abstract def newPost(NewPost postObject)
    abstract def editPost(int postId, Post postObject)
    abstract def deletePost(int postId)
    abstract def getPostType(postTypeName, fields)
    abstract def getPostTypes(PostFilter filter, fields)
    abstract def getPostFormats(filter)
    abstract def getPostStatusList()
}

@Canonical
class Post extends PropertyMapper {
    int             ID
    String          post_id
    String          post_title
    Date            post_date
    Date            post_date_gmt
    Date            post_modified
    Date            post_modified_gmt
    String          post_status
    String          post_type
    String          post_format
    String          post_name            // : Encoded URL (slug)
    String          post_author
    String          post_password
    String          post_excerpt
    String          post_content
    String          post_parent
    String          post_mime_type
    String          link
    String          guid
    int             menu_order
    String          comment_status
    String          ping_status
    boolean         sticky
    String          page_template
    
    MediaItem       post_thumbnail
    Term[]          terms
    CustomField[]   custom_fields
    Enclosure       enclosure
    
    /* copied from wordpress
    int             ID
    String          post_id
    int             post_parent
    String          guid
    String          post_type
    String          post_status
    String          post_title
    String          post_mime_type
    int             post_author
    String          post_excerpt
    String          post_content
    Date            post_date
    Date            post_date_gmt
    Date            post_modified
    Date            post_modified_gmt
    String          post_format
    String          post_name            // : Encoded URL (slug)
    String          post_password
    String          comment_status
    String          ping_status
    String          link
    boolean         sticky
    int             menu_order
    MediaItem       post_thumbnail
    Term[]          terms
    CustomField[]   custom_fields
    Enclosure       enclosure
    */
}

@Canonical
class NewPost extends PropertyMapper {
    String          post_type
    String          post_status
    String          post_title
    String          post_author
    String          post_excerpt
    String          post_content
    Date            post_date
    Date            post_date_gmt
    String          post_format
    String          post_name       // : Encoded URL (slug)
    String          post_password
    String          comment_status
    String          ping_status
    boolean         sticky
    int             post_thumbnail
    int             post_parent
    CustomField[]   custom_fields
    String          post_mime_type
    def             terms           // Taxonomy names as keys, array of term IDs as values.
    def             terms_names     // Taxonomy names as keys, array of term names as values.
    Enclosure       enclosure
    
    String          page_template
    String          link
    String          guid
    int             menu_order
}

@Canonical
class PostFilter extends PropertyMapper {
    String  post_type
    String  post_status
    int     number
    int     offset
    String  orderby
    String  order
}

@Canonical
class CustomField extends PropertyMapper {
    String      id
    String      key
    def         value
}

@Canonical
class Enclosure extends PropertyMapper {
    String      url
    int         length
    String      type
}

@InheritConstructors
class PostsServiceImpl extends WordpressRpcService implements PostsService {

    @Override
    def getPost(int postId, fields) {
        def request = getRequest()
        request << postId
        if (fields) {
            request << fields
        }
        new Post(rpcClient.execute('wp.getPost', request))
    }

    @Override
    def getPosts(PostFilter filter, fields) {
        def request = getRequest()
        if (filter) {
            request << filter.toMap()
        }
        if (fields) {
            request << fields
        }
        rpcClient.execute('wp.getPosts', request).collect { new Post (it) }
    }

    @Override
    def newPost(NewPost postObject) {
        def request = getRequest()
        request << postObject.toMap()
        rpcClient.execute('wp.newPost', request)
    }

    @Override
    def editPost(int postId, Post postObject) {
        def request = getRequest()
        request << postId
        request << postObject.toMap()
        rpcClient.execute('wp.editPost', request)
    }

    @Override
    def deletePost(int postId) {
        def request = getRequest()
        request << postId
        rpcClient.execute('wp.deletePost', request)
    }

    @Override
    def getPostType(postTypeName, fields) {
        def request = getRequest()
        request << postTypeName
        if (fields) {
            request << fields
        }
        rpcClient.execute('wp.getPostType', request)
    }

    @Override
    def getPostTypes(PostFilter filter, fields) {
        def request = getRequest()
        if (filter) {
            request << filter.toMap()
        }
        if (fields) {
            request << fields
        }
        rpcClient.execute('wp.getPostTypes', request)
    }

    @Override
    def getPostFormats(filter) {
        def request = getRequest()
        if (filter) {
            request << filter
        }
        rpcClient.execute('wp.getPostFormats', request)
    }

    @Override
    def getPostStatusList() {
        rpcClient.execute('wp.getPostStatusList', getRequest())
    }
}

