<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="<%=request.getContextPath() %>"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>审核界面</title>
<link rel="stylesheet" type="text/css" href="${ctx}/source/css/partner/list_video.css"/>
<script type = "text/javascript" charset="utf-8" src="${ctx}/source/js/libs/jquery/jquery/jquery.js"></script>
<script type = "text/javascript" charset="utf-8" src="${ctx}/source/js/jquery.swfobject.1-1-1.js"></script>
</head>
<body>
<div class="container-fluid">
     <div class="video-add">
        <div class="video-add-title">
        	<ul>
           	   <li id="li-0" class="current" onclick = "changeCheckStateLi(this,-1)">所有视频</li>
               <li id="li-2" class="" onclick = "changeCheckStateLi(this,2)">已上线</li>
               <li id="li-1" class="" onclick = "changeCheckStateLi(this,1)">待审核</li>
               <li id="li-1" class="" onclick = "changeCheckStateLi(this,0)">编码中</li>
               <li id="li-4" class="" onclick = "changeCheckStateLi(this,4)">下线</li>
               <li id="li-3" class="" onclick = "changeCheckStateLi(this,3)">未通过</li>
           </ul>
        </div>
     </div>
     <div class="video-tabs"><!--video-tabs Begin-->
	 	<div class="video-tabs-cons">
	       <div class="video-add-cons">
	        <ul class="bfq-tab">
	            <li style="padding-top:10px;"><label>商家名称</label></li>
	            <li><input type="text" name = "seller-name" class="inputs"></li>
	            <li><a href="javascript:void(0)" name = "search" onclick= "doSearch(1)" class="zhineng-sreach">搜索</a></li>
	        </ul>
	        <div class="clear"></div>
	       </div>
			<!--商品弹出框-->
              <div id = "video-yulan" style="position:absolute;background-color:#269fde;width:704px;height:555px; z-index:1000;left:14%;top:10%; display:none;">
	              <div style = "position: absolute;right:0;top:0;"><a href="javascript:void()" onclick="closePreView()" class="close-icon"></a></div>
	              <div id = "yulan" class="video-yulan" ></div>
              </div>
			<!--查看原因-->
			<div id = "reason-win" class="video-kuangs video-kuangs-1"  style="display: none;z-index: 9999">
				<div class="video-titles">
					<span>查看原因</span> <a href="javascript:cancelReason()" class="close-icon"></a>
				</div>
				<div class="comm-cons">
					<!--comm-cons Begin-->
					<div class="comm-cons-bz">
						<table>
							<tbody>
								<tr>
									<td valign="top">原因：</td>
									<td><textarea style="width:373px;height:96px;" id = "reason-area" readonly="readonly"></textarea></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div style="width: 216px; margin-top: 20px; margin-left: 95px;">
						<a href="javascript:cancelReason()" class="video-btnsInfors">取消</a>
					</div>
				</div>
				<!--comm-cons End-->
			</div>
		
			<!--视频违规下线弹出框-->
			<div id = "offline-win" class="video-kuangs video-kuangs-2" style="display: none;z-index: 9999">
				<div class="video-titles">
					<span>违规下线</span> <a href="javascript:cancelOffline()" class="close-icon"></a>
				</div>
				<div class="comm-cons">
					<!--comm-cons Begin-->
					<div class="comm-cons-bz">
						<table>
							<tbody>
								<tr>
									<td valign="top">下线理由：</td>
									<td><textarea style="width:373px;height:96px;" name = "o-msg"></textarea></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div style="width: 216px; margin-top: 20px; margin-left: 95px;">
						<a href="javascript:doCheck(4)" class="video-btnsInfor">违规下线</a>
						<a href="javascript:cancelOffline()" class="video-btnsInfors">取消</a>
					</div>
				</div>
				<!--comm-cons End-->
			</div>
		
		
			<!--视频审核弹出框-->
			<div id = "check-win" class="video-kuangs video-kuangs-3" style="display: none;z-index: 9999">
				<div class="video-titles">
					<span>视频审核</span> <a href="javascript:javascript:cancelCheck()" class="close-icon"></a>
				</div>
				<div class="comm-cons">
					<!--comm-cons Begin-->
					<div class="comm-cons-bz">
						<table>
							<tbody>
								<tr>
									<td valign="top">原因：</td>
									<td><textarea style="width:373px;height:96px;" name ="check-msg"></textarea></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div style="width: 216px; margin-top: 20px; margin-left: 155px;">
						<a href="javascript:doCheck(2)" class="video-btnsInfor">通过审核</a>
						<a href="javascript:doCheck(3)"  class="video-btnsInfors">拒绝</a>
					</div>
				</div>
				<!--comm-cons End-->
			</div>
			<!--选择-->
	    <div id = "content" class="voideo-add2"><!--voideo-add2 Begin-->
	    
	    </div>
	     
	    </div><!--voideo-add2 End-->
   	</div>

</div><!--video-tabs End-->
<script type="text/javascript">
var _pram_check_state = "${check_state}";
var ctx = "${ctx}";
var _name = "";
var _currId;
var _ids = "";//批量下线 视频ids
var _state = -1;
var _pn = 1;
init();
function init(){
	if(_pram_check_state){
		var obj = $('#li-'+_pram_check_state);
		changeCheckStateLi(obj,parseInt(_pram_check_state));
	}else{
		go(1);
	}
}
function changeCheckStateLi(obj,state){
	_state = state;
	$('.current').removeClass("current");
	$(obj).addClass("current");
	go(1);
}
function doSearch(pn){
	_name = $('input[name="seller-name"]').val();
	if(!_name||$.trim(_name).length<=0){
		_name = '';
	} 
	go2(_name,pn);
}
function go(pn){
	go2(_name,pn);
}
function go2(_name,pn){
	_pn = pn;
	var url = ctx+"/video/searchhm"; 
	$.post(url,{seller_name:_name,check_state:_state,pageNo:pn,pageSize:4},function(data){
		$('#content').html(data);
		if(_state==0){
			$("#multi-offline").hide();
		}
	});
}
function check(id){
	_currId = id;
	//console.log("_currId"+_currId);
	$('#check-win').show();
}
function offline(id){
	_currId = id;
	$('#offline-win').show();
}
function showReason(msg){
	//console.log($('#reason-win'));
	$('#reason-area').val(msg);
	$('#reason-win').show();
}
function cancelCheck(){
	$('#check-win').hide();
}
function cancelReason(){
	$('#reason-win').hide();
}
function cancelOffline(){
	$('#offline-win').hide();
}
function doCheck(state){
	var msg = $('textarea[name="check-msg"]').val();
	var _state = 2;//通过
	if(state){
	 _state = parseInt(state);
	}
	if(_state == 4){
		msg = $('textarea[name="o-msg"]').val();
	}
	if(_state==3||_state == 4){//未通过，下架
		if(msg==null||$.trim(msg).length<=0){
			if(_state==3){
				alert("请填写备注");
			}
			if(_state==4){
				alert("请填写下线理由");
			}
			return ;
		}
	}
	if(_currId==_ids&&_state == 4){//批量下线
		offLineMulti(msg);
		return;
	}
	var url = ctx+"/video/check";
	//console.log("_currId"+_currId);
	msg = msg.replace(/\s+/g, "");
	if(msg.length>200){
		msg = msg.substring(0,200);
	}
	$.post(url,{id:_currId,state:_state,msg:msg},function(data){
		var datas = data.substring(data.indexOf('{')+1,data.lastIndexOf('}'));
 		  datas = "{" + datas + "}";
 		  var attr = eval('('+datas+')');
 		  if(attr.success){
 			  alert("成功");
 			window.location.reload();
 		  }else{
 			  alert("失败");
 		  }
	});
}
function offLineMulti(msg){
	var url = ctx+"/video/offLineMulti";
	$.post(url,{ids:_ids,msg:msg},function(data){
		  var datas = data.substring(data.indexOf('{')+1,data.lastIndexOf('}'));
  		  datas = "{" + datas + "}";
  		  var attr = eval('('+datas+')');
  		  //console.log(attr);
  		if(attr.success){
			  alert("成功");
			window.location.reload();
		  }else{
			  alert("失败");
		  }
	});
}
function deleteVideo(vid){
	if(vid){
		if(confirm("确定删除?")){
			var url = ctx+"/video/delete";
			//console.log(url);
			$.post(url,{video_id:vid,res_type:"JSON"},function(data){
				var datas = data.substring(data.indexOf('{')+1,data.lastIndexOf('}'));
		  		  datas = "{" + datas + "}";
		  		  var attr = eval('('+datas+')');
		  		  //console.log(attr);
		  		  if(attr.success){
		  			  go(_pn);
		  		  }
			});
		}	
	}
}
function checkAll(obj){
	var _all_state = $(obj).attr("checked");
	var _checks = $('input[name="checkboxname"]');
	if(_all_state){
		if(_checks){
			if(_checks.length>0){
				for(var i = 0;i<_checks.length;i++){
					$(_checks[i]).attr("checked",true);
				}
			}
		}
	}else{
		if(_checks){
			if(_checks.length>0){
				for(var i = 0;i<_checks.length;i++){
					$(_checks[i]).attr("checked",false);
				}
			}
		}
		
	}
	//console.log(_all_state);
	//console.log(_checks);
}
function allOffLine(){
	var _checks = $('input[name="checkboxname"]:checked[vstate="2"]');
	if(_checks.length==0){
		alert("没有选中已上线视频");
		return;
	}
	
	for(var i=0;i<_checks.length;i++){
		_ids += $(_checks[i]).attr("vid")+",";
	}
	_ids = _ids.substring(0,_ids.length-1);
	offline(_ids);
}

function preView(id){
	var v_url = $('#pre-'+id).attr("v_url");
	//v_url=v_url.replace("http://","");
	$('#video-yulan').toggle();
	$("div#yulan").flash(
	    {
	      swf:ctx+"/source/swf/shenhe/YPPlayer.swf?v=${version}",
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
</script>
</body>
</html>