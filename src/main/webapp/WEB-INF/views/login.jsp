<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %> 
<%@ include file="/WEB-INF/views/inc/header.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/inc/_meta.jsp" %>
    <title>智能商家管理系统-登录</title>
    <!-- 图标 -->
    <link rel="icon" href="${IMAGES_PATH}/login/logo_ico.png" type="image/png" />
    <link rel="shortcut icon" href="${IMAGES_PATH}/login/logo_ico.png" type="image/png" />
    <link rel="bookmark" href="${IMAGES_PATH}/login/logo_ico.png" type="image/png" />
    <link rel="stylesheet" href="${CSS_PATH }/plus/login.css">
    <%@ include file="/WEB-INF/views/inc/_ie.jsp" %>
</head>
<body class="loginbg">
<div class="container">
    <div class="login-box">
        <div class="login-box-inner" id="J_login_tab_box" >
            <div class="ui-switchable-content fn-clear" id="J_login_tab">
                <div id="J_login_tab_trigger"></div>

                <div class="tab-cnt" style="" id="commonLoginTab">
                    <div class="hd login-box-bd">
                        <h2>智能商家管理系统</h2>
                    </div>
                    <div class="bd login-box-bd">
                        <form class="ui-form" name="loginForm" action="${ctx }/login" method="post"  id="loginForm">
                            <input type="hidden" name="submitAction" value="submit" />
                            <input type="hidden" name="clientType" value="1" />
                            <input type="hidden" name="returnUrl" value="" />
                            <div id="J_form_explain" >
                                <!-- 错误消息 -->
								<span style="color:red;">${error }</span>
                                <!-- 消息提示,正确消息 -->

                                <!-- 输出commandObject的错误消息 -->

                            </div>
                            <div class="ui-form-item ui-1-item">
                                <input autocomplete="off" type="text" class="ui-input tianyi-user-input"
                                       placeholder="请输入用户名" id="userName" name="userName" value="${userName }"  />
                                <span id="userNameMsg"></span> </div>
                            <div class="ui-form-item ui-2-item">
                                <input type="password" class="ui-input password-input" placeholder="请输入登录密码" id="password" name="password" value="">
                                <span id="pswMsg"></span> </div>

                            <div class="ui-form-item">
                                <button class="ui-button login-button" type="submit" id="logon">登 录</button>
                            </div>
                        </form>
                    </div>

                </div>

            </div>
        </div>

    </div>
</div>
<script src="${JS_PATH }/libs/jquery/jquery/jquery.js"></script>
<script src="${JS_PATH }/libs/jquery-plugin/jquery-watermark/jquery.watermark.min.js"></script>
<script type="text/javascript">
//validate 验证相关
(function($){
    var $userName=$('#userName');
    var $password=$('#password');
    var hasCode=false;
    var $commonTipsBox=$('#J_form_explain');
    $userName.blur(function () {
        //校验用户名
        checkUserName();
    });
    $userName.focus(function(){
        $commonTipsBox.html('').removeClass('ui-form-item-error');
    });
    $password.blur(function () {
        //校验密码
        var checkUserNameResult=checkUserName();
        if(checkUserNameResult){
            checkPassWord();
        }
    });
    $password.focus(function () {
        $commonTipsBox.html('').removeClass('ui-form-item-error');
    });
    //校验用户名
    function checkUserName(){
        var sUserName=$.trim($userName.val());
        var result=true;
        if(sUserName==''){
            var oUserNameTips=$('<div></div>').attr('class','alert alert-danger');
            oUserNameTips.text('请输入登录帐号');
            $commonTipsBox.html(oUserNameTips).addClass('ui-form-item-error');
            result= false;
        }
        return result;
    }
    //校验密码
    function checkPassWord(){
        var sUserName=$.trim($password.val());
        var result=true;
        if(sUserName==''){
            var oUserNameTips=$('<div></div>').attr('class','alert alert-danger');
            oUserNameTips.text('请输入登录密码');
            $commonTipsBox.html(oUserNameTips).addClass('ui-form-item-error');
            result= false;
        }
        return result;
    }
    $('#loginForm').submit(function(){
        var checkUserNameResult=checkUserName();
        var checkPassWordResult=false;
        var checkCodeResult=false;
        if(checkUserNameResult){
            checkPassWordResult=checkPassWord();
        }else{
            return false;
        }
        if(checkPassWordResult){
            checkCodeResult=checkCode();
        }else {
            return false;
        }
        if(!checkCodeResult){
            return false;
        }
    });
})($);
</script>
</body>
</html>