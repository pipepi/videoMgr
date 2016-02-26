<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/views/inc/header.jsp" %>
<c:set var="rootpath" value="${CONTEXT_PATH}/store"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <title>${title }</title>
    <%@include file="/WEB-INF/views/inc/_css.jsp" %>
	<%@ include file="/WEB-INF/views/inc/_scripts.jsp" %>
</head>
<body>
            <div class="table-option clearfix">
            	<div class="form-group pull-left"> 
	                <a class="btn btn-default pull-left" href="${rootpath }/create">新建</a>
	                <!-- <a class="btn btn-success pull-left" href="#">批量删除</a> -->
           		</div>
            	
                <div class="form-inline col-lg-offset-1">
                	<i>
                		<span>店铺名称：</span>
				        <select id="name" name="name" class="selected" style="width:150px;">
						    <option value="">请输入</option>
				        	<c:forEach items="${list }" var="store">
							    <option value="${store.name }">${store.name }</option>
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
                        <th data-sorttype="name">商铺名称</th>
                        <th data-sorttype="description">商铺描述</th>
                        <th data-sorttype="logoUrl">LOGO_URL</th>
                        <th data-sorttype="status">店铺状态</th>
                        <th data-sorttype="createTime">创建时间</th>
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
    	var name = $('#name').val().replace(/^\s*|\s*$/, "");
    	if(name != null && name != ''){
    		queryParam += ('&eqName='+encodeURIComponent(name));
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
                "sClass": "dropdown text-left",
            },
            {
            	"mData":"name",
                "bSortable": true,
                "sClass": "dropdown text-left",
            },
            {
            	"mData":"description",
                "bSortable": false,
                "sClass": "dropdown text-left",
            },
            {
            	"mData":"logoUrl",
            	"bSortable": true,
            	"sClass": "dropdown text-left",
            	"render": function(data){
            		return '<img src=\"' + data + '\" width=\"72px\" heigh=\"72px\" />';
                }
            },
            {
            	"mData":"status",
            	"bSortable": true,
            	"sClass": "dropdown text-left",
            	"render": function(data){
            		return data == 0 ? '<span style=\"color:red\">离线</span>' : (data == 1 ? '<span style=\"color:green\">在线</span>' 
            		: '异常');
                }
            },
            {
            	"mData":"createTime",
            	"bSortable": true,
            	"sClass": "dropdown text-left",
            	"render": function(data){
            		var date = new Date();
            		date.setTime(data);
            		return date.toLocaleString();
                }
            },
            {
            	"mData":"operation",
            	"bSortable": false,
            	"render": function(data, type, row){
            		var b = (row.status == 0);
            		var ret = '<div class="pull-left dropdown">'+
            		'<span class="pointer dropdown-toggle" data-toggle="dropdown">'+
            			'<span class="glyphicon glyphicon-cog"></span>'+
            		'</span>'+
            		'<ul class="dropdown-menu" role="menu">'+
            		    '<li><a href="${ctx}/product/unionStore?eqId=' + row.id + '">编辑店铺商品</a></li>'+
            			"<li><a onclick='if(confirm(\"确定要删除吗?\")) window.location=\"${rootpath}/delete?eqId="+row.id+"\"'>删除</a></li>" +  
            			(b ? '<li><a href="${rootpath}/online?eqId=' +row.id+ '">店铺上线</a></li>' : '<li><a href="${rootpath}/offline?eqId=' +row.id+ '">店铺下线</a></li>') +
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