package com.latoken.api.client.v2.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

@Data 
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Error {

    private boolean result;
    private String message;
    private ErrorCode error;
    private Status status;
    private Map<String, String> errors;

    public enum ErrorCode {
        /**
         * Internal server error. You can contact our support to solve this problem.
         */
        INTERNAL_ERROR,
        /**
         * Requested information currently not available. You can contact our support to solve this problem or retry later.
         */
        SERVICE_UNAVAILABLE,
        /**
         * User's query not authorized. Check if you are logged in.
         */
        NOT_AUTHORIZED,
        /**
         * You don't have enough access rights.
         */
        FORBIDDEN,
        /**
         * Some bad request, for example bad fields values or something else. Read response message for more information.
         */
        BAD_REQUEST,
        /**
         * Entity not found. Read message for more information.
         */
        NOT_FOUND,
        /**
         * Access is denied. Probably you don't have enough access rights, you contact our support.
         */
        ACCESS_DENIED,
        /**
         * User's request rejected for some reasons. Check error message.
         */
        REQUEST_REJECTED,
        /**
         * Http media type not supported.
         */
        HTTP_MEDIA_TYPE_NOT_SUPPORTED,
        /**
         * Media type not acceptable
         */
        MEDIA_TYPE_NOT_ACCEPTABLE,
        /**
         * One of method argument is invalid. Check argument types and error message for more information.
         */
        METHOD_ARGUMENT_NOT_VALID,
        /**
         * Check errors field to get reasons.
         */
        VALIDATION_ERROR,
        /**
         * Restore your account or create a new one.
         */
        ACCOUNT_EXPIRED,
        /**
         * Invalid username or password.
         */
        BAD_CREDENTIALS,
        /**
         * Cookie has been stolen. Let's try reset your cookies.
         */
        COOKIE_THEFT,
        /**
         * Credentials expired.
         */
        CREDENTIALS_EXPIRED,
        /**
         * For example, 2FA required.
         */
        INSUFFICIENT_AUTHENTICATION,
        /**
         * User logged from unusual location, email confirmation required.
         */
        UNKNOWN_LOCATION,
        /**
         * Too many requests at the time. A response header X-Rate-Limit-Remaining indicates the number of allowed request per a period.
         */
        TOO_MANY_REQUESTS,
    }

    public enum Status {
        FAILURE,
        SUCCESS
    }
}
