Ext.define('AM.store.ManagerCombo', {
    extend: 'Ext.data.Store',
    fields: ['name', 'value'],
    data: [
        {name: 'Category',    value: 'category'},
        {name: 'Product', value: 'product'},
        {name: 'HTML', value: 'html'},
        {name: 'Country', value: 'country'},
        {name: 'Setting', value: 'setting'},
        {name: 'Currency', value: 'currency'},
        {name: 'User', value: 'user'},
        {name: 'TabProduct', value: 'tabproduct'}, 
        {name: 'Coupon', value: 'coupon'}
    ]
});