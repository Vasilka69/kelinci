import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class CSV
{
    public static void main(final String[] args) {

        if (args.length != 1) {
            System.err.println("Current args:" + Arrays.toString(args));
            System.err.println("Usage: CSV <input csv file>");
            return;
        }

        try {
            String fileName = args[0];
            Reader myReader = new FileReader(fileName);
            CsvMapper mapper = new CsvMapper();

            CsvSchema schema = mapper.schemaFor(IndividualPersonImport.class)
                    .withColumnSeparator(';')
                    .withSkipFirstDataRow(true);

            MappingIterator<IndividualPersonImport> iterator = mapper
                    .readerFor(IndividualPersonImport.class)
                    .with(schema)
                    .readValues(myReader);

            List<IndividualPersonImport> persons = iterator.readAll();

            System.out.printf("Imported %d person(s)", persons.size());
        } catch (Exception e) {
            System.err.println("Error reading csv");
            e.printStackTrace();
        }

        System.out.println("Done.");
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
