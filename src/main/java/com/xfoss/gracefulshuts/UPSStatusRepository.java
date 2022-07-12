package com.xfoss.gracefulshuts;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
interface UPSStatusRepository extends JpaRepository<UPSStatus, Long> {
    public List<UPSStatus> findByUpsId(UUID UpsId);
}
