<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="<%=request.getContextPath() %>"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>智能商家管理系统</title>
<script type = "text/javascript" charset="utf-8" src="${ctx}/source/js/libs/jquery/jquery/jquery.js"></script>
<style type="text/css">
	table{border-collapse:collapse; margin-top: 5px;} 
	td {border: #C0C0C0 1px solid;padding-left: 30px;}
	.title_1{margin-top: 10px;color: orange;}
	.title_2{margin-left:20px;margin-top: 10px;color: green;}
	.btn{
		margin-left:-30px;
		background: transparent url("${ctx}/source/images/icons.jpg") no-repeat -220px  -223px  ;
		width: 35px;
		height: 35px;
		cursor: pointer;
	}
	.folder{
		width: 35px;
		height: 35px;
		cursor: pointer;
	}
	.open{
		background: transparent url("${ctx}/source/images/icons.jpg") no-repeat -115px  -113px  ;
	}
	.close{
		background: transparent url("${ctx}/source/images/icons.jpg") no-repeat -225px  -113px  ;
	}
	.commit{
		margin-left:2px;
		width: 35px;
		height: 35px;
		background: transparent url("${ctx}/source/images/icons.jpg") no-repeat -490px  -3px  ;
		cursor: pointer;
	}
	.cancel{
		margin-left:2px;
		width: 35px;
		height: 35px;
		background: transparent url("${ctx}/source/images/icons.jpg") no-repeat -600px  -3px  ;
		cursor: pointer;
	}
	.float{
		float: left;	
	}
	.edit{
		padding:2px;
		position: fixed;
		top: 200px;
		left: 300px;
		background-color: gray;
	}
	.edit input{
		height: 30px;
		width: 733px;
	}
	.display_none{
		display: none;
	}
</style>
<script type="text/javascript">
    var _ctx = "${ctx}";    
	var _conf_id = 0;
	var _conf_name = "";
	var _attr_name = "";
	var _attr_value = "";
	var _old_value;
	$(function(){
		toggle();
		$(".folder").click();
	});
	function init(_id){
		var _url = _ctx+"/config/initby"; 
		$.post(_url,{id:_id},function(data){
			window.location.reload();
		});
	}
	function toggle(){
		$(".folder").click(function(){
			$(this).parent().find("table").toggle();
			if($(this).is(".close")){
				$(this).removeClass("close");
				$(this).addClass("open");
			}else{
				$(this).removeClass("open");
				$(this).addClass("close");
			}
		});
		$(".btn").click(function (e){
			var _table = $(this).parent().parent().parent().parent();
			_conf_id = _table.attr("confid");
			_conf_name = _table.attr("confname");
			_attr_name = $(this).attr("attrname");
			_old_value = $(this).parent().prev();
			_attr_value = _old_value.html();
			var _mouse_y = e.pageY;
			$(".edit").find("input").val(_attr_value);
			//console.log(_mouse_y);
			if(_mouse_y>450){
				_mouse_y = 450;
			}
			$(".edit").css("top",_mouse_y+"px");
			$(".edit").css("left","150px");
			$(".edit").toggle();
			$(".edit").find("input").focus();
		});
		$(".edit").find("input").keydown(function(e){
			var e = e||event;
			var keycode = e.which||e.keyCode;
			if(keycode==13){
				$(".commit").click();
			}else if(keycode==27){
				$(".cancel").click();
			}
			
		});
		$(".cancel").click(function (){
			$(".edit").toggle();
		});
		$(".commit").click(function(){
			_attr_value = $(this).prev().find("input").val();
			var _url = _ctx+"/config/update";
			$.post(_url,{confid:_conf_id,
					confname:_conf_name,
					attrname:_attr_name,
					attrvalue:_attr_value
						},function(data){
				var attr = eval('('+data+')');
				if(attr.success==0){
					$(".edit").toggle();
					_old_value.html(_attr_value);
				}else{
					alert("修改失败");
				}
			});
		});
		
	}
</script>
</head>
<body>
<div class="edit display_none">
		<div class="float"><input type="text"></input></div>
		<div class="float commit"></div>
		<div class="float cancel"></div>
		<div style="clear:both"></div>
</div>
<div class="title_1" style="color: gray;">enter:提交编辑;esc:取消编辑</div>
<div class="title_1">视频图片文件</div>
  <div>
  	<div class="float title_2">存储平台配置[1：微软云；2：金山云；]
  	<c:if test="${conf.storageConfig==null }"><a href="javascript:init(12);">初始化</a></c:if>
  	</div><div class="float folder close"></div><div style="clear:both"></div>
  	<table confid="12" confname="StorageConfig">
  		<tr><td>平台id</td><td>${conf.storageConfig.storagePlatform }</td><td><div class="btn" attrname="storagePlatform"></div></td></tr>
  	</table>
  </div>
  <div >
    <div class="float title_2">微软云</div><div class="float folder close"></div><div style="clear:both"></div><!-- clear float -->
  	<table confid="8" confname="AzureConfig">
  		<tr><td>storageAccount</td><td>${conf.azureConfig.storageAccount }</td><td><div class="btn" attrname="storageAccount"></div></td></tr>
  		<tr><td>storeKey</td><td>${conf.azureConfig.storeKey }</td><td><div class="btn" attrname="storeKey"></div></td></tr>
  	    <tr><td>blobEndpoint</td><td>${conf.azureConfig.blobEndpoint }</td><td><div class="btn" attrname="blobEndpoint"></div></td></tr>
  	    <tr><td>queueEndpoint</td><td>${conf.azureConfig.queueEndpoint }</td><td><div class="btn" attrname="queueEndpoint"></div></td></tr>
  		<tr><td>tableEndpoint</td><td>${conf.azureConfig.tableEndpoint }</td><td><div class="btn" attrname="tableEndpoint"></div></td></tr>
  		<tr><td>mediaAccount</td><td>${conf.azureConfig.mediaAccount }</td><td><div class="btn" attrname="mediaAccount"></div></td></tr>
  		<tr><td>mediaKey</td><td>${conf.azureConfig.mediaKey }</td><td><div class="btn" attrname="mediaKey"></div></td></tr>
  	</table>
  </div>
  <div >
    <div class="float title_2">金山云</div><div class="float folder close"></div><div style="clear:both"></div><!-- clear float -->
  	<table confid="11" confname="Ks3Config">
  		<tr><td>accesskeyid</td><td>${conf.ks3Config.accesskeyid }</td><td><div class="btn" attrname="accesskeyid"></div></td></tr>
  		<tr><td>accesskeysecret</td><td>${conf.ks3Config.accesskeysecret }</td><td><div class="btn" attrname="accesskeysecret"></div></td></tr>
  	    <tr><td>notifyUrl</td><td>${conf.ks3Config.notifyUrl }</td><td><div class="btn" attrname="notifyUrl"></div></td></tr>
  		<tr><td>videoBucketName</td><td>${conf.ks3Config.videoBucketName }</td><td><div class="btn" attrname="videoBucketName"></div></td></tr>
  		<tr><td>picBucketName</td><td>${conf.ks3Config.picBucketName }</td><td><div class="btn" attrname="picBucketName"></div></td></tr>
  	</table>
  </div>
  <div>
  	<div class="float title_2">本地临时文件</div><div class="float folder close"></div><div style="clear:both"></div><!-- clear float -->
  	<table confid="1" confname="FileConfig">
  		<tr><td>文件临时目录</td><td>${conf.fileConfig.FILE_LOCAL_DIR }</td><td><div class="btn" attrname="FILE_LOCAL_DIR"></div></td></tr>
  		<tr><td>图片微软云目录</td><td>${conf.fileConfig.IMG_AZURE_DIR }</td><td><div class="btn" attrname="IMG_AZURE_DIR"></div></td></tr>
  		<tr><td>支付图片微软云目录</td><td>${conf.fileConfig.IMG_AZURE_PAY_DIR }</td><td><div class="btn" attrname="IMG_AZURE_PAY_DIR"></div></td></tr>
  		<tr><td>图片微软云地址前缀</td><td>${conf.fileConfig.IMG_AZURE_PRE }</td><td><div class="btn" attrname="IMG_AZURE_PRE"></div></td></tr>
  		<tr><td>图片裁剪插件地址</td><td>${conf.fileConfig.IM4JAVA_TOOLPATH }</td><td><div class="btn" attrname="IM4JAVA_TOOLPATH"></div></td></tr>
  		<tr><td>PAY_NOTIFY_URL</td><td>${conf.fileConfig.PAY_NOTIFY_URL }</td><td><div class="btn" attrname="PAY_NOTIFY_URL"></div></td></tr>
  		<tr><td>MAX_VIDEO_SIZE</td><td>${conf.fileConfig.MAX_VIDEO_SIZE }</td><td><div class="btn" attrname="MAX_VIDEO_SIZE"></div></td></tr>
  		<tr><td>MAX_IMGS_SIZE</td><td>${conf.fileConfig.MAX_IMGS_SIZE }</td><td><div class="btn" attrname="MAX_IMGS_SIZE"></div></td></tr>
  		<tr><td>MAX_IMG_WIDTH</td><td>${conf.fileConfig.MAX_IMG_WIDTH }</td><td><div class="btn" attrname="MAX_IMG_WIDTH"></div></td></tr>
  		<tr><td>MIN_IMG_WIDTH</td><td>${conf.fileConfig.MIN_IMG_WIDTH }</td><td><div class="btn" attrname="MIN_IMG_WIDTH"></div></td></tr>
  	</table>
  </div>
<div class="title_1">传统电商</div>
<div>
  	<div class="float title_2">c#</div><div class="float folder close"></div><div style="clear:both"></div><!-- clear float -->
  	<table confid="4" confname="PartnerConfig">
  		<tr><td>根目录</td><td>${conf.partnerConfig.ROOT_PATH_KAIMAI8 }</td><td><div class="btn" attrname="ROOT_PATH_KAIMAI8"></div></td></tr>
  		<tr><td>PARTNER_INDEX_URL</td><td>${conf.partnerConfig.PARTNER_INDEX_URL }</td><td><div class="btn" attrname="PARTNER_INDEX_URL"></div></td></tr>
  		<tr><td>GET_PARTNER_PRODUCT_PAGE_URL</td><td>${conf.partnerConfig.GET_PARTNER_PRODUCT_PAGE_URL }</td><td><div class="btn" attrname="GET_PARTNER_PRODUCT_PAGE_URL"></div></td></tr>
  		<tr><td>GET_PARTNER_PRODUCT_DETAIL_URL</td><td>${conf.partnerConfig.GET_PARTNER_PRODUCT_DETAIL_URL }</td><td><div class="btn" attrname="GET_PARTNER_PRODUCT_DETAIL_URL"></div></td></tr>
  		<tr><td>GET_PARTNER_PRODUCT_SKUSINFO</td><td>${conf.partnerConfig.GET_PARTNER_PRODUCT_SKUSINFO }</td><td><div class="btn" attrname="GET_PARTNER_PRODUCT_SKUSINFO"></div></td></tr>
  		<tr><td>UPDATE_PRODUCT_STOKE</td><td>${conf.partnerConfig.UPDATE_PRODUCT_STOKE }</td><td><div class="btn" attrname="UPDATE_PRODUCT_STOKE"></div></td></tr>
  	</table>
</div>
<div class="title_1">邮件短信</div>
<div>
  	<div class="float title_2">邮件</div><div class="float folder close"></div><div style="clear:both"></div><!-- clear float -->
  	<table confid="3" confname="EmailConfig">
  		<tr><td>url</td><td>${conf.emailConfig.url }</td><td><div class="btn" attrname="url"></div></td></tr>
  		<tr><td>apiKey</td><td>${conf.emailConfig.apiKey }</td><td><div class="btn" attrname="apiKey"></div></td></tr>
  		<tr><td>apiUser</td><td>${conf.emailConfig.apiUser }</td><td><div class="btn" attrname="apiUser"></div></td></tr>
  		<tr><td>apiBatchUser</td><td>${conf.emailConfig.apiBatchUser }</td><td><div class="btn" attrname="apiBatchUser"></div></td></tr>
  		<tr><td>from</td><td>${conf.emailConfig.from }</td><td><div class="btn" attrname="from"></div></td></tr>
  		<tr><td>fromname</td><td>${conf.emailConfig.fromname }</td><td><div class="btn" attrname="fromname"></div></td></tr>
  		<tr><td>logo</td><td>${conf.emailConfig.logo }</td><td><div class="btn" attrname="logo"></div></td></tr>
  		<tr><td>name</td><td>${conf.emailConfig.name }</td><td><div class="btn" attrname="name"></div></td></tr>
  		<tr><td>tel</td><td>${conf.emailConfig.tel }</td><td><div class="btn" attrname="tel"></div></td></tr>
  		<tr><td>address</td><td>${conf.emailConfig.address }</td><td><div class="btn" attrname="address"></div></td></tr>
  		<tr><td>email</td><td>${conf.emailConfig.email }</td><td><div class="btn" attrname="email"></div></td></tr>
  		<tr><td>webSite</td><td>${conf.emailConfig.webSite }</td><td><div class="btn" attrname="webSite"></div></td></tr>
  	</table>
</div>
<div>
  	<div class="float title_2">短信</div><div class="float folder close"></div><div style="clear:both"></div><!-- clear float -->
  	<table confid="2" confname="SmsConfig">
  		<tr><td>url</td><td>${conf.smsConfig.url }</td><td><div class="btn" attrname="url"></div></td></tr>
  		<tr><td>ac</td><td>${conf.smsConfig.ac }</td><td><div class="btn" attrname="ac"></div></td></tr>
  		<tr><td>authkey</td><td>${conf.smsConfig.authkey }</td><td><div class="btn" attrname="authkey"></div></td></tr>
  		<tr><td>cgid</td><td>${conf.smsConfig.cgid }</td><td><div class="btn" attrname="cgid"></div></td></tr>
  		<tr><td>csid</td><td>${conf.smsConfig.csid }</td><td><div class="btn" attrname="csid"></div></td></tr>
  		
  		<tr><td>url_2</td><td>${conf.smsConfig.url_2 }</td><td><div class="btn" attrname="url_2"></div></td></tr>
  		<tr><td>ac_2</td><td>${conf.smsConfig.ac_2 }</td><td><div class="btn" attrname="ac_2"></div></td></tr>
  		<tr><td>authkey_2</td><td>${conf.smsConfig.authkey_2 }</td><td><div class="btn" attrname="authkey_2"></div></td></tr>
  		<tr><td>cgid_2</td><td>${conf.smsConfig.cgid_2 }</td><td><div class="btn" attrname="cgid_2"></div></td></tr>
  		<tr><td>csid_2</td><td>${conf.smsConfig.csid_2 }</td><td><div class="btn" attrname="csid_2"></div></td></tr>
  		
  		<tr><td>oth_url</td><td>${conf.smsConfig.oth_url }</td><td><div class="btn" attrname="oth_url"></div></td></tr>
  		<tr><td>oth_ac</td><td>${conf.smsConfig.oth_ac }</td><td><div class="btn" attrname="oth_ac"></div></td></tr>
  		<tr><td>oth_authkey</td><td>${conf.smsConfig.oth_authkey }</td><td><div class="btn" attrname="oth_authkey"></div></td></tr>
  		<tr><td>oth_cgid</td><td>${conf.smsConfig.oth_cgid }</td><td><div class="btn" attrname="oth_cgid"></div></td></tr>
  		<tr><td>oth_csid</td><td>${conf.smsConfig.oth_csid }</td><td><div class="btn" attrname="oth_csid"></div></td></tr>
  	</table>
</div>
<div class="title_1">搜索引擎</div>
<div>
  	<div class="float title_2">lucene</div><div class="float folder close"></div><div style="clear:both"></div><!-- clear float -->
  	<table confid="5" confname="LuceneConfig">
  		<tr><td>索引创建目录</td><td>${conf.luceneConfig.INDEX_URL }</td><td><div class="btn" attrname="INDEX_URL"></div></td></tr>
  		<tr><td>庖丁词库目录</td><td>${conf.luceneConfig.DIR_URL }</td><td><div class="btn" attrname="DIR_URL"></div></td></tr>
  		<tr><td>solrCloud地址</td><td>${conf.luceneConfig.SOLR_URL }</td><td><div class="btn" attrname="SOLR_URL"></div></td></tr>
  	</table>
</div>
<div class="title_1">支付配置</div>
<div>
  	<div class="float title_2">微信</div><div class="float folder close"></div><div style="clear:both"></div><!-- clear float -->
  	<table confid="7" confname="WechatpayConfig">
  		<tr><td>appId</td><td>${conf.wechatpayConfig.appId }</td><td><div class="btn" attrname="appId"></div></td></tr>
  		<tr><td>mchId</td><td>${conf.wechatpayConfig.mchId }</td><td><div class="btn" attrname="mchId"></div></td></tr>
  		<tr><td>appSecret</td><td>${conf.wechatpayConfig.appSecret }</td><td><div class="btn" attrname="appSecret"></div></td></tr>
  		<tr><td>apiKey</td><td>${conf.wechatpayConfig.apiKey }</td><td><div class="btn" attrname="apiKey"></div></td></tr>
  	</table>
</div>
<div>
  	<div class="float title_2">支付宝</div><div class="float folder close"></div><div style="clear:both"></div><!-- clear float -->
  	<table confid="6" confname="AlipayConfig">
  		<tr><td>partner</td><td>${conf.alipayConfig.partner }</td><td><div class="btn" attrname="partner"></div></td></tr>
  		<tr><td>sellerEmail</td><td>${conf.alipayConfig.sellerEmail }</td><td><div class="btn" attrname="sellerEmail"></div></td></tr>
  		<tr><td>key</td><td>${conf.alipayConfig.key }</td><td><div class="btn" attrname="key"></div></td></tr>
  	</table>
</div>
<div class = "title_1">流量统计<c:if test="${conf.flowConfig==null }"><a href="javascript:init(10);">初始化</a></c:if></div>
<c:if test="${conf.flowConfig!=null }">
<div>
	<div class="float title_2">视频</div><div class="float folder close"></div><div style="clear:both"></div>
	<table confid="10" confname="FlowConfig">
		<tr><td>open</td><td>${conf.flowConfig.open }</td><td><div class="btn" attrname="open"></div></td></tr>
		<tr><td>authKey</td><td>${conf.flowConfig.authKey }</td><td><div class="btn" attrname="authKey"></div></td></tr>
	</table>
</div>
</c:if>
<div class="title_1"><font color="red"><strong>此页面</strong></font>秘钥</div>
<div>
  	<div class="float title_2">key</div><div class="float folder close"></div><div style="clear:both"></div><!-- clear float -->
  	<table confid="9" confname="ConfigMgrKey">
  		<tr><td>key</td><td>${conf.configMgrKey }</td><td><div class="btn" attrname="configMgrKey"></div></td></tr>
  	</table>
</div>
</body>
</html>