package UniFest.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum MemberRole {
    ADMIN("ADMIN"),
    NORMAL("NORMAL"),
    VERIFIED("VERIFIED");

    private final String value;

    MemberRole(final String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return value;
    }
}
