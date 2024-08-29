package src.unigate;

public interface ProxyRuleContext {
    String getHeader(String headerName);

    String getParam(String paramName);

    String getParam(String paramName, int paramIndex);

    String getPathVariable(String pathVarName);

    String getUserAttribute(String attrName);

    String getUserAttribute(String attrName, int attrIndex);

    String getUserId();

    String getUserFullname();

    boolean hasHeader(String headerName);

    boolean hasParam(String paramName);

    boolean hasPathVariable(String pathVarName);

    boolean hasParamValue(String paramName, String value);

    boolean hasPermission(String resourceId, String actionId);

    boolean hasRole(String roleId);

    boolean hasUserAttributeValue(String attrName, String attrValue);
    
}
