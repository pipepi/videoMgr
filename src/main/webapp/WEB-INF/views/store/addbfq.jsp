<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<c:set var="rootpath" value="${CONTEXT_PATH}/store" />
<c:set var="ctx" value="<%=request.getContextPath() %>"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${store==null?"添加":"编辑" }播放器</title>
<link rel="stylesheet" type="text/css" href="${CSS_PATH}/imgcut/imgareaselect-default.css"/>
<link rel="stylesheet" href="${CSS_PATH}/store/new_style.css" />
<script src="${JS_PATH}/libs/jquery.min.js"></script>
<script type="text/javascript">
jQuery(document).ready(function($) {
   $(".close-icon").click(function(){
    $(".commodity-kuang").hide();
  
  })
});
</script>
</head>

<body>
 <div class="container-fluid container-fluid2">
      <div class="video-add">
         <div class="video-add-title">
         	<ul>
            	<li onclick = "window.location='${ctx}/store/videolist'">播放器列表</li>
                <li  class="current">${store==null?"添加":"编辑" }播放器</li>
            </ul>
         </div>
      </div>
      <div class="video-tabs"><!--video-tabs Begin-->
        <div class="video-tabs-cons">
               <div class="video-add-cons">
                <ul class="bfq-tab">
                    <li class="current">基本设置 </li>
                    <!-- 
                    <li>&nbsp;高级设置</li>
                     -->
                </ul>
                <div class="clear"></div>
          </div>
          
          <form id="video" action="${rootpath }/savebfq" method="post">
            <div class="voideo-add2">
              <div class="video-add-cons">
              	<input type = "hidden" name = "id"  value = "${store==null?'':store.id }"/>
               <table cellpadding="5" cellspacing="3" border="0" width="600">
                <tr>
                  <td align="right" width="180" ><label><span>*</span> 播放器名称：</label></td>
                  <td ><input type="text" class="inputs" name="name"  maxlength="20" placeholder="请输入播放器名称" value = "${store==null?'':store.name }" onchange = "hideTip('name-tip')"/>
                  <span id="name-tip" stye="display:none"></span></td>
                </tr>
                <tr>
                  <td align="right" ><label><span>*</span> 播放器介绍：</label></td>
                  <td><textarea name="description" placeholder="请输入播放器介绍" maxlength="140" onchange = "hideTip('desc-tip')">${store==null?'':store.description }</textarea>
                  		<span id="desc-tip" stye="display:none"></span></td>
                </tr>
                <tr>
                  <td align="right" ><label><span>*</span> 播放器分享信息内容：</label></td>
                  <td><textarea name="shareContent" placeholder="请输入播放器分享信息内容" maxlength="140" onchange = "hideTip('share-tip')">${store==null?'':store.shareContent }</textarea>
                  <span id="share-tip" stye="display:none"></span></td>
                </tr>
                <tr <c:if test="${store==null||store.linkedVideoIds==''||store.linkedProductIds==''}"> style="display:none"</c:if>>
                  <td align="right" ><label>播放器分享内嵌代码：</label></td>
                  <td><textarea style="height:105px;" name="innerCode" placeholder="请输入播放器分享内嵌代码" readonly="readonly"><object type="application/x-shockwave-flash" data="https://player.kaimai8.com/source/swf/shopPage/YPPlayer.swf" width="960" height="618" id="flashcontent" style="visibility: visible;"><param name="quality" value="high"><param name="allowfullscreen" value="true"><param name="flashvars" value="storeId=${store.id}&amp;autoPlay=false&amp;"></object></textarea>
                  <span id="inner-tip" stye="display:none"></span></td>
                </tr>
                <tr>
                  <td align="right" valign="top"><label><span>*</span>播放器类别选择：</label><br/><span style="font-size:12px; margin-left:55px; float:left">(最多可以选择*个)</span></td>
                  <td>
                    <ul class="check-type">
                    
                        <c:forEach items="${ProductType }" var="productType" varStatus="status">
	                    <li><input type="checkbox" onclick = "hideTip('type-tip')" <c:if test="${types.contains(productType.id) }">checked</c:if> name="store-type" value="${productType.id }" />${productType.name }</li>
	                    </c:forEach>
                        
                    </ul>
                    <span id="type-tip" stye="display:none"></span>
                  </td>
                </tr>
                <tr style="display:none">
                  <td align="right" ><label>商店个性化域名：</label></td>
                  <td ><input type="text" name="privateDns" class="inputs" placeholder="字符不多余**个字符" value = "${store==null?'':store.privateDns }" /> <span>例：www.cinsay.cc/super Cinsay	</span>
                  <span id="private-tip" stye="display:none"></span>
                  </td>
                </tr>
                <!-- <tr>
                  <td align="right" ><label>封面图（最大3M）</label></td>
                  <td ><br /><br />
                <a class="btns-bc"  style="position:relative; float:left;" href="javascript:void(0)">选择图片
                <input type="file"  value="选择文件" name = "upload-pic" onchange="v.upload.img_cover();" id="upload-pic"  tabIndex=3 size=3 name=pic class="filePrew"/>
                </a> 
                <input type="button" name = "cut-img" id = "cut_big_img_div" onclick="v.upload.img_cut();$('#img-tip').hide();" value="裁剪" class="btns-yl" style="float:left"/>
                &nbsp;<span id = "img-tip" style = "display:none;"></span></td>
                </tr>
                
                </td></tr>-->
                
               </table>
                  <div style="width:240px; margin:0 auto;margin-top:20px;">
                  <a class="video-btnsInfor" href="javascript:void(0)" onclick="${store==null?'add':'edit'}Store()">保存</a>
                  <a class="video-btnsInfors" href="javascript:void(0)" onclick="history.go(-1);">返回</a>
                  <div class="clear"></div>
              </div>
              <div class="sp-information" style="display:none">
                <strong style="display:none">商品信息</strong>
                <div class="video-add-cons">
                  <table cellpadding="5" cellspacing="3" border="0" width="600" style="display:none">
                    <tr>
                      <td align="right" width="180" ><label><span>*</span> 公司 / 商店地址：</label></td>
                      <td><input type="text" name="comAddress" class="inputs" maxlength="19" onchange = "hideTip('addr-tip')" placeholder="字数不多余19字符 " value="${store==null?'':store.comAddress }"/>
                      <span id="addr-tip" stye="display:none"></span></td>
                    </tr>
                     <tr>
                      <td align="right" width="180" ><label><span>*</span> 公司 / 商店电话：</label></td>
                      <td><input type="text" name="comTele" class="inputs" maxlength="19" onchange = "hideTip('tele-tip')"  placeholder="字数不多余19字符 " value="${store==null?'':store.comTele }"/>
                      <span id="tele-tip" stye="display:none"></span></td>
                    </tr>
                  </table>
                 </div>
                 <div class="clear"></div>
                </div>
               </div>
            </div>
         </form>
        </div>
        
        </div><!--video-tabs End-->
   </div>
<script type="text/javascript">
var ctx = "${ctx}";
var _in_add = false;
var vali = true; 
	function addStore(){
		if(_in_add)return;
		_in_add = true;
		var url = ctx+"/store/savebfq";
		var name = $('input[name="name"]').val();
		var description = $('textarea[name="description"]').val();
		var shareContent = $('textarea[name="shareContent"]').val();
		var innerCode = $('textarea[name="innerCode"]').val();
		var store_type_check_vals = [];
		$('input[name="store-type"]:checked').each(function(){
					store_type_check_vals.push($(this).val());
				});
		var type = $('input[name="store-type"][checked="checked"]').val();
		var privateDns = $('input[name="privateDns"]').val();
		var comAddress = $('input[name="comAddress"]').val();
		var comTele = $('input[name="comTele"]').val();
		
		vali = true;		
		if(!name||$.trim(name).length<6||$.trim(name).length>20){showTip('name-tip','请输入播放器名称[6-20]');}
		if(!description||$.trim(description).length<1||$.trim(description).length>140){showTip('desc-tip','请输入播放器介绍[1-140]');}
		if(!shareContent||$.trim(shareContent).length<1||$.trim(shareContent).length>140){showTip('share-tip','请输入分享信息[1-140]');}
		if(!store_type_check_vals||store_type_check_vals.length==0){showTip('type-tip','请选择播放器类别');}
		//if(!comAddress||$.trim(comAddress).length==0){showTip('addr-tip','请输入地址');}
		//if(!comTele||$.trim(comTele).length==0){showTip('tele-tip','请输入电话');}
		if(!vali){
			_in_add = false;
			return;
		}
		
		var type = "";
		for(var i = 0;i<store_type_check_vals.length;i++){
			type += store_type_check_vals[i]+",";
		}
		type = type.substring(0,type.length-1);
		
		/* console.log("  name="+name+
					"  description="+description+
					"  shareContent="+shareContent+
					"  innerCode="+innerCode+
					"  store_type_check_vals="+store_type_check_vals+
					"  privateDns="+privateDns+
					"  comAddress="+comAddress+
					"  comTele="+comTele+
					"  type="+type
					); */
		$.post(url,{name:name,
					description:description,
					shareContent:shareContent,
					innerCode:innerCode,
					type:type,
					privateDns:privateDns,
					comAddress:comAddress,
					comTele:comTele
					},function(data){
			  var datas = data.substring(data.indexOf('{')+1,data.lastIndexOf('}'));
	  		  datas = "{" + datas + "}";
	  		  var attr = eval('('+datas+')');
	  		  //console.log(attr);
	  		  if(attr.success){
	  			  window.location = ctx+"/store/videolist";
	  		  }else{
	  			  alert("添加播放器失败");
	  		  }			
	  		_in_add = false;
		});
	}
	function showTip(id,tip){
		$('#'+id).text(tip);
		$('#'+id).show();
		vali = false;
	}
	function hideTip(id){
		$('#'+id).text('');
		$('#'+id).hide();
	}
	function editStore(){
		var url = ctx+"/store/updatebfq";
		var id = $('input[name="id"]').val();
		var name = $('input[name="name"]').val();
		var description = $('textarea[name="description"]').val();
		var shareContent = $('textarea[name="shareContent"]').val();
		var innerCode = $('textarea[name="innerCode"]').val();
		var store_type_check_vals = [];
		$('input[name="store-type"]:checked').each(function(){
					store_type_check_vals.push($(this).val());
				});
		var type = $('input[name="store-type"][checked="checked"]').val();
		var privateDns = $('input[name="privateDns"]').val();
		var comAddress = $('input[name="comAddress"]').val();
		var comTele = $('input[name="comTele"]').val();
		
		vali = true;		
		if(!name||$.trim(name).length<6||$.trim(name).length>20){showTip('name-tip','请输入播放器名称[6-20]');}
		if(!description||$.trim(description).length<1||$.trim(description).length>140){showTip('desc-tip','请输入播放器介绍[1-140]');}
		if(!shareContent||$.trim(shareContent).length<1||$.trim(shareContent).length>140){showTip('share-tip','请输入分享信息[1-140]');}
		if(!store_type_check_vals||store_type_check_vals.length==0){showTip('type-tip','请选择播放器类别');}
		//if(!comAddress||$.trim(comAddress).length==0){showTip('addr-tip','请输入地址');}
		//if(!comTele||$.trim(comTele).length==0){showTip('tele-tip','请输入电话');}
		if(!id||$.trim(id).length==0){vali=false;}
		if(!vali)return;
		
		var type = "";
		for(var i = 0;i<store_type_check_vals.length;i++){
			type += store_type_check_vals[i]+",";
		}
		type = type.substring(0,type.length-1);
		
		/* console.log("  id="+id+
					"  name="+name+
					"  description="+description+
					"  shareContent="+shareContent+
					"  innerCode="+innerCode+
					"  store_type_check_vals="+store_type_check_vals+
					"  privateDns="+privateDns+
					"  comAddress="+comAddress+
					"  comTele="+comTele+
					"  type="+type
					); */
		$.post(url,{id:id,
					name:name,
					description:description,
					shareContent:shareContent,
					innerCode:innerCode,
					type:type,
					privateDns:privateDns,
					comAddress:comAddress,
					comTele:comTele
					},function(data){
			  var datas = data.substring(data.indexOf('{')+1,data.lastIndexOf('}'));
	  		  datas = "{" + datas + "}";
	  		  var attr = eval('('+datas+')');
	  		  //console.log(attr);
	  		  if(attr.success){
	  			  window.location = ctx+"/store/videolist";
	  		  }else{
	  			  alert("修改播放器失败");
	  		  }			
		});
	}
</script>
</body>
</html>