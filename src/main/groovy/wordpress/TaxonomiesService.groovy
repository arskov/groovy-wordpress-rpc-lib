package wordpress

import groovy.transform.Canonical;
import groovy.transform.InheritConstructors;
/**
 * See <a href="http://codex.wordpress.org/XML-RPC_WordPress_API/Taxonomies">XML-RPC_WordPress_API (Taxonomies)</a>
 * 
 * @author Arseny Kovalchuk
 *
 */
public interface TaxonomiesService {

    abstract def getTaxonomy(String taxonomy)
    abstract def getTaxonomies()
    abstract def getTerm(String taxonomy, int termId)
    abstract def getTerms(String taxonomy, TermFilter filter)
    abstract def newTerm(NewTerm term)
    abstract def editTerm(int termId, NewTerm term)
    abstract def deleteTerm(int termId)
}

@Canonical
class Taxonomy extends PropertyMapper {
    
    String      name
    String      label
    boolean     hierarchical
    boolean     show_ui
    boolean     _builtin
    def         labels
    def         cap
    def         object_type

    private boolean isPublic;
    public boolean getPublic() { this.isPublic }
    public void setPublic(boolean isPublic) { this.isPublic = isPublic }
}

@Canonical
class Term extends PropertyMapper {
    
    String  term_id
    String  name
    String  slug
    String  term_group
    String  term_taxonomy_id
    String  taxonomy
    String  description
    String  parent
    int     count
    
}

@Canonical
class NewTerm extends PropertyMapper {
    String  name
    String  taxonomy
    String  slug            //: Optional.
    String  description     //: Optional.
    int     parent          //: Optional.
}

@Canonical
class TermFilter extends PropertyMapper {
    int         number
    int         offset
    String      orderby
    String      order
    boolean     hide_empty  //: Whether to return terms with count=0.
    String      search      //: Restrict to terms with names that contain (case-insensitive) this value.
}

@InheritConstructors
class TaxonomiesServiceImpl extends WordpressRpcService implements TaxonomiesService {
    
    @Override
    def getTaxonomy(String taxonomy) {
        def request = getRequest()
        request << taxonomy
        rpcClient.execute('wp.getTaxonomy', request)
    }

    @Override
    def getTaxonomies() {
        rpcClient.execute('wp.getTaxonomies', getRequest()).collect {
            new Taxonomy(it)
        }
    }
    
    @Override
    def getTerm(String taxonomy, int term_id) {
        def request = getRequest()
        request << taxonomy
        request << term_id
        new Term(rpcClient.execute('wp.getTerm', request))
    }
    
    @Override
    def getTerms(String taxonomy, TermFilter filter) {
        def request = getRequest()
        request << taxonomy
        if (filter) {
            request << filter.toMap()
        }
        rpcClient.execute('wp.getTerms', request).collect {
            new Term(it)
        }
    }
 
    @Override
    def newTerm(NewTerm term) {
        def request = getRequest()
        request << term.toMap()
        rpcClient.execute('wp.newTerm', request)
    }
    
    @Override
    def editTerm(int term_id, NewTerm term) {
        def request = getRequest()
        request << term_id
        request << term.toMap()
        rpcClient.execute('wp.editTerm', request)
    }
    
    @Override
    def deleteTerm(int term_id) {
        def request = getRequest()
        request << term_id
        rpcClient.execute('wp.deleteTerm', request)
    }

}
