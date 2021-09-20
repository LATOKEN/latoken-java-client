package com.latoken.api.client.v3;

import com.latoken.api.client.v2.response.Error;

public final class LatokenApiException extends RuntimeException {
    private Error latokenErrorDetails;

    public LatokenApiException() {
        super();
    }

    public LatokenApiException(String message) {
        super(message);
    }

    public LatokenApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public LatokenApiException(Throwable cause) {
        super(cause);
    }

    public LatokenApiException(Error error) {
        this.latokenErrorDetails = error;
    }

    public LatokenApiException(String message, Error error) {
        super(message);
        this.latokenErrorDetails = error;
    }

    public boolean hasDetails() {
        return latokenErrorDetails != null;
    }

    public Error getLatokenErrorDetails() {
        return latokenErrorDetails;
    }

    public void setLatokenErrorDetails(Error latokenErrorDetails) {
        this.latokenErrorDetails = latokenErrorDetails;
    }

    @Override
    public String toString() {
        String str = super.toString();
        if (this.hasDetails()) {
            return "LatokenApiException{latokenErrorDetails=" + latokenErrorDetails + "}\n" + str;
        } else {
            return str;
        }
    }
}
