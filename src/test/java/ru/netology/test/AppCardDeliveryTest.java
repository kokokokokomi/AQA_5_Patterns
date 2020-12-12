package ru.netology.test;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    private SelenideElement calendarSeekNextMonth = $(".calendar__arrow_direction_right[data-step='1']");
    private ElementsCollection calendarDays = $$("td.calendar__day");

    @BeforeEach
    void shouldOpenBrowser() {
        open("http://localhost:9999");
    }

    @Test
    void shouldConfirmReplanRequest() {
        cityField.setValue(DataGenerator.Generate.generateCity());
        dateField.doubleClick().sendKeys(BACK_SPACE);
        dateField.setValue(DataGenerator.Generate.generateCardDeliveryDate());
        personName.setValue(DataGenerator.Generate.generateNewApp("ru").getName());
        phoneNumber.setValue(DataGenerator.Generate.generateNewApp("ru").getPhone());
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
    void shouldConfirmReplanWithCalendarRequest() {
        cityField.setValue(DataGenerator.Generate.generateCity());
        LocalDate firstMeetingCalendarDay = LocalDate.now().plusDays(3);
        String seekingFirstDay = String.valueOf(firstMeetingCalendarDay.getDayOfMonth());
        LocalDate secondMeetingCalendarDay = LocalDate.now().plusDays(30);
        String seekingSecondDay = String.valueOf(secondMeetingCalendarDay.getDayOfMonth());
        dateField.click();
        calendarDays.find(text(seekingFirstDay)).click();
        personName.setValue(DataGenerator.Generate.generateNewApp("ru").getName());
        phoneNumber.setValue(DataGenerator.Generate.generateNewApp("ru").getPhone());
        agreementField.click();
        planButton.click();
        successMessage.waitUntil(visible, 5000);
        successNotificationContent.shouldHave(text("Встреча успешно запланирована на " + firstMeetingCalendarDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
        dateField.click();
        calendarDays.find(text(seekingFirstDay));
        if ((secondMeetingCalendarDay.getYear() > firstMeetingCalendarDay.getYear()) |
        (secondMeetingCalendarDay.getMonthValue() > firstMeetingCalendarDay.getMonthValue())) {
            calendarSeekNextMonth.click();
        }
        calendarDays.find(text(seekingSecondDay)).click();
        planButton.click();
        replanButton.click();
        successMessage.waitUntil(visible, 5000);
        successNotificationContent.shouldHave(text("Встреча успешно запланирована на " + secondMeetingCalendarDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
    }

        @Test
        void shouldConfirmSpecialNameRequest () {
            cityField.setValue(DataGenerator.Generate.generateCity());
            dateField.click();
            dateField.setValue(DataGenerator.Generate.generateCardDeliveryDate());
            personName.setValue(DataGenerator.Generate.generateSpecialName());
            phoneNumber.setValue(DataGenerator.Generate.generateNewApp("ru").getPhone());
            agreementField.click();
            planButton.click();
            successMessage.waitUntil(visible, 5000);
            successNotificationContent.shouldHave(text("Встреча успешно запланирована на " + DataGenerator.Generate.generateCardDeliveryDate()));
        }

        @Test
        void shouldNotConfirmWrongNameRequest () {
            cityField.setValue(DataGenerator.Generate.generateCity());
            personName.setValue(DataGenerator.Generate.generateInvalidName());
            phoneNumber.setValue(DataGenerator.Generate.generateNewApp("ru").getPhone());
            agreementField.click();
            planButton.click();
            $(withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(visible, 5000);
        }

        @Test
        void shouldNotConfirmRequest () {
            personName.setValue(DataGenerator.Generate.generateNewApp("ru").getName());
            phoneNumber.setValue(DataGenerator.Generate.generateNewApp("ru").getPhone());
            agreementField.click();
            planButton.click();
            $(withText("Поле обязательно для заполнения")).waitUntil(visible, 5000);
        }

        @Test
        void shouldNotConfirmWrongDataRequest () {
            cityField.setValue(DataGenerator.Generate.generateInvalidCity());
            personName.setValue(DataGenerator.Generate.generateNewApp("ru").getName());
            phoneNumber.setValue(DataGenerator.Generate.generateNewApp("ru").getPhone());
            agreementField.click();
            planButton.click();
            $(byText("Доставка в выбранный город недоступна")).waitUntil(visible, 5000);
        }

        @Test
        void shouldNotConfirmWrongDateRequest () {
            cityField.setValue(DataGenerator.Generate.generateCity());
            dateField.doubleClick().sendKeys(BACK_SPACE);
            dateField.setValue(DataGenerator.Generate.generateSpecialCardDeliveryDate());
            personName.setValue(DataGenerator.Generate.generateNewApp("ru").getName());
            phoneNumber.setValue(DataGenerator.Generate.generateNewApp("ru").getPhone());
            agreementField.click();
            planButton.click();
            $(withText("Неверно введена дата")).waitUntil(visible, 5000);
        }

        @Test
        void shouldNotConfirmWrongDateRequestV2 () {
            cityField.setValue(DataGenerator.Generate.generateCity());
            dateField.doubleClick().sendKeys(BACK_SPACE);
            dateField.setValue(DataGenerator.Generate.generateInvalidDate());
            personName.setValue(DataGenerator.Generate.generateNewApp("ru").getName());
            phoneNumber.setValue(DataGenerator.Generate.generateNewApp("ru").getPhone());
            agreementField.click();
            planButton.click();
            $(withText("Неверно введена дата")).waitUntil(visible, 5000);
        }

}
