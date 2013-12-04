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
				border : true,
				layout : 'hbox',
				itemId : 'orderProduct',
				items : [ {
					xtype : 'container',
					flex : 1,
					border : false,
					layout : 'vbox',
					width : '100',
					items : [ {
						xtype : 'image',
						src: 'http://www.dukal.com/Dental_supplies_BT.jpg',
						width : '100',
						heigth : '100'
					}, {
						xtype : 'label',
						text:'Image',
					}]
					
				}, {
					xtype : 'container',
					flex : 1,
					border : false,
					layout : 'vbox',
					width : '100',
					items : [ {
						xtype : 'image',
						src: 'http://www.dukal.com/Dental_supplies_BT.jpg',
						width : '100',
						heigth : '100'
					}, {
						xtype : 'label',
						text:'Image',
					}]
					
				}, {
					xtype : 'label',
					text:'Quantity',
					width : '100'
				}]
			}]
		});

		this.callParent(arguments);
	}
});