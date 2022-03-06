package net.szymonsawicki.reactivetimesheetapp.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.szymonsawicki.reactivetimesheetapp.application.service.exception.TeamServiceException;
import net.szymonsawicki.reactivetimesheetapp.domain.team.Team;
import net.szymonsawicki.reactivetimesheetapp.domain.team.dto.CreateTeamDto;
import net.szymonsawicki.reactivetimesheetapp.domain.team.dto.GetTeamDto;
import net.szymonsawicki.reactivetimesheetapp.domain.team.repository.TeamRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public Mono<GetTeamDto> findById(String teamId) {
        return teamRepository.findById(teamId)
                .map(Team::toGetTeamDto)
                .switchIfEmpty(Mono.error(new TeamServiceException("id doesn't exist")));
    }

    public Mono<GetTeamDto> findByName(String name) {
        return teamRepository.findByName(name)
                .map(Team::toGetTeamDto)
                .switchIfEmpty(Mono.error(new TeamServiceException("Username doesn't exist")));
    }

    public Mono<GetTeamDto> addTeam(Mono<CreateTeamDto> createTeamDtoMono) {

        return createTeamDtoMono
                .flatMap(createTeamDto -> {
                    return teamRepository.findByName(createTeamDto.name())
                            .map(team -> {
                                throw new TeamServiceException("Team with name " + createTeamDto.name() + " already exists");
                            })
                            .switchIfEmpty(createTeamWithMembers(createTeamDto));
                });
    }

    private Mono<GetTeamDto> createTeamWithMembers(CreateTeamDto createTeamDto) {

        var teamToInsert = createTeamDto.toTeam();

        return teamRepository
                .save(teamToInsert)
                .flatMap(insertTeam -> {
                    var membersToInsert = cre // TODO

                })

    }

}
