<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/views/inc/header.jsp" %>
<c:set var="rootpath" value="${CONTEXT_PATH}/sysmgr/resource"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <title>${title }</title>
    <%@ include file="/WEB-INF/views/inc/_css.jsp" %>
	<%@ include file="/WEB-INF/views/inc/_scripts.jsp" %>
</head>
<body>
<div class="container-fluid">
	<%@ include file="/WEB-INF/views/common/navbar.jsp" %>
    <div class="clearfix" style="overflow:hidden;">
		<%@ include file="/WEB-INF/views/common/side.jsp" %>
		<div class="main" style="overflow: hidden;">
			<iframe name="iframepage" marginheight="0" marginwidth="0" frameborder="0" scrolling="true" height="800px" width="100%" id="iframepage"></iframe>
		</div>   
    </div>
</div>

<%@ include file="/WEB-INF/views/inc/footer.jsp"%>

</body>
</html>