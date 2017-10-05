#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
#set( $CLASSNAME = $className.toUpperCase() )
#set( $classname = $className.toLowerCase() )
#set( $ClassName = $className.substring(0,1).toUpperCase() + $className.substring(1) )
#set( $className = $className.substring(0,1).toLowerCase() + $className.substring(1) )
'use strict';

define('${artifactId}', [
	'fujion-core', 
	'fujion-widget',
	'${artifactId}-css'
	], 
	
	function(fujion, Widget) { 
	
	var wclass = Widget.UIWidget.extend({
	
		/*------------------------------ Containment ------------------------------*/

		
		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
		},
		
		/*------------------------------ Other ------------------------------*/
	
		
		/*------------------------------ Rendering ------------------------------*/
		
		render$: function() {
			return $('<label>${displayName}</label>');
		},
		
		/*------------------------------ State ------------------------------*/
		
	});

	return Widget.addon('${package}', '${ClassName}', wclass);
});