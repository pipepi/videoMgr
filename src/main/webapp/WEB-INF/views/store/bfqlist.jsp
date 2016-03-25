<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<c:set var="rootpath" value="${CONTEXT_PATH}/store"/>
<c:set var="ctx" value="<%=request.getContextPath() %>"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新建播放器</title>
<link rel="stylesheet" href="${CSS_PATH}/store/new_style.css" />
<style type="text/css">
.div-fixed{
position: absolute;
margin-left:100px;
z-index: 1000;
}
</style>
<script src="${JS_PATH}/libs/jquery.min.js"></script>
<script type = "text/javascript" charset="utf-8" src="${ctx}/source/js/jquery.swfobject.1-1-1.js"></script>
<script type="text/javascript">
var _ctx = "${ctx}";
</script>
</head>

<body>
 <div class="container-fluid container-fluid2">
 	
      <div class="video-add">
         <div class="video-add-title">
         	<ul>
            	<li class="current">播放器列表</li>
                <li onclick = "addPlayer()">添加新播放器</li>
                <li><span id="packagelimitDesc" style="display: none;color: #e3393c;"></span></li>
                
            </ul>
         </div>
      </div>
      <div class="video-tabs"><!--video-tabs Begin-->
               
            <div class="video-tabs-cons">
               <div class="video-add-cons" >
                <ul class="bfq-tab">
                    <li class="bfq-bfNumber">已建立播放器数量<div class="fql-playerCount">${playerCount }</div></li>
                    <li  class="bfq-li02">
                     <ul>
                    	<li> <div style="padding-top:4px; float:left; color:#666666;"><div class="bfq-flow-font">当前套餐使用情况</div>
                    	<div style="width:452px; float:left;margin-left:9px;">&nbsp;播放器：<span>${playerCount }/${packageInfo.playerNum }</span>
                    	 &nbsp;视频： <span>${storeVideoCount}/${totalVideoNum }</span> &nbsp;产品：<span>${storeProductCount}/${totalProductNum }</span> &nbsp;有效期至：${packageEndTime}
                    	 </div>
                    	 </div>
                    	 <!-- <a href="javascript:buyPackage();" class="taocan-xy">套餐续约</a>  -->
                    	 </li>
                        <li> <div style="padding-top:5px; float:left; color:#666666;"><strong style="color:#333333;font-size:16px;">当前流量剩余情况</strong></div>
                        <div class="bf-list-jdt"><div class="bf-list-jdt-cons" style="width:${flowUseRate}%;"></div><div class="bf-liu-font">${remainFlowNum}G/${totalFlowNum}G</div></div>
                        <div style="float:left; margin-top:7px;margin-left:5px; color:#666666;">&nbsp;</div>
                        <!--  <a href="javascript:buyFlow();" class="goumai-ll">购买流量</a>  -->
                        
                        </li>
                    </ul>
                   </li>
                   <li>
                   	  	<a href="javascript:buyFlow();" class="goumai-ll">购买流量</a>
                		 <a href="javascript:buyPackage();" class="taocan-xy">套餐续约</a>
                		 <div style="margin-top:35px;"><a href="javascript:packageRecord();" class="lsjv-ch">历史记录查询</a></div>
                   </li>
                </ul>
                <div class="clear"></div>
               </div>
               
   
            <div class="voideo-add2"><!--voideo-add2 Begin-->
              <div class="video-add-cons" style="min-height:550px;height:auto;">
	              <div id = "video-yulan" class="voideo-yulan-consTan">
				   <div style = "position: absolute;right:0;top:0;"><a href="javascript:void(0);" onclick="closePreView()" class="close-icon"></a></div>
				   <div id = "yulan" class="video-yulan" ></div>
			      </div>  
			 	  <!--视频弹出框-->
			      <div id = "video-list" class="commodity-kuang video-kuang " style="z-index: 1000;display:none">
			      </div>
			      <!--商品弹出框-->
			      <div id="product-list" class="commodity-kuang commoditys-kuang " style="z-index: 1000;display:none" >
			      </div>
              <input type="hidden" value="${canAddPlayerNum}" id="canAddPlayerNum"></input>
              <input type="hidden" value="${packagePlayerNum}" id="packagePlayerNum"></input>
              <input type="hidden" value="${playerCount}" id="playerCount"></input>
               <ul class="bfq-listInfors">
                 <c:forEach items="${storelist }" var="store" varStatus="status">
               	 <li>
               	 
                   <div class="bf-lists"><img id="store-logo-${store.id}"
                    src="<c:choose><c:when test="${store.logoUrl!=null }">${store.logoUrl }</c:when><c:otherwise>${ctx }/source/images/aztimg/logo_kaimai8.png</c:otherwise></c:choose>" 
                   	onerror='this.src="${ctx }/source/images/aztimg/logo_kaimai8.png"' 
                    title="${store.description }" width="210px" height="126px" /><br/>
                   <em title="${store.name }" 
                    class="bfq-storeName">${store.name }</em>
                   </div>
                   <div class="bf-edit" >
                     <a href="javascript:edit(${store.id });" class="bfq-bjBtns" style="margin-left:20px;">编辑</a>
                     <a href="javascript:void(0)" previewbtn="1" storeId="${store.id }" class="bfq-bjBtns" >预览</a>
                     <a href="javascript:void(0)" linkPbtn="1" storeId="${store.id }"  class="bfq-cpBtns" id="link-product-a-${store.id}"  linked-product-ids = "${store.linkedProductIds==null?'':store.linkedProductIds }" >关联产品</a>
                     <a href="javascript:void(0)" linkVbtn="1" storeId="${store.id }"  class="bfq-spBtns" id="link-video-a-${store.id}" linked-video-ids = "${store.linkedVideoIds==null?'':store.linkedVideoIds }">关联视频</a>
                     <div class="bfq-time">最后编辑于：<fmt:formatDate value="${store.createTime }" pattern="yyyy年MM月dd日"/>
                                           已关联商品：<span id = "linked-num2-span-${store.id}"><strong>${store.linkedProductNum }</strong></span> 个/ 
                                           已关联视频：<span id = "linked-num-span-${store.id}"><strong>${store.linkedVideoNum }</strong></span> 个   </div>
                   </div>
                   
                   <div class="bfq-del-btns"><a href="javascript:void(0)"  class="zhineng-sreach" testvalue="${ store.id }">删除</a></div>
                 </li>
                 </c:forEach>
                 <div class="clear"></div>
               </ul>
              </div>
            </div><!--voideo-add2 End-->
           </div>
   
        </div><!--video-tabs End-->
   </div>
<script type="text/javascript" src="${ctx}/source/js/store/link_video.js"></script>
<script type="text/javascript" src="${ctx}/source/js/store/link_product.js"></script>
<script type="text/javascript">
var memberId = "${memberId}";
var _player = 0;
var _linked_videoids_arr = new Array();
var _linked_productids_arr = new Array();
var vids = "";
var pids = "";
initProductList();
loadVideoList();
function parseWindowTop(mouse_y){
	if(mouse_y>330&&mouse_y<460){
		mouse_y = mouse_y-460;
	}else if(mouse_y<=330){
		mouse_y = mouse_y -330;
	}else if(mouse_y<630){
		mouse_y = mouse_y -630;
	}else{
		mouse_y = mouse_y -670;
	}
	return mouse_y;
}
$(function(){
	$("[previewbtn=1]").click(function(e){
		var _store_id = $(this).attr('storeId');
		var _mouse_y = e.pageY;
		preView(_mouse_y,_store_id);
	});
	$("[linkPbtn=1]").click(function(e){
		var _store_id = $(this).attr('storeId');
		var _mouse_y = e.pageY;
		$('#product-list').css("top",parseWindowTop(_mouse_y)+"px");
		clinkProductPage(_store_id);
	});
	$("[linkVbtn=1]").click(function(e){
		var _store_id = $(this).attr('storeId');
		var _mouse_y = e.pageY;
		$('#video-list').css("top",parseWindowTop(_mouse_y)+"px");
		clinkVideoPage(_store_id);
	});
});
function preView(mouseY,id){
	
	if(!checkPackageStat()){
		return ;
	}
	var p_num = $('#linked-num2-span-'+id).find('strong').html();
	var v_num = $('#linked-num-span-'+id).find('strong').html();
	if(p_num=="0"||v_num=="0"){
		alert("请关联视频和产品");
		return;
	}
	//tmp todo
	//id = 2
	var _top_value = parseWindowTop(mouseY);
	//console.log("mouseY="+mouseY+"  _top_value="+_top_value);
	$('#video-yulan').css("top",_top_value+"px");
	$('#video-yulan').toggle();
	$("div#yulan").flash(
	    {
	      swf:_ctx+"/source/swf/preview/YPPlayer.swf",
	      width:800,
	      height:618,
	      allowFullScreenInteractive:true,
	      wmode: 'opaque',
	      flashvars:{ 
	    	storeId:id,
	    	userId:memberId
	      }
	    }
    );
}	


function clinkProductPage(id){
	 if(!checkPackageStat()){
			return ;
		}
	showLinkProductPage(id);
}

function clinkVideoPage(id){
	 if(!checkPackageStat()){
			return ;
		}
	showLinkVideoPage(id);
}

function edit(id){
	  if(!checkPackageStat()){
			return ;
		}
	window.location='${ctx}/store/editStore?storeId='+id;	
}

function closePreView(){
	$('#video-yulan').toggle();
	$("div#yulan").flash().remove();
}

function checkPackageStat(){
	var remainFlowNum="${remainFlowNum}"
		var totalFlowNum = "${totalFlowNum}"
		var isOutDate="${isOutDate}";
		var packageEndTime="${packageEndTime}";

		if(isOutDate=="true"){
			$("#packagelimitDesc").css("display","");
			$("#packagelimitDesc").text("套餐已过期，请续约。");
			return false;
		}else if(remainFlowNum<=0){	
			$("#packagelimitDesc").css("display","");
			$("#packagelimitDesc").text("流量不够，请购买流量。");
			return false;
		}
		return true;
}

//checkPackageStat();



var _ctx = "${ctx}";
jQuery(document).ready(function($) {
   $(".close-icon").click(function(){
   $(".commoditys-kuang").hide();
   $(".video-kuang").hide();
  
  })
});


//发布
jQuery(document).ready(function($) {
   $(".open-btns").click(function(){
    	window.location.href = '${rootpath}/videoOffLine?eqId=' + $(this).attr('testvalue');
  })
});
//删除
jQuery(document).ready(function($) {
   $(".zhineng-sreach").click(function(){
	   
	   if(!checkPackageStat()){
			return ;
		}
	   
    if(confirm('确定要删除播放器吗？')){
       window.location.href = '${rootpath}/deletestore?storeId=' + $(this).attr('testvalue');
    }
  })
});

//离线
jQuery(document).ready(function($) {
   $(".close-btns").click(function(){
       window.location.href = '${rootpath}/videoOnLine?eqId=' + $(this).attr('testvalue');
  })
});


function addPlayer(){
	if(!checkPackageStat()){
		return ;
	}
	var canAddPlayerNum=$("#canAddPlayerNum").val();
	var packagePlayerNum=$("#packagePlayerNum").val();
	var playerCount=$("#playerCount").val();
	
	
	if(canAddPlayerNum<=0){
		$("#packagelimitDesc").css("display","");
		$("#packagelimitDesc").text("可分享播放器["+playerCount+"]已达上限["+packagePlayerNum+"]");
		return;
	}
	
	window.location='${ctx}/store/addbfq'
}


function buyFlow(){
	
  window.location='${ctx}/package/buyflow';
  
}

function buyPackage(){
	window.location='${ctx}/package/buypackagelist';
}

function packageRecord(){
	window.location='${ctx}/package/recordpage';
}


</script>
</body>
</html>
