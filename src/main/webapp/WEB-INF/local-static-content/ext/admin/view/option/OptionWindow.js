Ext.define('AM.view.option.OptionWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.productoptionwindow',
	layout : 'fit',
	requires : [ 'Ext.form.*', 'AM.store.OptionType' ],
	config : {
		option : {}
	},
	closable : true,
	border : 0,
	height : 500,
	width: 500,
	defaults : {
		padding : 5,
		border : 0
	},
	initComponent : function() {
		this.items = [ {
			xtype : 'form',
			layout : 'vbox',
			itemId : 'productOptionForm',
			paramsAsHash: true,
			api : {
				saveOption : 'productDirectService.saveOption'
			},
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
				title : 'Option Properties',
				layout : 'hbox',
				height : 100,
				overflowY : 'auto',
				items : [ {
					xtype : 'container',
					flex : 1,
					border : false,
					itemId : 'optionWindow',
					layout : 'anchor',
					defaultType : 'textfield',
					items : [ {
						xtype : 'container',
						layout : 'hbox',
						anchor : '90%',
						defaults : {
							margin : '5 10 5 0'
						},
						items : [ {
							fieldLabel : 'Name',
							allowBlank : false,
							name : 'name',
							itemId : "opName",
							xtype : 'textfield',
							flex : 1
						}, {
							fieldLabel : 'Type',
							xtype : 'combo',
							store : 'OptionType',
							allowBlank : false,
							name : 'type',
							displayField : 'name',
							valueField : 'type',
							flex : 1
						} ]

					}, {
						fieldLabel : 'Default Value',
						name : 'defaultValue',
						xtype : 'textfield',
						labelPad : 2,
						anchor : '90%',
					} ]
				} ]
			}, {
				flex : 4,
				xtype : 'tabpanel',
				layout : 'fix',
				id : 'optionItemPanel',
				plugins : Ext.create('Ext.ux.TabReorderer'),
				//activeTab : '-1',
				/*items : [ {
					xtype : 'productoptionitem',
					title : 'Option Items'
				} ],*/
				//header : false
			} ],
			buttons : [ {
				text : 'Add Item',
				itemId : 'addOptionItem'
			}, {
				text : 'Save',
				itemId : 'saveOption'
			} ]
		}]
		this.callParent(arguments);
	},
});