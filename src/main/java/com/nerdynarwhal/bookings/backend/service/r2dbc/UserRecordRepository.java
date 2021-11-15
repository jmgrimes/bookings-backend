package com.nerdynarwhal.bookings.backend.service.r2dbc;

import org.apiguardian.api.API;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@API(since = "1.0", status = API.Status.INTERNAL)
interface UserRecordRepository extends ReactiveCrudRepository<UserRecord, UUID> {
}
