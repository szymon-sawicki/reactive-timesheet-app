package net.szymonsawicki.reactivetimesheetapp.web.handler;

import lombok.RequiredArgsConstructor;
import net.szymonsawicki.reactivetimesheetapp.application.service.TeamService;
import net.szymonsawicki.reactivetimesheetapp.domain.team.dto.CreateTeamDto;
import net.szymonsawicki.reactivetimesheetapp.web.config.GlobalRoutingHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TeamHandlers {
    private final TeamService teamService;

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        var teamId = serverRequest.pathVariable("id");
        return GlobalRoutingHandler.doRequest(teamService.findById(teamId), HttpStatus.OK);
    }

    public Mono<ServerResponse> findByName(ServerRequest serverRequest) {
        var teamName = serverRequest.pathVariable("name");
        return GlobalRoutingHandler.doRequest(teamService.findByName(teamName), HttpStatus.OK);
    }

    public Mono<ServerResponse> addTeam(ServerRequest serverRequest) {
        var createTeamDtoMono = serverRequest.bodyToMono(CreateTeamDto.class);
        return GlobalRoutingHandler.doRequest(teamService.addTeam(createTeamDtoMono), HttpStatus.CREATED);
    }

    public Mono<ServerResponse> deleteTeam(ServerRequest serverRequest) {
        var teamId = serverRequest.pathVariable("id");
        return GlobalRoutingHandler.doRequest(teamService.deleteTeam(teamId), HttpStatus.OK);
    }
}
