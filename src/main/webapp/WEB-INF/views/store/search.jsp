<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="<%=request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <title>搜索</title>
    <script type = "text/javascript" charset="utf-8" src="${ctx}/source/js/libs/jquery/jquery/jquery.js"></script>
</head>
<body>
	<input name = "k" type = "text" id = "k"/>
	<input type="button" name="搜索播放器" value="搜索播放器" onclick="doSearch()"/>
	<div id = "rs"></div>
	<input name = "k2" type = "text" id = "k2"/>
	<input type="button" name="多条件搜索播放器" value="多条件搜索播放器" onclick="doSearchMulti()"/>
	<div id = "rs2"></div>
<script type="text/javascript">
	function doSearch(){
		var k = $('#k').val();
		if(!k)return;
		if(k.length<1) return;
		var url = "${ctx}/store/searchbylucene"
		$.post(url,{searchWord:k},function(data){
			$('#rs').html(data);
		});
	}
	function doSearchMulti(){
		var k2 = $('#k2').val();
		if(!k2)return;
		if(k2.length<1) return;
		var url = "${ctx}/store/searchbylucene"
		$.post(url,{searchWord:k2},function(data){
			$('#rs2').html(data);
		});
	}
</script>
</body>
</html>