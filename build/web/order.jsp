<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script src="resources/bootstrap-3.3.7-dist/js/jquery-3.1.1.min.js" type="text/javascript"></script>
        <script src="resources/bootstrap-3.3.7-dist/js/bootstrap.js" type="text/javascript"></script>
        <link href="resources/bootstrap-3.3.7-dist/css/bootstrap.css" rel="stylesheet" type="text/css"/>
        <link href="resources/css/calendar.css" rel="stylesheet" type="text/css"/>
        <link href="resources/css/order.css" rel="stylesheet" type="text/css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="resources/js/calendar.js" defer></script>
        <title>Order Furniture </title>
    </head>
    <body>
        <div class="container" style="text-align: center">
            <h1>Заказ</h1> 
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
            <form action="create_order" name="create_order" method="POST" class="form-horizontal">
                <div>
                    <div id="date-box">
                        <div class="date-line">
                            Неделя:<br><select  name="selectedWeek" class="form-control" onchange="submit()">
                                <c:forEach var="week" begin="1" end="53">
                                    <c:if test="${week eq selectedWeek}">
                                        <option value="${week}" selected="true">${week}</option>
                                    </c:if>
                                    <c:if test="${week ne selectedWeek}">
                                        <option value="${week}">${week}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="date-line">
                            Месяц:<br><select  name="selectedMonth" class="form-control" onchange="submit()">
                                <c:forEach var="month" begin="1" end="12">
                                    <c:if test="${month eq selectedMonth}">
                                        <option value="${month}" selected="true">${month}</option>
                                    </c:if>
                                    <c:if test="${month ne selectedMonth}">
                                        <option value="${month}">${month}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="date-line">
                            Год:<br><select name="selectedYear" class="form-control" onchange="submit()">
                                <c:forEach var="year" begin="${selectedYear - 1}" end="${selectedYear + 1}">
                                    <c:if test="${year eq selectedYear}">
                                        <option value="${year}" selected="true">${year}</option>
                                    </c:if>
                                    <c:if test="${year ne selectedYear}">
                                        <option value="${year}">${year}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <!--<div id="boxes" style="margin: 0 auto">-->
                    <!--<div id="wrap">-->
                    <!--<div id="boxes-left">-->
                    <div id="order-name-box">
                        <label>Заказ:</label><span id="info">${infoMassage}</span>
                        <input class="form-control" id="order_name" name="order_name" value="${order.name}" type="text">
                        <input type="hidden" name="order_id" value="${order.id}">
                    </div>
                    <div id="block-labels">
                        <div id="label-line">
                            <label id="label-model">Модель:</label>
                            <label id="label-count">Количество:</label>
                        </div>
                    </div>
                    <div class="block-models">
                        <div class="model-select-line">
                            <select class="form-control select-model" name="model_name1" >
                                <option value="">Выберите модель</option>
                                <c:forEach var="model6" items="${models}">
                                    <c:if test="${model6.id eq model1.id}">
                                        <option selected="true" value="${model6.id}">${model6.name}</option>
                                    </c:if>
                                    <c:if test="${model6.id ne model1.id}">
                                        <option value="${model6.id}">${model6.name}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="model-count-line">
                            <input class="form-control input-count" name="model_count1" type="text" value="${model_count1}">
                        </div>
                    </div>
                    <div class="block-models">
                        <div class="model-select-line">
                            <select class="form-control select-model" name="model_name2" >
                                <option value="">Выберите модель</option>
                                <c:forEach var="model7" items="${models}">
                                    <c:if test="${model7.id eq model2.id}">
                                        <option selected="true" value="${model7.id}">${model7.name}</option>
                                    </c:if>
                                    <c:if test="${model7.id ne model2.id}">
                                        <option value="${model7.id}">${model7.name}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="model-count-line">
                            <input class="form-control input-count" name="model_count2" type="text" value="${model_count2}">
                        </div>
                    </div>
                    <div class="block-models">
                        <div class="model-select-line">
                            <select class="form-control select-model" name="model_name3" >
                                <option value="">Выберите модель</option>
                                <c:forEach var="model8" items="${models}">
                                    <c:if test="${model8.id eq model3.id}">
                                        <option selected="true" value="${model8.id}">${model8.name}</option>
                                    </c:if>
                                    <c:if test="${model8.id ne model3.id}">
                                        <option value="${model8.id}">${model8.name}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="model-count-line">
                            <input class="form-control input-count" name="model_count3" type="text" value="${model_count3}">
                        </div>
                    </div>
                    <div class="block-models">
                        <div class="model-select-line">
                            <select class="form-control select-model" name="model_name4" >
                                <option value="">Выберите модель</option>
                                <c:forEach var="model9" items="${models}">
                                    <c:if test="${model9.id eq model4.id}">
                                        <option selected="true" value="${model9.id}">${model9.name}</option>
                                    </c:if>
                                    <c:if test="${model9.id ne model4.id}">
                                        <option value="${model9.id}">${model9.name}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="model-count-line">
                            <input class="form-control input-count" name="model_count4" type="text" value="${model_count4}">
                        </div>
                    </div>
                    <div class="block-models">
                        <div class="model-select-line">
                            <select class="form-control select-model" name="model_name5" >
                                <option value="">Выберите модель</option>
                                <c:forEach var="model10" items="${models}">
                                    <c:if test="${model10.id eq model5.id}">
                                        <option selected="true" value="${model10.id}">${model10.name}</option>
                                    </c:if>
                                    <c:if test="${model10.id ne model5.id}">
                                        <option value="${model10.id}">${model10.name}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="model-count-line">
                            <input class="form-control  input-count" name="model_count5" type="text" value="${model_count5}">
                        </div>
                    </div>
                    <div id="button-box">
                        <input class="btn btn-primary" type="submit" name="add" value="Добавить">
                        <input class="btn btn-primary" type="submit" name="update" value="Обновить">

                    </div>
                    <!--</div>-->
                    <!--<div id="boxes-right">-->
                    <h4>Заказы на указанную дату:</h4>

                    <table class="table-bordered" style="margin: 0 auto">
                        <thead>

                        <th>№</th>
                        <th>Название ордера</th>
                        <th>Удалить</th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="orderl" items="${orders}" varStatus="status">
                                <tr class="success">

                                    <td>${status.getIndex()+1}</td>
                                    <td><a href="load?load_order_id=${orderl.id}">${orderl.name}</a></td>
                                    <td><a href="delete?delete_order_id=${orderl.id}" class="btn btn-danger btn-sm glyphicon glyphicon-remove"></a></td>
                                </tr>
                            </c:forEach>  
                        </tbody>
                    </table> 

                    <!--</div>-->  
                    <!-- слой clear необходим для позиционирования блока boxes-->
                    <!--<div class="clear"></div>-->
                    <!--</div>-->   
                    <!--</div>-->

                </div> 
            </form>
            




        </div>
    </body>
</html>
