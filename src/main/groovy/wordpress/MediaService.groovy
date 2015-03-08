package wordpress

import groovy.transform.Canonical;
import groovy.transform.InheritConstructors

/**
 * See <a href="http://codex.wordpress.org/XML-RPC_WordPress_API/Media">XML-RPC_WordPress_API (Media)</a>
 * 
 * @author Arseny Kovalchuk
 *
 */
public interface MediaService {
    
    abstract def getMediaItem(attachmentId)
    abstract def getMediaLibrary(filter)
    abstract def uploadFile(UploadData data)

}

@Canonical
class MediaItem {
    String                  attachment_id
    Date                    date_created_gmt
    int                     parent
    String                  link
    String                  title
    String                  caption
    String                  description
    MediaItemMetadata       metadata
    PostThumbnailImageMeta  image_meta
    String                  thumbnail
}

@Canonical
class MediaItemMetadata {
    int                     width
    int                     height
    String                  file
    def                     sizes
    PostThumbnailImageMeta  image_meta
}

@Canonical
class MediaItemSize {
    String file
    String width
    String height
    String mime_type
}

@Canonical
class PostThumbnailImageMeta {
    int     orientation
    int     aperture
    String  credit
    String  camera
    String  caption
    int     created_timestamp
    String  copyright
    int     focal_length
    int     iso
    int     shutter_speed
    String  title
}

class UploadData extends PropertyMapper {
    String  name
    String  type
    byte[]  bits // this is original field name from WP... it should be 'bytes' instead
    boolean overwrite
    int post_id
}

@InheritConstructors
class MediaServiceImpl extends WordpressRpcService implements MediaService {
    
    @Override
    def getMediaItem(attachmentId) {
        def request = getRequest()
        request << attachmentId
        new MediaItem(
            rpcClient.execute('wp.getMediaItem', request)
        )
    }
    
    @Override
    def getMediaLibrary(filter) {
        def request = getRequest()
        if (filter) {
            request << filter
        }
        rpcClient.execute('wp.getMediaLibrary', request).collect { new MediaItem( it ) }
    }
    
    @Override
    def uploadFile(UploadData data) {
        def request = getRequest()
        request << data.toMap()
        rpcClient.execute('wp.uploadFile', request)
    }
}

