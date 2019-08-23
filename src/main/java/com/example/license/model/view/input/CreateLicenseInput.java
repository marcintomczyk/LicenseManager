package com.example.license.model.view.input;

public class CreateLicenseInput {
    private String name;
    private String secretKey;

    public CreateLicenseInput(){}

    public CreateLicenseInput(String name, String secretKey){
        this.name = name;
        this.secretKey = secretKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
