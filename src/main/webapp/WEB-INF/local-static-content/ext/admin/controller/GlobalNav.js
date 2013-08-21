Ext.define('AM.controller.GlobalNav', {
    extend: 'Ext.app.Controller',
    views: [
            'toolbar.GlobalNav',
            'image.Create'
    ],
    stores: ['CreateCombo'],
    init: function() {
        this.control({
            'globalnav button#logout': {
                click: this.logout
            },
            
            'globalnav combo#create': {
            	select: this.create
            }
        });
    },
    
    create : function(combo, records, eOpts){
    	var opt = records[0].data.value;
    	if(opt == 'image'){
    		
    		var editTabPanel = combo.up("viewport").down("tabpanel#mainContainer");
    		
    		var imagecreate = combo.up("viewport").down("tabpanel#mainContainer imagecreate");
    		
    		if(imagecreate){
    			editTabPanel.setActiveTab(imagecreate);
    		}else{
    			var view = Ext.widget('imagecreate');
    			view.closable = true;
    			editTabPanel.insert(0,view);
    			editTabPanel.setActiveTab(0);
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