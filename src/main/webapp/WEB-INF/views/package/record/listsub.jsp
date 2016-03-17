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
		<c:forEach items="${list }" var="o" varStatus="ind">
			<tr height="60" <c:if test="${ind.index%2==0 }">bgcolor="#fafbfb"</c:if>>
				<td  width="15%" align="center">
					<c:choose>
						<c:when test="${o.attr==2}">流量</c:when>
						<c:otherwise>${o.productNames }</c:otherwise>
					</c:choose>
				<td width="15%" align="center">${o.amount }<c:if test="${o.attr==1}">个月</c:if><c:if test="${o.attr==2}">G</c:if></td>
				<td width="20%" align="center"><span><fmt:formatDate value="${o.createTime }" pattern="yyyy/MM/dd HH:mm"/></span></td>
				<td  width="20%" align="center">${o.price }</td>
				<td  width="15%" align="center">
					<c:choose>
						<c:when test="${o.orderStatus==0 }">未支付</c:when>
						<c:when test="${o.orderStatus==1 }">已支付</c:when>
						<c:otherwise>未知</c:otherwise>
					</c:choose>
				</td>
			   <td  width="15%" align="center"><c:if test="${o.orderStatus==0 }"><a href="javascript:cashPayment('${o.orderId}')">结算</a></c:if></td>
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
		<a href="javascript:go(${turn.nextPage })" class="next-page">下一页</a>
</div>
	<!--list-page End-->