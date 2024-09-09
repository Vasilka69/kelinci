package src;

import com.fasterxml.jackson.databind.ObjectMapper;
import src.unigate.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class AttributesMain
{
    public static void main(final String[] args) {
        System.out.println("Current args:" + Arrays.toString(args));

        String path = args[0];

        try {
            AttributeMapperDto attributeMapperDto = new AttributeMapperDto();
            AttributeService attributeService = new DefaultAttributeService();
            AttributeController attributeController = new AttributeController(attributeMapperDto, attributeService);

            AttributeInfo attributeInfo = parseJson(path, AttributeInfo.class);

            IdResponse<String> response = attributeController.addAttributeCard(attributeInfo);

            System.out.printf("Added attribute card with id = %s.%n", response.getId());
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
            return new ObjectMapper().readValue(json, type);
        } catch (RuntimeException e) {
            throw new Exception(e);
        }
    }
}

