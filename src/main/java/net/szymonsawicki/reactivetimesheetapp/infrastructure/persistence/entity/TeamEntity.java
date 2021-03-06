package net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.szymonsawicki.reactivetimesheetapp.domain.team.Team;
import net.szymonsawicki.reactivetimesheetapp.domain.user.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "teams")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamEntity {

    @Id
    String id;

    String name;
    List<UserEntity> members;

    public Team toTeam() {
        return Team.builder()
                .id(id)
                .name(name)
                .members(members.stream().map(UserEntity::toUser).toList())
                .build();
    }
}
