<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="<%=request.getContextPath() %>"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>微信扫描支付</title>
<link rel="stylesheet" href="${ctx}/source/css/pay/wechatpcguid.css" />
<link rel="stylesheet" href="${ctx}/source/css/pay/wechatpc.css" />

<script type = "text/javascript" charset="utf-8" src="${ctx}/source/js/libs/jquery/jquery/jquery.js"></script>

<script type="text/javascript" src="${ctx}/source/js/pay/wechatpayguid.js"></script>

</head>
<body>

<div class="content">
  <div class="wrapper">
    
      <div class="area primary">
        <div class="pay_msg" id="payMsg">
          <div class="area_bd" id="qr_normal">
            <span class="qr_img_wrapper"><img class="qrcode" src="${params.imgUrl}" alt="二维码" id="QRcode" /><img class="guide pngFix" src="${ctx}/source/images/pay/webpay_guide.png" alt="" id="guide" /></span>
			<div class="msg_default_box">
              <i class="icon60_qr" style="background-position:0 -60px"></i>
              <p>请使用微信扫描<br/>二维码以完成支付</p>
            </div>
          </div>
          <div class="area second">
        <div class="pay_bill shopping">
          <div class="area_hd ">
            <span class="icon_wrapper"><i class="icon60_pay pngFix"></i></span>
          </div>
          <div class="area_bd">
            <h3 class="pay_money"> <span>￥</span>${params.totalFee}</h3>

          </div>
        </div>
      </div>
        </div>
      </div>
    
  </div>
</div>


</body>

</html>
