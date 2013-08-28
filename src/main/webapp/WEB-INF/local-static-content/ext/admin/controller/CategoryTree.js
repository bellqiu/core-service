Ext.define('AM.controller.CategoryTree', {
	extend : 'Ext.app.Controller',
	views : [ 'category.TreeGrid', 'category.Edit' ],

	models : [ 'CategoryTree' ],

	stores : [ 'CategoryTree', 'CategoryType' ],

	init : function() {
		this.control({
			'categorytreegrid toolbar button#newCategory' : {
				click : this.newCategory
			},
			'categoryeditor form' : {
				beforeaction : this.beforeaction,
				actioncomplete : this.actioncomplete,
				actionfailed : this.actionfailed
			},'categoryeditor button#saveCategory' : {
				click : this.saveCategory
			}
			
		});
	},
	
	saveCategory : function (btn){
		btn.up("categoryeditor").down("form").getForm().submit();
	},
	
	actioncomplete : function(form){
		form.findField("id").up("categoryeditor").setLoading(false);
	},
	
	actionfailed : function(form, action){
		form.findField("id").up("categoryeditor").setLoading(false);
		Ext.example.msg('<font color="red">Error</font>', '<font color="red">' + action.type + " Failed </font>");
	},
	
	beforeaction : function(form){
		form.findField("id").up("categoryeditor").setLoading(true, true);
	},
	
	newCategory : function(btn){
		var contentPanel = btn.up("viewport").down("tabpanel#mainContainer");
		var queryPanel =btn.up("viewport").down("tabpanel#managerContainer categorytreegrid");

		var selected = [];
		var parentId = 0;

		if(queryPanel){
			selected = queryPanel.getSelectionModel().getSelection();
		}
		if(selected.length > 0){
			parentId = selected[0].get("id");
		}
		
		var editor = Ext.create("AM.view.category.Edit",{
			title : 'New Category'
		});
		
		contentPanel.insert(0,editor);
		contentPanel.setActiveTab(0);
		
		var form = editor.down("form");

		form.load({
			// pass 2 arguments to server side getBasicInfo method (len=2)
			params: {
				id: 0,
				parent: parentId
			}
		})
	}

});