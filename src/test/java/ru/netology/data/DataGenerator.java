package ru.netology.data;
import com.github.javafaker.Faker;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {

    private DataGenerator() {}

    public static AppRegistration generateNewApp() {
        Faker faker = new Faker(new Locale("ru"));
        return new AppRegistration(String.format(faker.address().cityName(), "Сыктывкар", "Анадырь", "Архангельск", "Москва"),
                faker.name().name(),
                faker.numerify("+7##########"));
                //String.format(faker.phoneNumber().cellPhone(), "+7##########", "###########"));
    }

    public static AppRegistration generateNewRejectedApp() {
        Faker faker = new Faker(new Locale("en"));
        return new AppRegistration(String.format(faker.address().cityName(), "London", "Paris", "New York", "Rome"),
                faker.name().name(),
                faker.numerify("+7##########"));
                //String.format(faker.phoneNumber().cellPhone(), "+7##########", "###########"));
    }

    public static AppRegistration generateNewSpecialNameApp() {
        Faker faker = new Faker(new Locale("ru"));
        return new AppRegistration(String.format(faker.address().cityName(), "Сыктывкар", "Анадырь", "Архангельск", "Москва"),
                faker.expression("Артём Булатов"),
                faker.numerify("+7##########"));
                //String.format(faker.phoneNumber().cellPhone(), "+7##########", "###########"));
    }


    public static String generateCardDeliveryDate() {
        LocalDate firstDate = LocalDate.now().plusDays(4);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return firstDate.format(formatter);
    }

    public static String generateNewCardDeliveryDate() {
        LocalDate secondDate = LocalDate.now().plusDays(5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return secondDate.format(formatter);
    }

    public static String generateSpecialCardDeliveryDate() {
        LocalDate specialDate = LocalDate.now().plusYears(400);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return specialDate.format(formatter);
    }
}
