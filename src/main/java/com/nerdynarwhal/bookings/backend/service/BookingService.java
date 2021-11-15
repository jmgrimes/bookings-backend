package com.nerdynarwhal.bookings.backend.service;

import com.nerdynarwhal.bookings.backend.model.Booking;

import org.apiguardian.api.API;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@Service
@API(since = "1.0", status = API.Status.STABLE)
public interface BookingService {

    @API(since = "1.0", status = API.Status.STABLE)
    Flux<Booking> findBookings(
            final UUID bookerId, final UUID bookableId,
            final LocalDate startDate, final LocalDate endDate
    );

    @API(since = "1.0", status = API.Status.STABLE)
    Mono<Booking> findBooking(final UUID id);

    @API(since = "1.0", status = API.Status.STABLE)
    Mono<Booking> createBooking(final Booking booking);

    @API(since = "1.0", status = API.Status.STABLE)
    Mono<Booking> updateBooking(final UUID id, final Booking booking);

    @API(since = "1.0", status = API.Status.STABLE)
    Mono<Booking> deleteBooking(final UUID id);

}
