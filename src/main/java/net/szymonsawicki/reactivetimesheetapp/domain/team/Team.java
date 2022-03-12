package net.szymonsawicki.reactivetimesheetapp.domain.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.szymonsawicki.reactivetimesheetapp.domain.team.dto.GetTeamDto;
import net.szymonsawicki.reactivetimesheetapp.domain.user.User;
import net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.entity.TeamEntity;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Team {

    String id;
    String name;
    User lead;
    List<User> members;

    public Team withMembers(List<User> newMembers) {
        return Team.builder()
                .id(id)
                .name(name)
                .lead(lead)
                .members(newMembers)
                .build();
    }

    public TeamEntity toEntity() {
        return TeamEntity.builder()
                .id(id)
                .name(name)
                .lead(lead)
                .members(members)
                .build();
    }

    public GetTeamDto toGetTeamDto() {
        return new GetTeamDto(
                id,
                name,
                lead,
                members);
    }
}
