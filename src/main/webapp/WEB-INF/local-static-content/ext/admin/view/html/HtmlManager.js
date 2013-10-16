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
						xtype : 'panel',
						border : 0,
						defaults : {
							border : 0,
							margin : 2
						},
						items : [ {
							xtype : 'button',
							text : 'Search',
							id : 'searchHtml',
							flex : 1
						}, {
							xtype : 'button',
							text : 'Reset',
							id : 'resetHtml',
							flex : 1
						} ]
					} ]

				} ]
			},

			{
				flex : 1,
				xtype : 'gridpanel',
				border : 0,
				store : 'HTML',
				viewConfig: {
	                plugins: {
	                    ddGroup: 'grid-to-edit-parent-html',
	                    ptype: 'gridviewdragdrop',
	                    enableDrop: false
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
				columns : [ {
					text : "Name",
					dataIndex : "name",
					flex : 1,
					editor : {
						xtype : 'textfield',
						allowBlank : false
					}
				}, /*{
					text : "Content",
					dataIndex : "content",
					renderer : function(value) {
						len = value.length;
						// TODO trim the content
						//if(len > 25) {
						//	value = escape(value.subString(0,25));
						//}
			            return value;
			        }, 
					flex : 2
				}, */{
					text : "Update Date",
					dataIndex : "updateDate",
					type : 'date',
					renderer : Ext.util.Format.dateRenderer('m/d/Y H:i:s'),
					flex : 1.5
				}, {
					text : "Create Date",
					dataIndex : "createDate",
					type : 'date',
					renderer : Ext.util.Format.dateRenderer('m/d/Y H:i:s'),
					flex : 1.5
				}, {
					xtype : 'actioncolumn',
					width : 30,
					sortable : false,
					menuDisabled : true,
					items : [ {
						icon : '/resources/ext/resources/images/edit.png',
						tooltip : 'Edit HTML',
						id : 'editHTML'

					}, {
						icon : '/resources/ext/resources/images/delete.gif',
						tooltip : 'Delete HTML',
						id : 'deleteHTML'
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
					}, {
						text : 'Refresh',
						itemId : 'refreshHTML'
					} ]
				} ]

			} ]
		} ];
		this.callParent(arguments);
	},
});