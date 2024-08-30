package UniFest.domain.menu.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MenuStatus {

    ENOUGH("ENOUGH"),
    UNDER_50("UNDER_50"),
    UNDER_10("UNDER_10"),
    SOLD_OUT("SOLD_OUT");

    private final String value;

    MenuStatus(final String value){
        this.value = value;
    }

    @JsonValue
    public String getValue(){
        return value;
    }
}
