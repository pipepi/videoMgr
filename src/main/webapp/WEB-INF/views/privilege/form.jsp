<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/views/inc/header.jsp" %>
<c:set var="rootpath" value="${CONTEXT_PATH}/privilege"/>
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
    <form:form class="tab-content" commandName="privileges" method="post" action="${rootpath }/save" modelAttribute="privileges">
      <input type="hidden" name="eqRoleId" value="${roleId }"/>
      <input type="hidden" name="eqRoleName" value="${roleName }"/>
      <input type="hidden" name="operateType" value="${operateType }"/>
      <div role="tabpanel" class="tab-pane active" id="tab1">
        <div class="panel-group panel-pms" id="accordion" role="tablist" aria-multiselectable="true">
          <div class="panel panel-default">
            <div class="panel-heading">
              <h4 class="panel-title"><input type="checkbox" id="checkbox1"> <label for="checkbox1">资源</label> <a class="pull-right" data-toggle="collapse" href="#collapse1"><i class="glyphicon glyphicon-chevron-up"></i></a></h4>
            </div>
            <span style="color: red;"><form:errors path="*" /></span>
            <div id="collapse1" class="panel-collapse collapse in">
            	<div class="panel-body form-horizontal">
		          <label class="col-sm-2 control-label control-left" for="j-select">角色</label>
		          <div class="col-sm-6">
		          	<label><input type="hidden" name="roleId" value="${roleId }"/>${roleName }</label>
		          </div>
		          <div class="col-sm-4 control-label j-explain" style="text-align:left;">
		          </div>
		      	</div>
            
              	<div class="panel-body form-horizontal">
				<c:forEach items="${sysmgrResources }" var="resource" varStatus="status">
	                <div class="form-group">
	                  <label class="col-sm-2 control-label control-left resource-checkbox">
	                  	<input type="checkbox" <c:if test="${resourceIds.contains(resource.id) }">checked</c:if> name="privilegeList[${status.index }].resourceId" value="${resource.id }"> ${resource.name }
	                  </label>
	                  <div class="col-sm-6">
	                    <div class="control-item">
	                      <h4></h4>
	                      <label class="operation-checkbox parentCheckBox" idx="${status.index }"><input type="checkbox"> 全选</label>
	                      <c:forEach items="${sysmgrOperations }" var="operation" varStatus="innerStatus">
	                      	  <c:set var="resOper" value="${resource.id }${operation.id }" />
		                      <label class="operation-checkbox select-checkbox-${status.index }">
		                      	<input type="checkbox" <c:if test="${operations.contains(resOper) }">checked</c:if> name="privilegeList[${status.index }].operationIds[${innerStatus.index}]" value="${operation.id }" /> ${operation.name } 
		                      </label>
	                      </c:forEach>
	                    </div>
	                  </div>
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