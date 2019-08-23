package com.example.license.service;

import com.example.license.model.License;
import com.example.license.model.enums.LicenseStatus;
import com.example.license.repository.LicenseRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
public class UpdateStatusTests {

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private StatusUpdater statusUpdater;

    /*
    for more complex cases/tests we could use dbUnit or initialize db from file
    */

        @Before
        public void setUp() {
            License license1 = new License("license1", "xxxx-1111");
            license1.setStatus(LicenseStatus.OK);
            License license2 = new License("license2", "xxxx-2222");
            License license3 = new License("license3", "xxxx-3333");
            license3.setStatus(LicenseStatus.OK);

            licenseRepository.save(license1);
            licenseRepository.save(license2);
            licenseRepository.save(license3);
        }

        @Test()
        public void isStatusUpdatedToStaleForLicensesOlderThanOneMinuteAndOkStatus() {

            License license1 = licenseRepository.findById(1L).get();
            License license2 = licenseRepository.findById(2L).get();
            License license3 = licenseRepository.findById(3L).get();

            assertEquals(LicenseStatus.OK, license1.getStatus());
            assertEquals(LicenseStatus.NEW, license2.getStatus());
            assertEquals(LicenseStatus.OK, license3.getStatus());

            Integer noOfUpdated = statusUpdater.updateLicensesStatusFromOkToStale(ZonedDateTime.now().plusMinutes(1));

            assertEquals(2, noOfUpdated.intValue());
            license1 = licenseRepository.findById(1L).get();
            license2 = licenseRepository.findById(2L).get();
            license3 = licenseRepository.findById(3L).get();
            assertEquals(LicenseStatus.STALE, license1.getStatus());
            assertEquals(LicenseStatus.NEW, license2.getStatus());
            assertEquals(LicenseStatus.STALE, license3.getStatus());

        }


}
