Ext.define('AM.view.toolbar.GlobalNav', {
			extend : 'Ext.toolbar.Toolbar',
			alias : 'widget.globalnav',
			requires : [ 'Ext.grid.*', 'Ext.data.*', 'Ext.util.*', 'Ext.state.*',
			 			'Ext.ux.upload.Button', 'Ext.ux.upload.plugin.Window' ],
			border : 0,
			initComponent : function() {
				var me = this;

				Ext.applyIf(me, {
							items : [
									{
										fieldLabel : 'Manage',
										xtype : 'combo',
										queryMode : 'local',
										displayField : 'name',
										valueField : 'value',
										store : 'ManagerCombo',
										labelWidth: 50,
										width: 150,
										id: 'managerCombo'

									},{
										xtype : 'button',
										text : 'New Product',
										id : 'newproduct'
										
									}/*,{
										
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
											statusQueuedText : 'Ready to upload',
											statusUploadingText : 'Uploading ({0}%)',
											statusFailedText : '<span style="color: red">Error</span>',
											statusDoneText : '<span style="color: green">Complete</span>',

											statusInvalidSizeText : 'File too large',
											statusInvalidExtensionText : 'Invalid file type'
										},
										listeners : {
											filesadded : function(uploader, files) {
												// console.log('filesadded');
												return true;
											},

											beforeupload : function(uploader, file) {
												// console.log('beforeupload');
											},

											fileuploaded : function(uploader, file) {
												// console.log('fileuploaded');
											},

											uploadcomplete : function(uploader, success, failed) {
												// console.log('uploadcomplete');
											},
											scope : this
										}

									
									}*/,'->', {
										xtype : 'label',
										text : loginuser.email
									},

									{
										xtype : 'button',
										text : 'Logout',
										id : 'logout'
									}]
						});

				me.callParent(arguments);
			}

		});