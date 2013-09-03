Ext.define('AM.controller.Setting', {
	extend : 'Ext.app.Controller',
	views : [ 'setting.Manager' ],

	models : [ 'Setting' ],

	stores : [ 'Setting', 'SettingType' ],

	init : function() {
		this.control({
			'settingmanager button#search' : {
				click : this.searchSetting
			},
			'settingmanager button#reset' : {
				click : function(btn) {
					btn.up("form").getForm().clearInvalid();
					btn.up("form").getForm().reset();
				}
			},
			'settingmanager button#synchronize' : {
				click : this.synchronizeGrid
			},

			'settingmanager button#newSetting' : {
				click : this.newSetting
			},
			'settingmanager gridpanel actioncolumn' : {
				click : this.deleteSetting
			}

		});
	},

	deleteSetting : function(grid, el, index) {

		Ext.MessageBox.confirm('Delete', 'Are you sure ?', function(btn) {
			if (btn === 'yes') {
				grid.getStore().removeAt(index);
				grid.getStore().sync();
			} else {
				Ext.example.msg('Cancel', 'Delete cancel');
			}
		});

	},

	newSetting : function(btn) {
		var store = btn.up('gridpanel').getStore();

		var rec = new AM.model.Setting({
			name : '',
			value : '',
			type : 'STRING',
			status : 'ACTIVE',
			createDate : Ext.Date.clearTime(new Date()),
			updateDate : Ext.Date.clearTime(new Date())
		});
		store.insert(0, rec);
		btn.up('gridpanel').getPlugin().startEditByPosition({
			row : 0,
			column : 0
		});
	},

	synchronizeGrid : function(btn) {

		btn.up('gridpanel').getStore().sync();
	},

	searchSetting : function(btn) {
		if (btn.up('form').getForm().isValid()) {
			// Ext.MessageBox.alert('Thank you!', 'Your inquiry has been sent.
			// We will respond as soon as possible.');
			store = this.getSettingStore();
			filters = btn.up('form#searchForm').getForm().getValues();
			var filterObj = [];
			var filtered = false;
			if (filters.name.length > 0) {
				filterObj.push({
					property : 'name',
					value : filters.name
				});
				filtered = true;
			}

			if (filters.type.length > 0) {
				filterObj.push({
					property : 'type',
					value : filters.type
				});
				filtered = true;
			}

			if (filters.value.length > 0) {
				filterObj.push({
					property : 'value',
					value : filters.value
				});
				filtered = true;
			}

			if (filtered) {
				store.clearFilter(true);
				store.filter(filterObj);
			} else {
				store.clearFilter(true);
				store.load(1);
			}
		}
	}
});