 Ext.define('AM.model.Blogger', {
        extend: 'Ext.data.Model',
        fields: [
           {name:'id',type:'int'},
           {name:'type',type:'string'},
           {name:'description',type:'string'},
           {name:'name',type:'string'},
           {name:'status',type:'string'},
           {name:'priority',type:'int'}
        ],
        idProperty: 'id'
    });