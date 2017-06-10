package servlet;

import entities.Worker;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.WorkerFacade;
import util.EncriptPass;

/**
 *
 * @author Roman Kurtsanov
 * Java котроллер обеспечивающий работоспосбность страницы создания, редактирования и удаления работников предприятия.
 */

@WebServlet(name = "WorkerController", urlPatterns = {"/worker", "/forWorker", "/selectWorker"})
public class WorkerController extends HttpServlet {

    @EJB
    WorkerFacade workerFacade;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NoSuchAlgorithmException {
        response.setContentType("text/html;charset=UTF-8");
        // Информационное сообщение, которое будет выводится на страничке
        // Worker worker=new Worker();
        Worker loginWorker = (Worker) request.getSession().getAttribute("regUser");

        if (loginWorker != null && ("Staff manager".equals(loginWorker.getStatus()) || "admin".equals(loginWorker.getStatus()) || "Staff manager".equals(loginWorker.getAditionalAccess()))) {
            List<String> statuses = new ArrayList<>();
            statuses.add("Worker");
            statuses.add("Bookkeeper");
            statuses.add("Desiner");
            statuses.add("Order acceptor");
            statuses.add("Staff manager");
            statuses.add("admin");
            List<String> accesses = new ArrayList<>();
            accesses.add("Worker");
            accesses.add("Bookkeeper");
            accesses.add("Desiner");
            accesses.add("Order acceptor");
            accesses.add("Staff manager");
            EncriptPass encriptPass = new EncriptPass();
            String msg = "";
            String userPath = request.getServletPath();
            if ("/worker".equals(userPath)) {
                getServletContext().setAttribute("accesses", accesses);
                getServletContext().setAttribute("statuses", statuses);
                getServletContext().setAttribute("workers", workerFacade.findAll());
                request.getRequestDispatcher("/worker.jsp").forward(request, response);
            } else if ("/forWorker".equals(userPath)) {

                String firstname = "";
                String lastname = "";
                String isikukood = "";
                String mail = "";
                String telephon = "";
                String status = "";
                Worker worker = new Worker();
                if (request.getParameter("add") != null) {
                    //добываем параметры из запроса
                    firstname = request.getParameter("firstname");
                    lastname = request.getParameter("lastname");
                    isikukood = request.getParameter("isikukood");
                    mail = request.getParameter("mail");
                    telephon = request.getParameter("telephon");
                    status = request.getParameter("status");
                    //проверяем на пустоту параметра и инициируем объект энтити worker (без параметра id)
                    if (firstname != "" && lastname != "" && isikukood != "" && mail != "" && telephon != "" && status != ""
                            && firstname != null && lastname != null && isikukood != null && mail != null && telephon != null && status != null) {
                        worker = new Worker(status, firstname, lastname, isikukood, mail, telephon);
                        worker.setLogin(firstname + "." + lastname);
                        worker.setSalts(encriptPass.getSalts());
                        encriptPass.setEncriptPassword(lastname, worker.getSalts());
                        worker.setPassword(encriptPass.getEncriptPassword());

                    }
                    try {
                        List<Worker> workers = workerFacade.findAll();
                        for (Worker w : workers) {
                            if (w.getFirstname().equals(worker.getFirstname()) && w.getLastname().equals(worker.getLastname())) {
                                worker.setLogin(worker.getFirstname() + "." + worker.getLastname() + "" + workers.size());
                            }
                        }
                        workerFacade.create(worker);
                        //т.к. работник успешно добавлен, то сообщаем об этом на страничке
                        msg = "Работник добавлен! Логин: " + worker.getLogin() + " Пароль: " + worker.getLastname();

                    } catch (Exception e) {
                        //т.к. работник не добавлен, то сообщаем об этом на страничке
                        msg = "Не добавлено. Такой работник существует";
                        request.setAttribute("infoMassage", msg);
                        // также сообщаем страничке введенные пользователем данные
                        request.setAttribute("worker", worker);
                    }
                } else if (request.getParameter("update") != null) {
                    //пользователь нажал кнопку "Изменить"
                    Long id = null;
                    if (request.getParameter("id") != null) {
                        id = Long.decode(request.getParameter("id"));
                    }
                    //находим по id работника в базе
                    worker = workerFacade.find(id);
                    //изменяем ему состояние, которое берем из формы странички
                    worker.setFirstname(request.getParameter("firstname"));
                    worker.setLastname(request.getParameter("lastname"));
                    worker.setIsikukood(request.getParameter("isikukood"));
                    worker.setMail(request.getParameter("mail"));
                    worker.setTelephon(request.getParameter("telephon"));
                    worker.setStatus(request.getParameter("status"));
                    worker.setLogin(worker.getFirstname() + "." + worker.getLastname());
                    if("admin".equals(loginWorker.getStatus()) && request.getParameter("access") != null){
                        worker.setAditionalAccess(request.getParameter("access"));
                        
                    }
                    //Записываем новое состояние работника
                    try {
                        List<Worker> workers = workerFacade.findAll();
                        for (Worker w : workers) {
                            if (w.getFirstname().equals(worker.getFirstname()) && w.getLastname().equals(worker.getLastname()) && !Objects.equals(w.getId(), worker.getId())) {
                                worker.setLogin(worker.getFirstname() + "." + worker.getLastname() + "" + worker.getId());
                            }
                        }
                        if (request.getParameter("password") != null && !"".equals(request.getParameter("password"))) {
                            worker.setSalts(encriptPass.getSalts());
                            encriptPass.setEncriptPassword(request.getParameter("password"), worker.getSalts());
                            worker.setPassword(encriptPass.getEncriptPassword());
                            
                            msg = "Обновлено! Логин: " + worker.getLogin() + " Пароль: " + request.getParameter("password");
                        } else {
                            msg = "Обновлено! Логин: " + worker.getLogin();
                        }
                        workerFacade.edit(worker);
                    } catch (Exception e) {
                        msg = "Не обновлено!";
                    }

                } else if (request.getParameter("remove") != null) {
                    //пользователь нажал кнопку "Удалить"
                    Long id = null;
                    if (request.getParameter("id") != null) {
                        id = Long.decode(request.getParameter("id"));
                    }
                    worker = workerFacade.find(id);
                    try {
                        workerFacade.remove(worker);
                        msg = "Работник удален!";
                    } catch (Exception e) {
                        msg = "Не удален.";
                        request.setAttribute("worker", worker);
                        request.setAttribute("selectedStatus", worker.getStatus());
                        request.setAttribute("selectedAccess", worker.getAditionalAccess());
                    }
                }
                request.setAttribute("infoMassage", msg);
                request.setAttribute("workers", workerFacade.findAll());
                request.getRequestDispatcher("/worker.jsp").forward(request, response);
            } else if ("/selectWorker".equals(userPath)) {
                Long idWorker = Long.parseLong(request.getParameter("selectWorker"));
                Worker worker = workerFacade.find(idWorker);
                request.setAttribute("worker", worker);
                request.setAttribute("selectedStatus", worker.getStatus());
                request.setAttribute("selectedAccess", worker.getAditionalAccess());
                request.setAttribute("workers", workerFacade.findAll());
                request.getRequestDispatcher("/worker.jsp").forward(request, response);
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
        try {
            processRequest(request, response);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(WorkerController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(WorkerController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
