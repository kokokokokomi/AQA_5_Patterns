package ru.netology.data;
import ru.netology.data.DataGenerator;
import ru.netology.data.AppRegistration;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AppCardDelivery {
    private SelenideElement city = $("[data-test-id=city] input");
    private SelenideElement date = $("[data-test-id=date] input");
    private SelenideElement name = $("[data-test-id=name] input");
    private SelenideElement phoneNumber = $("[data-test-id=phone] input");
    private SelenideElement agreement = $("[data-test-id=agreement]");

    public void openBrowser() { open("http://localhost:9999"); }

    public void requestAllFieldsFilled() {
    }
}
