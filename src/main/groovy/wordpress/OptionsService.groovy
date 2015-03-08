package wordpress

import groovy.transform.Canonical;
import groovy.transform.InheritConstructors;
/**
 * See <a href="http://codex.wordpress.org/XML-RPC_WordPress_API/Options">XML-RPC_WordPress_API (Options)</a>
 * 
 * @author Arseny Kovalchuk
 *
 */
public interface OptionsService {

    abstract def getOptions(filter)
    abstract def setOptions(options)
    
}
@Canonical
class Option extends PropertyMapper {
    String      desc
    String      value
    boolean     readonly
}

@InheritConstructors
class OptionsServiceImpl extends WordpressRpcService implements OptionsService {

    @Override
    def getOptions(filter) {
        def request = getRequest()
        if (filter) {
            request << filter
        }
        rpcClient.execute('wp.getOptions', request).collectEntries { key, value ->
            [key, new Option(value)]
        }
    }

    @Override
    def setOptions(options) {
        def request = getRequest()
        if (options) {
            request << options
        }
        rpcClient.execute('wp.setOptions', request).collectEntries { key, value ->
            [key, new Option(value)]
        }
    }
    
}

