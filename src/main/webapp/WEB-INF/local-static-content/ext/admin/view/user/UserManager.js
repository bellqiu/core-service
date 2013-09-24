Ext.define('AM.view.user.UserManager', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.usermanager',
	title : 'Manager User',
	layout : 'fit',
	requires : [ 'Ext.grid.*', 'Ext.util.*', 'Ext.form.*',
			'Ext.toolbar.Paging', 'Ext.ux.ProgressBarPager' ],
	closable : true,
	border : 0,
	initComponent : function() {
		this.items = [ {
			xtype : 'panel',
			layout : 'fit',
			border : 0,
			layout : {
				type : 'vbox',
				align : 'stretch',
				pack : 'start'
			},
			defaults : {

				validateOnChange : true
			},
			scroll : true,
			items : [ {
				xtype : 'form',
				id : 'searchUserForm',
				items : [ {
					xtype : 'fieldset',
					scroll : true,
					margin : '10',
					padding : 5,
					height : 100,
					title : 'User filters',
					layout : {
						type : 'table',
						columns : 2
					},
					defaultType : 'textfield',
					items : [ {
						margin : 5,
						fieldLabel : 'Email',
						name : 'email',
						regex : /^\w+$/,
						regexText : 'Only for characters',
						width : 250

					}, {
						margin : 5,
						fieldLabel : 'Role',
						xtype : 'combo',
						store : 'UserRole',
						name : 'roles',
						displayField : 'name',
						valueField : 'role',
						width : 250
					}, {
						margin : 5,
						xtype : 'panel',
						border : 0,
						colspan : 2,
						layout : {
							type : 'hbox',
							align : 'middle',
							pack : 'center'
						},
						defaults : {
							border : 0,
							margin : 2
						},
						items : [ {
							flex : 2
						}, {
							flex : 2,
							defaults : {
								border : 0,
								margin : 2
							},
							items : [ {
								xtype : 'button',
								text : 'Search',
								id : 'searchUser',
								flex : 1
							}, {
								xtype : 'button',
								text : 'Reset',
								id : 'resetUser',
								flex : 1
							} ]

						} ]
					} ]

				} ]
			},

			{
				flex : 1,
				xtype : 'gridpanel',
				border : 0,
				store : 'User',
				plugins : [ {
					ptype : 'cellediting',
					clicksToEdit : 2,
					id : 'cellEditor'
				} ],
				selModel : {
					selType : 'cellmodel'
				},
				columns : [ {
					text : "Email",
					dataIndex : "email",
					flex : 1,
					// TODO remove it to disable editing email
					editor : {
						xtype : 'textfield',
						allowBlank : false
					}
				}, {
					text : "Password",
					dataIndex : "password",
					flex : 2,
					editor : {
						inputType : 'password',
						xtype : 'textfield',
						allowBlank : false
					}
				}, {
					text : "Role",
					dataIndex : "roles",
					flex : 1,
					editor : {
						xtype : 'combo',
						displayField : 'name',
						valueField : 'role',
						typeAhead : true,
						triggerAction : 'all',
						store : 'UserRole',
						allowBlank : false
					}
				}, {
					text : 'Enabled',
					dataIndex : 'enabled',
					flex : 1,
					id : 'enableId',
					type : 'boolean',
					renderer : function(value) {
			            return "<input type='checkbox' name = 'enableCheckbox' disabled " + (value ? "checked='checked'" : "") + ">";
					},
				}/*, {
					xtype : 'actioncolumn',
					width : 30,
					sortable : false,
					menuDisabled : true,
					items : [ {
						icon : '/resources/ext/resources/images/delete.gif',
						tooltip : 'Delete Setting'
					} ]
				} */],
				bbar : {
					xtype : 'pagingtoolbar',
					pageSize : 30,
					store : 'User',
					displayInfo : true,
					plugins : new Ext.ux.ProgressBarPager()
				},
				dockedItems : [ {
					xtype : 'toolbar',
					items : [ {
						// TODO need to remove new user function
						text : 'New',
						itemId : 'newUser'
					}, {
						text : 'Synchronize',
						itemId : 'synchronize'
					} ]
				} ]

			} ]
		} ];
		this.callParent(arguments);
	},
});