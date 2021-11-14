package com.nerdynarwhal.bookings.backend.service.r2dbc;

import com.nerdynarwhal.bookings.backend.model.Bookable;
import com.nerdynarwhal.bookings.backend.model.BookableDay;
import com.nerdynarwhal.bookings.backend.model.BookableSession;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Persistent
@Table("bookables")
record BookableRecord(
        @Id @Column("id") UUID id,
        @Column("name") String name,
        @Column("group") String group,
        @Column("notes") Optional<String> notes,
        @Column("days") List<BookableDay> days,
        @Column("sessions") List<BookableSession> sessions) {

    public Bookable toBookable() {
        return new Bookable(id, name, group, notes, days, sessions);
    }

    public static BookableRecord of(final UUID id, final Bookable bookable) {
        return new BookableRecord(
                id, bookable.name(), bookable.group(), bookable.notes(),
                bookable.days(), bookable.sessions()
        );
    }

}