package com.example.license.service;

import com.example.license.repository.LicenseRepository;
import com.example.license.service.scheduler.StatusUpdateScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Component
public class StatusUpdater {

    private static final Logger log = LoggerFactory.getLogger(StatusUpdateScheduler.class);

    @Autowired
    private LicenseRepository licenseRepository;

    @Transactional
    public Integer updateLicensesStatusFromOkToStale(ZonedDateTime updateTime) {

        log.debug("Updating statuses of licenses older than {} ", updateTime );

        Integer noOfUpdatedEntities = licenseRepository.updateStatusToStale(updateTime);

        log.debug("Updated {} entities", noOfUpdatedEntities);

        return noOfUpdatedEntities;

    }

}
