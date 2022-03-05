package net.szymonsawicki.reactivetimesheetapp.domain.team.repository;

import net.szymonsawicki.reactivetimesheetapp.domain.configs.CrudRepository;
import net.szymonsawicki.reactivetimesheetapp.domain.team.Team;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface TeamRepository extends CrudRepository<Team, String> {
    Mono<Team> findByName(String name);
}
