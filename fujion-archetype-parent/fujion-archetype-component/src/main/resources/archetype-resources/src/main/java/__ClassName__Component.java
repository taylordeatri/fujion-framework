#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
#set( $CLASSNAME = $className.toUpperCase() )
#set( $classname = $className.toLowerCase() )
#set( $ClassName = $className.substring(0,1).toUpperCase() + $className.substring(1) )
#set( $className = $className.substring(0,1).toLowerCase() + $className.substring(1) )
package ${package};

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.component.BaseComponent;

@Component(tag = "${classname}", widgetModule = "${artifactId}", widgetClass = "${ClassName}", parentTag = "*")
public class ${ClassName}Component extends BaseComponent {

	private static final Log log = LogFactory.getLog(#ucc()Component.class);

	public ${ClassName}Component() {
	}
	
    @Override
    protected void _initProps(Map<String, Object> props) {
        props.put("wclazz", "${artifactId}");
        super._initProps(props);
    }

}
