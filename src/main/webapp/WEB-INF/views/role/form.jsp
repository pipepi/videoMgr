<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<c:set var="rootpath" value="${CONTEXT_PATH}/role"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<%@include file="/WEB-INF/views/inc/_css.jsp" %>
	<%@ include file="/WEB-INF/views/inc/_scripts.jsp" %>
</head>
<body>

<div class="container-fluid">
    <form:form class="form-horizontal" commandName="sysmgrRole" modelAttribute="sysmgrRole" data-widget="validator" data-item-class="form-group" data-item-error-class="has-error" data-explain-class="j-explain" role="form" action="${rootpath}/${sysmgrRole.id>0?'update':'save' }">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">编辑</h3>
            </div>
            <div class="panel-body ">
            	<span style="color: red;"><form:errors path="*" /></span>
            	<input type="hidden" name="eqId" value="${sysmgrRole.id }" />
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">角色名称</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-email" value="${sysmgrRole.name }" name="name" required type="text" placeholder="角色名称">
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:green;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-select">角色类别</label>
                    <div class="col-sm-6">
                        <select class="form-control " id="j-select" required data-rule="digits"  placeholder="下拉" name="type">
                            <option value="0" <c:if test="${sysmgrRole.type==0 }">selected</c:if>>普通用户</option>
                            <option value="1" <c:if test="${sysmgrRole.type==1 }">selected</c:if>>管理员</option>
                            <option value="2" <c:if test="${sysmgrRole.type==2 }">selected</c:if>>超级管理员</option>
                        </select>
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