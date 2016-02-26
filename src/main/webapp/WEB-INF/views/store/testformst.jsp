<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
1、获取用户商铺（在线）及商铺下产品
<form action="/rest/store/showStore" method="post">
卖家ID：<input name="user_id" /> <br />
<input type="submit" value="查询店铺">
</form><br />

2、获取partner用户商铺（在线）及商铺下产品
<form action="/rest/store/newShowStore" method="post">
卖家ID：<input name="user_id" /> <br />
<input type="submit" value="查询店铺">
</form><br />
</body>
</html>