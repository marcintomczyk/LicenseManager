package com.example.license.repository;

import com.example.license.model.License;
import com.example.license.model.enums.LicenseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;
import java.util.List;

public interface LicenseRepository extends JpaRepository<License, Long> {

    List<License> findAll();

    List<License> findAllByStatus(LicenseStatus status);

    @Modifying
    @Query("UPDATE License d SET d.status = 'STALE' WHERE d.status='OK' AND d.lastUpdate <= :last_update")
    Integer updateStatusToStale(ZonedDateTime last_update);

}
