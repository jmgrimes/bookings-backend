package com.nerdynarwhal.bookings.backend.router;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import com.nerdynarwhal.bookings.backend.model.Bookable;
import com.nerdynarwhal.bookings.backend.service.BookableService;

import org.apiguardian.api.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class BookableRoutesHandler {

    private final BookableService bookableService;

    public BookableRoutesHandler(@Autowired final BookableService bookableService) {
        super();
        this.bookableService = bookableService;
    }

    @API(since = "1.0", status = API.Status.STABLE)
    public Mono<ServerResponse> listBookables(final ServerRequest request) {
        final MediaType contentType = request.headers().accept().stream().findFirst().orElse(APPLICATION_JSON);
        final Flux<Bookable> bookables = this.bookableService.findBookables();
        return ok().contentType(contentType).body(bookables, Bookable.class);
    }

    @API(since = "1.0", status = API.Status.STABLE)
    public Mono<ServerResponse> getBookable(final ServerRequest request) {
        final MediaType contentType = request.headers().accept().stream().findFirst().orElse(APPLICATION_JSON);
        final UUID bookableId = UUID.fromString(request.pathVariable("id"));
        return this.bookableService.findBookable(bookableId)
                .flatMap(bookable -> ok().contentType(contentType).bodyValue(bookable))
                .switchIfEmpty(notFound().build());
    }

    @API(since = "1.0", status = API.Status.STABLE)
    public Mono<ServerResponse> newBookable(final ServerRequest request) {
        final MediaType contentType = request.headers().contentType().orElse(APPLICATION_JSON);
        return request.bodyToMono(Bookable.class)
                .flatMap(this.bookableService::createBookable)
                .flatMap(bookable -> ok().contentType(contentType).bodyValue(bookable));
    }

    @API(since = "1.0", status = API.Status.STABLE)
    public Mono<ServerResponse> saveBookable(final ServerRequest request) {
        final MediaType contentType = request.headers().contentType().orElse(APPLICATION_JSON);
        final UUID bookableId = UUID.fromString(request.pathVariable("id"));
        return request.bodyToMono(Bookable.class)
                .flatMap(bookable -> this.bookableService.updateBookable(bookableId, bookable))
                .flatMap(bookable -> ok().contentType(contentType).bodyValue(bookable));
    }

    @API(since = "1.0", status = API.Status.STABLE)
    public Mono<ServerResponse> deleteBookable(final ServerRequest request) {
        final MediaType contentType = request.headers().accept().stream().findFirst().orElse(APPLICATION_JSON);
        final UUID bookableId = UUID.fromString(request.pathVariable("id"));
        return this.bookableService.deleteBookable(bookableId)
                .flatMap(bookable -> ok().contentType(contentType).bodyValue(bookable))
                .switchIfEmpty(notFound().build());
    }

}
