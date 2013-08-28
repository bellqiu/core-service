 Ext.define('AM.model.CategoryTree', {
        extend: 'Ext.data.Model',
        fields: [
           {name:'id',type:'int'},
           {name:'displayName',type:'string'},
           {name:'name',type:'string'},
           {name:'type',type:'string'}
        ],
        idProperty: 'id'
});