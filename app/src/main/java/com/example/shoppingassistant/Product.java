package com.example.shoppingassistant;

public class Product {

    String timeLeft;
    String image;
    String name;
    String price;
    String priceFormat;
    String oldPrice;
    int quantityLeft;
    String bidCount;
    boolean chinese;
    String url;

    public Product(String timeLeft, String image, String name, String price, String priceFormat, String oldPrice,int quantityLeft, boolean chinese, String bidCount, String url) {
        this.timeLeft = timeLeft;
        this.image = image;
        this.name = name;
        this.price = price;
        this.priceFormat = priceFormat;
        this.oldPrice = oldPrice;
        this.quantityLeft = quantityLeft;
        this.chinese = chinese;
        this.bidCount = bidCount;
        this.url = url;
    }
}
// #53BEE6