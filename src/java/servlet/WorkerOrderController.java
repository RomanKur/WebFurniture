/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import entities.*;
import java.io.IOException;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.DoneWorkFacade;
import session.ModelFacade;
import session.OrderDateFacade;
import session.OrderFurnitureFacade;
import session.PartFacade;
import session.WorkerFacade;
import util.DateMyFormat;
import util.StringDoneWork;

/**
 *
 * @author Roman Kurtsanov Java контроллер обеспечивающи работоспособность
 * страницы выбора и удаления работы рядовым работником.
 */
@WebServlet(name = "WorkerOrderController", urlPatterns = {"/addWork", "/deleteWork"})
public class WorkerOrderController extends HttpServlet {

    @EJB
    ModelFacade modelFacade;
    @EJB
    PartFacade partFacade;
    @EJB
    OrderFurnitureFacade orderFacade;
    @EJB
    OrderDateFacade orderDateFacade;
    @EJB
    DoneWorkFacade doneWorkFacade;
    @EJB
    WorkerFacade workerFacade;

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

        response.setContentType("text/html;charset=UTF-8");
        Worker loginWorker = (Worker) request.getSession().getAttribute("regUser");
        if (loginWorker != null && ("Worker".equals(loginWorker.getStatus()) || "admin".equals(loginWorker.getStatus()) || "Worker".equals(loginWorker.getAditionalAccess()))) {
            if ("/addWork".equals(request.getServletPath())) {

                Integer profit = 0;
                DateMyFormat dateMyFormat = new DateMyFormat();
                Integer week = dateMyFormat.getCurentWeek();
                Integer month = dateMyFormat.getCurentMonth();
                Integer year = dateMyFormat.getCurentYear();
                getServletContext().setAttribute("week", week);
                getServletContext().setAttribute("month", month);
                getServletContext().setAttribute("year", year);

                if (week != 0 && month != 0 && year != 0) {

                    Integer currentWeek = 0;
                    Integer currentYear = 0;
                    Integer currentMonth = 0;
                    if (request.getParameter("currentWeek") != null) {
                        currentWeek = Integer.parseInt(request.getParameter("currentWeek"));
                    }
                    if (week != currentWeek && currentWeek != 0) {
                        week = currentWeek;
                    }

                    if (request.getParameter("currentMonth") != null) {
                        currentMonth = Integer.parseInt(request.getParameter("currentMonth"));
                    }
                    if (month != currentMonth && currentMonth != 0) {
                        month = currentMonth;
                    }

                    if (request.getParameter("currentYear") != null) {
                        currentYear = Integer.parseInt(request.getParameter("currentYear"));
                    }
                    if (year != currentYear && currentYear != 0) {
                        year = currentYear;
                    }

                    List<OrderFurniture> orderFurnitures = orderFacade.ordersByDate(week, month, year);
                    getServletContext().setAttribute("orders", orderFurnitures);

                    getServletContext().removeAttribute("week");
                    getServletContext().setAttribute("week", week);
                    getServletContext().removeAttribute("month");
                    getServletContext().setAttribute("month", month);
                    getServletContext().removeAttribute("year");
                    getServletContext().setAttribute("year", year);
                }
                if (loginWorker.getId() != null) {
                    Worker selectedWorker = workerFacade.find(loginWorker.getId());
                    getServletContext().setAttribute("selectedWorker", selectedWorker);
                    List<DoneWork> doneWorks = doneWorkFacade.doneWorkByWorkerAndDate(month, year, loginWorker.getId());
                    List<StringDoneWork> workDone = new ArrayList<>();

                    String modelName;
                    String partName;
                    for (int i = 0; i < doneWorks.size(); i++) {
                        Long id = doneWorks.get(i).getId();
                        Long orderId = doneWorks.get(i).getOrderId();

                        if (modelFacade.find(doneWorks.get(i).getModelId()) != null) {
                            modelName = modelFacade.find(doneWorks.get(i).getModelId()).getName();
                        } else {
                            modelName = doneWorks.get(i).getModelId().toString();
                        }
                        if (partFacade.find(doneWorks.get(i).getPartId()) != null) {
                            partName = partFacade.find(doneWorks.get(i).getPartId()).getDescription();
                        } else {
                            partName = doneWorks.get(i).getPartId().toString();
                        }
                        Integer price = doneWorks.get(i).getPrice();
                        Integer done = doneWorks.get(i).getDone();
                        StringDoneWork stringDoneWork = new StringDoneWork(id, week, month, year, orderId, modelName, partName, price, done);
                        workDone.add(stringDoneWork);
                    }

                    for (int i = 0; i < doneWorks.size(); i++) {
                        profit += doneWorks.get(i).getDone() * doneWorks.get(i).getPrice();
                    }

                    getServletContext().setAttribute("worksDone", workDone);
                    getServletContext().setAttribute("profiT", profit);
                }
                if (request.getParameter("orderId") != null && !"".equals(request.getParameter("orderId"))) {
                    Long orderId = Long.parseLong(request.getParameter("orderId"));
                    OrderFurniture selectedOrder = orderFacade.find(orderId);
                    List<Long> modelIds = new ArrayList<Long>(selectedOrder.getModels().keySet());
                    List<Model> models = new ArrayList<>();
                    for (int i = 0; i < modelIds.size(); i++) {
                        models.add(modelFacade.find(modelIds.get(i)));
                    }
                    getServletContext().setAttribute("selectedOrder", selectedOrder);
                    getServletContext().setAttribute("models", models);

                }
                if (request.getParameter("modelId") != null && !"".equals(request.getParameter("modelId"))) {
                    Long modelId = Long.parseLong(request.getParameter("modelId"));
                    Model selectedModel = modelFacade.find(modelId);

                    getServletContext().setAttribute("selectModel", selectedModel);
                }
                if (request.getParameter("operationId") != null && !"".equals(request.getParameter("operationId"))) {
                    Long partId = Long.parseLong(request.getParameter("operationId"));
                    Part selectedPart = partFacade.find(partId);
                    getServletContext().setAttribute("selectedPart", selectedPart);
                }
                if (request.getParameter("orderId") != null && !"".equals(request.getParameter("orderId")) && request.getParameter("modelId") != null
                        && !"".equals(request.getParameter("modelId")) && request.getParameter("operationId") != null && !"".equals(request.getParameter("operationId"))) {

                    Long orderId = Long.parseLong(request.getParameter("orderId"));
                    OrderFurniture order = orderFacade.find(orderId);
                    Long selectedModelId = Long.parseLong(request.getParameter("modelId"));

                    Long selectedPartId = Long.parseLong(request.getParameter("operationId"));

                    List<DoneWork> doneWorks = doneWorkFacade.listDoneWork(orderId, selectedModelId, selectedPartId);
                    Integer ammountForPart = 0;

                    for (DoneWork dw : doneWorks) {
                        ammountForPart += dw.getDone();
                    }

                    if (partFacade.find(selectedPartId) != null) {
                        List<Integer> ammounts = new ArrayList<>();
                        if (order.getModels().get(selectedModelId) - ammountForPart != 0) {
                            for (int i = 1; i <= order.getModels().get(selectedModelId) - ammountForPart; i++) {
                                ammounts.add(i);
                            }
                        }
                        getServletContext().setAttribute("ammounts", ammounts);
                    }

                }
                if (week != 0 && month != 0 && year != 0 && request.getParameter("orderId") != null && !"".equals(request.getParameter("orderId"))
                        && request.getParameter("modelId") != null && !"".equals(request.getParameter("modelId")) && request.getParameter("operationId") != null
                        && !"".equals(request.getParameter("operationId")) && request.getParameter("quantity") != null && !"".equals(request.getParameter("quantity"))) {
                    Long orderId = Long.parseLong(request.getParameter("orderId"));
                    Long modelId = Long.parseLong(request.getParameter("modelId"));
                    Long partId = Long.parseLong(request.getParameter("operationId"));
                    Part part = partFacade.find(partId);
                    Integer ammount = Integer.parseInt(request.getParameter("quantity"));
                    DoneWork doneWork = new DoneWork(week, month, year, orderId, modelId, loginWorker.getId(), partId);
                    doneWork.setDone(ammount);//quantity of done parts
                    doneWork.setPrice(part.getPrice());//price of 1 part
                    if (abs(week - dateMyFormat.getCurentWeek()) <= 5) {
                        doneWorkFacade.create(doneWork);
                        getServletContext().removeAttribute("message");
                        getServletContext().removeAttribute("selectedOrder");
                        getServletContext().removeAttribute("selectModel");
                        getServletContext().removeAttribute("selectedPart");
                        getServletContext().removeAttribute("quantity");
                        getServletContext().removeAttribute("profiT");
                        getServletContext().removeAttribute("selectedWorker");
                    } else {
                        getServletContext().setAttribute("message", "Сроки истекли");
                    }

                }

                request.getRequestDispatcher("order_selection.jsp").forward(request, response);
            }
            if ("/deleteWork".equals(request.getServletPath())) {
                Long doneWorkId = Long.parseLong(request.getParameter("doneWorkId"));
                doneWorkFacade.remove(doneWorkFacade.find(doneWorkId));
                getServletContext().removeAttribute("selectedOrder");
                getServletContext().removeAttribute("selectModel");
                getServletContext().removeAttribute("selectedPart");
                getServletContext().removeAttribute("profiT");
                getServletContext().removeAttribute("quantity");
                getServletContext().removeAttribute("selectedWorker");
                response.sendRedirect("addWork");
            }
        } else {
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
