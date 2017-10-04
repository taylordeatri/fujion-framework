#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.component.BaseComponent;

@Component(tag = "${ffwLC}", widgetModule = "${artifactId}", widgetClass = "${ffwUCC}", parentTag = "*")
public class ${ffwUCC}Component extends BaseComponent {

	private static final Log log = LogFactory.getLog(${ffwUCC}Component.class);

	public ${ffwUCC}Component() {
	}
	
    @Override
    protected void _initProps(Map<String, Object> props) {
        props.put("wclazz", "${artifactId}");
        super._initProps(props);
    }

}
