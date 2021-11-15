package com.nerdynarwhal.bookings.backend.router;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import com.nerdynarwhal.bookings.backend.model.User;
import com.nerdynarwhal.bookings.backend.service.UserService;

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

import java.util.UUID;

@Component
public class UsersRouter {

    private final UserService userService;

    @API(since = "1.0", status = API.Status.STABLE)
    public UsersRouter(@Autowired final UserService userService) {
        super();
        this.userService = userService;
    }

    @API(since = "1.0", status = API.Status.STABLE)
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .path("/users", users -> users
                        .nest(accept(APPLICATION_JSON), acceptingJson -> acceptingJson
                                .path("/{id}", byId -> byId
                                        .GET(this::getUser)
                                        .PUT(this::saveUser)
                                        .DELETE(this::deleteUser)
                                )
                                .GET(this::listUsers)
                                .POST(this::newUser)
                        )
                )
                .build();
    }

    @API(since = "1.0", status = API.Status.INTERNAL)
    private Mono<ServerResponse> listUsers(final ServerRequest request) {
        final MediaType contentType = request.headers().accept().stream().findFirst().orElse(APPLICATION_JSON);
        final Flux<User> users = this.userService.findUsers();
        return ok().contentType(contentType).body(users, User.class);
    }

    @API(since = "1.0", status = API.Status.INTERNAL)
    private Mono<ServerResponse> getUser(final ServerRequest request) {
        final MediaType contentType = request.headers().accept().stream().findFirst().orElse(APPLICATION_JSON);
        final UUID userId = UUID.fromString(request.pathVariable("id"));
        return this.userService.findUser(userId)
                .flatMap(user -> ok().contentType(contentType).bodyValue(user))
                .switchIfEmpty(notFound().build());
    }

    @API(since = "1.0", status = API.Status.INTERNAL)
    private Mono<ServerResponse> newUser(final ServerRequest request) {
        final MediaType contentType = request.headers().accept().stream().findFirst().orElse(APPLICATION_JSON);
        return request.bodyToMono(User.class)
                .flatMap(this.userService::createUser)
                .flatMap(user -> ok().contentType(contentType).bodyValue(user));
    }

    @API(since = "1.0", status = API.Status.INTERNAL)
    private Mono<ServerResponse> saveUser(final ServerRequest request) {
        final MediaType contentType = request.headers().accept().stream().findFirst().orElse(APPLICATION_JSON);
        final UUID userId = UUID.fromString(request.pathVariable("id"));
        return request.bodyToMono(User.class)
                .flatMap(user -> this.userService.updateUser(userId, user))
                .flatMap(user -> ok().contentType(contentType).bodyValue(user));
    }

    @API(since = "1.0", status = API.Status.INTERNAL)
    private Mono<ServerResponse> deleteUser(final ServerRequest request) {
        final MediaType contentType = request.headers().accept().stream().findFirst().orElse(APPLICATION_JSON);
        final UUID userId = UUID.fromString(request.pathVariable("id"));
        return this.userService.deleteUser(userId)
                .flatMap(user -> ok().contentType(contentType).bodyValue(user))
                .switchIfEmpty(notFound().build());
    }

}
