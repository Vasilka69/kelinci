package src.unigate;

public enum IdPrefix {
    APPLICATION("apps"),
    SECURITY_OBJECT("objects"),
    ATTRIBUTE("attributes"),
    PERMISSION("permission");

    IdPrefix(String prefix) {
        this.prefix = prefix;
    }

    private final String prefix;

    public String getPrefix() {
        return prefix;
    }
}
