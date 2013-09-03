Ext.define('AM.controller.HTML', {
    extend: 'Ext.app.Controller',
    views: [
            'html.HtmlManager'
    ],
    
    models : ['HTML'],
    
    stores: ['HTML'],
    
    init: function() {
    	 this.control({
             'htmlmanager button#searchHtml': {
                 click: this.searchHTML
             },
             'htmlmanager button#resetHtml': {
                 click: function(btn){
                	 btn.up("form").getForm().clearInvalid();
                	 btn.up("form").getForm().reset();
                 }
             },
             'htmlmanager button#synchronize': {
                 click: this.synchronizeGrid
             },
             
             'htmlmanager button#newHTML': {
                 click: this.newHTML
             },
             'htmlmanager gridpanel actioncolumn': {
                 click: this.deleteHTML
             }
             
         });
    },
    
    deleteHTML :function(grid, el, index){
    	grid.getStore().removeAt(index);
    	grid.getStore().sync();
   },
    
    newHTML :function(btn){
    	 var store = btn.up('gridpanel').getStore();
    	 
    	 var rec = new AM.model.HTML({
             name: '',
             content: '',
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
    
    searchHTML : function(btn){
    	 if (btn.up('form').getForm().isValid()) {
             //Ext.MessageBox.alert('Thank you!', 'Your inquiry has been sent. We will respond as soon as possible.');
    		 store = this.getHTMLStore();
    		 filters = btn.up('form#searchHTMLForm').getForm().getValues();
    		 var filterObj = [];
    		 var filtered = false;
    		 if(filters.name.length > 0){
    			 filterObj.push({
    				 property : 'name',
    				 value : filters.name
    			 });
    			 filtered = true;
    		 }
    		 
    		 if(filters.content.length > 0){
    			 filterObj.push({
    				 property : 'content',
    				 value : filters.content
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