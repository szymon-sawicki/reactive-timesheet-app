package net.szymonsawicki.reactivetimesheetapp.web;

import net.szymonsawicki.reactivetimesheetapp.web.handler.TeamHandlers;
import net.szymonsawicki.reactivetimesheetapp.web.handler.TimeEntryHandlers;
import net.szymonsawicki.reactivetimesheetapp.web.handler.UserHandlers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;

@Configuration
public class Routing {
    @Bean
    public RouterFunction<ServerResponse> routingFunction(TeamHandlers teamHandlers, TimeEntryHandlers timeEntryHandlers, UserHandlers userHandlers) {
        return RouterFunctions.nest(

                        // first argument of the router function is a request predicate, second - handler function

                        path("/teams"),
                        RouterFunctions.route(GET("/{name}").and(accept(MediaType.APPLICATION_JSON)), teamHandlers::findByName)
                                .andRoute(GET("/id/{id}").and(accept(MediaType.APPLICATION_JSON)), teamHandlers::findById)
                                .andRoute(POST("/").and(accept(MediaType.APPLICATION_JSON)), teamHandlers::addTeam)
                                .andRoute(DELETE("/{id}").and(accept(MediaType.APPLICATION_JSON)), teamHandlers::deleteTeam))
                .andNest(path("/users"),
                        RouterFunctions.route(GET("/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandlers::findById)
                                .andRoute(GET("/{username}").and(accept(MediaType.APPLICATION_JSON)), userHandlers::findByUsername)
                                .andRoute(POST("/").and(accept(MediaType.APPLICATION_JSON)), userHandlers::createUser)
                                .andRoute(DELETE("/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandlers::deleteUser))
                .andNest(path("/time_entries"),
                        RouterFunctions.route(POST("/").and(accept(MediaType.APPLICATION_JSON)), timeEntryHandlers::addTimeEntry));
    }
}
