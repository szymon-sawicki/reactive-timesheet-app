package net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.dao;

import net.szymonsawicki.reactivetimesheetapp.domain.team.Team;
import net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.entity.TeamEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface TeamDao extends ReactiveMongoRepository<TeamEntity, String> {
    Mono<TeamEntity> findByName(String name);
}
