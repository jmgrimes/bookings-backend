package com.nerdynarwhal.bookings.backend.service;

import com.nerdynarwhal.bookings.backend.model.User;

import org.apiguardian.api.API;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@API(since = "1.0", status = API.Status.STABLE)
public interface UserService {

    @API(since = "1.0", status = API.Status.STABLE)
    Flux<User> findUsers();

    @API(since = "1.0", status = API.Status.STABLE)
    Mono<User> findUser(final UUID id);

    @API(since = "1.0", status = API.Status.STABLE)
    Mono<User> createUser(final User user);

    @API(since = "1.0", status = API.Status.STABLE)
    Mono<User> updateUser(final UUID id, final User user);

    @API(since = "1.0", status = API.Status.STABLE)
    Mono<User> deleteUser(final UUID id);

}
