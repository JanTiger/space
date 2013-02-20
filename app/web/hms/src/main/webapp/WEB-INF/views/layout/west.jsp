<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	$(function() {

		$('#layout_west_tree').tree({
			url : 'menuAction!showAllTreeNode.action',
			parentField : 'pid',
			lines : true,
			onClick : function(node) {
				var url;
				if (node.attributes.url) {
					url = node.attributes.url;
				} else {
					url = 'navigate!error_dog.action';
				}
				if (url.indexOf('dataSourceAction') > -1) {
					layout_center_addTabFun({
						title : node.text,
						closable : true,
						iconCls : node.iconCls,
						content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>'
					});
				} else {
					layout_center_addTabFun({
						title : node.text,
						closable : true,
						iconCls : node.iconCls,
						href : url
					});
				}
			}
		});

	});
</script>
<div class="easyui-accordion" data-options="fit:true,border:false">
	<div title="树形系统菜单" data-options="isonCls:'icon-save',tools : [ {
				iconCls : 'icon-reload',
				handler : function() {
					$('#layout_west_tree').tree('reload');
				}
			}, {
				iconCls : 'icon-redo',
				handler : function() {
					var node = $('#layout_west_tree').tree('getSelected');
					if (node) {
						$('#layout_west_tree').tree('expandAll', node.target);
					} else {
						$('#layout_west_tree').tree('expandAll');
					}
				}
			}, {
				iconCls : 'icon-undo',
				handler : function() {
					var node = $('#layout_west_tree').tree('getSelected');
					if (node) {
						$('#layout_west_tree').tree('collapseAll', node.target);
					} else {
						$('#layout_west_tree').tree('collapseAll');
					}
				}
			} ]">
		<ul id="layout_west_tree"></ul>
	</div>
</div>