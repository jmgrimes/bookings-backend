package com.nerdynarwhal.bookings.backend.router;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import com.nerdynarwhal.bookings.backend.model.Booking;
import com.nerdynarwhal.bookings.backend.service.BookingService;

import org.apiguardian.api.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@Component
@API(since = "1.0", status = API.Status.STABLE)
public class BookingsRouter {

    private final BookingService bookingService;

    @API(since = "1.0", status = API.Status.STABLE)
    public BookingsRouter(@Autowired final BookingService bookingService) {
        super();
        this.bookingService = bookingService;
    }

    @API(since = "1.0", status = API.Status.STABLE)
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .path("/bookings", bookings -> bookings
                        .nest(accept(APPLICATION_JSON), acceptingJson -> acceptingJson
                                .GET(this::listBookings)
                                .POST(this::newBooking)
                                .path("/{id}", byId -> byId
                                        .GET(this::getBooking)
                                        .PUT(this::saveBooking)
                                        .DELETE(this::deleteBooking)
                                )
                        )
                )
                .build();
    }

    @API(since = "1.0", status = API.Status.INTERNAL)
    private Mono<ServerResponse> listBookings(final ServerRequest request) {
        final MediaType contentType = request.headers().accept().stream().findFirst().orElse(APPLICATION_JSON);
        final UUID bookerId = request.queryParam("bookerId").map(UUID::fromString).orElse(null);
        final UUID bookingId = request.queryParam("bookingId").map(UUID::fromString).orElse(null);
        final LocalDate startDate = request.queryParam("startDate").map(LocalDate::parse).orElse(null);
        final LocalDate endDate = request.queryParam("endDate").map(LocalDate::parse).orElse(null);
        final Flux<Booking> bookings = this.bookingService.findBookings(bookerId, bookingId, startDate, endDate);
        return ok().contentType(contentType).body(bookings, Booking.class);
    }

    @API(since = "1.0", status = API.Status.INTERNAL)
    private Mono<ServerResponse> getBooking(final ServerRequest request) {
        final MediaType contentType = request.headers().accept().stream().findFirst().orElse(APPLICATION_JSON);
        final UUID bookingId = UUID.fromString(request.pathVariable("id"));
        return this.bookingService.findBooking(bookingId)
                .flatMap(booking -> ok().contentType(contentType).bodyValue(booking))
                .switchIfEmpty(notFound().build());
    }

    @API(since = "1.0", status = API.Status.INTERNAL)
    private Mono<ServerResponse> newBooking(final ServerRequest request) {
        final MediaType contentType = request.headers().contentType().orElse(APPLICATION_JSON);
        return request.bodyToMono(Booking.class)
                .flatMap(this.bookingService::createBooking)
                .flatMap(booking -> ok().contentType(contentType).bodyValue(booking));
    }

    @API(since = "1.0", status = API.Status.INTERNAL)
    private Mono<ServerResponse> saveBooking(final ServerRequest request) {
        final MediaType contentType = request.headers().contentType().orElse(APPLICATION_JSON);
        final UUID bookingId = UUID.fromString(request.pathVariable("id"));
        return request.bodyToMono(Booking.class)
                .flatMap(booking -> this.bookingService.updateBooking(bookingId, booking))
                .flatMap(booking -> ok().contentType(contentType).bodyValue(booking));
    }

    @API(since = "1.0", status = API.Status.INTERNAL)
    private Mono<ServerResponse> deleteBooking(final ServerRequest request) {
        final MediaType contentType = request.headers().accept().stream().findFirst().orElse(APPLICATION_JSON);
        final UUID bookingId = UUID.fromString(request.pathVariable("id"));
        return this.bookingService.deleteBooking(bookingId)
                .flatMap(booking -> ok().contentType(contentType).bodyValue(booking))
                .switchIfEmpty(notFound().build());
    }

}
