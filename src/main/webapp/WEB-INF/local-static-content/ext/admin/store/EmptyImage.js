Ext.define('AM.store.EmptyImage', {
	extend : 'Ext.data.Store',
	model : 'AM.model.Image',
	data : [],
	buffered : false,
	proxy : {
		type : 'memory'
	}
});