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
						allowBlank : false,
						flex : 1
					}, {
						fieldLabel : 'Content',
						name : 'content',
						xtype : 'htmleditor',
						width : 950,
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
		this.callParent(arguments);
	},
	afterFirstLayout : function() {
		this.callParent(arguments);
		var form = this.down('form'), body = form.body;

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