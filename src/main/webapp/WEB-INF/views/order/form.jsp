<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/views/inc/header.jsp" %>
<%@ include file="/WEB-INF/views/inc/_css.jsp" %>
<%@ include file="/WEB-INF/views/inc/_scripts.jsp" %>
<c:set var="rootpath" value="${CONTEXT_PATH}/order"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

</head>
<body>

<div class="container-fluid">
    <form:form class="form-horizontal" commandName="productOrder" modelAttribute="productOrder" data-widget="validator" data-item-class="form-group" data-item-error-class="has-error" data-explain-class="j-explain" role="form" action="${rootpath}/${productOrder.id > 0 ? 'update' : 'save' }">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">编辑</h3>
            </div>
            <div class="panel-body ">
            	<span style="color: red;"><form:errors path="*" /></span>
            	<input type="hidden" name="id" value="${productOrder.id }" />
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">渠道ID</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-email" value="${productOrder.channelId }" name="channelId" required type="text" placeholder="渠道ID" readonly="true">
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">订单ID</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-email" value="${productOrder.orderId }" name="orderId" type="text" placeholder="订单ID" readonly="true">
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">买家名</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-email" value="${productOrder.buyersName }" name="buyersName" type="text" placeholder="买家名" readonly="true"> 
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">卖家名</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-email" value="${productOrder.sellerName }" name="sellerName" type="text" placeholder="卖家名" readonly="true">
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">价格(单位：元)</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-email" value="${productOrder.price }" name="price" onKeyUp="this.value=this.value.replace(/[^\.\d]/g,'');"  type="text" placeholder="价格" <c:if test="${read}">readonly=\"true\"</c:if> />
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">收货地址</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-email" value="${productOrder.toAddress }" name="toAddress" type="text" placeholder="收货地址" readonly="true">
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">收货手机号</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-email" value="${productOrder.toMobile }" name="toMobile" type="text" placeholder="收货手机号" readonly="true">
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">收货状态</label>
                    <div class="col-sm-6">
                        <input class="form-control " id="j-email" value="${deliverStatus[productOrder.deliverStatus] }" name="deliverStatus" type="text" placeholder="收货状态" readonly="true">
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" >状态</label>
                    <div class="col-sm-6">
                        <label class="radio-inline">
                            <c:if test="${productOrder.orderStatus == 0 }"><input type="radio" name="status" id="inlineRadio1" value="0" checked > 未付款</c:if>
                            <c:if test="${productOrder.orderStatus == 1 }"><input type="radio" name="status" id="inlineRadio2" value="1" checked > 已付款</c:if>
                            <c:if test="${productOrder.orderStatus == 2 }"><input type="radio" name="status" id="inlineRadio3" value="2" checked > 完成</c:if>
                            <c:if test="${productOrder.orderStatus == 3 }"><input type="radio" name="status" id="inlineRadio4" value="3" checked > 关闭</c:if>
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