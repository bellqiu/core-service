Ext.define('AM.view.blogger.BloggerManager', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.bloggermanager',
	title : 'Manager Blog',
	layout : 'fit',
	requires : [ 'Ext.grid.*', 'Ext.util.*', 'Ext.form.*',
			'Ext.toolbar.Paging', 'Ext.ux.ProgressBarPager' ],
	closable : true,
	border : 0,
	initComponent : function() {
		this.items = [ {
			xtype : 'panel',
			layout : 'fit',
			border : 0,
			layout : {
				type : 'vbox',
				align : 'stretch',
				pack : 'start'
			},
			defaults : {

				validateOnChange : true
			},
			scroll : true,
			items : [ {
				xtype : 'form',
				id : 'searchBloggerForm',
				items : [ {
					xtype : 'fieldset',
					scroll : true,
					margin : '10',
					padding : 5,
					height : 100,
					title : 'Blog filters',
					layout : {
						type : 'table',
						columns : 2
					},
					defaultType : 'textfield',
					items : [ {
						margin : 5,
						fieldLabel : 'Name',
						name : 'name',
						width : 250

					}, {
						margin : 5,
						fieldLabel : 'Description',
						name : 'description',
						width : 250

					}, {
						margin : 5,
						fieldLabel : 'Type',
						xtype : 'combo',
						store : 'BloggerType',
						name : 'type',
						displayField : 'name',
						valueField : 'type',
						width : 250
					}, {
						margin : 5,
						xtype : 'panel',
						border : 0,
						defaults : {
							border : 0,
							margin : 2
						},
						items : [ {
							xtype : 'button',
							text : 'Search',
							id : 'searchBlogger',
							flex : 1
						}, {
							xtype : 'button',
							text : 'Reset',
							id : 'resetBlogger',
							flex : 1
						} ]
					} ]

				} ]
			},

			{
				flex : 1,
				xtype : 'gridpanel',
				border : 0,
				store : 'Blogger',
				plugins : [ {
					ptype : 'cellediting',
					clicksToEdit : 2,
					id : 'cellEditor'
				} ],
				selModel : {
					selType : 'cellmodel'
				},
				columns : [ {
					text : "Name",
					dataIndex : "name",
					flex : 1,
					editor : {
						xtype : 'textfield',
						allowBlank : false
					}
				}, {
					text : "Description",
					dataIndex : "description",
					flex : 2,
					editor : {
						xtype : 'textfield',
						allowBlank : false
					}
				}, {
					text : "Priority",
					dataIndex : "priority",
					flex : 1,
					editor : {
						xtype : 'textfield',
						allowBlank : false
					}
				}, {
					xtype : 'actioncolumn',
					width : 40,
					sortable : false,
					menuDisabled : true,
					items : [ {
						icon : '/resources/ext/resources/images/pop.png',
						tooltip : 'View Blogger',
						id : 'viewBlogger'
					}, {
						icon : '/resources/ext/resources/images/delete.gif',
						tooltip : 'Delete Blogger',
						id : 'deleteBlogger'
					}]
				}, {
					text : "",
					width : 10,
					sortable : false,
					menuDisabled : true,
				}],
				bbar : {
					xtype : 'pagingtoolbar',
					pageSize : 30,
					store : 'Blogger',
					displayInfo : true,
					plugins : new Ext.ux.ProgressBarPager()
				},
				dockedItems : [ {
					xtype : 'toolbar',
					items : [ {
						xtype: 'uploadbutton',
						text : 'Upload',
						itemId : 'uploadBlogger',
						//singleFile: true,
						plugins : [ {
							ptype : 'ux.upload.window',
							title : 'Upload',
							width : 520,
							height : 350
						} ],
						uploader : {
							url : '/admin/blog/upload',
							autoStart : false,
							max_file_size : '20mb',
							filters : [
							           	{title : "Upload files", extensions : "pdf,html,jsp,txt"}
							           ],
							statusQueuedText : 'Ready to upload',
							statusUploadingText : 'Uploading ({0}%)',
							statusFailedText : '<span style="color: red">Error</span>',
							statusDoneText : '<span style="color: green">Complete</span>',
					
							statusInvalidSizeText : 'File too large',
							statusInvalidExtensionText : 'Invalid file type'
						},
						listeners : {
							fileuploaded : function(uploader, file, resp) {
								if(resp.success){
									var store = uploader.owner.up("bloggermanager").down("gridpanel").getStore();
									store.add(resp.blogger);
								}
								console.log('fileuploaded');
							}
						}
					}, {
						text : 'Synchronize',
						itemId : 'synchronize'
					}, {
						text : 'Refresh',
						itemId : 'refreshBlogger'
					} ]
				} ]

			} ]
		} ];
		this.callParent(arguments);
	},
});