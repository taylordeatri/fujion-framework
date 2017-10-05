#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
#set( $CLASSNAME = $className.toUpperCase() )
#set( $classname = $className.toLowerCase() )
#set( $ClassName = $className.substring(0,1).toUpperCase() + $className.substring(1) )
#set( $className = $className.substring(0,1).toLowerCase() + $className.substring(1) )
package ${package};

import static org.junit.Assert.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.fujion.test.MockTest;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for ${displayName}
 */
public class ${ClassName}Test extends MockTest {
    
    private static final Log log = LogFactory.getLog(${ClassName}Test.class);
    
    /**
     * Unit Test initialization
     */
    @Before
    public final void init() {
        log.info("Initializing Test Class");
    }
    
    /**
     * Performs unit test
     */
    @Test
    public void test() {
    }
    
}
