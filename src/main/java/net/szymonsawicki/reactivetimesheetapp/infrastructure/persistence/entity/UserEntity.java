package net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.szymonsawicki.reactivetimesheetapp.domain.user.User;
import net.szymonsawicki.reactivetimesheetapp.domain.user.type.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    String id;

    String username;
    String password;
    Role role;
    String teamId;

    public User withPassword(String newPassword) {
        return User.builder()
                .id(id)
                .username(username)
                .password(newPassword)
                .role(role)
                .teamId(teamId)
                .build();
    }

    public User withTeamId(String newTeamId) {
        return User.builder()
                .id(id)
                .username(username)
                .password(password)
                .role(role)
                .teamId(newTeamId)
                .build();
    }

    public User toUser() {
        return User.builder()
                .id(id)
                .username(username)
                .password(password)
                .role(role)
                .teamId(teamId)
                .build();
    }
}
