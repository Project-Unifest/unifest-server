package UniFest.domain.waiting.entity;

public enum ReservationStatus {
    RESERVED("예약됨"),
    CALLED("호출됨"),
    COMPLETED("처리됨"),
    CANCELED("취소됨");

    private final String description;

    ReservationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
