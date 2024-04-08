package com.example.mobile;

public class Order {
    private int id;
    private String name;
    private double total;

    public Order() {
    }

    public Order(String name, double total) {
        this.name = name;
        this.total = total;
    }

    public Order(int id, String name, double total) {
        this.id = id;
        this.name = name;
        this.total = total;
    }

    // Getter và Setter cho id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter và Setter cho tên đơn hàng
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter và Setter cho tổng giá trị của đơn hàng
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", total=" + total +
                '}';
    }
}
