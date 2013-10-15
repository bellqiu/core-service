Ext.define('AM.view.option.OptionGrid', {
	extend : 'Ext.container.Container',
	alias : 'widget.productoptiongrid',
	
	layout : 'fit',
	initComponent : function() {
		var me = this;
		

		var optionStore = Ext.create("Ext.data.JsonStore",{
			model : 'AM.model.Option',
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
				store : optionStore,
				itemId : 'option',
				viewConfig: {
	                plugins: {
	                    ddGroup: 'option-row-to-row',
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
				        	itemId: 'newoption'
				        }
				        ],
				columns : [ {
					text : "Name",
					dataIndex : "name",
					flex : 1,
					editor : {
						xtype : 'textfield',
						allowBlank : false,
						name : "optionName"
					}
				}, {
					text : "Default",
					dataIndex : "defaultValue",
					flex : 2,
					editor : {
						xtype : 'textfield',
					}
				}, {
					xtype : 'actioncolumn',
					width : 30,
					sortable : false,
					menuDisabled : true,
					items : [ {
						icon : '/resources/ext/resources/images/edit.png',
						tooltip : 'Edit'
					}]
				} ]
			}]
		});

		this.callParent(arguments);
	}
});