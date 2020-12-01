import ru.netology.data.AppCardDelivery;

import org.junit.jupiter.api.Test;

public class AppCardDeliveryTest {
    AppCardDelivery cardDelivery = new AppCardDelivery();

    @Test
    void shouldConfirmReplanRequest() {
        cardDelivery.openBrowser();
        cardDelivery.sendAllFieldsFilledRequest();
        cardDelivery.findSuccessMessage();
        cardDelivery.sendReplanRequest();
    }

    @Test
    void shouldConfirmSpecialNameRequest() {
        cardDelivery.openBrowser();
        cardDelivery.sendSpecialNameRequest();
        cardDelivery.findSuccessMessage();
    }

    @Test
    void shouldNotConfirmWrongNameRequest() {
        cardDelivery.openBrowser();
        cardDelivery.sendWrongNameRequest();
        cardDelivery.findRejectPersonNameMessage();
    }

    @Test
    void shouldNotConfirmRequest() {
        cardDelivery.openBrowser();
        cardDelivery.sendEmptyFieldRequest();
        cardDelivery.findRejectMessage();
    }

    @Test
    void shouldNotConfirmWrongDataRequest() {
        cardDelivery.openBrowser();
        cardDelivery.sendWrongDataRequest();
        cardDelivery.findRejectCityValueMessage();
    }

    @Test
    void shouldNotConfirmWrongDateRequest() {
        cardDelivery.openBrowser();
        cardDelivery.sendSpecialDateRequest();
        cardDelivery.findRejectDateFieldMessage();
    }
}
