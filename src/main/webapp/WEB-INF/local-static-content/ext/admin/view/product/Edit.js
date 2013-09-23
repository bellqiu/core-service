Ext.define('AM.view.product.Edit', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.producteditor',
	
	config : {
		product : {}
	},
	layout : 'fit',
	defaults : {
		border : 0,
		padding : 3
	},
	closable : true,
	
	initComponent : function() {
		
		var imageStore = Ext.create("Ext.data.JsonStore",{
			model : 'AM.model.Image',
			data : [],
			buffered : false,
		});
		
		var categoryStore = Ext.create("Ext.data.JsonStore",{
			model : 'AM.model.CategoryTree',
			data : [],
			buffered : false,
		});
		
		this.items = [ {
			border : 0,
			xtype : 'form',
			itemId : 'productForm',
			paramsAsHash: true,
			api : {
				// The server-side method to call for load() requests
				load : 'productDirectService.loadProduct',
				submit : 'productDirectService.saveDetail'
			},
			layout : 'vbox',
			fieldDefaults : {
				labelAlign : 'left',
				msgTarget : 'side'
			},
			defaults : {
				width : '100%',
				align : 'stretch'
			},

			items : [ {
				xtype : 'fieldset',
				title : 'Basic Info',
				layout : 'hbox',
				flex : 2,
				overflowY : 'auto',
				items : [ {
					xtype : 'container',
					flex : 1,
					border : false,
					layout : 'anchor',
					defaultType : 'textfield',
					items : [ {
						fieldLabel : 'Name',
						allowBlank : false,
						name : 'name',
						anchor : '90%'
					}, {
						fieldLabel : 'Title',
						name : 'title',
						allowBlank : false,
						anchor : '90%'
					}, {
						fieldLabel : 'Override Url',
						name : 'overrideUrl',
						allowBlank : false,
						anchor : '90%'
					}, {
						xtype : 'container',
						layout : 'hbox',
						anchor : '90%',
						defaults : {
							margin : '5 10 5 0'
						},
						items : [ {
							fieldLabel : 'Price',
							xtype : 'numberfield',
							allowBlank : false,
							name : 'price',
							flex : 1
						}, {
							fieldLabel : 'Actual Price',
							allowBlank : false,
							name : 'actualPrice',
							xtype : 'numberfield',
							flex : 1
						} ]

					}, {
						fieldLabel : 'Keywords',
						name : 'keywords',
						xtype : 'textarea',
						anchor : '90%'
					}, {
						fieldLabel : 'Tags',
						name : 'tags',
						xtype : 'textarea',
						anchor : '90%'
					}, {
						fieldLabel : 'Abstract Text',
						name : 'abstractText',
						xtype : 'textarea',
						anchor : '90%'
					} ]
				} ]
			}, {
				xtype : 'tabpanel',
				plain : true,
				activeTab : 0,
				flex : 3,
				defaults : {
					bodyPadding : 10
				},
				items : [{
					title : 'Detail',
					select :true,
					layout : 'fit',
					items : {
						xtype : 'htmleditor',
						name : 'detail'
					}
				}, {
					title : 'Category',
					layout : 'fit',
					items : {
						xtype : 'gridpanel',
						itemId : 'category',
						viewConfig: {
			                plugins: {
			                    ddGroup: 'category-row-to-row',
			                    ptype: 'gridviewdragdrop',
			                    enableDrop: true
			                }
			            },
						enableKeyEvents:true,
						store : categoryStore,
						columns: [
						          { text: 'Name', dataIndex: 'name', flex: 1 },
						          { text: 'Display Name',  dataIndex: 'displayName' , flex: 2}
						         
						      ]
					}
				}, {
					title : 'Images',
					defaults : {
						width : 230
					},
					layout : 'fit',
					items : [ {
						xtype : 'gridpanel',
						tbar: [
									{
										xtype: 'uploadbutton',
										text : 'Upload Image',
										// singleFile: true,
										plugins : [ {
											ptype : 'ux.upload.window',
											title : 'Upload',
											width : 520,
											height : 350
										} ],
										uploader : {
											url : '/admin/image/upload',
											autoStart : false,
											max_file_size : '20mb',
											filters : [
											           	{title : "Image files", extensions : "jpg,gif,png"}
											           ],
											statusQueuedText : 'Ready to upload',
											statusUploadingText : 'Uploading ({0}%)',
											statusFailedText : '<span style="color: red">Error</span>',
											statusDoneText : '<span style="color: green">Complete</span>',
									
											statusInvalidSizeText : 'File too large',
											statusInvalidExtensionText : 'Invalid file type'
										},
										listeners : {
									
											fileuploaded : function(uploader, file, resp) {
												if(resp.success){
													var store = uploader.owner.up("producteditor").down("gridpanel#image").getStore();
													store.add(resp.image);
												}
												console.log('fileuploaded');
											}
										}
									}
						],
						itemId : 'image',
						viewConfig: {
			                plugins: {
			                    ddGroup: 'image-row-to-row',
			                    ptype: 'gridviewdragdrop',
			                    enableDrop: true
			                }
			            },
						enableKeyEvents:true,
						store :imageStore,
						columns: [
						          { text: 'Icon', dataIndex: 'iconUrl', flex: 1 , renderer :function (val ){ return "<img src='" + site.resourceServer + site.webResourcesFolder+ site.productImageResourcesFolder + "/"+ val + "'>"}},
						          { text: 'Name',  dataIndex: 'name' , flex: 2},
						          { text: 'URL',  dataIndex: 'noChangeUrl' , flex: 2,  
						        	  	renderer :function (val ){ 
						        	  			
						        	  		return "<a href='" + site.resourceServer + site.webResourcesFolder+ site.productImageResourcesFolder + "/"+ val + "' target='_blank'>"+val+"</a>"
						        	  			
						        	  	}
						          }
						      ]
						
					} ]
				}, {
					title : 'Option',
					layout : 'fit',
					items : {
						xtype : 'htmleditor',
						name : 'bio2',
						fieldLabel : 'Biography'
					}
				}, {
					title : 'Property',
					layout : 'fit',
					items : {
						xtype : 'htmleditor',
						name : 'bio2',
						fieldLabel : 'Biography'
					}
				} , {
					title : 'Mannual',
					layout : 'fit',
					items : {
						xtype : 'htmleditor',
						name : 'bio2',
						fieldLabel : 'Biography'
					}
				} , {
					title : 'Related Product',
					layout : 'fit',
					items : {
						xtype : 'htmleditor',
						name : 'bio2',
						fieldLabel : 'Biography'
					}
				} ]
			} ],

			buttons : [ {
				text : 'Save',
				itemId :'saveProduct'
			}, {
				text : 'Reload',
				itemId :'reloadProduct'
			} ]
		} ];
		
		this.callParent(arguments);
	}

});