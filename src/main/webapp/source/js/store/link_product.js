var _pn = 1;
var _order_by = "time";
var _order_type = "desc";
var _purl = _ctx+"/product3/list4player";
function go(pn){
	_pn = pn;
	loadProductList();
	
}
function initProductList(){
	$.post(_purl,{pageNo:_pn,orderBy:_order_by,orderType:_order_type},function(data){
		//console.log(data);
		$('#product-list').html(data);
	});
}
function loadProductListOrdered(_orderby,_ordertype){
	_order_by = _orderby;
	_order_type = _ordertype;
	$.post(_purl,{pageNo:_pn,orderBy:_order_by,orderType:_order_type},function(data){
		//console.log(data);
		$('#product-list').html(data);
		showLinkProductPage(_player);
	});
}
function loadProductList(){
		$.post(_purl,{pageNo:_pn,orderBy:_order_by,orderType:_order_type},function(data){
			//console.log(data);
			$('#product-list').html(data);
			showLinkProductPage(_player);
		});
	}
	
function showLinkProductPage(playerid){
	if(playerid!=_player){
		_linked_productids_arr = new Array();
		var linkedids = $('#link-product-a-'+playerid).attr("linked-product-ids");
		if(linkedids){
			if(linkedids.indexOf(',',0)>0){
				var lidsArr = linkedids.split(',');
				for(var i = 0;i<lidsArr.length;i++){
					_linked_productids_arr.push(parseInt(lidsArr[i]));
				}
			}else{
				_linked_productids_arr[0] = parseInt(linkedids);
			}
		}
	}
	_player = playerid;
	//console.log(linkedids);
	var checkboxlist =document.getElementsByName("input-product");
	//console.log(checkboxlist);
	if(checkboxlist.length>0){
		//var checkednum = 0;
		for(var i=0;i<checkboxlist.length;i++){
			//console.log(parseInt($(checkboxlist[i]).val()));
			if($.inArray(parseInt($(checkboxlist[i]).val()),_linked_productids_arr)>=0){
				$(checkboxlist[i]).attr("checked",true);
				//checkednum++;
			}else{
				$(checkboxlist[i]).attr("checked",false);
			}
		}
	}
	$('#checkednum2').text(_linked_productids_arr.length);
	$("#product-list").show();
}
function checkProduct(obj){
	var num = parseInt($('#checkednum2').text());
	var id = parseInt($(obj).val());
	if($(obj).attr('checked')){
		$('#checkednum2').text(num+1);
		_linked_productids_arr.push(id);
	}else{
		$('#checkednum2').text(num-1);
		var ind = $.inArray(id,_linked_productids_arr);
		//console.log("id = "+id+"  ind="+ind);
		_linked_productids_arr.splice(ind,1);
	}
	//console.log(_linked_productids_arr);
}
function recheckProduct(){
	var checkboslist =$("#product-list").find('input');
	if(checkboslist.length>0){
		for(var i=0;i<checkboslist.length;i++){
			$(checkboslist[i]).attr('checked',false);
		}
		$('#checkednum2').text(0);
		//$('#link-product-a-'+playerid).attr("linked-product-ids","");
		_linked_productids_arr = new Array();
	}
	//console.log(_linked_productids_arr);
}
function saveProductLinks(){
	if(_player<=0){
		alert("播放器id错误");
		return;
	}
	var url = _ctx+"/product3/linkPlayer";
	pids = "";
	//console.log(" _linked_productids_arr.length = "+_linked_productids_arr.length);
	if(_linked_productids_arr.length>0){
		for(var i=0;i<_linked_productids_arr.length;i++){
			pids+=""+_linked_productids_arr[i]+",";
		}
		pids = pids.substring(0,pids.length-1);
	}
	//console.log(" _player = "+_player+"   pids = "+pids);
	$.post(url,{storeId:_player,productIds:pids},function(data){
		 var datas = data.substring(data.indexOf('{')+1,data.lastIndexOf('}'));
		  datas = "{" + datas + "}";
		  var attr = eval('('+datas+')');
		  if(attr.success){
			  $('#linked-num2-span-'+_player).html("<strong>"+_linked_productids_arr.length+"</strong>");
			  $('#link-product-a-'+_player).attr("linked-product-ids",pids);
			 $("#product-list").toggle();
		  }else{
			  if(attr.ol>0){
				  alert("单个播放器关联["+attr.ol+"]个商品，但您的套餐单个播放器只可承载["+attr.oc+"]个！");
			  }else{
				  alert("保存将关联["+attr.l+"]个商品，但您的套餐只可关联["+attr.c+"]个！");
			  }
		  }
	});
	
}