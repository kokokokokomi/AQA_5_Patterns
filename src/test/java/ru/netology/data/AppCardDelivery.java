package ru.netology.data;
import com.codeborne.selenide.Condition;
import ru.netology.data.DataGenerator;
import ru.netology.data.AppRegistration;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class AppCardDelivery {
    private SelenideElement cityField = $("[data-test-id=city] input");
    private SelenideElement dateField = $("[data-test-id=date] input");
    private SelenideElement personName = $("[data-test-id=name] input");
    private SelenideElement phoneNumber = $("[data-test-id=phone] input");
    private SelenideElement agreementField = $("[data-test-id=agreement]");
    private SelenideElement planButton = $$("button").find(exactText("Запланировать"));
    private SelenideElement replanButton = $$("button").find(exactText("Перепланировать"));
    private SelenideElement successMessage = $(withText("Успешно!"));
    private SelenideElement successNotificationContent = $("[data-test-id=success-notification] .notification__content");

    public void openBrowser() { open("http://localhost:9999"); }

    public void sendAllFieldsFilledRequest() {
        cityField.setValue(DataGenerator.generateNewApp().getCity());
        personName.setValue(DataGenerator.generateNewApp().getName());
        phoneNumber.setValue(DataGenerator.generateNewApp().getPhone());
        agreementField.click();
        planButton.click();
    }

    public void sendWrongDataRequest() {
        cityField.setValue(DataGenerator.generateNewRejectedApp().getCity());
        personName.setValue(DataGenerator.generateNewRejectedApp().getName());
        phoneNumber.setValue(DataGenerator.generateNewRejectedApp().getPhone());
        agreementField.click();
        planButton.click();
    }

    public void sendWrongNameRequest() {
        cityField.setValue(DataGenerator.generateNewApp().getCity());
        personName.setValue(DataGenerator.generateNewRejectedApp().getName());
        phoneNumber.setValue(DataGenerator.generateNewApp().getPhone());
        agreementField.click();
        planButton.click();
    }

    public void sendEmptyFieldRequest() {
        personName.setValue(DataGenerator.generateNewApp().getName());
        phoneNumber.setValue(DataGenerator.generateNewApp().getPhone());
        agreementField.click();
        planButton.click();
    }

    public void sendReplanRequest() {
        dateField.doubleClick().sendKeys(BACK_SPACE);
        dateField.setValue(DataGenerator.generateNewApp().getDate());
        planButton.click();
        replanButton.click();
        successMessage.waitUntil(visible, 5000);
        successNotificationContent.shouldHave(text("Встреча успешно запланирована на " + dateField.getValue()));
    }

    public void sendReplanWithCalendarRequest() {
        dateField.click();
        $(".calendar__arrow_direction_right[data-step='1']").click();
        $$("td.calendar__day").find(text("1")).click();
        planButton.click();
        replanButton.click();
        successMessage.waitUntil(visible, 5000);
        successNotificationContent.shouldHave(text("Встреча успешно запланирована на " + dateField.getValue()));
    }

    public void findSuccessMessage() {
        successMessage.waitUntil(visible, 5000);
        successNotificationContent.shouldHave(text("Встреча успешно запланирована на " + dateField.getValue()));
    }

    public void findRejectCityValueMessage() {
        $(byText("Доставка в выбранный город недоступна")).waitUntil(visible, 5000);
    }

    public void findRejectMessage() {
        $(withText("Поле обязательно для заполнения")).waitUntil(visible, 5000);
    }

    public void findRejectPersonNameMessage()  {
        $(withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(visible, 5000);
    }

    public void findRejectDateFieldMessage() {
        $(withText("Неверно введена дата")).waitUntil(visible, 5000);
    }

    public void sendSpecialNameRequest() {
        cityField.setValue(DataGenerator.generateNewApp().getCity());
        personName.setValue("Алёна Иванова");
        phoneNumber.setValue(DataGenerator.generateNewApp().getPhone());
        agreementField.click();
        planButton.click();
    }

    public void sendSpecialDateRequest() {
        cityField.setValue(DataGenerator.generateNewApp().getCity());
        dateField.doubleClick().sendKeys(BACK_SPACE);
        dateField.setValue("16.16.2020");
        personName.setValue(DataGenerator.generateNewApp().getName());
        phoneNumber.setValue(DataGenerator.generateNewApp().getPhone());
        agreementField.click();
        planButton.click();
    }
}
