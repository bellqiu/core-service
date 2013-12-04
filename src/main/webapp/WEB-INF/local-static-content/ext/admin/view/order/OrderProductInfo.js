Ext.define('AM.view.order.OrderProductInfo', {
	extend : 'Ext.container.Container',
	alias : 'widget.orderproductinfo',
	
	//layout : 'fit',
	border: 5,
	padding : 5,
	//cls : 'redBackgroud',
	initComponent : function() {
		var me = this;
		
		Ext.applyIf(me, {
			items : [{
				xtype : 'container',
				layout : 'hbox',
				flex : 1,
				defaults : {
					width : '100%',
					align : 'stretch'
				},
				itemId : 'orderProduct',
				items : [ {
					xtype : 'container',
					border : false,
					layout : 'vbox',
					width : '150',
					items : [ {
						xtype : 'panel',
						itemId : 'imagePanel',
						width : '100',
						height : '100',
						html : ''
					}, {
						xtype : 'label',
						itemId : 'productTitle',
						shrinkWrap : 2,
						width : '150',
						text : ''
					}]
					
				}, {
					xtype : 'container',
					flex : 1,
					padding : "0 15",
					layout : 'vbox',
					itemId : 'selectedOpts',
					items : [ {
						xtype : 'label',
						text:'Selected Options'
					}/*, {
						xtype : 'label',
						text :'Item111111111111111111111111111s'
					}, {
						xtype : 'label',
						text :'Item2'
					}*/]
					
				}, {
					xtype : 'container',
					border : false,
					layout : 'vbox',
					width : '100',
					items : [ {
						xtype : 'label',
						text:'Quantity',
						margin:'10 5 3 10'
					}, {
						xtype : 'label',
						itemId : 'quantityValue',
						text:'1',
						margin:'10 35 3 30'
					}]
				}]
			}]
		});

		this.callParent(arguments);
	}
});