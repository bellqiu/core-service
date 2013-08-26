Ext.define('AM.controller.GlobalNav', {
    extend: 'Ext.app.Controller',
    views: [
            'toolbar.GlobalNav',
            'setting.Manager'
    ],
    stores: ['ManagerCombo'],
    init: function() {
        this.control({
            'globalnav button#logout': {
                click: this.logout
            },
            
            'globalnav combo#managerCombo': {
            	select: this.manage
            }
            
        });
    },
    
    manage : function(combo, records, eOpts){
    	var opt = records[0].data.value;
    	if(opt == 'setting'){
    		
    		var tabpanel = combo.up("viewport").down("tabpanel#managerContainer");
    		
    		if(tabpanel.down("settingmanager")){
    			tabpanel.setActiveTab(tabpanel.down("settingmanager"));
    		}else{
    			var view = Ext.widget('settingmanager');
    			tabpanel.insert(0, view);
    			tabpanel.setActiveTab(0);
    		}
    		
    	}else{
    		Ext.Msg.alert('Status', 'No operation');
    	}
    	combo.reset();
    },
    
    logout: function(grid, record) {
      window.location.href = window.location.protocol+window.location.hostname+":"+window.location.port+"/logout"
    }

});