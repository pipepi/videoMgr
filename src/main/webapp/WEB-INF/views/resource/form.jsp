<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<c:set var="rootpath" value="${CONTEXT_PATH}/resource"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<%@include file="/WEB-INF/views/inc/_css.jsp" %>
	<%@ include file="/WEB-INF/views/inc/_scripts.jsp" %>
</head>
<body>

<div class="container-fluid">
    <form:form class="form-horizontal" commandName="sysmgrResource" modelAttribute="sysmgrResource" data-widget="validator" data-item-class="form-group" data-item-error-class="has-error" data-explain-class="j-explain" role="form" action="${rootpath}/${sysResource.id > 0 ? 'update' : 'save' }">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">编辑</h3>
            </div>
            <div class="panel-body ">
            	<span style="color: red;"><form:errors path="*" /></span>
            	<input type="hidden" name="id" value="${sysResource.id }" />
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">资源名称</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-email" value="${sysResource.name }" name="name" required type="text" placeholder="资源名称">
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">资源路径</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-email" value="${sysResource.url }" name="url" type="text" placeholder="资源路径">
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">父资源</label>
                    <div class="col-sm-6">
                    	<select id="pId" name="pId" class="selected" style="width:150px;">
						    <option value="0">请输入</option>
				        	<c:forEach items="${list }" var="resource">
							    <option value="${resource.id }" <c:if test="${resource.id == sysResource.pid }">selected="selected"</c:if>>${resource.name }</option>
			                </c:forEach>
						</select>
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:green;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" >状态</label>
                    <div class="col-sm-6">
                        <label class="radio-inline">
                            <input type="radio" name="status" id="inlineRadio1" value="1" <c:if test="${sysResource.status==1 }">checked</c:if>> 可用
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="status" id="inlineRadio2" value="-1" <c:if test="${sysResource.status==-1 }">checked</c:if>> 停用
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="status" id="inlineRadio3" value="0" <c:if test="${sysResource.status==0 }">checked</c:if>> 未审核
                        </label>
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:green;">
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
	    
	    $('.selected').chosen({
	        no_results_text: '没有找到',
	        allow_single_deselect: true,    //是否允许取消选择
	        max_selected_options: 3
	    });
	});
	
</script>

</body>
</html>