<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<c:set var="rootpath" value="${CONTEXT_PATH}/store"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<%@ include file="/WEB-INF/views/inc/_css.jsp" %>
	<%@ include file="/WEB-INF/views/inc/_scripts.jsp" %>
</head>
<body>
<script type="text/javascript">
	$(document).ready(function(){
	        alert('test');
			initPhoto();
    });
    
	function initPhoto(){
		//初始化图片上传  
   		var btn = document.getElementById("logoUrl");
   		alert(btn);
   		var img = document.getElementById("logoImg");
   		alert(img) ;  
   		var hidImgName = document.getElementById("eqId");
   		alert(hidImgName);
  		g_AjxUploadImg(btn, img, hidImgName, 0,"bl"); 	 	
	}
	
	//图片上传  
	function g_AjxUploadImg(btn, img, hidPut, type, name) {
		var button = btn;  
		new AjaxUpload(button, {  
			action: "<c:out value= '${ctx}'/>/admin/ajaxUploadFile?cpId=<c:out value='${sysmgrStore.id}' />&type=" + type,  
			data: {},  
			name: 'myfile',  
			onSubmit: function(file, ext) {  
				if (!(ext && /^(jpg|JPG|png|PNG|gif|GIF)$/.test(ext))) {  
					$.messager.alert("提示","您上传的图片格式不对，请重新选择！");  
					return false;  
				}  
			},  
			onComplete: function(file, response) {  
				var flagValue = response;  
				if (flagValue == "-2") {  
					$.messager.alert("提示","您上传的图片格式不对，请重新选择！");  
				}  
				else if (flagValue == "-3") {  
					$.messager.alert("提示","您上传的图片大于200K，请重新选择！");  
				}  
				else if (flagValue == "-1") {  
					$.messager.alert("提示","图片上传失败！");  
				}  
				else {  
					img.src = "<c:out value= '${ctx}'/>/admin/ajaxShowPicture?id=" + flagValue;	
					hidPut.value = flagValue;
					
					var te = new Image();				
					te.src = img.src;
					te.onload = function(){
						document.getElementById(name + "Width").value = te.width;
						document.getElementById(name + "Height").value = te.height;
					};
					
					img.width = 271;
					img.height = 240;				
				}  
			}  
		});  
	}
		
	function showPic(obj,value,wid,hei){
		var div= $("<div id=\"showPic\"></div>");
		div.appendTo("body");	
		var id = document.getElementById(value).value;
		var img = getImage(obj,wid,hei);
		var width = getTrueWidth(img);
		var height = getTrueHeight(img);
		var result = getPicResult(img,id,"<c:out value= '${ctx}'/>/admin/ajaxShowPicture?id=","<c:out value= '${ctx}'/>/static/images/noPicTure.jpg");
		var widthEnd = 20;
		var HeightEnd = 40;
		if(width == window.screen.availWidth * 0.8){
			HeightEnd = 60;
		}
		if(height == window.screen.availHeight * 0.8){
			widthEnd = 40;
		}	
		$('#showPic').dialog({    
	    	width:width + widthEnd,    
	    	height:height + HeightEnd,		       
	    	top:fixTop(), 
	    	modal:true,
	    	title:'图片显示',
	    	minimizable: false,
	    	closed:true,
	    	content:result,
	    	onClose:function(){
				$("#showPic").remove();								
	    	}		    
		});	
		$('#showPic').dialog('open');
		$('#showPic').dialog('refresh');
	}			
</script>

<div class="container-fluid">
    <form:form class="form-horizontal" commandName="sysmgrStore" modelAttribute="sysmgrStore" data-widget="validator" data-item-class="form-group" data-item-error-class="has-error" data-explain-class="j-explain" role="form" action="${rootpath}/${sysmgrStore.id>0?'update':'save' }">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">编辑</h3>
            </div>
            <div class="panel-body ">
            	<span style="color: red;"><form:errors path="*" /></span>
            	<input type="hidden" name="eqId" value="${sysmgrStore.id }" />
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">店铺名称</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-email" value="${sysmgrStore.name }" name="name" required type="text" placeholder="角色名称">
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:green;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-select">店铺描述</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control " id="j-email" value="${sysmgrStore.description }" name="description" required type="text" placeholder="店铺描述（限制250字）"> 
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-select">
                                                    上传图片：请上传所有证件的扫描件（不接受拍照图片），JPG格式，200K以内
                    <br />店铺LOGO图</label>
                    <div class="col-sm-6">
                        <img onclick="showPic(this,'businessLicenseId','blWidth','blHeight')" 
                        style="cursor:pointer" name='businessLicenseName' 
                        id='businessLicenseName' 
                        src='<c:out value= "${ctx}"/>/static/images/loading.gif' 
                        width="271" height="240"/>
                        <img src="${sysmgrStore.logoUrl }" name="logoImg" alt="" />
                        <input type="file" class="form-control " id="j-email" value="" name="logoUrl" required type="file" placeholder="店铺LOGO图">
                        <input type="hidden" id="blWidth" name='blWidth' value='0'/>
						<input type="hidden" id="blHeight" name='blHeight' value='0'/>	
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;">
                    </div>
                </div>
            </div>
        </div>
        <div class="clearfix" style="margin-bottom:10px;">
            <div class="pull-right">
            	<button class="btn btn-default btn-lg " type="reset" onclick="history.go(-1);">返回</button>&nbsp;&nbsp;
                <button class="btn btn-default btn-lg " type="reset">重置</button>&nbsp;&nbsp;
                <button class="btn btn-primary btn-lg " type="submit">确认</button>
            </div>
        </div>
    </form:form>
</div>

<script type="text/javascript" language="javascript">
	
	seajs.use(['$','widget'],function($,Widget){
	    //表单验证
	    Widget.autoRenderAll();
	});
	
</script>

</body>
</html>