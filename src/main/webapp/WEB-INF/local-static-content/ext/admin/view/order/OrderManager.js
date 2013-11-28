Ext.define('AM.view.order.OrderManager', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.ordermanager',
	title : 'Manager Order',
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
				id : 'searchOrderForm',
				items : [ {
					xtype : 'fieldset',
					scroll : true,
					margin : '10',
					padding : 5,
					height : 150,
					title : 'Order filters',
					layout : {
						type : 'table',
						columns : 2
					},
					defaultType : 'textfield',
					items : [ {
						margin : 5,
						fieldLabel : 'OrderSN',
						name : 'orderSN',
						regex : /^\w+$/,
						regexText : 'Only for characters',
						width : 250
					}, {
						margin : 5,
						fieldLabel : 'Status',
						xtype : 'combo',
						store : 'OrderType',
						name : 'status',
						displayField : 'name',
						valueField : 'status',
						width : 250
					}, {
						margin : 5,
						fieldLabel : 'Email',
						name : 'useremail',
						width : 250
					}, {
						margin : 5,
						fieldLabel : 'Tracking Id',
						name : 'trackingId',
						width : 250
					}, {
						margin : 5,
						fieldLabel : 'Source Id',
						name : 'sourceId',
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
							id : 'searchOrder',
							flex : 1
						}, {
							xtype : 'button',
							text : 'Reset',
							id : 'resetOrder',
							flex : 1
						} ]
					}]

				} ]
			},

			{
				flex : 1,
				xtype : 'gridpanel',
				border : 0,
				store : 'OrderSummary',
				plugins : [ {
					ptype : 'cellediting',
					clicksToEdit : 2,
					id : 'cellEditor'
				} ],
				selModel : {
					selType : 'cellmodel'
				},
				columns : [ {
					text : "OrderSN",
					dataIndex : "orderSN",
					flex : 1
				}, {
					text : "Status",
					dataIndex : "status",
					flex : 1
				}, {
					text : "Email",
					dataIndex : "useremail",
					flex : 1.5
				}, {
					text : "Tracking Id",
					dataIndex : "trackingId",
					flex : 1.5
				}, {
					text : "Source Id",
					dataIndex : "sourceId",
					flex : 1.5
				}, {
					text : "Amount",
					dataIndex : "amount",
					flex : 1.5
				}, {
					xtype : 'actioncolumn',
					width : 20,
					sortable : false,
					menuDisabled : true,
					items : [ {
						icon : '/resources/ext/resources/images/edit.png',
						tooltip : 'Edit Order',
						id : 'editOrder'

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
					store : 'OrderSummary',
					displayInfo : true,
					plugins : new Ext.ux.ProgressBarPager()
				},
				dockedItems : [ {
					xtype : 'toolbar',
					items : [{
						text : 'Refresh',
						itemId : 'refreshOrder'
					} ]
				} ]

			} ]
		} ];
		this.callParent(arguments);
	},
});