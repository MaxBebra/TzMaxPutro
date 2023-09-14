package com.example.tz.Models;

import java.util.List;

public class AboutTheHotel {
    private String description;
    private List<String> peculiarities;

    public String getDescription() {
        return description;
    }

    public List<String> getPeculiarities() {
        return peculiarities;
    }

    public void setPeculiarities(List<String> peculiarities) {
        this.peculiarities = peculiarities;
    }
}
