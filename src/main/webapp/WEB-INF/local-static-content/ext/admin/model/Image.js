 Ext.define('AM.model.Image', {
        extend: 'Ext.data.Model',
        fields: [
           {name:'id',type:'int'},
           {name:'name',type:'string'},
           {name:'iconUrl',type:'string'},
        ],
        idProperty: 'id'
    });