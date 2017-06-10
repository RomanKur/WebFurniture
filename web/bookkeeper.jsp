<%-- 
    Document   : bookkeeper
    Created on : Dec 1, 2016, 12:33:49 PM
    Author     : pupil
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script src="resources/bootstrap-3.3.7-dist/js/jquery-3.1.1.min.js" type="text/javascript"></script>
        <script src="resources/bootstrap-3.3.7-dist/js/bootstrap.js" type="text/javascript"></script>
        <link href="resources/bootstrap-3.3.7-dist/css/bootstrap.css" rel="stylesheet" type="text/css"/>
        <link href="resources/css/bookkeeper.css" rel="stylesheet" type="text/css"/>
        <link href="resources/css/calendar.css" rel="stylesheet" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="resources/js/calendar.js" defer></script>
        <title>bookkepper</title>
    </head>
    <body style="margin: 0 auto">

        <div class="container">
            <h1 style="text-align: center">Зарплата</h1>
            <div id="calendar">

                <table id="calendar4" >
                    <thead>
                        <tr><td><td colspan="5" style="padding-right: 10px;">
                                <select class="form-control">
                                    <option value="0">Январь</option>
                                    <option value="1">Февраль</option>
                                    <option value="2">Март</option>
                                    <option value="3">Апрель</option>
                                    <option value="4">Май</option>
                                    <option value="5">Июнь</option>
                                    <option value="6">Июль</option>
                                    <option value="7">Август</option>
                                    <option value="8">Сентябрь</option>
                                    <option value="9">Октябрь</option>
                                    <option value="10">Ноябрь</option>
                                    <option value="11">Декабрь</option>
                                </select><td colspan="3"><input class="form-control" type="number" value="" min="0" max="9999" size="4" style="width: 75px;">
                        <tr><td><td>Пн<td>Вт<td>Ср<td>Чт<td>Пт<td>Сб<td>Вс
                    <tbody></tbody>
                </table>
            </div>
            <c:if test="${regUser.status ne 'admin' and regUser.aditionalAccess eq null or regUser.aditionalAccess eq ''}">
                <form action="logout">
                    <button type="submit" class="btn btn-info" style="float: right">Выход</button>
                </form>
            </c:if>
            <c:if test="${regUser.status eq 'admin' and regUser.aditionalAccess ne null or regUser.aditionalAccess ne ''}">
                <a href="index.jsp" class="btn btn-info" style="float: right">Выход</a>
            </c:if>
            <form method="POST" action="bookkeeper" id="_bookkeeper" name="bookkeeper" class="form-inline" style="margin-bottom: 10px;">
                
                    <div class="form-group">
                        <select class="form-control" name="selectMonth" id="sel1" onchange="submit()">
                            <c:forEach var="i" begin="1" end="12">
                                <c:if test="${i eq montH}">
                                    <option value="${montH}" selected="true">${montH}</option>
                                </c:if>
                                <c:if test="${i ne montH}">
                                    <option value="${i}">${i}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div> 
                    <div class="form-group">
                        <select class="form-control" name="selectYear" id="sel1" onchange="submit()">
                                <c:forEach var="i" begin="${yeaR - 1}" end="${yeaR + 1}">
                                    <c:if test="${i eq yeaR}">
                                        <option value="${yeaR}" selected="true">${yeaR}</option>
                                    </c:if>
                                    <c:if test="${i ne yeaR}">
                                        <option value="${i}">${i}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                    </div>              
                
                <div class="form-group">
                    <select class="form-control" name="workerId" onchange="submit()">
                        <option value="">Выберите работника</option>
                        <c:forEach var="worker" items="${workers}">
                            <c:if test="${worker.id eq selectWorker.id}">
                                <option value="${worker.id}" selected="true">${worker.lastname} ${worker.firstname}</option>
                            </c:if>
                            <c:if test="${worker.id ne selectWorker.id}">
                                <option value="${worker.id}">${worker.lastname} ${worker.firstname}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
                
            </form>
            

            <c:if test="${selectWorker.id ne null}">
                
                <table class="table-bordered" style="margin: 0 auto; margin-bottom: 10px;">
                    <thead>
                        <tr>
                            <th style="padding: 10px">Неделя</th>
                            <th style="padding: 10px">Месяц</th>
                            <th style="padding: 10px">Год</th>
                            <th style="padding: 10px">Номер заказа</th>
                            <th style="padding: 10px">Модель</th>
                            <th style="padding: 10px">Компонент</th>
                            <th style="padding: 10px">Количество</th>
                            <th style="padding: 10px">Плата(за 1 шт.)</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="doneWork" items="${doneWorks}">
                            <tr>
                                <td style="padding: 10px">${doneWork.week}</td>
                                <td style="padding: 10px">${doneWork.month}</td>
                                <td style="padding: 10px">${doneWork.year}</td>
                                <td style="padding: 10px">${doneWork.orderId}</td>
                                <td style="padding: 10px">${doneWork.modelId}</td>
                                <td style="padding: 10px">${doneWork.partId}</td>
                                <td style="padding: 10px">${doneWork.done}</td>
                                <td style="padding: 10px">${doneWork.price}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <h4>Заработано: ${profit} €</h4>
                </c:if>
                
        </div>
    </body>
</html>
