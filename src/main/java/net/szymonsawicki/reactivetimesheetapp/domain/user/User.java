package net.szymonsawicki.reactivetimesheetapp.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.szymonsawicki.reactivetimesheetapp.domain.team.Team;
import net.szymonsawicki.reactivetimesheetapp.domain.user.dto.GetUserDto;
import net.szymonsawicki.reactivetimesheetapp.domain.user.type.Role;
import net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.entity.UserEntity;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class User {

    String id;
    String username;
    String password;
    Role role;
    String teamId;

    public User withTeamId(String newTeamId) {
        return User.builder()
                .id(id)
                .username(username)
                .password(password)
                .role(role)
                .teamId(newTeamId)
                .build();
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .id(id)
                .username(username)
                .password(password)
                .role(role)
                .teamId(teamId)
                .build();
    }

    public GetUserDto toGetUserDto() {
        return new GetUserDto(
                id,
                username,
                password,
                role);
    }
}
