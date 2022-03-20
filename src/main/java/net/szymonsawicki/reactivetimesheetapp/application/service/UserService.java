package net.szymonsawicki.reactivetimesheetapp.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.szymonsawicki.reactivetimesheetapp.application.service.exception.UserServiceException;
import net.szymonsawicki.reactivetimesheetapp.domain.team.TeamUtils;
import net.szymonsawicki.reactivetimesheetapp.domain.team.repository.TeamRepository;
import net.szymonsawicki.reactivetimesheetapp.domain.user.User;
import net.szymonsawicki.reactivetimesheetapp.domain.user.UserUtils;
import net.szymonsawicki.reactivetimesheetapp.domain.user.dto.CreateUserDto;
import net.szymonsawicki.reactivetimesheetapp.domain.user.dto.GetUserDto;
import net.szymonsawicki.reactivetimesheetapp.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public Mono<GetUserDto> findById(String userId) {
        return userRepository.findById(userId)
                .map(User::toGetUserDto)
                .switchIfEmpty(Mono.error(new UserServiceException("id doesn't exist")));
    }

    public Mono<GetUserDto> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(User::toGetUserDto)
                .switchIfEmpty(Mono.error(new UserServiceException("username doesn't exist")));
    }

    public Mono<GetUserDto> addUser(Mono<CreateUserDto> createUserDtoMono) {
        return createUserDtoMono
                .flatMap(createUserDto -> userRepository
                        .findByUsername(createUserDto.username())
                        .hasElement()
                        .flatMap(isUserPresent -> Boolean.TRUE.equals(isUserPresent)
                                ?
                                Mono.error(new UserServiceException("user with username " + createUserDto.username() + " already exists"))
                                :
                                createUser(createUserDto)));
    }

    private Mono<GetUserDto> createUser(CreateUserDto createUserDto) {
        var user = createUserDto.toUser();
        return userRepository
                .save(user)
                .map(User::toGetUserDto);
    }

    public Mono<GetUserDto> deleteUser(String userId) {
        return userRepository
                .findById(userId)
                .flatMap(user -> {

                    // when teamId is not null then list of members in related team will be updated

                    String teamId = UserUtils.toTeamId.apply(user);
                    if (teamId != null) {
                        teamRepository
                                .findById(teamId)
                                .flatMap(team -> {
                                    TeamUtils.toMembers.apply(team).remove(user);
                                    return teamRepository.save(team);
                                });
                    }
                    userRepository.delete(userId);
                    return Mono.just(user.toGetUserDto());
                })
                .switchIfEmpty(Mono.error(new UserServiceException("cannot find user to delete")));
    }
}
