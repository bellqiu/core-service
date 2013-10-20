Ext.define('AM.controller.ProductSummary', {
	extend : 'Ext.app.Controller',
	views : [ 'product.ProductSummaryManager' ],

	models : [ 'ProductSummary' ],

	stores : [ 'ProductSummary' ],

	init : function() {
		this.control({
			'productsummarymanager button#searchProductSummary' : {
				click : this.searchProductSummary
			},
			'productsummarymanager button#resetProductSummary' : {
				click : function(btn) {
					btn.up("form").getForm().clearInvalid();
					btn.up("form").getForm().reset();
				}
			},
			'productsummarymanager button#synchronize' : {
				click : this.synchronizeGrid
			},

			'productsummarymanager toolbar button#refreshProductSummary' : {
				click : this.refreshProductSummary
			}, 
			
			'productsummarymanager gridpanel actioncolumn' : {
				click : this.acitonProduct
			},
			
			'productsummarymanager form#searchProductSummaryForm combo' : {
				afterrender : this.beforeadd
			}
			
		});
	},

	synchronizeGrid : function(btn) {
		btn.up('gridpanel').getStore().sync();
	},

	searchProductSummary : function(btn) {
		if (btn.up('form').getForm().isValid()) {
			// Ext.MessageBox.alert('Thank you!', 'Your inquiry has been
			// sent.
			// We will respond as soon as possible.');
			store = this.getProductSummaryStore();
			filters = btn.up('form#searchProductSummaryForm').getForm().getValues();
			var filterObj = [];
			var filtered = false;
			if (filters.name.length > 0) {
				filterObj.push({
					property : 'name',
					value : filters.name
				});
				filtered = true;
			}

			if (filters.title.length > 0) {
				filterObj.push({
					property : 'title',
					value : filters.title
				});
				filtered = true;
			}
			if (filters.price.length > 0) {
				filterObj.push({
					property : 'price',
					value : filters.price
				});
				filtered = true;
			}
			if (filters.actualPrice.length > 0) {
				filterObj.push({
					property : 'actualPrice',
					value : filters.actualPrice
				});
				filtered = true;
			}
			active=btn.up('form#searchProductSummaryForm').down('checkbox').getValue();
			if (active) {
				filterObj.push({
					property : 'active',
					value : 'true',
				});
			} else {
				filterObj.push({
					property : 'active',
					value : 'false',
				});
			}
			filtered = true;

			if (filtered) {
				store.clearFilter(true);
				store.filter(filterObj);
			} else {
				store.clearFilter(true);
				store.load(1);
			}
		}
	},

	refreshProductSummary : function(btn) {
		/*if (btn.up('form').getForm().isValid()) {
			
		}*/
		var queryPanel = btn.up("viewport").down(
		"tabpanel#managerContainer productsummarymanager");
		this.getProductSummaryStore().load();
	}, 
	
	acitonProduct : function(view, cell, row, col, e) {
		var m = e.getTarget().src.match(/.*\/images\/(\w+)\.\w+\b/);
		if (m) {
			view.getSelectionModel().select(row, false)
			switch (m[1]) {
			case 'edit':
				var contentPanel = view.up("viewport").down("tabpanel#mainContainer");
				var editor = Ext.create("AM.view.product.Edit", {
					title : 'Edit Product'
				});
				var productForm = editor.down("form");

				contentPanel.insert(0, editor);
				contentPanel.setActiveTab(0);
		
				productForm.load({
					params : {
						id : view.getSelectionModel().getSelection()[0]
						.get('id')
					},
					success : function (form, action){
						productForm.up("producteditor").setProduct(action.result.data);
						productForm.getForm().setValues(action.result.data);
					}
				});
				break;
			case 'delete':
				Ext.MessageBox.confirm('Delete', 'Are you sure ?',
						function(btn) {
							if (btn === 'yes') {
								store = view.getStore();
								product = view.getSelectionModel().getSelection()[0].data;
								productDirectService.setProductAsDelete(product, function(data, rs, suc){
									if(suc && data){
										view.getStore().remove(view.getSelectionModel()
												.getSelection()[0]);
									}else if(rs && rs.type == 'exception'){
										Ext.example.msg('<font color="red">Error</font>',
												'<font color="red">' + rs.message
												+ " </font>");3
									}
								});
							} else {
								Ext.example.msg('Cancel', 'Delete canceled');
							}
						});
				break;
			}
		}
	},
	
	beforeadd : function (form){
		console.log("OK1");
		var store = form.getStore();
		/*da=categoryDirectService.loadAllCategoryName(function(data, rs, suc){
			if(suc && data){
				console.log("OK2");
				var categoryNames = [];
				for(var index in data) {
					var rec = {};
					rec["name"] = data[index];
					rec["type"] = data[index];
					var rec = new Ext.data.Record(myArray)
					categoryNames.push(rec);
				}
				store.loadData([{name:1},{name:2}]);
				//store.sync();
				//store.loadData(data);
			}else if(rs && rs.type == 'exception'){
				Ext.example.msg('<font color="red">Error</font>',
						'<font color="red">' + rs.message
								+ " </font>");3
			}
			
		});*/
		var myArray = {};
		myArray["name"]="a";
		myArray["type"]="b";
		store.add(myArray);
		console.log("OK3");
	}

});