package ru.netology.test;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Month;
import java.util.Random;

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
    private ElementsCollection calendarDays = $$("td.calendar__day");

    @BeforeEach
    void shouldOpenBrowser() { open("http://localhost:9999"); }

    @Test
    void shouldConfirmReplanRequest() {
        cityField.setValue(DataGenerator.generateNewApp().getCity());
        dateField.doubleClick().sendKeys(BACK_SPACE);
        dateField.setValue(DataGenerator.generateCardDeliveryDate());
        personName.setValue(DataGenerator.generateNewApp().getName());
        phoneNumber.setValue(DataGenerator.generateNewApp().getPhone());
        agreementField.click();
        planButton.click();
        successMessage.waitUntil(visible, 5000);
        successNotificationContent.shouldHave(text("Встреча успешно запланирована на " + DataGenerator.generateCardDeliveryDate()));
        dateField.doubleClick().sendKeys(BACK_SPACE);
        dateField.setValue(DataGenerator.generateNewCardDeliveryDate());
        planButton.click();
        replanButton.click();
        successMessage.waitUntil(visible, 5000);
        successNotificationContent.shouldHave(text("Встреча успешно запланирована на " + DataGenerator.generateNewCardDeliveryDate()));
    }

    @Test
    void shouldConfirmReplanWithCalendarRequest() {
        LocalDate defaultCalendarDay = LocalDate.now().plusDays(4);
        String seekingDefaultDay = String.valueOf(defaultCalendarDay.getDayOfMonth());
        LocalDate meetingDay = LocalDate.now().plusDays(5);
        String seekingReplanMeetingDay = String.valueOf(meetingDay.getDayOfMonth());
        cityField.setValue(DataGenerator.generateNewApp().getCity());
        dateField.click();
        calendarDays.find(text(seekingDefaultDay)).click();
        personName.setValue(DataGenerator.generateNewApp().getName());
        phoneNumber.setValue(DataGenerator.generateNewApp().getPhone());
        agreementField.click();
        planButton.click();
        successMessage.waitUntil(visible, 5000);
        successNotificationContent.shouldHave(text("Встреча успешно запланирована на " + defaultCalendarDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
        dateField.click();
        calendarDays.find(text(seekingReplanMeetingDay)).click();
        planButton.click();
        replanButton.click();
        successMessage.waitUntil(visible, 5000);
        successNotificationContent.shouldHave(text("Встреча успешно запланирована на " + meetingDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
    }

    @Test
    void shouldConfirmSpecialNameRequest() {
        cityField.setValue(DataGenerator.generateNewSpecialNameApp().getCity());
        dateField.click();
        dateField.setValue(DataGenerator.generateCardDeliveryDate());
        personName.setValue(DataGenerator.generateNewSpecialNameApp().getName());
        phoneNumber.setValue(DataGenerator.generateNewSpecialNameApp().getPhone());
        agreementField.click();
        planButton.click();
        successMessage.waitUntil(visible, 5000);
        successNotificationContent.shouldHave(text("Встреча успешно запланирована на " + DataGenerator.generateCardDeliveryDate()));
    }

    @Test
    void shouldNotConfirmWrongNameRequest() {
        cityField.setValue(DataGenerator.generateNewApp().getCity());
        personName.setValue(DataGenerator.generateNewRejectedApp().getName());
        phoneNumber.setValue(DataGenerator.generateNewApp().getPhone());
        agreementField.click();
        planButton.click();
        $(withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(visible, 5000);
    }

    @Test
    void shouldNotConfirmRequest() {
        personName.setValue(DataGenerator.generateNewApp().getName());
        phoneNumber.setValue(DataGenerator.generateNewApp().getPhone());
        agreementField.click();
        planButton.click();
        $(withText("Поле обязательно для заполнения")).waitUntil(visible, 5000);
    }

    @Test
    void shouldNotConfirmWrongDataRequest() {
        cityField.setValue(DataGenerator.generateNewRejectedApp().getCity());
        personName.setValue(DataGenerator.generateNewRejectedApp().getName());
        phoneNumber.setValue(DataGenerator.generateNewRejectedApp().getPhone());
        agreementField.click();
        planButton.click();
        $(byText("Доставка в выбранный город недоступна")).waitUntil(visible, 5000);
    }

    @Test
    void shouldNotConfirmWrongDateRequest() {
        cityField.setValue(DataGenerator.generateNewApp().getCity());
        dateField.doubleClick().sendKeys(BACK_SPACE);
        dateField.setValue(DataGenerator.generateSpecialCardDeliveryDate());
        personName.setValue(DataGenerator.generateNewApp().getName());
        phoneNumber.setValue(DataGenerator.generateNewApp().getPhone());
        agreementField.click();
        planButton.click();
        $(withText("Неверно введена дата")).waitUntil(visible, 5000);
    }

    @Test
    void shouldNotConfirmWrongDateRequestV2() {
        cityField.setValue(DataGenerator.generateNewApp().getCity());
        dateField.doubleClick().sendKeys(BACK_SPACE);
        dateField.setValue("16.16.2220");
        personName.setValue(DataGenerator.generateNewApp().getName());
        phoneNumber.setValue(DataGenerator.generateNewApp().getPhone());
        agreementField.click();
        planButton.click();
        $(withText("Неверно введена дата")).waitUntil(visible, 5000);
    }
}
