<html>
<head>
	<title>Loading...</title>
	<script src="../../js/libs/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		function getQueryString(name) { 
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
			var r = window.location.search.substr(1).match(reg); 
			if (r != null) return unescape(r[2]); return null; 
		} 
		var param = getQueryString("storeId");
		var storeId = param.split(",")[0];
		var mallHost = param.split(",")[1];
		function IsPC() 
		{ 
			var userAgentInfo = navigator.userAgent; 
			var Agents = new Array("Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod"); 
			var flag = true; 
			for (var v = 0; v < Agents.length; v++) { 
				if (userAgentInfo.indexOf(Agents[v]) > 0) 
				{
				 	flag = false; 
				 	break; 
				} 
			} 
			
			var userAgentInfo = navigator.userAgent; 
			var Agents = new Array("Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod"); 
			var flag = true; 
			for (var v = 0; v < Agents.length; v++) { 
				if (userAgentInfo.indexOf(Agents[v]) > 0) 
				{
				 	flag = false; 
				 	break; 
				} 
			} 
			if(flag)
			{
				window.location = mallHost+"/Play/Index?id="+storeId;
			}else
			{
				window.location = mallHost+"/m-Wap/VideoShop/shop?storeId="+storeId;
			}
			return flag; 
		}
		IsPC();
	</script>

	<?php
		header("Last-Modified: " . gmdate( "D, d M Y H:i:s" ) . "GMT" );  
		header("Cache-Control: no-cache, must-revalidate" );  
	?>
</head>
<body>
	
		
	
</body>

</html>