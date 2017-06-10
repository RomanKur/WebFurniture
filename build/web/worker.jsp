<%-- 
    Document   : workers
    Created on : Dec 2, 2016, 9:19:45 AM
    Author     : pupil
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
    <head>
        <script src="resources/bootstrap-3.3.7-dist/js/jquery-3.1.1.min.js" type="text/javascript"></script>
        <script src="resources/bootstrap-3.3.7-dist/js/bootstrap.js" type="text/javascript"></script>
        <link href="resources/bootstrap-3.3.7-dist/css/bootstrap.css" rel="stylesheet" type="text/css"/>
        <link href="resources/css/worker.css" rel="stylesheet" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Работники</title>
    </head>
    <body>

        <div class="container">
            <h1>Данные работника</h1>

            <div id="info">
                <c:out value="${infoMassage}" default="" escapeXml="true" />
            </div>


            <form class="form-horizontal" action="forWorker">   
                <div class="form-group">
                    <label class="control-label col-sm-2" for="firstname">Имя:</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="_firstname" name="firstname" value="${worker.firstname}" required>
                        <input type="hidden" id="_id" name="id" value="${worker.id}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="lastname">Фамилия:</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="_lastname" name="lastname" value="${worker.lastname}" required>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="isikukood">Isikukood:</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="_isikukood" name="isikukood" maxlength="11" value="${worker.isikukood}" required>
                    </div>   
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="mail">Емаил:</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="_mail" name="mail" value="${worker.mail}" required>
                    </div>   
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="telephon">Телефон:</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="_telephon" name="telephon" value="${worker.telephon}" required>
                    </div>
                </div>
                <c:if test="${regUser.status eq 'admin'}">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="status">Статус:</label>
                        <div class="col-sm-10">
                            <select class="form-control" name="status" >
                                <option value="">Не выбрано</option>
                                <c:forEach var="status" items="${statuses}">
                                    <c:if test="${status eq selectedStatus}">
                                        <option value="${status}" selected="true">${status}</option>
                                    </c:if>
                                    <c:if test="${status ne selectedStatus}">
                                        <option value="${status}">${status}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </c:if>
                <c:if test="${regUser.status ne 'admin'}">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="status">Статус:</label>
                        <div class="col-sm-10">
                            <select class="form-control" name="status" >
                                <option value="">Не выбрано</option>
                                <c:forEach var="status" items="${statuses}">
                                    <c:if test="${status ne 'admin'}">
                                        <c:if test="${status eq selectedStatus}">
                                            <option value="${status}" selected="true">${status}</option>
                                        </c:if>
                                        <c:if test="${status ne selectedStatus}">
                                            <option value="${status}">${status}</option>
                                        </c:if>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </c:if>
                <c:if test="${worker.id ne null and regUser.status eq 'admin'}">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="password" >Пароль: </label>
                        <div class="col-sm-10">
                            <input name="password" type="password" class="form-control" >
                        </div>
                    </div>
                </c:if>
                <c:if test="${regUser.status eq 'admin' and worker.id ne null and worker.status ne 'admin'}">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="access" >Дополн. доступ: </label>
                        <div class="col-sm-10">
                            <select class="form-control" name="access" >
                                <option value="">Не выбрано</option>
                                <c:forEach var="access" items="${accesses}">
                                    <c:if test="${access eq selectedAccess}">
                                        <option value="${access}" selected="true">${access}</option>
                                    </c:if>
                                    <c:if test="${access ne selectedAccess}">
                                        <option value="${access}">${access}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </c:if>
                <div class="form-group" style="float: left; margin-left: 50px;">        
                    <div class="col-sm-offset-2 col-sm-10">
                        <c:if test="${worker.id eq null}">
                            <button type="submit" class="btn btn-primary" id="_add" name="add">Добавить</button>
                        </c:if>
                        <c:if test="${worker.id ne null}">
                            <button type="submit" class="btn btn-primary" id="_update" name="update" style="margin-bottom: 5px;">Изменить</button>
                            <button type="submit" class="btn btn-primary" id="_remove" name="remove">Удалить</button>
                        </c:if>



                    </div>
                </div>
            </form>
            <c:if test="${regUser.status ne 'admin' and regUser.aditionalAccess eq null or regUser.aditionalAccess eq ''}">
                <form action="logout">
                    <button type="submit" class="btn btn-info" style="float: right">Выход</button>
                </form>
            </c:if>
            <c:if test="${regUser.status eq 'admin' and regUser.aditionalAccess ne null or regUser.aditionalAccess ne ''}">
                <a href="index.jsp" class="btn btn-info" style="float: right">Выход</a>
            </c:if>
            <c:if test="${regUser.status eq 'admin'}">
                <form action="selectWorker" method="POST">
                    <div class="form-group">
                        <select class="form-control" id="_selectWorker" name="selectWorker" onchange="submit(this)">
                            <option value="#">Выберите работника</option>
                            <c:forEach var="worker" items="${workers}">
                                <option value="${worker.id}">${worker.firstname} ${worker.lastname}</option> 
                            </c:forEach>
                        </select>
                    </div>
                </form>
            </c:if>
            <c:if test="${regUser.status ne 'admin'}">
                <form action="selectWorker" method="POST">
                    <div class="form-group">
                        <select class="form-control" id="_selectWorker" name="selectWorker" onchange="submit(this)">
                            <option value="#">Выберите работника</option>
                            <c:forEach var="worker" items="${workers}">
                                <c:if test="${worker.firstname ne 'admin' and worker.lastname ne 'admin'}">
                                    <option value="${worker.id}">${worker.firstname} ${worker.lastname}</option> 
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                </form>
            </c:if>



        </div>

    </body>
</html>


<!--value="worker?id=-->