package src.unigate;

public class ProtocolEventType {
    private ProtocolEventType() {
    }

    public static final String CREATE_EVENT = "create";
    public static final String EDIT_EVENT = "edit";
    public static final String DELETE_EVENT = "delete";
    public static final String LOGIN_EVENT = "login";
    public static final String LOGOUT_EVENT = "logout";
    public static final String INTERRUPT_EVENT = "interrupt";
    public static final String LOCK_EVENT = "lock";
    public static final String UNLOCK_EVENT = "unlock";
    public static final String LOGICAL_DELETE_EVENT = "logical_delete";
    public static final String RESTORE_EVENT = "restore";
}
