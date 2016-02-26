/**
 * lanker
 * 2015-08-06
 */
var $ = jQuery;
var l = {
		update:function(obj){
			var vid = $(obj).attr("v_id");
			var active = $(obj).attr("checked");
			if(active=='checked'){
				active = true;
			}else{
				active = false;  
			}
			//alert(vid+"   "+active);
			$.post("/videoMgr/video/update_ajax",{"video_id":vid,"active":active},function(data){
				var datas = data.substring(data.indexOf('{')+1,data.lastIndexOf('}'));
	    		  datas = "{" + datas + "}";
	    		  var attr = eval('('+datas+')');
	    		  if(attr.success == true){
	    			  
	    		  }else{
	    			  alert("修改激活状态失败");
	    		  }
			});
		}
}