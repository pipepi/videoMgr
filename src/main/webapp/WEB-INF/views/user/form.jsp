<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<c:set var="rootpath" value="${CONTEXT_PATH}/user"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<%@include file="/WEB-INF/views/inc/_css.jsp" %>
	<%@ include file="/WEB-INF/views/inc/_scripts.jsp" %>
</head>
<body>

<div class="container-fluid">
    <form:form class="form-horizontal" data-widget="validator" commandName="sysmgrUser" modelAttribute="sysmgrUser" data-item-class="form-group" data-item-error-class="has-error" data-explain-class="j-explain" role="form" action="${rootpath}/${sysmgrUser.id > 0 ? 'update' : 'save' }">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">编辑</h3>
            </div>
            <div class="panel-body ">
            	<span style="color: red;"><form:errors path="*" /></span>
            	<input type="hidden" name="eqId" value="${sysmgrUser.id }" />
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">用户名</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-email" value="${sysmgrUser.userName }" name="username" required type="text" placeholder="用户名" <c:if test="${read}">readonly=\"true\"</c:if> >
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <c:if test="${psread}">
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-pwd">密码</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-pwd" required type="password" data-rule="password" name="password" minlength="6" maxlength="16" placeholder="密码" >
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                </c:if>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">邮箱</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-email" name="email" value="${sysmgrUser.email }" required type="email" placeholder="邮箱" <c:if test="${read}">readonly=\"true\"</c:if> >
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-select">角色</label>
                    <div class="col-sm-6">
                        <select class="form-control " id="j-select" required data-rule="digits"  placeholder="下拉" name="roleId" <c:if test="${read}">readonly=\"true\"</c:if> >
                        	<c:forEach items="${sysmgrRoles }" var="role">
	                            <option value="${role.id }" <c:if test="${sysmgrUser.roleId==role.id }">selected</c:if>>${role.name }</option>
                        	</c:forEach>
                        </select>
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-select">套餐</label>
                    <div class="col-sm-6">
                        <select class="form-control " id="j-select" required data-rule="digits"  placeholder="下拉" name="packageId" <c:if test="${read}">readonly=\"true\"</c:if> >
                        	<c:forEach items="${sysmgrPackages }" var="package">
	                            <option value="${package.id }" <c:if test="${sysmgrUser.packageId == package.id }">selected</c:if>>${package.name }</option>
                        	</c:forEach>
                        </select>
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" >状态</label>
                    <div class="col-sm-6">
                        <label class="radio-inline">
                            <input type="radio" name="status" id="inlineRadio1" value="1" <c:if test="${sysmgrUser.status==1 }">checked</c:if>> 可用
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="status" id="inlineRadio2" value="-1" <c:if test="${sysmgrUser.status==-1 }">checked</c:if>> 停用
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="status" id="inlineRadio3" value="0" <c:if test="${sysmgrUser.status==0 }">checked</c:if>> 未审核
                        </label>
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