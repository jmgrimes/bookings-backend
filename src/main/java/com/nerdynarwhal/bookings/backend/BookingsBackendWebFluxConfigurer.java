package com.nerdynarwhal.bookings.backend;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import com.nerdynarwhal.bookings.backend.router.BookableRoutesHandler;
import com.nerdynarwhal.bookings.backend.router.BookingRoutesHandler;
import com.nerdynarwhal.bookings.backend.router.UserRoutesHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Configuration
public class BookingsBackendWebFluxConfigurer implements WebFluxConfigurer {

    @Bean
    public RouterFunction<?> bookables(@Autowired final BookableRoutesHandler bookableRoutesHandler) {
        return RouterFunctions.route()
                .path("/bookables", bookables -> bookables
                        .nest(accept(APPLICATION_JSON), acceptingJson -> acceptingJson
                                .GET(bookableRoutesHandler::listBookables)
                                .POST(bookableRoutesHandler::newBookable)
                                .path("/{id}", byId -> byId
                                        .GET(bookableRoutesHandler::getBookable)
                                        .PUT(bookableRoutesHandler::saveBookable)
                                        .DELETE(bookableRoutesHandler::deleteBookable)
                                )
                        )
                )
                .build();
    }

    @Bean
    public RouterFunction<?> bookings(@Autowired final BookingRoutesHandler bookingRoutesHandler) {
        return RouterFunctions.route()
                .path("/bookings", bookings -> bookings
                        .nest(accept(APPLICATION_JSON), acceptingJson -> acceptingJson
                                .GET(bookingRoutesHandler::listBookings)
                                .POST(bookingRoutesHandler::newBooking)
                                .path("/{id}", byId -> byId
                                        .GET(bookingRoutesHandler::getBooking)
                                        .PUT(bookingRoutesHandler::saveBooking)
                                        .DELETE(bookingRoutesHandler::deleteBooking)
                                )
                        )
                )
                .build();
    }

    @Bean
    public RouterFunction<?> users(@Autowired final UserRoutesHandler userRoutesHandler) {
        return RouterFunctions.route()
                .GET("/users", accept(APPLICATION_JSON), userRoutesHandler::listUsers)
                .GET("/users/{id}", accept(APPLICATION_JSON), userRoutesHandler::getUser)
                .build();
    }

}
