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
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
class UPSController {
    private final UPSRepository repo;

    UPSController(UPSRepository repository) {
        repo = repository;
    }

    @GetMapping("/gs/api/ups-list")
    List<UPS> all() {
        return repo.findAll();
    }

    @GetMapping("/gs/api/ups/{id}")
    EntityModel<UPS> one(@PathVariable UUID id) {
        UPS ups = repo.findById(id)
            .orElseThrow(() -> new UPSNotFoundException(id));

        return EntityModel.of(ups,
                linkTo(methodOn(UPSController.class).one(id)). withSelfRel(),
                linkTo(methodOn(UPSController.class).all()).withRel("/ups-list")
                );
    }
}
