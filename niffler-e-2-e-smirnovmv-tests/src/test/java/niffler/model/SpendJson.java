package niffler.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import niffler.utils.DateConverter;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SpendJson(
        String id,
        Date spendDate,
        CategoryValues category,
        CurrencyValues currency,
        Double amount,
        String description,
        String username) {

    public String spendDateAsString() {
        return DateConverter.dateToString(spendDate);
    }
}