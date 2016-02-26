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
                        width="271" height="240"/><br />
                        <input type="file" name="up_big_img" id="up_big_img" onchange="javascript:v.upload.img_cover();" value="选择图片" />
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
<script type = "text/javascript" charset="utf-8" src="${CONTEXT_PATH}/source/js/plus/jquery.imgareaselect.pack.js"></script>
<script type = "text/javascript" charset="utf-8" src="${CONTEXT_PATH}/source/js/ajaxfileupload.js"></script>
<script type = "text/javascript" charset="utf-8" src="${CONTEXT_PATH}/source/js/file_upload.js"></script>

</body>
</html>