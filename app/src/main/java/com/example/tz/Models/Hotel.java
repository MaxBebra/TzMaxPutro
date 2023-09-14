package com.example.tz.Models;

import com.example.tz.Models.AboutTheHotel;

import java.util.List;

public class Hotel {
    private  String id,name,adress,minimal_price,price_for_it,rating,rating_name,description;


    private AboutTheHotel about_the_hotel;
    private List<String> image_urls;


    public AboutTheHotel getAboutTheHotel() {
        return about_the_hotel;
    }
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getMinimal_price() {
        return minimal_price;
    }

    public void setMinimal_price(String minimal_price) {
        this.minimal_price = minimal_price;
    }

    public String getPrice_for_it() {
        return price_for_it;
    }

    public void setPrice_for_it(String price_for_it) {
        this.price_for_it = price_for_it;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRating_name() {
        return rating_name;
    }

    public void setRating_name(String rating_name) {
        this.rating_name = rating_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public List<String> getImage_urls() {
        return image_urls;
    }

    public void setImage_urls(List<String> image_urls) {
        this.image_urls = image_urls;
    }
}
