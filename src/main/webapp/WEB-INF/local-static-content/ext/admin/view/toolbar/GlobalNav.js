Ext.define('AM.view.toolbar.GlobalNav', {
			extend : 'Ext.toolbar.Toolbar',
			alias : 'widget.globalnav',
			border : 0,
			initComponent : function() {
				var me = this;

				Ext.applyIf(me, {
							items : [{
										fieldLabel : 'Create',
										xtype : 'combo',
										queryMode : 'local',
										displayField : 'name',
										valueField : 'value',
										store : 'CreateCombo',
										labelWidth: 40,
										width: 150,
										id: 'create'

									},
									{
										fieldLabel : 'Query',
										xtype : 'combo',
										queryMode : 'local',
										displayField : 'name',
										valueField : 'value',
										store : 'CreateCombo',
										labelWidth: 40,
										width: 150,
										id: 'query'

									},'->', {
										xtype : 'label',
										text : loginuser.email
									},

									{
										xtype : 'button',
										text : 'Logout',
										id : 'logout'
									}]
						});

				me.callParent(arguments);
			}

		});