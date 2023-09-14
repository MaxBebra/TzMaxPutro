package com.example.tz.Models;

import java.util.List;

public class NumberModel {
    private String id,name,price,price_per;
    private List<String> image_urls , peculiarities;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice_per() {
        return price_per;
    }

    public void setPrice_per(String price_per) {
        this.price_per = price_per;
    }

    public List<String> getImage_urls() {
        return image_urls;
    }

    public void setImage_urls(List<String> image_urls) {
        this.image_urls = image_urls;
    }

    public List<String> getPeculiarities() {
        return peculiarities;
    }

    public void setPeculiarities(List<String> peculiarities) {
        this.peculiarities = peculiarities;
    }
}
