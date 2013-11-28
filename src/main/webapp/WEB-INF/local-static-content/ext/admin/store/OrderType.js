Ext.define('AM.store.OrderType', {
    extend: 'Ext.data.Store',
    fields: ['name', 'status'],
    data: [
           {name: 'Pending', status: 'PENDING'},
           {name: 'Canceled', status: 'CANCELED'},
           {name: 'Paid', status: 'PAID'},
           {name: 'Onshopping',    status: 'ONSHOPPING'},
           {name: 'Shipping', status: 'SHIPPING'},
           {name: 'Completed', status: 'COMPLETED'}
    ]
});