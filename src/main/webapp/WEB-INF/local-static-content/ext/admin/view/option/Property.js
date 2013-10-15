Ext.define('AM.view.option.Property', {
	extend : 'Ext.container.Container',
	alias : 'widget.productpropertyitem',
	
	layout : 'fit',
	initComponent : function() {
		var me = this;
		

		var propertyStore = Ext.create("Ext.data.JsonStore",{
			model : 'AM.model.Property',
			data : [],
			buffered : false,
			proxy: {
		        type: 'memory'
		    }
		});

		Ext.applyIf(me, {
			items : [{
				flex : 1,
				xtype : 'gridpanel',
				border : 0,
				store : propertyStore,
				itemId : 'property',
				viewConfig: {
			        plugins: {
			            ddGroup: 'property-row-to-row',
			            ptype: 'gridviewdragdrop',
			            enableDrop: true
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
						allowBlank : false,
						name : "propertyName"
					}
				}, {
					text : "Value",
					dataIndex : "value",
					flex : 2,
					editor : {
						xtype : 'textfield',
						allowBlank : false,
						name : "propertyValue"
					}
				}]
			}]
		});

		this.callParent(arguments);
	}
});