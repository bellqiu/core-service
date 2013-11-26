 Ext.define('AM.model.ProductSummary', {
        extend: 'Ext.data.Model',
        fields: [
           {name:'id',type:'int'},
           {name:'name',type:'string'},
           {name:'title',type:'string'},
           {name:'price',type:'float'},
           {name:'actualPrice',type:'float'},
           {name:'categoryBreadcrumb',type:'String'},
           {name:'keywords',type:'String'},
           {name:'tags',type:'String'},
           {name:'abstractText',type:'String'},
           {name:'overrideUrl',type:'String'},
           {name:'sku',type:'String'},
           {name:'createDate', type:'date', defaultValue : 0 , convert : function(v,record){return new Date(v)}},
        ],
        idProperty: 'id'
    });