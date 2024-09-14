package com.example.inventory.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Availability {
    private boolean isAvailable;

    public Availability(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
