Ext.define('AM.controller.Currency', {
	extend : 'Ext.app.Controller',
	views : [ 'currency.CurrencyManager' ],

	models : [ 'Currency' ],

	stores : [ 'Currency' ],

	init : function() {
		this.control({
			'currencymanager button#searchCurrency' : {
				click : this.searchCurrency
			},
			'currencymanager button#resetCurrency' : {
				click : function(btn) {
					btn.up("form").getForm().clearInvalid();
					btn.up("form").getForm().reset();
				}
			},
			'currencymanager button#synchronize' : {
				click : this.synchronizeGrid
			},

			'currencymanager button#newCurrency' : {
				click : this.newCurrency
			},
			'currencymanager gridpanel actioncolumn' : {
				click : this.deleteCurrency
			}

		});
	},

	deleteCurrency : function(grid, el, index) {

		Ext.MessageBox.confirm('Delete', 'Are you sure ?', function(btn) {
			if (btn === 'yes') {
				grid.getStore().removeAt(index);
				grid.getStore().sync();
			} else {
				Ext.example.msg('Cancel', 'Delete canceled');
			}
		});

	},

	newCurrency : function(btn) {
		var store = btn.up('gridpanel').getStore();
		var d = Ext.Date.clearTime(new Date());
		var rec = new AM.model.Currency({
			name : '',
			code : '',
			exchangeRateBaseOnDefault : '1',
			defaultCurrency : false,
			status : 'ACTIVE',
			createDate : d,
			updateDate : d
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

	searchCurrency : function(btn) {
		if (btn.up('form').getForm().isValid()) {
			// Ext.MessageBox.alert('Thank you!', 'Your inquiry has been sent.
			// We will respond as soon as possible.');
			store = this.getCurrencyStore();
			filters = btn.up('form#searchCurrencyForm').getForm().getValues();
			var filterObj = [];
			var filtered = false;
			if (filters.name.length > 0) {
				filterObj.push({
					property : 'name',
					value : filters.name
				});
				filtered = true;
			}

			if (filters.code.length > 0) {
				filterObj.push({
					property : 'code',
					value : filters.code
				});
				filtered = true;
			}

			if (filters.exchangeRateBaseOnDefault.length > 0) {
				filterObj.push({
					property : 'exchangeRateBaseOnDefault',
					value : filters.exchangeRateBaseOnDefault
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