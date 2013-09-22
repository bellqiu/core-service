Ext.define('AM.store.EmptyImage', {
	extend : 'Ext.data.Store',
	model : 'AM.model.Image',
	data : [],
	proxy : {
		type : 'memory'
	}
});