package wordpress

import groovy.transform.Canonical;
import groovy.transform.InheritConstructors
/**
 * See <a href="http://codex.wordpress.org/XML-RPC_WordPress_API/Comments">XML-RPC_WordPress_API (Comments)</a>
 * 
 * @author Arseny Kovalchuk
 *
 */
public interface CommentsService {

    abstract def getCommentCount(int postId)
    abstract def getComment(int commentId)
    abstract def getComments(CommentFilter filter)
    abstract def newComment(int postId, NewComment comment)
    abstract def editComment(int commentId, EditComment comment)
    abstract def deleteComment(int commentId)
    abstract def getCommentStatusList()
    
}

@Canonical
class Comment extends PropertyMapper {
    String  comment_id
    String  parent
    String  user_id
    Date    dateCreated
    String  status
    String  content
    String  link
    String  post_id
    String  post_title
    String  author
    String  author_url
    String  author_email
    String  author_ip
    String  type
}

@Canonical
class NewComment extends PropertyMapper {
    int     comment_parent
    String  content
    String  author
    String  author_url
    String  author_email
}

@Canonical
class EditComment extends PropertyMapper {
    String  status //: See #wp.getCommentStatusList. Usually 'hold', 'approve', or 'spam'.
    Date    date_created_gmt
    String  content
    String  author
    String  author_url
    String  author_email
}

@Canonical
class CommentCount extends PropertyMapper {
    int     approved
    int     awaiting_moderation
    int     spam
    int     total_comments
}

@Canonical
class CommentFilter extends PropertyMapper {
    int     post_id //: If empty, retrieves all comments.
    String  status
    int     number
    int     offset
}

@InheritConstructors
class CommentsServiceImpl extends WordpressRpcService implements CommentsService {

    @Override
    def getCommentCount(int postId) {
        def request = getRequest()
        request << postId
        rpcClient.execute('wp.getCommentCount', request).collect {
            new CommentCount(it)
        }
    }
    
    @Override
    def getComment(int commentId) {
        def request = getRequest()
        request << commentId
        new Comment(rpcClient.execute('wp.getComment', request))
    }
    
    @Override
    def getComments(CommentFilter filter) {
        def request = getRequest()
        request << filter.toMap()
        rpcClient.execute('wp.getComment', request).collect {
            new Comment(it)
        }
    }
    
    @Override
    def newComment(int postId, NewComment comment) {
        def request = getRequest()
        request << comment.toMap()
        rpcClient.execute('wp.newComment', request)
    }
    
    @Override
    def editComment(int commentId, EditComment comment) {
        def request = getRequest()
        request << commentId
        request << comment.toMap()
        rpcClient.execute('wp.editComment', request)
    }
    
    @Override
    def deleteComment(int commentId) {
        def request = getRequest()
        request << commentId
        rpcClient.execute('wp.deleteComment', request)
    }
    
    @Override
    def getCommentStatusList() {
        rpcClient.execute('wp.getCommentStatusList', getRequest())
    }

}

