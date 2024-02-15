package src;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class XLS
{
    public static void main(final String[] args) throws IOException, InvalidFormatException {
        System.err.println("Current args:" + Arrays.toString(args));

        File file = new File(args[0]);
        try {
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

