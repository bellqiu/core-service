Ext.define('AM.controller.CategoryTree', {
	extend : 'Ext.app.Controller',
	views : [ 'category.TreeGrid', 'category.Edit' ],

	models : [ 'CategoryTree' ],

	stores : [ 'CategoryTree' ],

	init : function() {
		this.control({
			'categorytreegrid toolbar button#newCategory' : {
				click : this.newCategory
			}
		});
	},
	
	newCategory : function(btn){
		var contentPanel = btn.up("viewport").down("tabpanel#mainContainer");
		var editor = Ext.create("AM.view.category.Edit",{
			title : 'New Category'
		});
		contentPanel.insert(0,editor);
		contentPanel.setActiveTab(0);
	}

});