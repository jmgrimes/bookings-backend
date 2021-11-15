package com.nerdynarwhal.bookings.backend.service.r2dbc;

import com.nerdynarwhal.bookings.backend.model.User;

import org.apiguardian.api.API;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Optional;
import java.util.UUID;

@Persistent
@Table("users")
@API(since = "1.0", status = API.Status.INTERNAL)
record UserRecord(
        @Id @Column("id") UUID id,
        @Column("name") String name,
        @Column("title") String title,
        @Column("notes") Optional<String> notes,
        @Column("image") String image) {

    @API(since = "1.0", status = API.Status.INTERNAL)
    public User toUser() {
        return new User(id, name, title, notes, image);
    }

    public static UserRecord of(final UUID id, final User user) {
        return new UserRecord(id, user.name(), user.title(), user.notes(), user.image());
    }

}
