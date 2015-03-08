package wordpress

import groovy.transform.Canonical;
import groovy.transform.InheritConstructors;
/**
 * See <a href="http://codex.wordpress.org/XML-RPC_WordPress_API/Users">XML-RPC_WordPress_API (Users)</a>
 * 
 * @author Arseny Kovalchuk
 *
 */
public interface UsersService {

    abstract def getUsersBlogs()
    abstract def getUser(int userId, fields)
    abstract def getUsers(UserFilter filter, fields)
    abstract def getProfile(fields)
    abstract def editProfile(Profile profile)
    abstract def getAuthors()

}

@Canonical
class UserBlog extends PropertyMapper {
    String  blogid
    String  blogName
    String  url
    String  xmlrpc //: XML-RPC endpoint for the blog.
    boolean isAdmin
}

@Canonical
class User extends PropertyMapper {
    String  user_id
    String  username
    String  first_name
    String  last_name
    String  bio
    String  email
    String  nickname
    String  nicename
    String  url
    String  display_name
    Date    registered
    def     roles
}

@Canonical
class UserFilter extends PropertyMapper {
    String  role    //: Restrict results to only users of a particular role.
    String  who     //: If 'authors', then will return all non-subscriber users.
    int     number
    int     offset
    String  orderby
    String  order
}

@Canonical
class Profile extends PropertyMapper {
    String first_name
    String last_name
    String url
    String display_name
    String nickname
    String nicename
    String bio
}

@Canonical
class Author extends PropertyMapper {
    String user_id
    String user_login
    String display_name
}

@InheritConstructors
class UsersServiceImpl extends WordpressRpcService implements UsersService {

    @Override
    def getUsersBlogs() {
        rpcClient.execute('wp.getUsersBlogs', getRequest()).collect {
            new UserBlog(it)
        }
    }

    @Override
    def getUser(int userId, fields) {
        def request = getRequest()
        request << userId
        if (fields) {
            request << fields
        }
        new User(rpcClient.execute('wp.getUser', request))
    }
    
    @Override
    def getUsers(UserFilter filter, fields) {
        def request = getRequest()
        request << filter.toMap()
        if (fields) {
            request << fields
        }
        rpcClient.execute('wp.getUsers', request).collect {
            new User(it)
        }
    }
    
    @Override
    def getProfile(fields) {
        def request = getRequest()
        if (fields) {
            request << fields
        }
        new User(rpcClient.execute('wp.getProfile', request))
    }
    
    @Override
    def editProfile(Profile profile) {
        def request = getRequest()
        request << profile.toMap()
        rpcClient.execute('wp.editProfile', request)
    }
    
    @Override
    def getAuthors() {
        rpcClient.execute('wp.getAuthors', getRequest()).collect {
            new Author(it)
        }
    }

}

