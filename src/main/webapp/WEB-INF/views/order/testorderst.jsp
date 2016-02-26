<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<c:set var="ctx" value="<%=request.getContextPath() %>"/>
<form action="${ctx }/rest/order/create" method="post">
外部订单号 ID：<input name="channel_id" />（需唯一）<br />
买家ID：<input name="buyers_id" /> <br />
买家名：<input name="buyers_name" /> <br />
卖家ID：<input name="seller_id" /> <br />
卖家名：<input name="seller_name" /> <br />
产品SKU(可复数):<input name="product_id" />（产品SKU）遍历 <br />
产品数量(可复数):<input name="quantitys" />（数量）遍历 <br />
收货地址：<input name="to_address" /> <br />
邮箱：<input name="to_mail" /> <br />
邮政编码：<input name="to_zip_code" /> <br />
收货手机：<input name="to_mobile" /> <br />
属性：<input name="attr" /> <br />
<input type="submit" value="创建订单">
</form><br />
<form action="${ctx }/rest/order/update" method="post">
渠道ID：<input name="channel_id" /> <br />
订单ID：<input name="order_id" /> <br />
价格：<input name="price" />  1、流水号；2、订单号；3、价格支持到小数点后2位<br />
<input type="hidden" name="method" value="1" />
<input type="submit" value="修改价格">
</form><br />
<form action="${ctx }/rest/order/update" method="post">
渠道ID：<input name="channel_id" /> <br />
订单ID：<input name="order_id" /> <br />
订单状态：<input name="order_status" />  0、未支付；1、支付；2、完成；3、已关闭 <br />
<input type="hidden" name="method" value="2" />
<input type="submit" value="修改订单状态">
</form><br />
<form action="${ctx }/rest/order/update" method="post">
渠道ID：<input name="channel_id" /> <br />
订单ID：<input name="order_id" /> <br />
到货状态：<input name="deliver_status" />0、未送货；1、已送货未到达；2、已收货；3退货；4、延期 <br />
<input type="hidden" name="method" value="3" />
<input type="submit" value="修改到货状态">
</form><br />
<form action="${ctx }/rest/order/update" method="post">
渠道ID：<input name="channel_id" /> <br />
订单ID：<input name="order_id" /> <br />
<input type="hidden" name="method" value="4" />
<input type="submit" value="取消状态">
</form>

<form action="${ctx }/pay/platformPay" method="post">
订单号：<input name="orderId" /> <br />
 支付类型: <select id="payType" name="payType"  style="width:150px;">
	    <option value="1">支付宝</option>
       	<option value="2">财富通</option>
       	<option value="3">微信</option>
       	<option value="4">支付宝wap</option>
	    </select>
<input type="submit" value="付款">
</form>


</body>
</html>