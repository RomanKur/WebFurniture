/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import entities.Model;
import entities.Part;
import entities.Worker;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.ModelFacade;
import session.PartFacade;

/**
 *
 * @author Roman Kurtsanov
 * Java контроллер обоспечивабщий работоспосбность страницы создания, редактирования и удаления моделей и деталей.
 */
@WebServlet(name = "Controller", urlPatterns = {"/models", "/editmodel", "/deletemodel", "/addmodelname", "/part", "/deletePart", "/editPart"})
public class ModelController extends HttpServlet {

    @EJB
    ModelFacade modelFacade;
    PartFacade partFacade;

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
        if (loginWorker != null &&("Desiner".equals(loginWorker.getStatus()) || "admin".equals(loginWorker.getStatus()) || "Desiner".equals(loginWorker.getAditionalAccess()))) {
            String userPath = request.getServletPath();
            Model selectedModel = new Model();

            if ("/models".equals(userPath)) {
                getServletContext().setAttribute("models", modelFacade.findAll());

                if (request.getParameter("model") != null) {
                    Long modelId = Long.parseLong(request.getParameter("model"));
                    selectedModel = modelFacade.find(modelId);
                    getServletContext().setAttribute("model", modelFacade.findAll());
                }
                request.getRequestDispatcher("/models.jsp").forward(request, response);

            } else if ("/addmodelname".equals(userPath)) {

                if (request.getParameter("newmodel") != "") {
                    String newmodelname = request.getParameter("newmodel");
                    Model newModel = new Model(newmodelname, new ArrayList<Part>());
                    modelFacade.create(newModel);
                    getServletContext().setAttribute("selectedModel", newModel);
                }

                getServletContext().setAttribute("models", modelFacade.findAll());
                request.getRequestDispatcher("/models").forward(request, response);

            } else if ("/deletemodel".equals(userPath)) {
                Long modelId = Long.parseLong(request.getParameter("selectedModelId"));

                if (request.getParameter("selectedModelId") != null) {
                    selectedModel = modelFacade.find(modelId);
                    modelFacade.remove(selectedModel);
                }
                selectedModel = modelFacade.find(modelId);
                getServletContext().setAttribute("selectedModel", selectedModel);
                getServletContext().setAttribute("models", modelFacade.findAll());
                request.getRequestDispatcher("/models.jsp").forward(request, response);

            } else if ("/editmodel".equals(userPath)) {

                if (request.getParameter("model") != null) {
                    Long modelId = Long.parseLong(request.getParameter("model"));
                    selectedModel = modelFacade.find(modelId);
                    getServletContext().setAttribute("selectedModel", selectedModel);

                    String partname, partdescription;
                    Integer partprice, partduration;

                    if (request.getParameter("save") != null) {
                        if (request.getParameter("part_name") != "" && request.getParameter("part_description") != ""
                                && request.getParameter("part_price") != "" && request.getParameter("part_duration") != "") {
                            partname = request.getParameter("part_name");
                            partdescription = request.getParameter("part_description");
                            partprice = Integer.parseInt(request.getParameter("part_price"));
                            partduration = Integer.parseInt(request.getParameter("part_duration"));
                            Part newPart = new Part(partname, partdescription, partprice, partduration);
                            selectedModel.getParts().add(newPart);

                            modelFacade.edit(selectedModel);

                            selectedModel = modelFacade.find(modelId);
                            getServletContext().setAttribute("selectedModel", selectedModel);
                            getServletContext().setAttribute("models", modelFacade.findAll());
                        }
                    }
                    if (request.getParameter("update") != null) {
                        if (request.getParameter("part_id") != null) {
                            Part oldPart = new Part();
                            Long oldPartId = Long.parseLong(request.getParameter("part_id"));
                            List<Part> parts = selectedModel.getParts();
                            for (Part p : parts) {
                                if (p.getId() == oldPartId) {
                                    oldPart = p;
                                }
                            }
                            if (request.getParameter("part_id") != null && request.getParameter("part_name") != "" && request.getParameter("part_description") != ""
                                    && request.getParameter("part_price") != "" && request.getParameter("part_duration") != "") {

                                partname = request.getParameter("part_name");
                                partdescription = request.getParameter("part_description");
                                partprice = Integer.parseInt(request.getParameter("part_price"));
                                partduration = Integer.parseInt(request.getParameter("part_duration"));
                                Part editedPart = new Part(partname, partdescription, partprice, partduration);

                                selectedModel.getParts().add(editedPart);
                                selectedModel.getParts().remove(oldPart);
                                modelFacade.edit(selectedModel);

                                selectedModel = modelFacade.find(modelId);
                                getServletContext().setAttribute("selectedModel", selectedModel);
                                getServletContext().setAttribute("models", modelFacade.findAll());

                            }
                        }
                    }
                    request.getRequestDispatcher("/models.jsp").forward(request, response);
                }

            } else if ("/deletePart".equals(userPath)) {
                Part partToDelete = new Part();

                if (request.getParameter("delete_part_id") != null) {
                    Long partToDeleteId = Long.parseLong(request.getParameter("delete_part_id"));
                    Long modelId = Long.parseLong(request.getParameter("selected_model_id"));
                    selectedModel = modelFacade.find(modelId);
                    List<Part> parts = selectedModel.getParts();
                    for (Part p : parts) {
                        if (p.getId() == partToDeleteId) {
                            partToDelete = p;
                        }
                    }
                    selectedModel.getParts().remove(partToDelete);
                    modelFacade.edit(selectedModel);
                    getServletContext().setAttribute("models", modelFacade.findAll());
                    getServletContext().setAttribute("selectedModel", selectedModel);
                    request.getRequestDispatcher("/models.jsp").forward(request, response);
                }

            } else if ("/editPart".equals(userPath)) {
                Part partToEdit = new Part();

                if (request.getParameter("edit_part_id") != null) {
                    Long partToEditId = Long.parseLong(request.getParameter("edit_part_id"));
                    Long modelId = Long.parseLong(request.getParameter("selected_model_id"));
                    selectedModel = modelFacade.find(modelId);
                    List<Part> parts = selectedModel.getParts();
                    for (Part p : parts) {
                        if (p.getId() == partToEditId) {
                            partToEdit = p;
                        }
                    }
                    getServletContext().setAttribute("partToEdit", partToEdit);
                    getServletContext().setAttribute("models", modelFacade.findAll());
                    getServletContext().setAttribute("selectedModel", selectedModel);
                    request.getRequestDispatcher("/models.jsp").forward(request, response);
                    getServletContext().setAttribute("partToEdit", null);
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
