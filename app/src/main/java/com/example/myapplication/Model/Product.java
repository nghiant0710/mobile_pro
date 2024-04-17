package com.example.myapplication.Model;

public class Product {
    private int id;
    private String name;
    private double price;
    private String brand;
    private String imgUrl;
    private int amount;
    private String capacity;

    public Product() {
    }

    public Product(String name, double price, String brand, String imgUrl, int amount, String capacity) {
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.imgUrl = imgUrl;
        this.amount = amount;
        this.capacity = capacity;
    }

    public Product(int id, String name, double price, String brand, String imgUrl, int amount, String capacity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.imgUrl = imgUrl;
        this.amount = amount;
        this.capacity = capacity;
    }

    // Getter và Setter cho id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter và Setter cho tên sản phẩm
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter và Setter cho giá sản phẩm
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Getter và Setter cho thương hiệu (brand)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    // Getter và Setter cho URL hình ảnh (imgUrl)
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    // Getter và Setter cho số lượng (amount)
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    // Getter và Setter cho dung lượng (dung)
    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", amount=" + amount +
                ", dung='" + capacity + '\'' +
                '}';
    }
}
