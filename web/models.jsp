<%-- 
    Document   : models
    Created on : Nov 29, 2016, 11:07:07 AM
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
        <title>Модели</title>
    </head>
    <body>
        <section class="container">
            <h1 class="col-sm-offset-5">Модель: </h1>
            <form method="POST" class="col-sm-8 col-sm-offset-2" action="editmodel" id="_editmodel" name="editmodel">
                <div class="form-group">
                    <label for="usr">Наименование модели: </label>                    
                    <div class="row">
                        <div class="col-sm-7">
                            <select class="form-control" required="true" id="_model" name="model" onchange="submit()" >
                                <option disabled selected value="">Выберите модель</option>
                                <c:forEach var="model" items="${models}">
                                    <c:if test="${model.id eq selectedModel.id}">
                                        <option selected="true" value="${model.id}">${model.name}</option>
                                    </c:if>
                                    <c:if test="${model.id ne selectedModel.id}">
                                        <option value="${model.id}">${model.name}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-5">

                            <div class="col-sm-4">
                                <c:if test="${selectedModel.id != null}">  
                                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#deleteModel">Удалить</button>   
                                </c:if>
                            </div>

                            <div class="col-sm-3">
                                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addModelName">Добавить новую</button>
                            </div>
                        </div>



                    </div>                  
                    <br>

                    <%-- таблица составляющих модели --%>

                    <h2 class="col-sm-offset-4">Составляющие: </h2>
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th class="col-sm-2">Серийный номер:</th>
                                <th class="col-sm-2">Стоимость операции: </th>
                                <th class="col-sm-2">Время выполнения: </th>
                                <th class="col-sm-4">Описание: </th>
                                <th class="col-sm-1">Удалить: </th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="part" items="${selectedModel.parts}">                               
                                <tr>
                                    <td class="col-sm-2"><a href='editPart?edit_part_id=${part.id}&selected_model_id=${selectedModel.id}' name='edit_part_id'>${part.serial}</a></td>
                                    <td class="col-sm-2">${part.price}</td>
                                    <td class="col-sm-2">${part.duration}</td>
                                    <td class="col-sm-4">${part.description}</td>                
                                    <td class="col-sm-1"><a href='deletePart?delete_part_id=${part.id}&selected_model_id=${selectedModel.id}' name='delete_part_id'> 
                                            <span class="btn btn-danger btn-sm glyphicon glyphicon-remove" title="Удалить ${part.serial} (id = ${part.id})"></span></a>
                                    </td>
                                </tr>                                    
                            </c:forEach>
                        </tbody>
                    </table>



                    <%-- добавление новой операции --%>
                    <div> 
                        <input type="hidden"  name="part_id" value="${partToEdit.id}">                            

                        <label for="comment">Серийный номер: </label>
                        <input required="true" type="text" class="form-control" id="_part_name" name="part_name" value="${partToEdit.serial}">                        
                        <br>

                        <label for="comment">Описание: </label>
                        <textarea required="true" class="form-control" rows="5" id="_part_description" name="part_description">${partToEdit.description}</textarea>
                        <br>  
                        <div class="row">
                            <div class="col-sm-3">
                                <label for="comment">Стоимость операции: </label>
                                <input required="true" type="text" class="form-control" id="_part_price" name="part_price" value="${partToEdit.price}">
                                <br>
                                <label for="comment">Время выполнения: </label>
                                <input required="true" type="text" class="form-control" id="_part_duration" name="part_duration" value="${partToEdit.duration}">
                            </div>
                        </div>
                    </div>
                    <br>
                </div>

                <c:if test="${partToEdit.id == null}">
                    <input type="submit" class="btn btn-primary" id="save" name="save" value="Сохранить">
                </c:if>
                <c:if test="${partToEdit.id != null}">
                    <input type="submit" class="btn btn-primary" id="update" name="update" value="Обновить">
                </c:if>                              

            </form>
            <c:if test="${regUser.status ne 'admin' and regUser.aditionalAccess eq null or regUser.aditionalAccess eq ''}">
                <form action="logout">
                    <button type="submit" class="btn btn-info" style="float: right">Выход</button>
                </form>
            </c:if>
            <c:if test="${regUser.status eq 'admin' and regUser.aditionalAccess ne null or regUser.aditionalAccess ne ''}">
                <a href="index.jsp" class="btn btn-info" style="float: right">Выход</a>
            </c:if>


        </section>


        <%-- добавление новой модели --%>
        <div class="modal fade" id="addModelName" role="dialog">
            <form method="POST" action="addmodelname" id="_addmodelname" name="addmodelname">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Новая модель: </h4>
                        </div>
                        <div class="modal-body">
                            <label for="comment">Название: </label>
                            <input required="true" type="text" class="form-control" id="_newmodel" name="newmodel">
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary">Добавить</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>

        <%-- удаление выбранной модели --%>
        <div class="modal fade" id="deleteModel" role="dialog">
            <form method="POST" action="deletemodel" id="_deletemodel" name="deletemodel">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Удалить: </h4>
                        </div>
                        <div class="modal-body">
                            <label for="comment">${selectedModel.name}</label>
                            <input type="hidden"  name="selectedModelId" value="${selectedModel.id}">
                        </div>
                        <div class="modal-footer">
                            <input type="submit" class="btn btn-primary" value="Удалить">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>


    </body>
</html>
