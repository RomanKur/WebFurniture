/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entities.OrderFurniture;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.DateMyFormat;

/**
 *
 * @author pupil
 */
@Stateless
public class OrderFurnitureFacade extends AbstractFacade<OrderFurniture> {

    @PersistenceContext(unitName = "KTVR15WebFurniture_KurtsanovPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderFurnitureFacade() {
        super(OrderFurniture.class);
    }

    public List<OrderFurniture> oderByToday() {
//      Вычисляем номер текущей недели (week) и года (year)
//        Date curentDate = new Date();
//        SimpleDateFormat dateFormat=null;
//        dateFormat = new SimpleDateFormat("w");
//        Integer week=Integer.decode(dateFormat.format(curentDate));
//        dateFormat = new SimpleDateFormat("yyyy");//Уточнить формат года в сущности OrderDate
//        Integer year=Integer.decode(dateFormat.format(curentDate));

//      Если напишем свой класс работы с датой, то можно воспользоваться им
        DateMyFormat dateMyFormat = new DateMyFormat();
        Integer week = dateMyFormat.getCurentWeek();
        Integer year = dateMyFormat.getCurentYear();
//      Делаем выборку заказов в базе на текущую неделю года

//        Calendar today = Calendar.getInstance();
//        Integer week = today.get(Calendar.WEEK_OF_YEAR) - 1;
//        Integer year = today.get(Calendar.YEAR);
        Query query = getEntityManager().createQuery("SELECT o FROM OrderFurniture o WHERE o.orderDate.week=:week AND o.orderDate.year=:year");
        query.setParameter("week", week);
        query.setParameter("year", year);

        return query.getResultList();
    }

    public List<OrderFurniture> ordersByDate(Integer week, Integer month, Integer year) {
        Query query = getEntityManager().createQuery("Select o FROM OrderFurniture o WHERE o.orderDate.week = :week AND o.orderDate.month = :month AND o.orderDate.year = :year")
                .setParameter("week", week)
                .setParameter("month", month)
                .setParameter("year", year);

        return query.getResultList();
    }
}
