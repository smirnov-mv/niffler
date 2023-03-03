package niffler.model;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum CategoryValues {
    RESTAURANTS("Рестораны"),
    PRODUCTS("Продукты"),
    STUDY("Обучение"),
    PUB("Бар");

    private final String categoryName;

    CategoryValues(String categoryName) {
        this.categoryName = categoryName;
    }

    @JsonValue
    public String getCategoryName() {
        return categoryName;
    }

    public static CategoryValues getByName(String categoryName) {
        return Arrays.stream(values())
                .filter(x -> x.getCategoryName().equals(categoryName))
                .findFirst().orElse(null);
    }


}
