Ext.define('AM.view.coupon.CouponDetail', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.couponeditor',
	title : 'Coupon Detail',
	layout : 'fit',
	requires : [ 'Ext.form.*', 'AM.model.Coupon' ],
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
			itemId : 'saveCoupon'
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
					load : 'couponDirectService.loadCoupon',
					submit : 'couponDirectService.saveCoupon'
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
					}, {
						fieldLabel : 'Code',
						name : 'code',
						xtype : 'textfield',
						regex : /^\w+$/,
						regexText : 'Only for characters',
						flex : 1
					}, {
						fieldLabel : 'Name',
						name : 'name',
						xtype : 'textfield',
						regex : /^\w+$/,
						regexText : 'Only for characters',
						flex : 1
					}, {
						fieldLabel : 'Value',
						name : 'value',
						xtype : 'numberfield',
						minValue : 0,
						flex : 1
					}, {
						fieldLabel : 'Used Count',
						name : 'usedCount',
						xtype : 'numberfield',
						decimalPrecision : 0,
						minValue : 0,
						flex : 1
					}, {
						fieldLabel : 'Min Cost',
						name : 'minCost',
						xtype : 'numberfield',
						minValue : 0,
						flex : 1
					}, {
						fieldLabel : 'Max Used Count',
						name : 'maxUsedCount',
						xtype : 'numberfield',
						decimalPrecision : 0,
						minValue : 1,
						flex : 1
					}, {
						fieldLabel : 'Start Date',
						name : 'startDate',
						xtype : 'datefield',
						format : 'm/d/Y',
						/*renderData : function(v) {
							return new Date();
						}*/
						/*renderContent : function(out, values) {
							return new Date();
						},*/
						listeners : {
						    render : function(datefield) {
						        /// code to convert GMT String to date object
						        datefield.setValue(new Date());
						             }
						 },
						//renderer : Ext.util.Format.dateRenderer('m/d/Y'),
						flex : 1
					}, {
						fieldLabel : 'End Date',
						name : 'endDate',
						xtype : 'datefield',
						renderer : function(v) {
							Ext.util.Format.dateRenderer('m/d/Y');
						},
						flex : 1
					}, {
						fieldLabel : 'Description',
						name : 'desc',
						xtype : 'htmleditor',
						width : 850,
					}, /*{
						fieldLabel : 'Create Date',
						name : 'createDate',
						xtype : 'datefield',
						hidden: true
					}, {
						fieldLabel : 'Update Date',
						name : 'updateDate',
						xtype : 'datefield',
						hidden: true
					},*/ {
						fieldLabel : 'Status',
						name : 'status',
						xtype : 'textfield',
						hidden: true
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