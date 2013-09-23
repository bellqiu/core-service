Ext.Loader.setConfig({
	enabled : true,
	 paths: {
	        'Ext.ux': '/resources/ext/ux'
	       
	   }
});

Ext.direct.Manager.addProvider(Ext.app.REMOTING_API); 

Ext.state.Manager.setProvider(new Ext.state.CookieProvider({
	   expires: new Date(new Date().getTime()+(1000*60*60*24*7)), //7 days from now
}));

Ext.application({
	requires : [ 'Ext.container.Viewport', 'Ext.data.*',
	             'Ext.util.*', 'Ext.direct.*'],
	name : 'AM',

	appFolder : '/resources/ext/admin',

	controllers : ['GlobalNav' , 'Setting', 'CategoryTree', 'HTML', 'Country', 'Product', 'Currency', 'User', 'ProductSummary', 'TabProduct'],

	launch : function() {
		
		Ext.create('Ext.container.Viewport', {
			layout : 'border',
			defaults : {
				border : 0,
				margin : 2
			},
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
				id: 'managerContainer'
			}, {
				xtype : 'tabpanel',
				flex : 1,
				region : 'center',
				id : 'mainContainer',
				layout : 'fit'
			} ]
		});
	}
});