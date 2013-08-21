Ext.Loader.setConfig({
	enabled : true,
	 paths: {
	        'Ext.ux': '/resources/ext/ux'
	   }
});

Ext.application({
	requires : [ 'Ext.container.Viewport' ],
	name : 'AM',

	appFolder : '/resources/ext/admin',

	controllers : [ 'Users', 'GlobalNav' ],

	launch : function() {
		Ext.create('Ext.container.Viewport', {
			layout : 'border',
			items : [ {
				xtype : 'globalnav',
				region : 'north',
				height : 35,
			}, {
				xtype : 'tabpanel',
				region : 'west',
				flex : 1,
				split : true,
				collapsible : true,
				header : false,
				border : 0,
				items : [ {
					border : 0,
					title : "Query Product",
					xtype : 'panel',
					html : 'dd'
				} ]
			}, {
				xtype : 'tabpanel',
				flex : 1,
				region : 'center',
				id : 'mainContainer',
				layout : 'fit',
				items : [ {
					border : 0,
					title : "Create Product",
					xtype : 'panel',
					closable : true,
					html : 'dd'
				} ]
			} ]
		});
	}
});