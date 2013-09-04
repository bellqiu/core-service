Ext.define('AM.controller.Country', {
    extend: 'Ext.app.Controller',
    views: [
            'country.CountryManager'
    ],
    
    models : ['Country'],
    
    stores: ['Country'],
    
    init: function() {
    	 this.control({
             'countrymanager button#searchCountry': {
                 click: this.searchCountry
             },
             'countrymanager button#resetCountry': {
                 click: function(btn){
                	 btn.up("form").getForm().clearInvalid();
                	 btn.up("form").getForm().reset();
                 }
             },
             'countrymanager button#synchronize': {
                 click: this.synchronizeGrid
             },
             
             'countrymanager button#newCountry': {
                 click: this.newCountry
             },
             'countrymanager gridpanel actioncolumn': {
                 click: this.deleteCountry
             }
             
         });
    },
    
    deleteCountry :function(grid, el, index){
    	Ext.MessageBox.confirm('Delete', 'Are you sure ?', function(btn) {
			if (btn === 'yes') {
				grid.getStore().removeAt(index);
				grid.getStore().sync();
			} else {
				Ext.example.msg('Cancel', 'Delete canceled');
			}
		});
   },
    
    newCountry :function(btn){
    	 var store = btn.up('gridpanel').getStore();
    	 
    	 var rec = new AM.model.Country({
             code: '',
             abbrCode: '',
             normalDeliveryPrice : '',
             advanceDeliveryPrice : '',
             freeDeliveryPrice : '',
             freeAdvanceDeliveryPrice : '',
             status: 'ACTIVE',
             createDate: Ext.Date.clearTime(new Date()),
             updateDate: Ext.Date.clearTime(new Date())
         });
    	 store.insert(0,rec);
    	 btn.up('gridpanel').getPlugin().startEditByPosition({
    	        row: 0, 
    	        column: 0
    	 });
    },
    
    synchronizeGrid : function(btn){
    	
    	btn.up('gridpanel').getStore().sync();
    },
    
    searchCountry : function(btn){
    	 if (btn.up('form').getForm().isValid()) {
             //Ext.MessageBox.alert('Thank you!', 'Your inquiry has been sent. We will respond as soon as possible.');
    		 store = this.getCountryStore();
    		 filters = btn.up('form#searchFormCountry').getForm().getValues();
    		 var filterObj = [];
    		 var filtered = false;
    		 if(filters.code.length > 0){
    			 filterObj.push({
    				 property : 'code',
    				 value : filters.code
    			 });
    			 filtered = true;
    		 }
    		 
    		 if(filters.abbrCode.length > 0){
    			 filterObj.push({
    				 property : 'abbrCode',
    				 value : filters.abbrCode
    			 });
    			 filtered = true;
    		 }
    		 
    		 if(filters.normalDeliveryPrice.length > 0){
    			 filterObj.push({
    				 property : 'normalDeliveryPrice',
    				 value : filters.normalDeliveryPrice
    			 });
    			 filtered = true;
    		 }
    		 
    		 if(filters.advanceDeliveryPrice.length > 0){
    			 filterObj.push({
    				 property : 'advanceDeliveryPrice',
    				 value : filters.advanceDeliveryPrice
    			 });
    			 filtered = true;
    		 }
    		 
    		 if(filters.freeDeliveryPrice.length > 0){
    			 filterObj.push({
    				 property : 'freeDeliveryPrice',
    				 value : filters.freeDeliveryPrice
    			 });
    			 filtered = true;
    		 }
    		 
    		 if(filters.freeAdvanceDeliveryPrice.length > 0){
    			 filterObj.push({
    				 property : 'freeAdvanceDeliveryPrice',
    				 value : filters.freeAdvanceDeliveryPrice
    			 });
    			 filtered = true;
    		 }
    		 
    		 if(filtered){
    			 store.clearFilter(true);
    			 store.filter(filterObj);
    		 }else{
    			 store.clearFilter(true);
    			 store.load(1);
    		 }
         }
    }
});