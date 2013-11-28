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
    		
    	}else if(opt == 'category'){
    		
    		var tabpanel = combo.up("viewport").down("tabpanel#managerContainer");
    		
    		if(tabpanel.down("categorytreegrid")){
    			tabpanel.setActiveTab(tabpanel.down("categorytreegrid"));
    		}else{
    			var view = Ext.widget('categorytreegrid');
    			tabpanel.insert(0, view);
    			tabpanel.setActiveTab(0);
    		}
    	} else if(opt == 'html'){
    		
    		var tabpanel = combo.up("viewport").down("tabpanel#managerContainer");
    		
    		if(tabpanel.down("htmlmanager")){
    			tabpanel.setActiveTab(tabpanel.down("htmlmanager"));
    		}else{
    			var view = Ext.widget('htmlmanager');
    			tabpanel.insert(0, view);
    			tabpanel.setActiveTab(0);
    		}
    		
    	} else if(opt == 'country'){
    		
    		var tabpanel = combo.up("viewport").down("tabpanel#managerContainer");
    		
    		if(tabpanel.down("countrymanager")){
    			tabpanel.setActiveTab(tabpanel.down("countrymanager"));
    		}else{
    			var view = Ext.widget('countrymanager');
    			tabpanel.insert(0, view);
    			tabpanel.setActiveTab(0);
    		}
    		
    	} else if(opt == 'currency'){
    		
    		var tabpanel = combo.up("viewport").down("tabpanel#managerContainer");
    		
    		if(tabpanel.down("currencymanager")){
    			tabpanel.setActiveTab(tabpanel.down("currencymanager"));
    		}else{
    			var view = Ext.widget('currencymanager');
    			tabpanel.insert(0, view);
    			tabpanel.setActiveTab(0);
    		}
    		
    	} else if(opt == 'user'){
    		
    		var tabpanel = combo.up("viewport").down("tabpanel#managerContainer");
    		
    		if(tabpanel.down("usermanager")){
    			tabpanel.setActiveTab(tabpanel.down("usermanager"));
    		}else{
    			var view = Ext.widget('usermanager');
    			tabpanel.insert(0, view);
    			tabpanel.setActiveTab(0);
    		}
    		
    	} else if(opt == 'product'){
    		
    		var tabpanel = combo.up("viewport").down("tabpanel#managerContainer");
    		
    		if(tabpanel.down("productsummarymanager")){
    			tabpanel.setActiveTab(tabpanel.down("productsummarymanager"));
    		}else{
    			var view = Ext.widget('productsummarymanager');
    			tabpanel.insert(0, view);
    			tabpanel.setActiveTab(0);
    		}
    		
    	} else if(opt == 'tabproduct'){
    		
    		var tabpanel = combo.up("viewport").down("tabpanel#managerContainer");
    		
    		if(tabpanel.down("tabproductmanager")){
    			tabpanel.setActiveTab(tabpanel.down("tabproductmanager"));
    		}else{
    			var view = Ext.widget('tabproductmanager');
    			tabpanel.insert(0, view);
    			tabpanel.setActiveTab(0);
    		}
    		
    	} else if(opt == 'coupon'){
    		
    		var tabpanel = combo.up("viewport").down("tabpanel#managerContainer");
    		
    		if(tabpanel.down("couponmanager")){
    			tabpanel.setActiveTab(tabpanel.down("couponmanager"));
    		}else{
    			var view = Ext.widget('couponmanager');
    			tabpanel.insert(0, view);
    			tabpanel.setActiveTab(0);
    		}
    		
    	} else if(opt == 'order'){
    		
    		var tabpanel = combo.up("viewport").down("tabpanel#managerContainer");
    		
    		if(tabpanel.down("ordermanager")){
    			tabpanel.setActiveTab(tabpanel.down("ordermanager"));
    		}else{
    			var view = Ext.widget('ordermanager');
    			tabpanel.insert(0, view);
    			tabpanel.setActiveTab(0);
    		}
    		
    	} else {
    		Ext.Msg.alert('Status', 'No operation');
    	}
    	combo.reset();
    },
    
    logout: function(grid, record) {
      window.location.href = window.location.protocol+window.location.hostname+":"+window.location.port+"/logout"
    }

});