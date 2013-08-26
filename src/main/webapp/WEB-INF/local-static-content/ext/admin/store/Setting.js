Ext.define('AM.store.Setting', {
    extend: 'Ext.data.Store',
    model: 'AM.model.Setting',
    autoLoad: true,
    remoteFilter: true,
	autoSync: false
	
});