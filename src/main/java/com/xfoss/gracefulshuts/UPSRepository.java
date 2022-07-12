package com.xfoss.gracefulshuts;

import java.util.UUID;
import java.util.Optional;

// import java.net.InetAddress;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
interface UPSRepository extends JpaRepository<UPS, UUID> {
    Optional<UPS> findOneByNameFQDN(String nameFQDN);
}
