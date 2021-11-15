package com.nerdynarwhal.bookings.backend.service;

import com.nerdynarwhal.bookings.backend.model.Bookable;

import org.apiguardian.api.API;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@API(since = "1.0", status = API.Status.STABLE)
public interface BookableService {

    @API(since = "1.0", status = API.Status.STABLE)
    Flux<Bookable> findBookables();

    @API(since = "1.0", status = API.Status.STABLE)
    Mono<Bookable> findBookable(final UUID id);

    @API(since = "1.0", status = API.Status.STABLE)
    Mono<Bookable> createBookable(final Bookable bookable);

    @API(since = "1.0", status = API.Status.STABLE)
    Mono<Bookable> updateBookable(final UUID id, final Bookable bookable);

    @API(since = "1.0", status = API.Status.STABLE)
    Mono<Bookable> deleteBookable(final UUID id);

}
