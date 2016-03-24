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
</head>
<body>
  <div  class="container-fluid container-fluid2" ><!--container-fluid Begin-->
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
         <div class="video-add-cons">
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
      </div>
      <div class="voideo-add2">
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
          <div style="margin-left:90px;margin-top:30px;display:none">
          <div class="adress-kuang">粘贴地址到此框</div>
          <input type="button"  value="保存" class="btns-bc"/><input type="button"  value="预览" class="btns-yl"/></div>
          <!--上传视频-->
          <div class="load-video">
           <div class="clear"></div>
           <c:if test='${video!=null && video.checkState==0 }'>
           		<font style="color:red;">编码中...</font>
           </c:if>
           <div style="overflow: hidden;<c:if test='${video!=null && video.checkState==0 }'>display:none;</c:if>">
             
              <div style="width:94px;float:left;margin-right:10px;">
	              	<a class="btns-bc"   href="javascript:void()" >选择视频
	               		<input type="file" onchange="v.upload.video();"  value="选择文件"  id="up_video"  tabIndex=3 size=3 name="up-video" class="filePrew"
	               			style="cursor: pointer;"/>
	               </a>
	             
               </div>
               <input type="button"  value="预览" class="btns-yl" style="display: none;"/>
                <span id = "video-tip" style = "display:none;"></span>
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
               <em style="margin-bottom:10px;">封面图（最大3M、JPG、PNG）</em>
               <a class="btns-bc"  style="position:relative; float:left;" href="javascript:void(0)">选择图片
               		<input type="file"  value="选择文件" name = "upload-pic" onchange="v.upload.img_cover();" id="upload-pic"  tabIndex=3 size=3 name=pic class="filePrew"
               		 style="cursor: pointer;"/>
               </a>
               
               &nbsp;<div id = "img-tip" style = "display:none;position: absolute;top:80px;" class="tis-infors"></div>
             </div>
             
             <!--剪裁范围-->
             <div class="jcfw-images">
             
              <div id = "show_big_img" class="jcfw-bigImg">
              	<img alt="剪裁范围" src="${video==null?'':video.imgMin }" width="200" height="113" id = "cover_img_big" ${video==null?'style="display:none;"':'' }></img>
              	<em id = "img-title" style = "font-style:normal; ${video==null?'':'display:none;' }">剪裁范围</em>
              </div>
              
              <div class="jcfw-smallImg" id = "cover_div_small">预览</div>
              <input type="button" name = "cut-img" id = "cut_big_img_div" onclick="v.upload.img_cut();$('#img-tip').hide();" value="裁剪" class="btns-yl" style="float:left"/>
             </div>
             
             <div class="video-btns-cons">
              <a href="javascript:v.submit_all();" id="sub-btn" class="video-btnsInfor">保存</a>
              <a href="javascript:window.history.go(-1)" class="video-btnsInfors">取消</a>
             </div>
          
          </div>
           <div class="clear"></div>   
        </div>
       
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
	//console.log(v.canUpVideo);
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
</body>
</html>