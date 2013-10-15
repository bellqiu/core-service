Ext.define('AM.view.category.Edit', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.categoryeditor',
	title : 'Manager Setting',
	layout : 'fit',
	requires : [ 'Ext.form.*', 'AM.model.CategoryDetail' ],
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
			itemId : 'saveCategory'
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
					load : 'categoryDirectService.loadDetail',
					submit : 'categoryDirectService.saveDetail'
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
						fieldLabel : 'Parent Id',
						name : 'parentId',
						xtype : 'textfield',
						flex : 1
					} ,{
						fieldLabel : 'Name',
						name : 'name',
						xtype : 'textfield',
						regex : /^\w+$/,
						regexText : 'Only for characters',
						flex : 1
					}, {
						fieldLabel : 'Display Name',
						name : 'displayName',
						xtype : 'textfield',
						flex : 1
					}, {
						fieldLabel : 'Description',
						name : 'description',
						xtype : 'textfield',
						flex : 1
					}, {
						fieldLabel : 'Type',
						name : 'type',
						xtype : 'combo',
						displayField : 'name',
						valueField : 'type',
						typeAhead : true,
						triggerAction : 'all',
						store : 'CategoryType',
						allowBlank : false
					},{
						fieldLabel : 'Icon Url',
						name : 'iconUrl',
						xtype : 'textfield',
						flex : 1
					},{
						fieldLabel : 'Url',
						name : 'url',
						xtype : 'textfield',
						flex : 1
					},{
						fieldLabel : 'Page Title',
						name : 'pageTitle',
						xtype : 'textfield',
						flex : 1
					},{
						fieldLabel : 'Related Keyword',
						name : 'relatedKeyword',
						xtype : 'textfield',
						flex : 1
					},{
						fieldLabel : 'Market Content',
						name : 'marketContent',
						xtype : 'htmleditor',
						width : 600
					}]
				} ]
			} ]
		});
		this.callParent();
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