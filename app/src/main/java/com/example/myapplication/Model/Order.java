package com.example.myapplication.Model;

import java.sql.Timestamp;

public class Order {
    private int id;
    private int idProduct;
    private String nameCustomer;
    private String phoneCustomer;
    private String addressCustomer;
    private String status;
    private Timestamp timeOrder;
    private int amount;

    public Order(int id, int idProduct, String nameCustomer, String phoneCustomer, String addressCustomer, String status, Timestamp timeOrder, int amount) {
        this.id = id;
        this.idProduct = idProduct;
        this.nameCustomer = nameCustomer;
        this.phoneCustomer = phoneCustomer;
        this.addressCustomer = addressCustomer;
        this.status = status;
        this.timeOrder = timeOrder;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getPhoneCustomer() {
        return phoneCustomer;
    }

    public void setPhoneCustomer(String phoneCustomer) {
        this.phoneCustomer = phoneCustomer;
    }

    public String getAddressCustomer() {
        return addressCustomer;
    }

    public void setAddressCustomer(String addressCustomer) {
        this.addressCustomer = addressCustomer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(Timestamp timeOrder) {
        this.timeOrder = timeOrder;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Order(int idProduct, String nameCustomer, String phoneCustomer, String addressCustomer, String status, Timestamp timeOrder, int amount) {
        this.idProduct = idProduct;
        this.nameCustomer = nameCustomer;
        this.phoneCustomer = phoneCustomer;
        this.addressCustomer = addressCustomer;
        this.status = status;
        this.timeOrder = timeOrder;
        this.amount = amount;
    }

    public Order() {
    }
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", idProduct=" + idProduct +
                ", nameCustomer='" + nameCustomer + '\'' +
                ", phoneCustomer='" + phoneCustomer + '\'' +
                ", addressCustomer='" + addressCustomer + '\'' +
                ", status='" + status + '\'' +
                ", timeOrder=" + timeOrder +
                ", amount=" + amount +
                '}';
    }
}