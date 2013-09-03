Ext.define('AM.view.html.HtmlManager', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.htmlmanager',
	title : 'Manager HTML',
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
				id : 'searchHTMLForm',
				items : [ {
					xtype : 'fieldset',
					scroll : true,
					margin : '10',
					padding : 5,
					height : 100,
					title : 'HTML filters',
					layout : {
						type : 'table',
						columns : 2
					},
					defaultType : 'textfield',
					items : [ {
						margin : 5,
						fieldLabel : 'Name',
						name : 'name',
						regex : /^\w+$/,
						regexText : 'Only for characters',
						width : 250

					}, {
						margin : 5,
						fieldLabel : 'Content',
						name : 'content',
						width : 250
					}, {
						margin : 5,
						xtype : 'panel',
						border : 0,
						defaults : {
							border : 0,
							margin : 2
						},
						items : [ {
							xtype : 'button',
							text : 'Search',
							id : 'searchHtml'
						}, {
							xtype : 'button',
							text : 'reset',
							id : 'resetHtml'
						} ]
					} ]

				} ]
			},

			{
				flex : 1,
				xtype : 'gridpanel',
				border : 0,
				store : 'HTML',
				plugins : [ {
					ptype : 'cellediting',
					clicksToEdit : 2,
					id : 'cellEditor'
				} ],
				selModel : {
					selType : 'cellmodel'
				},
				columns : [ {
					text : "Name",
					dataIndex : "name",
					flex : 1,
					editor : {
						xtype : 'textfield',
						allowBlank : false
					}
				}, {
					text : "Content",
					dataIndex : "Content",
					flex : 2
				}, {
					text : "Update Date",
					dataIndex : "updateDate",
					type : 'date',
					renderer : Ext.util.Format.dateRenderer('m/d/Y h:s'),
					flex : 1.5
				}, {
					text : "Create Date",
					dataIndex : "createDate",
					type : 'date',
					renderer : Ext.util.Format.dateRenderer('m/d/Y h:s'),
					flex : 1.5
				}, {
					xtype : 'actioncolumn',
					width : 30,
					sortable : false,
					menuDisabled : true,
					items : [ {
						icon : '/resources/ext/resources/images/delete.gif',
						tooltip : 'Delete HTML'
					} ]
				} ],
				bbar : {
					xtype : 'pagingtoolbar',
					pageSize : 30,
					store : 'HTML',
					displayInfo : true,
					plugins : new Ext.ux.ProgressBarPager()
				},
				dockedItems : [ {
					xtype : 'toolbar',
					items : [ {
						text : 'New',
						itemId : 'newHTML'
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