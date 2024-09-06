package src;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.connector.Request;
import src.unigate.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;

public class LoginMain
{
    public static void main(final String[] args) {
        System.err.println("Current args:" + Arrays.toString(args));

        String path = args[0];

        try {
            

            JWTLoginFilter jwtLoginFilter = new JWTLoginFilter();

            SystemCredentials systemCredentials = parseJson(path, SystemCredentials.class);

            ServletRequest request = new ;
            // HeaderWriterRequest
            ServletResponse response = null;
            // HeaderWriterResponse
            FilterChain chain = null;
            // VirtualFilterChain

            jwtLoginFilter.doFilter(request, response, chain);

            System.out.printf("User with username = %s, password = %s was logged in%n", systemCredentials.getUsername(), systemCredentials.getPassword());
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

