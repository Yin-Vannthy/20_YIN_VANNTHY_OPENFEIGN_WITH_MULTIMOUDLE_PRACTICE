package com.api.configuration;

import com.api.exception.CustomException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ServiceErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ErrorDecoder defaultDecoder = new ErrorDecoder.Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        String detail = getErrorDetail(response);

        return switch (response.status()) {
            case 400 -> new CustomException("Bad Request from Service. " + detail);
            case 404 -> new CustomException("Resource Not Found. " + detail);
            case 500 -> new CustomException("Internal Server Error from Service. " + detail);
            default -> defaultDecoder.decode(methodKey, response);
        };
    }

    private String getErrorDetail(Response response) {
        try {
            if (response.body() != null) {
                String errorBody = new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
                JsonNode errorJson = objectMapper.readTree(errorBody);
                return errorJson.path("detail").asText("Unable to retrieve details from response.");
            }
        } catch (IOException e) {
            return "Unable to read error response";
        }
        return "No additional error details available.";
    }
}
