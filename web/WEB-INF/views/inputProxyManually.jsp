<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>输入代理ip</title>
</head>
<body>
<h1>输入代理ip地址：</h1>

<form action="updateProxyManually.do" method="post">
    <table>
        <tr>
<%--            <td>代理ip地址：</td>--%>
            <td><textarea id="proxyIps" name="proxyIps" rows="30" cols="120" autofocus placeholder="输入ip地址"></textarea></td>
        </tr>

        <tr>
            <td colspan="2"><input type="submit" value="提交" /></td>
        </tr>
    </table>
</form>
</body>
</html>