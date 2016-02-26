<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<c:set var="rootpath" value="${CONTEXT_PATH}/package"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新建播放器</title>
<link rel="stylesheet" href="${CSS_PATH}/store/new_style.css" />
<script type = "text/javascript" charset="utf-8" src="${ctx}/source/js/libs/jquery/jquery/jquery.js"></script>
<script type="text/javascript">
	
	var partnerIndexUrl="${partnerIndexUrl}";
	
	window.top.location=partnerIndexUrl;
</script>
</head>
<body>
</body>
</html>