package com.happing.one.domain.enumeration;


public enum  HttpStatusCoustom {
    EXECUTION_ERROR(512, "Execution Error"),
    EXECUTION_SUCCESS(200, "Execution Success");

    private final int value;
    private final String reasonPhrase;

    private HttpStatusCoustom(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int value() {
        return this.value;
    }
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

}
