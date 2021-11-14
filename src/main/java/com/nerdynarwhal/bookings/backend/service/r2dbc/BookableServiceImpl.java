package com.nerdynarwhal.bookings.backend.service.r2dbc;

import com.nerdynarwhal.bookings.backend.model.Bookable;
import com.nerdynarwhal.bookings.backend.service.BookableService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
class BookableServiceImpl implements BookableService {

    private final BookableRecordRepository bookableRecordRepository;

    public BookableServiceImpl(@Autowired final BookableRecordRepository bookableRecordRepository) {
        super();
        this.bookableRecordRepository = bookableRecordRepository;
    }

    public Flux<Bookable> findBookables() {
        return this.bookableRecordRepository.findAll().map(BookableRecord::toBookable);
    }

    public Mono<Bookable> findBookable(final UUID id) {
        return this.bookableRecordRepository.findById(id).map(BookableRecord::toBookable);
    }

    public Mono<Bookable> createBookable(final Bookable bookable) {
        return updateBookable(UUID.randomUUID(), bookable);
    }

    public Mono<Bookable> updateBookable(final UUID id, final Bookable bookable) {
        final BookableRecord bookableRecord = BookableRecord.of(id, bookable);
        return this.bookableRecordRepository.save(bookableRecord).map(BookableRecord::toBookable);
    }

    public Mono<Bookable> deleteBookable(final UUID id) {
        return this.bookableRecordRepository.findById(id)
                .flatMap(bookableRecord -> this.bookableRecordRepository.delete(bookableRecord)
                        .map(nothing -> bookableRecord)
                        .map(BookableRecord::toBookable)
                );
    }

}
