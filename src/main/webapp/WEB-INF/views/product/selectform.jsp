<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/views/inc/header.jsp" %>
<c:set var="rootpath" value="${CONTEXT_PATH}/product"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${title }</title>
    <%@include file="/WEB-INF/views/inc/_css.jsp" %>
	<%@ include file="/WEB-INF/views/inc/_scripts.jsp" %>
</head>
<body>
<div class="container-fluid">
  <!-- TAB切换 -->
  <div role="tabpanel">
    <!-- Tab panes -->
    <form:form class="tab-content" commandName="storeProducts" method="post" action="${rootpath }/psave" modelAttribute="products">
      <div role="tabpanel" class="tab-pane active" id="tab1">
        <div class="panel-group panel-pms" id="accordion" role="tablist" aria-multiselectable="true">
          <div class="panel panel-default">
            <div class="panel-heading">
              <h4 class="panel-title"><input type="checkbox" id="checkbox1"> <label for="checkbox1">店铺下商品列表</label> <a class="pull-right" data-toggle="collapse" href="#collapse1"><i class="glyphicon glyphicon-chevron-up"></i></a></h4>
            </div>
            <span style="color: red;"><form:errors path="*" /></span>
            <div id="collapse1" class="panel-collapse collapse in">
            	<div class="panel-body form-horizontal">
		          <label class="col-sm-2 control-label control-left" for="j-select">商铺名：</label>
		          <div class="col-sm-6">
		          	<label><input type="hidden" name="storeId" value="${store.id }"/>${store.name }</label>
		          </div>
		          <div class="col-sm-4 control-label j-explain" style="text-align:left;">
		          </div>
		      	</div>
            
              	<div class="panel-body form-horizontal">
				<c:forEach items="${Products }" var="product" varStatus="status">
	                <div class="form-group">
	                  <label class="col-sm-2 control-label control-left resource-checkbox">
	                    <!-- 产品名  -->
	                  	<input type="checkbox" <c:if test="${productIds.contains(product.id) }">checked</c:if> name="productInfoList[${status.index }].id" value="${product.id }" /> ${product.name }
	                  </label>
	                </div>
				</c:forEach>
              	</div>
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
    </form:form><!-- /.tab-content -->
  </div><!-- /.tabpanel -->
</div>

<script>
  seajs.use(['bootstrap','$'],function(bootstrap, $) {

	$('.parentCheckBox').click(function(){
		var idx = $(this).attr('idx');
		if('checked'==(jQuery(this).find('input[type="checkbox"]').attr('checked'))){
			jQuery('.select-checkbox-'+idx).find('input[type="checkbox"]').prop('checked', true);
		}else{
			jQuery('.select-checkbox-'+idx).find('input[type="checkbox"]').prop('checked', false);
		}
	});
	
	$('.operation-checkbox').click(function(){
		if('checked'==(jQuery(this).find('input[type="checkbox"]').attr('checked'))){
			jQuery(this).closest('.form-group').find('.resource-checkbox').find('input[type="checkbox"]').prop('checked', true);
		}else{
			jQuery(this).closest('.form-group').find('.resource-checkbox').find('input[type="checkbox"]').prop('checked', false);
		}
	});
	
	  
    // 全选
    $('.panel-pms .panel-title input[type="checkbox"]').on('change', function(e){
      var $target = $(e.target);
      if($target.is(':checked')){
        $target.closest('.panel').find('.panel-body').find('input[type="checkbox"]').prop('checked', true);
      }else{
        $target.closest('.panel').find('.panel-body').find('input[type="checkbox"]').prop('checked', false);
      }
    });

  });
</script>
</body>
</html>