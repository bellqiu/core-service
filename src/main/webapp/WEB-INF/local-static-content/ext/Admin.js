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

	controllers : [ 'GlobalNav', 'Setting', 'CategoryTree', 'HTML', 'Country',
			'Product', 'Currency', 'User', 'ProductSummary', 'TabProduct',
			'Coupon' ],

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

Ext.namespace('Ext.ux','Ext.ux.plugins');  
Ext.ux.plugins.HtmlEditorImageInsert=function(config) {  
    config=config||{};  
    Ext.apply(this,config);  
    this.init=function(htmlEditor) {  
        this.editor=htmlEditor;  
        this.editor.on('render',onRender,this);  
    };  
    this.imageInsertConfig={  
        popTitle: config.popTitle||'Image URL',  
        popMsg: config.popMsg||'Please select the URL of the image you want to insert:',  
        popWidth: config.popWidth||350,  
        popValue: config.popValue||''  
    }  
    this.imageInsert=function() {  
        var range;  
        if(Ext.isIE)  
            range=this.editor.doc.selection.createRange();  
        var msg=Ext.MessageBox.show({  
            title: this.imageInsertConfig.popTitle,  
            msg: this.imageInsertConfig.popMsg,  
            width: this.imageInsertConfig.popWidth,  
            buttons: Ext.MessageBox.OKCANCEL,  
            prompt: true,  
            value: this.imageInsertConfig.popValue,  
            scope: this,  
            fn: function(btn,text) {  
                if(btn=='ok') {  
                    if(Ext.isIE)  
                        range.select();  
                    this.editor.relayCmd('insertimage',text);  
                }  
            }  
        });  
        /*var win=msg.getDialog()  
        win.show.defer(200,win) */ 
    }  
    function onRender(){  
        if( ! Ext.isSafari){  
            this.editor.toolbar.add({  
                itemId : 'htmlEditorImage',  
                cls : 'x-btn-icon x-edit-insertimage',  
                enableToggle : false,  
                scope : this,  
                handler : function(){  
                    this.imageInsert();  
                },  
                text : 'image',
                clickEvent : 'mousedown',  
                tooltip : config.buttonTip ||   
                {  
                    title : 'Insert Picture',  
                    text : 'Insert picture to editor',  
                    cls : 'x-html-editor-tip'  
                },  
                tabIndex :- 1  
            });  
        }  
    }  
} 