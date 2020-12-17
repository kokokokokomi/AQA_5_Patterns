package ru.netology.test;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.AppRegistration;
import ru.netology.data.DataGenerator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class AppCardDeliveryTest {
    private SelenideElement cityField = $("[data-test-id=city] input");
    private SelenideElement dateField = $("[data-test-id=date] input");
    private SelenideElement personName = $("[data-test-id=name] input");
    private SelenideElement phoneNumber = $("[data-test-id=phone] input");
    private SelenideElement agreementField = $("[data-test-id=agreement]");
    private SelenideElement planButton = $$("button").find(exactText("Запланировать"));
    private SelenideElement replanButton = $$("button").find(exactText("Перепланировать"));
    private SelenideElement successMessage = $(withText("Успешно!"));
    private SelenideElement successNotificationContent = $("[data-test-id=success-notification] .notification__content");

    @BeforeEach
    void shouldOpenBrowser() {
        open("http://localhost:9999");
    }

    @Test
    void shouldConfirmReplanRequest() {
        AppRegistration userData = DataGenerator.Generate.generateNewApp("ru");
        cityField.setValue(userData.getCity());
        dateField.doubleClick().sendKeys(BACK_SPACE);
        dateField.setValue(DataGenerator.Generate.generateCardDeliveryDate());
        personName.setValue(userData.getName());
        phoneNumber.setValue(userData.getPhone());
        agreementField.click();
        planButton.click();
        successMessage.waitUntil(visible, 5000);
        successNotificationContent.shouldHave(text("Встреча успешно запланирована на " + DataGenerator.Generate.generateCardDeliveryDate()));
        dateField.doubleClick().sendKeys(BACK_SPACE);
        dateField.setValue(DataGenerator.Generate.generateNewCardDeliveryDate());
        planButton.click();
        replanButton.click();
        successMessage.waitUntil(visible, 5000);
        successNotificationContent.shouldHave(text("Встреча успешно запланирована на " + DataGenerator.Generate.generateNewCardDeliveryDate()));
    }

    @Test
    void shouldConfirmSpecialNameRequest () {
        AppRegistration userData = DataGenerator.Generate.generateNewApp("ru");
        cityField.setValue(userData.getCity());
        dateField.click();
        dateField.setValue(DataGenerator.Generate.generateCardDeliveryDate());
        personName.setValue(DataGenerator.Generate.generateSpecialName());
        phoneNumber.setValue(userData.getPhone());
        agreementField.click();
        planButton.click();
        successMessage.waitUntil(visible, 5000);
        successNotificationContent.shouldHave(text("Встреча успешно запланирована на " + DataGenerator.Generate.generateCardDeliveryDate()));
    }

    @Test
    void shouldNotConfirmWrongNameRequest () {
        AppRegistration userData = DataGenerator.Generate.generateNewApp("ru");
        cityField.setValue(userData.getCity());
        personName.setValue(DataGenerator.Generate.generateInvalidName());
        phoneNumber.setValue(userData.getPhone());
        agreementField.click();
        planButton.click();
        $(withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(visible, 5000);
    }

    @Test
    void shouldNotConfirmRequest () {
        AppRegistration userData = DataGenerator.Generate.generateNewApp("ru");
        personName.setValue(userData.getName());
        phoneNumber.setValue(userData.getPhone());
        agreementField.click();
        planButton.click();
        $(withText("Поле обязательно для заполнения")).waitUntil(visible, 5000);
    }

    @Test
    void shouldNotConfirmWrongDataRequest () {
        AppRegistration userData = DataGenerator.Generate.generateNewApp("ru");
        cityField.setValue(DataGenerator.Generate.generateInvalidCity());
        personName.setValue(userData.getName());
        phoneNumber.setValue(userData.getPhone());
        agreementField.click();
        planButton.click();
        $(byText("Доставка в выбранный город недоступна")).waitUntil(visible, 5000);
    }

    @Test
    void shouldNotConfirmWrongDateRequest () {
        AppRegistration userData = DataGenerator.Generate.generateNewApp("ru");
        cityField.setValue(userData.getCity());
        dateField.doubleClick().sendKeys(BACK_SPACE);
        dateField.setValue(DataGenerator.Generate.generateSpecialCardDeliveryDate());
        personName.setValue(userData.getName());
        phoneNumber.setValue(userData.getPhone());
        agreementField.click();
        planButton.click();
        $(withText("Неверно введена дата")).waitUntil(visible, 5000);
    }

    @Test
    void shouldNotConfirmWrongDateRequestV2 () {
        AppRegistration userData = DataGenerator.Generate.generateNewApp("ru");
        cityField.setValue(userData.getCity());
        dateField.doubleClick().sendKeys(BACK_SPACE);
        dateField.setValue(DataGenerator.Generate.generateInvalidDate());
        personName.setValue(userData.getName());
        phoneNumber.setValue(userData.getPhone());
        agreementField.click();
        planButton.click();
        $(withText("Неверно введена дата")).waitUntil(visible, 5000);
    }

    @Test
    void shouldNotConfirmWrongPhoneNumberRequest () {
        AppRegistration userData = DataGenerator.Generate.generateNewApp("ru");
        cityField.setValue(userData.getCity());
        dateField.click();
        dateField.setValue(DataGenerator.Generate.generateCardDeliveryDate());
        personName.setValue(userData.getName());
        phoneNumber.setValue(DataGenerator.Generate.generateInvalidPhoneNumber());
        agreementField.click();
        planButton.click();
        $(withText("Номер телефона указан неверно")).waitUntil(visible, 5000); //В форме не предусмотрена фраза для этого сценария
    }

}
