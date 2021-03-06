package net.szymonsawicki.reactivetimesheetapp.domain.user.repository;

import net.szymonsawicki.reactivetimesheetapp.domain.configs.CrudRepository;
import net.szymonsawicki.reactivetimesheetapp.domain.team.Team;
import net.szymonsawicki.reactivetimesheetapp.domain.user.User;
import net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserRepository extends CrudRepository<User, String> {

    Flux<User> findByTeamId(String teamId);
    Mono<User> findByUsername(String username);
    Flux<User> saveAll(List<User> users);
    Mono<Void> deleteAll(List<User> users);
}
