 Ext.define('AM.model.CategoryDetail', {
        extend: 'Ext.data.Model',
        fields: [
           {name:'id',type:'int'},
           {name:'displayName',type:'string'},
           {name:'iconUrl',type:'string'},
           {name:'url',type:'string'},
           {name:'relatedKeyword',type:'string'},
           {name:'pageTitle',type:'string'},
           {name:'marketContent',type:'string'},
           {name:'description',type:'string'},
           {name:'name',type:'string'},
           {name:'type',type:'string'},
           {name:'parentId',type:'int'}
        ],
        idProperty: 'id'
});