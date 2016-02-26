<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/views/inc/header.jsp" %>
<c:set var="rootpath" value="${CONTEXT_PATH}/privilege"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <title>${title }-资源管理</title>
    <%@ include file="/WEB-INF/views/inc/_css.jsp" %>
	<%@ include file="/WEB-INF/views/inc/_scripts.jsp" %>
</head>
<body>
            <div class="table-option clearfix">
                <div class="form-inline pull-left">
                	<i>
                		<span>角色名称：</span>
				        <select id="roleName" name="roleName" class="selected" style="width:150px;">
						    <option value="">请输入</option>
				        	<c:forEach items="${sysmgrRoles }" var="role">
							    <option value="${role.name }">${role.name }</option>
			                </c:forEach>
						</select>
                	</i>
			        <i>
				        <span>资源名称：</span>
				        <select id="resourceName" name="resourceName" class="selected" style="width:150px;">
						    <option value="">请输入</option>
						    <c:forEach items="${sysmgrResources }" var="resource">
							    <option value="${resource.name }">${resource.name }</option>
			                </c:forEach>
						</select>
					</i>
			        <button type="button" class="btn btn-default" id="j-searchbtn"><i class="glyphicon glyphicon-search">搜索</i></button>
			    </div>
            </div>

            <div class="table-responsive">
                <table class="table table-hover table-striped table-bordered" id="j-table">
                    <thead>
                    <tr>
                    	<th data-sorttype="checkBox"><input type="checkbox" id="checkAll" name="cId"></th>
                        <th data-sorttype="id">ID</th>
                        <th data-sorttype="roleName">角色名称</th>
                        <th data-sorttype="resourceName">资源名称</th>
                        <th data-sorttype="operationName">操作名称</th>
                        <th data-sorttype="operation">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    
                    </tbody>
                </table>
            </div>
	
<!--页面业务-->
<script type="text/javascript">

seajs.use(['bootstrap','$','underscore'],function(bootstrap,$,_) {
   
	$('.selected').chosen({
        no_results_text: '没有找到',
        allow_single_deselect: true,    //是否允许取消选择
        max_selected_options: 3
    });
    
    function searchListPage() {
    	var queryParam = '';
    	var roleName = $('#roleName').val().replace(/^\s*|\s*$/, "");
    	if(roleName!=null && roleName!=''){
    		queryParam += ('&eqRoleName='+encodeURIComponent(roleName));
    	}
    	var resourceName = $('#resourceName').val().replace(/^\s*|\s*$/, "");
    	if(resourceName!=null && resourceName!=''){
    		queryParam += ('&eqResourceName='+encodeURIComponent(resourceName));
    	}
    	var url = '${rootpath}/search?1=1' + queryParam;
    	tabl.fnReloadAjax(url);
    }

    //输入条件搜索
    $('#j-searchbtn').click(function(){
        searchListPage();
    });
    
    //表格
    var tabl = $("#j-table").dataTable({
    	"oLanguage": {//语言国际化
            "sUrl": "/source/zh_cn.txt"
        },
        "sPaginationType": "full_numbers",	//分页风格(two_button/full_numbers)
        "sDom": 'rt<"clearfix datatablefoot"<"pull-left"l><"pull-left"i><"pull-right"p>>',	//这是用于定义DataTable布局的一个强大的属性
        "bSortClasses": false,	//classes样式
        "bProcessing": false,	//正在处理
        "bServerSide": true,	//延迟加载
        "sServerMethod": "POST",	//请求方式
        "aLengthMenu": [[10, 20, 30, 50], [10, 20, 30, 50]],	//这个为选择每页的条目数
        "sAjaxSource": '${rootpath}/search',	//URL获取数据
        "aaSorting" : [["1", "asc"]],
        "aoColumns":[	//参数用来定义表格的列，这是一个数组
            {
            	"mData":"checkBox",
                "bSortable": false,
                "render": function(){
                	return '<input type="checkbox" id="checkAll" name="cId">';
                }
            },
            {
            	"mData":"id",
                "bSortable": true,
                "sClass":"dropdown text-left"
            },
            {
            	"mData":"roleName",
                "bSortable": true,
                "sClass":"dropdown text-left"
            },
            {
            	"mData":"resourceName",
                "bSortable": true,
                "sClass":"dropdown text-left"
            },
            {
            	"mData":"operationName",
                "bSortable": true,
                "sClass":"dropdown text-left"
            },
            {
            	"mData":"operation",
            	"bSortable": false,
            	"render": function(data, type, row){
            		var b = (row.status==0);
            		var ret = '<div class="pull-left dropdown">'+
            		'<span class="pointer dropdown-toggle" data-toggle="dropdown">'+
            			'<span class="glyphicon glyphicon-cog"></span>'+
            		'</span>'+
            		'<ul class="dropdown-menu" role="menu">'+
            			"<li><a onclick='if(confirm(\"确定删除?\")) window.location=\"${rootpath}/delete?eqId="+row.id+"\"'>删除</a></li>"+
            		'</ul>'+
            		'</div>';
                    return ret;
                }
            }
        ]
    });
});
</script>
</body>
</html>