Ext.define('AM.view.country.CountryManager', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.countrymanager',
	title : 'Manager Country',
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
				id : 'searchFormCountry',
				items : [ {
					xtype : 'fieldset',
					scroll : true,
					margin : '10',
					padding : 5,
					height : 200,
					title : 'Country filters',
					layout : {
						type : 'table',
						columns : 2
					},
					defaultType : 'textfield',
					items : [ {
						margin : 5,
						fieldLabel : 'Code',
						name : 'code',
						regex : /^\w+$/,
						regexText : 'Only for characters',
						width : 250

					}, {
						margin : 5,
						fieldLabel : 'Abbr Code',
						name : 'abbrCode',
						width : 250
					}, {
						xtype: 'numberfield',
						margin : 5,
						fieldLabel : 'Normal Price',
						name : 'normalDeliveryPrice',
						width : 250
					}, {
						xtype: 'numberfield',
						margin : 5,
						fieldLabel : 'Advance Price',
						name : 'advanceDeliveryPrice',
						width : 250
					}, {
						xtype: 'numberfield',
						margin : 5,
						fieldLabel : 'Free Price',
						name : 'freeDeliveryPrice',
						width : 250
					}, {
						xtype: 'numberfield',
						margin : 5,
						fieldLabel : 'Free Advance Price',
						name : 'freeAdvanceDeliveryPrice',
						width : 250
					}, {
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
						},
						{
							flex : 2,
							defaults : {
								border : 0,
								margin : 2
							},
							items : [{
								xtype : 'button',
								text : 'Search',
								id : 'searchCountry',
								flex : 1
							},
							{
								xtype : 'button',
								text : 'Reset',
								id : 'resetCountry',
								flex : 1
							}]
							
						}]
					} ]

				} ]
			},

			{
				flex : 1,
				xtype : 'gridpanel',
				border : 0,
				id : 'countryTable',
				store : 'Country',
				plugins : [ {
					ptype : 'cellediting',
					clicksToEdit : 2,
					id : 'cellEditor'
				} ],
				selModel : {
					selType : 'cellmodel'
				},
				columns : [ {
					text : 'Code',
					dataIndex : 'code',
					flex : 1,
					editor : {
						xtype : 'textfield',
						allowBlank : false
					}
				}, {
					text : 'Abbr Code',
					dataIndex : 'abbrCode',
					flex : 2,
					editor : {
						xtype : 'textfield',
						allowBlank : false
					}
				}, {
					text : 'Normal Price',
					dataIndex : 'normalDeliveryPrice',
					flex : 2,
					editor : {
						xtype : 'numberfield',
						allowBlank : false
					}
				}, {
					text : 'Advance Price',
					dataIndex : 'advanceDeliveryPrice',
					flex : 2,
					editor : {
						xtype : 'numberfield',
						allowBlank : false
					}
				}, {
					text : 'Free Price',
					dataIndex : 'freeDeliveryPrice',
					flex : 2,
					editor : {
						xtype : 'numberfield',
						allowBlank : false
					}
				}, {
					text : 'Free Advance Price',
					dataIndex : 'freeAdvanceDeliveryPrice',
					flex : 2,
					editor : {
						xtype : 'numberfield',
						allowBlank : false
					}
				}, {
					text : 'Update Date',
					dataIndex : 'updateDate',
					type : 'date',
					renderer : Ext.util.Format.dateRenderer('m/d/Y H:i:s'),
					flex : 1.5
				}, {
					text : 'Create Date',
					dataIndex : 'createDate',
					type : 'date',
					renderer : Ext.util.Format.dateRenderer('m/d/Y H:i:s'),
					flex : 1.5
				}, {
					xtype : 'actioncolumn',
					width : 20,
					sortable : false,
					menuDisabled : true,
					items : [ {
						icon : '/resources/ext/resources/images/delete.gif',
						tooltip : 'Delete Country'
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
					store : 'Country',
					displayInfo : true,
					plugins : new Ext.ux.ProgressBarPager()
				},
				dockedItems : [ {
					xtype : 'toolbar',
					items : [ {
						text : 'New',
						itemId : 'newCountry'
					}, {
						text : 'Synchronize',
						itemId : 'synchronize'
					} ]
				} ]

			} ]
		} ];
		this.callParent(arguments);
	},
});