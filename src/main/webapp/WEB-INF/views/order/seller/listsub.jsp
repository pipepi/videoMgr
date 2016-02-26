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
			<th width="5%" align="center">订单号</th>
			<th width="8%" align="center">买家名称</th>
			<th width="10%" align="center">卖家名称</th>
			<th width="10%" align="center">商品名称</th>
			<th width="4%" align="center">价格</th>
			<th width="12%" align="center">收货地址</th>
			<th width="8%" align="center">收货手机号</th>
			<th width="8%" align="center">支付状态</th>
			<th width="8%" align="center">送货状态</th>
			<th width="8%" align="center">创建时间</th>
			<th width="8%" align="center">关闭时间</th>
			<th align="center" width="10%">操作</th>
		</tr>
		<c:forEach items="${list }" var="o" varStatus="ind">
			<tr height="60" <c:if test="${ind.index%2==0 }">class="bg-col1"</c:if>>
				<td align="center">${o.orderId }</td>
				<td align="center">${o.buyersName }</td>
				<td align="center">${o.sellerName }</td>
				<td align="center">${o.productNames }</td>
				<td align="center">${o.price }</td>
				<td align="center">${o.toProvince }&nbsp;${o.toCity }&nbsp;${o.toArea }&nbsp;${o.toAddress }</td>
				<td align="center">${o.toMobile }</td>
				<td align="center"><span style="color: #125ee9;">
					<c:choose>
						<c:when test="${o.orderStatus==0 }">未支付</c:when>
						<c:when test="${o.orderStatus==1 }">已支付</c:when>
						<c:otherwise>未知</c:otherwise>
					</c:choose>
					</span></td>
				<td align="center"><strong><span title="电话：${o.backMobile }   邮箱：${o.backMail }   地址：${o.backAddress }" style="color: #e3393c;">
					<c:choose>
						<c:when test="${o.deliverStatus==0 }">未送货</c:when>
						<c:when test="${o.deliverStatus==1 }"><a href = "${ctx }/order/showlogistics?id=${o.id}" target="_blank">已送货</a></c:when>
						<c:when test="${o.deliverStatus==2 }"><a href = "${ctx }/order/showlogistics?id=${o.id}" target="_blank">已收货</a></c:when>
						<c:when test="${o.deliverStatus==3 }">申请退换货</c:when>
						<c:when test="${o.deliverStatus==4 }">退换货中</c:when>
						<c:when test="${o.deliverStatus==5 }">退换货中</c:when>
						<c:when test="${o.deliverStatus==6 }">退换货完成</c:when>
						<c:otherwise>未知</c:otherwise>
					</c:choose>
					</span></strong></td>
				<td align="center"><span><fmt:formatDate value="${o.createTime }" pattern="yyyy年MM月dd日"/></span></td>
				<td align="center"><span><fmt:formatDate value="${o.cannelTime }" pattern="yyyy年MM月dd日"/></span></td>
				<td align="center">
				<c:if test="${o.orderStatus==1&&o.deliverStatus==0 }">
					<a href="${ctx }/seller/addlogistics?eqId=${o.id}"  class="houtai-shBtn bfq-cpBtns-1">我要发货</a>
				</c:if>
				<c:if test="${o.deliverStatus==3 }">
					<a href="javascript:void(0)" onclick="showAgreeBack(this,${o.id})" buyer="${o.buyersName }" phone ="${o.backMobile }" email="${o.backMail }" addr="${o.backAddress }" class="houtai-shBtn-lan">同意退换货</a>
				</c:if>
				<c:if test="${o.deliverStatus==5 }">
					<a href="javascript:void(0)" onclick="showConfBack(${o.id})" class="houtai-shBtn bfq-cpBtns-1">完成退换货</a>
				</c:if>
				</td>
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