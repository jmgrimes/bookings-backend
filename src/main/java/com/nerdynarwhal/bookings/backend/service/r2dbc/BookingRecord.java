package com.nerdynarwhal.bookings.backend.service.r2dbc;

import com.nerdynarwhal.bookings.backend.model.BookableSession;
import com.nerdynarwhal.bookings.backend.model.Booking;
import org.apiguardian.api.API;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Persistent
@Table("bookings")
@API(since = "1.0", status = API.Status.INTERNAL)
record BookingRecord(
        @Id @Column("id") UUID id,
        @Column("booker_id") UUID bookerId,
        @Column("bookable_id") UUID bookableId,
        @Column("title") String title,
        @Column("date") LocalDate date,
        @Column("session") BookableSession session) {

    @API(since = "1.0", status = API.Status.INTERNAL)
    public Booking toBooking() {
        return new Booking(id, bookerId, bookableId, title, date, session);
    }

    @API(since = "1.0", status = API.Status.INTERNAL)
    public static BookingRecord of(final UUID id, final Booking booking) {
        return new BookingRecord(
                id, booking.bookerId(), booking.bookableId(), booking.title(), booking.date(), booking.session()
        );
    }

}
