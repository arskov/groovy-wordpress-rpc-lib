package wordpress

import groovy.transform.Canonical;

import java.util.Date;
/**
 * 
 * @author Arseny Kovalchuk
 *
 */
class PropertyMapper {
    def toMap() {
        this.properties.findAll {
            it.value != null && it.value != 'null' && it.value != 0 && it.key != 'class'
        }
    }
}

