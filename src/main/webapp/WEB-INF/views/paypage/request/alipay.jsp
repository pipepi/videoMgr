<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
  
<html>
  <head>
 	 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>支付宝充值</title>
  </head>
  <body>
       
	<form name="alipaysubmit" id="alipaysubmit" method="post" action="${params.paygateway}_input_charset=utf-8">
		<input type=hidden name="body" value="${params.body}"/>
		<input type=hidden name="notify_url" value="${params.notify_url}"/>
		<input type=hidden name="out_trade_no" value="${params.out_trade_no}"/>
		<input type=hidden name="partner" value="${params.partner}"/>
		<input type=hidden name="payment_type" value="${params.payment_type}"/>
		<input type=hidden name="seller_email" value="${params.seller_email}"/>
		<input type=hidden name="service" value="${params.service}"/>
		<input type=hidden name="sign" value="${params.sign}"/>
		<input type=hidden name="sign_type" value="${params.sign_type}"/>
		<input type=hidden name="subject" value="${params.subject}"/>
		<input type=hidden name="total_fee" value="${params.total_fee}"/>
		<input type=hidden name="show_url" value="${params.show_url}"/>
		<input type=hidden name="return_url" value="${params.return_url}"/>
		<input type=hidden name="_input_charset" value="${params._input_charset}">
		<input type=hidden name="exter_invoke_ip" value="${params.exter_invoke_ip}">
	</form>
	<script type="text/javascript">
	//<!--
	  document.write("正在连接到支付宝支付页面……请稍后");
	  document.getElementById("alipaysubmit").submit();
	//-->
	</script>
	</body>
</html>
