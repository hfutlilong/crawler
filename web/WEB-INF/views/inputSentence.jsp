<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>输入一段文字</title>
</head>
<body>
<h1>输入一段文字</h1>

<form action="getKeyworks.do" method="post">
    <table>
        <tr>
            <td><textarea id="sentence" name="sentence" rows="30" cols="120" autofocus placeholder="输入一段文字..."></textarea></td>
        </tr>

        <tr>
            <td colspan="2"><input type="submit" value="提交" /></td>
        </tr>
    </table>
</form>
</body>
</html>