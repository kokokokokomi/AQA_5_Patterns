package ru.netology.data;
import com.github.javafaker.Faker;


import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {

    private DataGenerator() {}

    public static AppRegistration generateNewApp() {
        Faker faker = new Faker(new Locale("ru"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");
        LocalDate local = LocalDate.now().plus(Period.ofDays(7));
        return new AppRegistration(faker.address().city(),
                formatter.format(local),
                faker.name().fullName(),
                faker.phoneNumber().cellPhone());
    }

    public static AppRegistration generateNewRejectedApp() {
        Faker faker = new Faker(new Locale("en"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");
        LocalDate local = LocalDate.now().plus(Period.ofDays(7));
        return new AppRegistration(faker.address().city(),
                formatter.format(local),
                faker.name().fullName(),
                faker.phoneNumber().cellPhone());
    }
}
