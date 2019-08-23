package com.example.license.db;


import com.example.license.model.License;
import com.example.license.model.enums.LicenseStatus;
import com.example.license.repository.LicenseRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class LicenseManagerDBTests {

    @Autowired
    private LicenseRepository licenseRepository;

    /*
    for more complex cases/tests we could use dbUnit or initialize db from file
     */
    @Before
    public void setUp() {
        License license = new License("license1", "xxxx-1111");
        licenseRepository.save(license);
    }

    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void testOptimisticLockingWhileUpdatingLicenseStatus() throws IOException {

        License license1 = licenseRepository.findById(1L).get();
        License license2 = licenseRepository.findById(1L).get();

        license1.setStatus(LicenseStatus.UNHEALTHY);
        license2.setStatus(LicenseStatus.STALE);

        assertEquals(0, license1.getVersion().intValue());
        assertEquals(0, license2.getVersion().intValue());

        licenseRepository.save(license1);
        licenseRepository.save(license2);

    }


}