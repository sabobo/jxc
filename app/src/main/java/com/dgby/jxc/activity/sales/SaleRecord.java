package com.dgby.jxc.activity.sales;

public class SaleRecord {

    private int id;
    private String productName;
    private double price;
    private int amount;
    private String timestamp;

    public SaleRecord(int id, String productName, double price, int amount, String timestamp) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "SaleRecord{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}

