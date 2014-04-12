Ext.define('AM.controller.Blogger', {
	extend : 'Ext.app.Controller',
	views : [ 'blogger.BloggerManager'],

	models : [ 'Blogger' ],

	stores : [ 'Blogger', 'BloggerType' ],

	init : function() {
		this.control({
			'bloggermanager button#searchBlog' : {
				click : this.searchBlog
			},
			'bloggermanager button#resetBlog' : {
				click : function(btn) {
					btn.up("form").getForm().clearInvalid();
					btn.up("form").getForm().reset();
				}
			},
			'bloggermanager button#synchronize' : {
				click : this.synchronizeGrid
			},

			'bloggermanager button#uploadBlog' : {
				click : this.uploadBlog
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

	uploadBlog : function(btn) {
		btn.uploader.removeAll();
	},

	synchronizeGrid : function(btn) {

		btn.up('gridpanel').getStore().sync();
	},

	searchBlog : function(btn) {
		if (btn.up('form').getForm().isValid()) {
			// Ext.MessageBox.alert('Thank you!', 'Your inquiry has been
			// sent.
			// We will respond as soon as possible.');
			store = this.getBloggerStore();
			filters = btn.up('form#searchBlogForm').getForm().getValues();
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
			
			var actioncolumn = btn.up('panel#blogPanel').down('actioncolumn');
			active=btn.up('form#searchBlogForm').down('checkbox').getValue();
			if (active) {
				filterObj.push({
					property : 'active',
					value : 'true',
				});
				actioncolumn.items[1].tooltip = 'Delete Product';
				actioncolumn.items[1].icon = '/resources/ext/resources/images/delete.gif';
			} else {
				filterObj.push({
					property : 'active',
					value : 'false',
				});
				actioncolumn.items[1].tooltip = 'Recover Product';
				actioncolumn.items[1].icon = '/resources/ext/resources/images/recover.png';
				console.log("OK");
			}
			filtered = true;

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
			case 'pop':
				var win=window.open("/rs/blog/"+view.getSelectionModel().getSelection()[0].get("name"), '_blank');
				win.focus();
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
			case 'recover':
				store = view.getStore();
				var blogId = view.getSelectionModel().getSelection()[0].data.id;
				bloggerDirectService.setBlogAsActive(blogId, function(data, rs, suc){
					if(suc && data){
						view.getStore().remove(view.getSelectionModel()
								.getSelection()[0]);
					}else if(rs && rs.type == 'exception'){
						Ext.example.msg('<font color="red">Error</font>',
								'<font color="red">' + rs.message
								+ " </font>");3
					}
				});
				break;
			}
		}
	}

});