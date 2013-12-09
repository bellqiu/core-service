 Ext.define('AM.model.Option', {
        extend: 'Ext.data.Model',
        fields: [
           {name:'id',type:'int'},
           {name:'defaultValue',type:'string'},
           {name:'name',type:'string'},
           {name:'type',type:'string'},
           {name:'customize',type:'boolean'},
           {name:'htmlKey',type:'string'}
        ],
        idProperty: 'id'
    });