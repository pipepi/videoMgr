<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="<%=request.getContextPath() %>"/>
<table cellpadding="0" cellspacing="0" width="100%"
	style="margin-bottom: 20px;">
	<tbody>
		<tr class="video-choise">
				<th width="10%">商家账号</th>
               <th width="10%">播放器数量</th>
               <th width="10%">视频数量</th>
               <th width="10%">商品数量</th>
               <th width="10%">套餐情况</th>
               <th width="10%">有效期</th>
		</tr>
		 <c:forEach items="${list}" var="PartnerMgrUser" varStatus="status">
                     <tr height="40" bgcolor="#fafbfb">
                      <td width="10%" align="center">${PartnerMgrUser.partnerName}</td>
                      <td align="center">${PartnerMgrUser.playerNum}</td>
                      <td align="center">${PartnerMgrUser.videoNum}</td>
                      <td align="center">${PartnerMgrUser.productNum}</td>
                      <td align="center">${PartnerMgrUser.packageName}</td>
                      <td align="center">${PartnerMgrUser.createTime}</td>
                      <!-- td align="center"><a href="">查看</a> <a href="javascript:void(0)">编辑</a></td-->
                     </tr>
          </c:forEach>
	</tbody>
</table>

<div class="clear"></div>
<div class="list-page">
		<!--list-page Begin-->
		<span>${turn.page }/${turn.pageCount }页</span>
		<a href="javascript:go(${turn.prevPage })" class="pre-page">上一页</a>
		<c:if test="${turn.page-2>0}">
			<a href="javascript:go(${turn.page-2 })">${turn.page-2 }</a>
		</c:if>
		<c:if test="${turn.page-1>0}">
			<a href="javascript:go(${turn.page-1 })">${turn.page-1 }</a>
		</c:if>
		<a href="javascript:void(0)" class="current">${turn.page}</a>
		<c:if test="${turn.page+1<=turn.pageCount}">
			<a href="javascript:go(${turn.page+1 })">${turn.page+1 }</a>
		</c:if>
		<c:if test="${turn.page+2<=turn.pageCount}">
			<a href="javascript:go(${turn.page+2 })">${turn.page+2 }</a>
		</c:if>
		<c:if test="${turn.page==turn.pageCount }">
			<a href="javascript:void(0)" class="current">下一页</a>
		</c:if>
		<c:if test="${turn.page<turn.pageCount }">
			<a href="javascript:go(${turn.nextPage })" class="next-page">下一页</a>
		</c:if>
</div>
	<!--list-page End-->