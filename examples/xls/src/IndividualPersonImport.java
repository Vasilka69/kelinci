package src;

import java.util.List;

public class IndividualPersonImport {

    private String firstName;

    private String lastName;

    private String patronymic;

    private String post;

    private String email;

    private String password;

    private String username;

    private String rolesNames;

    private List<String> rolesIds;

    private Integer index;

    private String errorDescription;

    private String rolesIdsNotFormatted;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRolesNames() {
        return rolesNames;
    }

    public void setRolesNames(String rolesNames) {
        this.rolesNames = rolesNames;
    }

    public List<String> getRolesIds() {
        return rolesIds;
    }

    public void setRolesIds(List<String> rolesIds) {
        this.rolesIds = rolesIds;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getRolesIdsNotFormatted() {
        return rolesIdsNotFormatted;
    }

    public void setRolesIdsNotFormatted(String rolesIdsNotFormatted) {
        this.rolesIdsNotFormatted = rolesIdsNotFormatted;
    }

    @Override
    public String toString() {
        return "IndividualPersonImport{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", post='" + post + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", rolesNames='" + rolesNames + '\'' +
                ", rolesIds=" + rolesIds +
                ", index=" + index +
                ", errorDescription='" + errorDescription + '\'' +
                ", rolesIdsNotFormatted='" + rolesIdsNotFormatted + '\'' +
                '}' + "\n";
    }
}
