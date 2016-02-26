<%@ page import="com.aepan.sysmgr.model.User"%>
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<nav class="navbar navbar-inverse" role="navigation">
	<div class="navbar-header">
		<a class="navbar-brand" href="#">智能商家后台管理系统 v1.0</a>
	</div>
	<%
		HttpSession httpsession = request.getSession();
		Object obj = httpsession.getAttribute("currentUser");
		if(obj != null){
			User user = (User)obj;
			request.setAttribute("currentUsername", user.getUserName());
		}
	%>
	<div class="collapse navbar-collapse" id="example-navbar-collapse">
		<ul class="nav navbar-nav navbar-right">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">当前用户：${currentUsername}<span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                    <li><a href="/logout.do">退出</a></li>
                </ul>
            </li>
        </ul>
	</div>
</nav>
