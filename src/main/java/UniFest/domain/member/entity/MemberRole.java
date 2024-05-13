package UniFest.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum MemberRole {
    DEV("DEV"),
    ADMIN("ADMIN"),
    PENDING("PENDING"),
    VERIFIED("VERIFIED"),
    DENIED("DENIED");

    private final String value;

    MemberRole(final String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return value;
    }
}
