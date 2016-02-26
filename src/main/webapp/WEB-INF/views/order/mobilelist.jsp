<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/views/inc/header.jsp" %>
<c:set var="rootpath" value="${CONTEXT_PATH}/order"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <title>${title }</title>
    <%@include file="/WEB-INF/views/inc/_css.jsp" %>
	<%@ include file="/WEB-INF/views/inc/_scripts.jsp" %>
<script type="text/javascript" src="${JS_PATH}/libs/jquery.md5.js"></script>
<script type="text/javascript">
jQuery(document).ready(function($) {
   $(".close-icon").click(function(){
   $(".commodity-kuang").hide();
  })
});

jQuery(document).ready(function($) {
   $(".hq-yzm").click(function(){
   
   var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
   if(!myreg.test($(".sms-mobile").val())) 
   { 
    alert('请输入有效的手机号码！'); 
    return false; 
   } 
   
   var myDate = new Date();
   var t = myDate.getTime();
   $.post("${CONTEXT_PATH}/sms/sendcode", { phone : $(".sms-mobile").val(), time : t, 
         key: $.md5($(".sms-mobile").val() + t + 'azt123qet')},
     function(data){
        var ret = jQuery.parseJSON(data);
        if(ret.code == 0){
          alert("短信已发送成功！"); 
        }
   });
   
   $(".hq-yzm").hide();
   //$(".hq-yzm1").show();
   
  })
});

jQuery(document).ready(function($) {
   $(".houtai-loginUser").click(function(){
    $(".commodity-kuang").show();
  
  })
});

jQuery(document).ready(function($) {
   $(".houtai-chaxun").click(function(){
    var myDate = new Date();
    var t = myDate.getTime();
    $.post("${CONTEXT_PATH}/sms/checkcode", { phone : $(".sms-mobile").val(), content : $(".check-mobile").val(), 
         time : t, key: $.md5($(".sms-mobile").val() + t + 'azt123qet')},
        function(data){
	        //alert("Data check: " + data);
	        var ret = jQuery.parseJSON(data);
	        //alert("check ret" + ret.code);
	        if(ret.code == 0){
	           $.post( "${CONTEXT_PATH}/order/mobilelogin", { mobile: $(".sms-mobile").val()}).done(function(data1) {
               //alert( "Data logined: " + data1);
               var ret1 = jQuery.parseJSON(data1);
                  if(ret1.code == 0){
					alert("登录成功！");
				    $(".commodity-kuang").hide();
                  }else{
				    alert("该号码未注册无法登录！");
				  }
               });
	        }else{
			   alert("验证码有误！");
			} 
    });
   
  })
});
</script>
</head>
<body>
<div class="container-fluid">

 <!--登录弹出框-->
  <div class="commodity-kuang video-kuang" style="display:none; z-index:10000;border:2px solid #333333;width:510px;height:190px; position:absolute;background:#ffffff; top:20%;left:25%;">
  <a href="javascript:void(0)" class="close-icon" style="background:url(../source/images/aztimg/close_icon.png) no-repeat;height:33px;width:33px; display:block; float:right;"></a>
           <table cellpadding="3" cellspacing="0" border="0" class="houtai-loginKuang" style="padding:20px; margin-left:60px; margin-top:20px;">
           	<tr>
                <td align="right"><span>*</span>联系电话：</td>
                <td><input class="sms-mobile" type="text" /></td>
                <td>
                <a href="javascript:void(0)" class="hq-yzm" style="background-color:#4b92f2;color:#ffffff;width:89px;height:28px; text-align:center;line-height:28px; display:block;">获取验证码</a>
                <a href="javascript:void(0)" class="hq-yzm1" style="background-color:#4b92f2;color:#ffffff;width:89px;height:28px; text-align:center;line-height:28px; display:none;">60s</a>
                </td>  
             </tr>
             <tr>
               <td align="right"><span>*</span>验证码：</td>
               <td><input class="check-mobile" type="text"  placeholder="请输入6位数验证码" /></td>
               <td>&nbsp;</td>
              </tr>
              <tr>
               <td colspan="3"><a href="javascript:void(0)" class="houtai-chaxun" style="background-color:#f2784b;color:#ffffff;width:105px;height:28px;line-height:28px;text-align:center; display:block; margin:0 auto; margin-top:20px;">查询</a></td>
              </tr>
           </table>   
    </div>
            <div class="table-option clearfix">
            	<div class="form-group pull-left"> 
	                <!-- <a class="btn btn-default pull-left" href="${rootpath }/create">新建</a>
	                <a class="btn btn-success pull-left" href="#">批量删除</a> -->
           		</div>
            	
                <div class="form-inline col-lg-offset-1">
                    <i>
                		<span>订单号：</span>
                		<input id="orderId" name="orderId" style="width:150px" />
                	</i>
                	<i>
                		<span>买家名：</span>
                		<input id="buyer" name="buyer" style="width:150px" />
                	</i>
			        <i>
				        <span>卖家名：</span>
				        <input id="seller" name="seller" style="width:150px" />
					</i>
					<i>
				        <span>交易管理：</span>
				        <select id="stype" name="stype" class="selected" style="width:150px;">
						    <option value="">请输入</option>
							<option value="0">买家未付款订单</option>
							<option value="1">未发货订单</option>
							<option value="2">待买家收货订单</option>
							<option value="3">交易成功订单</option>
							<option value="4">已关闭/取消订单</option>
							<option value="5">退款中订单</option>
						</select>
					</i>
			        <button type="button" class="btn btn-default" id="j-searchbtn"><i class="glyphicon glyphicon-search">搜索</i></button>
			        <i>
			        <a class="houtai-loginUser" href="javascript:void(0)" style="color:black;padding-left:150px">登录</a>
			        </i>
			        
			    </div>
            </div>

            <div class="table-responsive">
                <table class="table table-hover table-striped table-bordered" id="j-table">
                    <thead>
                    <tr>
                    	<th data-sorttype="checkBox"><input type="checkbox" id="checkAll" name="cId"></th>
                        <th data-sorttype="id">ID</th>
                        <th data-sorttype="channelId">渠道ID</th>
                        <th data-sorttype="buyersName">买家名</th>
                        <th data-sorttype="sellerName">卖家名</th>
                        <th data-sorttype="productNames">商品名</th>
                        <th data-sorttype="price">价格</th>
                        <th data-sorttype="toAddress">收货地址</th>
                        <th data-sorttype="toMobile">收货手机号</th>
                        <th data-sorttype="orderStatus">订单状态</th>
                        <th data-sorttype="deliverStatus">送货状态</th>
                        <th data-sorttype="createTime">创建时间</th>
                        <th data-sorttype="cannelTime">撤单时间</th>
                        <th data-sorttype="operation">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    
                    </tbody>
                </table>
            </div>
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
    	var orderId = $('#orderId').val().replace(/^\s*|\s*$/, "");
    	if(orderId != null && orderId != ''){
    		queryParam += ('&orderId=' + encodeURIComponent(orderId));
    	}
    	var buyer = $('#buyer').val().replace(/^\s*|\s*$/, "");
    	if(buyer != null && buyer != ''){
    		queryParam += ('&buyer=' + encodeURIComponent(buyer));
    	}
    	var seller = $('#seller').val().replace(/^\s*|\s*$/, "");
    	if(seller != null && seller != ''){
    		queryParam += ('&seller=' + encodeURIComponent(seller));
    	}
    	var stype = $('#stype').val().replace(/^\s*|\s*$/, "");
    	if(stype != null && stype != ''){
    		queryParam += ('&stype=' + encodeURIComponent(stype));
    	}
    	var url = '${rootpath}/mobilesearch?1=1' + queryParam;
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
        "sAjaxSource": '${rootpath}/mobilesearch',	//URL获取数据
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
            	"mData":"channelId",
                "bSortable": false,
                "sClass":"dropdown text-left"
            },
            {
            	"mData":"buyersName",
                "bSortable": true,
                "sClass":"dropdown text-left"
            },
            {
            	"mData":"sellerName",
                "bSortable": true,
                "sClass":"dropdown text-left"
            },
            {
            	"mData":"productNames",
                "bSortable": true,
                "sClass":"dropdown text-left"
            },
            {
            	"mData":"price",
                "bSortable": true,
                "sClass":"dropdown text-left",
                "render": function(data){
                	return data + "元";
                }
            },
            {
            	"mData":"toAddress",
                "bSortable": true,
                "sClass":"dropdown text-left"
            },
            {
            	"mData":"toMobile",
                "bSortable": true,
                "sClass":"dropdown text-left"
            },
            {
            	"mData":"orderStatus",
                "bSortable": true,
                "sClass":"dropdown text-left",
                "render": function(data){
                    if(data == 0){
                       return '<span style=\"color:red\">未支付</span>';
                    }else if(data == 1){
                       return '<span style=\"color:green\">已支付</span>';
                    }else if(data == 2){
                       return '<span style=\"color:black\">完成</span>';
                    }else if(data == 3){
                       return '<span style=\"color:black\">关闭</span>';
                    }
                }
            },
            {
            	"mData":"deliverStatus",
                "bSortable": true,
                "sClass":"dropdown text-left",
                "render": function(data){
                    if(data == 0){
                       return '<span style=\"color:red\">未送货</span>';
                    }else if(data == 1){
                       return '已送货未到达';
                    }else if(data == 2){
                       return '<span style=\"color:green\">已收货</span>';
                    }else if(data == 3){
                       return '退货';
                    }else if(data == 4){
                       return '延期';
                    }
                }
            },
            {
            	"mData":"createTime",
            	"bSortable": true,
            	"sClass":"dropdown text-left",
            	"render": function(data){
            		var date = new Date();
            		date.setTime(data);
            		return date.toLocaleString();
                }
            },
            {
            	"mData":"cannelTime",
            	"bSortable": true,
            	"sClass":"dropdown text-left",
            	"render": function(data){
            	    if(data != null){
	            	    var date = new Date();
	            		date.setTime(data);
	            		return date.toLocaleString();
            	    }else{
            	        return '';
            	    }
                }
            },
            {
            	"mData":"operation",
            	"bSortable": false,
            	"render": function(data, type, row){
            		var b = (row.orderStatus == 0);
            		var ret = '<div class="pull-left dropdown">'+
            		'<span class="pointer dropdown-toggle" data-toggle="dropdown">'+
            			'<span class="glyphicon glyphicon-cog"></span>'+
            		'</span>'+
            		'<ul class="dropdown-menu" role="menu">'+
            			(row.orderStatus == 0 ? '<li><a href="${rootpath}/pay?eqId='+row.id+'">付款</a></li>' : '') +
            			((row.orderStatus == 1)? '<li><a href="${rootpath}/showlogistics?id='+row.id+'">查看物流</a></li>' : '') +
            			(row.orderStatus == 1 ? '<li><a onclick=\'if(confirm(\"确定申请退款?\")) window.location=\"${rootpath}/refund?eqId=" + row.id + "\"\'>申请退款</a></li>' : '') +
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