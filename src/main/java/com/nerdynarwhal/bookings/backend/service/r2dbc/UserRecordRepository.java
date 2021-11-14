package com.nerdynarwhal.bookings.backend.service.r2dbc;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface UserRecordRepository extends ReactiveCrudRepository<UserRecord, UUID> {
}
