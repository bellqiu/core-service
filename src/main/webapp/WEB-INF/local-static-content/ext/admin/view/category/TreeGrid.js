Ext.define('AM.view.category.TreeGrid', {
	extend : 'Ext.tree.Panel',
	alias : 'widget.categorytreegrid',
	requires : [ 'Ext.data.*', 'Ext.grid.*', 'Ext.tree.*' ,  'Ext.dd.DropTarget','Ext.selection.*'],
	title : 'Manager Category',
	useArrows : true,
	rootVisible : false,
	multiSelect : false,
	singleExpand : true,
	closable : true,
	stateful : true,
	initComponent : function() {

		Ext.apply(this, {
			store : 'CategoryTree',
			stateful : true,
			viewConfig: {
                plugins: {
                    ddGroup: 'grid-to-edit-parent',
                    ptype: 'gridviewdragdrop',
                    enableDrop: false
                }
            },
            selModel: new Ext.selection.RowModel({
                singleSelect : true
            }),
			plugins : [ {
				ptype : 'cellediting',
				clicksToEdit : 2,
				id : 'categoryCellEditor'
			} ],
			dockedItems : [ {
				xtype : 'toolbar',
				items : [ {
					text : 'New',
					itemId : 'newCategory'
				}, {
					text : 'Synchronize',
					itemId : 'synchronize'
				}, {
					text : 'Refresh',
					itemId : 'refresh'
				} ]
			} ],
			columns : [ {
				xtype : 'treecolumn', // this is so we know which column will
										// show the tree
				text : 'Name',
				flex : 2,
				sortable : true,
				dataIndex : 'name',
				editor : {
					xtype : 'textfield',
					allowBlank : false
				}
			}, {
				text : 'Display Name',
				flex : 1,
				sortable : true,
				dataIndex : 'displayName',
				editor : {
					xtype : 'textfield',
					allowBlank : false
				}
			}, {
				text : 'Type',
				flex : 1,
				dataIndex : 'type',
				sortable : true
			}, {
				text : 'Edit',
				width : 55,
				menuDisabled : true,
				xtype : 'actioncolumn',
				//tooltip : 'Edit task',
				//align : 'center',
				//
				items : [
				         {
				        	 icon : '/resources/ext/resources/images/edit.png',
				        	 tooltip : 'Edit Category',
				        	 id : 'edit'
				        	 
				         },
				         {
				        	 icon : '/resources/ext/resources/images/delete.gif',
				        	 tooltip : 'Delete Category',
				        	 id : 'delete'
				         }
				         ]
			} ]
		});
		this.callParent();
	}
	 

});