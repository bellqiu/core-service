Ext.define('AM.view.order.OrderDetail', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.ordereditor',
	title : 'Manager Order',
	config : {
		order : {}
	},
	layout : 'fit',
	requires : [ 'Ext.form.*' ],
	closable : true,
	border : 0,
	padding : 5,
	layout : 'fit',
	defaults : {
		border : 0
	},
	closable : true,
	
	initComponent : function() {
		
		this.items = [ {
			border : 0,
			xtype : 'form',
			itemId : 'orderDetailForm',
			paramsAsHash: true,
			api : {
				// The server-side method to call for load() requests
				load : 'orderDirectService.loadOrderDetail',
			},
			layout : 'vbox',
			fieldDefaults : {
				labelAlign : 'left',
				msgTarget : 'side'
			},
			defaults : {
				width : '100%',
				align : 'stretch'
			},

			items : [ {
				xtype : 'fieldset',
				title : 'Basic Info',
				layout : 'hbox',
				flex : 2,
				overflowY : 'auto',
				items : [ {
					xtype : 'container',
					flex : 1,
					border : false,
					layout : 'anchor',
					defaultType : 'textfield',
					items : [ {
						fieldLabel : 'Order SN',
						allowBlank : false,
						name : 'orderSN',
						anchor : '90%',
						disabled : true
					}, {
						fieldLabel : 'Shipping Address',
						name : 'shippingAddress',
						anchor : '90%',
						disabled : true
					}, {
						fieldLabel : 'Billing Address',
						name : 'billingAddress',
						anchor : '90%',
						disabled : true
					}, {
						fieldLabel : 'Amount',
						name : 'grandTotal',
						anchor : '90%',
						disabled : true
					}, {
						fieldLabel : 'Delivery Price',
						name : 'deliveryPrice',
						anchor : '90%',
						disabled : true
					}, {
						fieldLabel : 'User Email',
						name : 'useremail',
						anchor : '90%',
						disabled : true
					}, {
						fieldLabel : 'Coupon Cut Off',
						name : 'couponCutOff',
						anchor : '90%',
						disabled : true
					}, {
						fieldLabel : 'Currency',
						name : 'currency',
						anchor : '90%',
						disabled : true
					}, {
						fieldLabel : 'Shipping Method',
						name : 'shippingMethod',
						anchor : '90%',
						disabled : true
					}, {
						fieldLabel : 'Customer Message',
						name : 'customerMsg',
						anchor : '90%',
						disabled : true
					}, {
						fieldLabel : 'Tracking Id',
						name : 'trackingId',
						anchor : '90%',
						disabled : true
					}, {
						fieldLabel : 'Source Id',
						name : 'sourceId',
						anchor : '90%',
						disabled : true
					}]
				} ]
			}, {
				xtype : 'fieldset',
				title : 'Option Info',
				layout : 'hbox',
				height : '110',
				items : [ {
					xtype : 'container',
					flex : 1,
					border : false,
					layout : 'anchor',
					defaultType : 'textfield',
					items : [ {
						fieldLabel : 'Order Status',
						name : 'orderStatus',
						itemId : 'orderStatus',
						xtype : 'combo',
						displayField : 'name',
						valueField : 'status',
						typeAhead : true,
						triggerAction : 'all',
						store : 'OrderType',
						allowBlank : false
					}, {
						fieldLabel : 'traceInfo',
						name : 'traceInfo',
						itemId : 'traceInfo',
						xtype : 'textfield',
						anchor : '90%'
					}, {
						xtype : 'button',
						text : 'Save',
						itemId : 'saveOrder',
						flex : 1
					}]
				} ]
			}, {
				xtype : 'fieldset',
				title : 'Product Info',
				itemId : 'productInfo',
				layout : 'vbox',
				defaults : {
					width : '100%',
					align : 'stretch'
				},
				flex : 2,
				overflowY : 'auto',
				items : [/*{
					
					xtype : 'orderproductinfo',
				}, {
					xtype : 'orderproductinfo'
				}, {
					xtype : 'orderproductinfo'
				}, {
					xtype : 'orderproductinfo'
				}, {
					xtype : 'orderproductinfo'
				}, {
					xtype : 'orderproductinfo'
				}, {
					xtype : 'orderproductinfo'
				}, {
					xtype : 'orderproductinfo'
				}, {
					xtype : 'orderproductinfo'
				}, {
					xtype : 'orderproductinfo'
				}, {
					xtype : 'orderproductinfo'
				}, {
					xtype : 'orderproductinfo'
				}, {
					xtype : 'orderproductinfo'
				}*/]
			} ]
		} ];
		
		this.callParent(arguments);
	}
});