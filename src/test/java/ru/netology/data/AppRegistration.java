package ru.netology.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor

public class AppRegistration {
    private String city;
    private String name;
    private String phone;

    public AppRegistration(String city, String name, String phone) {
        this.city = city;
        this.name = name;
        this.phone = phone;
    }
}
