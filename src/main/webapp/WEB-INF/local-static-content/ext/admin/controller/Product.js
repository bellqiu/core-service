Ext.define('AM.controller.Product', {
	extend : 'Ext.app.Controller',
	views : [ 'product.Edit', 'option.OptionWindow'],

	 models : [ 'Image' ],
	 /*
	 * * 
	 stores : [ 'EmptyCategoryTree', 'EmptyImage'],*/
	
	init : function() {
		this.control({
			'globalnav button#newproduct' : {
				click : this.newProduct
			},
			
			'producteditor form#productForm' : {
				actioncomplete : this.initProductData,
				beforeaction : this.beforeaction,
				actionfailed : this.actionfailed
			},
			'producteditor gridpanel#category' : {
				cellkeydown : this.bindDeleteKey,
				render : this.initCategoryDragAndDrop,
				beforeDestroy : this.distoryCategoryView
			},
			'producteditor button#saveProduct' : {
				click : this.submitProduct
			},'producteditor gridpanel#image' : {
				cellkeydown : this.bindDeleteKey
			},'producteditor gridpanel#manual' : {
				cellkeydown : this.bindDeleteKey,
				render : this.initManualDragAndDrop,
				beforeDestroy : this.distoryManualView
			},'producteditor gridpanel#relatedProduct' : {
				cellkeydown : this.bindDeleteKey,
				render : this.initTabProductDragAndDrop,
				beforeDestroy : this.distoryTabProductView
			},
			
			'producteditor button#newoption' : {
				click : this.newOption
			}
		});
	},
	
	newOption : function(btn){
		var optionWin = Ext.create("AM.view.option.OptionWindow",{
			title  : "New Option",
			modal  : true,
			height: 400,
		    width: 600
		});
		optionWin.show();
		optionWin.center();
	},
	
	submitProduct : function (btn){
		var productEditor = btn.up("producteditor");
		var productForm = productEditor.down("form#productForm").getForm();
		
		var product = productEditor.getProduct();
		var productOverider = productForm.getValues();
		
		var categories = [];
		var images = [];
		var manuals = {};
		var relatedProducts = [];
		
		var categoryStoredData = productEditor.down("gridpanel#category").getStore().getRange();
		var imageStoredData = productEditor.down("gridpanel#image").getStore().getRange();
		var manualStoredData = productEditor.down("gridpanel#manual").getStore().getRange();
		productEditor.down("gridpanel#manual").getStore().sync();
		var relatedProductData = productEditor.down("gridpanel#relatedProduct").getStore().getRange();
		
		for(var i = 0; i < categoryStoredData.length; i++){
			categories.push(categoryStoredData[i].data)
		}
		
		for(var i = 0; i < imageStoredData.length; i++){
			images.push(imageStoredData[i].data)
		}
		
		var manualJsonData = {};
		for(var i = 0; i < manualStoredData.length; i++){
			var data = manualStoredData[i].data
			manuals[data.key] = data;
		}
		
		for(var i = 0; i < relatedProductData.length; i++) {
			relatedProducts.push(relatedProductData[i].data);
		}
		
		product.categories = categories;
		product.images = images;
		product.relatedProducts = relatedProducts;
		product.manuals = manuals; 
		
		product = Ext.apply(product, productOverider)
		if(productForm.isValid()){
			productForm.findField("name").up("producteditor").setLoading(true);
			productDirectService.saveDetail(product, function(data, rs, suc){
				if(suc && data){
					productForm.findField("name").up("producteditor").setProduct(data);
					productForm.setValues(data);
				}else if(rs && rs.type == 'exception'){
					Ext.example.msg('<font color="red">Error</font>',
							'<font color="red">' + rs.message
									+ " </font>");
				}
				
				productForm.findField("name").up("producteditor").setLoading(false);
			});
		}
	},
	
	distoryCategoryView :function(grid){
		grid.dd = null;
	},
	initCategoryDragAndDrop : function (grid){
		var body = grid.body
		grid.dd = new Ext.dd.DropTarget(body, {
			ddGroup : 'grid-to-edit-parent',
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
	
	actionfailed : function(form, action) {
		form.findField("name").up("producteditor").setLoading(
				false);
		Ext.example.msg('<font color="red">Error</font>',
				'<font color="red">' + action.type
						+ " Failed </font>");
	},

	beforeaction : function(form) {
		form.findField("name").up("producteditor").setLoading(
				true, true);
	},
	
	bindDeleteKey : function (grid, td, cellIndex, record, tr, rowIndex, e, eOpts){
		if(e.keyCode == 46){
			var store = grid.getStore();
	        store.removeAt(rowIndex);
		}
	},
	
	initProductData : function (productFrom){
		productFrom.findField("name").up("producteditor").setLoading(
				false);
		var productEditor = productFrom.findField("name").up("producteditor");
		
		var categoryStore = productEditor.down("gridpanel#category").getStore();
		var imageStore = productEditor.down("gridpanel#image").getStore();
		var relatedProductStore = productEditor.down("gridpanel#relatedProduct").getStore();
		var manualStore = productEditor.down("gridpanel#manual").getStore();
		
		categoryStore.loadData(productEditor.getProduct().categories);
		imageStore.loadData(productEditor.getProduct().images);
		relatedProductStore.loadData(productEditor.getProduct().relatedProducts);
		
		var manuals = productEditor.getProduct().manuals;
		var manualArray = [];
		for(var k in manuals) {
			var rec = {};
			rec["key"] = k;
			m = manuals[k];
			for(var ht in m) {
				rec[ht] = m[ht];
			}
			manualArray.push(rec);
		}
		manualStore.loadData(manualArray);
	},

	newProduct : function(btn) {
		var contentPanel = btn.up("viewport").down("tabpanel#mainContainer");
		
		var editor = Ext.create("AM.view.product.Edit", {
			title : 'New Product'
		});
		
		contentPanel.insert(0, editor);
		contentPanel.setActiveTab(0);
		
		
		var productForm = editor.down("form");
		
		productForm.load({
			// pass 2 arguments to server side getBasicInfo
			// method (len=2)
			params : {
				id : 0
			},
			success : function (form, action){
				productForm.up("producteditor").setProduct(action.result.data);
				productForm.getForm().setValues(action.result.data);
			}
		})
	}, 
	
	initTabProductDragAndDrop : function (grid){
		var body = grid.body
		grid.dd = new Ext.dd.DropTarget(body, {
			ddGroup : 'grid-to-edit-parent-tabproduct',
			notifyEnter : function(ddSource, e, data) {
				body.stopAnimation();
				body.highlight();
			},
			notifyDrop : function(ddSource, e, data) {
				var store = grid.getStore();

				var tabProduct = ddSource.dragData.records[0].data;
				
				var data = store.getRange();
				
				var existing = false;
				
				for(var i = 0; i < data.length; i++){
					if(	data[i].data.id  == tabProduct.id){
						existing = true;
						break;
					}
				}
				
				if(!existing){
					
					store.add(tabProduct);
					
					return true;
				}
				
				return false;
			}
		});
	},
	distoryTabProductView : function(grid){
		grid.dd = null;
	},
	
	initManualDragAndDrop : function (grid){
		var body = grid.body
		grid.dd = new Ext.dd.DropTarget(body, {
			ddGroup : 'grid-to-edit-parent-html',
			notifyEnter : function(ddSource, e, data) {
				body.stopAnimation();
				body.highlight();
			},
			notifyDrop : function(ddSource, e, data) {
				var store = grid.getStore();

				var manual = ddSource.dragData.records[0].data;
				
				var data = store.getRange();
				
				var existing = false;
				
				for(var i = 0; i < data.length; i++){
					if(	data[i].data.id  == manual.id){
						existing = true;
						break;
					}
				}
				
				if(!existing){
					manual.key = "";
					
					store.add(manual);
					
					return true;
				}
				
				return false;
			}
		});
	},
	distoryManualView : function(grid){
		grid.dd = null;
	},
	
});