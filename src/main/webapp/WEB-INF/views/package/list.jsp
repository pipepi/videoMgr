<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/views/inc/header.jsp" %>
<c:set var="rootpath" value="${CONTEXT_PATH}/package"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>智能商家套餐管理</title>
<link rel="stylesheet" href="${ctx}/source/css/partner/manage_package.css" />
<script type = "text/javascript" charset="utf-8" src="${ctx}/source/js/libs/jquery/jquery/jquery.js"></script>

</head>

<body>

    <div class="container-fluid">
      <div class="video-add">
         <div class="video-add-title">
         	<ul>
            	<li  class="current">管理</a></li>
                <!-- li ><a href="javascript:window.location='${ctx}/package/create'">新增</a></li-->
            </ul>
         </div>
      </div>
      <div class="video-tabs"><!--video-tabs Begin-->
         <div class="video-tabs-cons">
            <div class="voideo-add2"><!--voideo-add2 Begin-->

 <table border="0" class="table-tc-list" cellpadding="0" cellspacing="0" width="100%">
                 	 <tr height="40" width="100%">
                 	 
                 	   	<th width="10%">套餐名称</th>
                        <th width="10%">可分享播放器</th>
                        <th width="14%">每播放器搭载视频</th>
                        <th width="14%">每播放器搭载商品</th>
                        <th width="10%">每月套餐单价</th>
                        <th width="10%">流量(单位T)</th>
                        <th width="10%">每月流量费用</th>
                        <th width="8%">期限</th>
                        <th width="9%">操作</th>
                     <tr>

            
            
            <c:forEach items="${packageList }" var="packageinfo">
            
            	 <tr height="40" bgcolor="#fafbfb">
           			  <td width="15%" align="center">${packageinfo.name }</td>
                      <td width="10%" align="center">${packageinfo.playerNum }</td>
                      <td align="center">${packageinfo.videoNum }</td>
                      <td align="center">${packageinfo.productNum }</td>
                      <td align="center">${packageinfo.price }</td>
                      <td align="center">${packageinfo.flowNum } T</td>
                       <td align="center">${packageinfo.monthFlowPrice } 元</td>
                      <td align="center">${packageinfo.duration } 个月</td>
                      <td align="center"><a href="javascript:window.location='${ctx}/package/show?eqId=${packageinfo.id }'">编辑</a></td>
							    
							    
				</tr>
							    
			 </c:forEach>
             </table>
            
 <div class="clear"></div>
                </div>
            </div><!--voideo-add2 End-->
           </div>
        
        </div><!--video-tabs End-->
   </div>

<!--页面业务-->

</body>
</html>