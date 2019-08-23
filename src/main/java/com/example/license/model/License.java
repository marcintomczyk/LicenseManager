package com.example.license.model;

import com.example.license.model.enums.LicenseStatus;
import com.example.license.model.view.input.CreateLicenseInput;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name="License")
public class License implements Serializable {

    private static final long serialVersionUID = -2175011056704090820L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="secret_key")
    private String secretKey;

    @Column(name="status")
    private LicenseStatus status;

    @Version
    @Column(name="version")
    private Long version;

    @Column(name="last_update")
    private ZonedDateTime lastUpdate;

    public License() {
    }

    public License(String name, String secretKey) {
        this.name = name;
        this.secretKey = secretKey;
    }

    public License(CreateLicenseInput input) {
        this.name = input.getName();
        this.secretKey = input.getSecretKey();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        License device = (License) o;

        return id.equals(device.id);
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

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public LicenseStatus getStatus() {
        return status;
    }

    public void setStatus(LicenseStatus status) {
        this.status = status;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public ZonedDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(ZonedDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "License{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }


    @PrePersist
    void prePersist() {
        setCurrentTime();

        if (this.status == null)
            this.status = LicenseStatus.NEW;
    }

    @PreUpdate
    public void preUpdate() {
        setCurrentTime();
    }

    private void setCurrentTime() {
        if (this.lastUpdate == null)
            this.lastUpdate = ZonedDateTime.now();


    }

}