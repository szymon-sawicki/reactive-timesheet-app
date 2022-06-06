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
    List<User> members;

    public Team withMembers(List<User> newMembers) {
        return Team.builder()
                .id(id)
                .name(name)
                .members(newMembers)
                .build();
    }

    public TeamEntity toEntity() {
        return TeamEntity.builder()
                .id(id)
                .name(name)
                .members(members == null ? null : members.stream().map(User::toEntity).toList())
                .build();
    }

    public GetTeamDto toGetTeamDto() {
        return new GetTeamDto(
                id,
                name,
                members.stream().map(User::toGetUserDto).toList());
    }
}
