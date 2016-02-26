<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- response.setStatus(200); -->
<!-- response.sendRedirect("/oc/front/"); -->
<!DOCTYPE html>
<html>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<head>
	<title>404 - 页面不存在</title>
</head>

<body>
	<table>
		<tr>			
			<td align='left' width='10%'>
				<br/><br/><br/>
			</td>
			<td align='center'>
				
			</td>

			<td align='left' width='40%'>

			</td>
		</tr>
		<tr>			
			<td align='left' width='10%'>
			
			</td>
			<td align='center'>
				<img src="<c:out value= '${ctx}'/>/static/images/warning.png"/>
			</td>

			<td align='left' width='40%'>
				<label>抱歉，您所发出的请求页面不存在，如有问题，请向相关人员咨询情况，谢谢</label>
				<br/>
				<br/>
				<!--
				您可以：<br/>
				1、返回<a href="http://admin.e.189.cn/index.do">首页</a>。<br/>
				2、返回<a href="http://admin.e.189.cn/logon.do">登陆页</a>。<br/>-->
			</td>
		</tr>
	</table>
</body>
</html>