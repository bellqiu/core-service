Ext.define('AM.view.option.OptionWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.productoptionwindow',
	layout : 'fit',
	requires : [ 'Ext.form.*' ],
	closable : true,
	border : 0,
	height : 200,
	width : 400,
	defaults : {
		padding : 5,
		border : 0
	},
	initComponent : function() {
		this.items = [ {
			xytpe : 'form',
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
				title : 'Option Properties',
				layout : 'hbox',
				height : 100,
				overflowY : 'auto',
				items : [ {
					xtype : 'container',
					flex : 1,
					border : false,
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
							xtype : 'textfield',
							flex : 1
						}, {
							fieldLabel : 'Type',
							allowBlank : false,
							xtype : 'textfield',
							name : 'type',
							flex : 1
						} ]

					}, {
						fieldLabel : 'Default Value',
						allowBlank : false,
						name : 'defaultvalue',
						anchor : '90%',
					} ]
				} ]
			}, {
				flex : 4,
				xtype : 'tabpanel',
				layout : 'fix',
				plugins : Ext.create('Ext.ux.TabReorderer'),
				activeTab : '-1',
				items : [ {
					title : '1',
					html :'1'
				}, {
					title : '2',
					html :'2'
				},{
					title : '3',
					html :'3'
				},{
					title : '+',
					itemId : 'newOptionItem'
				} ],
				header : false
			} ],
			buttons : [ {
				text : 'Save',
				itemId : 'saveOption'
			} ]
		}

		]
		this.callParent(arguments);
	},
});