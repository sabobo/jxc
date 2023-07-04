package com.dgby.jxc.activity.goods;

    public class Product {
        private long id;
        private String name;
        private double price;
        private int stock;
        private String description;

        public Product() {
        }

        public Product(long id, String name, double price, int stock, String description) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.stock = stock;
            this.description = description;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

