seajs.use([ 'bootstrap', '$', 'widget', 'underscore', 'validator' ],function(bootstrap, $, Widget, _, Validator) {
					'use strict';
					$('.help-popover').popover();

					var setting = {
						view : {
							showIcon : false,
							showLine : false
						},
						data : {
							simpleData : {
								enable : true,
								idKey : "id",
								pIdKey : "pId",
								rootPId : ""
							}
						},
						callback : {
							onNodeCreated : insertIcon,
						}
					};
					$(document).ready(function() {
						// 初始化节点数据
						var zNodes = initNodes();
						$.fn.zTree.init($("#tree"), setting, zNodes);
						$.fn.zTree.init($("#tree1"), setting, zNodes);
						//新建应用
						$('#j-add-site').click(function(){
							showEditModal('','create','true','','site');
						});		
						$('#j-add-channel').click(function(){
							showEditModal('','create','true','','channel');

						});		
						$('#j-add-page').click(function(){
							showEditModal('','create','true','','page');

						});	
					});
		
					//获取节点数据
					function initNodes() {
						var node ='';
						$.ajax({
								url : '/media/adSite/searchZtreeData.do',
								type : 'POST',
								dataType : 'json',
								async : false,
								success : function(data) {
									node=data;
								},
								error : function(err) {
									alert('error系统错误!');
								}
								});
						return node;
					};
					//增删改查按钮的显示
					function insertIcon(event, treeId, treeNode) {
						if(treeId=='tree'){
							var editStr = "<div id='icon_"+ treeNode.tId
							+ "' class='linkfour hidden'><a class='j-action-icon delete' href='javascript:;'></a> "
							+ "<a class='j-action-icon edit' href='javascript:;'></a> "
							+ "<a class='yu' href='javascript:;'></a> "
							+ "<a class='tou' href='javascript:;'></a></div>";
		
							var $dom = $("#" + treeNode.tId + " a");
							$dom.append(editStr).hover(function() {
								$("#icon_" + treeNode.tId).removeClass('hidden');
							}, function() {
								$("#icon_" + treeNode.tId).addClass('hidden');
							});
							//编辑和删除的触发
							$("#icon_" + treeNode.tId).on('click','.j-action-icon',function(e) {
								var $label = $(e.target);
								var ids = treeNode.id.split("_");
								var flag=ids[0];
								var id = ids[1];
								if ($label.hasClass('delete')) { // 删除
									if(flag=='SITE'){
										delRecord(id,'http://admp.e.189.cn/media/adSite/',treeNode);
									}else if(flag=='CHANNEL'){
										delRecord(id,'http://admp.e.189.cn/media/adChannel/',treeNode);
									}else if(flag=='PAGE'){
										delRecord(id,'http://admp.e.189.cn/media/adPage/',treeNode);
									}
									
								} else { // 编辑
									if(flag=='SITE'){
										showEditModal(id,"show",'true',treeNode,'site');
									}else if(flag=='CHANNEL'){
										showEditModal(id,"show",'true',treeNode,'channel');
									}else if(flag=='PAGE'){
										showEditModal(id,"show",'true',treeNode,'page');
									}
									
								}
							});
						}
					}
				
});