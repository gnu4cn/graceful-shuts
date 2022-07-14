package com.xfoss.gracefulshuts;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
interface PowerARepository extends JpaRepository<PowerA, Long> 
{}
