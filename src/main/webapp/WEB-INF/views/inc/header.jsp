<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="java.util.*,java.io.*,java.text.*,java.net.*" %>
<%@ page import="com._21cn.framework.web.*"%>




<c:set var="CONTEXT_PATH" value="<%=request.getContextPath() %>"/>
<c:set var="ctx" value="<%=request.getContextPath() %>"/>
<c:set var="CSS_PATH" value="${ctx }/source/css" />
<c:set var="JS_PATH" value="${ctx }/source/js" />
<c:set var="IMAGES_PATH" value="${ctx }/source/images" />
<c:set var="title" value="智能商家管理系统" />