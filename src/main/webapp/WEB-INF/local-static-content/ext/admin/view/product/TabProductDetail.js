Ext.define('AM.view.product.TabProductDetail', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.tabproducteditor',
	
	config : {
		tabproduct : {}
	},
	layout : 'fit',
	defaults : {
		border : 0,
		padding : 3
	},
	closable : true,
	
	initComponent : function() {
		
		var productSummaryStore = Ext.create("Ext.data.JsonStore",{
			model : 'AM.model.ProductSummary',
			data : [],
			buffered : false,
		});
		
		this.items = [ {
			border : 0,
			xtype : 'form',
			itemId : 'tabProductForm',
			paramsAsHash: true,
			api : {
				// The server-side method to call for load() requests
				load : 'tabProductDirectService.loadTabProduct',
				submit : 'tabProductDirectService.saveTabProductDetail'
			},
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
				items : [ {
					flex : 2,
					overflowY : 'auto',
					xtype : 'container',
					flex : 1,
					border : false,
					layout : 'anchor',
					defaultType : 'textfield',
					items : [{
						fieldLabel : 'Name',
						allowBlank : false,
						name : 'name',
						anchor : '90%'
					}]
				}]
			}, {
				xtype : 'tabpanel',
				plain : true,
				activeTab : 0,
				flex : 3,
				defaults : {
					bodyPadding : 10
				},
				items : [{
					title : 'Products',
					layout : 'fit',
					items : {
						xtype : 'gridpanel',
						itemId : 'productSummary',
						viewConfig: {
			                plugins: {
			                    ddGroup: 'productSummary-row-to-row',
			                    ptype: 'gridviewdragdrop',
			                    enableDrop: true
			                }
			            },
						enableKeyEvents:true,
						store : productSummaryStore,
						columns: [
						          { text: 'Name', dataIndex: 'name', flex: 1 },
						          { text: 'Title',  dataIndex: 'title' , flex: 2}
						         
						      ]
					}
				}]
			} ],

			buttons : [ {
				text : 'Save',
				itemId :'saveTabProduct'
			}]
		} ];
		
		this.callParent(arguments);
	}

});