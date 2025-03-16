package UniFest.domain;

public class Device {
    private String deviceId;

    private Device(String deviceId) {
        this.deviceId = validate(deviceId);
    }

    public static Device of(String deviceId) {
        return new Device(deviceId);
    }

    public String getDeviceId() {
        return deviceId;
    }

    private String validate(String deviceId) {
        if (deviceId == null || deviceId.isEmpty()) {
            throw new IllegalArgumentException("Device ID cannot be null or empty");
        }
        return deviceId;
    }

    @Override
    public String toString() {
        return deviceId;
    }
}
