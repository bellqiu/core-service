Ext.define('AM.store.CreateCombo', {
    extend: 'Ext.data.Store',
    fields: ['name', 'value'],
    data: [
        {name: 'User',    value: 'user'},
        {name: 'Image', value: 'image'}
    ]
});