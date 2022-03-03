import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    @Test
    void shouldRegisterCardDelivery() {

        Date deliveryDate = new Date();
        SimpleDateFormat formatForDeliveryDate = new SimpleDateFormat("E dd.MM.yyyy");

        open ("http://localhost:9999");
        SelenideElement form = $(By.className("form"));
        $("[Data-test-id=city] input").setValue("Саратов");
        $("[Data-test-id=date] input").setValue(String.valueOf(deliveryDate));
        $("[Data-test-id=name] input").setValue("Петрова-Иванова Ольга");
        $("[Data-test-id=phone] input").setValue("+79998886677");
        $(By.cssSelector("[class='checkbox__box']")).click();
        $$("button").find(exactText("Забронировать")).click();;
        $(withText("Успешно")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldNotRegisterWrongCity() {

        Date deliveryDate = new Date();
        SimpleDateFormat formatForDeliveryDate = new SimpleDateFormat("E dd.MM.yyyy");

        open ("http://localhost:9999");
        SelenideElement form = $(By.className("form"));
        $("[Data-test-id=city] input").setValue("Париж");
        $("[Data-test-id=date] input").setValue(String.valueOf(deliveryDate));
        $("[Data-test-id=name] input").setValue("Петрова-Иванова Ольга");
        $("[Data-test-id=phone] input").setValue("+79998886677");
        $(By.cssSelector("[class='checkbox__box']")).click();
        $$("button").find(exactText("Забронировать")).click();;
        $(withText("Доставка в выбранный город недоступна")).shouldBe(visible);
    }
    @Test
    void shouldNotRegisterWrongName() {

        Date deliveryDate = new Date();
        SimpleDateFormat formatForDeliveryDate = new SimpleDateFormat("E dd.MM.yyyy");

        open ("http://localhost:9999");
        SelenideElement form = $(By.className("form"));
        $("[Data-test-id=city] input").setValue("Москва");
        $("[Data-test-id=date] input").setValue(String.valueOf(deliveryDate));
        $("[Data-test-id=name] input").setValue("Petrova Olga");
        $("[Data-test-id=phone] input").setValue("+79998886677");
        $(By.cssSelector("[class='checkbox__box']")).click();
        $$("button").find(exactText("Забронировать")).click();;
        $(withText("Имя и Фамилия указаные неверно")).shouldBe(visible);
    }
    @Test
    void shouldNotRegisterWrongPhone() {

        Date deliveryDate = new Date();
        SimpleDateFormat formatForDeliveryDate = new SimpleDateFormat("E dd.MM.yyyy");

        open ("http://localhost:9999");
        SelenideElement form = $(By.className("form"));
        $("[Data-test-id=city] input").setValue("Москва");
        $("[Data-test-id=date] input").setValue(String.valueOf(deliveryDate));
        $("[Data-test-id=name] input").setValue("Петрова Ольга");
        $("[Data-test-id=phone] input").setValue("+799988866");
        $(By.cssSelector("[class='checkbox__box']")).click();
        $$("button").find(exactText("Забронировать")).click();;
        $(withText("Телефон указан неверно")).shouldBe(visible);
    }
}
