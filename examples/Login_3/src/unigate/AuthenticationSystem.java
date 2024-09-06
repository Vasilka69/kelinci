package src.unigate;

public enum AuthenticationSystem {
    UNIGATE("Юнигейт"),
    ESIA ("Единая система идентификации и аутентификации"),
    ;

    private final String name;

    public String getName() {
        return name;
    }

    AuthenticationSystem(String name) {
        this.name = name;
    }
}
