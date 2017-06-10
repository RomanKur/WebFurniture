/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import entities.DoneWork;
import entities.Worker;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.DoneWorkFacade;
import session.WorkerFacade;
import util.DateMyFormat;

/**
 *
 * @author Roman Kurtsanov
 * Java конроллер обеспечивающий работу страницы бухгалтера.
 */
@WebServlet(name = "WebController", urlPatterns = {"/viewBookkeeper", "/bookkeeper"})
public class BookkeeperController extends HttpServlet {

    @EJB
    WorkerFacade workerFacade;
    @EJB
    DoneWorkFacade doneWorkFacade;

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
        if (loginWorker != null &&("Bookkeeper".equals(loginWorker.getStatus()) || "admin".equals(loginWorker.getStatus()) || "Bookkeeper".equals(loginWorker.getAditionalAccess()))) {
            String msg = "";
            String userPath = request.getServletPath();
            Integer profit = 0;

            if ("/viewBookkeeper".equals(userPath)) {
                DateMyFormat dateMyFormat = new DateMyFormat();
                Integer week = dateMyFormat.getCurentWeek(); //Integer.parseInt(request.getParameter("week"));
                Integer month = dateMyFormat.getCurentMonth(); //Integer.parseInt(request.getParameter("month"));
                Integer year = dateMyFormat.getCurentYear();//Integer.parseInt(request.getParameter("year"));        
                getServletContext().removeAttribute("week");
                getServletContext().setAttribute("week", week);
                getServletContext().setAttribute("montH", month);
                getServletContext().setAttribute("yeaR", year);
                List<Worker> workers = workerFacade.findAll();
                getServletContext().setAttribute("workers", workers);
                request.getRequestDispatcher("bookkeeper.jsp").forward(request, response);

            } else if ("/bookkeeper".equals(userPath)) {
                String workerId = request.getParameter("workerId");
                if (workerId != null && !"".equals(workerId)) {
                    String month = request.getParameter("selectMonth");
                    String year = request.getParameter("selectYear");
                    getServletContext().setAttribute("montH", month);
                    getServletContext().setAttribute("yeaR", year);

                    Worker selectedWorker = workerFacade.find(new Long(workerId));

                    List<DoneWork> doneWorks = doneWorkFacade.doneWorkByWorkerAndDate(new Integer(month), new Integer(year), new Long(workerId));
                    for (DoneWork doneWork : doneWorks) {

                        profit += doneWork.getPrice() * doneWork.getDone();

                    }
                    getServletContext().setAttribute("doneWorks", doneWorks);
                    getServletContext().setAttribute("month", month);
                    getServletContext().setAttribute("year", year);
                    getServletContext().setAttribute("selectWorker", selectedWorker);
                    getServletContext().setAttribute("profit", profit);
                }
                request.getRequestDispatcher("bookkeeper.jsp").forward(request, response);
            }

        }else{
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
    }
}
// </editor-fold>
//}

