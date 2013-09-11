Ext.define('AM.controller.User', {
	extend : 'Ext.app.Controller',
	views : [ 'user.UserManager' ],

	models : [ 'User' ],

	stores : [ 'User', 'UserRole' ],

	init : function() {
		this.control({
			'usermanager button#searchUser' : {
				click : this.searchUser
			},
			'usermanager button#resetUser' : {
				click : function(btn) {
					btn.up("form").getForm().clearInvalid();
					btn.up("form").getForm().reset();
				}
			},
			'usermanager button#synchronize' : {
				click : this.synchronizeGrid
			},

			'usermanager button#newUser' : {
				click : this.newUser
			}/*,
			'usermanager gridpanel actioncolumn' : {
				click : this.deleteUser
			}*/

		});
	},

	newUser : function(btn) {
		var store = btn.up('gridpanel').getStore();
		var rec = new AM.model.User({
			email : '',
			password : '',
			role : 'STRING',
			enable : true,
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

	searchUser : function(btn) {
		if (btn.up('form').getForm().isValid()) {
			// Ext.MessageBox.alert('Thank you!', 'Your inquiry has been sent.
			// We will respond as soon as possible.');
			store = this.getUserStore();
			filters = btn.up('form#searchUserForm').getForm().getValues();
			var filterObj = [];
			var filtered = false;
			if (filters.email.length > 0) {
				filterObj.push({
					property : 'email',
					value : filters.email
				});
				filtered = true;
			}

			if (filters.roles.length > 0) {
				filterObj.push({
					property : 'roles',
					value : filters.roles
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