Ext.define('AM.controller.Coupon', {
	extend : 'Ext.app.Controller',
	views : [ 'coupon.CouponManager'/*, 'coupon.CouponDetail'*/ ],

	models : [ 'Coupon' ],

	stores : [ 'Coupon' ],

	init : function() {
		this.control({
			'couponmanager button#searchCoupon' : {
				click : this.searchCoupon
			},
			'couponmanager button#resetCoupon' : {
				click : function(btn) {
					btn.up("form").getForm().clearInvalid();
					btn.up("form").getForm().reset();
				}
			},
			'couponmanager button#synchronize' : {
				click : this.synchronizeGrid
			},

			'couponmanager button#newCoupon' : {
				click : this.newCoupon
			},
			'couponmanager gridpanel actioncolumn' : {
				click : this.actionCouponDetail
			},
			'couponmanager toolbar button#refreshCoupon' : {
				click : this.refreshCouponManager
			},
			
			'couponeditor button#saveCoupon' : {
				click : this.saveCoupon
			},
			
			'couponeditor form' : {
				beforeaction : this.beforeaction,
				actioncomplete : this.actioncomplete,
				actionfailed : this.actionfailed
			}

		});
	},
	
	actioncomplete : function(form) {
		form.findField("id").up("couponeditor").setLoading(
				false);
	},

	actionfailed : function(form, action) {
		form.findField("id").up("couponeditor").setLoading(
				false);
		Ext.example.msg('<font color="red">Error</font>',
				'<font color="red">' + action.type
						+ " Failed </font>");
	},

	beforeaction : function(form) {
		form.findField("id").up("couponeditor").setLoading(
				true, true);
	},

	newCoupon : function(btn) {
		var store = btn.up('gridpanel').getStore();

		var rec = new AM.model.Coupon({
			name : '',
			code : '',
			value : 0,
			usedCount : 0,
			minCost : 0,
			maxUsedCount : 1,
			desc : '',
			status : 'ACTIVE',
			createDate : Ext.Date.clearTime(new Date()),
			updateDate : Ext.Date.clearTime(new Date()),
			startDate : null,
			endDate : null
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

	searchCoupon : function(btn) {
		if (btn.up('form').getForm().isValid()) {
			// Ext.MessageBox.alert('Thank you!', 'Your inquiry has been
			// sent.
			// We will respond as soon as possible.');
			store = this.getCouponStore();
			filters = btn.up('form#searchCouponForm').getForm().getValues();
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
	},

	refreshCouponManager : function(btn) {
		/*if (btn.up('form').getForm().isValid()) {
			
		}*/
		var queryPanel = btn.up("viewport").down(
		"tabpanel#managerContainer couponmanager");
		this.getCouponStore().load();
	},

	actionCouponDetail : function(view, cell, row, col, e) {
		var m = e.getTarget().src.match(/.*\/images\/(\w+)\.\w+\b/);
		if (m) {
			view.getSelectionModel().select(row, false)
			switch (m[1]) {
			case 'edit':
				var contentPanel = view.up("viewport").down(
						"tabpanel#mainContainer");

				var editor = Ext.create("AM.view.coupon.CouponDetail", {
					title : 'Edit Coupon'
				});

				contentPanel.insert(0, editor);
				contentPanel.setActiveTab(0);

				var form = editor.down("form");

				form.load({
					// pass 1 argument to server side getBasicInfo
					// method (len=1)
					params : {
						id : view.getSelectionModel().getSelection()[0]
								.get('id')
					},
					success : function (form, action){
						action.result.data.startDate = new Date(action.result.data.startDate);
						action.result.data.endDate = new Date(action.result.data.endDate);
						form.setValues(action.result.data);
					}
				})
				
				break;
			case 'delete':
				Ext.MessageBox.confirm('Delete', 'Are you sure ?',
						function(btn) {
							if (btn === 'yes') {
								view.getStore().remove(view.getSelectionModel()
														.getSelection()[0]);
								view.getStore().sync();
							} else {
								Ext.example.msg('Cancel', 'Delete canceled');
							}
						});

				break;
			}
		}
	},

	saveCoupon : function(btn) {
		btn.up("couponeditor").down("form").getForm().submit({

			success : function(form, action) {
				action.result.resultForm.startDate = new Date(action.result.resultForm.startDate);
				action.result.resultForm.endDate = new Date(action.result.resultForm.endDate);
				form.setValues(action.result.resultForm);
			},
		});
	}
});