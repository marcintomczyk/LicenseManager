package com.example.license.model.view.output;

import com.example.license.model.enums.LicenseStatus;

public class LicenseSimpleView {

    private Long id;
    private String name;
    private String status;
    private Long version;

    public LicenseSimpleView() {
    }

    public LicenseSimpleView(Long id, String name, LicenseStatus status, Long version) {
        this.id = id;
        this.name = name;
        this.status = status.getCode();
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(LicenseStatus status) {
        this.status = status.getCode();
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}