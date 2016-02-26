<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<c:set var="rootpath" value="${CONTEXT_PATH}/product"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<%@include file="/WEB-INF/views/inc/_css.jsp" %>
	<%@ include file="/WEB-INF/views/inc/_scripts.jsp" %>
</head>
<body>

<div class="container-fluid">
    <form:form class="form-horizontal" commandName="product" modelAttribute="product" data-widget="validator" data-item-class="form-group" data-item-error-class="has-error" data-explain-class="j-explain" role="form" action="${rootpath}/${product.id > 0 ? 'update' : 'save' }">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">编辑</h3>
            </div>
            <div class="panel-body ">
            	<span style="color: red;"><form:errors path="*" /></span>
            	<input type="hidden" name="eqId" value="${product.id }" />
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">产品名称</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-email" value="${product.name }" name="name" required type="text" placeholder="产品名称">
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">图片路径1</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-email" value="${product.photoUrl1 }" name="photoUrl1" type="text" placeholder="图片路径">
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">图片路径2</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-email" value="${product.photoUrl2 }" name="photoUrl2" type="text" placeholder="图片路径">
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">图片路径3</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-email" value="${product.photoUrl3 }" name="photoUrl3" type="text" placeholder="图片路径">
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">图片路径4</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-email" value="${product.photoUrl4 }" name="photoUrl4" type="text" placeholder="图片路径">
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">图片路径5</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-email" value="${product.photoUrl5 }" name="photoUrl5" type="text" placeholder="图片路径">
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-select">产品类型</label>
                    <div class="col-sm-6">
                        <select class="form-control " id="j-select" required data-rule="digits"  placeholder="下拉" name="type">
                        	<c:forEach items="${productTypeList }" var="productType">
	                            <option value="${productType.id }" <c:if test="${product.type == productType.id }">selected</c:if>>${productType.name }</option>
                        	</c:forEach>
                        </select>
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">产品价格（单位：元）</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-email" value="${product.price }" name="price" type="number" onKeyUp="this.value=this.value.replace(/[^\.\d]/g,'');"  placeholder="产品价格">
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">产品质量（单位：KG）</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-email" value="${product.weight }" name="weight" type="number" placeholder="产品质量">
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">产品库存（正整数）</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-email" value="${product.reserve }" name="reserve" type="text" onKeyUp="this.value=this.value.replace(/[^\.\d]/g,'');" placeholder="产品库存">
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" >状态</label>
                    <div class="col-sm-6">
                        <label class="radio-inline">
                            <input type="radio" name="status" id="inlineRadio1" value="1" <c:if test="${product.status==1 }">checked</c:if>> 可用
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="status" id="inlineRadio2" value="-1" <c:if test="${product.status==-1 }">checked</c:if>> 停用
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="status" id="inlineRadio3" value="0" <c:if test="${product.status==0 }">checked</c:if>> 不可用
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