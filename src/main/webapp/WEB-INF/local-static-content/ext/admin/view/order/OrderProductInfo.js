Ext.define('AM.view.order.OrderProductInfo', {
	extend : 'Ext.container.Container',
	alias : 'widget.orderproductinfo',
	
	layout : 'fit',
	initComponent : function() {
		var me = this;
		
		Ext.applyIf(me, {
			items : [{
				xtype : 'container',
				flex : 1,
				border : false,
				layout : 'hbox',
				itemId : 'orderProduct',
				items : [ {
					xtype : 'label',
					text:'Order Status',
					width : '100'
				}, {
					fieldLabel : 'traceInfo1',
					name : 'traceInfo',
					xtype : 'textfield',
				}, {
					fieldLabel : 'traceInfo2',
					width : '100'
				}]
			}]
		});

		this.callParent(arguments);
	}
});