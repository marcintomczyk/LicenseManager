package com.example.license.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.license.model.License;
import com.example.license.model.enums.LicenseStatus;
import com.example.license.model.view.output.LicenseSecretView;
import com.example.license.model.view.output.LicenseSimpleView;
import com.example.license.repository.LicenseRepository;


import java.util.List;
import java.util.stream.Collectors;

public class Query implements GraphQLQueryResolver {
    private LicenseRepository licenseRepository;

    public Query(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    public Iterable<LicenseSimpleView> getLicenses() {
        List<License> licenses = licenseRepository.findAll();

        List<LicenseSimpleView> simpleLicenses =
                licenses.stream()
                        .map(license -> new LicenseSimpleView(license.getId(), license.getName(),
                                license.getStatus(), license.getVersion()))
                        .collect(Collectors.toList());

        return simpleLicenses;
    }

    public Iterable<LicenseSimpleView> getLicensesByStatus(LicenseStatus status) {
        List<License> licenses = licenseRepository.findAllByStatus(status);
        List<LicenseSimpleView> simpleLicenses =
                licenses.stream()
                        .map(license -> new LicenseSimpleView(license.getId(), license.getName(),
                                license.getStatus(), license.getVersion()))
                        .collect(Collectors.toList());

        return simpleLicenses;
    }


    public LicenseSecretView getLicense(Long id) {
        License license = licenseRepository.findById(id).get();
        LicenseSecretView licenseSecretView = new LicenseSecretView(license.getId(), license.getName(),
                license.getStatus(), license.getSecretKey(), license.getVersion());
        return licenseSecretView;
    }
}

