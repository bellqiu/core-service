Ext.define('AM.view.option.OptionItem', {
	extend : 'Ext.container.Container',
	alias : 'widget.productoptionitem',
	initComponent : function() {
		var me = this;

		Ext.applyIf(me, {
			items : [{
				xtype : 'grid',
				
			}]
		});

		this.callParent(arguments);
	}
});