package src.unigate;

public class ProxyRuleValidationContext implements ProxyRuleContext {
    @Override
    public String getHeader(String headerName) {
        return null;
    }

    @Override
    public String getParam(String paramName) {
        return null;
    }

    @Override
    public String getParam(String paramName, int paramIndex) {
        return null;
    }

    @Override
    public String getPathVariable(String pathVarName) {
        return null;
    }

    @Override
    public String getUserAttribute(String attrName) {
        return null;
    }

    @Override
    public String getUserAttribute(String attrName, int attrIndex) {
        return null;
    }

    @Override
    public String getUserId() {
        return null;
    }

    @Override
    public String getUserFullname() {
        return null;
    }

    @Override
    public boolean hasHeader(String headerName) {
        return false;
    }

    @Override
    public boolean hasParam(String paramName) {
        return false;
    }

    @Override
    public boolean hasPathVariable(String pathVarName) {
        return false;
    }

    @Override
    public boolean hasParamValue(String paramName, String value) {
        return false;
    }

    @Override
    public boolean hasPermission(String resourceId, String actionId) {
        return false;
    }

    @Override
    public boolean hasRole(String roleId) {
        return false;
    }

    @Override
    public boolean hasUserAttributeValue(String attrName, String attrValue) {
        return false;
    }
}
