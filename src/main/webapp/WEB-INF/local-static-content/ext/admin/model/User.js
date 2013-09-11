 Ext.define('AM.model.User', {
        extend: 'Ext.data.Model',
        fields: [
           {name:'id',type:'int'},
           {name:'email',type:'string'},
           {name:'password',type:'string'},
           {name:'enabled',type:'boolean'},
           {name:'roles',type:'string', convert : function(v,record){
        	   return Ext.Array.from(v);
           }},
        ],
        idProperty: 'id'
    });