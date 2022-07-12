package com.xfoss.gracefulshuts;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface UPSRepository extends JpaRepository<UPS, UUID> {}
