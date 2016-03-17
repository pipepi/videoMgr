<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="<%=request.getContextPath() %>"/>
<input type ="hidden" value = "${turn.rowCount }" id = "rowCount"/>
<ul class="bfq-listInfors">
	 <c:forEach var="v" items="${list }" >
	 <li>
	   <div class="bf-lists"><img src="${v.imgMin }" width="210" height="126" title="${v.desc }" /><br/><em 
	   style="height:20px;width: 215px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;"  title="${v.name }">${v.name}</em></div>
	   <div class="sp-edit">
	      <div class="sp-btns-infors">
	      	<a href="javascript:editVideo(${v.id });" class="bfq-bjBtns" style="margin-left:20px;">编辑</a>
	     	<c:if test="${v.checkState == 2 }">
	     		<a href="javascript:changeActiveState(${v.id })" class="bfq-bjBtns" id = "active-${v.id }" vid="${v.id }" active-state = "${v.active }">${v.active?'下线':'发布'}</a>
	     	</c:if>
	     </div>
	     <div class="bfq-time">最后编辑于：<fmt:formatDate value="${v.modificationTime }" pattern="yyyy年MM月dd日"/> </div>
	   </div>
	   <div class="sp-jdt-list">
	   		<c:choose>
	   			<c:when test="${v.checkState == 3||v.checkState == 4 }">
				    <div style="position:absolute;margin-left:220px;"><img src="${ctx }/source/images/aztimg/zt2.png" width="24" height="24" /></div>
				    <div class="sp-ckyy"  style="position:absolute;margin-top:-35px;margin-left:200px; cursor:pointer;">
				    	<img src="${ctx }/source/images/aztimg/ckyy.gif" width="61" height="33" onclick="showReason('${v.checkMsgStr}')"/>
				   	</div>
				    <div style="position:absolute;margin-top:30px;" class="jhs">视频上传</div>
				    <div style="position:absolute;margin-top:30px;margin-left:100px;" class="jhs">审核中</div>
				    <div style="position:absolute;margin-top:30px;margin-left:200px;" class="hhs">审核未通过</div>
	   			</c:when>
	   			<c:when test="${v.checkState == 2 }">
	   				<div class="" style="position:absolute;margin-left:220px;"><img src="${ctx }/source/images/aztimg/zt.png" width="24" height="24" /></div>
                    <div style="position:absolute;margin-top:30px;" class="jhs">视频上传</div>
                    <div style="position:absolute;margin-top:30px;margin-left:100px;" class="jhs">审核中</div>
                    <div style="position:absolute;margin-top:30px;margin-left:200px;"  class="jhs">审核通过</div>
	   			</c:when>
	   			<c:when test="${v.checkState == 0||v.checkState == 1 }">
	   				<div style="position:absolute;margin-top:30px;" class="jhs">视频上传</div>
                    <div style="position:absolute;margin-top:30px;margin-left:110px;" class="jhs">审核中</div>
                    <div style="position:absolute;margin-top:30px;margin-left:200px;">审核通过</div>
	   			</c:when>
	   		</c:choose>
	   </div>
	   <div style="margin-top:43px; float:left;"><a href="javascript:deleteVideo(${v.id });" class="zhineng-sreach">删除</a></div>
	 </li>
	 </c:forEach>
</ul>
<div class="list-page"><!--list-page Begin-->
 <span>${turn.page }/${turn.pageCount }页 </span>
 <a href="javascript:go(${turn.prevPage })" class="pre-page">上一页</a>
 <c:choose>
 	<c:when test="${turn.page==turn.pageCount }">
			<a href="javascript:void(0)" class="current">下一页</a>
 	</c:when>
 	<c:otherwise>
			 <a href="javascript:go(${turn.nextPage })" class="next-page">下一页</a>
 	</c:otherwise>
 </c:choose>
</div><!--list-page End-->
<div class="clear"></div>               