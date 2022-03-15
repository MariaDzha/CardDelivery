import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    String generateDate(int days) {
    return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
}
    String deliveryDate = generateDate(3);

    @Test
    void shouldRegisterCardDelivery() {

        open ("http://localhost:9999");
        $("[Data-test-id=city] input").setValue("Саратов");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[Data-test-id=date] input").setValue(deliveryDate);
        $("[Data-test-id=name] input").setValue("Петрова-Иванова Ольга");
        $("[Data-test-id=phone] input").setValue("+79998886677");
        $(By.cssSelector("[class='checkbox__box']")).click();
        $$("button").find(exactText("Забронировать")).click();;
        $("[class='notification__content']").shouldHave(Condition.text("Встреча успешно забронирована на " + deliveryDate), Duration.ofSeconds(15));
    }

    @Test
    void shouldNotRegisterWrongCity() {

        open ("http://localhost:9999");
        $("[Data-test-id=city] input").setValue("Париж");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[Data-test-id=date] input").setValue(deliveryDate);
        $("[Data-test-id=name] input").setValue("Петрова-Иванова Ольга");
        $("[Data-test-id=phone] input").setValue("+79998886677");
        $(By.cssSelector("[class='checkbox__box']")).click();
        $$("button").find(exactText("Забронировать")).click();;
        $(withText("Доставка в выбранный город недоступна")).shouldBe(visible);
    }
    @Test
    void shouldNotRegisterWrongName() {

        open ("http://localhost:9999");
        $("[Data-test-id=city] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[Data-test-id=date] input").setValue(deliveryDate);
        $("[Data-test-id=name] input").setValue("Petrova Olga");
        $("[Data-test-id=phone] input").setValue("+79998886677");
        $(By.cssSelector("[class='checkbox__box']")).click();
        $$("button").find(exactText("Забронировать")).click();;
        $(withText("Имя и Фамилия указаные неверно")).shouldBe(visible);
    }
    @Test
    void shouldNotRegisterWrongPhone() {

        open ("http://localhost:9999");
        $("[Data-test-id=city] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[Data-test-id=date] input").setValue(deliveryDate);
        $("[Data-test-id=name] input").setValue("Петрова Ольга");
        $("[Data-test-id=phone] input").setValue("+799988866");
        $(By.cssSelector("[class='checkbox__box']")).click();
        $$("button").find(exactText("Забронировать")).click();;
        $(withText("Телефон указан неверно")).shouldBe(visible);
    }
}
