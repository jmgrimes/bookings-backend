package com.nerdynarwhal.bookings.backend.model;

import org.apiguardian.api.API;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@API(since = "1.0", status = API.Status.STABLE)
public record Bookable(
        UUID id,
        String name,
        String group,
        Optional<String> notes,
        List<BookableDay> days,
        List<BookableSession> sessions) {
}
