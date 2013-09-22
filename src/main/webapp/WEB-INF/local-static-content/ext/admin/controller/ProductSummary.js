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
				click : this.editProduct
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
	
	editProduct : function(view, cell, row, col, e) {
		view.getSelectionModel().select(row, false);
		var contentPanel = view.up("viewport").down("tabpanel#mainContainer");
		var editor = Ext.create("AM.view.product.Edit", {
			title : 'Edit Product'
		});
		var productForm = editor.down("form");

		contentPanel.insert(0, editor);
		contentPanel.setActiveTab(0);
		
		productForm.load({
			// pass 2 arguments to server side getBasicInfo
			// method (len=2)
			params : {
				id : view.getSelectionModel().getSelection()[0]
				.get('id')
			},
			success : function (form, action){
				productForm.up("producteditor").setProduct(action.result.data);
				productForm.getForm().setValues(action.result.data);
			}
		})
	}

});