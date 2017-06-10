/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import entities.Worker;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import security.AuthBean;
import session.WorkerFacade;

/**
 *
 * @author Roman Kurtsanov
 * 
 * Java котроллер обеспечивающий работоспосбность страницы входа в приложение.
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    @EJB
    WorkerFacade workerFacade;
    @EJB
    AuthBean authBean;

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
        request.setCharacterEncoding("UTF-8");

        if ("/login".equals(request.getServletPath())) {

            String login = request.getParameter("logiN");
            String password = request.getParameter("password");
            Worker regUser = authBean.getAuthorizationRegUser(login, password);
            if (regUser != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute("regUser", regUser);
                if (regUser.getAditionalAccess() == null || "".equals(regUser.getAditionalAccess())) {
                    if ("admin".equals(regUser.getStatus())) {
                        request.getRequestDispatcher("/index.jsp").forward(request, response);
                        return;
                    } else if ("Worker".equals(regUser.getStatus())) {
                        response.sendRedirect("addWork");
                        return;
                    } else if ("Desiner".equals(regUser.getStatus())) {
                        response.sendRedirect("models");
                        return;
                    } else if ("Order acceptor".equals(regUser.getStatus())) {
                        response.sendRedirect("order");
                        return;
                    } else if ("Staff manager".equals(regUser.getStatus())) {
                        response.sendRedirect("worker");
                        return;
                    } else if ("Bookkeeper".equals(regUser.getStatus())) {
                        response.sendRedirect("viewBookkeeper");
                        return;
                    }
                } 
                if(regUser.getAditionalAccess() != null){
                    getServletContext().setAttribute("aditionalAccess", regUser.getAditionalAccess());
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                    return;
                }
            } else {
                getServletContext().removeAttribute("info");
                request.setAttribute("info", "Неправильный логин или пароль!<br><a href=\"newuser\">зарегистрироваться</a>");
                request.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a User containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
