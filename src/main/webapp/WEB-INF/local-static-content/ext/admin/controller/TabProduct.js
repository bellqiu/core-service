Ext.define('AM.controller.TabProduct', {
	extend : 'Ext.app.Controller',
	views : [ 'product.TabProductManager' ],

	models : [ 'TabProduct' ],

	stores : [ 'TabProduct' ],

	init : function() {
		this.control({
			'tabproductmanager button#searchTabProduct' : {
				click : this.searchTabProduct
			},
			'tabproductmanager button#resetTabProduct' : {
				click : function(btn) {
					btn.up("form").getForm().clearInvalid();
					btn.up("form").getForm().reset();
				}
			},
			'tabproductmanager button#synchronize' : {
				click : this.synchronizeGrid
			},

			'tabproductmanager button#newTabProduct' : {
				click : this.newTabProduct
			},
			'tabproductmanager gridpanel actioncolumn' : {
				click : this.actionTabProduct
			},
			'tabproducteditor form#tabProductForm' : {
				actioncomplete : this.initTabProductData,
				beforeaction : this.beforeaction,
				actionfailed : this.actionfailed
			},
			'tabproducteditor gridpanel#productSummary' : {
				cellkeydown : this.bindDeleteKey,
				render : this.initProductSummaryDragAndDrop,
				beforeDestroy : this.destoryProductSummaryView
			},
			
			'tabproducteditor button#saveTabProduct' : {
				click : this.submitTabProduct
			}
			
		});
	},

	actionTabProduct : function(view, cell, row, col, e) {
		var m = e.getTarget().src.match(/.*\/images\/(\w+)\.\w+\b/);
		if (m) {
			view.getSelectionModel().select(row, false)
			switch (m[1]) {
			case 'edit':
				var contentPanel = view.up("viewport").down(
						"tabpanel#mainContainer");
				// TODO
				var editor = Ext.create("AM.view.product.TabProductDetail", {
					title : 'Edit TabProduct'
				});
				
				contentPanel.insert(0, editor);
				contentPanel.setActiveTab(0);
				
				
				var productForm = editor.down("form");
				
				productForm.load({
					// pass 2 arguments to server side getBasicInfo
					// method (len=2)
					params : {
						id : view.getSelectionModel().getSelection()[0]
						.get('id')
					}, 
					success : function (form, action){
						productForm.up("tabproducteditor").setTabproduct(action.result.data);
						productForm.getForm().setValues(action.result.data);
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
	
	newTabProduct : function(btn) {
		var store = btn.up('gridpanel').getStore();
		var rec = new AM.model.TabProduct({
			name : ''
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

	searchTabProduct : function(btn) {
		if (btn.up('form').getForm().isValid()) {
			// Ext.MessageBox.alert('Thank you!', 'Your inquiry has been
			// sent.
			// We will respond as soon as possible.');
			store = this.getTabProductStore();
			filters = btn.up('form#searchTabProductForm').getForm().getValues();
			var filterObj = [];
			var filtered = false;
			if (filters.name.length > 0) {
				filterObj.push({
					property : 'name',
					value : filters.name
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
	
	initTabProductData : function (tabProductFrom){
		tabProductFrom.findField("name").up("tabproducteditor").setLoading(
				false);
		var tabproductEditor = tabProductFrom.findField("name").up("tabproducteditor");
		
		var productSummaryStore = tabproductEditor.down("gridpanel#productSummary").getStore();
		
		productSummaryStore.loadData(tabproductEditor.getTabproduct().products);
	},
	
	beforeaction : function(form) {
		form.findField("name").up("tabproducteditor").setLoading(
				true, true);
	},
	
	actionfailed : function(form, action) {
		form.findField("name").up("tabproducteditor").setLoading(
				false);
		Ext.example.msg('<font color="red">Error</font>',
				'<font color="red">' + action.type
						+ " Failed </font>");
	},
	
	bindDeleteKey : function (grid, td, cellIndex, record, tr, rowIndex, e, eOpts){
		if(e.keyCode == 46){
			var store = grid.getStore();
	        store.removeAt(rowIndex);
		}
	},
	
	initProductSummaryDragAndDrop : function (grid){
		var body = grid.body
		grid.dd = new Ext.dd.DropTarget(body, {
			ddGroup : 'grid-to-edit-parent-productsummary',
			notifyEnter : function(ddSource, e, data) {
				body.stopAnimation();
				body.highlight();
			},
			notifyDrop : function(ddSource, e, data) {
				var store = grid.getStore();

				var catlog = ddSource.dragData.records[0].data;
				
				var data = store.getRange();
				
				var existing = false;
				
				for(var i = 0; i < data.length; i++){
					if(	data[i].data.id  == catlog.id){
						existing = true;
					}
				}
				
				if(!existing){
					
					store.add(catlog);
					
					return true;
				}
				
				return false;
			}
		});
	},
	
	destoryProductSummaryView :function(grid){
		grid.dd = null;
	},
	
	submitTabProduct : function (btn){
		var tabproductEditor = btn.up("tabproducteditor");
		var productForm = tabproductEditor.down("form#tabProductForm").getForm();
		
		var tabproduct = tabproductEditor.getTabproduct();
		var productOverider = productForm.getValues();
		
		var products = [];
		
		var productSummaryStoredData = tabproductEditor.down("gridpanel#productSummary").getStore().getRange();
		
		for(var i = 0; i < productSummaryStoredData.length; i++){
			products.push(productSummaryStoredData[i].data)
		}
		
		tabproduct.products = products;
		
		tabproduct = Ext.apply(tabproduct, productOverider)
		if(productForm.isValid()){
			productForm.findField("name").up("tabproducteditor").setLoading(true);
			tabProductDirectService.saveTabProductDetail(tabproduct, function(data, rs, suc){
				if(suc && data){
					productForm.findField("name").up("tabproducteditor").setTabproduct(data);
					productForm.setValues(data);
				}else if(rs && rs.type == 'exception'){
					Ext.example.msg('<font color="red">Error</font>',
							'<font color="red">' + rs.message
									+ " </font>");
				}
				
				productForm.findField("name").up("tabproducteditor").setLoading(false);
			});
		}
	},

});