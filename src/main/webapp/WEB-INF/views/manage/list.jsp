<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="<%=request.getContextPath() %>"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>智能商家管理</title>
<link rel="stylesheet" href="${ctx}/source/css/store/new_style.css" />
<script type = "text/javascript" charset="utf-8" src="${ctx}/source/js/libs/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/source/js/libs/divselect.js"></script>
<script type="text/javascript">
$(function(){
	$.divselect("#divselect","#inputselect");
});
</script>
<script type="text/javascript" src="${ctx}/source/js/libs/jquery.md5.js"></script>
</head>
<body >
<div class="container-fluid">
           <div class="video-add-title">
         	<ul>
            	<li class="current"><a href="javascript:void(0)">商家管理</a></li>
            </ul>
         </div>
      <div class="video-tabs"><!--video-tabs Begin-->
               
                 <div class="video-add-cons">
                <ul class="bfq-tab">
                    <li >商家账号<input type="text" id="querySellerName" class="inputs"/></li>
                    <li style="margin:8px 5px 0 10px">套餐情况</li>
                    <li>
           			   <ul>
           			     <select style="height: 30px;width: 200px;font-size:15px;" id="queryPackage" >
           			     <option value="">全部</option>
           			     <c:forEach items="${packageList }" var="packageInfo" varStatus="status">
				  		  <option value="${packageInfo.id }">${packageInfo.name}</option>
				    
            	         </c:forEach>
				    	</select>
                       </ul> 
                    <input name="inputselect" type="hidden" value="" id="inputselect"/> 
                   </li>
                   <li>
                   	<a href="javascript:void(0)" onclick="go(1);" class="zhineng-sreach" id="j-searchbtn">搜索</a>
                   </li>
                </ul>
                <div class="clear"></div>
         	</div>  
               
               
   
            <div class="voideo-add2"><!--voideo-add2 Begin-->
            <!--是否确认收货-->
            <div class="houtai-kuangs video-kuangs-1" style="display:none">
                 
               <span>是否确认收货</span>
                  <div style="width:216px;margin-top:20px;margin-left:75px;">
                      <a href="" class="video-btnsInfor">确定</a><a href="" class="video-btnsInfors">取消</a>
                    </div>
                 </div><!--comm-cons End-->
              <div id="page-content" class="video-shenghe-infomation">  
                
              </div>
    			<div class="clear"></div>
               <!--list-page End-->
               
              </div>
             
            </div><!--voideo-add2 End-->
           </div>
   
<script type="text/javascript">
var ctx = "${ctx}";
var _phone = "";
var _pn = 1;
var _ps = 8;
var _sellers_name = "";
var _package_id = "";


function doSearch(){
	 var url = ctx+"/playerseller/search";
	 _sellers_name = $('#querySellerName').val();
	 _package_id = $('#queryPackage').val();
	    $.post(url,
	    		{
	    		 sellerName:_sellers_name,
	    		 packageId:_package_id,
			     pageNo:_pn,
			     pageSize:_ps},function(data){
	  	  $('#page-content').html(data);
	    });
}
function go(pn){
	_pn = pn;
	doSearch();
}
go(1);
</script>
</body>
</html>