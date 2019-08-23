package com.example.license.model.enums;

public enum LicenseStatus {
    NEW("NEW"),
    OK("OK"),
    STALE("STALE"),
    UNHEALTHY("UNHEALTHY");

    private String code;

    private LicenseStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
