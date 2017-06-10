<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div class="container" style="text-align: center; margin-top: 300px;">            
            <c:if test="${regUser.status eq 'admin' or aditionalAccess eq 'Bookkeeper' or regUser.status eq 'Bookkeeper'}">
                <a href="viewBookkeeper"><button type="button" class="btn btn-primary">Бухгалтер</button></a>
            </c:if>
            <c:if test="${regUser.status eq 'admin' or aditionalAccess eq 'Order acceptor' or regUser.status eq 'Order acceptor'}">
                <a href="order"><button type="button" class="btn btn-primary">Заказы</button></a>
            </c:if>
            <c:if test="${regUser.status eq 'admin' or aditionalAccess eq 'Staff manager' or regUser.status eq 'Staff manager'}">
                <a href="worker"><button type="button" class="btn btn-primary">Работники</button></a>
            </c:if>
            <c:if test="${regUser.status eq 'admin' or aditionalAccess eq 'Desiner' or regUser.status eq 'Desiner'}">
                <a href="models"><button type="button" class="btn btn-primary">Модели</button></a>
            </c:if>
            <c:if test="${regUser.status eq 'admin' or aditionalAccess eq 'Worker' or regUser.status eq 'Worker'}">
                <a href="addWork"><button type="button" class="btn btn-primary">Работа</button></a>
            </c:if>
            <form action="logout">
                <button type="submit" class="btn btn-info" style="margin-top: 10px;">Выход</button>
            </form>

        </div>
    </body>
</html>
