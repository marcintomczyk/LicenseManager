package com.example.license.model.view.input;

import com.example.license.model.enums.UpdateLicenseStatus;

public class UpdateLicenseStatusInput {

    private Long id;
    private UpdateLicenseStatus status;
    private String secretKey;

    public UpdateLicenseStatusInput(){}

    public UpdateLicenseStatusInput(Long id, UpdateLicenseStatus status, String secretKey){
        this.id = id;
        this.status = status;
        this.secretKey = secretKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UpdateLicenseStatus getStatus() {
        return status;
    }

    public void setStatus(UpdateLicenseStatus status) {
        this.status = status;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
