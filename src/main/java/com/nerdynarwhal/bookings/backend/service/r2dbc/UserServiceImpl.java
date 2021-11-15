package com.nerdynarwhal.bookings.backend.service.r2dbc;

import com.nerdynarwhal.bookings.backend.model.User;
import com.nerdynarwhal.bookings.backend.service.UserService;

import org.apiguardian.api.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@API(since = "1.0", status = API.Status.INTERNAL)
class UserServiceImpl implements UserService {

    private final UserRecordRepository userRecordRepository;

    @API(since = "1.0", status = API.Status.INTERNAL)
    public UserServiceImpl(@Autowired final UserRecordRepository userRecordRepository) {
        super();
        this.userRecordRepository = userRecordRepository;
    }

    @Override
    public Flux<User> findUsers() {
        return this.userRecordRepository.findAll().map(UserRecord::toUser);
    }

    @Override
    public Mono<User> findUser(final UUID id) {
        return this.userRecordRepository.findById(id).map(UserRecord::toUser);
    }

}
