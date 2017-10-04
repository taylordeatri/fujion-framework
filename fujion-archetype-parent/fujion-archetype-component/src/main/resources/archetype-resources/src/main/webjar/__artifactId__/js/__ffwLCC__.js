'use strict';

define('${ffwUCC}', [
	'fujion-core', 
	'fujion-widget'
	], 
	
	function(fujion, Widget) { 
	
	Widget.${ffwUCC} = Widget.UIWidget.extend({
	
		/*------------------------------ Containment ------------------------------*/

		
		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
		},
		
		/*------------------------------ Other ------------------------------*/
	
		
		/*------------------------------ Rendering ------------------------------*/
		
		render$: function() {
			return $('<div/>');
		},
		
		/*------------------------------ State ------------------------------*/
		
	});

	return fujion.widget;
});