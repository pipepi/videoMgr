<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<c:set var="ctx" value="<%=basePath %>"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>智能商家管理系统</title>
<link rel="stylesheet" type="text/css" href="${ctx}/source/css/imgcut/imgareaselect-default.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/source/css/partner/edit_video.css"/>
<script type = "text/javascript" charset="utf-8" src="${ctx}/source/js/libs/jquery/jquery/jquery.js"></script>
<script type = "text/javascript" charset="utf-8" src="${ctx}/source/js/plus/jquery.imgareaselect.pack.js"></script>
<script type = "text/javascript" charset="utf-8" src="${ctx}/source/js/ajaxfileupload.js"></script>
<script type="text/javascript"> var ctx = "${ctx}";</script>
<script type = "text/javascript" charset="utf-8" src="${ctx}/source/js/video/file_upload_async.js"></script>
<script type = "text/javascript" charset="utf-8" src="${ctx}/source/js/video/drag.js"></script>
</head>
<body>
  <div  class="container-fluid <c:if test="${role==1}">container-fluid2</c:if>" ><!--container-fluid Begin-->
 <!--网络错误-->
<div class="video-kuangs-newInfors video-kuangs-new" style="display:none;" >
    <div><a href="javascript:void(0)" onclick = "reflashpage()" class="close-icon"></a></div>
    <div class="comm-cons-newInfors"><!--comm-cons Begin-->
     <em class="wlcw-font">网络错误</em>
     <i>请刷新页面，再次上传视频</i>
      <div><a href="javascript:void(0)" onclick = "reflashpage()" class="video-btnsInfors-new">确定</a></div>
    </div>
    <!--comm-cons End--> 
  </div> 
      <div class="video-add">
         <div class="video-add-title">
         	<ul>
         		<c:choose>
         			<c:when test="${role==1 }">
            			<li onclick = "window.location='${ctx}/video/listhm'">视频列表</li>
         			</c:when>
         			<c:otherwise>
         				<li onclick = "window.location='${ctx}/video/checkpage?check_state=-1'">所有视频</li>
		               	<li onclick = "window.location='${ctx}/video/checkpage?check_state=2'">已上线</li>
		               	<li onclick = "window.location='${ctx}/video/checkpage?check_state=1'">待审核</li>
		               	<li onclick = "window.location='${ctx}/video/checkpage?check_state=0'">编码中</li>
		               	<li onclick = "window.location='${ctx}/video/checkpage?check_state=4'">下线</li>
		               	<li onclick = "window.location='${ctx}/video/checkpage?check_state=3'">未通过</li>
         			</c:otherwise>
         		</c:choose>
                <li class="current" >${video==null?"添加":"编辑"}视频</li>
            </ul>
         </div>
        
      </div>
      <div class="voideo-add3">
        <div class="video-add-cons">
          <div class="video-text"><label><span>*</span> 视频来源：</label>
          <div id="divselect" style="display:none;"> 
            <cite>请选择视频方式</cite> 
           	 <ul> 
            	<li><a href="javascript:;" selectid="1">外链视频</a></li> 
           	    <li><a href="javascript:;" selectid="2">上传视频</a></li> 
            </ul> 
           </div> 
			<input name="" type="hidden" value="" id="inputselect"/> 
           <div style="float:left; margin-top:7px;"> &nbsp;视频文件（最大200M、MP4、MKV、MOV、AVI、WMV格式）</div>
          
         </div>
          <br/>
          <!--外链视频-->
          <div style="margin-left:90px;margin-top:30px; display:none;">
          <div class="adress-kuang">粘贴地址到此框</div>
          <input type="button"  value="保存" class="btns-bc"/><input type="button"  value="预览" class="btns-yl"/></div>
          <!--上传视频-->
          <div class="load-video">
           <c:if test='${video!=null && video.checkState==0 }'>
           		<div style="float: left;height:80px;"><font style="color:red;">编码中...</font></div>
           </c:if>
           <div class="clear"></div>
           <div style="overflow: hidden;<c:if test='${video!=null && video.checkState==0 }'>display:none;</c:if>">
             
              <div style="float:left;margin-right:10px;">
	              	<a class="btns-bc"   href="javascript:void()" >选择视频
	               		<input type="file" onchange="v.upload.video();"  value="选择文件"  id="up_video"  tabIndex=3 size=3 name="up-video" class="filePrew"
	               			style="cursor: pointer;"/>
	               </a>
	             
               </div>
                <div id = "video-tip" style = "display:none;" class="tis-infors-s"></div>
               <input type="button"  value="预览" class="btns-yl" style="display: none;"/>
               
            </div> 
             <div class="progress-bar" style="margin-left:5px;display: none;">             
	              <div class="progress-bar-cons"></div>
	              <div class="progress-bar-font">
	              	<ul>
	                 <li>视频上传</li>
	                 <li style="margin-left:220px;">上传完成</li>
	                </ul>
	              </div>
	              <div class="progress-quan1"><img src="${ctx}/source/images/aztimg/quan1.png" /></div>
	              <div class="progress-quan2"><img src="${ctx}/source/images/aztimg/quan1.png" /></div>
	              
	              <div style="position:relative;width:240px; top:-10px;margin-left:28px;">
	              	<div style="left:50%" class="progress-number">0%</div>
	              	<div class="progress-bar-cons-up" style="width:0%"></div>
	              </div>
	              <em id="uping_file_name"></em>
			 </div>
             
             
             <div id="uploaded_video" style="margin-top:5px;border:1px solid #dcdcdc;height:30px;width:500px;${video==null||video.checkState==0?'display:none;':''}" >
             	<em style="font-style:normal;width:400px;height:15px;overflow:hidden; float:left;margin-top:3px;padding-left:4px;">
             		<c:choose>
             			<c:when test="${video==null }"></c:when>
             			<c:when test="${video.checkState==0 }">${video.uploadAssetId }</c:when>
             			<c:when test="${video.checkState>0 }">${video.video }</c:when>
             			<c:otherwise></c:otherwise>
             		</c:choose>
             	</em>
             	<a href="javascript:void(0)" onclick ="v.del(this)" style="background:#e3393c;text-align:center; margin-left:10px;float:right;text-decoration:none;line-height:30px; color:#ffffff;display:block;width:80px;height:30px;">删除</a>
             </div>
             
             <!--封面图-->
             <div class="fmt-images"> 
               <em>视频封面图（封面图尺寸为1004*565；建议上传不超过3M，分辨率不小于1004*565，JPG、PNG格式的图片）</em>
               <span style="display:block;">请勿上传涉及他人隐私、色情暴露、暴力、血腥、可能引起大部分用户反感的一级违反有关法律法规和政策的图片。</span>
               <a class="btns-bc"  style="float:left;" href="javascript:void(0)">选择图片
               		<input type="file"  value="选择文件" name = "upload-pic" onchange="v.upload.img_cover();" id="upload-pic"  tabIndex=3 size=3 name=pic class="filePrew"
               		 style="cursor: pointer;"/>
               </a>
               
               &nbsp;<div id = "img-tip" style = "display:none;" class="tis-infors"></div>
             </div>
             
             <!--剪裁范围-->
             <div class="jcfw-images" style="display:none;">
             
              <div id = "show_big_img" class="jcfw-bigImg">
              	<img alt="剪裁范围" src="${video==null?'':video.imgMin }" width="200" height="113" id = "cover_img_big" ${video==null?'style="display:none;"':'' }></img>
              	<em id = "img-title" style = "font-style:normal; ${video==null?'':'display:none;' }">剪裁范围</em>
              </div>
              
              <div class="jcfw-smallImg" id = "cover_div_small">预览</div>
             </div>
              <!-- 新图片裁剪 begin -->
             <div style="float:left;margin-top: 150px;">
             	<div id="cut_div" style="border:1px solid #666666; width:284px; height:266px; overflow: hidden; position:relative; top:0px; left:0px; margin:4px; ">
				 <table style="border-collapse: collapse; z-index: 10; filter: alpha(opacity=75); position: relative; left: 0px; top: 0px; width: 284px;  height: 266px; opacity: 0.75;" cellspacing="0" cellpadding="0" border="0" unselectable="on">
				 <tbody><tr>
				   <td style="background: #ffffff; height: 65.5px;cursor:move;" colspan="3"></td>
				 </tr>
				 <tr>
				   <td style="background: #ffffff; width: 22px;cursor:move;"></td>
				   <td style="border: 2px solid #ffffff; width: 240px; height: 135px;cursor:move;"></td>
				   <td style="background: #ffffff; width: 22px;cursor:move;"></td>
				 </tr>
				 <tr>
				   <td style="background: #ffffff; height: 65.5px;cursor:move;" colspan="3"></td>
				 </tr>
				 </tbody></table>
				 <img id="cut_img" style="position: relative; top: -201px; left: 21px;${video==null?'display:none;':'' }" src="${video==null?'':video.imgMin }" width="240" height="135"  />
				</div>
             	
             	<table cellspacing="0" cellpadding="0">
				  <tbody><tr>
				    <td><img style="margin-top: 5px; cursor:pointer; cursor:" src="/source/images/imagecut/_h.gif" alt="图片缩小" onmouseover="this.src='/source/images/imagecut/_c.gif'" onmouseout="this.src='/source/images/imagecut/_h.gif'" onclick="imageresize(false)"></td>
				    <td><img id="img_track" style="width: 250px; height: 18px; margin-top: 5px" src="/source/images/imagecut/track.gif"></td>
				    <td><img style="margin-top: 5px; cursor:pointer;" src="/source/images/imagecut/+h.gif" alt="图片放大" onmouseover="this.src='/source/images/imagecut/+c.gif'" onmouseout="this.src='/source/images/imagecut/+h.gif'" onclick="imageresize(true)"></td>
				  </tr>
				</tbody></table>
				
				<img id="img_grip" style="position: relative; z-index: 100; left: -71px; top: -18px; cursor: pointer;" src="/source/images/imagecut/grip.gif">
             	<input type="hidden" name="cut_pos" id="cut_pos" value="0,0,0,0">
             	<input type="button" name = "cut-img" id = "cut_big_img_div" onclick="v.upload.img_cut();" value="裁剪" class="cut_btn" style="float:left;"/>
             </div>
             <!-- 新图片裁剪 end -->
          </div>
     
           <div class="clear"></div>   
        </div>
       <div class="video-add-cons" style="margin-top:20px;" >
           <div class="video-texts"><label>
           	<span>*</span> 视频名称：</label>
           	<input type="submit" onclick="return false;" style="display:none"></input>
           	<input type="text" name = "video-name" class="inputs" maxlength="20"
           		value="${video==null?'请输入视频名称':video.name }" 
           		onfocus="if(this.value =='请输入视频名称')this.value='';"
           		onblur="if($.trim(this.value).length == 0){this.value='请输入视频名称';}else{$('#name-tip').hide();}"
           		/>&nbsp;<span id = "name-tip" style = "display:none;vertical-align: top"></span>
           </div>
           <div class="video-texts">
           	<label><span>*</span> 视频描述：</label>
           	<textarea name = "video-desc" maxlength="140"
           		onfocus="if(this.value =='请输入视频描述')this.value='';"
           		onblur="if($.trim(this.value).length == 0){this.value='请输入视频描述';}else{$('#desc-tip').hide();}"
           		>${video==null?'请输入视频描述':video.desc }</textarea>&nbsp;<span id = "desc-tip" style = "display:none;vertical-align: top"></span>
           </div>
         </div>
          <div class="video-btns-cons">
              <a href="javascript:v.submit_all();" id="sub-btn" class="video-btnsInfor">保存</a>
              <a href="javascript:window.history.go(-1)" class="video-btnsInfors">取消</a>
             </div>
          <div class="clear"></div>   
      </div>
      	<input type="hidden" name = "id" value="${video==null?'':video.id }"/>
      	<input type="hidden" name = "role" value="${role }"/>
		<input type="hidden" name = "src_img_id" />
		<input type="hidden" name = "cut_img_id" />
		<input type="hidden" name = "video_file_name" value = "${video==null?'':video.uploadAssetId }"/>
		<input type="hidden" name = "video_url" value = "${video==null?'':video.encodeAssetId }"/>
		<input type="hidden" name = "upload_asset_id" value = "${video==null?'':video.uploadAssetId }"/>
		<input type="hidden" name = "encode_asset_id" value = "${video==null?'':video.encodeAssetId }"/>
		
  </div><!--container-fluid End-->
<script type="text/javascript">
var ctx = "${ctx}";
var partner_ctx = "${partner_ctx}";
init();
function init(){
	var videoVar = '${video}';
	if(videoVar==''){
		v.canUpVideo = true;
	}else{
		$('#up_video').attr('disabled',true);
		$('#up_video').parent().css('background-color','#999999');
		v.canUpVideo = false;
	}
	////console.log(v.canUpVideo);
	//套餐视频数量控制
	var error_msg = '${error_msg}';
	if(error_msg){
		if(error_msg!=''){
			alert(error_msg);
			$('#up_video').attr('disabled',true);
			$('#up_video').parent().css('background-color','#999999');
			v.canUpVideo = false;
			window.location = ctx+"/video/listhm";
		}
	}
}
function reflashpage(){
	top.window.location.href=partner_ctx+"/SellerAdmin?url="+ctx+"/video/async/page&tar="+ctx+"/video/listhm";
}
</script>
<script language="javascript" type="text/javascript">
	var cut_div;  //裁减图片外框div
	var cut_img;  //裁减图片
	var imgdefw;  //图片默认宽度
	var imgdefh;  //图片默认高度
	var offsetx = 22; //图片位置位移x
	var offsety = -200; //图片位置位移y
	var divx = 284; //外框宽度
	var divy = 266; //外框高度
	var cutx = 240;  //裁减宽度
	var cuty = 135;  //裁减高度
	var zoom = 1; //缩放比例

	var zmin = 0.1; //最小比例
	var zmax = 10; //最大比例
	var grip_pos = 5; //拖动块位置0-最左 10 最右
	var img_grip; //拖动块
	var img_track; //拖动条
	var grip_y; //拖动块y值
	var grip_minx; //拖动块x最小值
	var grip_maxx; //拖动块x最大值
	
	
//图片初始化
function imageinit(){
	cut_div = document.getElementById('cut_div');
	cut_img = document.getElementById('cut_img');
	imgdefw = cut_img.width;
	imgdefh = cut_img.height;
	if(imgdefw > divx){
		zoom = divx / imgdefw;
		cut_img.width = divx;
		cut_img.height = Math.round(imgdefh * zoom);
	}

	cut_img.style.left = Math.round((divx - cut_img.width) / 2);
	cut_img.style.top = Math.round((divy - cut_img.height) / 2) - divy;

	if(imgdefw > cutx){
		zmin = cutx / imgdefw;
	}else{
		zmin = 1;
	}
	//zmax =  zmin > 0.25 ? 8.0: 4.0 / Math.sqrt(zmin);
	if(imgdefw > cutx){
		zmin = cutx / imgdefw;
		grip_pos = 5 * (Math.log(zoom * zmax) / Math.log(zmax));
	}else{
		zmin = 1;
		grip_pos = 5;
	}

	Drag.init(cut_div, cut_img);
	cut_img.onDrag = when_Drag;
}

//图片逐步缩放
function imageresize(flag){
    if(flag){
		zoom = zoom * 1.05;
	}else{
		zoom = zoom / 1.05;
	}
	if(zoom < zmin) zoom = zmin;
	if(zoom > zmax) zoom = zmax;
	cut_img.width = Math.round(imgdefw * zoom);
	cut_img.height = Math.round(imgdefh * zoom);
	checkcutpos();
	//grip_pos = 5 * (Math.log(zoom * zmax) / Math.log(zmax));
	grip_pos = (zoom-zmin)/(zmax-zmin);
	img_grip.style.left = (grip_minx + (grip_pos* (grip_maxx - grip_minx))) + "px";
}

//获得style里面定位
function getStylepos(e){  
	return {x:parseInt(e.style.left), y:parseInt(e.style.top)}; 
}

//获得绝对定位
function getPosition(e){  
	var t=e.offsetTop;  
	var l=e.offsetLeft; 
	//console.log("t="+t+"  l="+l);
	while(e=e.offsetParent){  
		//t+=e.offsetTop;  
		l+=e.offsetLeft;  
	}
	//console.log("t="+t+"  l="+l);
	l=l-161;
	t=t-4;
	return {x:l, y:t}; 
}

//检查图片位置
function checkcutpos(){
	var imgpos = getStylepos(cut_img);
	
	max_x = Math.max(offsetx, offsetx + cutx - cut_img.clientWidth);
	min_x = Math.min(offsetx + cutx - cut_img.clientWidth, offsetx);
	if(imgpos.x > max_x) cut_img.style.left = max_x + 'px';
	else if(imgpos.x < min_x) cut_img.style.left = min_x + 'px';

	max_y = Math.max(offsety, offsety + cuty - cut_img.clientHeight);
	min_y = Math.min(offsety + cuty - cut_img.clientHeight, offsety);
	//console.log("   max_y="+max_y+"  min_y="+min_y);
	if(imgpos.y > max_y) cut_img.style.top = max_y + 'px';
	else if(imgpos.y < min_y) cut_img.style.top = min_y + 'px';
	getTransform();
}

//图片拖动时触发
function when_Drag(clientX , clientY){
	checkcutpos();
}
function getTransform(){
	var imgpos = getStylepos(cut_img);
	v.options.x1 = offsetx - imgpos.x;
	v.options.y1 = offsety - imgpos.y;
	v.options.x2 = v.options.x1 + cutx;
	v.options.y2 = v.options.y1 +  cuty;
	v.options.scale = zoom;
	//console.log("x1="+v.options.x1+"  y1="+v.options.y1+"  x2="+v.options.x2+"  y2="+v.options.y2+"  zoom="+zoom);
	
}
//获得图片裁减位置
function getcutpos(){
	var imgpos = getStylepos(cut_img);
	var x = offsetx - imgpos.x;
	var y = offsety - imgpos.y;
	var cut_pos = document.getElementById('cut_pos');
	cut_pos.value = x + ',' + y + ',' + cut_img.width + ',' + cut_img.height;
	return true;
}

//缩放条初始化
function gripinit(){
	img_grip = document.getElementById('img_grip');
	img_track = document.getElementById('img_track');
	track_pos = getPosition(img_track);
	//console.log("track_pos.y="+track_pos.y);
	grip_y = track_pos.y-19;
	grip_minx = track_pos.x -92;
	grip_maxx = track_pos.x + img_track.clientWidth - img_grip.clientWidth - 102;

	//img_grip.style.left = (grip_minx + (grip_pos / 10 * (grip_maxx - grip_minx))) + "px";
	//img_grip.style.top = grip_y + "px";

	Drag.init(img_grip, img_grip);
	img_grip.onDrag = grip_Drag;

}

//缩放条拖动时触发
function grip_Drag(clientX , clientY){
	var posx = clientX;
	//console.log("grip_y="+grip_y);
	img_grip.style.top = grip_y + "px";
	if(clientX < grip_minx){
		img_grip.style.left = grip_minx + "px";
		posx = grip_minx;
	}
	if(clientX > grip_maxx){
		img_grip.style.left = grip_maxx + "px";
		posx = grip_maxx;
	}
	//console.log("clientX="+clientX+"    grip_minx="+grip_minx+"    grip_maxx"+grip_maxx);
	grip_pos = (posx - grip_minx)/ (grip_maxx - grip_minx);
	//console.log("grip_pos="+grip_pos);
	//console.log("drag 1 zoom="+zoom+"   zmin="+zmin+"   zmax="+zmax);
	zoom = grip_pos*(zmax-zmin)+zmin;
	//console.log("drag 2 zoom="+zoom+"   zmin="+zmin+"   zmax="+zmax);
	if(zoom < zmin) zoom = zmin;
	if(zoom > zmax) zoom = zmax;
	cut_img.width = Math.round(imgdefw * zoom);
	cut_img.height = Math.round(imgdefh * zoom);
	checkcutpos();
}

//页面载入初始化
function avatarinit(){
	imageinit();
	gripinit();
	//刷新裁剪后的图片
	//document.getElementById('cutimg_l').src = document.getElementById('cutimg_l').src + '?' + (new   Date().getTime());
	//document.getElementById('cutimg_m').src = document.getElementById('cutimg_m').src + '?' + (new   Date().getTime());
	//document.getElementById('cutimg_s').src = document.getElementById('cutimg_s').src + '?' + (new   Date().getTime());

}

if (document.all){
	window.attachEvent('onload',avatarinit);
}else{
	window.addEventListener('load',avatarinit,false);
} 
</script>
</body>
</html>