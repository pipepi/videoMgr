<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<c:set var="rootpath" value="${CONTEXT_PATH}/packageRule"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<%@include file="/WEB-INF/views/inc/_css.jsp" %>
	<%@ include file="/WEB-INF/views/inc/_scripts.jsp" %>
</head>
<body>

<div class="container-fluid">
    <form:form class="form-horizontal" commandName="packageRule" modelAttribute="packageRule" data-widget="validator" data-item-class="form-group" data-item-error-class="has-error" data-explain-class="j-explain" role="form" action="${rootpath}/${packageRule.id > 0 ? 'update' : 'save' }">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">编辑</h3>
            </div>
            <div class="panel-body ">
            	<span style="color: red;"><form:errors path="*" /></span>
            	<input type="hidden" name="eqId" value="${packageRule.id }" />
            	<input type="hidden" name="packageId" value="${ruleId != null ? ruleId : packageRule.packageId }" />
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">套餐规则名</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-email" value="${packageRule.name }" name="name" required type="text" placeholder="产品名称">
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">规则类型</label>
                    <div class="col-sm-6">
                        <select class="form-control " id="j-select" required data-rule="digits"  placeholder="下拉" name="type">
                            <option value="0" <c:if test="${packageRule.type == 0 }">selected</c:if>>无限制</option>
                            <option value="1" <c:if test="${packageRule.type == 1 }">selected</c:if>>次数限制</option>
                        </select>
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">产品类型</label>
                    <div class="col-sm-6">
                        <select class="form-control " id="j-select" required data-rule="digits"  placeholder="下拉" name="productType">
                            <option value="0" <c:if test="${packageRule.productType == 0 }">selected</c:if>>视频电商</option>
                            <option value="1" <c:if test="${packageRule.productType == 1 }">selected</c:if>>视频</option>
                            <option value="2" <c:if test="${packageRule.productType == 2 }">selected</c:if>>产品</option>
                            <option value="3" <c:if test="${packageRule.productType == 3 }">selected</c:if>>其他</option>
                        </select>
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">限制次数</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-email" value="${packageRule.time > 0 ? packageRule.time : '0'  }" name="times" onKeyUp="this.value=this.value.replace(/[^\.\d]/g,'');"  type="text" placeholder="限制次数"  />
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
	    
	    $('.selected').chosen({
	        no_results_text: '没有找到',
	        allow_single_deselect: true,    //是否允许取消选择
	        max_selected_options: 3
	    });
	});
	
</script>

</body>
</html>