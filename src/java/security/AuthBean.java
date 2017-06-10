    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import entities.Worker;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import session.WorkerFacade;
import util.EncriptPass;

/**
 *
 * @author Roman Kurtsanov
 * Java класс предназначенный для получениея авторизации пользователем.
 * 
 */
@Stateless
public class AuthBean {

    @EJB WorkerFacade workerFacade;
    

    public AuthBean() {

    }

    /**
     * Метод для занесения пользователя в сессию.
     * @param request запрос из которого метод получает экземпляр класса пользователя
     * @return 
     */
    public  Worker getSessionUser(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            return (Worker) session.getAttribute("regUser");
        }else{
            return null;
        }
        
    }
    /**
     * Метод для авторизации пользователя на сайте.
     * 
     * @param login 
     * @param password
     * @return User or null
     */

    public Worker getAuthorizationRegUser(String login, String password) {
        try {
            Worker loginWorker = workerFacade.FindWorkerByLoginAndPass(login);
            EncriptPass encriptPass = new EncriptPass(password, loginWorker.getSalts());
             
            if(encriptPass.getEncriptPassword().equals(loginWorker.getPassword())){
                
                return loginWorker;
            }else{
                return null;
            }
            
        } catch (Exception e) {
            return null;
        }
        
    }
    
}
