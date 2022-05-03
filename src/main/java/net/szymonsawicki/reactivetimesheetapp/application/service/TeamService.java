package net.szymonsawicki.reactivetimesheetapp.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.szymonsawicki.reactivetimesheetapp.application.service.exception.TeamServiceException;
import net.szymonsawicki.reactivetimesheetapp.domain.team.Team;
import net.szymonsawicki.reactivetimesheetapp.domain.team.TeamUtils;
import net.szymonsawicki.reactivetimesheetapp.domain.team.dto.CreateTeamDto;
import net.szymonsawicki.reactivetimesheetapp.domain.team.dto.GetTeamDto;
import net.szymonsawicki.reactivetimesheetapp.domain.team.repository.TeamRepository;
import net.szymonsawicki.reactivetimesheetapp.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public Mono<GetTeamDto> findById(String teamId) {
        return teamRepository.findById(teamId)
                .map(Team::toGetTeamDto)
                .switchIfEmpty(Mono.error(new TeamServiceException("Team with given id doesn't exist")));
    }

    public Mono<GetTeamDto> findByName(String name) {
        return teamRepository.findByName(name)
                .map(Team::toGetTeamDto)
                .switchIfEmpty(Mono.error(new TeamServiceException("Team with given name doesn't exist")));
    }

    public Mono<GetTeamDto> addTeam(Mono<CreateTeamDto> createTeamDtoMono) {

        return createTeamDtoMono
                .flatMap(createTeamDto -> teamRepository.findByName(createTeamDto.name())
                        .doOnEach(team -> log.error("Team with name " + createTeamDto.name() + " already exists"))
                        .map(Team::toGetTeamDto)
                                .switchIfEmpty(Mono.defer(() -> createTeamWithMembers(createTeamDto))));
    }

    private Mono<GetTeamDto> createTeamWithMembers(CreateTeamDto createTeamDto) {

        // at first team is inserted into db and all its member are updated with the new teamId

        return teamRepository
                .save(createTeamDto.toTeam())
                .flatMap(insertedTeam -> {
                    var membersToInsert = createTeamDto
                            .members()
                            .stream()
                            .map(createUserDto -> createUserDto.toUser().withTeamId(TeamUtils.toId.apply(insertedTeam)))
                            .toList();

                    // then all members with correct teamId are saved into db, flux is converted to list which is used to update team's members. At the end updated team is saved in db.

                    return userRepository
                            .saveAll(membersToInsert)
                            .collectList()
                            .flatMap(insertedUsers -> teamRepository
                                    .save(insertedTeam.withMembers(insertedUsers))
                                    .map(Team::toGetTeamDto));
                });
    }

    public Mono<GetTeamDto> deleteTeam(String teamId) {

        return teamRepository
                .findById(teamId)
                .flatMap(team -> {

                    // at first, I'm changing teamId of all team members to null

                    var membersToUpdate = TeamUtils.toMembers.apply(team)
                            .stream()
                            .map(member -> member.withTeamId(null))
                            .toList();

                    // then I'm saving all updated members, deleting team and returning mono of DTO

                    return userRepository.saveAll(membersToUpdate)
                            .then(teamRepository.delete(teamId))
                            .then(Mono.just(team.toGetTeamDto()));
                })
                .switchIfEmpty(Mono.error(new TeamServiceException("cannot find team to delete")));
    }
}
