package com.api.configuration;

import com.api.exception.CustomException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class ServiceErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder = new ErrorDecoder.Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        // Check the status code and handle the error accordingly
        return switch (response.status()) {
            case 400 ->
                // Bad request, you can return a custom exception
                    new CustomException("Bad Request from Service: " + methodKey);
            case 404 ->
                // Resource not found
                    new CustomException("Resource Not Found: " + methodKey);
            case 500 ->
                // Internal server error
                    new CustomException("Internal Server Error");
            default ->
                // Use the default decoder for other status codes
                    defaultDecoder.decode(methodKey, response);
        };
    }
}
