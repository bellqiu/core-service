Ext.define('AM.view.option.OptionItem', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.productoptionitem',
	config : {
		optionItem : {}
	},
	layout : 'fit',
	defaults : {
		border : 0,
		padding : 3
	},
	closable : true,
	initComponent : function() {
		
		var optionItemStore = Ext.create("Ext.data.JsonStore",{
			model : 'AM.model.OptionItem',
			data : [],
			buffered : false,
			proxy: {
		        type: 'memory'
		    }
		});
		
		this.items = [ {
			border : 0,
			xtype : 'form',
			itemId : 'optionItemForm',
			paramsAsHash: true,
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
					itemId : 'optionItem',
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
							name : 'displayName',
							itemId : "opItemName",
							xtype : 'textfield',
							flex : 1
						}, {
							fieldLabel : 'Value',
							name : 'value',
							itemId : "opItemValue",
							xtype : 'textfield',
							flex : 1
						} ]

					}, {
						fieldLabel : 'Price Change',
						xtype : 'numberfield',
						name : 'priceChange',
						anchor : '50%'
					}, {
						fieldLabel : 'Icon Url',
						name : 'iconUrl',
						anchor : '90%'
					} ]
				} ]
			}, {
				xtype : 'tabpanel',
				plain : true,
				layout : 'hbox',
				activeTab : 0,
				flex : 3,
				defaults : {
					bodyPadding : 10
				},
				items : [{
					title : 'Property',
					layout : 'fit',
					items : {
						xtype : 'productpropertyitem',
					}
				}]
			} ],

		} ];
		
		this.callParent(arguments);
	}
});