package src;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import src.unigate.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class DeletedUsernamesMain
{
    public static void main(final String[] args) {
        System.err.println("Current args:" + Arrays.toString(args));

        String path = args[0];

        try {
            DeletedUsernameRepository deletedUsernameRepository = new DeletedUsernameRepository();
            UserRepository userRepository = new UserRepository();
            DeletedUsernameController deletedUsernameController = new DeletedUsernameController(deletedUsernameRepository, userRepository);

            DeletedUsernameInfo deletedUsernameInfo = parseJson(path, DeletedUsernameInfo.class);

            IdResponse<String> response = deletedUsernameController.createCard(deletedUsernameInfo);

            System.out.printf("Added deleted username with id = %s.%n", response.getId());
        } catch (Exception e) {
            System.err.println("Error: ");
            e.printStackTrace();
        }

        System.out.println("Done.");
    }

    public static <T> T parseJson(String jsonPath, Class<T> type) throws Exception {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(jsonPath));
            String json = new String(encoded, StandardCharsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(json, type);
        } catch (RuntimeException e) {
            throw new Exception(e);
        }
    }
}

