package com.example.license.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecretKeyMismatchException extends RuntimeException implements GraphQLError {

    private Map<String, Object> extensions = new HashMap<>();

    public SecretKeyMismatchException(String message, String invalidSecretKey) {
        super(message);
        extensions.put("invalidSecretKey", invalidSecretKey);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return extensions;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DataFetchingException;
    }
}