<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<c:set var="rootpath" value="${CONTEXT_PATH}/package"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>确认购买流量</title>
<link rel="stylesheet" href="${ctx}/source/css/partner/buy_package.css" />
<script type = "text/javascript" charset="utf-8" src="${ctx}/source/js/libs/jquery/jquery/jquery.js"></script>
</head>

<body>
<div class="container-fluid">
      <div class="video-add">
         <div class="video-add-title">
         	<ul>
            	<li>套餐购买</li>
              <li class="current">流量购买</li>
            </ul>
         </div>
      </div>
            <div class="taocan-containter">
        <strong>当前套餐使用情况</strong>播放器：<span>9/9</span> &nbsp;视频： <span>99/99</span> &nbsp;流量剩余：<span>9999G</span> &nbsp;有限期至2015年12月31日
      </div>
 <div class="sj_buy-video" style="padding-top:20px;">
   <div class="sj-buy-videoCons" style="display:none;">
     <img src="imgaes/jdt_2.jpg" width="885" height="62" />
     <ul class="tjt-font">
     	<li style="margin-left:65px;">套餐选择</li>
        <li style="margin-left:295px;"><strong>套餐支付</strong></li>
        <li style="margin-left:280px;">开启视频电商</li>
        <div class="clear"></div>
     </ul>
        <!--支付平台-->
       
      <div class="video-kuangs" style="z-index:2000; left:30%;" >
           <div class="video-titles">
                   <span>支付平台</span>
                  <a href="javascript:void(0)" class="close-icon"></a>
                 </div>
                 <div class="zf-fonts"><!--comm-cons Begin-->
                	请您在新打开的支付平台页面进行支付，支付完成前请不要关闭窗口
                </div>  
                   <div style="width:216px;margin-top:50px;margin-left:135px;">
                     <a href="" class="video-btnsInfors">取消</a><a href="" class="video-btnsInfors-s">已支付完成</a>
                    </div>
          </div><!--comm-cons End-->
      </div>
      
      
       <!--支付平台失败-->
      <div class="video-kuangs_1 video-kuangs" style="z-index:2000;left:27%;" >
           <div class="video-titles">
                   <span>支付平台</span>
                  <a href="javascript:void(0)" class="close-icon"></a>
                 </div>
                 <div class="zf-fonts"><!--comm-cons Begin-->
             		   请未完成支付，请重新支付
                </div>  
                   <div style="width:216px;margin-top:50px;margin-left:190px;">
                     <a href="" class="video-btnsInfors-s">确定</a>
                    </div>
          </div><!--comm-cons End-->
      

     <!--请选择支付方式-->
     <div class="choice-type2" >
        <div style="margin:30px 0 0 70px;color:#333333; font-size:16px;">请选择支付方式</div>
        <ul>
           <li><input type="radio"  checked="checked" style=" vertical-align:top;margin-top:20px;margin-right:10px;"/><img src="imgaes/zfb.jpg" width="126" height="36" /></li>
           <li><input type="radio"  style=" vertical-align:top;margin-top:20px;margin-right:10px;"/><img src="imgaes/weibo.jpg" width="126" height="36" /></li>
        </ul>
        <div class="next-btns-zf" style="width:240px;"><a href="index3.html" class="naxt-btns-cons" style="width:100px;">支付</a><a href="index.html" class="black-btns-cons" style="width:100px; margin-left:10px;">返回</a></div>    
     </div>
         <div class="clear" style="padding-bottom:30px;">&nbsp;</div>  
   </div> 
</div>
</div>
</div>

</body>
</html>
