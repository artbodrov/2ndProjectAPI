package core.setting;

public enum ApiEndpoints {
    PING("/ping"),
    BOOKING("/booking");

    private final String path;

    ApiEndpoints(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
