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
 * 
 */
@Entity
public class OrderDate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer week;
    private Integer month;
    private Integer year;
    
    public OrderDate() {
    }
    /**
    * Java класс содержит в себе дату с которой начинается выполнение заказа.
    * @param week Неделя
    * @param month месяц
    * @param year и год на с который подразумевается начало выполнение заказа
    */
    public OrderDate(Integer week, Integer month, Integer year) {
        this.week = week;
        this.month = month;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer _week) {
        this.week = _week;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer _month) {
        this.month = _month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer _year) {
        this.year = _year;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.week);
        hash = 23 * hash + Objects.hashCode(this.month);
        hash = 23 * hash + Objects.hashCode(this.year);
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
        final OrderDate other = (OrderDate) obj;
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
        return true;
    }

    
    

}
