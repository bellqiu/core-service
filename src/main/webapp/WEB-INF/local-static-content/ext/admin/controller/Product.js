Ext.define('AM.controller.Product', {
	extend : 'Ext.app.Controller',
	views : [ 'product.Edit' ],

	/*
	 * models : [ 'Setting' ],
	 * 
	 * stores : [ 'Setting', 'SettingType' ],
	 */

	init : function() {
		this.control({
			'globalnav button#newproduct' : {
				click : this.newProduct
			}

		});
	},

	newProduct : function(btn) {
		var contentPanel = btn.up("viewport").down("tabpanel#mainContainer");

		var editor = Ext.create("AM.view.product.Edit", {
			title : 'New Product'
		});

		contentPanel.insert(0, editor);
		contentPanel.setActiveTab(0);
	}
});