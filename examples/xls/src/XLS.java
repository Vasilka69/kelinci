package src;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class XLS
{
    public static void main(final String[] args) {
        System.err.println("Current args:" + Arrays.toString(args));

        try {
            File file = new File(args[0]);
            IndividualPersonImportServiceImpl individualPersonImportService = new IndividualPersonImportServiceImpl();
            List<IndividualPersonImport> persons = individualPersonImportService.parseUsersFromExcel(file);

            System.out.printf("Imported %d person(s).%n", persons.size());
        } catch (Exception e) {
            System.err.println("Error reading xls: ");
            e.printStackTrace();
        }

        System.out.println("Done.");
    }
}

