package net.szymonsawicki.reactivetimesheetapp.web;

import net.szymonsawicki.reactivetimesheetapp.web.handler.TeamHandlers;
import net.szymonsawicki.reactivetimesheetapp.web.handler.TimeEntryHandlers;
import net.szymonsawicki.reactivetimesheetapp.web.handler.UserHandlers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class Routing {
    @Bean
    public RouterFunction<ServerResponse> routingFunction(TeamHandlers teamHandlers, TimeEntryHandlers timeEntryHandlers, UserHandlers userHandlers) {
        return RouterFunctions.nest(
                RequestPredicates.path("/teams/"
                ), RouterFunctions.route(RequestPredicates.GET("/{name}")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), teamHandlers::findByName)
                        .andRoute(RequestPredicates.GET("/id/{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), teamHandlers::findById)
                        .andRoute(RequestPredicates.POST("/").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), teamHandlers::addTeam)
                        .andRoute(RequestPredicates.DELETE("/{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), teamHandlers::deleteTeam));
    }
}
