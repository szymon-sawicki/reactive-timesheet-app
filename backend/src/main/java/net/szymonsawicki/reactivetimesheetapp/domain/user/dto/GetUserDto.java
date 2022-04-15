package net.szymonsawicki.reactivetimesheetapp.domain.user.dto;

import net.szymonsawicki.reactivetimesheetapp.domain.user.User;
import net.szymonsawicki.reactivetimesheetapp.domain.user.type.Role;

public record GetUserDto(String id, String username, String password, Role role) {
    public User toUser() {
        return User.builder()
                .id(id)
                .username(username)
                .password(password)
                .role(role)
                .build();
    }
}
