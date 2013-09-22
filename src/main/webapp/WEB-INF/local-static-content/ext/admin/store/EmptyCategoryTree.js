Ext.define('AM.store.EmptyCategoryTree', {
	extend : 'Ext.data.Store',
	model : 'AM.model.CategoryTree',
	data : [],
	proxy : {
		type : 'memory'
	}
});