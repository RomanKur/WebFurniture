<%-- 
    Document   : login
    Created on : May 10, 2017, 10:58:43 AM
    Author     : pupil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <title>Login</title>
    </head>
    <body class="container">
        <h2 style="text-align: center; margin-top: 200px">Вход</h2>
        <form method="post" action="login" class="form-horizontal" style="margin: 0 auto; width: 500px;">
            <div class="form-group">
                <label class="control-label col-sm-2" for="login">Логин</label>
                <div class="col-sm-10">
                    <input id="login" type="text" name="logiN" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2"for="pwd">Пароль</label>
                <div class="col-sm-10">
                    <input id="pwd" name="password" type="password" class="form-control">
                </div>
            </div>
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-primary">Войти</button>
            </div>
        </form>
    </body>

</html>
