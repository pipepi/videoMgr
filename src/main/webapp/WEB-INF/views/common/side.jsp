<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<div class="sidebar">
	<div class="panel panel-default">
		<div class="panel-heading">
			<h4 class="panel-title">
				<a data-toggle="collapse" data-parent="#accordion"
					href="#collapseOne">功能菜单</a>
			</h4>
		</div>
		<div class="zTreeDemoBackground left">
	        <ul id="tree" class="ztree"></ul>
	    </div>
	</div>
</div>
<script type="text/javascript">
	var setting = {
	    data: {
	        simpleData: {
	            enable: true
	        }
	    }
	};
	
	var zNodes = eval('('+'${ztreeNodes}'+')');
	
	var ztree = null;
	
	seajs.use('$',function($){
		$(function(){
			ztree = $.fn.zTree.init($("#tree"), setting, zNodes);
		});
	})
</script>
