package com.example.license.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.license.model.License;
import com.example.license.exception.LicenseNotFoundException;
import com.example.license.exception.SecretKeyMismatchException;
import com.example.license.model.enums.LicenseStatus;
import com.example.license.model.view.input.CreateLicenseInput;
import com.example.license.model.view.input.UpdateLicenseStatusInput;
import com.example.license.repository.LicenseRepository;

import java.util.Optional;

public class Mutation implements GraphQLMutationResolver {
    private LicenseRepository licenseRepository;

    public Mutation(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    public License newLicense(CreateLicenseInput input) {
        License license = new License(input);
        licenseRepository.save(license);
        return license;
    }


    // validation is on schema level
    public License updateLicenseStatus(UpdateLicenseStatusInput input) {
        Long id = input.getId();

        Optional<License> licenseOpt = licenseRepository.findById(id);

        if(licenseOpt.isPresent()) {
            License license = licenseOpt.get();
            String secretKey = input.getSecretKey();

            if(!secretKey.equals(license.getSecretKey())) {
                throw new SecretKeyMismatchException("SecretKeys don't match", secretKey);
            }
            license.setStatus(LicenseStatus.valueOf(input.getStatus().name()));
            licenseRepository.save(license);

            return license;

        } else {
            throw new LicenseNotFoundException("The license to be updated was not found", id);
        }


    }


}