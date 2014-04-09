Ext.define('AM.controller.HTML', {
	extend : 'Ext.app.Controller',
	views : [ 'html.HtmlManager', 'html.HtmlDetail' ],

	models : [ 'HTML' ],

	stores : [ 'HTML' ],

	init : function() {
		this.control({
			'htmlmanager button#searchHtml' : {
				click : this.searchHTML
			},
			'htmlmanager button#resetHtml' : {
				click : function(btn) {
					btn.up("form").getForm().clearInvalid();
					btn.up("form").getForm().reset();
				}
			},
			'htmlmanager button#synchronize' : {
				click : this.synchronizeGrid
			},

			'htmlmanager button#newHTML' : {
				click : this.newHTML
			},
			'htmlmanager gridpanel actioncolumn' : {
				click : this.actionHtmlDetail
			},
			'htmlmanager toolbar button#refreshHTML' : {
				click : this.refreshHtmlManager
			},
			
			'html_editor button#saveHTML' : {
				click : this.saveHTML
			},
			
			'html_editor form' : {
				beforeaction : this.beforeaction,
				actioncomplete : this.actioncomplete,
				actionfailed : this.actionfailed
			}

		});
	},

	deleteHTML : function(grid, el, index) {

		Ext.MessageBox.confirm('Delete', 'Are you sure ?', function(btn) {
			if (btn === 'yes') {
				grid.getStore().removeAt(index);
				grid.getStore().sync();
			} else {
				Ext.example.msg('Cancel', 'Delete canceled');
			}
		});

	},
	
	actioncomplete : function(form) {
		form.findField("id").up("html_editor").setLoading(
				false);
	},

	actionfailed : function(form, action) {
		form.findField("id").up("html_editor").setLoading(
				false);
		Ext.example.msg('<font color="red">Error</font>',
				'<font color="red">' + action.type
						+ " Failed </font>");
	},

	beforeaction : function(form) {
		form.findField("id").up("html_editor").setLoading(
				true, true);
	},

	newHTML : function(btn) {
		/*var store = btn.up('gridpanel').getStore();

		var rec = new AM.model.HTML({
			name : '',
			content : '',
			status : 'ACTIVE',
			createDate : Ext.Date.clearTime(new Date()),
			updateDate : Ext.Date.clearTime(new Date())
		});
		store.insert(0, rec);
		btn.up('gridpanel').getPlugin().startEditByPosition({
			row : 0,
			column : 0
		});*/
		var contentPanel = btn.up("viewport").down(
		"tabpanel#mainContainer");

		var editor = Ext.create("AM.view.html.HtmlDetail", {
			title : 'Edit HTML'
		});

		contentPanel.insert(0, editor);
		contentPanel.setActiveTab(0);

		var form = editor.down("form");
		form.load({
			params : {
				id : 0
			}
		})
	},

	synchronizeGrid : function(btn) {

		btn.up('gridpanel').getStore().sync();
	},

	searchHTML : function(btn) {
		if (btn.up('form').getForm().isValid()) {
			// Ext.MessageBox.alert('Thank you!', 'Your inquiry has been
			// sent.
			// We will respond as soon as possible.');
			store = this.getHTMLStore();
			filters = btn.up('form#searchHTMLForm').getForm().getValues();
			var filterObj = [];
			var filtered = false;
			if (filters.name.length > 0) {
				filterObj.push({
					property : 'name',
					value : filters.name
				});
				filtered = true;
			}

			if (filtered) {
				store.clearFilter(true);
				store.filter(filterObj);
			} else {
				store.clearFilter(true);
				store.load(1);
			}
		}
	},

	refreshHtmlManager : function(btn) {
		/*if (btn.up('form').getForm().isValid()) {
			
		}*/
		var queryPanel = btn.up("viewport").down(
		"tabpanel#managerContainer htmlmanager");
		this.getHTMLStore().load();
	},

	actionHtmlDetail : function(view, cell, row, col, e) {
		var m = e.getTarget().src.match(/.*\/images\/(\w+)\.\w+\b/);
		if (m) {
			view.getSelectionModel().select(row, false);
			switch (m[1]) {
			case 'edit':
				var contentPanel = view.up("viewport").down(
						"tabpanel#mainContainer");

				var editor = Ext.create("AM.view.html.HtmlDetail", {
					//title : 'Edit HTML'
				});

				contentPanel.insert(0, editor);
				contentPanel.setActiveTab(0);

				var form = editor.down("form");

				form.load({
					// pass 1 argument to server side getBasicInfo
					// method (len=1)
					params : {
						id : view.getSelectionModel().getSelection()[0]
								.get('id')
					},
					success : function (form, action){
						editor.setTitle("H-" + action.result.data.name);
					}
				})
				break;
			case 'delete':
				Ext.MessageBox.confirm('Delete', 'Are you sure ?',
						function(btn) {
							if (btn === 'yes') {
								view.getStore().remove(view.getSelectionModel()
														.getSelection()[0]);
								view.getStore().sync();
							} else {
								Ext.example.msg('Cancel', 'Delete canceled');
							}
						});

				break;
			}
		}
	},

	saveHTML : function(btn) {
		btn.up("html_editor").down("form").getForm().submit({

			success : function(form, action) {
				form.setValues(action.result.resultForm);
				btn.up("html_editor").setTitle("H-" + action.result.resultForm.name);
			},
		});
	}
});