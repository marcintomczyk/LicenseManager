package com.example.license.model.view.output;

import com.example.license.model.enums.LicenseStatus;

public class LicenseSecretView extends LicenseSimpleView {

    private String secretKey;

    public LicenseSecretView() {
    }

    public LicenseSecretView(Long id, String name, LicenseStatus status, String secretKey, Long version) {
        super(id, name, status, version);
        this.secretKey = secretKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}