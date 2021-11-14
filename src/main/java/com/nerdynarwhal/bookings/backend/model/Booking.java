package com.nerdynarwhal.bookings.backend.model;

import org.apiguardian.api.API;

import java.time.LocalDate;
import java.util.UUID;

@API(since = "1.0", status = API.Status.STABLE)
public record Booking(
        UUID id,
        UUID bookerId,
        UUID bookableId,
        String title,
        LocalDate date,
        BookableSession session) {
}
