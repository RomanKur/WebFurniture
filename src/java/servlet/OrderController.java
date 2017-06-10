/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import entities.Model;
import entities.OrderDate;
import entities.OrderFurniture;
import entities.Worker;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.ModelFacade;
import session.OrderFurnitureFacade;
import util.DateMyFormat;

/**
 *
 * @author Roman Kurtsanov
 * Java котроллер обеспечивающий работоспосбность страницы приёма, редактирования и удаления заказов.
 */
@WebServlet(name = "OrderController", urlPatterns = {"/order", "/create_order", "/load", "/delete"})
public class OrderController extends HttpServlet {

    @EJB
    OrderFurnitureFacade orderFurnitureFacade;
    @EJB
    ModelFacade modelFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Worker loginWorker = (Worker) request.getSession().getAttribute("regUser");
        if (loginWorker != null &&("Order acceptor".equals(loginWorker.getStatus()) || "admin".equals(loginWorker.getStatus()) || "Order acceptor".equals(loginWorker.getAditionalAccess()))) {
            response.setContentType("text/html;charset=UTF-8");
            String msg = ""; // Информационное сообщение, которое будет выводится на страничке
            DateMyFormat dateMyFormat = new DateMyFormat();
            List<String> curentDate = new ArrayList<>();
            curentDate.add(dateMyFormat.getCurentWeek().toString());
            curentDate.add(dateMyFormat.getCurentMonth().toString());
            curentDate.add(dateMyFormat.getCurentYear().toString());
            String userPath = request.getServletPath();
            int week = Integer.parseInt(curentDate.get(0));
            int month = Integer.parseInt(curentDate.get(1));
            int year = Integer.parseInt(curentDate.get(2));
            if ("/order".equals(userPath)) {
                getServletContext().setAttribute("selectedWeek", curentDate.get(0));
                getServletContext().setAttribute("selectedMonth", curentDate.get(1));
                getServletContext().setAttribute("selectedYear", curentDate.get(2));
                getServletContext().setAttribute("orders", orderFurnitureFacade.ordersByDate(dateMyFormat.getCurentWeek(), dateMyFormat.getCurentMonth(), dateMyFormat.getCurentYear()));
                getServletContext().setAttribute("models", modelFacade.findAll());
                request.getRequestDispatcher("order.jsp").forward(request, response);
            } else if ("/create_order".equals(userPath)) {
                for (int i = 1; i < 6; i++) {
                    getServletContext().removeAttribute("model_count" + i);
                    getServletContext().removeAttribute("model" + i);
                }
                getServletContext().removeAttribute("order");
                getServletContext().removeAttribute("order_name");
                if (week != 0 && month != 0 && year != 0) {

                    Integer currentWeek = 0;
                    Integer currentYear = 0;
                    Integer currentMonth = 0;
                    if (request.getParameter("selectedWeek") != null) {
                        currentWeek = Integer.parseInt(request.getParameter("selectedWeek"));
                    }
                    if (week != currentWeek && currentWeek != 0) {
                        week = currentWeek;
                    }

                    if (request.getParameter("selectedMonth") != null) {
                        currentMonth = Integer.parseInt(request.getParameter("selectedMonth"));
                    }
                    if (month != currentMonth && currentMonth != 0) {
                        month = currentMonth;
                    }

                    if (request.getParameter("selectedYear") != null) {
                        currentYear = Integer.parseInt(request.getParameter("selectedYear"));
                    }
                    if (year != currentYear && currentYear != 0) {
                        year = currentYear;
                    }

                    getServletContext().setAttribute("orders", orderFurnitureFacade.ordersByDate(week, month, year));

                    getServletContext().removeAttribute("selectedWeek");
                    getServletContext().setAttribute("selectedWeek", week);
                    getServletContext().removeAttribute("selectedMonth");
                    getServletContext().setAttribute("selectedMonth", month);
                    getServletContext().removeAttribute("selectedYear");
                    getServletContext().setAttribute("selectedYear", year);
                }

                if (request.getParameter("add") != null && request.getParameter("order_name") != null && request.getParameter("model_name1") != null) {
//            Получаем данные из формы
                    String orderName = request.getParameter("order_name");
                    Map<Long, Integer> models = new HashMap<>();
                    Model model = new Model();
                    for (Integer i = 1; i < 6; i++) {
                        if (!"".equals(request.getParameter("model_name" + i.toString()))) {
                            model = modelFacade.find(Long.decode(request.getParameter("model_name" + i.toString())));
                            models.put(model.getId(), Integer.decode(request.getParameter("model_count" + i.toString())));
                        }
                    }
//              Инициируем дату выполнения ордера
                    OrderDate orderDate = new OrderDate();
                    orderDate.setWeek(week);
                    orderDate.setMonth(month);
                    orderDate.setYear(year);
//                Инициируем заказ
                    OrderFurniture orderFurniture = new OrderFurniture();
                    orderFurniture.setModels(models);
                    orderFurniture.setName(orderName);
                    orderFurniture.setOrderDate(orderDate);
                    try {
                        orderFurnitureFacade.create(orderFurniture);
                        msg = "Ордер добавлен.";
                    } catch (Exception e) {
                        msg = "Ордер добавть не удалось.";
                        request.setAttribute("infoMassage", msg);
                        getServletContext().removeAttribute("orders");
                        getServletContext().setAttribute("selectedWeek", curentDate.get(0));
                        getServletContext().setAttribute("selectedMonth", curentDate.get(1));
                        getServletContext().setAttribute("selecteYear", curentDate.get(2));
                        getServletContext().setAttribute("orders", orderFurnitureFacade.oderByToday());
                        getServletContext().setAttribute("models", modelFacade.findAll());
                        request.getRequestDispatcher("/order.jsp").forward(request, response);
                    }

                    request.setAttribute("infoMassage", msg);
                    getServletContext().removeAttribute("orders");
                    getServletContext().setAttribute("selectedWeek", curentDate.get(0));
                    getServletContext().setAttribute("selectedMonth", curentDate.get(1));
                    getServletContext().setAttribute("selecteYear", curentDate.get(2));
                    getServletContext().setAttribute("orders", orderFurnitureFacade.oderByToday());
                    getServletContext().setAttribute("models", modelFacade.findAll());
                }
                if (request.getParameter("update") != null && request.getParameter("order_id") != null) {
                    String orderId = request.getParameter("order_id");
                    OrderFurniture orderFurniture = orderFurnitureFacade.find(new Long(orderId));
                    String orderName = request.getParameter("order_name");
                    Map<Long, Integer> models = new HashMap<>();
                    Model model = new Model();
                    for (Integer i = 1; i < 6; i++) {
                        if (!"".equals(request.getParameter("model_name" + i.toString()))) {
                            model = modelFacade.find(Long.decode(request.getParameter("model_name" + i.toString())));
                            models.put(model.getId(), Integer.decode(request.getParameter("model_count" + i.toString())));
                        }
                    }
//              Инициируем дату выполнения ордера
                    OrderDate orderDate = orderFurniture.getOrderDate();
                    orderDate.setWeek(week);
                    orderDate.setMonth(month);
                    orderDate.setYear(year);
                    orderFurniture.setModels(models);
                    orderFurniture.setName(orderName);
                    orderFurniture.setOrderDate(orderDate);
                    orderFurnitureFacade.edit(orderFurniture);
                    getServletContext().setAttribute("orders", orderFurnitureFacade.ordersByDate(week, month, year));
                    getServletContext().removeAttribute("order");
                    for (int i = 0; i < 6; i++) {
                        Integer modelNo = i + 1;
                        Integer modelN = i + 6;
                        getServletContext().removeAttribute("model_count" + modelNo.toString());
                        getServletContext().removeAttribute("model" + modelNo.toString());
                        getServletContext().removeAttribute("model" + modelN.toString());
                    }

                }
                request.getRequestDispatcher("/order.jsp").forward(request, response);
            } else if ("/load".equals(userPath)) {
                try {
                    //getServletContext().removeAttribute("order");
                    String orderId = request.getParameter("load_order_id");
                    OrderFurniture orderFurniture = orderFurnitureFacade.find(Long.decode(orderId));
                    getServletContext().setAttribute("order", orderFurniture);
                    curentDate.clear();
                    curentDate.add(orderFurniture.getOrderDate().getWeek().toString());
                    curentDate.add(orderFurniture.getOrderDate().getMonth().toString());
                    curentDate.add(orderFurniture.getOrderDate().getYear().toString());
                    getServletContext().setAttribute("selectedWeek", curentDate.get(0));
                    getServletContext().setAttribute("selectedMonth", curentDate.get(1));
                    getServletContext().setAttribute("selecteYear", curentDate.get(2));
                    getServletContext().setAttribute("week", orderFurniture.getOrderDate().getWeek());
                    getServletContext().setAttribute("month", orderFurniture.getOrderDate().getMonth());
                    getServletContext().setAttribute("year", orderFurniture.getOrderDate().getYear());
                    List<Model> models = new ArrayList<>();
                    List<Long> modelIds = new ArrayList<>(orderFurniture.getModels().keySet());
                    List<Integer> modelQuantities = new ArrayList<>(orderFurniture.getModels().values());
                    for (int i = 0; i < orderFurniture.getModels().size(); i++) {

                        Long modelId = 0L;
                        Integer modelNo = i + 1;
                        Integer modelN = i + 6;
                        if (request.getParameter("model_name" + modelNo.toString()) != null) {
                            modelId = Long.parseLong(request.getParameter("model_name" + modelNo.toString()));
                        }
                        if (modelId != modelIds.get(i) && modelId != 0) {
                            modelId = modelIds.get(i);
                        }
                        Model model = modelFacade.find(modelIds.get(i));
                        models.add(model);
                        getServletContext().setAttribute("model_count" + modelNo.toString(), modelQuantities.get(i));
                        getServletContext().setAttribute("model" + modelNo.toString(), model);
                        getServletContext().setAttribute("model" + modelN.toString(), model);
                    }
                    request.getRequestDispatcher("/order.jsp").forward(request, response);

                } catch (Exception e) {
                    msg = "Заказ не был прочитан";
                    request.getRequestDispatcher("/order.jsp").forward(request, response);
                }

            } else if ("/delete".equals(userPath)) {
                OrderFurniture orderFurniture = null;
                try {
                    String orderId = request.getParameter("delete_order_id");
                    orderFurniture = orderFurnitureFacade.find(Long.decode(orderId));
                    orderFurnitureFacade.remove(orderFurniture);
                    msg = "Ордер " + orderFurniture.getName() + " удален!";
                    request.setAttribute("infoMassage", msg);
                    getServletContext().removeAttribute("order");
                    getServletContext().setAttribute("selectedWeek", curentDate.get(0));
                    getServletContext().setAttribute("selectedMonth", curentDate.get(1));
                    getServletContext().setAttribute("selecteYear", curentDate.get(2));
                    getServletContext().setAttribute("orders", orderFurnitureFacade.oderByToday());
                    getServletContext().setAttribute("models", modelFacade.findAll());
                    request.getRequestDispatcher("/order.jsp").forward(request, response);
                } catch (Exception e) {
                    msg = "Ордер " + orderFurniture.getName() + " удалить не удалось";
                    request.setAttribute("infoMassage", msg);
                    getServletContext().removeAttribute("orders");
                    getServletContext().setAttribute("selectedWeek", curentDate.get(0));
                    getServletContext().setAttribute("selectedMonth", curentDate.get(1));
                    getServletContext().setAttribute("selecteYear", curentDate.get(2));
                    getServletContext().setAttribute("orders", orderFurnitureFacade.oderByToday());
                    getServletContext().setAttribute("models", modelFacade.findAll());
                    request.getRequestDispatcher("/order.jsp").forward(request, response);
                }
            }
        }else {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
