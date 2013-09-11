Ext.define('AM.store.UserRole', {
    extend: 'Ext.data.Store',
    fields: ['name', 'role'],
    data: [
        {name: 'User',  role: 'USER'},
        {name: 'Admin', role: 'ADMIN'}
    ]
});