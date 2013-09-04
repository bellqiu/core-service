Ext.define('AM.view.product.Edit', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.producteditor',
	layout : 'fit',
	defaults : {
		border : 0,
		padding : 3
	},
	closable : true,
	items : [{
		border : 0,
		xtype : 'form',
		html : 'test'
	}]
});