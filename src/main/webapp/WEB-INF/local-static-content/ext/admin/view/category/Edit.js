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
		}, {
			text : 'Delete',
			itemId : 'deleteCategory'
		} ]
	} ],
	initComponent : function() {

		Ext.apply(this, {
			items : [ {
				xtype : 'form',
				border : 0,
				layout : 'fit',
				api : {
					// The server-side method to call for load() requests
					load : 'categoryDirectService.loadDetail'
				},
				defaults : {
					border : 0
				},
				items : [ {
					xtype : 'fieldset',
					layout : {
						type : 'table',
						columns : 2
					},
					items : [ {
						margin : 5,
						fieldLabel : 'Parent Name',
						name : 'parentName',
						xtype : 'textfield',
						regex : /^\w+$/,
						regexText : 'Only for characters',
						width : 250
					} ]
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