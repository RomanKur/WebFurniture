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
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String isikukood;
    private String mail;
    private String telephon;
        
    public Person() {
    }

    /**
     * Java класс содержит в себе данные о человеке.
     * @param firstname Имя
     * @param lastname Фамилия
     * @param isikukood ид-код(исикукод)
     * @param mail емаил
     * @param telephon номер телефона
     */
    public Person(String firstname, String lastname, String isikukood, String mail, String telephon) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.isikukood = isikukood;
        this.mail = mail;
        this.telephon = telephon;
       
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getIdCode() {
        return isikukood;
    }

    public void setIdCode(String idCode) {
        this.isikukood = idCode;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelephon() {
        return telephon;
    }

    public void setTelephon(String telefon) {
        this.telephon = telefon;
    }

    public String getIsikukood() {
        return isikukood;
    }

    public void setIsikukood(String isikukood) {
        this.isikukood = isikukood;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.firstname);
        hash = 47 * hash + Objects.hashCode(this.lastname);
        hash = 47 * hash + Objects.hashCode(this.isikukood);
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
        final Person other = (Person) obj;
        if (!Objects.equals(this.firstname, other.firstname)) {
            return false;
        }
        if (!Objects.equals(this.lastname, other.lastname)) {
            return false;
        }
        if (!Objects.equals(this.isikukood, other.isikukood)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

       
}
