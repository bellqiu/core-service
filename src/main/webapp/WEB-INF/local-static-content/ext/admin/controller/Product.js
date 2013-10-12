Ext.define('AM.controller.Product', {
	extend : 'Ext.app.Controller',
	views : [ 'product.Edit', 'option.OptionWindow','option.OptionItem','option.OptionGrid', 'option.Property'],

	 models : [ 'Image', 'Property', 'Option', 'OptionItem' ],
	 /*
	 * * 
	 stores : [ 'EmptyCategoryTree', 'EmptyImage'],*/
	 stores : [ 'OptionType' ],
	
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
			}
			,'producteditor gridpanel#property' : {
				cellkeydown : this.bindDeleteKey
			}, 'producteditor button#addProperty' : {
				click : this.addProperty
			},
			
			'producteditor button#newoption' : {
				click : this.newOption
			}, 'producteditor gridpanel#option' : {
				cellkeydown : this.bindDeleteKey
			}, 'producteditor gridpanel#option actioncolumn' : {
				click : this.editOption
			},
			'productoptionwindow button#saveOption' : {
				click : this.saveOption
			}, 'productoptionwindow button#addOptionItem' : {
				click : this.addOptionItem
			}, 'productoptionwindow productpropertyitem button#addProperty' : {
				click : this.addProperty
			}, 'productoptionwindow productpropertyitem gridpanel#property' : {
				cellkeydown : this.bindDeleteKey
			}
			
		});
	},
	
	newOption : function(btn){
		var optionWin = Ext.create("AM.view.option.OptionWindow",{
			title  : "New Option",
			modal  : true,
			height: 550,
		    width: 500,
		    parentComponentId : btn.up("producteditor").getId()
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
		var properties = [];
		var options = [];
		
		var categoryStoredData = productEditor.down("gridpanel#category").getStore().getRange();
		var imageStoredData = productEditor.down("gridpanel#image").getStore().getRange();
		var manualStoredData = productEditor.down("gridpanel#manual").getStore().getRange();
		productEditor.down("gridpanel#manual").getStore().sync();
		var relatedProductData = productEditor.down("gridpanel#relatedProduct").getStore().getRange();
		var propertyStoredData = productEditor.down("gridpanel#property").getStore().getRange();
		productEditor.down("gridpanel#property").getStore().sync();
		var optionStoredData = productEditor.down("gridpanel#option").getStore().getRange();
		productEditor.down("gridpanel#option").getStore().sync();
		
		for(var i = 0; i < categoryStoredData.length; i++){
			categories.push(categoryStoredData[i].data)
		}
		
		for(var i = 0; i < imageStoredData.length; i++){
			images.push(imageStoredData[i].data);
		}
		
		var manualJsonData = {};
		for(var i = 0; i < manualStoredData.length; i++){
			var data = manualStoredData[i].data
			manuals[data.key] = data;
		}
		
		for(var i = 0; i < relatedProductData.length; i++) {
			relatedProducts.push(relatedProductData[i].data);
		}
		
		for(var i = 0; i < propertyStoredData.length; i++){
			properties.push(propertyStoredData[i].data);
		}
		
		for(var i = 0; i < optionStoredData.length; i++){
			options.push(optionStoredData[i].data);
		}
		
		product.categories = categories;
		product.images = images;
		product.relatedProducts = relatedProducts;
		product.manuals = manuals; 
		product.props = properties;
		product.options = options;
		
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
									+ " </font>");3
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
		var propertyStore = productEditor.down("gridpanel#property").getStore();
		var optionStore = productEditor.down("gridpanel#option").getStore();
		
		categoryStore.loadData(productEditor.getProduct().categories);
		imageStore.loadData(productEditor.getProduct().images);
		propertyStore.loadData(productEditor.getProduct().props);
		relatedProductStore.loadData(productEditor.getProduct().relatedProducts);
		optionStore.loadData(productEditor.getProduct().options);
		
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
	
	addProperty : function(btn){
		var store = btn.up('gridpanel').getStore();
		var rec = new AM.model.Property({
			name : '',
			value : '',
		});
		rec.id = 0;
		store.insert(0, rec);
		btn.up('gridpanel').getPlugin().startEditByPosition({
			row : 0,
			column : 0
		});
	},
	
	editOption : function(grid, el, index) {
		var storeData = grid.getStore().getRange();
		var optionWin = Ext.create("AM.view.option.OptionWindow",{
			title  : "Edit Option",
			modal  : true,
			height: 550,
		    width: 500,
		    parentComponentId : grid.up("producteditor").getId()
		});
		var option = storeData[index].raw;
		optionWin.setOption(option);
		optionWin.show();
		optionWin.center();
		optionWin.down("form#productOptionForm").getForm().loadRecord(storeData[index]);
		var contentPanel = optionWin.down("tabpanel#optionItemPanel");
		var optionItems = storeData[index].raw.items;
		option.optionItemId = [];
		for(i=0;i<optionItems.length;i++) {
			editor = Ext.create("AM.view.option.OptionItem",{
				title  : "Option Item",
			});
			editor.down("form#optionItemForm").getForm().setValues(optionItems[i]);
			editor.down("form#optionItemForm").getForm().id = optionItems[i].id;
			propertyStore = editor.down("gridpanel#property").getStore();
			propertyStore.loadData(optionItems[i].overrideProps);
			contentPanel.insert(0, editor);
			option.optionItemId.push(editor.getId());
		}
		contentPanel.setActiveTab(0);
		option.columnId = index;
	},
	
	saveOption : function(btn){
		var optionwindow = btn.up("productoptionwindow");
		var optionForm = optionwindow.down("form#productOptionForm").getForm();
		var productEditor = Ext.getCmp(optionwindow.parentComponentId);
		var optionItemPanel = optionwindow.down("tabpanel#optionItemPanel");
		var product = productEditor.getProduct();
		var optionOverider = optionForm.getValues();
		var option = optionwindow.getOption();
		
		var optionItems = [];
		var options = [];
		if(option.optionItemId != undefined) {
			for(i = 0; i < option.optionItemId.length; i++) {
				optionItem = Ext.getCmp(option.optionItemId[i]);
				if(optionItem == undefined) {
					break;
				}
				optionItemFormValue = optionItem.down("form#optionItemForm").getForm().getValues();
				propertyItemStore = optionItem.down("gridpanel#property").getStore();
				propertyItemStore.sync();
				if(optionItem.down("form#optionItemForm").getForm().id != undefined) {
					optionItemFormValue.id = optionItem.down("form#optionItemForm").getForm().id;
				} else {
					optionItemFormValue.id = 0;
				}
				propertyItems = [];
				propertyItemData = propertyItemStore.getRange();
				for(j=0;j<propertyItemData.length;j++) {
					propertyItems.push(propertyItemData[j].data);
				}
				optionItemFormValue.overrideProps = propertyItems;
				optionItems.push(optionItemFormValue);
			}
		} 
		option.items = optionItems;

		var optionStore = productEditor.down("gridpanel#option").getStore();
		var optionStoreData = optionStore.getRange();
		var existing = false;
		var columnIndex = -1;
		if(option.columnId != undefined) {
			for(i = 0; i < optionStoreData.length; i++) {
				if(option.columnId == i) {
					existing = true;
					columnIndex = i;
					break;
				}
			}
		}
		option = Ext.apply(option, optionOverider);
		if(!existing) {
			option.columnId = 0;
			optionStore.insert(0, option);
		} else {
			optionStore.removeAt(columnIndex);
			optionStore.insert(columnIndex, option);
		}
		optionStore.sync();
		optionStoreData = optionStore.getRange();
		for(i = 0; i < optionStoreData.length; i++) {
			options.push(optionStoreData[i].raw);
		}
		product.options = options;
		
		if(optionForm.isValid) {
			productEditor.setLoading(true);
			productDirectService.saveDetail(product, function(data, rs, suc){
				if(suc && data){
					optionStore.loadData(data.options);
					optionwindow.close();
					//productForm.setValues(data);
				}else if(rs && rs.type == 'exception'){
					Ext.example.msg('<font color="red">Error</font>',
						'<font color="red">' + rs.message
								+ " </font>");
				}
			
				productEditor.setLoading(false);
			});
		}
	},
	
	addOptionItem : function(btn){
		var contentPanel = btn.up("productoptionwindow").down("tabpanel#optionItemPanel");
		var editor = Ext.create("AM.view.option.OptionItem",{
			title  : "Option Item",
		});
		contentPanel.insert(0, editor);
		contentPanel.setActiveTab(0);
		var option = btn.up("productoptionwindow").getOption();
		if(option.optionItemId == undefined) {
			option.optionItemId = [];
		} 
		option.optionItemId.push(editor.getId());
		
	},
	
});