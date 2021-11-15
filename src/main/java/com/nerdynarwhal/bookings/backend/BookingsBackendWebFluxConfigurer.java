package com.nerdynarwhal.bookings.backend;

import com.nerdynarwhal.bookings.backend.router.BookablesRouter;
import com.nerdynarwhal.bookings.backend.router.BookingsRouter;
import com.nerdynarwhal.bookings.backend.router.UsersRouter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class BookingsBackendWebFluxConfigurer implements WebFluxConfigurer {

    @Bean
    public RouterFunction<ServerResponse> bookables(@Autowired final BookablesRouter bookablesRouter) {
        return bookablesRouter.routerFunction();
    }

    @Bean
    public RouterFunction<ServerResponse> bookings(@Autowired final BookingsRouter bookingsRouter) {
        return bookingsRouter.routerFunction();
    }

    @Bean
    public RouterFunction<ServerResponse> users(@Autowired final UsersRouter usersRouter) {
        return usersRouter.routerFunction();
    }

}
