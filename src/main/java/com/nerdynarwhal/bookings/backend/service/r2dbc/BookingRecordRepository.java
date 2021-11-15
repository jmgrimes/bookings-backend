package com.nerdynarwhal.bookings.backend.service.r2dbc;

import org.apiguardian.api.API;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.UUID;

@Repository
@API(since = "1.0", status = API.Status.INTERNAL)
public interface BookingRecordRepository extends ReactiveCrudRepository<BookingRecord, UUID> {

    @Query(
            "SELECT * FROM booking " +
            "WHERE (:bookerId IS NULL OR booker_id = :bookerId) " +
            "AND (:bookableId IS NULL OR bookable_id = :bookableId) " +
            "AND (:startDate IS NULL OR date >= :startDate) " +
            "AND (:endDate IS NULL OR date <= :endDate)"
    )
    @API(since = "1.0", status = API.Status.INTERNAL)
    Flux<BookingRecord> findAll(
            final UUID bookerId, final UUID bookableId, final LocalDate startDate, final LocalDate endDate);

}
