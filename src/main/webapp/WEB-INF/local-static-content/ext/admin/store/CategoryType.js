Ext.define('AM.store.CategoryType', {
    extend: 'Ext.data.Store',
    fields: ['name', 'type'],
    data: [
        {name: 'LINK',    type: 'LINK'},
        {name: 'SPECIAL_OFFER', type: 'SPECIAL_OFFER'},
        {name: 'ICON', type: 'ICON'},
        {name: 'NAVIGATION', type: 'NAVIGATION'}
    ]
});