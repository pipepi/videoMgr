<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>财付通支付</title>
<script type="text/javascript">
function red(){
	
window.location.href="${params.tenpayUrl}";
}
</script>
</head>
<body onload="javascript:red();">

<br/><a target="_blank" href="${params.tenpayUrl}">正在连接到财富通支付页面……请稍后</a>

</body>
</html>
