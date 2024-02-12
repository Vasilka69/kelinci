package src;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XLS
{
    public static void main(final String[] args) {
        System.err.println("Current args:" + Arrays.toString(args));

        File file = new File(args[0]);

        IndividualPersonImportServiceImpl individualPersonImportService = new IndividualPersonImportServiceImpl();
        List<IndividualPersonImport> persons = individualPersonImportService.parseUsersFromExcel(file);

        System.out.println(persons);
        System.out.printf("Imported %d person(s).%n", persons.size());
    }

    static class IndividualPersonImport {

        public String login;
        public String firstName;
        public String lastName;
        public String patronymic;
        public String position;
        public String email;
        public String password;
        public String role;

        public IndividualPersonImport() {
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

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

        @Override
        public String toString() {
            return "IndividualPersonImport{" +
                    "login='" + login + '\'' +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", patronymic='" + patronymic + '\'' +
                    ", position='" + position + '\'' +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    ", role='" + role + '\'' +
                    '}';
        }

        public void setPatronymic(String patronymic) {
            this.patronymic = patronymic;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
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

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}
