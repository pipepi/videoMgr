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
<title>智能商家管理系统</title>
<link rel="stylesheet" type="text/css" href="${ctx}/source/css/partner/list_video.css"/>
<script type = "text/javascript" charset="utf-8" src="${ctx}/source/js/libs/jquery/jquery/jquery.js"></script>
<script type = "text/javascript" charset="utf-8" src="${ctx}/source/js/jquery.swfobject.1-1-1.js"></script>
</head>
<body>
 <div class="container-fluid">
      <div class="video-add">
         <div class="video-add-title">
         	<ul>
            	<li class="current">视频列表</li>
                <li onclick = "javascript:addVideo();">添加视频</li>
                 <li><span id="packagelimitDesc" style="display: none;color: #e3393c;"></span></li>
            </ul>
         </div>
      </div>
      <div class="video-tabs"><!--video-tabs Begin-->
         <div class="video-tabs-cons">
               <div class="video-add-cons">
                <ul class="bfq-tab">
                    <li>已建立视频数量：<span id="row-count-span"></span></li>
                </ul>
                <div class="clear"></div>
               </div>
   
            <div class="voideo-add2"><!--voideo-add2 Begin-->
              <!--商品弹出框-->
              <div id = "video-yulan" style="position:absolute;background-color:#269fde;width:704px;height:555px; z-index:1000;left:14%;top:10%; display:none;">
	              <div style = "position: absolute;right:0;top:0;"><a href="javascript:void(0)" onclick="closePreView()" class="close-icon"></a></div>
	              <div id = "yulan" class="video-yulan" ></div>
              </div>
              
              <!--查看原因-->
            <div id = "reason-win" class="video-kuangs video-kuangs-1" style="display:none; z-index:1000;" >
                 <div class="video-titles">
                   <span>查看原因</span>
                  <a href="javascript:void(0)" onclick="$('#reason-win').toggle()" class="close-icon"></a>
                 </div>
                 <div class="comm-cons"><!--comm-cons Begin-->
                   <div class="comm-cons-bz"><table>
                    	<tr>
                        	<td valign="top">原因：</td>
                            <td><textarea style="width:373px;height:96px;" id = "reason-area" readonly="readonly"></textarea></td>
                        </tr>
                    </table></div>  
                   <div style="width:216px;margin-top:20px;margin-left:95px;">
                     <a href="javascript:void(0)" onclick="$('#reason-win').toggle()" class="video-btnsInfors">取消</a>
                    </div>
                 </div><!--comm-cons End-->
              </div>
              <div id="page-content" class="video-add-cons">
              </div>
            </div><!--voideo-add2 End-->
           </div>
        
        </div><!--video-tabs End-->
   </div>
</body>
<script type="text/javascript">
var ctx = "${ctx}";
	function deleteVideo(id){
		if(!checkPackageStat()){
			return
		}
		if(confirm("确定删除?")){
			window.location="${ctx}/video/delete?res_type=HTML&video_id="+id;
		}
	}
	
	function editVideo(id){
		if(!checkPackageStat()){
			return
		}
		window.location='${ctx}/video/async/updatepage?role=1&video_id='+id;
	}
	loaddata(1);
	function loaddata(pn){
		var url = ctx+'/video/listhmdata';
		
		$.post(url,{pageNo:pn,pageSize:4},function(data){
			//console.log(data);
			$('#page-content').html(data);
			$('#row-count-span').html($('#rowCount').val());
		});
	}
	function go(pn){
		loaddata(pn);
	}
	//查看原因
	function showReason(msg){
		$('#reason-area').text(msg);
		$('#reason-win').toggle();
	}
var curr_vid;
var curr_active;
	function changeActiveState(vid){
		
		if(!checkPackageStat()){
			return
		}
		
		var active = $('#active-'+vid).attr("active-state");
		if(active=='true'){
			active = false;
		}else{
			active = true;
		}
		vid = parseInt(vid);
		curr_vid = vid;
		curr_active = active;
		$.post(ctx+"/video/update_ajax",{"video_id":vid,"active":active},function(data){
			var datas = data.substring(data.indexOf('{')+1,data.lastIndexOf('}'));
    		  datas = "{" + datas + "}";
    		  var attr = eval('('+datas+')');
    		  if(attr.success == true){
    			  $('#active-'+curr_vid).attr("active-state",''+curr_active);
    			  $('#active-'+curr_vid).text(curr_active?'下线':'发布');
    		  }else{
    			  alert("修改激活状态失败");
    		  }
		});
	}
function preView(id){
	var v_url = $('#pre-'+id).attr("v_url");
	$('#video-yulan').toggle();
	
	console.log(v_url);
	$("div#yulan").flash(
	    {
	      swf:ctx+"/source/swf/StrobeMediaPlayback.swf",
	      width:704,
	      height:555,
	      allowFullScreen:true,
	      wmode: 'opaque',
	      flashvars:{
	        externalVideoUrl:v_url
	      }
	    }
    );
}
function closePreView(){
	$('#video-yulan').toggle();
	$("div#yulan").flash().remove();
}




function checkPackageStat(){
	
	var isOutDate="${isOutDate}";
	var flowEnough="${flowEnough}";

	if(isOutDate=="true"){
		$("#packagelimitDesc").css("display","");
		$("#packagelimitDesc").text("套餐已过期。请续约，");
		return false;
	}else if(flowEnough=='false'){	
		$("#packagelimitDesc").css("display","");
		$("#packagelimitDesc").text("流量不够.请购买流量。");
		return false;
	}
	return true;
}



function addVideo(){
	if(!checkPackageStat()){
		return
	}
	window.location='${ctx}/video/async/page';	
}
</script>
</html>