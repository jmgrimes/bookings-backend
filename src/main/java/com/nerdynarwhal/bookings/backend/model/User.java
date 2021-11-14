package com.nerdynarwhal.bookings.backend.model;

import org.apiguardian.api.API;

import java.util.Optional;
import java.util.UUID;

@API(since = "1.0", status = API.Status.STABLE)
public record User(
        UUID id,
        String name,
        String title,
        Optional<String> notes,
        String image) {
}
