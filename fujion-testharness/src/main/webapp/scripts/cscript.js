System.import('fujion-core').then(function(fujion) {
	var msg = 'External client script was executed.';
	fujion.event.sendToServer({type: 'log', data: msg});
	console.log(msg);
});