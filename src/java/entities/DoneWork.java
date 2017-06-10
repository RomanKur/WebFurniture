/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Roman Kurtsanov
 */
@Entity
public class DoneWork implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer week;// = OrderDate.getWeek();
    private Integer month;// = OrderDate.getMonth();
    private Integer year;// = OrderDate.getYear();
    private Long orderId;
    private Long modelId;
    private Long workerID;
    private Long partId;
    private Integer price;
    private Integer done = 0;

    public DoneWork() {
    }

    /**
    * Java класс собирающий в себе информацию о сделанной работе. 
     * @param week Неделя
     * @param month месяц и  
     * @param year год на с которых подразумевается начало выполнение заказа
     * @param orderId Номер заказа для которго выполнялась работа
     * @param modelId Номер модели для которой создавались детали
     * @param workerID Иднетификационный номер работника выполнявшего работу
     * @param partId Номер детали
     */
    public DoneWork(Integer week, Integer month, Integer year, Long orderId, Long modelId, Long workerID, Long partId) {
        this.week = week;
        this.month = month;
        this.year = year;
        this.orderId = orderId;
        this.modelId = modelId;
        this.workerID = workerID;
        this.partId = partId;
    }

       
    public Integer getDone() {
        return done;
    }

    public void setDone(Integer done) {
        this.done = done;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Long getPartId() {
        return partId;
    }

    public void setPartId(Long partId) {
        this.partId = partId;
    }

    public Long getWorkerID() {
        return workerID;
    }

    public void setWorkerID(Long workerID) {
        this.workerID = workerID;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.week);
        hash = 67 * hash + Objects.hashCode(this.month);
        hash = 67 * hash + Objects.hashCode(this.year);
        hash = 67 * hash + Objects.hashCode(this.orderId);
        hash = 67 * hash + Objects.hashCode(this.modelId);
        hash = 67 * hash + Objects.hashCode(this.workerID);
        hash = 67 * hash + Objects.hashCode(this.partId);
        hash = 67 * hash + Objects.hashCode(this.price);
        hash = 67 * hash + Objects.hashCode(this.done);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DoneWork other = (DoneWork) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.week, other.week)) {
            return false;
        }
        if (!Objects.equals(this.month, other.month)) {
            return false;
        }
        if (!Objects.equals(this.year, other.year)) {
            return false;
        }
        if (!Objects.equals(this.orderId, other.orderId)) {
            return false;
        }
        if (!Objects.equals(this.modelId, other.modelId)) {
            return false;
        }
        if (!Objects.equals(this.workerID, other.workerID)) {
            return false;
        }
        if (!Objects.equals(this.partId, other.partId)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        if (!Objects.equals(this.done, other.done)) {
            return false;
        }
        return true;
    }

      
    
}