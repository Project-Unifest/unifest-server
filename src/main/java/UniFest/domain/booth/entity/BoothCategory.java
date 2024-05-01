package UniFest.domain.booth.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BoothCategory {

    BAR("BAR"),
    FOOD("FOOD"),
    EVENT("EVENT"),
    NORMAL("NORMAL"),
    MEDICAL("MEDICAL"),
    TOILET("TOILET");


    private final String value;

    BoothCategory(final String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
