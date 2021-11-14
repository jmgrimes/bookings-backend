package com.nerdynarwhal.bookings.backend.router;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import com.nerdynarwhal.bookings.backend.model.User;
import com.nerdynarwhal.bookings.backend.service.UserService;

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
public class UserRoutesHandler {

    private final UserService userService;

    public UserRoutesHandler(@Autowired final UserService userService) {
        super();
        this.userService = userService;
    }

    @API(since = "1.0", status = API.Status.STABLE)
    public Mono<ServerResponse> listUsers(final ServerRequest request) {
        final MediaType contentType = request.headers().accept().stream().findFirst().orElse(APPLICATION_JSON);
        final Flux<User> users = this.userService.findUsers();
        return ok().contentType(contentType).body(users, User.class);
    }

    @API(since = "1.0", status = API.Status.STABLE)
    public Mono<ServerResponse> getUser(final ServerRequest request) {
        final MediaType contentType = request.headers().accept().stream().findFirst().orElse(APPLICATION_JSON);
        final UUID userId = UUID.fromString(request.pathVariable("id"));
        return this.userService.findUser(userId)
                .flatMap(user -> ok().contentType(contentType).bodyValue(user))
                .switchIfEmpty(notFound().build());
    }

}
