package ru.netology.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor

public class AppRegistration {
    private String city;
    private String date;
    private String name;
    private String phone;

    public AppRegistration(String city, String date, String name, String phone) {
        this.city = city;
        this.date = date;
        this.name = name;
        this.phone = phone;
    }
}
