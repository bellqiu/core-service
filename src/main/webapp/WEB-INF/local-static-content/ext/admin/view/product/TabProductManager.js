Ext.define('AM.view.product.TabProductManager', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.tabproductmanager',
	title : 'Manager Tab Product',
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
				id : 'searchTabProductForm',
				items : [ {
					xtype : 'fieldset',
					scroll : true,
					margin : '10',
					padding : 5,
					height : 70,
					title : 'Tab Product filters',
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
						xtype : 'panel',
						border : 0,
						defaults : {
							border : 0,
							margin : 2
						},
						items : [ {
							xtype : 'button',
							text : 'Search',
							id : 'searchTabProduct'
						}, {
							xtype : 'button',
							text : 'Reset',
							id : 'resetTabProduct'
						} ]
					} ]

				} ]
			},

			{
				flex : 1,
				xtype : 'gridpanel',
				border : 0,
				store : 'TabProduct',
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
					xtype : 'actioncolumn',
					width : 50,
					sortable : false,
					menuDisabled : true,
					items : [ {
						icon : '/resources/ext/resources/images/edit.png',
						tooltip : 'Edit Tab Product',
						id : 'editTabProduct'

					}, {
						icon : '/resources/ext/resources/images/delete.gif',
						tooltip : 'Delete Tab Product',
						id : 'deleteTabProduct'
					} ]
				}],
				bbar : {
					xtype : 'pagingtoolbar',
					pageSize : 30,
					store : 'TabProduct',
					displayInfo : true,
					plugins : new Ext.ux.ProgressBarPager()
				},
				dockedItems : [ {
					xtype : 'toolbar',
					items : [ {
						text : 'New',
						itemId : 'newTabProduct'
					}, {
						text : 'Synchronize',
						itemId : 'synchronize'
					}]
				} ]

			} ]
		} ];
		this.callParent(arguments);
	},
});