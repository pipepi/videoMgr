<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/views/inc/header.jsp" %>
<c:set var="rootpath" value="${CONTEXT_PATH}/package"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>开启视频电商</title>
<link rel="stylesheet" href="${ctx}/source/css/partner/buy_package.css" />
<script type = "text/javascript" charset="utf-8" src="${ctx}/source/js/libs/jquery/jquery/jquery.js"></script>

</head>

<body>
<div class="container-fluid">
<div class="sj_buy-video">
  <div class="sj-buy-videoInfors">
    <div class="sj-buy-video"><img src="${ctx}/source/images/partner/package/banner_01.jpg" width="1000" height="300" /></div>
    <div class="sj-buy-video"><img src="${ctx}/source/images/partner/package/banner_02.jpg" width="1000" height="300" /></div>
  </div>
  <div class="next-btns"><a href="${ctx}/package/buypackagelist" class="naxt-btns-cons">开启我的视频电商</a>
      <div class="clear"></div>  
  </div>
</div>
</div>
</body>
</html>
