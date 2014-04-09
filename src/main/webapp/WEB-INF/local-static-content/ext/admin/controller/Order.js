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
				click : this.actionOrder
			},
			'ordermanager toolbar button#refreshOrder' : {
				click : this.refreshOrder
			},
			
			'ordereditor button#saveOrder' : {
				click : this.updateOrder
			}
			/*
			'ordereditor form#orderDetailForm' : {
				beforeaction : this.beforeaction,
				actioncomplete : this.actioncomplete,
				actionfailed : this.actionfailed
			}
			 */

		});
	},

	actioncomplete : function(form) {
		form.findField("orderSN").up("ordereditor").setLoading(
				false);
	},

	actionfailed : function(form, action) {
		form.findField("orderSN").up("ordereditor").setLoading(
				false);
		Ext.example.msg('<font color="red">Error</font>',
				'<font color="red">' + action.type
						+ " Failed </font>");
	},

	beforeaction : function(form) {
		form.findField("orderSN").up("ordereditor").setLoading(
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
			/*if (filters.useremail.length > 0) {
				filterObj.push({
					property : 'useremail',
					value : filters.useremail
				});
				filtered = true;
			}*/
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

	actionOrder : function(view, cell, row, col, e) {
		var m = e.getTarget().src.match(/.*\/images\/(\w+)\.\w+\b/);
		if (m) {
			view.getSelectionModel().select(row, false);
			switch (m[1]) {
			case 'edit':
				var contentPanel = view.up("viewport").down("tabpanel#mainContainer");
				var editor = Ext.create("AM.view.order.OrderDetail", {
					title : 'Edit Order'
				});
				var orderForm = editor.down("form#orderDetailForm");

				contentPanel.insert(0, editor);
				contentPanel.setActiveTab(0);

				orderForm.load({
					params : {
						id : view.getSelectionModel().getSelection()[0].get('id')
					},
					success : function (form, action){
						editor.setOrder(action.result.data);
						orderForm.getForm().setValues(action.result.data);
						editor.setTitle('O-' + action.result.data.orderSN);
						var productItems = action.result.data.items;
						var productContainer = editor.down('container#productInfo');
						for(var i = 0; i < productItems.length; i++) {
							var pInfoEditor = Ext.create('AM.view.order.OrderProductInfo', {
							});
							var imgPanel = pInfoEditor.down('panel#imagePanel');
							imgPanel.html = '<a href="' + site.domain + '/' + productItems[i].productSummary.name  + '"><img src="' + site.resourceServer + site.webResourcesFolder + site.productImageResourcesFolder + "/"+ productItems[i].productSummary.imageURL + '" width="50px" target="_blank"/></a>';
							var pTitle = pInfoEditor.down('label#productTitle');
							//pTitle.html = '<a href="' + site.domain + '/' + productItems[i].productSummary.name  + '">' + productItems[i].productSummary.title + '</a>';
							pTitle.setText(productItems[i].productSummary.title);
							productContainer.add(pInfoEditor);
							var pQuantity = pInfoEditor.down('label#quantityValue');
							pQuantity.setText(productItems[i].quantity);
							
							var pOptContainer = pInfoEditor.down('container#selectedOpts');
							var pSelectedOpts = productItems[i].selectedOpts;
							for(var k in pSelectedOpts) {
								var labelOpt = Ext.create('Ext.form.Label', {
									shrinkWrap : 2,
									text : k + '(' + pSelectedOpts[k] + ')'
								});
								pOptContainer.add(labelOpt)
							}
						}
					}
				});
				break;
			case 'pop': 
				var sel = view.getSelectionModel().getSelection()[0];
				var win=window.open("/od/orderDetail?orderId="+sel.get("id")+"&token="+sel.get("token"), '_blank');
				win.focus();
				break;
			}
		}
		/*var contentPanel = grid.up("viewport").down("tabpanel#mainContainer");
		var editor = Ext.create("AM.view.order.OrderDetail", {
			title : 'Edit Order'
		});
		var orderForm = editor.down("form#orderDetailForm");

		contentPanel.insert(0, editor);
		contentPanel.setActiveTab(0);

		orderForm.load({
			params : {
				id : grid.getStore().getAt(index).get('id')
			},
			success : function (form, action){
				editor.setOrder(action.result.data);
				orderForm.getForm().setValues(action.result.data);
				editor.setTitle('O-' + action.result.data.orderSN);
				var productItems = action.result.data.items;
				var productContainer = editor.down('container#productInfo');
				for(var i = 0; i < productItems.length; i++) {
					var pInfoEditor = Ext.create('AM.view.order.OrderProductInfo', {
					});
					var imgPanel = pInfoEditor.down('panel#imagePanel');
					imgPanel.html = '<a href="' + site.domain + '/' + productItems[i].productSummary.name  + '"><img src="' + site.resourceServer + site.webResourcesFolder + site.productImageResourcesFolder + "/"+ productItems[i].productSummary.imageURL + '" width="50px" target="_blank"/></a>';
					var pTitle = pInfoEditor.down('label#productTitle');
					//pTitle.html = '<a href="' + site.domain + '/' + productItems[i].productSummary.name  + '">' + productItems[i].productSummary.title + '</a>';
					pTitle.setText(productItems[i].productSummary.title);
					productContainer.add(pInfoEditor);
					var pQuantity = pInfoEditor.down('label#quantityValue');
					pQuantity.setText(productItems[i].quantity);
					
					var pOptContainer = pInfoEditor.down('container#selectedOpts');
					var pSelectedOpts = productItems[i].selectedOpts;
					for(var k in pSelectedOpts) {
						var labelOpt = Ext.create('Ext.form.Label', {
							shrinkWrap : 2,
							text : k + '(' + pSelectedOpts[k] + ')'
						});
						pOptContainer.add(labelOpt)
					}
				}
			}
		});*/
   },

   updateOrder : function(btn) {
	   var editor = btn.up("ordereditor");
	   var orderDetail = editor.getOrder();
	   orderDetail.orderStatus = editor.down("combo#orderStatus").getValue();
	   orderDetail.traceInfo = editor.down("textfield#traceInfo").getValue();
	   var orderForm = editor.down("form");
	   orderForm.setLoading(true);
	   orderDirectService.saveOrderInfo(orderDetail, function(data, rs, suc){
		   if(suc && data){
			   editor.setOrder(data);
			}else if(rs && rs.type == 'exception'){
				Ext.example.msg('<font color="red">Error</font>',
					'<font color="red">' + rs.message
							+ " </font>");
			}
		   orderForm.setLoading(false);
	   });
	}
});