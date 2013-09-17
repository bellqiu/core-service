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
		var form = editor.down("form");

		contentPanel.insert(0, editor);
		contentPanel.setActiveTab(0);
		
		form.load({
			// pass 2 arguments to server side getBasicInfo
			// method (len=2)
			params : {
				id : 2
			}
		})
	}
});