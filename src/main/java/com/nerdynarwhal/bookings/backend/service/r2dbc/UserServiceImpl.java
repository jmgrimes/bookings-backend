package com.nerdynarwhal.bookings.backend.service.r2dbc;

import com.nerdynarwhal.bookings.backend.model.User;
import com.nerdynarwhal.bookings.backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
class UserServiceImpl implements UserService {

    private final UserRecordRepository userRecordRepository;

    public UserServiceImpl(@Autowired final UserRecordRepository userRecordRepository) {
        super();
        this.userRecordRepository = userRecordRepository;
    }

    public Flux<User> findUsers() {
        return this.userRecordRepository.findAll().map(UserRecord::toUser);
    }

    public Mono<User> findUser(final UUID id) {
        return this.userRecordRepository.findById(id).map(UserRecord::toUser);
    }

}
