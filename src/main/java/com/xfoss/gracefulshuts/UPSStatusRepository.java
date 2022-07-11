package com.xfoss.gracefulshuts;

import org.springframework.data.jpa.repository.JpaRepository;

interface UPSStatusRepository extends JpaRepository<UPSStatus, Long> {}
