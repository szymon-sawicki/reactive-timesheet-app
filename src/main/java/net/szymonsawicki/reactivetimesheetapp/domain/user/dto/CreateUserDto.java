package net.szymonsawicki.reactivetimesheetapp.domain.user.dto;

import net.szymonsawicki.reactivetimesheetapp.domain.user.User;
import net.szymonsawicki.reactivetimesheetapp.domain.user.type.Role;

public record CreateUserDto(String username, String password, String passwordConfirmation, Role role, String teamId) {
    User toUser() {
        return User.builder()
                .username(username)
                .password(password)
                .role(role)
                .teamId(teamId)
                .build();
    }
}

