package com.nerdynarwhal.bookings.backend.service.r2dbc;

import com.nerdynarwhal.bookings.backend.model.Booking;
import com.nerdynarwhal.bookings.backend.service.BookingService;

import org.apiguardian.api.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@Service
@API(since = "1.0", status = API.Status.INTERNAL)
class BookingServiceImpl implements BookingService {

    private final BookingRecordRepository bookingRecordRepository;

    @API(since = "1.0", status = API.Status.INTERNAL)
    public BookingServiceImpl(@Autowired final BookingRecordRepository bookingRecordRepository) {
        super();
        this.bookingRecordRepository = bookingRecordRepository;
    }

    @Override
    public Flux<Booking> findBookings(
            final UUID bookerId, final UUID bookableId, final LocalDate startDate, final LocalDate endDate) {
        return this.bookingRecordRepository.findAll(bookerId, bookableId, startDate, endDate)
                .map(BookingRecord::toBooking);
    }

    @Override
    public Mono<Booking> findBooking(final UUID id) {
        return this.bookingRecordRepository.findById(id).map(BookingRecord::toBooking);
    }

    @Override
    public Mono<Booking> createBooking(final Booking booking) {
        return updateBooking(UUID.randomUUID(), booking);
    }

    @Override
    public Mono<Booking> updateBooking(final UUID id, final Booking booking) {
        final BookingRecord bookingRecord = BookingRecord.of(id, booking);
        return this.bookingRecordRepository.save(bookingRecord).map(BookingRecord::toBooking);
    }

    @Override
    public Mono<Booking> deleteBooking(final UUID id) {
        return this.bookingRecordRepository.findById(id)
                .flatMap(bookingRecord ->
                        this.bookingRecordRepository.delete(bookingRecord)
                                .map(nothing -> bookingRecord)
                                .map(BookingRecord::toBooking)
                );
    }

}
