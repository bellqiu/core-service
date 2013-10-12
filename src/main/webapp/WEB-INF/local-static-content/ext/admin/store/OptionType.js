Ext.define('AM.store.OptionType', {
    extend: 'Ext.data.Store',
    fields: ['name', 'type'],
    data: [
        {name: 'Text',    type: 'TEXT'},
        {name: 'Single Text', type: 'SINGLE_TEXT_LIST'},
        {name: 'Multi Text', type: 'MULTI_TEXT_LIST'},
        {name: 'Single Icon', type: 'SINGLE_ICON_LIST'},
        {name: 'Multi Icon', type: 'MULTI_ICON_LIST'}
    ]
});