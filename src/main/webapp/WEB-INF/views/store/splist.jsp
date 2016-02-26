<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<c:set var="rootpath" value="${CONTEXT_PATH}/store"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>视频列表</title>
<link rel="stylesheet" href="${CSS_PATH}/store/new_style.css" />
<script src="${JS_PATH}/libs/jquery.min.js"></script>
<script type="text/javascript">
jQuery(document).ready(function($) {
   $(".close-icon").click(function(){
    $(".commodity-kuang").hide();
  
  })
});
</script>
</head>

<body>
 <div class="container-fluid">
      <div class="video-add">
         <div class="video-add-title">
         	<ul>
            	<li class="current">视频列表</li>
                <li><a href="${rootpath}/addbfq">添加新视频</a></li>
            </ul>
         </div>
      </div>
      <div class="video-tabs"><!--video-tabs Begin-->
         <div class="video-tabs-cons">
               <div class="video-add-cons">
                <ul class="bfq-tab">
                    <li>已建立视频数量：<span>2650</span></li>
                </ul>
                <div class="clear"></div>
               </div>
   
            <div class="voideo-add2"><!--voideo-add2 Begin-->
              <!--商品弹出框-->
              
              <div class="video-add-cons">
               <ul class="bfq-listInfors">
               	 <li>
                   <div class="bf-list"><img src="images/1.jpg" width="210" height="126" /><br/><em>视频名称视频名称视频名称</em></div>
                   <div class="sp-edit">
                      <div class="sp-btns-infors"><a href="javascript:void(0)" class="bfq-bjBtns" style="margin-left:20px;">编辑</a>
                     <a href="javascript:void(0)" class="bfq-bjBtns">预览</a></div>
                     <div class="bfq-time">最后编辑于：2015年12月12日 </div>
                   </div>
                   
                   <div><a href="javasrcipt:void(0)" class="del-btns">删除</a></div>
                 </li>
                 <li>
                   <div class="bf-list"><img src="images/1.jpg" width="210" height="126" /><br/><em>视频名称视频名称视频名称</em></div>
                   <div class="sp-edit">
                     <div class="sp-btns-infors"><a href="javascript:void(0)" class="bfq-bjBtns" style="margin-left:20px;">编辑</a>
                     <a href="javascript:void(0)" class="bfq-bjBtns">预览</a></div>
                     <div class="bfq-time">最后编辑于：2015年12月12日 </div>
                   </div>
                   
                   <div><a href="javasrcipt:void(0)" class="del-btns">删除</a></div>
                 </li>
                 <li>
                   <div class="bf-list"><img src="images/1.jpg" width="210" height="126" /><br/><em>视频名称视频名称视频名称</em></div>
                   <div class="sp-edit">
                      <div class="sp-btns-infors"><a href="javascript:void(0)" class="bfq-bjBtns" style="margin-left:20px;">编辑</a>
                     <a href="javascript:void(0)" class="bfq-bjBtns">预览</a></div>
                     <div class="bfq-time">最后编辑于：2015年12月12日 </div>
                   </div>
                   
                   <div><a href="javasrcipt:void(0)" class="del-btns">删除</a></div>
                 </li>
                 <li>
                   <div class="bf-list"><img src="images/1.jpg" width="210" height="126" /><br/><em>视频名称视频名称视频名称</em></div>
                   <div class="sp-edit">
                     <div class="sp-btns-infors"><a href="javascript:void(0)" class="bfq-bjBtns" style="margin-left:20px;">编辑</a>
                     <a href="javascript:void(0)" class="bfq-bjBtns">预览</a></div>
                     <div class="bfq-time">最后编辑于：2015年12月12日 </div>
                   </div>
                   
                   <div><a href="javasrcipt:void(0)" class="del-btns">删除</a></div>
                 </li>
                 <div class="clear"></div>
               </ul>
              </div>
            </div><!--voideo-add2 End-->
           </div>
        
        </div><!--video-tabs End-->
   </div>
</body>
</html>
