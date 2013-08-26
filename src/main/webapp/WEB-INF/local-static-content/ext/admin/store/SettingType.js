Ext.define('AM.store.SettingType', {
    extend: 'Ext.data.Store',
    fields: ['name', 'type'],
    data: [
        {name: 'Number',    type: 'NUMBER'},
        {name: 'String', type: 'STRING'},
        {name: 'Html', type: 'HTML'}
    ]
});