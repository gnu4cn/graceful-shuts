package com.xfoss.gracefulshuts;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UPSController {
    private final UPSRepository repo;

    UPSController(UPSRepository repository) {
        repo = repository;
    }

    @GetMapping("/ups-list")
    List<UPS> all() {
        return repo.findAll();
    }
}
