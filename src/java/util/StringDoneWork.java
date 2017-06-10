/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Roman Kurtsanov
 * Java класс предназначенный для более наглядного вывода на страницу класса DoneWork
 */
public class StringDoneWork {

    private Long id;
    private Integer week;
    private Integer month;
    private Integer year;
    private Long order;
    private String modelName;
    private String partName;
    private Integer price;
    private Integer done;

    /**
     * Метод предназначенный для более наглядного вывода на страницу класса DoneWork.
     * @param id идентификационный номер для выбоар из БД экземпляра класса DoneWork
     * @param week выбиратеся из найденного в базе экземпляра класса DoneWork
     * @param month выбиратеся из найденного в базе экземпляра класса DoneWork
     * @param year выбиратеся из найденного в базе экземпляра класса DoneWork
     * @param order выбиратеся из найденного в базе экземпляра класса DoneWork
     * @param modelName из найденного в базе экземпляра класса DoneWork берётся идентификатор, по нему находится название экземпляра класса Model из БД
     * @param partName из найденного в базе экземпляра класса DoneWork берётся идентификатор, по нему находится название экземпляра класса Part из БД
     * @param price выбиратеся из найденного в базе экземпляра класса DoneWork
     * @param done выбиратеся из найденного в базе экземпляра класса DoneWork
     */
    public StringDoneWork(Long id, Integer week, Integer month, Integer year, Long order, String modelName, String partName, Integer price, Integer done) {
        this.id = id;
        this.week = week;
        this.month = month;
        this.year = year;
        this.order = order;
        this.modelName = modelName;
        this.partName = partName;
        this.price = price;
        this.done = done;
    }

    public StringDoneWork() {
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

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDone() {
        return done;
    }

    public void setDone(Integer done) {
        this.done = done;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
