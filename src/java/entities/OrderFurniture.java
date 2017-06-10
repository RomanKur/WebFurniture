/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Roman Kurtsanov
 * 
 */
@Entity
public class OrderFurniture implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ElementCollection
    @CollectionTable(name = "MODEL_COUNT")
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private Map<Long,Integer> models = new HashMap<>();
    @OneToOne(orphanRemoval = true,cascade = CascadeType.ALL)
    private OrderDate orderDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createOrderFurniture;
    

    public OrderFurniture() {
    }

    /**
     * Java класс содердит в себе данные заказа.
     * @param name Название заказа
     * @param models Список моделей и их количество
     * @param orderDate Предполагаемая дата начала выполнения заказа
     */
    public OrderFurniture(String name, Map<Long, Integer> models, OrderDate orderDate) {
        this.name = name;
        this.models = models;
        this.orderDate = orderDate;
        this.createOrderFurniture = (Date) java.util.Calendar.getInstance().getTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Long, Integer> getModels() {
        return models;
    }

    public void setModels(Map<Long, Integer> models) {
        this.models = models;
    }
    
    public OrderDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(OrderDate orderDate) {
        this.orderDate = orderDate;
    }

    public Date getCreateOrderFurniture() {
        return createOrderFurniture;
    }

    public void setCreateOrderFurniture(Date createOrderFurniture) {
        this.createOrderFurniture = createOrderFurniture;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.models);
        hash = 53 * hash + Objects.hashCode(this.orderDate);
        hash = 53 * hash + Objects.hashCode(this.createOrderFurniture);
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
        final OrderFurniture other = (OrderFurniture) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.models, other.models)) {
            return false;
        }
        if (!Objects.equals(this.orderDate, other.orderDate)) {
            return false;
        }
        if (!Objects.equals(this.createOrderFurniture, other.createOrderFurniture)) {
            return false;
        }
        return true;
    }

}
