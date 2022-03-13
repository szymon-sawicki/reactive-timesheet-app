package net.szymonsawicki.reactivetimesheetapp.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.szymonsawicki.reactivetimesheetapp.application.service.exception.TeamServiceException;
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
                .switchIfEmpty(Mono.error(new UserServiceException("username doen't exist")));
    }

    public Mono<GetUserDto> addUser(Mono<CreateUserDto> createUserDtoMono) {
        return createUserDtoMono
                .flatMap(createUserDto -> userRepository
                        .findByUsername(createUserDto.username())
                        .map(user -> {
                            log.error("user with username " + createUserDto.username() + " already exists");
                            return user.toGetUserDto();
                        })
                        .switchIfEmpty(createUserDtoMono.flatMap(userDto -> userRepository
                                .save(userDto.toUser())
                                .map(User::toGetUserDto)
                        )));
    }


}
