package com.xfoss.gracefulshuts;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UPSStatusController {
    private final UPSStatusRepository repo;

    UPSStatusController(UPSStatusRepository repository) {
        repo = repository;
    }

    @GetMapping("/ups-status-list/{UPSId}")
    List<UPSStatus> all(@PathVariable UUID UPSId) {
        return repo.findByUpsId(UPSId);
    }
}
