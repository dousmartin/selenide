package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class AppCardDeliverySecondTest {
    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldBeSuccessfullyCompleted() {
        open("http://localhost:9999");
        String city = "Рязань";
        int dayToAdd = 7;
        int defaultAddedDays = 3;
        $("[data-test-id='city'] input").setValue(city.substring(0, 2));
        $$(".menu-item__control").findBy(text(city)).click();
        $("[data-test-id='date'] input").click();
        if (!generateDate(defaultAddedDays, "MM").equals(generateDate(dayToAdd, "MM"))) {
            $("[data-step='1']").click();
        }
        $$(".calendar__day").findBy(text(generateDate(dayToAdd, "d"))).click();
        $("[data-test-id='name'] input").setValue("Склодовская-Кюри Мария");
        $("[data-test-id='phone'] input").setValue("+79270000000");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирован на " + generateDate(dayToAdd, "dd.MM.yyyy")));
    }
}