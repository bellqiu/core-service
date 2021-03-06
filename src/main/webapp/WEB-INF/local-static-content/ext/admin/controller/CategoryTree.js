Ext
		.define(
				'AM.controller.CategoryTree',
				{
					extend : 'Ext.app.Controller',
					views : [ 'category.TreeGrid', 'category.Edit' ],

					models : [ 'CategoryTree' ],

					stores : [ 'CategoryTree', 'CategoryType' ],

					init : function() {
						this.control({
							'categorytreegrid toolbar button#newCategory' : {
								click : this.newCategory
							},
							
							'categorytreegrid toolbar button#synchronize' : {
								click : this.synchronize
							},
							'categorytreegrid toolbar button#refresh' : {
								click : this.refreshCategoryGrid
							},
							'categoryeditor form' : {
								beforeaction : this.beforeaction,
								actioncomplete : this.actioncomplete,
								actionfailed : this.actionfailed
							},
							'categoryeditor button#saveCategory' : {
								click : this.saveCategory
							},
							'categorytreegrid actioncolumn' : {
								click : this.actionCategoryDetail
							}

						});
					},
					
					synchronize : function(btn){
						btn.up('categorytreegrid').getStore().sync();
					},
					
					refreshCategoryGrid : function(btn) {
						var queryPanel = btn.up("viewport").down(
								"tabpanel#managerContainer categorytreegrid")
								.getStore().load();
					},

					actionCategoryDetail : function(view, cell, row, col, e) {
						var m = e.getTarget().src
								.match(/.*\/images\/(\w+)\.\w+\b/);
						if (m) {
							view.getSelectionModel()
									.select(row, false)
							switch (m[1]) {
							case 'edit':
								var contentPanel = view.up("viewport").down(
								"tabpanel#mainContainer");
								
								var editor = Ext.create("AM.view.category.Edit", {
									title : 'Edit Category'
								});

								contentPanel.insert(0, editor);
								contentPanel.setActiveTab(0);

								var form = editor.down("form");
								
								form.load({
									// pass 2 arguments to server side getBasicInfo
									// method (len=2)
									params : {
										id : view.getSelectionModel().getSelection()[0].get('id'),
										parent : 0
									},
									success : function (form, action){
										editor.setTitle("C-" + action.result.data.name);
									}
									
								})
								break;
							case 'delete':
								Ext.MessageBox.confirm('Delete', 'Are you sure ?', function(btn){
								   if(btn === 'yes'){
									   view.getTreeStore().getNodeById(view.getSelectionModel().getSelection()[0].get('id')).remove();
									   view.getTreeStore().sync();
								   }
								   else{
									   Ext.example.msg('Cancel', 'Delete canceled');
								   }
								 });
								
								
								break;
							}
						}
					},

					saveCategory : function(btn) {
						btn.up("categoryeditor")
								.down("form")
								.getForm()
								.submit(
										{

											success : function(form, action) {
												form.setValues(action.result.resultForm);
												btn.up("categoryeditor").setTitle("C-" + action.result.resultForm.name);
											},
										});
					},

					actioncomplete : function(form) {
						form.findField("id").up("categoryeditor").setLoading(
								false);
					},

					actionfailed : function(form, action) {
						form.findField("id").up("categoryeditor").setLoading(
								false);
						Ext.example.msg('<font color="red">Error</font>',
								'<font color="red">' + action.type
										+ " Failed </font>");
					},

					beforeaction : function(form) {
						form.findField("id").up("categoryeditor").setLoading(
								true, true);
					},

					newCategory : function(btn) {
						var contentPanel = btn.up("viewport").down(
								"tabpanel#mainContainer");
						var queryPanel = btn.up("viewport").down(
								"tabpanel#managerContainer categorytreegrid");

						var selected = [];
						var parentId = 0;

						if (queryPanel) {
							selected = queryPanel.getSelectionModel()
									.getSelection();
						}
						if (selected.length > 0) {
							parentId = selected[0].get("id");
						}

						var editor = Ext.create("AM.view.category.Edit", {
							title : 'New Category'
						});

						contentPanel.insert(0, editor);
						contentPanel.setActiveTab(0);

						var form = editor.down("form");

						form.load({
							// pass 2 arguments to server side getBasicInfo
							// method (len=2)
							params : {
								id : 0,
								parent : parentId
							}
						})
					}

				});