Ext.define('AM.view.coupon.CouponManager', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.couponmanager',
	title : 'Manager Coupon',
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
				id : 'searchCouponForm',
				items : [ {
					xtype : 'fieldset',
					scroll : true,
					margin : '10',
					padding : 5,
					height : 100,
					title : 'Coupon filters',
					layout : {
						type : 'table',
						columns : 2
					},
					defaultType : 'textfield',
					items : [ {
						margin : 5,
						fieldLabel : 'Code',
						name : 'code',
						width : 250

					}, {
						margin : 5,
						fieldLabel : 'Name',
						name : 'name',
						width : 250

					}, {
						xtype: 'numberfield',
						margin : 5,
						fieldLabel : 'Value',
						name : 'value',
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
							id : 'searchCoupon'
						}, {
							xtype : 'button',
							text : 'Reset',
							id : 'resetCoupon'
						} ]
					} ]

				} ]
			},

			{
				flex : 1,
				xtype : 'gridpanel',
				border : 0,
				store : 'Coupon',
				plugins : [ {
					ptype : 'cellediting',
					clicksToEdit : 2,
					id : 'cellEditor'
				} ],
				selModel : {
					selType : 'cellmodel'
				},
				columns : [ {
					text : "Code",
					dataIndex : "code",
					flex : 1,
					editor : {
						xtype : 'textfield',
						allowBlank : false
					}
				}, {
					text : "Name",
					dataIndex : "name",
					flex : 1,
					editor : {
						xtype : 'textfield',
						allowBlank : false
					}
				}, {
					text : "Value",
					dataIndex : "value",
					flex : 1,
					editor : {
						xtype : 'numberfield',
						allowBlank : false
					}
				}, {
					xtype : 'actioncolumn',
					width : 40,
					sortable : false,
					menuDisabled : true,
					items : [ {
						icon : '/resources/ext/resources/images/edit.png',
						tooltip : 'Edit Coupon',
						id : 'editCoupon'

					}, {
						icon : '/resources/ext/resources/images/delete.gif',
						tooltip : 'Delete Coupon',
						id : 'deleteCoupon'
					} ]
				}, {
					text : "",
					width : 10,
					sortable : false,
					menuDisabled : true,
				}],
				bbar : {
					xtype : 'pagingtoolbar',
					pageSize : 30,
					store : 'Coupon',
					displayInfo : true,
					plugins : new Ext.ux.ProgressBarPager()
				},
				dockedItems : [ {
					xtype : 'toolbar',
					items : [ {
						text : 'New',
						itemId : 'newCoupon'
					}, {
						text : 'Synchronize',
						itemId : 'synchronize'
					}, {
						text : 'Refresh',
						itemId : 'refreshCoupon'
					} ]
				} ]

			} ]
		} ];
		this.callParent(arguments);
	},
});