Ext.define('AM.view.currency.CurrencyManager', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.currencymanager',
	title : 'Manager Currency',
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
				id : 'searchCurrencyForm',
				items : [ {
					xtype : 'fieldset',
					scroll : true,
					margin : '10',
					padding : 5,
					height : 100,
					title : 'Currency filters',
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
						fieldLabel : 'Code',
						name : 'code',
						width : 250
					}, {
						xtype: 'numberfield',
						margin : 5,
						fieldLabel : 'Rate On Default',
						name : 'exchangeRateBaseOnDefault',
						minValue : 0,
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
							id : 'searchCurrency'
						}, {
							xtype : 'button',
							text : 'Reset',
							id : 'resetCurrency'
						} ]
					} ]

				} ]
			},

			{
				flex : 1,
				xtype : 'gridpanel',
				border : 0,
				store : 'Currency',
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
					text : "code",
					dataIndex : "code",
					flex : 1,
					editor : {
						xtype : 'textfield',
						allowBlank : false
					}
				}, {
					text : 'Rate On Default',
					dataIndex : 'exchangeRateBaseOnDefault',
					flex : 2,
					editor : {
						xtype : 'numberfield',
						allowBlank : false
					}
				}, {
					text : 'Default Currency',
					dataIndex : 'defaultCurrency',
					flex : 2,
					id : 'defaultCurrencyId',
					type : 'boolean',
					/*renderer : function(value) {
			            return "<input type='radio' name = 'defaultCurrencyRadio' onchange='setValue'" + (value ? "checked='checked'" : "") + ">";
					},*/
					renderer: function(value, metaData, record, rowIdx, colIdx, store) {
						return '<input '+(value?'checked=checked':'')+' onchange="setDefault('+rowIdx+',\''+store.storeId+'\');" type=radio name="rgrp'+this.body.id+'"/>'
					}
				}, {
					text : "Update Date",
					dataIndex : "updateDate",
					type : 'date',
					renderer : Ext.util.Format.dateRenderer('m/d/Y H:i:s'),
					flex : 1.5
				}, {
					text : "Create Date",
					dataIndex : "createDate",
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
						tooltip : 'Delete Currency'
					} ]
				}, {
					text : "",
					width : 10,
					sortable : false,
					menuDisabled : true,
				} ],
				bbar : {
					xtype : 'pagingtoolbar',
					pageSize : 30,
					store : 'Currency',
					displayInfo : true,
					plugins : new Ext.ux.ProgressBarPager()
				},
				dockedItems : [ {
					xtype : 'toolbar',
					items : [ {
						text : 'New',
						itemId : 'newCurrency'
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
function setDefault(rowIdx,storeId)
{
	for(index in Ext.getStore(storeId).data.items)
	{
		if(Ext.getStore(storeId).data.items[index].data.defaultCurrency)
			Ext.getStore(storeId).data.items[index].set('defaultCurrency',false); 
	}
	Ext.getStore(storeId).data.items[rowIdx].set('defaultCurrency',true);
}