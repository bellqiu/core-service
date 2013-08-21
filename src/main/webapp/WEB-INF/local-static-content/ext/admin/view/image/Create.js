Ext.define('AM.view.image.Create', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.imagecreate',

	title : 'Create images',
	layout : 'fit',
	border : 0,
	padding : 5,
	initComponent : function() {
		var upload = Ext.create('Ext.ux.multiupload.Panel', {
			title : 'Upload',
			width : 600,
			height : 300,
			frame : true,
			uploadConfig : {
				uploadUrl : '/postdata/upload',
				maxFileSize : 8 * 1024 * 1024,
				maxQueueLength : 50
			}
		});
		var images = Ext.create('Ext.grid.Panel', {
			title : 'Images',
			width : 600,
			height : 300,
			frame : true,
			margin : '5 0 0',
			store : {
				fields : [ 'id' ]
			},
			columns : [
					{
						header : 'Id',
						dataIndex : 'id',
						width : 250
					},
					{
						header : 'Image',
						dataIndex : 'id',
						width : 120,
						align : 'center',
						sortable : false,
						renderer : function(v) {
							return Ext.String.format(
									'<img src="/postdata/thumb/{0}" />', v);
						}
					} ]
		});

		upload.on('fileuploadcomplete', function(id) {
			images.store.add({
				id : id
			});
		});
		this.items = [ {
			xtype : 'form',
			border : 0,
			items : [ {
				xtype : 'textfield',
				name : 'name',
				fieldLabel : 'Name'
			}, {
				xtype : 'textfield',
				name : 'email',
				fieldLabel : 'Email'
			},upload, images]
		}

		];
		this.callParent(arguments);
	},
});