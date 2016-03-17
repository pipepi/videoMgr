<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<c:set var="rootpath" value="${CONTEXT_PATH}/package"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>智能商家套餐管理</title>
<link rel="stylesheet" href="${ctx}/source/css/partner/manage_package.css" />
<script type = "text/javascript" charset="utf-8" src="${ctx}/source/js/libs/jquery/jquery/jquery.js"></script>
<script type="text/javascript" >



/* function chooseDuration(d){
	
	var beforeDuration=$("#duration").attr("value");
	
	$("#duration").attr("value",d);
	$("#duration"+beforeDuration).attr("class","zhine-a");
	$("#duration"+d).attr("class","currents");	

} */


function submit(){	
	
	
	var name=$("#name").val();
	
	if(name==null||name==""||name.trim()==""){
		$("#name-tip").text("请输入套餐名称");
		$("#name-tip").show();
		return;		
	}
	
	
	$("#packagesave").submit();
}




	
</script>
</head>

<body>

 <div class="container-fluid">
      <div class="video-add">
         <div class="video-add-title">
         	<ul>
            	<li onclick = "window.location='${ctx}/package/list'">管理</li>
                <li class="current" >${packageInfo==null?"添加":"编辑"}套餐</li>
            </ul>
         </div>
      </div>
      <div class="video-tabs"><!--video-tabs Begin-->
        <div class="video-tabs-cons">
               
          <form id="packagesave" action="${ctx}/package/${packageInfo==null ? 'save' : 'update' }">
            <div class="voideo-add2">
              <div class="video-add-cons zhineng-add">
               <table cellpadding="5" cellspacing="3" border="0" width="700">
                <tr>
                  <td align="right" width="180" ><label>套餐名称：</label></td>
                  <td ><input type="text" maxlength="50" class="inputs" name="name" id="name" value="${packageInfo==null?'':packageInfo.name }"/> &nbsp;<span id = "name-tip" style = "display:none;color: #e3393c;"></span></td>
                </tr>
                <tr>
                  <td align="right" ><label>可分享播放器（个）：</label></td>
                  <td ><input type="text" class="inputs" name="playerNum" maxlength="5" value="${packageInfo==null?'':packageInfo.playerNum }"/></td>
                </tr>
                <tr>
                  <td align="right" ><label>每播放器搭载视频（个）：</label></td>
                  <td ><input type="text" class="inputs" name="videoNum" maxlength="5" value="${packageInfo==null?'':packageInfo.videoNum }"/></td>
                </tr>
                <tr>
                  <td align="right" ><label>每播放器搭载商品（个）：</label></td>
                 <td ><input type="text" class="inputs" name="productNum" maxlength="5" value="${packageInfo==null?'':packageInfo.productNum }"/></td>
                </tr>
                
                
                <tr>
                  <td align="right" ><label>套餐价格（元）：</label></td>
                 <td ><input type="text" class="inputs" name="price" maxlength="5" value="${packageInfo==null?'':packageInfo.totalPrice }"/></td>
                </tr>
                
                
                <tr>
                  <td align="right" ><label>流量(单位GB)：</label></td>
                 <td ><input type="text" class="inputs" name="flowNum" maxlength="5" value="${packageInfo==null?'':packageInfo.flowNum }"/></td>
                </tr>
                <tr>
                  <td align="right" ><label>期限（月）：</label></td>
                  <!--<td>
                   <a href="javascript:chooseDuration(1)" id="duration1" class="zhine-a ${(packageInfo!=null&&packageInfo.duration=='1')?'currents':''}" >1个月</a>
                  <a href="javascript:chooseDuration(6)" id="duration6" class="zhine-a ${(packageInfo!=null&&packageInfo.duration=='6')?'currents':''}" >6个月</a>
                  <a href="javascript:chooseDuration('12')" id="duration12" class="zhine-a ${(packageInfo!=null&&packageInfo.duration=='12')?'currents':''}">12个月</a>
                  <a href="javascript:chooseDuration('24')" id="duration24" class="zhine-a ${(packageInfo!=null&&packageInfo.duration=='24')?'currents':''}">24个月</a>
                  </td>-->
                  <td><input type="text" class="inputs" name="duration"  id="duration"  maxlength="5" value="${packageInfo==null?'':packageInfo.duration }"/></td>
                </tr>
               </table>
                 <div style="width:240px; margin:0 auto;margin-top:20px;">
                 
                  <a href="javascript:submit();" class="video-btnsInfor">保存</a>
             	 <a href="javascript:window.location='${ctx}/package/list';" class="video-btnsInfors">取消</a>
              
                 </div>
                 <div class="clear"></div>
              </div>
            </div>
            <input type="hidden" class="inputs" name="id" value="${packageInfo==null?'':packageInfo.id }"/>
            
            <!-- <input type="hidden" class="inputs" name="duration"  id="duration" value="${packageInfo==null?'':packageInfo.duration }"/> -->
            
         </form>
        </div>
        
        
   
        </div><!--video-tabs End-->
   </div>


</body>
</html>