 Ext.define('AM.model.HTMLDetail', {
        extend: 'Ext.data.Model',
        fields: [
           {name:'id',type:'int'},
           {name:'content',type:'string'},
           {name:'name',type:'string'},
        ],
        idProperty: 'id'
    });