package wordpress

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
/**
 * 
 * @author Arseny Kovalchuk
 *
 */
class WordpressRpcService {
    
    protected String wordpressUrl
    protected String wordpressUser
    protected String wordpressPassword
    
    protected final XmlRpcClient rpcClient
    protected def requestTemplate
    
    WordpressRpcService(config) {
        this.wordpressUrl = config.wordpressUrl
        this.wordpressUser = config.wordpressUser
        this.wordpressPassword = config.wordpressPassword
        XmlRpcClientConfigImpl rpcClientConfig = new XmlRpcClientConfigImpl()
        rpcClientConfig.setServerURL(new URL(wordpressUrl))
        this.rpcClient = new XmlRpcClient()
        this.rpcClient.setConfig(rpcClientConfig);
        this.requestTemplate = [1, wordpressUser, wordpressPassword]
    }
    
    protected getRequest() {
        requestTemplate.clone()
    }
    
}

