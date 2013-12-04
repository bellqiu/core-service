Ext.define('AM.controller.Order', {
	extend : 'Ext.app.Controller',
	views : [ 'order.OrderManager', 'order.OrderDetail', 'order.OrderProductInfo' ],

	models : [ 'OrderSummary' ],

	stores : [ 'OrderSummary', 'OrderType' ],

	init : function() {
		this.control({
			'ordermanager button#searchOrder' : {
				click : this.searchOrder
			},
			'ordermanager button#resetOrder' : {
				click : function(btn) {
					btn.up("form").getForm().clearInvalid();
					btn.up("form").getForm().reset();
				}
			},

			'ordermanager gridpanel actioncolumn' : {
				click : this.editOrder
			},
			'ordermanager toolbar button#refreshOrder' : {
				click : this.refreshOrder
			},
			
			/*'html_editor button#saveHTML' : {
				click : this.saveHTML
			},
			
			'html_editor form' : {
				beforeaction : this.beforeaction,
				actioncomplete : this.actioncomplete,
				actionfailed : this.actionfailed
			}*/

		});
	},

	actioncomplete : function(form) {
		form.findField("id").up("html_editor").setLoading(
				false);
	},

	actionfailed : function(form, action) {
		form.findField("id").up("html_editor").setLoading(
				false);
		Ext.example.msg('<font color="red">Error</font>',
				'<font color="red">' + action.type
						+ " Failed </font>");
	},

	beforeaction : function(form) {
		form.findField("id").up("html_editor").setLoading(
				true, true);
	},

	searchOrder : function(btn) {
		if (btn.up('form').getForm().isValid()) {
			// Ext.MessageBox.alert('Thank you!', 'Your inquiry has been
			// sent.
			// We will respond as soon as possible.');
			store = this.getOrderSummaryStore();
			filters = btn.up('form#searchOrderForm').getForm().getValues();
			var filterObj = [];
			var filtered = false;
			if (filters.orderSN.length > 0) {
				filterObj.push({
					property : 'orderSN',
					value : filters.orderSN
				});
				filtered = true;
			}
			if (filters.status.length > 0) {
				filterObj.push({
					property : 'status',
					value : filters.status
				});
				filtered = true;
			}
			if (filters.useremail.length > 0) {
				filterObj.push({
					property : 'useremail',
					value : filters.useremail
				});
				filtered = true;
			}
			if (filters.trackingId.length > 0) {
				filterObj.push({
					property : 'trackingId',
					value : filters.trackingId
				});
				filtered = true;
			}
			if (filters.sourceId.length > 0) {
				filterObj.push({
					property : 'sourceId',
					value : filters.sourceId
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

	refreshOrder : function(btn) {
		/*if (btn.up('form').getForm().isValid()) {
			
		}*/
		var queryPanel = btn.up("viewport").down(
		"tabpanel#managerContainer ordermanager");
		this.getOrderSummaryStore().load();
	},

	editOrder : function(grid, el, index){
		var contentPanel = grid.up("viewport").down("tabpanel#mainContainer");
		var editor = Ext.create("AM.view.order.OrderDetail", {
			title : 'Edit Order'
		});
		var orderForm = editor.down("form");

		contentPanel.insert(0, editor);
		contentPanel.setActiveTab(0);

		orderForm.load({
			params : {
				id : grid.getStore().getAt(index).get('id')
			},
			success : function (form, action){
				editor.setOrder(action.result.data);
				orderForm.getForm().setValues(action.result.data);
				editor.setTitle("O-" + action.result.data.orderSN);
			}
		});
   },/*function(view, cell, row, col, e) {
		var contentPanel = view.up("viewport").down("tabpanel#mainContainer");
		var editor = Ext.create("AM.view.order.OrderDetail", {
			title : 'Edit Order'
		});
		var orderForm = editor.down("form");

		contentPanel.insert(0, editor);
		contentPanel.setActiveTab(0);
		var id = view.getSelectionModel().getSelection()[0].get('id');
		console.log(id);

		orderForm.load({
			params : {
				id : view.getSelectionModel().getSelection()[0].get('id')
			},
			success : function (form, action){
				editor.setProduct(action.result.data);
				orderForm.getForm().setValues(action.result.data);
				editor.setTitle("O-" + action.result.data.orderSN);
			}
		});
	},*/

	saveHTML : function(btn) {
		btn.up("html_editor").down("form").getForm().submit({

			success : function(form, action) {
				form.setValues(action.result.resultForm);
				btn.up("html_editor").setTitle("H-" + action.result.resultForm.name);
			},
		});
	}
});