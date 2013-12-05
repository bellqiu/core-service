Ext.define('AM.store.OptionType', {
    extend: 'Ext.data.Store',
    fields: ['name', 'type'],
    data: [
        {name: '下拉列表', type: 'SINGLE_TEXT_LIST'},
        {name: '平铺列表', type: 'SINGLE_ICON_LIST'},
        {name: '自定义尺寸', type: 'SINGLE_INPUT_CHECKBOX'}
        /*,{name: 'Text',    type: 'TEXT'},
        {name: 'Multi Text', type: 'MULTI_TEXT_LIST'},
        {name: 'Multi Icon', type: 'MULTI_ICON_LIST'}*/
    ]
});