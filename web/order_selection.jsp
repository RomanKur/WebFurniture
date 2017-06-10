<%-- 
    Document   : order_selection
    Created on : Dec 1, 2016, 12:29:19 PM
    Author     : pupil
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <title>Выбор заказа</title>
    </head>
    <body>
        <div class="container">
            <h1 style="text-align: center">Выбор заказа</h1>

            <table id="calendar4">
                <thead>
                    <tr><td><td colspan="4"><select class="form-control">
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
                            </select><td colspan="3"><input class="form-control" type="number" value="" min="0" max="9999" size="4">
                    <tr><td><td>Пн<td>Вт<td>Ср<td>Чт<td>Пт<td>Сб<td>Вс
                <tbody>
            </table>
            <div style="color: red;text-align: center;">
                <c:out value="${message}" default="" escapeXml="true" />
            </div>
            <c:if test="${regUser.status ne 'admin' and regUser.aditionalAccess eq null or regUser.aditionalAccess eq ''}">
                <form action="logout">
                    <button type="submit" class="btn btn-info" style="float: right">Выход</button>
                </form>
            </c:if>
            <c:if test="${regUser.status eq 'admin' and regUser.aditionalAccess ne null or regUser.aditionalAccess ne ''}">
                <a href="index.jsp" class="btn btn-info" style="float: right">Выход</a>
            </c:if>
            <form action="addWork" method="POST">
                <table style="margin: 0 auto">
                    <div class="form-inline" style="text-align: center; margin-bottom: 10px;">
                        <div class="form-group">
                            <label for="week" style="margin-right: 5px;">Неделя</label>
                            <select class="form-control" name="currentWeek" style="width: 100px; margin-right: 5px;" onchange="submit()">
                                <c:forEach var="i" begin="1" end="53">
                                    <c:if test="${i eq week}">
                                        <option value="${week}" selected="true">${week}</option>
                                    </c:if>
                                    <c:if test="${i ne week}">
                                        <option value="${i}">${i}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="month" style="margin-right: 5px;">Месяц</label>
                            <select class="form-control" name="currentMonth" style="width: 100px; margin-right: 5px;" onchange="submit()">
                                <c:forEach var="i" begin="1" end="12">
                                    <c:if test="${i eq month}">
                                        <option value="${month}" selected="true">${month}</option>
                                    </c:if>
                                    <c:if test="${i ne month}">
                                        <option value="${i}">${i}</option>
                                    </c:if>
                                </c:forEach>
                            </select>

                        </div>
                        <div class="form-group">
                            <label for="year" style="margin-right: 5px;">Год</label>
                            <select class="form-control" name="currentYear" style="width: 100px; margin-right: 5px;" onchange="submit()">
                                <c:forEach var="i" begin="${year - 1}" end="${year + 1}">
                                    <c:if test="${i eq year}">
                                        <option value="${year}" selected="true">${year}</option>
                                    </c:if>
                                    <c:if test="${i ne year}">
                                        <option value="${i}">${i}</option>
                                    </c:if>
                                </c:forEach>
                            </select>

                        </div>
                    </div>

                    <tr class="form-group" style="text-align: center">
                        <td>
                            <label for="orderId" style="padding-right: 10px;">Заказ</label>
                        </td>
                        <td>
                            <select class="form-control" name="orderId" style="width: 250px"  onchange="submit()">
                                <option value="">Не выбрано</option>
                                <c:forEach var="ord" items="${orders}">
                                    <c:if test="${ord.id eq selectedOrder.id}">
                                        <option value="${ord.id}" selected="true">${ord.name}</option>
                                    </c:if>
                                    <c:if test="${ord.id ne selectedOrder.id}">
                                        <option value="${ord.id}">${ord.name}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr class="form-group" style="text-align: center">
                        <td>
                            <label for="modelId" style="padding-right: 10px;">Модель</label>
                        </td>
                        <td>
                            <select class="form-control" name="modelId" style="width: 250px" onchange="submit()">
                                <option value="">Не выбрано</option>
                                <c:forEach var="mode" items="${models}">
                                    <c:if test="${mode.id eq selectModel.id}">
                                        <option value="${mode.id}" selected="true">${mode.name}</option>
                                    </c:if>
                                    <c:if test="${mode.id ne selectModel.id}">
                                        <option value="${mode.id}">${mode.name}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr class="form-group" style="text-align: center">
                        <td>
                            <label for="operationId" style="padding-right: 10px;">Деталь</label>
                        </td>
                        <td>
                            <select class="form-control" name="operationId" id="operation" onchange="submit()" style="width: 250px" >
                                <option value="">Не выбрано</option>
                                <c:forEach var="part" items="${selectModel.parts}">
                                    <c:if test="${part.id eq selectedPart.id}">
                                        <option value="${part.id}" selected="true">${part.description}</option>
                                    </c:if>
                                    <c:if test="${part.id ne selectedPart.id}">
                                        <option value="${part.id}">${part.description}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr class="form-group" style="text-align: center">
                        <td>
                            <label for="operationId" style="padding-right: 10px;">Номер </label>
                        </td>
                        <td>
                            <select class="form-control" name="quantity" style="width: 250px" >
                                <option value="">Не выбрано</option>
                                <c:forEach var="ammount" items="${ammounts}">
                                    <option value="${ammount}" >${ammount}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td style="text-align: center">
                            <c:if test="${selectedWorker eq null}">
                                <button name="add" type="submit" class="btn btn-primary" style="margin-top: 10px;">Обновить</button>
                            </c:if>
                            <c:if test="${selectedWorker ne null}">
                                <button name="add" type="submit" class="btn btn-primary" style="margin-top: 10px;">Записать</button>
                            </c:if>

                        </td>
                    </tr>
                </table>
            </form>


            <c:if test="${selectedWorker.id ne null}">
                <h4>Заработано: ${profiT} €</h4>
                <table class="table-bordered" style="margin: 0 auto">
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
                            <th style="padding: 10px">Удалить</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="workDone" items="${worksDone}">
                            <tr>
                                <td style="padding: 10px">${workDone.week}</td>
                                <td style="padding: 10px">${workDone.month}</td>
                                <td style="padding: 10px">${workDone.year}</td>
                                <td style="padding: 10px">${workDone.order}</td>
                                <td style="padding: 10px">${workDone.modelName}</td>
                                <td style="padding: 10px">${workDone.partName}</td>
                                <td style="padding: 10px">${workDone.done}</td>
                                <td style="padding: 10px">${workDone.price}</td>
                                <td style="text-align: center;">
                                    <form action="deleteWork" method="post">
                                        <input name="doneWorkId" hidden="true" value="${workDone.id}">
                                        <button type="submit" class="btn btn-danger btn-sm glyphicon glyphicon-remove"></button>
                                    </form>

                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </body>
    <style>
        #calendar4 {
            margin: 0 auto;
            width: 25%;
            line-height: 1.2em;
            font-size: 15px;
            text-align: center;
        }
        #calendar4 thead tr:last-child {
            font-size: small;
            font-weight: 700;
            color: black;
        }
        #calendar4 tbody td {
            color: #337ab7;
        }
        #calendar4 tbody td:nth-child(1) {
            font-size: small;
            color: black;
        }
        #calendar4 tbody td:nth-child(n+7), #calendar4 .holiday {
            color: red;
        }
        #calendar4 tbody td.today {
            outline: 3px solid red;
        }
    </style>

    <script>
        function Calendar4(id, year, month) {

            Date.prototype.getWeek = function () {
                var target = new Date(this.valueOf());
                var dayNr = (this.getDay() + 6) % 7;
                target.setDate(target.getDate() - dayNr + 3);
                var firstThursday = target.valueOf();
                target.setMonth(0, 1);
                if (target.getDay() != 4) {
                    target.setMonth(0, 1 + ((4 - target.getDay()) + 7) % 7);
                }
                return 1 + Math.ceil((firstThursday - target) / 604800000);
            }

            var Dlast = new Date(year, parseFloat(month) + 1, 0).getDate(),
                    D = new Date(year, month, Dlast),
                    DNlast = D.getDay(),
                    DNfirst = new Date(D.getFullYear(), D.getMonth(), 1).getDay(),
                    m = document.querySelector('#' + id + ' option[value="' + D.getMonth() + '"]'),
                    g = document.querySelector('#' + id + ' input');

            if (new Date(D.getFullYear(), D.getMonth(), 1).getWeek() < 10) {
                calendar = '<tr><td>0' + new Date(D.getFullYear(), D.getMonth(), 1).getWeek();
            } else {
                calendar = '<tr><td>' + new Date(D.getFullYear(), D.getMonth(), 1).getWeek();
            }

            if (DNfirst != 0) {
                for (var i = 1; i < DNfirst; i++)
                    calendar += '<td>';
            } else {
                for (var i = 0; i < 6; i++)
                    calendar += '<td>';
            }

            for (var i = 1; i <= Dlast; i++) {
                if (i == new Date().getDate() && D.getFullYear() == new Date().getFullYear() && D.getMonth() == new Date().getMonth()) {
                    calendar += '<td class="today">' + i;
                } else {
                    if (
                            (i == 1 && D.getMonth() == 0 && ((D.getFullYear() > 1897 && D.getFullYear() < 1930) || D.getFullYear() > 1947)) ||
                            (i == 2 && D.getMonth() == 0 && D.getFullYear() > 1992) ||
                            ((i == 3 || i == 4 || i == 5 || i == 6 || i == 8) && D.getMonth() == 0 && D.getFullYear() > 2004) ||
                            (i == 7 && D.getMonth() == 0 && D.getFullYear() > 1990) ||
                            (i == 23 && D.getMonth() == 1 && D.getFullYear() > 2001) ||
                            (i == 8 && D.getMonth() == 2 && D.getFullYear() > 1965) ||
                            (i == 1 && D.getMonth() == 4 && D.getFullYear() > 1917) ||
                            (i == 9 && D.getMonth() == 4 && D.getFullYear() > 1964) ||
                            (i == 12 && D.getMonth() == 5 && D.getFullYear() > 1990) ||
                            (i == 7 && D.getMonth() == 10 && (D.getFullYear() > 1926 && D.getFullYear() < 2005)) ||
                            (i == 8 && D.getMonth() == 10 && (D.getFullYear() > 1926 && D.getFullYear() < 1992)) ||
                            (i == 4 && D.getMonth() == 10 && D.getFullYear() > 2004)
                            ) {
                        calendar += '<td class="holiday">' + i;
                    } else {
                        calendar += '<td>' + i;
                    }
                }
                if (new Date(D.getFullYear(), D.getMonth(), i).getDay() == 0 && i != Dlast) {
                    if (new Date(D.getFullYear(), D.getMonth(), i).getWeek() < 9) {
                        calendar += '<tr><td>0' + new Date(D.getFullYear(), D.getMonth(), i + 1).getWeek();
                    } else {
                        calendar += '<tr><td>' + new Date(D.getFullYear(), D.getMonth(), i + 1).getWeek();
                    }
                }
            }

            if (DNlast != 0) {
                for (var i = DNlast; i < 7; i++)
                    calendar += '<td>';
            }

            document.querySelector('#' + id + ' tbody').innerHTML = calendar;
            g.value = D.getFullYear();
            m.selected = true;

            if (document.querySelectorAll('#' + id + ' tbody tr').length < 6) {
                document.querySelector('#' + id + ' tbody').innerHTML += '<tr><td>&nbsp;<td><td><td><td><td><td><td>';
            }

            document.querySelector('#' + id + ' option[value="' + new Date().getMonth() + '"]').style.color = 'rgb(220, 0, 0)';

        }

        Calendar4("calendar4", new Date().getFullYear(), new Date().getMonth());
        document.querySelector('#calendar4').onchange = function Kalendar4() {
            Calendar4("calendar4", document.querySelector('#calendar4 input').value, document.querySelector('#calendar4 select').options[document.querySelector('#calendar4 select').selectedIndex].value);
        }
    </script>
</html>
