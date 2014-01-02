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
		var cateogryNameStore = Ext.create("Ext.data.ArrayStore", {
	    	storeId: 'cateogryNameStore',
	    	fields: [
	    	         {name:'name'}
	    	],
	    	data : [],
	    	load : function( store, records, successful, eOpts ) {
	    	}
			//buffered : false,
		});
		this.items = [ {
			xtype : 'panel',
			layout : 'fit',
			id : 'productPanel',
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
				api : {
					load : 'productDirectService.loadProductCategory',
				},
				items : [ {
					xtype : 'fieldset',
					scroll : true,
					margin : '10',
					padding : 5,
					height : 200,
					title : 'Product Summary filters',
					layout : {
						type : 'table',
						columns : 2,
					},
					defaultType : 'textfield',
					items : [ {
						margin : 5,
						fieldLabel : 'ID',
						name : 'id',
						regex : /^\d+$/,
						regexText : 'Only for number',
						width : 350
					}, {
						margin : 5,
						fieldLabel : 'URL',
						name : 'name',
						width : 350

					}, {
						margin : 5,
						fieldLabel : 'Title',
						name : 'title',
						width : 350
					}, {
						xtype: 'numberfield',
						margin : 5,
						fieldLabel : 'Price',
						name : 'price',
						width : 350
					}, {
						xtype: 'numberfield',
						margin : 5,
						fieldLabel : 'Actual Price',
						name : 'actualPrice',
						width : 350
					}, {
						margin : 5,
						fieldLabel : 'SKU',
						name : 'sku',
						width : 350
					}, {
						margin : 5,
						fieldLabel : 'Category',
						xtype : 'combo',
						stateful : true,
						store : 'cateogryNameStore',
						name : 'categoryName',
						displayField : 'name',
						valueField : 'name',
						width : 350
					}, {
						xtype: 'checkbox',
						margin : 5,
						fieldLabel : 'Active Product',
						name : 'active',
						checked : 'true',
						width : 350
					}, {
						margin : 5,
						xtype : 'panel',
						border : 0,
						defaults : {
							border : 0,
							margin : 2
						},
						items : [ {
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
					text : "URL",
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
					text : "SKU",
					dataIndex : "sku",
					flex : 1,
					editor : {
						xtype : 'textfield',
						allowBlank : true
					}
				}, {
					text : "Breadcrumb",
					dataIndex : "categoryBreadcrumb",
					flex : 2,
				}, {
					text : "Create Date",
					dataIndex : "createDate",
					type : 'date',
					renderer : Ext.util.Format.dateRenderer('m/d/Y H:i:s'),
					flex : 1.5
				}, {
					xtype : 'actioncolumn',
					width : 60,
					sortable : false,
					menuDisabled : true,
					items : [ {
						icon : '/resources/ext/resources/images/pop.png',
						tooltip : 'View Product',
						id : 'viewProduct'
					}, {
						icon : '/resources/ext/resources/images/edit.png',
						tooltip : 'Edit Product',
						id : 'editProduct'
					}, {
						icon : '/resources/ext/resources/images/delete.gif',
						tooltip : 'Delete Product',
						id : 'deleteProduct'
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