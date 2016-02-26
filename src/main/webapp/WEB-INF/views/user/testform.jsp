<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
1、卖家用户注册
<form action="/rest/user/create" method="post">
卖家名字：<input name="user_name" /> <br />
卖家密码：<input name="pass_word" /> <br />
卖家邮箱<input name="email" /><br />
<input type="submit" value="卖家用户注册">
</form><br />

2、更新卖家邮箱
<form action="/rest/user/updateEmail" method="post">
卖家名字：<input name="user_name" /> <br />
卖家密码：<input name="pass_word" /> <br />
卖家邮箱<input name="email" /><br />
<input type="submit" value="更新卖家邮箱">
</form><br />

3、更新卖家套餐
<form action="/rest/user/updatePackage" method="post">
卖家名字：<input name="user_name" /> <br />
卖家邮箱：<input name="email" /> <br />
卖家更新套餐ID：<input name="package_id" /><br />
<input type="submit" value="更新卖家套餐">
</form><br />

4、查询卖家套餐使用情况
<form action="/rest/user/showUserPackage" method="post">
卖家名字：<input name="user_name" /> <br />
卖家邮箱：<input name="email" /> <br />
<input type="submit" value="查询卖家套餐使用情况">
</form><br />

5、套餐使用（1、用户名；2、邮箱；3、套餐规则ID）
<form action="/rest/user/useUserPackage" method="get">
卖家名字：<input name="user_name" /> <br />
卖家邮箱：<input name="email" /> <br />
卖家套餐规则ID：<input name="package_rule_id" /><br />
<input type="submit" value="更新卖家套餐">
</form><br />

6、系统间登陆（1、用户名；2、邮箱；3、系统机密参数（（用户名 + 用户邮箱 + 字符串'traditionsys')MD5））
<form action="/user/syslogin" method="post">
卖家名字：<input name="user_name" /> <br />
卖家邮箱：<input name="email" /> <br />
系统Code：<input name="sysCode" /><br />
<input type="submit" value="系统登陆">
</form><br />

</body>
</html>