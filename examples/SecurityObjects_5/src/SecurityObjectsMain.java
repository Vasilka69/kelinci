package src;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import src.unigate.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class SecurityObjectsMain
{
    public static void main(final String[] args) {
        System.err.println("Current args:" + Arrays.toString(args));

        String path = args[0];

        try {
            SecurityObjectRepository securityObjectRepository = new SecurityObjectRepository();
            SecurityObjectTypeRepository securityObjectTypeRepository = new SecurityObjectTypeRepository();

            SecurityObjectValidator securityObjectValidator = new SecurityObjectValidator(securityObjectRepository, securityObjectTypeRepository);

            SecurityObjectService securityObjectService = new DefaultSecurityObjectService(securityObjectValidator);
            SecurityObjectMapperDto securityObjectMapperDto = new SecurityObjectMapperDto();

            SecurityObjectController securityObjectController = new SecurityObjectController(securityObjectService, securityObjectMapperDto, securityObjectTypeRepository);

            SecurityObjectFullInfo securityObjectFullInfo = parseJson(path, SecurityObjectFullInfo.class);

            IdResponse<String> response = securityObjectController.addSecurityObjectCard(securityObjectFullInfo);

            System.out.printf("Added security object with id = %s.%n", response.getId());
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

