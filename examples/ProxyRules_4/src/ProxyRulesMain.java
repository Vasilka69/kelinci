package src;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import src.unigate.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class ProxyRulesMain
{
    public static void main(final String[] args) {
        System.out.println("Current args:" + Arrays.toString(args));

        String path = args[0];

        try {
            ExpressionParser expressionParser = new SpelExpressionParser();
            SpelParserConfiguration spelParserConfiguration = new SpelParserConfiguration();
            ProxyRuleContext validationContext = new ProxyRuleValidationContext();

            ProxyRuleService proxyRuleService = new DefaultProxyRuleService();
            ProxyRuleMapperDto mapperDto = new ProxyRuleMapperDto();
            SpelValidationService spelValidationService = new SpelValidationService(expressionParser, spelParserConfiguration, validationContext);

            ProxyRulesController proxyRulesController = new ProxyRulesController(proxyRuleService, mapperDto, spelValidationService);

            ProxyRuleInfo proxyRuleInfo = parseJson(path, ProxyRuleInfo.class);

            IdResponse<Integer> response = proxyRulesController.createCard(proxyRuleInfo);

            System.out.printf("Added proxy rule with id = %s.%n", response.getId());
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

