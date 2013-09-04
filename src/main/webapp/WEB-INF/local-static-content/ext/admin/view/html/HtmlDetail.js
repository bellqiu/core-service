Ext.define('AM.view.html.HtmlDetail', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.html_editor',
	title : 'Manager HTML',
	layout : 'fit',
	requires : [ 'Ext.form.*', 'AM.model.HTMLDetail' ],
	closable : true,
	border : 0,
	padding : 5,
	layout : 'fit',
	defaults : {
		border : 0
	},
	dockedItems : [ {
		xtype : 'toolbar',
		items : [ {
			text : 'Save',
			itemId : 'saveHTML'
		} ]
	} ],
	initComponent : function() {
		var self = this;
		Ext.apply(this, {
			items : [ {
				xtype : 'form',
				paramsAsHash: true,
				border : 0,
				layout : 'fit',
				api : {
					// The server-side method to call for load() requests
					load : 'htmlDirectService.loadHtmlDetail',
					submit : 'htmlDirectService.saveHtmlDetail'
				},
				defaults : {
					border : 0,
					anchor : '100%'
				},
				fieldDefaults : {
					labelAlign : 'left',
					labelWidth : 115,
					msgTarget : 'side',
					margin : 5
				},
				items : [ {
					xtype : 'fieldset',
					layout : {
						type : 'table',
						columns : 1
					},
					
					items : [
					  {
						name : 'id',
						xtype : 'textfield',
						hidden: true
					} ,{
						fieldLabel : 'Name',
						name : 'name',
						xtype : 'textfield',
						regex : /^\w+$/,
						regexText : 'Only for characters',
						flex : 1
					},/*{
						fieldLabel : 'update Date',
						name : 'updateDate',
						xtype : 'datefield',
						renderer : Ext.util.Format.dateRenderer('m/d/Y h:s'),
						//hidden : true,
						value: new Date(),
						flex : 1
					},{
						fieldLabel : 'create Date',
						name : 'createDate',
						xtype : 'datefield',
						renderer : Ext.util.Format.dateRenderer('m/d/Y h:s'),
						//hidden : true,
						flex : 1
					},*/{
						fieldLabel : 'Content',
						name : 'content',
						xtype : 'htmleditor',
						width : 600
					}/*,{
						fieldLabel : 'status',
						name : 'status',
						xtype : 'textfield',
						hidden : true,
						flex : 1
					}*/]
				} ]
			} ]
		});
		this.callParent(arguments);
	},
	afterFirstLayout : function() {
		this.callParent(arguments);
		var form = this.down('form'), body = form.body;

		this.formPanelDropTarget = new Ext.dd.DropTarget(body, {
			ddGroup : 'grid-to-edit-parent',
			notifyEnter : function(ddSource, e, data) {
				body.stopAnimation();
				body.highlight();
			},
			notifyDrop : function(ddSource, e, data) {
				var f = form.getForm();
				f.findField('parentId').setValue(ddSource.dragData.records[0].data.id);
				return true;
			}
		});
	},

	beforeDestroy : function() {
		var target = this.formPanelDropTarget;
		if (target) {
			target.unreg();
			this.formPanelDropTarget = null;
		}
		this.callParent();
	}
});