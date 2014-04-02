Ext.define('AM.controller.Blogger', {
	extend : 'Ext.app.Controller',
	views : [ 'blogger.BloggerManager'],

	models : [ 'Blogger' ],

	stores : [ 'Blogger', 'BloggerType' ],

	init : function() {
		this.control({
			'bloggermanager button#searchBlogger' : {
				click : this.searchBlogger
			},
			'bloggermanager button#resetBlogger' : {
				click : function(btn) {
					btn.up("form").getForm().clearInvalid();
					btn.up("form").getForm().reset();
				}
			},
			'bloggermanager button#synchronize' : {
				click : this.synchronizeGrid
			},

			'bloggermanager button#uploadBlogger' : {
				click : this.uploadBlogger
			},
			'bloggermanager gridpanel actioncolumn' : {
				click : this.actionBloggerDetail
			},
			'bloggermanager toolbar button#refreshBlogger' : {
				click : this.refreshBloggerManager
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

	uploadBlogger : function(btn) {
		btn.uploader.removeAll();
	},

	synchronizeGrid : function(btn) {

		btn.up('gridpanel').getStore().sync();
	},

	searchBlogger : function(btn) {
		if (btn.up('form').getForm().isValid()) {
			// Ext.MessageBox.alert('Thank you!', 'Your inquiry has been
			// sent.
			// We will respond as soon as possible.');
			store = this.getBloggerStore();
			filters = btn.up('form#searchBloggerForm').getForm().getValues();
			var filterObj = [];
			var filtered = false;
			if (filters.name.length > 0) {
				filterObj.push({
					property : 'name',
					value : filters.name
				});
				filtered = true;
			}
			if (filters.description.length > 0) {
				filterObj.push({
					property : 'description',
					value : filters.description
				});
				filtered = true;
			}
			if (filters.type.length > 0) {
				filterObj.push({
					property : 'type',
					value : filters.type
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

	refreshBloggerManager : function(btn) {
		var queryPanel = btn.up("viewport").down(
		"tabpanel#managerContainer bloggermanager");
		this.getBloggerStore().load();
	},

	actionBloggerDetail : function(view, cell, row, col, e) {
		var m = e.getTarget().src.match(/.*\/images\/(\w+)\.\w+\b/);
		if (m) {
			view.getSelectionModel().select(row, false)
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
	}

});