package com.xfoss.gracefulshuts;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;

public abstract class AbstractEntity {
    @Id 
    @Column(name = "id", unique = true, length = 16, nullable = false)
    private UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }
}
