Ext.define('AM.view.product.ProductSummaryManager', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.productsummarymanager',
	title : 'Manager Product',
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
				id : 'searchProductSummaryForm',
				items : [ {
					xtype : 'fieldset',
					scroll : true,
					margin : '10',
					padding : 5,
					height : 150,
					title : 'Product Summary filters',
					layout : {
						type : 'table',
						columns : 2
					},
					defaultType : 'textfield',
					items : [ {
						margin : 5,
						fieldLabel : 'Name',
						name : 'name',
						regex : /^\w+$/,
						regexText : 'Only for characters',
						width : 250

					}, {
						margin : 5,
						fieldLabel : 'Title',
						name : 'title',
						width : 250
					}, {
						xtype: 'numberfield',
						margin : 5,
						fieldLabel : 'Price',
						name : 'price',
						width : 250
					}, {
						xtype: 'numberfield',
						margin : 5,
						fieldLabel : 'Actual Price',
						name : 'actualPrice',
						width : 250
					}, {
						xtype: 'checkbox',
						margin : 5,
						fieldLabel : 'Active Product',
						name : 'active',
						checked : 'true',
						width : 250
					}, /*{
						margin : 5,
						xtype : 'panel',
						border : 0,
						colspan : 2,
						layout : {
							type : 'hbox',
							align : 'middle',
							pack : 'center'
						},
						defaults : {
							border : 0,
							margin : 2
						},
						items : [ {
							flex : 2
						}, {
							flex : 2,
							defaults : {
								border : 0,
								margin : 2
							},
							items : [ {
								xtype : 'button',
								text : 'Search',
								id : 'searchProductSummary',
								flex : 1
							}, {
								xtype : 'button',
								text : 'reset',
								id : 'resetProductSummary',
								flex : 1
							} ]

						} ]
					},*/{
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
							id : 'searchProductSummary',
							flex : 1
						}, {
							xtype : 'button',
							text : 'reset',
							id : 'resetProductSummary',
							flex : 1
						} ]
					} ]

				} ]
			},

			{
				flex : 1,
				xtype : 'gridpanel',
				border : 0,
				store : 'ProductSummary',
				viewConfig: {
	                plugins: {
	                    ddGroup: 'grid-to-edit-parent-productsummary',
	                    ptype: 'gridviewdragdrop',
	                    enableDrop: false
	                }
	            },
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
					text : "Title",
					dataIndex : "title",
					flex : 2,
					editor : {
						xtype : 'textfield',
						allowBlank : false
					}
				}, {
					text : "Price",
					dataIndex : "price",
					flex : 1,
					editor : {
						xtype : 'numberfield',
						allowBlank : false
					}
				}, {
					text : "Actual Price",
					dataIndex : "actualPrice",
					flex : 2,
					editor : {
						xtype : 'numberfield',
						allowBlank : false
					}
				}, {
					text : "Breadcrumb",
					dataIndex : "categoryBreadcrumb",
					flex : 2,
				}, {
					xtype : 'actioncolumn',
					width : 30,
					sortable : false,
					menuDisabled : true,
					items : [ {
						icon : '/resources/ext/resources/images/edit.png',
						tooltip : 'Edit Product',
						id : 'editProduct'
					}, {
						icon : '/resources/ext/resources/images/delete.gif',
						tooltip : 'Delete Product',
						id : 'deleteProduct'
					}]
				}],
				bbar : {
					xtype : 'pagingtoolbar',
					pageSize : 30,
					store : 'ProductSummary',
					displayInfo : true,
					plugins : new Ext.ux.ProgressBarPager()
				},
				dockedItems : [ {
					xtype : 'toolbar',
					items : [ {
						text : 'Synchronize',
						itemId : 'synchronize'
					}, {
						text : 'Refresh',
						itemId : 'refreshProductSummary'
					} ]
				} ]

			} ]
		} ];
		this.callParent(arguments);
	},
});