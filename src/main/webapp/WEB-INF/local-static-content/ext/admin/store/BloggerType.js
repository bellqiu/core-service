Ext.define('AM.store.BloggerType', {
    extend: 'Ext.data.Store',
    fields: ['name', 'type'],
    data: [
        {name: 'PDF',    type: 'PDF'},
        {name: 'HTML', type: 'HTML'},
        {name: 'JSP', type: 'JSP'},
        {name: 'TXT', type: 'TXT'}
    ]
});