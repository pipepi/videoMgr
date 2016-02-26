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

<div class="container-fluid"><div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">显示物流</h3>
            </div>
            <div class="panel-body ">
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="j-email">物流情况：</label>
                    <div class="col-sm-6">
                        订单号：${logistics.nu }<br />
                        信息：${logistics.message }<br />
                        快递公司：${logistics.com }<br />
                        物流更新时间：${logistics.updatetime }<br /><br />
         <c:forEach items="${logistics.data }" var="logisticsdata" varStatus="innerStatus">
	             开始时间：${logisticsdata.time }<br />
	             正文：${logisticsdata.context }<br />
	             完成时间：${logisticsdata.ftime }<br />
	     </c:forEach>
                    </div>
                    <div class="col-sm-4 control-label j-explain" style="text-align:left;font-style:italic;font-size:12px;color:red;">
                    </div>
                </div>
         
            </div>
        </div>
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