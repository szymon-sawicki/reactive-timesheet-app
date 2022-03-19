package net.szymonsawicki.reactivetimesheetapp.web.handler;


import lombok.RequiredArgsConstructor;
import net.szymonsawicki.reactivetimesheetapp.application.service.UserService;
import net.szymonsawicki.reactivetimesheetapp.domain.user.dto.CreateUserDto;
import net.szymonsawicki.reactivetimesheetapp.web.config.GlobalRoutingHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandlers {
    private final UserService userService;

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        var userId = serverRequest.pathVariable("userId");
        return GlobalRoutingHandler.doRequest(userService.findById(userId), HttpStatus.OK);
    }

    public Mono<ServerResponse> findByUsername(ServerRequest serverRequest) {
        var username = serverRequest.pathVariable("username");
        return GlobalRoutingHandler.doRequest(userService.findById(username), HttpStatus.OK);
    }

    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        var createUserDtoMono = serverRequest.bodyToMono(CreateUserDto.class);
        return GlobalRoutingHandler.doRequest(userService.addUser(createUserDtoMono), HttpStatus.CREATED);
    }

    public Mono<ServerResponse> deleteUser(ServerRequest serverRequest) {
        var userId = serverRequest.pathVariable("userId");
        return GlobalRoutingHandler.doRequest(userService.deleteUser(userId), HttpStatus.OK);
    }
}
