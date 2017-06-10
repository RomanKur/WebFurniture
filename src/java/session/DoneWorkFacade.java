/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entities.DoneWork;
import entities.Model;
import entities.OrderFurniture;
import entities.Part;
import entities.Worker;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author pupil
 */
@Stateless
public class DoneWorkFacade extends AbstractFacade<DoneWork> {

    @PersistenceContext(unitName = "KTVR15WebFurniture_KurtsanovPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DoneWorkFacade() {
        super(DoneWork.class);
    }
    public List<DoneWork> doneWorkByWorkerAndDate(Integer month, Integer year, Long workerId){
        Query query = getEntityManager().createQuery("Select d from DoneWork d Where d.month = :month AND d.year = :year AND d.workerID = :worker")
                .setParameter("month", month)
                .setParameter("year", year)
                .setParameter("worker", workerId);
        
        return query.getResultList();
    }
    
    public List<DoneWork> listDoneWork(Long order, Long model, Long part){
        Query query = getEntityManager().createQuery("SELECT d from DoneWork d WHERE d.orderId = :order AND d.modelId = :model AND d.partId = :part")
                .setParameter("order", order)
                .setParameter("model", model)
                .setParameter("part", part);
        return  query.getResultList();
        
    }
    
}
