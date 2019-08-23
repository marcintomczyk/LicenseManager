package com.example.license.model.enums;

public enum UpdateLicenseStatus {
    OK("OK"),
    UNHEALTHY("UNHEALTHY");

    private String code;

    private UpdateLicenseStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
