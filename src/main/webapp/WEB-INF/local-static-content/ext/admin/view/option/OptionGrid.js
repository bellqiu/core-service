Ext.define('AM.view.option.OptionGrid', {
	extend : 'Ext.container.Container',
	alias : 'widget.productoptiongrid',
	initComponent : function() {
		var me = this;
		

		var propertyStore = Ext.create("Ext.data.JsonStore",{
			model : 'AM.model.Option',
			data : [],
			buffered : false,
		});

		Ext.applyIf(me, {
			items : [{
				flex : 1,
				xtype : 'gridpanel',
				border : 0,
				store : propertyStore,
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
						allowBlank : false
					}
				}, {
					text : "Default",
					dataIndex : "defaltValue",
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
						icon : '/resources/ext/resources/images/edit.gif',
						tooltip : 'Edit'
					},{
						icon : '/resources/ext/resources/images/delete.gif',
						tooltip : 'Delete'
					} ]
				} ]
			}]
		});

		this.callParent(arguments);
	}
});