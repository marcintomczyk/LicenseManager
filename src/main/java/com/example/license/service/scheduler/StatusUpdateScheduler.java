package com.example.license.service.scheduler;

import com.example.license.service.StatusUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@ConditionalOnProperty(value = "app.scheduling.enabled", havingValue = "true", matchIfMissing = false)
public class StatusUpdateScheduler {

    @Autowired
    private StatusUpdater statusUpdater;

    @Scheduled(fixedRate = 60000)
    public void updateLicensesStatus() {
        ZonedDateTime fiveMinutesOld = ZonedDateTime.now().minusMinutes(5L);

        statusUpdater.updateLicensesStatusFromOkToStale(fiveMinutesOld);
    }

}