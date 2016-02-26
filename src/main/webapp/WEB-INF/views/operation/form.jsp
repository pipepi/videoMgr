<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<c:set var="rootpath" value="${CONTEXT_PATH}/operation"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<%@ include file="/WEB-INF/views/inc/_css.jsp" %>
	<%@ include file="/WEB-INF/views/inc/_scripts.jsp" %>
</head>
<body>

<div class="container-fluid">
    <form:form class="form-horizontal" commandName="sysmgrOperation" modelAttribute="sysmgrOperation" data-widget="validator" data-item-class="form-group" data-item-error-class="has-error" data-explain-class="j-explain" role="form" action="${rootpath}/${sysmgrOperation.id>0?'update':'save' }">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">编辑</h3>
            </div>
            <div class="panel-body ">
            	<span style="color: red;"><form:errors path="*" /></span>
            	<input type="hidden" name="eqId" value="${sysmgrOperation.id }" />
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">操作名称</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-email" value="${sysmgrOperation.name }" name="name" required type="text" placeholder="操作名称">
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-number">操作编码</label>
                    <div class="col-sm-6">
                    	<input class="form-control " id="j-email" value="${sysmgrOperation.shortcut }" name="shortcut" required type="text" placeholder="操作编码">
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
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