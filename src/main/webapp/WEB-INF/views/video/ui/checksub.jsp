<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="<%=request.getContextPath() %>"/>
	<div class="video-shenghe-infomation">
		<table cellpadding="0" cellspacing="0" width="100%"
			style="margin-bottom: 20px;">
			<tbody>
				<tr class="video-choise">
					<th width="50" align="center">选择</th>
					<th width="140" align="center">商家名称</th>
					<th width="160" align="center">视频详情</th>
					<th width="160" align="center">上传时间</th>
					<th width="60" align="center">状态</th>
					<th align="center" width="300">操作</th>
					<th>&nbsp;</th>
				</tr>
				<c:forEach items="${list }" var = "v" varStatus="stat">
					<tr height="170">
						<td valign="middle">
							<c:if test="${v.checkState>0 }">
							<div class="checkboxFive"
								style="margin-top: 7px; margin-left: 15px;">
								<input type="checkbox" value="1" id="checkboxFiveInput" name="checkboxname" vid = "${v.id }" vstate = "${v.checkState }">
							</div>
							</c:if>
						</td>
						<td align="center">${v.partnerAccountName}</td>
						<td><div class="bf-list">
								<img src="${v.imgMin}" width="160" height="90"  title = "${v.desc }"
								/><br/>
								<em>${v.name}</em>
							</div></td>
						<td width="160" align="center">
							<fmt:formatDate value="${v.createTime }" pattern="yyyy-MM-dd HH:mm"/>
						</td>
						<td width="60" align="center">
							<span style="color: #e3393c; font-weight: bold;">
								<c:choose>
								    <c:when test="${v.checkState==0 }">编码中</c:when>
									<c:when test="${v.checkState==1 }">待审核</c:when>
									<c:when test="${v.checkState==2 }">已上线</c:when>
									<c:when test="${v.checkState==3 }">未通过</c:when>
									<c:when test="${v.checkState==4 }">下线</c:when>
									<c:otherwise>其他</c:otherwise>
								</c:choose>		
							</span>
						</td>
						<td>
							<div class="bf-edit" style="width: 300px; margin-left: 40px;">
								<c:if test="${v.checkState>0 }">
								<a href="javascript:window.location='${ctx}/video/async/updatepage?role=2&video_id=${v.id }'" class="bfq-bjBtns" style="margin-left: 20px;">编辑</a> 
								<a href="javascript:preView(${v.id})" class="bfq-bjBtns" id = "pre-${v.id}" v_url = "${v.video }">预览</a>
								</c:if>
								<c:choose>
									<c:when test="${v.checkState==1 }"><a class="bfq-bjBtns  bfq-cpBtns-3" href="javascript:check(${v.id});">审核</a></c:when>
									<c:when test="${v.checkState==2 }"><a class="bfq-bjBtns  bfq-cpBtns-2" href="javascript:offline(${v.id});">违规下线</a></c:when>
									<c:when test="${v.checkState==3 }"><a class="bfq-bjBtns  bfq-cpBtns-1" href="javascript:showReason('${v.checkMsgs==null?'':v.checkMsgs[fn:length(v.checkMsgs)-1].msg }');">查看原因</a></c:when>
									<c:when test="${v.checkState==4 }"><a class="bfq-bjBtns  bfq-cpBtns-1" href="javascript:showReason('${v.checkMsgs==null?'':v.checkMsgs[fn:length(v.checkMsgs)-1].msg }');">查看原因</a></c:when>
									<c:otherwise></c:otherwise>
								</c:choose>
								<div class="bfq-time" style="margin-left: 30px;">最后编辑于：
									<fmt:formatDate value="${v.modificationTime }" pattern="yyyy年MM月dd日 HH:mm"/>
								</div>
							</div>
						</td>
						<td>
						<c:if test="${v.checkState>0 }">
						<a href="javascript:deleteVideo(${v.id })" class="zhineng-sreach">删除</a>
						</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div class="clear"></div>
	</div>
	<div class="clear"></div>
	<div id="multi-offline" style="float: left; margin-left: 50px; margin-top: 0;display:block;">
		<div style="float: left">
			<div class="checkboxFive">
				<input type="checkbox" value="1" id="checkboxFiveInput" name="" onclick = "checkAll(this)">
			</div>
			全选
		</div>
		<a href="javascript:allOffLine()" class="wgxx-btns">违规下线</a>
	</div>

	<div class="list-page">
		<!--list-page Begin-->
		<span>${turn.page>0?turn.page:0 }/${turn.pageCount>0?turn.pageCount:0 }页</span>
		<a href="javascript:go(${turn.prevPage })" class="pre-page">上一页</a>
		<c:if test="${turn.page-2>0}">
			<a href="javascript:go(${turn.page-2 })">${turn.page-2 }</a>
		</c:if>
		<c:if test="${turn.page-1>0}">
			<a href="javascript:go(${turn.page-1 })">${turn.page-1 }</a>
		</c:if>
		<c:if test="${turn.page>0 }">
		<a href="javascript:void(0)" class="current">${turn.page}</a>
		</c:if>
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

