Ext.define('AM.view.product.Edit', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.producteditor',
	layout : 'fit',
	defaults : {
		border : 0,
		padding : 3
	},
	closable : true,
	items : [ {
		border : 0,
		xtype : 'form',
		paramsAsHash: true,
		api : {
			// The server-side method to call for load() requests
			load : 'productDirectService.loadProduct'/*,
			submit : 'categoryDirectService.saveDetail'*/
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
			flex : 2,
			overflowY : 'auto',
			items : [ {
				xtype : 'container',
				flex : 1,
				border : false,
				layout : 'anchor',
				defaultType : 'textfield',
				items : [ {
					fieldLabel : 'Name',
					allowBlank : false,
					name : 'name',
					anchor : '90%'
				}, {
					fieldLabel : 'Title',
					name : 'title',
					allowBlank : false,
					anchor : '90%'
				}, {
					fieldLabel : 'Override Url',
					name : 'overrideUrl',
					allowBlank : false,
					anchor : '90%'
				}, {
					xtype : 'container',
					layout : 'hbox',
					anchor : '90%',
					defaults : {
						margin : '5 10 5 0'
					},
					items : [ {
						fieldLabel : 'Price',
						xtype : 'numberfield',
						allowBlank : false,
						name : 'price',
						flex : 1
					}, {
						fieldLabel : 'Actual Price',
						allowBlank : false,
						name : 'actualPrice',
						xtype : 'numberfield',
						flex : 1
					} ]

				}, {
					fieldLabel : 'Keywords',
					name : 'keywords',
					xtype : 'textarea',
					anchor : '90%'
				}, {
					fieldLabel : 'Tags',
					name : 'tags',
					xtype : 'textarea',
					anchor : '90%'
				}, {
					fieldLabel : 'Abstract Text',
					name : 'abstractText',
					xtype : 'textarea',
					anchor : '90%'
				} ]
			} ]
		}, {
			xtype : 'tabpanel',
			plain : true,
			activeTab : 0,
			flex : 3,
			defaults : {
				bodyPadding : 10
			},
			items : [{
				title : 'Detail',
				select :true,
				layout : 'fit',
				items : {
					xtype : 'htmleditor',
					name : 'detail'
				}
			}, {
				title : 'Category',
				layout : 'fit'/*,
				items : {
					xtype : 'gridpanel',
					store : new Ext.data.Store({
		                model: 'AM.model.CategoryTree'
		            }),
					columns: [
					          { text: 'Name', dataIndex: 'name', flex: 1 },
					          { text: 'Display Name',  dataIndex: 'displayName' , flex: 2}
					         
					      ]
				}*/
			}, {
				title : 'Images',
				defaults : {
					width : 230
				},
				defaultType : 'textfield',

				items : [ {
					fieldLabel : 'Home',
					name : 'home',
					value : '(888) 555-1212'
				}, {
					fieldLabel : 'Business',
					name : 'business'
				}, {
					fieldLabel : 'Mobile',
					name : 'mobile'
				}, {
					fieldLabel : 'Fax',
					name : 'fax'
				} ]
			}, {
				title : 'Option',
				layout : 'fit',
				items : {
					xtype : 'htmleditor',
					name : 'bio2',
					fieldLabel : 'Biography'
				}
			}, {
				title : 'Property',
				layout : 'fit',
				items : {
					xtype : 'htmleditor',
					name : 'bio2',
					fieldLabel : 'Biography'
				}
			} , {
				title : 'Mannual',
				layout : 'fit',
				items : {
					xtype : 'htmleditor',
					name : 'bio2',
					fieldLabel : 'Biography'
				}
			} , {
				title : 'Related Product',
				layout : 'fit',
				items : {
					xtype : 'htmleditor',
					name : 'bio2',
					fieldLabel : 'Biography'
				}
			} ]
		} ],

		buttons : [ {
			text : 'Save',
			handler : function() {
				this.up('form').getForm().isValid();
			}
		}, {
			text : 'Cancel',
			handler : function() {
				this.up('form').getForm().reset();
			}
		} ]
	} ]
});