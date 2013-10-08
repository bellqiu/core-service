Ext.define('AM.view.option.OptionItem', {
	extend : 'Ext.container.Container',
	alias : 'widget.productoptionitem',
	initComponent : function() {
		var me = this;
		

		var propertyStore = Ext.create("Ext.data.JsonStore",{
			model : 'AM.model.Property',
			data : [],
			buffered : false,
		});

		Ext.applyIf(me, {
			items : [{
				flex : 1,
				xtype : 'gridpanel',
				border : 0,
				store : propertyStore,
				itemId : 'property',
				plugins : [ {
					ptype : 'cellediting',
					clicksToEdit : 2,
					id : 'cellEditor'
				} ],
				selModel : {
					selType : 'cellmodel'
				},
				tbar : [
				        {
				        	xtype:'button',
				        	text : 'Add',
				        	itemId : 'addProperty'
				        }
				        ],
				columns : [ {
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
					flex : 2,
					editor : {
						xtype : 'textfield',
						allowBlank : false
					}
				}, {
					xtype : 'actioncolumn',
					width : 30,
					sortable : false,
					menuDisabled : true,
					items : [ {
						icon : '/resources/ext/resources/images/delete.gif',
						tooltip : 'Delete Setting'
					} ]
				} ]
			}]
		});

		this.callParent(arguments);
	}
});