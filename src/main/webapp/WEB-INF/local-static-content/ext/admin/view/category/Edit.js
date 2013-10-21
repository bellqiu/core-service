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
					anchor : '100%',
					align : 'stretch'
				},
				fieldDefaults : {
					labelAlign : 'left',
					//labelWidth : 115,
					msgTarget : 'side',
					//margin : 5
				},
				items : [ {
					xtype : 'fieldset',
					width : 600,
					flex : 1,
					/*layout : {
						type : 'table',
						columns : 1
					},*/

					layout : 'anchor',
					items : [
					  {
						name : 'id',
						xtype : 'textfield',
						hidden: true
					}, {
						fieldLabel : 'Parent Id',
						name : 'parentId',
						xtype : 'textfield',
						anchor : '90%',
					}, {
						fieldLabel : 'Page Title',
						name : 'pageTitle',
						xtype : 'textfield',
						anchor : '90%',
					},{
						fieldLabel : 'Related Keyword',
						name : 'relatedKeyword',
						xtype : 'textfield',
						anchor : '90%'
					}, {
						fieldLabel : 'Description',
						name : 'description',
						xtype : 'textarea',
						anchor : '90%'
					}, {
						fieldLabel : 'Name',
						name : 'name',
						xtype : 'textfield',
						regex : /^[\w\-]+$/,
						regexText : 'Only for characters',
						anchor : '90%'
					}, {
						fieldLabel : 'Display Name',
						name : 'displayName',
						xtype : 'textfield',
						anchor : '90%'
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
						anchor : '90%'
					},{
						fieldLabel : 'Url',
						name : 'url',
						xtype : 'textfield',
						anchor : '90%'
					}, {
						fieldLabel : 'Market Content',
						name : 'marketContent',
						xtype : 'htmleditor',
						anchor : '90%',
						plugins: new Ext.ux.plugins.HtmlEditorImageInsert({
		                    popTitle: 'Image url?',
		                    popMsg: 'Please insert an image URL...',
		                    popWidth: 400/*,
		                    popValue: 'http://www.google.gr/intl/en_com/images/logo_plain.png'*/
		                })
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